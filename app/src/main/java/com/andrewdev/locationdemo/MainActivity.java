package com.andrewdev.locationdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Location(View view)
    {
        LocationManager locManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        locManager.requestSingleUpdate(LocationManager.GPS_PROVIDER,this,null);
    }
}
