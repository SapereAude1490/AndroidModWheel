package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
//import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public int X;   //Holds the X position of the finger
    public int Y;   //Holds the Y position of the finger

    BluetoothAdapter BTAdapter = BluetoothAdapter.getDefaultAdapter();
    // Defines the variable BTAdapter as a BluetoothAdapter variable type
    // Gets the default bluetooth adapter and assigns it to BTAdapter variable

    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (action.equals(BTAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BTAdapter.ERROR);

                switch(state){
                    case BluetoothAdapter.STATE_OFF:
                        Log.d("", "onReceive: STATE OFF");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d("", "mBroadcastReceiver: STATE OFF");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d("", "mBroadcastReceiver: STATE OFF");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d("", "mBroadcastReceiver: STATE OFF");
                        break;
                }
            }
        }
    };

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Getting the X and Y

         ConstraintLayout PitchArea = (ConstraintLayout)  findViewById(R.id.MainLayout);

        PitchArea.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View view, MotionEvent motionEvent) {
                 Log.i("TouchEvents","Touch is detected.");

                 int eventType = motionEvent.getActionMasked();
                 X = (int)motionEvent.getX();
                 Y = (int)motionEvent.getY();

                 //Thus switch only logs where X and Y are.

                 switch (eventType)
                 {
                     case MotionEvent.ACTION_DOWN:
                         //Log.i("TouchEvents","Action Down.");
                         break;
                     case MotionEvent.ACTION_UP:
                         //Log.i("TouchEvents","Action Up.");
                         break;
                     case MotionEvent.ACTION_MOVE:
                         //Log.i("TouchEvents","Action Action Move.");
                         Log.d("PositionXY","Xvalue: " + X);
                         Log.d("PositionXY","Yvalue: " + Y);
                         break;
                 }
                 return true;
             }
         });

            ////Bluetooth adapter code////

        ImageView BTImageOnOff = findViewById(R.id.BluetoothImg);
        // Defines the BTImageOnOff as an ImageView type variable (can switch image)

        Button onOffButton = findViewById(R.id.onOffButton);
        // Defines the button switch as a Button type variable



        onOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableDisableBT();
            }
        });



        //Checks if the Bluetooth adapter is available

        if (BTAdapter.isEnabled()){
            BTImageOnOff.setImageResource(R.drawable.bluetooth_on);
        }
        else{
            BTImageOnOff.setImageResource(R.drawable.bluetooth_off);

        };

        }

        public void enableDisableBT(){
            if(BTAdapter == null){
                Log.d("","No Bluetooth on device.");
            }
            if(!BTAdapter.isEnabled()){
                Intent enabledBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivity(enabledBTIntent);

                IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
                registerReceiver(mBroadcastReceiver, BTIntent);
            }
            if(BTAdapter.isEnabled()){
                BTAdapter.disable();

                IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
                registerReceiver(mBroadcastReceiver, BTIntent);
            }
        }

    }



