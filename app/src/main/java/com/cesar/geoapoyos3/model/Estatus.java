package com.cesar.geoapoyos3.model;

import java.io.Serializable;

public class Estatus implements Serializable {

    String estatus;

    public Estatus(String estatus) {
        this.estatus = estatus;
    }

    public Estatus() {
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
}
