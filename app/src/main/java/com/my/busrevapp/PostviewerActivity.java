package com.my.busrevapp;

import android.app.DownloadManager;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.webkit.URLUtil;
import android.widget.*;
import android.graphics.*;
import android.util.*;

import java.util.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ImageView;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.content.Intent;
import android.net.Uri;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import android.webkit.WebViewClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.bumptech.glide.Glide;

public class PostviewerActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener{
	
	private Toolbar _toolbar;
	private String postid = "";
	private String urm = "";
	private String html = "";
	private String contentstr = "";
	private String day = "";
	private String mDate = "";
	private String year = "";
	private String Month = "";
	private String author = "";
	DarkSave darkSave;
	private String post_link = "";

	
	private ArrayList<HashMap<String, Object>> mo = new ArrayList<>();

	private TextView appbar_title , title, date , dispauthor ,categories,favou;
	private ImageView imageview,favimg ;
	private boolean isHideToolbarView = false;
	private FrameLayout date_behavior;
	private LinearLayout titleAppbar;
	private AppBarLayout appBarLayout;
	private WebView webview1;
	
	private Intent link = new Intent();
	private SharedPreferences favourites;
	private SharedPreferences file;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.postviewer);
		darkSave=new DarkSave(this);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	@Override
	public boolean onSupportNavigateUp(){
		onBackPressed();
		return true;

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		supportFinishAfterTransition();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.my_menu_share, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
	    int id = item.getItemId();
	    if (id==R.id.share) {
		Intent i = new Intent(android.content.Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(android.content.Intent.EXTRA_SUBJECT, post_link);
		String body= post_link + "\n" + "Έγινε κοινοποίηση από το Business Review Greece App";
		i.putExtra(android.content.Intent.EXTRA_TEXT, body);
		startActivity(Intent.createChooser(i,"Κοινοποίηση με"));
		return super.onOptionsItemSelected(item);
	    }
	    else if (id==R.id.open_web){
            Intent i = new Intent(android.content.Intent.ACTION_VIEW);
            i.setData(Uri.parse(post_link));
            startActivity(i);
            return true;
	    }
	    return super.onOptionsItemSelected(item);
    }

	private void initialize(Bundle _savedInstanceState) {


		
		_toolbar = (Toolbar) findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setTitle("");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
		collapsingToolbarLayout.setTitle("");
		appBarLayout = findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(this);
		date_behavior=findViewById(R.id.date_behavior);
		date=findViewById(R.id.date);
		favou=findViewById(R.id.fav);
		favimg=findViewById(R.id.favimg);
        title =findViewById(R.id.title);
        titleAppbar = findViewById(R.id.title_appbar);
		imageview=findViewById(R.id.backdrop);
        appbar_title=findViewById(R.id.title_on_appbar);
		categories=findViewById(R.id.categories);

		webview1 = (WebView) findViewById(R.id.webView);
		webview1.getSettings().setJavaScriptEnabled(true);
		webview1.getSettings().setSupportZoom(true);
		webview1.setWebViewClient(new WebViewClient());

		registerForContextMenu(webview1);
		webview1.setBackgroundColor(Color.parseColor("#FAFAFA"));



		dispauthor = (TextView) findViewById(R.id.author);
		favourites = getSharedPreferences("favourites", Activity.MODE_PRIVATE);
		file = getSharedPreferences("favourites", Activity.MODE_PRIVATE);
        webview1.setWebViewClient(new WebViewClient()
        {
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                if (url != null && url.startsWith("https://businessrev.gr/")){
                    Intent intent=new Intent(getIntent());
                    intent.putExtra("activity_id", "from_post");
                    intent.putExtra("post_info", url.substring(34));
                    intent.putExtra("post_link",url);
                    startActivity(intent);
                    return true;
                }
                else if (url != null && url.startsWith("http://businessrev.gr/")){
                    Intent intent=new Intent(getIntent());
                    intent.putExtra("activity_id", "from_post");
                    intent.putExtra("post_info", url.substring(33));
                    intent.putExtra("post_link",url);
                    startActivity(intent);
                    return true;
                }
                else
                {
                    view.getContext().startActivity(
                            new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;
                }
            }
        });


		dispauthor.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				link.setAction(Intent.ACTION_VIEW);
				link.setClass(getApplicationContext(), AllActivity.class);
				link.putExtra("key", "author");
				link.putExtra("auth_id", mo.get((int)0).get("author").toString().replace(".0", ""));
				startActivity(link);
			}
		});

		
		favou.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (file.getString("favs", "").contains(postid)) {
					file.edit().putString("favs", file.getString("favs", "").replace(",".concat(postid), "")).commit();
					favimg.setImageResource(R.drawable.withwhite_1);
				}
				else {
					file.edit().putString("favs", file.getString("favs", "").concat(",".concat(postid))).commit();
					favimg.setImageResource(R.drawable.withwhite_2);
					Toastandmore.showMessage(getApplicationContext(), "Το άρθρο προσθέθηκε επιτυχώς στα αγαπημένα.");
				}
			}
		});
	}

	private void initializeLogic() {

		post_link = getIntent().getStringExtra("post_link");
		if (getIntent().getStringExtra("activity_id").equals("from_main")) {

            postid = getIntent().getStringExtra("post_id").replace(".0", "");
            if (favourites.getString("favs", "").contains(postid)) {
                favimg.setImageResource(R.drawable.withwhite_2);
            } else {
                favimg.setImageResource(R.drawable.withwhite_1);
            }
        }
		if (getIntent().getStringExtra("activity_id").equals("from_main")) {
            urm = "https://businessrev.gr/wp-json/wp/v2/posts/?filter[p]=".concat(postid);
        }
		else if (getIntent().getStringExtra("activity_id").equals("from_post")){
		    urm="https://businessrev.gr/wp-json/wp/v2/posts?slug=".concat(getIntent().getStringExtra("post_info"));
        }
		new BackTask().execute(urm);
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
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		final WebView.HitTestResult result = webview1.getHitTestResult();

		if (result.getType() == WebView.HitTestResult.IMAGE_TYPE ||
				result.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {

				String DownloadImageURL = result.getExtra();
				Intent intent=new Intent(PostviewerActivity.this,ImageViewer.class);
				intent.putExtra("imageurl", DownloadImageURL);
				startActivity(intent);
				overridePendingTransition(0,0);
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
        if (getIntent().getStringExtra("activity_id").equals("from_main")) {

            postid = getIntent().getStringExtra("post_id").replace(".0", "");
        }
        if (getIntent().getStringExtra("activity_id").equals("from_main")) {
            urm = "https://businessrev.gr/wp-json/wp/v2/posts/?filter[p]=".concat(postid);
        }
        else if (getIntent().getStringExtra("activity_id").equals("from_post")){
            urm="https://businessrev.gr/wp-json/wp/v2/posts?slug=".concat(getIntent().getStringExtra("post_info"));
        }
        new BackTask().execute(urm);
	}

	@Override
	public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
		int maxScroll=appBarLayout.getTotalScrollRange();
		float percentage = (float) Math.abs(verticalOffset)/(float) maxScroll;
		if (percentage==0){
			date_behavior.setVisibility(View.VISIBLE);
            titleAppbar.setVisibility(View.GONE);
			isHideToolbarView = !isHideToolbarView;
		}
		else if (percentage>0.8 && Math.abs(verticalOffset) != appBarLayout.getTotalScrollRange()){
            date_behavior.setVisibility(View.VISIBLE);
            titleAppbar.setVisibility(View.VISIBLE);
			isHideToolbarView = !isHideToolbarView;
		}
        else if (percentage<0.8 && Math.abs(verticalOffset) != appBarLayout.getTotalScrollRange()){
            titleAppbar.setVisibility(View.GONE);
            isHideToolbarView = !isHideToolbarView;
        }
		else if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()){
            date_behavior.setVisibility(View.GONE);
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
				Glide.with(getApplicationContext()).load(Uri.parse(mo.get((int)0).get("jetpack_featured_media_url").toString())).into(imageview);
				title.setText(mo.get((int)0).get("title").toString().replace("&#8221", "\"").replace("&#8220;", "\"").replace("&#8217;", "'").replace("&#8211;", "-").replace("{", "").replace("}", "").replace("rendered=", ""));
				appbar_title.setText(mo.get((int)0).get("title").toString().replace("&#8221", "\"").replace("&#8220;", "\"").replace("&#8217;", "'").replace("&#8211;", "-").replace("{", "").replace("}", "").replace("rendered=", ""));
				mDate = mo.get((int)0).get("date").toString().replace("T", "").substring((int)(0), (int)(10));
				if (mDate.substring((int)(5), (int)(7)).equals("01")) {
					Month = " Ιανουαρίου ";
				}
				if (mDate.substring((int)(5), (int)(7)).equals("02")) {
					Month = " Φεβρουαρίου ";
				}
				if (mDate.substring((int)(5), (int)(7)).equals("03")) {
					Month = " Μαρτίου ";
				}
				if (mDate.substring((int)(5), (int)(7)).equals("04")) {
					Month = " Απριλίου ";
				}
				if (mDate.substring((int)(5), (int)(7)).equals("05")) {
					Month = " Μαΐου ";
				}
				if (mDate.substring((int)(5), (int)(7)).equals("06")) {
					Month = " Ιουνίου ";
				}
				if (mDate.substring((int)(5), (int)(7)).equals("07")) {
					Month = " Ιουλίου ";
				}
				if (mDate.substring((int)(5), (int)(7)).equals("08")) {
					Month = " Αυγούστου ";
				}
				if (mDate.substring((int)(5), (int)(7)).equals("09")) {
					Month = " Σεπτεμβρίου ";
				}
				if (mDate.substring((int)(5), (int)(7)).equals("10")) {
					Month = " Οκτωβρίου ";
				}
				if (mDate.substring((int)(5), (int)(7)).equals("11")) {
					Month = " Νοεμβρίου ";
				}
				if (mDate.substring((int)(5), (int)(7)).equals("12")) {
					Month = " Δεκεμβρίου ";
				}
				day = mDate.substring((int)(8), (int)(10));
				year = mDate.substring((int)(0), (int)(4));
				date.setText(day.concat(Month.concat(year)));
				contentstr = mo.get((int)0).get("content").toString().replace("&#8216;", "'").replace("width='800' height='450'", "iframe width=\"100%\" height=\"200\"").replace("?version=3&#038;rel=1&#038;fs=1&#038;autohide=2&#038;showsearch=0&#038;showinfo=1&#038;iv_load_policy=1&#038;wmode=transparent' allowfullscreen='true' style='border:0;'", "\" frameborder=\"0\"").replace("&#8217;", "'").replace("&#8230;", "...").replace("&#8220;", "\"").replace("&#8221;", "\"").replace("&#8211;", "-").replace("{rendered=", "").replace(", protected=false}", "").replace("#", "$");
                if (mo.get((int)0).get("author").toString().equals("11.0")) {
                    author = "Elisavet Birmpou";
                }
				if (mo.get((int)0).get("author").toString().equals("5.0")) {
					author = "Βασίλης Ανδριανόπουλος";
				}
				if (mo.get((int)0).get("author").toString().equals("7.0")) {
					author = "Business Review";
				}
				if (mo.get((int)0).get("author").toString().equals("2.0")) {
					author = "Δημήτρης Βλάμης";
				}
				if (mo.get((int)0).get("author").toString().equals("4.0")) {
					author = "Χάρης Αθανασίου";
				}
				if (mo.get((int)0).get("author").toString().equals("6.0")) {
					author = "Αλέξανδρος Απέργης";
				}
				if (mo.get((int)0).get("author").toString().equals("9.0")) {
					author = "BR Guest";
				}
				if (mo.get((int)0).get("author").toString().equals("10.0")) {
					author = "Βασίλης Γιάννης";
				}
				categories.setText(mo.get(0).get("categories").toString().replace("[","").replace("]","").replace("0,","0 ,").replace("314.0","Απόψεις Αναγνωστών").replace("48.0","Success Stories").replace("11.0","Ορολογίες").replace("10.0","Επιστήμη").replace("12.0","Πρόσωπα").replace("13.0","Μάρκετινγκ").replace("50.0","Αθλητικά").replace("49.0","Αφιερώματα").replace("47.0","Επικαιρότητα").replace("4.0","Επιχειρηματικότητα").replace("5.0","Οικονομία").replace("6.0","Τεχνολογία").replace("7.0","Απόψεις").replace("8.0","Συνεντεύξεις").replace("9.0","Ενδιαφέροντα"));
				dispauthor.setText(author);
				if (darkSave.loadNightModeState()==true) {
					webview1.loadDataWithBaseURL(null, "<style>img{display: inline;height: auto;max-width: 100%;}</style>" +"<html><head>"
							+ "<style type=\"text/css\">body{color: #fafafa; background-color: #272A33;} a{color:#0062ff; text-decoration:none; font-weight:bold;}"
							+ "</style></head>"
							+ "<body>" + contentstr, "text/html", "UTF-8", null);
				} else if (darkSave.loadNightModeState()==false) {
					webview1.loadDataWithBaseURL(null, "<style>img{display: inline;height: auto;max-width: 100%;}</style>" +"<html><head>"
							+ "<style type=\"text/css\">body{color: #4e4e4e; background-color: #f9f9fa;}"
							+ "</style></head>"
							+ "<body>" + contentstr, "text/html", "UTF-8", null);
				}
				WebSettings webSettings=webview1.getSettings();
				webSettings.setJavaScriptEnabled(true);
				webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
				if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN)
				{ webSettings.setAllowFileAccessFromFileURLs(true);
				webSettings.setAllowUniversalAccessFromFileURLs(true); }

                if (getIntent().getStringExtra("activity_id").equals("from_post")) {

                    postid = mo.get(0).get("id").toString().replace(".0", "");
                    if (favourites.getString("favs", "").contains(postid)) {
                        favimg.setImageResource(R.drawable.withwhite_2);
                    } else {
                        favimg.setImageResource(R.drawable.withwhite_1);
                    }
                }
			}
		};
		
	}

}
