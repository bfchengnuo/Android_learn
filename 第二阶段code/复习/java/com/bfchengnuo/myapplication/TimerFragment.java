package com.bfchengnuo.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class TimerFragment extends Fragment {
    private Spinner mSpinner;
    private String[] mStrings = new String[]{"2000","3000","4000","5000"};
//    private Handler mHandler = new Handler();
    private Runnable mRunnable;
    private TextView mTextView;
    private ProgressBar mProgressBar;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            mTextView.setText("当前进度：" + msg.obj);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        mStrings = getActivity().getResources().getStringArray(R.array.timerTest);

        View view = inflater.inflate(R.layout.fragment_timer, container, false);
        mSpinner = (Spinner) view.findViewById(R.id.timer_sp);
        mTextView = (TextView) view.findViewById(R.id.tv_timer_show);
        mProgressBar = (ProgressBar) view.findViewById(R.id.pb_no);

        view.findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sendNow();
                showDiog();
            }
        });
        view.findViewById(R.id.btn_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeCallbacks(mRunnable);
            }
        });
        view.findViewById(R.id.btn_pb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downLoad();
            }
        });
        ((SeekBar)view.findViewById(R.id.sb_yes)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mTextView.setText("当前值：" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return view;
    }

    private void downLoad() {
        mProgressBar.setProgress(1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    mProgressBar.incrementProgressBy(1);
                    Message message = new Message();
                    message.obj = mProgressBar.getProgress();
                    mHandler.sendMessage(message);
                }
            }
        }).start();
    }

    private void sendNow() {
        final int flag = mSpinner.getSelectedItemPosition();
        Toast.makeText(getActivity(), Integer.parseInt(mStrings[flag]) + "", Toast.LENGTH_SHORT).show();

        mRunnable = new Runnable() {
            @Override
            public void run() {
                mTextView.setText(System.currentTimeMillis()+"");
                mHandler.postDelayed(this,Integer.parseInt(mStrings[flag]));
            }
        };

        mHandler.postDelayed(mRunnable,Integer.parseInt(mStrings[flag]));
    }

    private void showDiog(){
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity(),R.style.MyAlertDialog).setIcon(R.mipmap.ic_launcher)
                .setTitle("提示")
                .setMessage("你确定要开始么")
                .setNegativeButton("取消",null)
                .setNeutralButton("卖萌", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendNow();
                    }
                })
                .create();

        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.6f;
        window.setAttributes(lp);

        alertDialog.show();
    }

}
