package com.my.busrevapp;

import android.content.Context;
import android.content.SharedPreferences;

public class DarkSave {
    SharedPreferences dark;
    public DarkSave(Context context){
        dark=context.getSharedPreferences("filename",Context.MODE_PRIVATE);

    }

    public void setNightModeState(Boolean state) {
        SharedPreferences.Editor editor=dark.edit();
        editor.putBoolean("NightMode",state);
        editor.commit();
    }
    public Boolean loadNightModeState(){
        Boolean state=dark.getBoolean("NightMode",false);
        return state;
    }
}
