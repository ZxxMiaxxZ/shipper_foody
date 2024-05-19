package com.example.shipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.shipper.ShipperPanel.ShipperHomeFragment;
import com.example.shipper.ShipperPanel.ShipperOrderFragment;
import com.example.shipper.ShipperPanel.ShipperPendingOrderFragment;
import com.example.shipper.ShipperPanel.ShipperProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class ShipperPanel_BottomNavigation extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipper_panel_bottom_navigation);
        BottomNavigationView navigationView = findViewById(R.id.shipper_bottom_navigation);
        navigationView.setOnItemSelectedListener(this);

        // Set default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new ShipperHomeFragment()).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.shipperHome:
                fragment = new ShipperHomeFragment();
                break;
            case R.id.pendingOrder:
                fragment = new ShipperPendingOrderFragment();
                break;
            case R.id.Order:
                fragment = new ShipperOrderFragment();
                break;
            case R.id.shipperProfile:
                fragment = new ShipperProfileFragment();
                break;
        }
        return loadShipperFragment(fragment);
    }

    private boolean loadShipperFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
            return true;
        }
        return false;
    }
}
