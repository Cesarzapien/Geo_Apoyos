package com.cesar.geoapoyos3;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    TextView idusuariosplash;
    Bundle recibeDatos;
    String info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        idusuariosplash = findViewById(R.id.idusuario_splash);
        recibeDatos = getIntent().getExtras();
        if (recibeDatos != null) {
            info = recibeDatos.getString("idUsuario");
            if (info != null) {
                idusuariosplash.setText(info);
            }
        }

        ImageView imageView = findViewById(R.id.logo_image_view);

        // Definir la animación de escala
        final Animation animation = new ScaleAnimation(
                1f, 1.5f, // Escala inicial y final en X
                1f, 1.5f, // Escala inicial y final en Y
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivote X
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivote Y
        animation.setDuration(550); // Duración de la animación en milisegundos
        animation.setRepeatCount(3); // Repetir la animación dos veces
        animation.setRepeatMode(Animation.REVERSE); // Modo de repetición: revertir

        // Ejecutar la animación en la imagen
        imageView.startAnimation(animation);

        // Configurar un temporizador para iniciar la siguiente actividad después de que la animación haya finalizado
        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                Bundle enviaDatos2 = new Bundle();
                enviaDatos2.putString("idUsuario_main",info);
                Intent intent_datos = new Intent(SplashActivity.this, MainActivity.class);
                intent_datos.putExtras(enviaDatos2);
                startActivity(intent_datos);
                finish();
            }
        };
        Timer tiempo = new Timer();
        tiempo.schedule(tarea, 2000); // Retraso de 3500ms para permitir que la animación se complete antes de iniciar Login
    }
}
