package com.example.user.uyari;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.arubanetworks.meridian.Meridian;
import com.arubanetworks.meridian.editor.Placemark;
import com.arubanetworks.meridian.location.LocationRequest;
import com.arubanetworks.meridian.location.MeridianLocation;
import com.arubanetworks.meridian.location.MeridianOrientation;
import com.arubanetworks.meridian.maps.MapFragment;
import com.arubanetworks.meridian.maps.MapOptions;
import com.arubanetworks.meridian.maps.MapView;
import com.arubanetworks.meridian.maps.directions.DirectionsDestination;



public class MainActivity extends AppCompatActivity {
    TextView tv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv =(TextView)findViewById(R.id.textView);

        Meridian.configure(this);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        else{
            getFragmentManager().beginTransaction().add(android.R.id.content,new LocationFragment()).commit();
            getFragmentManager().beginTransaction().add(android.R.id.content, new DeviceListFragment()).commit();
            selectItem();
        }
    }


    private void selectItem() {
        Fragment fragment;

        MapFragment.Builder builder = new MapFragment.Builder()
                .setMapKey(Application.MAP_KEY);
        MapOptions mapOptions = MapOptions.getDefaultOptions();
        mapOptions.HIDE_OVERVIEW_BUTTON = true;
        builder.setMapOptions(mapOptions);

        final MapFragment mapFragment = builder.build();
        mapFragment.setMapEventListener(new MapView.MapEventListener() {

            @Override
            public void onMapLoadFinish() {
                for (Placemark placemark : mapFragment.getMapView().getPlacemarks()) {
                    if ("Apple".equals(placemark.getName())) {
                        mapFragment.startDirections(DirectionsDestination.forPlacemarkKey(placemark.getKey()));
                    }
                }
            }

            @Override
            public void onMapLoadStart() {

            }

            @Override
            public void onPlacemarksLoadFinish() {

            }

            @Override
            public void onMapRenderFinish() {

            }

            @Override
            public void onMapLoadFail(Throwable tr) {

            }

            @Override
            public void onMapTransformChange(Matrix transform) {

            }

            @Override
            public void onLocationUpdated(MeridianLocation location) {

            }

            @Override
            public void onOrientationUpdated(MeridianOrientation orientation) {

            }

            @Override
            public boolean onLocationButtonClick() {
                final MapView mapView = mapFragment.getMapView();
                MeridianLocation location = mapView.getUserLocation();
                if (location != null) {
                    mapView.updateForLocation(location);

                } else {
                    LocationRequest.requestCurrentLocation(getApplicationContext(), Application.APP_KEY, new LocationRequest.LocationRequestListener() {
                        @Override
                        public void onResult(MeridianLocation location) {
                            mapView.updateForLocation(location);
                        }

                        @Override
                        public void onError(LocationRequest.ErrorType location) {
                        }
                    });
                }
                return true;
            }
        });
        fragment = mapFragment;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }


}
