package com.jrivera.bikecontrol.adapters;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.jrivera.bikecontrol.R;
import com.jrivera.bikecontrol.clases.CardViews;

/**
 * Created by ANDRES on 28-08-2016..
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.historyViewHolder>{
    private ArrayList<CardViews> items;

    public HistoryAdapter(ArrayList<CardViews> items) {
        this.items = items;
    }

    @Override
    public historyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        historyViewHolder historyViewHolder = new historyViewHolder(view);
        return historyViewHolder;
    }

    @Override
    public void onBindViewHolder(historyViewHolder holder, int position) {
        holder.minutos.setText(items.get(position).getMinutos());
        holder.fechayhora.setText(items.get(position).getFechayhora());
        holder.imagen.setImageResource(items.get(position).getImagen());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class historyViewHolder extends RecyclerView.ViewHolder{
        TextView minutos,fechayhora;
        ImageView imagen;
        public historyViewHolder(View itemView) {
            super(itemView);

            minutos = (TextView)itemView.findViewById(R.id.minutos);
            fechayhora = (TextView)itemView.findViewById(R.id.fechayhora);
            imagen = (ImageView)itemView.findViewById(R.id.imagen);
        }
    }

}