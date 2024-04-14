package com.cesar.geoapoyos3;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ImageButton menuButton;

    UserFragment userFragment;
    MapFragment mapFragment;
    SettingsFragment settingsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userFragment = new UserFragment();
        mapFragment = new MapFragment();
        settingsFragment = new SettingsFragment();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        menuButton = findViewById(R.id.menu_button);

        menuButton.setOnClickListener((v -> {
            startActivity(new Intent(MainActivity.this, MenuActivity.class));
        }));

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.user){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,userFragment).commit();
                }
                if(item.getItemId()==R.id.map){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,mapFragment).commit();
                }
                if(item.getItemId()==R.id.settings){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,settingsFragment).commit();
                }
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.user);
    }
}