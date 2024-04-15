package com.cesar.geoapoyos3.model;

import java.util.ArrayList;
import java.util.List;

public class DataEstatus {

    public static List<Estatus> getEstatusList() {
        List<Estatus> estatusList = new ArrayList<>();

        Estatus Visitado = new Estatus();
        Visitado.setEstatus("Visitado");
        estatusList.add(Visitado);

        Estatus Encontrado = new Estatus();
        Encontrado.setEstatus("Encontrado");
        estatusList.add(Encontrado);

        Estatus No_encontrado = new Estatus();
        No_encontrado.setEstatus("No encontrado");
        estatusList.add(No_encontrado);

        Estatus Rechazada_visita = new Estatus();
        Rechazada_visita.setEstatus("Rechazada la visita");
        estatusList.add(Rechazada_visita);

        Estatus Abandonada_visita = new Estatus();
        Abandonada_visita.setEstatus("Abandonada la visita");
        estatusList.add(Abandonada_visita);

        return estatusList;
    }
}
