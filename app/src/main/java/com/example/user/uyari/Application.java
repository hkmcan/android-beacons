package com.example.user.uyari;

import com.arubanetworks.meridian.Meridian;
import com.arubanetworks.meridian.editor.EditorKey;

/**
 * Created by user on 7.08.2017.
 */

public class Application extends android.app.Application {

    public static final EditorKey APP_KEY = EditorKey.forApp("5177582070792192"); // replace this with your app id
    public static final EditorKey MAP_KEY = EditorKey.forMap("5676073085829120", APP_KEY); // replace this with your map id
    public static final String PLACEMARK_UID = ""; // replace this with a unique id for one of your placemarks.
    public static final String TAG_MAC = ""; // mac address of one of your tags here
    public static final String EDITOR_TOKEN = ""; // your editor token here

    @Override
    public void onCreate() {
        // Configure Meridian
        Meridian.configure(this);
        Meridian.getShared().initGoalsForLocation(APP_KEY.toString());
        Meridian.getShared().setEditorToken(EDITOR_TOKEN);

        super.onCreate();
    }
}