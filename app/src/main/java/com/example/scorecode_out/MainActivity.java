package com.example.scorecode_out;

import android.content.Context;
import android.graphics.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Toast;

import java.security.Policy;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    final CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    final String[] cameraId = {null};

                    String myString = "01010101010101010101010101010101010101010101010101";
                    long blinkDelay = 100; //Delay in ms
                    for (int i = 0; i < myString.length(); i++) {
                        if (myString.charAt(i) == '0') {
                            try {
                                cameraId[0] = camManager.getCameraIdList()[0];
                                //Turn ON
                                camManager.setTorchMode(cameraId[0], true);
                            } catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                cameraId[0] = camManager.getCameraIdList()[0];
                                //Turn ON
                                camManager.setTorchMode(cameraId[0], false);

                            } catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                        }
                        try {
                            Thread.sleep(blinkDelay);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }




                    final int duration = 10; // duration of sound
                    final int sampleRate = 22050; // Hz (maximum frequency is 7902.13Hz (B8))
                    final int numSamples = duration * sampleRate;
                    final double samples[] = new double[numSamples];
                    final short buffer[] = new short[numSamples];
                    for (int i = 0; i < numSamples; ++i)
                    {
                        samples[i] = Math.sin(2 * Math.PI * i / (sampleRate / note[0])); // Sine wave
                        buffer[i] = (short) (samples[i] * Short.MAX_VALUE);  // Higher amplitude increases volume
                    }
                    AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                            sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                            AudioFormat.ENCODING_PCM_16BIT, buffer.length,
                            AudioTrack.MODE_STATIC);
                    audioTrack.write(buffer, 0, buffer.length);
                    audioTrack.play();


                }


                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
