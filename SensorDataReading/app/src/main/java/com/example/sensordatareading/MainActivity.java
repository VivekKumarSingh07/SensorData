package com.example.sensordatareading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private Sensor mSensorProximity;
    private Sensor mSensorLight;
    private Sensor mSensorAccelerometer;
    private Sensor mSensorMagnetometer;

    private SensorManager mSensorManager;

    private TextView mTextSensorLight;
    private TextView mTextSensorProximity;
    private TextView mtextSensorAccelerometer;
    private TextView mTextSensorMagnetometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextSensorLight = findViewById(R.id.label_light);
        mTextSensorProximity = findViewById(R.id.label_proximity);
        mtextSensorAccelerometer= findViewById(R.id.label_accelerometer);
        mTextSensorMagnetometer = findViewById(R.id.label_magnetometer);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        String sensorError = getResources().getString(R.string.error_no_sensor);


        if (mSensorLight == null)
            mTextSensorLight.setText(sensorError);

        if (mSensorProximity == null)
            mTextSensorLight.setText(sensorError);

        if(mSensorAccelerometer ==null)
            mtextSensorAccelerometer.setText(sensorError);

        if(mSensorMagnetometer ==null)
            mTextSensorMagnetometer.setText(sensorError);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mSensorProximity != null) {
            mSensorManager.registerListener(this, mSensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorLight != null) {
            mSensorManager.registerListener(this, mSensorLight, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(mSensorAccelerometer!=null){
            mSensorManager.registerListener(this, mSensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if(mSensorMagnetometer!=null){
            mSensorManager.registerListener(this, mSensorMagnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        float []currentValue =  new float[3];

        switch (sensorType) {
            case Sensor.TYPE_LIGHT:
                currentValue[0] = event.values[0];
                mTextSensorLight.setText(getResources().getString(R.string.label_light, currentValue[0]));
                break;
            case Sensor.TYPE_PROXIMITY:
                currentValue[0] = event.values[0];
                mTextSensorProximity.setText(getResources().getString(R.string.label_proximity, currentValue[0]));
                break;
            case Sensor.TYPE_ACCELEROMETER:
                for(int i=0; i<3; i++){
                    currentValue[i] = event.values[i];
                }
                mtextSensorAccelerometer.setText("accelerometer Values" + currentValue[0] + " " + currentValue[1] + " " + currentValue[2]);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                for(int i=0; i<3; i++){
                    currentValue[i] = event.values[i];
                }
                mTextSensorMagnetometer.setText("Magnetometer value:" + currentValue[0] + " " + currentValue[1] + " " + currentValue[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}

