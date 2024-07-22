package com.my.busrevapp;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Categories extends AppCompatActivity{
    List<String> titles;
    List<Integer> images;
    RecyclerView dataList;
    Adapter adapter;
    Context context=this;

    String m_Text="";
    private Intent intent = new Intent();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories);

        dataList = findViewById(R.id.cat_rec);
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
                        final EditText input = new EditText(Categories.this);
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

        titles = new ArrayList<>();
        images = new ArrayList<>();

        titles.add("Επιχ/τητα");
        titles.add("Οικονομία");
        titles.add("Τεχνολογία");
        titles.add("Απόψεις");
        titles.add("Συνεντεύξεις");
        titles.add("Απόψεις Αναγνωστών");
        titles.add("Αφιερώματα");
        titles.add("Success Stories");
        titles.add("Ορολογίες");
        titles.add("Επιστήμη");
        titles.add("Πρόσωπα");
        titles.add("Μάρκετινγκ");
        titles.add("Αθλητικά");

        images.add(R.drawable.entrepreneurship);
        images.add(R.drawable.economy);
        images.add(R.drawable.technology);
        images.add(R.drawable.interview);
        images.add(R.drawable.real_interview);
        images.add(R.drawable.apopseis);
        images.add(R.drawable.tribute);
        images.add(R.drawable.success);
        images.add(R.drawable.terminology);
        images.add(R.drawable.science);
        images.add(R.drawable.celebrities);
        images.add(R.drawable.marketing);
        images.add(R.drawable.athletics);

        adapter = new Adapter(this,titles,images);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(adapter);

    }
}
