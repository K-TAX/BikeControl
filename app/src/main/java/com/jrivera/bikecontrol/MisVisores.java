package com.jrivera.bikecontrol;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.jrivera.bikecontrol.adapters.DispositivosAdapter;
import com.jrivera.bikecontrol.clases.Dispositivos;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Andres on 04/12/2016.
 */

public class MisVisores extends AppCompatActivity {
    @Bind(R.id.btnBack)
    ImageButton btnBack;
    private ImageView img;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;


    public MisVisores() {
        // Require empty public constructor
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fragmentvisores);
        ButterKnife.bind(this);

        ArrayList<Dispositivos> items = new ArrayList<Dispositivos>();
        items.add(new
                Dispositivos(R.drawable.smartphoneimg, "Moto X"));
        items.add(new Dispositivos(R.drawable.smartphoneimg, "LG-S5"));
        items.add(new Dispositivos(R.drawable.smartphoneimg, "Samsung Pro"));
        items.add(new Dispositivos(R.drawable.smartphoneimg, "Arcatel POP 5"));

        lManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
// Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.recyclerrastreo);
        recycler.setLayoutManager(lManager);
        //recycler.setHasFixedSize(true);
// Usar un administrador para LinearLayout
        adapter = new DispositivosAdapter(items);
        recycler.setAdapter(adapter);
    }
    @OnClick(R.id.btnBack)
    public void onClick() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}





