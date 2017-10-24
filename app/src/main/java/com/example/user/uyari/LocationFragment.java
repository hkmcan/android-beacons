package com.example.user.uyari;

import android.app.*;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.arubanetworks.meridian.location.LocationRequest;
import com.arubanetworks.meridian.location.MeridianLocation;
import com.arubanetworks.meridian.location.MeridianLocationManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LocationFragment extends Fragment{
    ArrayList<PlacemarkCustom> placemarkArrayList ;
    JSONObject placemarkInfo;
    JSONObject jsonObj;

    private MeridianLocationManager locationHelper;
    private LocationRequest locationRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        placemarkInfos();

        locationHelper = new MeridianLocationManager(getActivity(), Application.APP_KEY, listener);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (locationHelper != null) locationHelper.startListeningForLocation();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (locationHelper != null) locationHelper.stopListeningForLocation();
    }


    public void placemarkInfos(){
        placemarkArrayList = new ArrayList<>();
        jsonObj = new JSONObject();

        ArkaPlan placemarks = new ArkaPlan(new TaskListener() {
            @Override
            public void onTaskCompleted(CustomResponse response) {
                try {
                    placemarkInfo = new JSONObject(response.getMessage());
                    JSONArray result = placemarkInfo.getJSONArray("results");
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject c = result.getJSONObject(i);
                        PlacemarkCustom placemarkCustom = new PlacemarkCustom(
                                c.getString("name"),
                                c.getInt("width"),
                                c.getInt("height"),
                                c.getDouble("x"),
                                c.getDouble("y")
                        );
                        if (!placemarkArrayList.equals(placemarkCustom)) {
                            placemarkArrayList.add(placemarkCustom);
                            Log.i("/-/-/-/-/-/-/-/-", " " + placemarkCustom.getName()+"\t "+placemarkCustom.getHeight()+"\t "+placemarkCustom.getWidth()+"\t "+placemarkCustom.getX()+"\t "+placemarkCustom.getY());
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        placemarks.execute("https://edit.meridianapps.com/api/locations/5177582070792192/placemarks?format=json");
    }

    public void Mylocation(double locationWidth,double locationHeight){

        for(PlacemarkCustom placemarkCustom : placemarkArrayList){
            Log.i("Suan Buradasın"," "+placemarkCustom.getName());
            double konumAralıkWidtha=(placemarkCustom.getWidth()/2)+ placemarkCustom.getX();
            double konumAralıkWidthb=(placemarkCustom.getWidth()/2)- placemarkCustom.getX();
            double konumAralıkHighta=(placemarkCustom.getHeight()/2)+ placemarkCustom.getY();
            double konumAralıkHightb=(placemarkCustom.getHeight()/2)- placemarkCustom.getY();

            if(konumAralıkWidtha>locationWidth && locationWidth>konumAralıkWidthb && konumAralıkHighta>locationHeight && locationHeight>konumAralıkHightb){
                Log.i("Suan Buradasın/*/*"," "+placemarkCustom.getName()+" "+placemarkCustom.getHeight()+" "+placemarkCustom.getWidth());
                Log.d("Suan Buradasın kon",locationWidth+ " " +locationHeight);
                ((MainActivity) getActivity()).tv.setText(placemarkCustom.getName());
                break;
            }
        }
    }

    private final MeridianLocationManager.LocationUpdateListener listener = new MeridianLocationManager.LocationUpdateListener() {
        @Override
        public void onLocationUpdate(MeridianLocation location) {
            if(location != null)
                Mylocation(location.getPoint().x, location.getPoint().y);
        }

        @Override
        public void onLocationError(Throwable tr) {
            tr.printStackTrace();
        }

        @Override
        public void onEnableBluetoothRequest() {
            toastMessage("Requested Bluetooth");
        }

        @Override
        public void onEnableWiFiRequest() {
            toastMessage("Requested Wifi");
        }

        @Override
        public void onEnableGPSRequest() {
            toastMessage("Requested GPS");
        }
    };

    private void toastMessage(String message) {
        Context c = getActivity();
        if (c == null) return;
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
    }
}
