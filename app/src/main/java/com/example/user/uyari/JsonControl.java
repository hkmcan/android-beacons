package com.example.user.uyari;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JsonControl {

    Context context;
    AlertDialog alertDialog ;
    ArrayList<String> beaconMacs =new ArrayList<>();

    HashMap<Campaign, Boolean> hashMap;
    boolean isDialogOpened = false;

    public JsonControl(Context context) {
        this.context = context;
        hashMap = new HashMap<>();

        alertDialog=new AlertDialog.Builder(context).create();

        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {

                if(hashMap != null){

                    for (Map.Entry<Campaign, Boolean> entrySet : hashMap.entrySet()){
                        if(!entrySet.getValue()){
                            showDialog(entrySet.getKey());
                            break;
                        }
                    }
                }

                // Log.d("-----------------", "Alert Dailog Cancelled");
            }
        });
    }

    public void process(String beaconMac) {

        if(!beaconMacs.contains(beaconMac)){
            beaconMacs.add(beaconMac);

            ArkaPlan arkaPlan = new ArkaPlan(new TaskListener() {
                @Override
                public void onTaskCompleted(final CustomResponse response) {
                    JSONArray campaignsIds = null;

                    try {
                        JSONObject jsonObject = new JSONObject(response.getMessage());
                        campaignsIds = jsonObject.getJSONArray("enter_campaigns");

                        for(int a=0;a<campaignsIds.length();a++){

                            ArkaPlan campanings = new ArkaPlan(new TaskListener() {
                                @Override
                                public void onTaskCompleted(CustomResponse response1) {

                                    try {
                                        JSONObject campaingInfo = new JSONObject(response1.getMessage());

                                        Campaign campaign = new Campaign(
                                                campaingInfo.getString("id"),
                                                campaingInfo.getString("title"),
                                                campaingInfo.getString("message"),
                                                campaingInfo.getBoolean("active"),
                                                campaingInfo.getString("target_url")
                                        );

                                        if(!hashMap.containsKey(campaign)){

                                            if(campaign.isActive()){
                                                hashMap.put(campaign, false);

                                                // Log.i("-------------campaings", campaign.getMessage());

                                                if(!isDialogOpened)
                                                    showDialog(campaign);
                                            }
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            campanings.execute("https://edit.meridianapps.com/api/locations/5177582070792192/campaigns/" + campaignsIds.get(a).toString() + "?format=json");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            arkaPlan.execute("https://edit.meridianapps.com/api/locations/5177582070792192/beacons/" + beaconMac.replace(":", "") + "?format=json");
        Log.i("444444444444444",beaconMac.replace(":", ""));
        //}
        }
    }

    public void showDialog(Campaign campaign){
        // Log.i("-------------shows", campaign.getMessage());

        isDialogOpened = true;
        hashMap.put(campaign, true);

        if (campaign.getMessage() != "null") {
            alertDialog.setMessage(campaign.getMessage());
        } else if(campaign.getUrl()!=null){
            SpannableString link = new SpannableString(campaign.getUrl());
            Linkify.addLinks(link, Linkify.ALL);
            alertDialog.setMessage(link);

        }
        alertDialog.setTitle(campaign.getTitle());
        alertDialog.show();

        ((TextView) alertDialog.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
    }
}


