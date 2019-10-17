package com.andrewdev.locationdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationListener {


    LocationManager locManager;
    TextView locText ;
    EditText editText;

    double lati;
    double longi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locText = findViewById(R.id.text);
        editText = findViewById(R.id.editText);

    }

    public void GetLocation(View view)
    {
        locManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            String[]perm={Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this,perm,1);
        }
        else{
            Toast.makeText(this, "gone", Toast.LENGTH_SHORT).show();
            locManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, null);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode==1){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                try {
                    locManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null);
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            }
            else{
                Toast.makeText(this, "denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location)
    {
        Toast.makeText(this, "got loc", Toast.LENGTH_SHORT).show();


        lati = location.getLatitude();
        longi=location.getLongitude();

        locText.append(""+lati);
        locText.append("\n"+longi);

        Geocoder geocoder=new Geocoder(this);
        try {
            List<Address> addresses = geocoder.getFromLocation(lati, longi, 1);
            locText.append("\n"+addresses.get(0).getAddressLine(0));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void map(View view)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:"+lati+","+longi));
        startActivity(intent);
    }

    public void GetLocation() {



    }
}
