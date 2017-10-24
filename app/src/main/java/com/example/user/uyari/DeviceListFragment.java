package com.example.user.uyari;

import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.ArrayList;
import java.util.Timer;

public  class DeviceListFragment extends Fragment implements BluetoothAdapter.LeScanCallback {
    public BluetoothManager bluetoothManager;
    private BluetoothAdapter adapter;
    ArrayList<String> mac = new ArrayList<String>();
    boolean ilk=false;
    int i=0;
    JsonControl jsb = null;
    Timer timer ;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        jsb=new JsonControl(getActivity());
        timer=new Timer();

        bluetoothManager = (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
        adapter = bluetoothManager.getAdapter();
        if (!adapter.isEnabled()) {
            adapter.enable();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.startLeScan(DeviceListFragment.this);
            }
        },10000);
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopLeScan(DeviceListFragment.this);
    }


    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
        if(device.getName()!=null) {
            jsb.process(device.getAddress().toString());
            Log.i("--------------", device.getAddress());
        }
    }
}
