package com.bfchengnuo.datatimedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Calendar cal;
    private int year;
    private int month;
    private int day ;
    private int hour;
    private int minute;

    private DatePicker datePicker;
    private TimePicker timePicker;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);


        textView = (TextView) findViewById(R.id.text);

        //获取时间对象，并初始化
        cal = Calendar.getInstance();
        //从对象获取数值
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH)+1;
        day = cal.get(Calendar.DAY_OF_MONTH);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
        //设置标题栏
        setTitle(year+"-"+month+"-"+day+"-"+hour+":"+minute);
        textView.setText(year+"-"+month+"-"+day+"-"+hour+":"+minute);

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        //datepicker初始化
        datePicker.init(year, month-1, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                setTitle(year+"-"+monthOfYear+"-"+dayOfMonth);
//                setTitle(year+"-"+monthOfYear+"-"+(dayOfMonth + 1));
                textView.setText(year+"-"+monthOfYear+"-"+(dayOfMonth + 1));
              //  Log.d("dddd",year+"-"+monthOfYear+"-"+(dayOfMonth + 1));
                MainActivity.this.year = year;
                MainActivity.this.month = monthOfYear;
                MainActivity.this.day = dayOfMonth;

            }
        });

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        //timepicker不需要初始化
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//                textView.setText(textView.getText()+"--"+hourOfDay+":"+minute);
                setTitle(year+"-"+month+"-"+day+"-"+hourOfDay+":"+minute);
            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Second.class);
                startActivity(intent);
            }
        });
    }

}
