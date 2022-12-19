package com.example.sequence;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity implements SensorEventListener
{

    //Booleans
    boolean northHighLimit = false;
    boolean southHighLimit = false;
    boolean eastHighLimit = false;
    boolean westHighLimit = false;

    //Counters
    int northCounter = 0;
    int southCounter = 0;
    int eastCounter = 0;
    int westCounter = 0;

    TextView tvNorthCount, tvSouthCount, tvEastCount, tvWestCount;

    private SensorManager mSensorManager;
    private Sensor mSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tvNorthCount = findViewById(R.id.tvNorthCount);
        tvSouthCount = findViewById(R.id.tvSouthCount);
        tvEastCount = findViewById(R.id.tvEastCount);
        tvWestCount = findViewById(R.id.tvWestCount);

        //sensor service
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Get the array from first activity
        //Bundle extras = getIntent().getExtras();
        //int[] arrayB = extras.getIntArray("numbers");
    }


    protected void onResume()
    {
        super.onResume();
        //turn on the sensor
        mSensorManager.registerListener(this, mSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }


    //App running but not on screen - in the background
    protected void onPause()
    {
        super.onPause();
        mSensorManager.unregisterListener(this);    // turn off listener to save power
    }


    @Override
    public void onSensorChanged(SensorEvent event)
    {

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        //North tilt
        double northForward = -4.0;
        double northBack = -3.0;

        if ((x > northForward) && (!northHighLimit))
        {
            northHighLimit = true;
        }
        else if ((x < northBack) && (northHighLimit))
        {
            //tilt to the north
            northCounter++;
            tvNorthCount.setText(String.valueOf(northCounter));
            northHighLimit = false;
        }

        //South tilt
        double southForward = 4.0;
        double southBack = 3.0;

        if ((x > southForward) && (!southHighLimit))
        {
            southHighLimit = true;
        }
        else if ((x < southBack) && (southHighLimit))
        {
            //tilt to the south
            southCounter++;
            tvSouthCount.setText(String.valueOf(southCounter));
            southHighLimit = false;
        }

        //East tilt
        double eastForward = 4.0;
        double eastBack = 3.0;

        if ((y > eastForward) && (!eastHighLimit))
        {
            eastHighLimit = true;
        }
        else if ((y < eastBack) && (eastHighLimit))
        {
            //tilt to the east
            eastCounter++;
            tvEastCount.setText(String.valueOf(eastCounter));
            eastHighLimit = false;
        }

        //West tilt
        double westForward = -4.0;
        double westBack = -3.0;

        if ((y > westForward) && (!westHighLimit))
        {
            westHighLimit = true;
        }
        else if ((y < westBack) && (westHighLimit))
        {
            //tilt to the west
            westCounter++;
            tvWestCount.setText(String.valueOf(westCounter));
            westHighLimit = false;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
        // not used
    }

    public static double round(double value, int places)
    {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
