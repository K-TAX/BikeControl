package com.jrivera.bikecontrol.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import com.jrivera.bikecontrol.R;
import com.jrivera.bikecontrol.adapters.DispositivosAdapter;
import com.jrivera.bikecontrol.clases.Dispositivos;

/**
 * Created by ANDRES on 08-09-2016.
 */
public class DispositivosFragment extends Fragment{
    ImageView img;
    public DispositivosFragment() {
        // Require empty public constructor
    }

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LinearLayout linearView2 = (LinearLayout)inflater.inflate(R.layout.fragmentvisores, container, false);
        // Inicializar Animes
        ArrayList<Dispositivos> items = new ArrayList<Dispositivos>();
        items.add(new Dispositivos(R.drawable.smartphoneimg,"Moto X"));
        items.add(new Dispositivos(R.drawable.smartphoneimg,"LG-S5"));
        items.add(new Dispositivos(R.drawable.smartphoneimg,"Samsung Pro"));
        items.add(new Dispositivos(R.drawable.smartphoneimg,"Arcatel POP 5"));


// Obtener el Recycler
        recycler = (RecyclerView)linearView2.findViewById(R.id.recyclerrastreo);
        //recycler.setHasFixedSize(true);
// Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler.setLayoutManager(lManager);

// Crear un nuevo adaptador
        adapter = new DispositivosAdapter(items);
        recycler.setAdapter(adapter);

        return linearView2;

    }


}

