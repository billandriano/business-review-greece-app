package com.my.busrevapp;

import android.os.*;
import android.text.InputType;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.util.*;

import java.util.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.content.Intent;
import android.net.Uri;
import android.app.Activity;

import android.view.View;
import android.widget.AdapterView;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MoreActivity extends AppCompatActivity {

	private ArrayList<HashMap<String, Object>> map = new ArrayList<>();
	
    private String searchtext="";
	private ImageView imageview1,facebook,instagram,youtube,twitter,linkedin,instagrampod,spotify,applepod;
    private Switch darkmode;
	
	private Intent intent = new Intent();
	DarkSave darkSave;

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.more);
		initialize(_savedInstanceState);
		darkSave=new DarkSave(this);
		if (darkSave.loadNightModeState()==true){
			AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
			darkmode.setChecked(true);
		} else {
			AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
			darkmode.setChecked(false);
		}

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
						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MoreActivity.this, R.style.CustomAlertDialog);
						alertDialogBuilder.setTitle("Αναζήτηση");
						final EditText input = new EditText(MoreActivity.this);
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


		darkmode=(Switch) findViewById(R.id.darkmode);
		instagrampod= findViewById(R.id.instagrampod);
		spotify=findViewById(R.id.spotify);
		applepod=findViewById(R.id.applepod);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		facebook = (ImageView) findViewById(R.id.facebook);
		instagram = (ImageView) findViewById(R.id.instagram);
		youtube = (ImageView) findViewById(R.id.youtube);
		twitter = (ImageView) findViewById(R.id.twitter);
		linkedin = (ImageView) findViewById(R.id.linkedin);
		darkmode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) {
					AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
					darkSave.setNightModeState(true);
				}
				else {
					AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
					darkSave.setNightModeState(false);
				}
			}
		});

		facebook.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("https://www.facebook.com/BRgreece/"));
				startActivity(intent);
			}
		});
		
		instagram.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("https://www.instagram.com/business_review_greece/"));
				startActivity(intent);
			}
		});
		
		youtube.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("https://www.youtube.com/channel/UCCZoNvq4dMpHlbGj1E3yiyQ?view_as=subscriber"));
				startActivity(intent);
			}
		});
		
		twitter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("https://twitter.com/BusinessRevGR"));
				startActivity(intent);
			}
		});
		
		linkedin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("https://www.linkedin.com/company/businessreviewgreece"));
				startActivity(intent);
			}
		});
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("https://businessrev.gr"));
				startActivity(intent);
			}
		});
		spotify.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("https://open.spotify.com/show/5jE5Jx3qMe9whz7YSRy4MD"));
				startActivity(intent);
			}
		});
		applepod.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("https://podcasts.apple.com/us/podcast/business-talks/id1495868580"));
				startActivity(intent);
			}
		});
		instagrampod.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("https://www.instagram.com/businesstalksgr/"));
				startActivity(intent);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}

	
}
