package com.bfchengnuo.myapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.VideoView;

public class PlayFragment extends Fragment implements View.OnClickListener, SensorEventListener {
    private MediaPlayer mMediaMusic;
    private VideoView mVideoView;
    private SensorManager mSensorManager;
    private Sensor mSensor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play, container, false);

        view.findViewById(R.id.btn_start).setOnClickListener(this);
        view.findViewById(R.id.btn_pause).setOnClickListener(this);
        view.findViewById(R.id.btn_stop).setOnClickListener(this);
        view.findViewById(R.id.btn_startVideo).setOnClickListener(this);
        mVideoView = (VideoView) view.findViewById(R.id.vv_show);

        initData();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // 获取传感器的管理者
        mSensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager != null) {
            // 获取加速度传感器
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (mSensor != null) {
                mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
            }
        }
    }

    @Override
    public void onPause() {
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
        super.onPause();
    }

    private void initData() {
        mMediaMusic = MediaPlayer.create(getContext(), R.raw.yousa);
        mVideoView.setVideoURI(Uri.parse("android.resource://com.bfchengnuo.myapplication/" + R.raw.lesson));
//        mVideoView.setMediaController(new MediaController(getContext()));
//        try {
//            mMediaMusic.prepare();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                mMediaMusic.start();
                break;
            case R.id.btn_pause:
                mMediaMusic.pause();
                break;
            case R.id.btn_stop:
                mMediaMusic.stop();
                break;
            case R.id.btn_startVideo:
                mVideoView.start();
                break;

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMediaMusic.isPlaying()) {
            mMediaMusic.stop();
            mMediaMusic.release();
        }
        if (mVideoView.isPlaying()) {
            mVideoView.stopPlayback();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();

        if (type == Sensor.TYPE_ACCELEROMETER) {
            //获取三个方向值
            float[] values = event.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];

            if ((Math.abs(x) > 17 || Math.abs(y) > 17 || Math
                    .abs(z) > 17)) {
                Toast.makeText(getActivity(), "晃动手机", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
