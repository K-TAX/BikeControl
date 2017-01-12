package com.jrivera.bikecontrol.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import com.jrivera.bikecontrol.R;
import com.jrivera.bikecontrol.adapters.ImagesAdapter;
import com.jrivera.bikecontrol.clases.Images;

/**
 * Created by ANDRES on 28-08-2016.
 */
public class ThirdCameraFragment extends Fragment implements OnClickListener {
    Button btn;
    private RecyclerView.LayoutManager lManager;
    Intent intent;
    Bitmap bmp;
    private RecyclerView.Adapter adapter;
    private RecyclerView recycler;
    Fragment frag = this;
    LinearLayout linearView2;
    final static int cont = 0;
    final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    ArrayList<Images> items = new ArrayList<Images>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        linearView2 = (LinearLayout)inflater.inflate(R.layout.fragmentcamara, container, false);
        init();
        // Obtener el Recycler
         recycler = (RecyclerView)linearView2.findViewById(R.id.recycler);
        //recycler.setHasFixedSize(true);
// Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(lManager);

// Crear un nuevo adaptador

        adapter = new ImagesAdapter(items);
        recycler.setAdapter(adapter);
        return linearView2;
    }
    public void init() {
        btn = (Button)linearView2.findViewById(R.id.btnShotImage);
        btn.setOnClickListener(this);
       // img = (ImageView)linearView2.findViewById(R.id.recycler);
    }


    @Override
    public void onClick(View v) {
    int id = v.getId();
        switch (id){
            case R.id.btnShotImage:
            intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,
                        CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
              //  startActivityForResult(intent,cont);
                break;
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int a = 0;
        if(resultCode== Activity.RESULT_OK){
            Bundle ext = data.getExtras();
            bmp = (Bitmap)ext.get("data");
            items.add(new Images(bmp));
        }
    }
}
