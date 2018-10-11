package com.example.rxandroidmvp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SharedPreferenceSingleton
{
    public static String MY_PREF = "MyPref";
    private static SharedPreferenceSingleton mInstance;
   // private Context mContext;
    private SharedPreferences myPreference;

    public static SharedPreferenceSingleton getInstance()
    {
        if(mInstance == null)
        {
            mInstance = new SharedPreferenceSingleton();
        }
        return mInstance;
    }

    private void SharedPrefSingleton()
    {
    }

    public void init(Context context)
    {
        //mContext = context;
        myPreference = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void writeStringPreference(String key, String value)
    {
        SharedPreferences.Editor mEditor = myPreference.edit();
        mEditor.putString(key, value);
        mEditor.apply();
    }


    public void writeBoolPreference(String key, boolean value)
    {
        SharedPreferences.Editor mEditor = myPreference.edit();
        mEditor.putBoolean(key, value);
        mEditor.apply();
    }

    public void clearPreference()
    {
        SharedPreferences.Editor mEditor = myPreference.edit();
        mEditor.clear();
        mEditor.apply();
    }

    public void writeIntPreference(String key, int value)
    {
        SharedPreferences.Editor mEditor = myPreference.edit();
        mEditor.putInt(key, value);
        mEditor.apply();
    }

    public void writeFloatPreference(String key, Float value)
    {
        SharedPreferences.Editor mEditor = myPreference.edit();
        mEditor.putFloat(key, value);
        mEditor.apply();
    }


    public float getFloatPreference(String key)
    {
        return myPreference.getFloat(key, 0);
    }

    public int getIntPreference(String key)
    {
        return myPreference.getInt(key, -1);
    }

    public int getIntPreferenceNull(String key)
    {
        return myPreference.getInt(key, -1000);
    }

    public String getStringPreferenceNull(String key)
    {
        return myPreference.getString(key, null);
    }

    public String getStringPreference(String key)
    {
        return myPreference.getString(key, "");
    }

    public boolean getBoolPreference(String key)
    {
        return myPreference.getBoolean(key, false);
    }

    public void removePreference(String key)
    {
        SharedPreferences.Editor mEditor = myPreference.edit();
        mEditor.remove(key);
        mEditor.apply();
    }

}