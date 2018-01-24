package com.example.patryyyk21.sensory;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager sensorManager;
    private Sensor proximyty, gravity, accelerometer;
    private float[] gravityArray = new float[3];
    private float[] accelerometerArray = new float[3];
    private TextView tvMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ListView listS = (ListView)findViewById(R.id.sensorList);
        tvMessage = (TextView)findViewById(R.id.sensorMessage);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        // Pobieranie inforamcji z konkretnego sensora
        //proximyty = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // pobranie do listy wszystkich sensorów dostępnych na urządzeniu
        List<Sensor> tempList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        List<String> sensorList = new ArrayList<String>();

        for(int i = 0; i<tempList.size(); i++){
            // Pobranie wybranych informacji o sensorach
            sensorList.add(tempList.get(i).getName());
        }

        // Wyświetlenie informacji o sensorach dostępnych na urządzeniu
        //ArrayAdapter<Sensor> adapter = new ArrayAdapter<Sensor>(getApplicationContext(),
        //        android.R.layout.simple_list_item_1, tempList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, sensorList);
        //listS.setAdapter(adapter);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //tvMessage.setText(event.values[0] + " ");

        // Kopiowanie z nasłuchiwanego sensoru do wybranej tabeli
        if(event.sensor.getType() == Sensor.TYPE_GRAVITY){
            System.arraycopy(event.values, 0, gravityArray, 0, gravityArray.length);
        }
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            System.arraycopy(event.values, 0, accelerometerArray, 0,
                    accelerometerArray.length);
        }

        tvMessage.setText(gravityArray[0] + " " + gravityArray[1] + " " + gravityArray[2] + "\n" +
                    accelerometerArray[0] + " " +
                accelerometerArray[1] + " " +
                accelerometerArray[2] + " ");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Rejestracja nasłuchisania sensorów
        //sensorManager.registerListener(this, proximyty, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gravity, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // wyrejsetrowanie nasłuchiwania sensorów
        sensorManager.unregisterListener(this);
    }
}
