package com.jrivera.bikecontrol.clases;

/**
 * Created by ANDRES on 08-09-2016.
 */
public class Dispositivos {
    private int imagen;
    private String dispositivoname;

    public Dispositivos(int imagen,String dispositivoname) {
        this.imagen = imagen;
        this.dispositivoname = dispositivoname;

    }
    public int getImagen() {
        return imagen;
    }
    public String getDispositivoName() {
        return dispositivoname;
    }
}
