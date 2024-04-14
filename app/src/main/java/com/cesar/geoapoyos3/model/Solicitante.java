package com.cesar.geoapoyos3.model;

public class Solicitante {
    int idSolicitante;
    String nombre;
    String primerApellido;
    String segundoApellido;
    String genero;
    String edad;
    String institucion;
    String grado;
    String tipoApoyo;
    String estatus;
    String correo;

    public Solicitante(int idSolicitante, String nombre, String primerApellido, String segundoApellido, String genero, String edad, String institucion, String grado, String tipoApoyo, String estatus, String correo) {
        this.idSolicitante = idSolicitante;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.genero = genero;
        this.edad = edad;
        this.institucion = institucion;
        this.grado = grado;
        this.tipoApoyo = tipoApoyo;
        this.estatus = estatus;
        this.correo = correo;
    }

    public Solicitante() {
    }

    public int getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(int idSolicitante) {
        this.idSolicitante = idSolicitante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getTipoApoyo() {
        return tipoApoyo;
    }

    public void setTipoApoyo(String tipoApoyo) {
        this.tipoApoyo = tipoApoyo;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
