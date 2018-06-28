package com.example.android.moviemanager.Activites;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.moviemanager.Adapters.ViewPagerAdapter;
import com.example.android.moviemanager.Fragments.PopularMoviesFragment;
import com.example.android.moviemanager.Fragments.TrendingMoviesFragment;
import com.example.android.moviemanager.Fragments.UpcomingMoviesFragment;
import com.example.android.moviemanager.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        SettingUpNavigationTabs();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){
                    // user SignedIn:
                    Toast.makeText(getApplicationContext(),"You are signed In",Toast.LENGTH_SHORT).show();
                } else {
                    //user SignedOut
                    returnToLogin();
                }

            }
        };
    }

    private void returnToLogin() {
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.signOut:
                makeSignOut();
                break;
        }
        return true;
    }

    private void makeSignOut() {
        FirebaseAuth.getInstance().signOut();
        finish();
        returnToLogin();
    }

    private void SettingUpNavigationTabs() {
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.pager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.AddFragment(new PopularMoviesFragment(),"Popular");
        viewPagerAdapter.AddFragment(new TrendingMoviesFragment(),"Trending");
        viewPagerAdapter.AddFragment(new UpcomingMoviesFragment(),"Upcoming");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        auth.removeAuthStateListener(authStateListener);
    }
}
