package com.my.busrevapp;

import android.os.*;
import android.text.InputType;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.util.*;

import java.util.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.content.Intent;
import android.net.Uri;
import android.widget.AdapterView;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.bumptech.glide.Glide;

public class AllActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private ImageView author,search;
    private MainAdapter adapter;
    RecyclerView mRecyclerView;
    private String searchtext = "";
    private String html = "";
    private String urm = "";
    private SwipeRefreshLayout swipeRefreshLayout;
    Boolean isScrolling = false;
    LinearLayoutManager linearLayoutManager;
    int page = 2;
    int currentItems, totalItems, scrollOutItems;
    private ArrayList<HashMap<String, Object>> mo = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> mo2 = new ArrayList<>();
    private ImageView imageview1;
    private TextView textview1,foralltext;
    private String m_Text = "";
    int count = 0;
    private Intent intent = new Intent();
    String author_title = "";

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.all);
        author=findViewById(R.id.all_author);
        search=findViewById(R.id.all_search);
        foralltext=findViewById(R.id.titleforall);
        if (getIntent().getStringExtra("key").equals("author")) {
            search.setVisibility(View.GONE);

            if (getIntent().getStringExtra("auth_id").equals("5")) {
                author_title = "Βασίλης Ανδριανόπουλος";
            }
            if (getIntent().getStringExtra("auth_id").equals("7")) {

                author_title = "Business Review";
            }
            if (getIntent().getStringExtra("auth_id").equals("2")) {
                author_title = "Δημήτρης Βλάμης";
            }
            if (getIntent().getStringExtra("auth_id").equals("4")) {
                author_title = "Χάρης Αθανασίου";
            }
            if (getIntent().getStringExtra("auth_id").equals("6")) {
                author_title = "Αλέξανδρος Απέργης";
            }
            if (getIntent().getStringExtra("auth_id").equals("9")) {
                author_title = "BR Guest";
            }
            if (getIntent().getStringExtra("auth_id").equals("10")) {
                author_title = "Βασίλης Γιάννης";
            }
            foralltext.setText(author_title);
        }
        if (getIntent().getStringExtra("key").equals("search")){
            m_Text=getIntent().getStringExtra("search");
            author.setVisibility(View.GONE);
            foralltext.setText("'"+m_Text+"'");

        }
        if (getIntent().getStringExtra("key").equals("categories")){
            author.setVisibility(View.GONE);
            search.setVisibility(View.GONE);
            foralltext.setVisibility(View.GONE);

        }
        swipeRefreshLayout = findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.getResources().getColor(R.color.colorAccent);
        initialize(_savedInstanceState);
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setSelectedItemId(R.id.nav_main);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        final View iconView = menuView.getChildAt(2).findViewById(com.google.android.material.R.id.icon);
        final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
        final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, displayMetrics);
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, displayMetrics);
        iconView.setLayoutParams(layoutParams);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.categories:
                        startActivity(new Intent (getApplicationContext(),Categories.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.search:
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AllActivity.this, R.style.CustomAlertDialog);
                        alertDialogBuilder.setTitle("Αναζήτηση");
                        final EditText input = new EditText(AllActivity.this);

                        alertDialogBuilder.setView(input);
                        alertDialogBuilder.setPositiveButton("Αναζήτηση", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                searchtext = input.getText().toString();
                                intent.putExtra("search",searchtext);
                                intent.putExtra("key", "search");
                                intent.setClass(getApplicationContext(), AllActivity.class);
                                startActivity(intent);
                                overridePendingTransition(0,0);
                            }
                        });
                        alertDialogBuilder.setNegativeButton("Ακύρωση", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alertDialogBuilder.show();

                        return true;
                    case R.id.nav_main:
                        startActivity(new Intent (getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.favourites:
                        startActivity(new Intent (getApplicationContext(),FavouritesActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.more:
                        startActivity(new Intent (getApplicationContext(),MoreActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        linearLayoutManager = new LinearLayoutManager(AllActivity.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);

        imageview1 = (ImageView) findViewById(R.id.imageview1);
        textview1 = (TextView) findViewById(R.id.textview1);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = linearLayoutManager.getChildCount();
                totalItems = linearLayoutManager.getItemCount();
                scrollOutItems = linearLayoutManager.findFirstVisibleItemPosition();
                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                    isScrolling = false;
                    if (getIntent().getStringExtra("key").equals("categories")) {
                        urm = "https://businessrev.gr/wp-json/wp/v2/posts/?_embed&categories="+getIntent().getStringExtra("cat_id")+"&per_page=8"+"&page=" + page;

                    }
                    else if (getIntent().getStringExtra("key").equals("search")) {

                        urm = "https://businessrev.gr/wp-json/wp/v2/posts?search="+m_Text+"&per_page=8"+"&page=" + page;

                    }
                    else if (getIntent().getStringExtra("key").equals("author")) {

                        urm = "https://businessrev.gr/wp-json/wp/v2/posts/?author="+getIntent().getStringExtra("auth_id")+"&per_page=8"+"&page=" + page;
                    }
                    page = page + 1;
                    new BackTask().execute(urm);

                }

            }
        });

    }


    private void initListener() {

        adapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(AllActivity.this, PostviewerActivity.class);
                intent.putExtra("activity_id", "from_main");
                intent.putExtra("post_id", mo.get((int) position).get("id").toString());
                intent.putExtra("post_link", mo.get((int) position).get("link").toString().replace("\"", ""));
                startActivity(intent);

            }
        });

    }

    private void initializeLogic() {

        textview1.setVisibility(View.GONE);
        imageview1.setVisibility(View.GONE);
        if (getIntent().getStringExtra("key").equals("search")) {
            urm = "https://businessrev.gr/wp-json/wp/v2/posts?search=".concat(m_Text).concat("&per_page=8");
            onLoadingSwipeRefresh(urm);

        }

        if (getIntent().getStringExtra("key").equals("author")) {
            urm = "https://businessrev.gr/wp-json/wp/v2/posts/?author=".concat(getIntent().getStringExtra("auth_id").concat("&per_page=8"));

            onLoadingSwipeRefresh(urm);
        }

        if (getIntent().getStringExtra("key").equals("categories")) {
            urm = "https://businessrev.gr/wp-json/wp/v2/posts/?_embed&categories=".concat(getIntent().getStringExtra("cat_id")).concat("&per_page=8");
            onLoadingSwipeRefresh(urm);

        }
    }

    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);

        switch (_requestCode) {

            default:
                break;
        }
    }

    public void onRefresh() {
        page=2;
        count = 0;
        new BackTask().execute(urm);

    }

    private void onLoadingSwipeRefresh(final String urm) {
        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        count = 0;
                        new BackTask().execute(urm);
                    }
                }
        );
    }


    private class BackTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {

                swipeRefreshLayout.setRefreshing(true);

        }

        protected String doInBackground(String... address) {
            String output = "";
            try {
                java.net.URL url = new java.net.URL(address[0]);
                java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(url.openStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    output += line;
                }
                in.close();
            } catch (java.net.MalformedURLException e) {
                output = e.getMessage();
            } catch (java.io.IOException e) {
                output = e.getMessage();
            } catch (Exception e) {
                output = e.toString();
            }
            return output;
        }

        protected void onProgressUpdate(Integer... values) {
        }

        protected void onPostExecute(String s) {
            html = s;
            if (html.toLowerCase().contains("Unable to resolve host".toLowerCase())) {
                swipeRefreshLayout.setRefreshing(false);
                Toastandmore.showMessage(getApplicationContext(), "Παρακαλώ ελέγξτε την σύνδεσή σας στο διαδίκτυο και δοκιμάστε ξανά");
            }
            else if (html.contains("date")){
                if (count == 0) {
                    count = count + 1;
                    mo = new Gson().fromJson(html, new TypeToken<ArrayList<HashMap<String, Object>>>() {
                    }.getType());

                    adapter = new MainAdapter(AllActivity.this, mo);
                    mRecyclerView.setAdapter(adapter);
                    initListener();
                    swipeRefreshLayout.setRefreshing(false);

                }
                else {
                    mo2 = new Gson().fromJson(html, new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType());
                    mo.addAll(mo2);
                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);

                }
            }
            swipeRefreshLayout.setRefreshing(false);
        }
    }

}