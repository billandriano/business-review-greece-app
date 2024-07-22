package com.my.busrevapp;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.os.*;
import android.text.InputType;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.util.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.ListView;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.net.Uri;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.AdapterView;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


	private String html = "";
	private String m_Text = "";
    Boolean isScrolling=false;
    LinearLayoutManager linearLayoutManager;
    int currentItems, totalItems, scrollOutItems;
	private int page=2;
	int count=0;
	private ArrayList<HashMap<String, Object>> mo = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> mo2 = new ArrayList<>();
    private String urm = "https://businessrev.gr/wp-json/wp/v2/posts?per_page=8";
	RecyclerView mRecyclerView;
	private MainAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    DarkSave darkSave;
    Context context=this;
    

	private Intent intent = new Intent();

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		darkSave=new DarkSave(this);
		if (darkSave.loadNightModeState()==true){
			AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
		} else {
			AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
		}
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
						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.CustomAlertDialog);
						alertDialogBuilder.setTitle("Αναζήτηση");
						final EditText input = new EditText(MainActivity.this);
						input.setInputType(InputType.TYPE_CLASS_TEXT);
						alertDialogBuilder.setView(input);
						alertDialogBuilder.setPositiveButton("Αναζήτηση", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								m_Text = input.getText().toString();
								intent.putExtra("search",m_Text);
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



		swipeRefreshLayout = findViewById(R.id.refresh);
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.getResources().getColor(R.color.colorAccent);
		initialize(_savedInstanceState);
		initializeLogic();


	}


	private void initListener(){

		adapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {
			    ImageView imageView=view.findViewById(R.id.mimg);

				Intent intent=new Intent(MainActivity.this,PostviewerActivity.class);
                intent.putExtra("activity_id", "from_main");
				intent.putExtra("post_id", mo.get((int)position).get("id").toString());
				intent.putExtra("post_link", mo.get((int)position).get("link").toString().replace("\"", ""));

                Pair<View,String> pair = Pair.create((View)imageView, ViewCompat.getTransitionName(imageView));
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,pair);


                startActivity(intent,optionsCompat.toBundle());

			}
		});

	}

	private void initialize(Bundle _savedInstanceState) {

		mRecyclerView = (RecyclerView) findViewById(R.id.list);
		linearLayoutManager=new LinearLayoutManager(MainActivity.this);

		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setLayoutManager(linearLayoutManager);
		mRecyclerView.setNestedScrollingEnabled(false);
		mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState==AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling=true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems=linearLayoutManager.getChildCount();
                totalItems=linearLayoutManager.getItemCount();
                scrollOutItems=linearLayoutManager.findFirstVisibleItemPosition();
                if (isScrolling && (currentItems + scrollOutItems==totalItems)){
                    isScrolling=false;
                    urm="https://businessrev.gr/wp-json/wp/v2/posts?per_page=8&page="+page;
					page=page+1;
                    new BackTask().execute(urm);
                }

            }
        });

    }
	private void initializeLogic() {
		onLoadingSwipeRefresh("https://businessrev.gr/wp-json/wp/v2/posts?per_page=8");

	}

	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}



	@Override
	public void onRefresh() {
		page=2;
		count=0;
		new BackTask().execute("https://businessrev.gr/wp-json/wp/v2/posts?per_page=8");
	}
	 private void onLoadingSwipeRefresh(final String urm){
		swipeRefreshLayout.post(
				new Runnable() {
					@Override
					public void run() {
						count=0;
						new BackTask().execute(urm);
					}
				}
		);
	 }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    private class BackTask extends AsyncTask<String, Integer, String> {
		@Override
		protected void onPreExecute() {
			swipeRefreshLayout.setRefreshing(true);
		}
		protected String doInBackground(String... address) {
			String output = "";
			try {
				URL url = new URL(address[0]);
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
				String line;
				while ((line = in.readLine()) != null) {
					output += line;
				}
				in.close(); } catch (MalformedURLException e) {
				output = e.getMessage();
			} catch (IOException e) {
				output = e.getMessage();
			} catch (Exception e) {
				output = e.toString();
			}
			return output;
		}
		protected void onProgressUpdate(Integer... values) {}
		protected void onPostExecute(String s){
			html = s ;
			if (html.toLowerCase().contains("Unable to resolve host".toLowerCase())) {
				swipeRefreshLayout.setRefreshing(false);
				Toastandmore.showMessage(getApplicationContext(), "Παρακαλώ ελέγξτε την σύνδεσή σας στο διαδίκτυο και δοκιμάστε ξανά");
			}
			else {
			    if (count==0) {
			        count=count+1;
                    mo = new Gson().fromJson(html, new TypeToken<ArrayList<HashMap<String, Object>>>() {
                    }.getType());
                    adapter = new MainAdapter(MainActivity.this, mo);
                    mRecyclerView.setAdapter(adapter);
                    initListener();
                    swipeRefreshLayout.setRefreshing(false);

                }
			    else{

                    mo2 = new Gson().fromJson(html, new TypeToken<ArrayList<HashMap<String, Object>>>() {
                    }.getType());
                    mo.addAll(mo2);
                    swipeRefreshLayout.setRefreshing(false);
                    adapter.notifyDataSetChanged();

                }

			}
		}
	}

	
}
