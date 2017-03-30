package com.bfchengnuo.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class SocketFragment extends Fragment {
    private String URLSTR = "https://www.baidu.com";
    private TextView mTextView;
    private TextView mGps;
    private LocationManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_socket, container, false);
        Button button = (Button) view.findViewById(R.id.btn_pull);
        mTextView = (TextView) view.findViewById(R.id.tv_show);
        mGps = (TextView) view.findViewById(R.id.tv_gps);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new Thread(new MyThread()).start();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String str = doGet();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTextView.setText(str);
                            }
                        });
                    }
                }).start();
            }
        });

        manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {
            updateTV();
        }

        return view;
    }

    class MyThread implements Runnable {

        @Override
        public void run() {
            try {
                URL url = new URL(URLSTR);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setReadTimeout(2000);

                BufferedReader bufr = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                final StringBuilder sb = new StringBuilder();
                String str;
                while ((str = bufr.readLine()) != null) {
                    sb.append(str);
                }

                conn.disconnect();
                bufr.close();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextView.setText(sb.toString());
                    }
                });

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String doGet() {
        try {
//            URLEncoder.encode()
            URL url = new URL(URLSTR);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(2000);

            InputStream is = conn.getInputStream();
            byte[] data = new byte[2 * 1024];
            ByteArrayOutputStream buff = new ByteArrayOutputStream();

            int flag;
            while ((flag = is.read(data)) != -1) {
                buff.write(data, 0, flag);
            }

            is.close();
            conn.disconnect();
            return new String(buff.toByteArray(), "UTF-8");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String doPost() {
        try {
            URL url = new URL(URLSTR);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(2000);

            OutputStream ops = conn.getOutputStream();
            String str = "key1=val1&key2=val2";
            ops.write(str.getBytes());
            ops.flush();

            InputStream ips = conn.getInputStream();
            ByteArrayOutputStream buff = new ByteArrayOutputStream();
            byte[] data = new byte[2 * 1024];
            int len;
            while ((len = ips.read(data)) != -1) {
                buff.write(data, 0, len);
            }

            ops.close();
            ips.close();
            conn.disconnect();

            return new String(buff.toByteArray(), "UTF-8");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1 && grantResults.length != 0) {
            updateTV();
        } else {
            Toast.makeText(getActivity(), "获取权限失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTV() {
        try {
            Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                mGps.setText("经度：" + location.getLongitude() + "\n纬度:" + location.getLatitude());
            }
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    mGps.setText("fk-经度：" + location.getLongitude() + "\n纬度:" + location.getLatitude());
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
            });
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}
