package com.jrivera.bikecontrol.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import com.jrivera.bikecontrol.clases.Images;
import com.jrivera.bikecontrol.R;

/**
 * Created by ANDRES on 02-09-2016.
 */
public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.imagesViewHolder> {
    private ArrayList<Images> items;
    public ImagesAdapter(ArrayList<Images> items) {
        this.items = items;
    }

    @Override
    public imagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.images,parent,false);
        imagesViewHolder imagesViewHolder = new imagesViewHolder(view);
        return imagesViewHolder;
    }

    @Override
    public void onBindViewHolder(imagesViewHolder holder, int position) {

        holder.imagen.setImageBitmap(items.get(position).getImagen());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class imagesViewHolder extends RecyclerView.ViewHolder{
        ImageView imagen;
        public imagesViewHolder(View itemView) {
            super(itemView);
            imagen = (ImageView)itemView.findViewById(R.id.images);
        }
    }
}
