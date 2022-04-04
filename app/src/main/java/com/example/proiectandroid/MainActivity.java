package com.example.proiectandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.accounts.Account;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.proiectandroid.Events.EventChange;
import com.example.proiectandroid.Fragments.AllLocationsFragment;
import com.example.proiectandroid.Fragments.RecordExperienceFragment;
import com.example.proiectandroid.Fragments.TravelPlanningFragment;
import com.example.proiectandroid.Fragments.VideoTutorialFragment;
import com.example.proiectandroid.Services.LocationsService;
import com.example.proiectandroid.Services.TravelService;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.navigation.NavigationView;

import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static String CHANNEL_ID = "travelAppChannel";
    private static Context applicationContext;

    private static final String TAG = "MainActivity";
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        applicationContext = getApplicationContext();

        // Create notification channel
        createNotificationChannel();

        // Rebind toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Sidepanel navigation
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Update user name in nav bar
        TextView usernameView=navigationView.getHeaderView(0).findViewById(R.id.nav_header_username);
        usernameView.setText(getIntent().getStringExtra("ACCOUNT_NAME"));


        // Search bar
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_all_locations:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AllLocationsFragment()).commit();
                break;
            case R.id.nav_add_travel_point:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TravelPlanningFragment()).commit();
                break;
            case R.id.nav_map:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MapFragment()).commit();
                break;
            case R.id.nav_video:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new VideoTutorialFragment()).commit();
                break;
            case R.id.nav_camera:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RecordExperienceFragment()).commit();
                break;
            case R.id.nav_share:
                Intent shareIntent = new Intent();
                String data = "Travel plan: ";

                TravelService travelService = TravelService.getInstance();
                data += travelService.getDestinations().stream().collect(Collectors.joining(", "));

                shareIntent.setType("text/plain");
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, data);
                startActivity(shareIntent);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static Context getInstanceApplicationContext() {
        return applicationContext;
    }

}