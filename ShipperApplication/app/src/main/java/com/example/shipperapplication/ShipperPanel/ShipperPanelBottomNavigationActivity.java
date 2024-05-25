<<<<<<<< HEAD:ShipperApplication/app/src/main/java/com/example/shipperapplication/ShipperPanel/ShipperPanelBottomNavigationActivity.java
package com.example.shipperapplication.ShipperPanel;
========
package com.example.shipperapplication.Activity;
>>>>>>>> 9a86aa0dc5c2704c6fc50fc9f3e900684602b9df:ShipperApplication/app/src/main/java/com/example/shipperapplication/Activity/ShipperPanelBottomNavigationActivity.java
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.shipperapplication.R;
<<<<<<<< HEAD:ShipperApplication/app/src/main/java/com/example/shipperapplication/ShipperPanel/ShipperPanelBottomNavigationActivity.java
========
import com.example.shipperapplication.ShipperPanel.ChatFragment;
>>>>>>>> 9a86aa0dc5c2704c6fc50fc9f3e900684602b9df:ShipperApplication/app/src/main/java/com/example/shipperapplication/Activity/ShipperPanelBottomNavigationActivity.java
import com.example.shipperapplication.ShipperPanel.ShipperHomeFragment;
import com.example.shipperapplication.ShipperPanel.ShipperPendingOrderFragment;
import com.example.shipperapplication.ShipperPanel.ShipperProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class ShipperPanelBottomNavigationActivity extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener {
    private String delivery_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipper_panel_bottom_navigation);
        BottomNavigationView navigationView = findViewById(R.id.shipper_bottom_navigation);
        navigationView.setOnItemSelectedListener(this);
        android.content.Intent intent = getIntent();
        if (intent != null) {
            delivery_id = intent.getStringExtra("delivery_id");
        }
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
            case R.id.chat:
                Bundle args = new Bundle();
                args.putString("delivery_id", delivery_id);
                fragment.setArguments(args);
                fragment = new ChatFragment();
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
