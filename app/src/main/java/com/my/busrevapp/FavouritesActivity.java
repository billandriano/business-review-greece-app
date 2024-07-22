package com.my.busrevapp;

import android.os.*;
import android.text.InputType;
import android.view.*;
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

import java.util.ArrayList;
import java.util.HashMap;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;
import android.content.SharedPreferences;
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

public class FavouritesActivity extends AppCompatActivity {

	RecyclerView mRecyclerView;
	private Toolbar _toolbar;
	private String dispfavs = "";
	private String urm = "";
	private String html = "";
	private String searchtext = "";
	private String hours = "";
	private String hours2 = "";
	private String desc = "";
	private String month = "";
	
	private ArrayList<HashMap<String, Object>> mo = new ArrayList<>();
	private MainAdapter adapter;
	private LinearLayout linear1;
	private ListView listview1;
	private LinearLayout linear2;
	private ImageView imageview1;
	private TextView textview1;
	
	private SharedPreferences favourites;
	private SharedPreferences file;
	private Calendar calendar = Calendar.getInstance();
	private Intent intent = new Intent();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.favourites);
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
						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FavouritesActivity.this, R.style.CustomAlertDialog);
						alertDialogBuilder.setTitle("Αναζήτηση");
						final EditText input = new EditText(FavouritesActivity.this);
						input.setInputType(InputType.TYPE_CLASS_TEXT);
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

		linear1 = (LinearLayout) findViewById(R.id.linear1);

		mRecyclerView = (RecyclerView) findViewById(R.id.list);
		LinearLayoutManager linearLayoutManager=new LinearLayoutManager(FavouritesActivity.this);
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setLayoutManager(linearLayoutManager);
		mRecyclerView.setNestedScrollingEnabled(false);

		linear2 = (LinearLayout) findViewById(R.id.linear2);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		textview1 = (TextView) findViewById(R.id.textview1);
		favourites = getSharedPreferences("favourites", Activity.MODE_PRIVATE);
		file = getSharedPreferences("favourites", Activity.MODE_PRIVATE);

	}
	private void initListener(){

		adapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {
				Intent intent=new Intent(FavouritesActivity.this,PostviewerActivity.class);
				intent.putExtra("activity_id", "from_main");
				intent.putExtra("post_id", mo.get((int)position).get("id").toString());
				intent.putExtra("post_link", mo.get((int)position).get("link").toString().replace("\"", ""));
				startActivity(intent);

			}
		});

	}
	private void initializeLogic() {
		textview1.setVisibility(View.GONE);
		imageview1.setVisibility(View.GONE);
		linear2.setVisibility(View.GONE);
		if (!file.getString("favs", "").equals("")) {
			dispfavs = file.getString("favs", "").substring((int)(1), (int)(file.getString("favs", "").length())).replace(",", "&include[]=");
			urm = "https://businessrev.gr/wp-json/wp/v2/posts?include[]=".concat(dispfavs);
			new BackTask().execute(urm);
		}
		else {
			textview1.setVisibility(View.VISIBLE);
			imageview1.setVisibility(View.VISIBLE);
			linear2.setVisibility(View.VISIBLE);
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

	private class BackTask extends AsyncTask<String, Integer, String> {
		@Override
		protected void onPreExecute() {}
		protected String doInBackground(String... address) {
			String output = "";
			try {
				java.net.URL url = new java.net.URL(address[0]);
				java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(url.openStream()));
				String line;
				while ((line = in.readLine()) != null) {
					output += line;
				}
				in.close(); } catch (java.net.MalformedURLException e) {
				output = e.getMessage();
			} catch (java.io.IOException e) {
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
				Toastandmore.showMessage(getApplicationContext(), "Παρακαλώ ελέγξτε την σύνδεσή σας στο διαδίκτυο και δοκιμάστε ξανά");
			}
			else {
				mo = new Gson().fromJson(html, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
				adapter=new MainAdapter(FavouritesActivity.this,mo);
				mRecyclerView.setAdapter(adapter);
				initListener();

			}
		};
	}

	
}
