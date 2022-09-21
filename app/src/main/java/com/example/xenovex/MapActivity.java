package com.example.xenovex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity {

    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    Button btDetails;

    String address,Name,Email,Phone;
    DataBaseHelper dataBaseHelper;
    UserModel userModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        btDetails=findViewById(R.id.btDetails);


        if (getIntent().hasExtra("User")) {

            Name=getIntent().getStringExtra("name");
            Phone=getIntent().getStringExtra("phone");
            Email=getIntent().getStringExtra("email");

        }

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        client = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(MapActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(MapActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, 44);

        }




        btDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(address.isEmpty()||Name.isEmpty()||Phone.isEmpty()||Email.isEmpty()){
                    Toast.makeText(MapActivity.this,address,Toast.LENGTH_LONG).show();
                }else{

                    dataBaseHelper=new DataBaseHelper(MapActivity.this);
                    boolean success = dataBaseHelper.addOne(userModel);

                    if(success==true){
                        Intent intent = new Intent(MapActivity.this, DetailedActivity.class);
                        intent.putExtra("UserWithLocation", "UserDetails");
                        intent.putExtra("name",Name);
                        intent.putExtra("phone",Phone);
                        intent.putExtra("email",Email);
                        intent.putExtra("Address",address);
                        startActivity(intent);

                    }

                }



            }
        });
    }

    private void getCurrentLocation() {

        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location !=null){
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
                            MarkerOptions markerOptions=new MarkerOptions().position(latLng).title("Am here");
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,100));
                            googleMap.addMarker(markerOptions);

                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();

                            //Toast.makeText(MapActivity.this,String.valueOf(latitude),Toast.LENGTH_LONG).show();

                            Geocoder geocoder=new Geocoder(MapActivity.this, Locale.getDefault());

                            List<Address> addresses  = null;
                            try {
                                addresses = geocoder.getFromLocation(latitude,longitude, 1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            address = addresses.get(0).getAddressLine(0);
                            String city = addresses.get(0).getLocality();
                            String state = addresses.get(0).getAdminArea();
                            String zip = addresses.get(0).getPostalCode();
                            String country = addresses.get(0).getCountryName();

                            userModel=new UserModel(-1,Name,Phone,Email,address);
                            //Toast.makeText(MapActivity.this,address,Toast.LENGTH_LONG).show();

                        }
                    });
                }

            }
        });



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==44){
           if(grantResults.length>0&&grantResults[0]==getPackageManager().PERMISSION_GRANTED){
               getCurrentLocation();
           }
        }
    }
}