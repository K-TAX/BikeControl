package com.jrivera.bikecontrol.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import com.jrivera.bikecontrol.R;
import com.jrivera.bikecontrol.adapters.HistoryAdapter;
import com.jrivera.bikecontrol.clases.CardViews;


/**
 * Created by ANDRES on 28-08-2016.
 */
public class HistoryFragment extends Fragment {

    public HistoryFragment() {
        // Require empty public constructor
    }

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LinearLayout linearView2 = (LinearLayout)inflater.inflate(R.layout.fragmenthistory, container, false);
        // Inicializar Animes
        ArrayList<CardViews> items = new ArrayList<CardViews>();
        items.add(new CardViews(R.drawable.ic_bike, "20 Minutos", "Osorno - 11:30 AM"));
        items.add(new CardViews(R.drawable.ic_bike, "23 Minutos", "Osorno - 12:30 AM"));
        items.add(new CardViews(R.drawable.ic_bike, "30 Minutos", "Osorno - 10:30 AM"));
        items.add(new CardViews(R.drawable.ic_bike, "10 Minutos", "Osorno - 17:30 AM"));
        items.add(new CardViews(R.drawable.ic_bike, "15 Minutos", "Osorno - 11:30 AM"));

// Obtener el Recycler
        recycler = (RecyclerView)linearView2.findViewById(R.id.recycler);
        //recycler.setHasFixedSize(true);
// Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(lManager);

// Crear un nuevo adaptador
        adapter = new HistoryAdapter(items);
        recycler.setAdapter(adapter);

        return linearView2;

    }


}