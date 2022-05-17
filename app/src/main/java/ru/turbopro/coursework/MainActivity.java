package ru.turbopro.coursework;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class MainActivity extends AppCompatActivity {

    //private AdView mAdView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = new Home_Fragment();
                    break;
                case R.id.navigation_dashboard:
                    selectedFragment = new Report();
                    break;
                case R.id.navigation_notifications:
                    selectedFragment = new Statistics_Fragment();
                    break;
            }
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("09F34835DC3F0DED2139AFB85B4877E1")
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mAdView.loadAd(adRequest);*/

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home_Fragment()).commit();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
          getMenuInflater().inflate(R.menu.main_menu, menu);
          return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.action_about:
//                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
//                startActivity(intent);
                Dialog dialog = new Dialog(MainActivity.this);

                // Установите заголовок
                dialog.setTitle("О программе");
                dialog.setContentView(R.layout.activity_about);
                TextView tvAbout = (TextView)dialog.findViewById(R.id.tvAboutText);
                tvAbout.setText(getString(R.string.text_about_app).toString());
                dialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}