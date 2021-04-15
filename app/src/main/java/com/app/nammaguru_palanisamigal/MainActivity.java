package com.app.nammaguru_palanisamigal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import com.app.nammaguru_palanisamigal.fragment.Events_Fragment;
import com.app.nammaguru_palanisamigal.fragment.Gallery_Fragment;
import com.app.nammaguru_palanisamigal.fragment.Home_Fragment;
import com.app.nammaguru_palanisamigal.fragment.More_Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private String str_language;
    BottomNavigationView bottomNavigation;
    Fragment fragment = null;
    private FirebaseAnalytics mFirebaseAnalytics;
    FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        str_language = getDefaults("Language", getApplicationContext());
        setAppLocale(str_language);
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
// Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");

        myRef.setValue("Hello ranjith, ");

        Bundle bundle = new Bundle();

        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "hi");

        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


// Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
              //  Toast.makeText(MainActivity.this, ""+value, Toast.LENGTH_SHORT).show();
                Log.d("sdjhfgh", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("mjsfbjjs", "Failed to read value.", error.toException());
            }
        });
        initialView();

    }


    public void initialView() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
        initialFragment();
    }

    private void setAppLocale(String localeCode) {
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(new Locale(localeCode.toLowerCase()));
        } else {
            config.locale = new Locale(localeCode.toLowerCase());
        }
        resources.updateConfiguration(config, dm);
    }


    public static String getDefaults(String key, Context context) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String value = preferences.getString(key, "");

        return value;
    }


    private boolean loadFragment(Fragment fragment) {

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case R.id.home:


                initialFragment();

                break;

            case R.id.gallery:

                GalleryFragment();

                break;

            case R.id.events:


                eventsFragment();

                break;

//            case R.id.more:
//
//
//                moreFragment();
//
//                break;
        }

        return true;
    }

    public void initialFragment() {

        fragment = new Home_Fragment();
        loadFragment(fragment);
    }

    public void GalleryFragment() {

        fragment = new Gallery_Fragment();
        loadFragment(fragment);
    }

    public void eventsFragment() {

        fragment = new Events_Fragment();
        loadFragment(fragment);
    }

    public void moreFragment() {

        fragment = new More_Fragment();
        loadFragment(fragment);
    }

}
