package com.jrivera.bikecontrol.clases;

/**
 * Created by Andres on 04/12/2016.
 */

public class CardViews {
    private int imagen;
    private String minutos;
    private String fechayhora;

    public CardViews(int imagen, String minutos, String fechayhora) {
        this.imagen = imagen;
        this.minutos = minutos;
        this.fechayhora = fechayhora;
    }

    public String getMinutos() {
        return minutos;
    }

    public String getFechayhora() {
        return fechayhora;
    }

    public int getImagen() {
        return imagen;
    }
}
