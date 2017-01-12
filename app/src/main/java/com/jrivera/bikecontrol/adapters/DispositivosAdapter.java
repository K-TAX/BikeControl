package com.jrivera.bikecontrol.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.jrivera.bikecontrol.R;
import com.jrivera.bikecontrol.clases.Dispositivos;


/**
 * Created by ANDRES on 07-09-2016.
 */
public class DispositivosAdapter  extends RecyclerView.Adapter<DispositivosAdapter.dispoViewHolder> {
    private ArrayList<Dispositivos> items;
    public DispositivosAdapter(ArrayList<Dispositivos> items) {
        this.items = items;
    }
    Context context;

    @Override
    public DispositivosAdapter.dispoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewdispositivos,parent,false);
        dispoViewHolder dispositivosViewHolder = new dispoViewHolder(view);
        context=parent.getContext();
        return dispositivosViewHolder;
    }

    @Override
    public void onBindViewHolder(final DispositivosAdapter.dispoViewHolder holder, int position) {
        holder.imagen.setImageResource(items.get(position).getImagen());
        holder.dispositivoname.setText(items.get(position).getDispositivoName());
        holder.btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //click informacion
                System.out.println("click perfect");
            }
        });
        holder.btnDesvincular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                // set title
                alertDialogBuilder.setTitle("Desea Desvincular este Dispositivo");
                alertDialogBuilder
                        .setPositiveButton("Si",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                                System.out.println("desvinculando....");

                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class dispoViewHolder extends RecyclerView.ViewHolder{

        ImageView imagen;
        TextView dispositivoname;
       public Button btnInfo;
       public Button btnDesvincular;
        public dispoViewHolder(View itemView) {
            super(itemView);
            imagen = (ImageView)itemView.findViewById(R.id.imagen);
            dispositivoname= (TextView)itemView.findViewById(R.id.txtDispositivoName);
            btnInfo = (Button)itemView.findViewById(R.id.btnInfo);
            btnDesvincular = (Button)itemView.findViewById(R.id.btnDesvincular);
        }
    }
}
