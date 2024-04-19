package com.cesar.geoapoyos3.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("usuarioRecortado")
    private UsuarioRecortado usuarioRecortado;
    @SerializedName("token")
    private String token; // Add the token field

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                '}';
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UsuarioRecortado getUsuarioRecortado() {
        return usuarioRecortado;
    }

    public void setUsuarioRecortado(UsuarioRecortado usuarioRecortado) {
        this.usuarioRecortado = usuarioRecortado;
    }

    public static class UsuarioRecortado {
        int idUsuario;
        String nombre;
        String correo;

        public UsuarioRecortado(int idUsuario, String nombre, String correo) {
            this.idUsuario = idUsuario;
            this.nombre = nombre;
            this.correo = correo;
        }

        public UsuarioRecortado() {
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

        public String getCorreo() {
            return correo;
        }

        public void setCorreo(String correo) {
            this.correo = correo;
        }

        @Override
        public String toString() {
            return "UsuarioRecortado{" +
                    "idUsuario=" + idUsuario +
                    ", nombre='" + nombre + '\'' +
                    ", correo='" + correo + '\'' +
                    '}';
        }
    }

}
