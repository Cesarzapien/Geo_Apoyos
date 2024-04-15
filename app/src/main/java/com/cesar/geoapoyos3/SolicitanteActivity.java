package com.cesar.geoapoyos3;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cesar.geoapoyos3.adapter.EstatusAdapter;
import com.cesar.geoapoyos3.model.CustomSpinner;
import com.cesar.geoapoyos3.model.DataEstatus;

import java.util.Calendar;
import java.util.Locale;

public class SolicitanteActivity extends AppCompatActivity implements CustomSpinner.OnSpinnerEventsListener {
    private static final long MIN_TIME_UPDATE = 10000; // 1 minute
    private long lastUpdateTime = 0; // Initialize to 0
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private EditText editTextX, editTextY;

    private CustomSpinner spinner_estatus;
    private EstatusAdapter adapter;

    private ImageButton regresar, photo;
    private EditText fecha,hora;

    private int horaa,minuto;

    ActivityResultLauncher<Intent> imagePickerLauncher;
    Uri slectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if(data!=null && data.getData()!=null){
                            slectedImageUri = data.getData();
                        }
                    }
                });
        setContentView(R.layout.activity_solicitante);
        spinner_estatus = findViewById(R.id.spinner_estatus);
        regresar = findViewById(R.id.back_btn);
        fecha = findViewById(R.id.fecha_input);
        hora = findViewById(R.id.hora_input);
        editTextX = findViewById(R.id.coordenadas_input);
        editTextY = findViewById(R.id.coordenadas_input_y);
        photo = findViewById(R.id.btncamara);

        spinner_estatus.setSpinnerEventsListener(this);

        adapter = new EstatusAdapter(SolicitanteActivity.this, DataEstatus.getEstatusList());
        spinner_estatus.setAdapter(adapter);

        // Retrieve data from Intent
        Intent intent = getIntent();
        String nombreSolicitante = intent.getStringExtra("nombre_solicitante");
        String institucionSolicitante = intent.getStringExtra("instucion_solicitante");

        // Set data in TextViews
        TextView nombreTextView = findViewById(R.id.nombre_solicitante);
        nombreTextView.setText(nombreSolicitante);

        TextView institucionTextView = findViewById(R.id.instucion_solicitante);
        institucionTextView.setText(institucionSolicitante);

        // Get the current date and time (including minutes) upon Activity creation
        Calendar calendar = Calendar.getInstance();
        horaa = calendar.get(Calendar.HOUR_OF_DAY);
        minuto = calendar.get(Calendar.MINUTE);
        String currentDate = DateFormat.getDateFormat(this).format(calendar.getTime()); // Format date according to device locale

        // Set the date and time in the EditText fields
        fecha.setText(currentDate);
        hora.setText(String.format(Locale.getDefault(), "%d:%d", horaa, minuto));

        /*boton_hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                horaa = calendar.get(Calendar.HOUR_OF_DAY);
                minuto = calendar.get(Calendar.MINUTE);

                TimePickerDialog dialog;
                dialog = new TimePickerDialog(SolicitanteActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        horaa = hourOfDay;
                        minuto = minute;

                        hora.setText(String.format(Locale.getDefault(),"%d:%d"));

                    }
                }, horaa, minuto, true);
                dialog.show();
            }
        });*/

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SolicitanteActivity.this, MainActivity.class));
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Verificar permisos de ubicación
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Solicitar permisos de ubicación
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
            return;
        }

        // Inicializar LocationManager
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Configurar LocationListener para escuchar actualizaciones de ubicación
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                // Check if enough time has passed since the last update
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastUpdateTime >= MIN_TIME_UPDATE) {
                    lastUpdateTime = currentTime;

                    // Aquí obtienes la ubicación actual
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    // Actualizar los EditText con las coordenadas de ubicación
                    editTextX.setText(String.valueOf(latitude));
                    editTextY.setText(String.valueOf(longitude));
                }
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {}

            @Override
            public void onProviderDisabled(@NonNull String provider) {}

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}
        };

        // Solicitar actualizaciones de ubicación
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, reinicia la actividad para iniciar el seguimiento de ubicación
                recreate();
            } else {
                // Permiso denegado, muestra un mensaje de error
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onPopupWindowOpened(Spinner spinner) {
        spinner_estatus.setBackground(getResources().getDrawable(R.drawable.bg_spinner_estatus_up));
    }

    @Override
    public void onPopupWindowClosed(Spinner spinner) {
        spinner_estatus.setBackground(getResources().getDrawable(R.drawable.bg_spinner_estatus));
    }
}
