package com.cesar.geoapoyos3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cesar.geoapoyos3.model.ErrorResponse;
import com.cesar.geoapoyos3.model.LoginRequest;
import com.cesar.geoapoyos3.model.LoginResponse;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class LoginActivity extends AppCompatActivity {
    EditText email;
    EditText password_in;

    interface LoginService {
        @POST("/api/GeoA/login")
        Call<LoginResponse> login(@Body LoginRequest requestPost);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email_input);
        password_in = findViewById(R.id.password_input);
        Button btn_login = findViewById(R.id.btn_login);

        email.requestFocus();

        btn_login.setOnClickListener(v -> {
            String correo= email.getText().toString();
            String password = password_in.getText().toString();

            if (password.isEmpty() || password.length() < 5) {
                password_in.setError("La contraseña debe de tener mas de 5 caracteres");
            } else if (correo.isEmpty() || correo.length() < 8 || !correo.contains("@")) {
                email.setError("El Correo debe de tener @");
            } else {
                attemptLogin(correo, password);
            }
        });
    }
    private void attemptLogin(String correo, String password) {
        // Crear el cliente Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jellyfish-app-jr47t.ondigitalocean.app") // Reemplaza con la URL de tu API
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Crear el servicio de la API
        LoginService loginService = retrofit.create(LoginService.class);

        // Crear la solicitud de login
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setCorreo(correo);
        loginRequest.setPassword(password);

        // Realizar la llamada a la API
        Call<LoginResponse> loginCall = loginService.login(loginRequest);

        loginCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    // Login successful
                    LoginResponse loginResponse = response.body();

                    // Check for null response object before accessing properties
                    if (loginResponse != null) {
                        String tokenString = loginResponse.getToken();
                        // Check for null usuario object before accessing token
                        if (tokenString != null) {
                            // Handle successful login (store user/token, navigate to new activity)
                            Log.d("LoginActivity", "Token: " + tokenString);
                            // After getting the tokenString
                            SharedPreferences sharedPref = getSharedPreferences("my_prefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("access_token", tokenString);
                            editor.apply();

                            // Later to retrieve the token
                            String savedToken = sharedPref.getString("access_token", null);

                            startActivity(new Intent(LoginActivity.this, SplashActivity.class));
                        } else {
                            // Handle unexpected response structure (empty usuario object)
                            Toast.makeText(LoginActivity.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Handle unexpected response structure (empty response body)
                        Toast.makeText(LoginActivity.this, "Error: Respuesta inesperada", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Login failed (handle error response)
                    try {
                        ErrorResponse errorResponse = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);

                        // Mostrar el mensaje de error (assuming ErrorResponse has a message field)
                        String errorMessage = errorResponse.getMsg();
                        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Error en la comunicación con la API
                String errorMessage = "Error de red: " + t.getMessage();
                Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
