package com.beshoykamal.locationapp;

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
import android.webkit.WebSettings;
import android.widget.ActionMenuView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationListener {
    TextView locattext;
    double lat,lon,la,lo;
    Long longg;
    Float speed;
    LocationManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locattext = findViewById(R.id.locattext);

        loc(locattext);
    }

    public void loc(View view) {

        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           String[] perm={Manifest.permission.ACCESS_FINE_LOCATION};
           ActivityCompat.requestPermissions(this,perm,1);

            return;
        }
        else
        manager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1)
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                try {
                    manager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, getMainLooper());
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            }
        else
                Toast.makeText(this, "please accept ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {

        lat=location.getLatitude();
        locattext.append("\n lat "+lat);
        lon=location.getLongitude();
        locattext.append("\n long "+lon);
        longg=location.getTime();
        locattext.append("\n time "+longg);
        speed=location.getSpeed();
        locattext.append("\n speed "+speed);

        la=30.086721;
        lo=31.330276;

        Geocoder geocoder=new Geocoder(this);
        try {

            List<Address> addressList =
                    geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            locattext.append("\n"+addressList.get(0).getAddressLine(0));

        } catch (IOException e) {
            Toast.makeText(this, "ERORR CONECTION", Toast.LENGTH_SHORT).show();
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

    public void map(View view) {

        Intent in=new Intent(Intent.ACTION_VIEW,Uri.parse("geo: "+lat+","+lon+"?z=16"));
        startActivity(in);

    }
    public void station(View view) {

        Intent in=new Intent(Intent.ACTION_VIEW,Uri.parse("geo: 30.086721,31.330276?z=13"));
        startActivity(in);

    }

    public void city(View view) {
        Intent in=new Intent(Intent.ACTION_VIEW,Uri.parse("geo: 30.073686,31.346190?z=13"));
        startActivity(in);
    }

    public void peside(View view) {
//        Location location1=new Location("");
//        location1.setLatitude();
//        location1.setLongitude();
    }
}
