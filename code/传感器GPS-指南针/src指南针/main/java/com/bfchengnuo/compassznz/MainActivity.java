package com.bfchengnuo.compassznz;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView beijing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        beijing = (ImageView) findViewById(R.id.beijing);

        //定义一个传感器管理器
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //获取一个地磁传感器实例
        Sensor magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        //获取一个加速度传感器实例
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //绑定监听器
        sensorManager.registerListener(listener,magneticSensor,SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(listener,accelerometerSensor,SensorManager.SENSOR_DELAY_GAME);

    }
    private SensorEventListener listener = new SensorEventListener() {

        float[] accelerometerValues = new float[3];
        float[] magneticValues = new float[3];
        private float lastRotateDegree;

        //数值发生变化的时候触发
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                //如果是加速传感器
                //加clone是为了防止它们全部指向同一个引用
                accelerometerValues = event.values.clone();
            }else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
                //如果是地磁传感器
                magneticValues = event.values.clone();
            }

            float[] R = new float[9];
            float[] values = new float[3];
            //给R数组赋值  得到一个包含旋转矩阵的R数组  是一个9个Float的数组
            SensorManager.getRotationMatrix(R,null,accelerometerValues,magneticValues);
            //解析R数组中的数据，手机的旋转数据会保存到values中  Z  X  Y的旋转弧度
            SensorManager.getOrientation(R,values);
           // Log.d("lxc","values[0]---->"+Math.toDegrees(values[0]));  //转换为角度

            //计算出的角度取反用于旋转背景图
            float rotateDegree = -(float)Math.toDegrees(values[0]);
            if (Math.abs(rotateDegree - lastRotateDegree) > 1){
                //加载一个动画
                RotateAnimation animation = new RotateAnimation(lastRotateDegree,rotateDegree, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                animation.setFillAfter(true);
                beijing.startAnimation(animation);
                lastRotateDegree = rotateDegree;
            }
        }
        //精度发生变化的时候触发
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
