package com.jrivera.bikecontrol;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jrivera.bikecontrol.fragments.HistoryFragment;
import com.jrivera.bikecontrol.fragments.MapFragment;
import com.jrivera.bikecontrol.fragments.ThirdCameraFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.btnActionFloatMap)
    FloatingActionButton btnActionFloatMap;
    @Bind(R.id.btnActionFloatHistory)
    FloatingActionButton btnActionFloatHistory;
    @Bind(R.id.btnActionFloatCamera)
    FloatingActionButton btnActionFloatCamera;
    @Bind(R.id.fragmentContainer)
    FrameLayout fragmentContainer;
    Fragment fragment;
    String username;
    @Bind(R.id.btn_DrawerIn)
    android.support.design.widget.FloatingActionButton btnDrawerIn;
    @Bind(R.id.btnActionFloatAlarm)
    FloatingActionButton btnActionFloatAlarm;
    @Bind(R.id.btn_disabledAlarm)
    android.support.design.widget.FloatingActionButton btnDisabledAlarm;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private static MediaPlayer soundAcc;
    /**
     * Sensors
     */
    private SensorManager mSensorManager;
    private Sensor mSensorGyr;
    private long mShakeTime = 0;

    /**
     * Constants for sensors
     */
    private static final float SHAKE_THRESHOLD = 1.1f;
    private static final int SHAKE_WAIT_TIME_MS = 250;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegation_drawer);
        ButterKnife.bind(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //sensores
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorGyr = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        soundAcc = MediaPlayer.create(this, R.raw.acc);

        mAuth = FirebaseAuth.getInstance();
        DatabaseReference mRefDatabase = FirebaseDatabase.getInstance().getReference();
        mRefDatabase = mRefDatabase.child("users").child(mAuth.getCurrentUser().getUid()).child("alarma");

        mRefDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               System.out.println(dataSnapshot.getChildren());
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
        System.out.println();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // User is signed in
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    Log.d("", "onAuthStateChanged:signed_out");
                } else {
                    Log.d("", "onAuthStateChanged:signed_in:" + user.getUid());
                }
                // ...
            }
        };
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new MapFragment()).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.btnActionFloatMap, R.id.btnActionFloatHistory, R.id.btnActionFloatCamera, R.id.btnActionFloatAlarm,R.id.btn_disabledAlarm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnActionFloatMap:
                cambiarFragment(new MapFragment(), "Mapa Activado");
                setTitle("Mapa");
                break;
            case R.id.btnActionFloatHistory:
                cambiarFragment(new HistoryFragment(), "Historial de Recorrido");
                setTitle("Historial de Recorrido");
                break;
            case R.id.btnActionFloatCamera:
                cambiarFragment(new ThirdCameraFragment(), "Toma Fotografias");
                setTitle("Camara");
                break;
            case R.id.btnActionFloatAlarm:
                openDialog();
                break;
            case R.id.btn_disabledAlarm:
              btnDisabledAlarm.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void openDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle("Desea Colocar el Dispositivo en Modo Alarma");

        alertDialogBuilder
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                       dialog.cancel();
                    }
                })
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        btnDisabledAlarm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                btnDisabledAlarm.setVisibility(View.INVISIBLE);
                                DatabaseReference mRefDatabase = FirebaseDatabase.getInstance().getReference();
                                mSensorManager.unregisterListener(sensorListener);
                                mRefDatabase.child("users").child(mAuth.getCurrentUser().getUid()).child("alarma").setValue(false);
                            }
                        });
                        btnDisabledAlarm.setVisibility(View.VISIBLE);
                        DatabaseReference mRefDatabase = FirebaseDatabase.getInstance().getReference();
                        mRefDatabase.child("users").child(mAuth.getCurrentUser().getUid()).child("alarma").setValue(true);
                        mSensorManager.registerListener(sensorListener, mSensorGyr, SensorManager.SENSOR_DELAY_NORMAL);

                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }


    SensorEventListener sensorListener = new SensorEventListener(){
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {


        }

        @Override
        public void onSensorChanged(SensorEvent event) {


            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                detectShake(event);
            }

        }

    };

    private void detectShake(SensorEvent event) {
        long now = System.currentTimeMillis();

        if ((now - mShakeTime) > SHAKE_WAIT_TIME_MS) {
            mShakeTime = now;

            float gX = event.values[0] / SensorManager.GRAVITY_EARTH;
            float gY = event.values[1] / SensorManager.GRAVITY_EARTH;
            float gZ = event.values[2] / SensorManager.GRAVITY_EARTH;

            // gForce will be close to 1 when there is no movement
            double gForce = Math.sqrt(gX * gX + gY * gY + gZ * gZ);

            // Change background color if gForce exceeds threshold;
            // otherwise, reset the color
            if (gForce > SHAKE_THRESHOLD) {
                soundAcc.start();
            }
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        System.out.println("perfect");
        int id = item.getItemId();
        if (id == R.id.nav_bikecontrol) {


        } else if (id == R.id.nav_misvisores) {
            Intent i = new Intent(this, MisVisores.class);
            startActivity(i);
        } else if (id == R.id.nav_gallery) {


        } else if (id == R.id.nav_cerrar) {

            mAuth.signOut();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void cambiarFragment(Fragment newFragment, String message) {

        fragment = newFragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @OnClick(R.id.btn_DrawerIn)
    public void onClick() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.openDrawer(Gravity.LEFT);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    protected void onResume() {
        super.onResume();
       // mSensorManager.registerListener(sensorListener, mSensorGyr, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(sensorListener);
    }

}
