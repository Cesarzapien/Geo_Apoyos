package com.cesar.geoapoyos3.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("usuario")
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public static class Usuario {
        int idUsuario;
        String nombre;
        String primerApellido;
        String segundoApellido;
        String puesto;
        String fechaContratacion;
        String sueldo;
        String correo;
        String contrasenia;
        String estatus;

        public Usuario(int idUsuario, String nombre, String primerApellido, String segundoApellido, String puesto, String fechaContratacion, String sueldo, String correo, String contrasenia, String estatus) {
            this.idUsuario = idUsuario;
            this.nombre = nombre;
            this.primerApellido = primerApellido;
            this.segundoApellido = segundoApellido;
            this.puesto = puesto;
            this.fechaContratacion = fechaContratacion;
            this.sueldo = sueldo;
            this.correo = correo;
            this.contrasenia = contrasenia;
            this.estatus = estatus;
        }

        public Usuario() {
        }

        @Override
        public String toString() {
            return "Usuario{" +
                    "idUsuario=" + idUsuario +
                    ", nombre='" + nombre + '\'' +
                    ", primerApellido='" + primerApellido + '\'' +
                    ", segundoApellido='" + segundoApellido + '\'' +
                    ", puesto='" + puesto + '\'' +
                    ", fechaContratacion='" + fechaContratacion + '\'' +
                    ", sueldo='" + sueldo + '\'' +
                    ", correo='" + correo + '\'' +
                    ", contrasenia='" + contrasenia + '\'' +
                    ", estatus='" + estatus + '\'' +
                    '}';
        }

        public int getIdUsuario() {
            return idUsuario;
        }

        public void setIdUsuario(int idUsuario) {
            this.idUsuario = idUsuario;
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

        public String getPuesto() {
            return puesto;
        }

        public void setPuesto(String puesto) {
            this.puesto = puesto;
        }

        public String getFechaContratacion() {
            return fechaContratacion;
        }

        public void setFechaContratacion(String fechaContratacion) {
            this.fechaContratacion = fechaContratacion;
        }

        public String getSueldo() {
            return sueldo;
        }

        public void setSueldo(String sueldo) {
            this.sueldo = sueldo;
        }

        public String getCorreo() {
            return correo;
        }

        public void setCorreo(String correo) {
            this.correo = correo;
        }

        public String getContrasenia() {
            return contrasenia;
        }

        public void setContrasenia(String contrasenia) {
            this.contrasenia = contrasenia;
        }

        public String getEstatus() {
            return estatus;
        }

        public void setEstatus(String estatus) {
            this.estatus = estatus;
        }
    }

}
