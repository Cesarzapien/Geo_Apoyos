package com.cesar.geoapoyos3;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
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

import java.io.FileDescriptor;
import java.io.IOException;
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

    private ImageButton regresar,photo,camara;
    private EditText fecha,hora;

    private int horaa,minuto;

    ImageView casa_photo;

    Uri image_uri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitante);
        spinner_estatus = findViewById(R.id.spinner_estatus);
        regresar = findViewById(R.id.back_btn);
        fecha = findViewById(R.id.fecha_input);
        hora = findViewById(R.id.hora_input);
        editTextX = findViewById(R.id.coordenadas_input);
        editTextY = findViewById(R.id.coordenadas_input_y);
        camara = findViewById(R.id.btncamara);
        photo = findViewById(R.id.btngaleria);
        casa_photo = findViewById(R.id.image_view);

        ActivityResultLauncher<Intent> galleryActivityResultLauncher =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        new ActivityResultCallback<ActivityResult>() {

                            // ▬▬ "ON ACTIVITY RESULT()" METHOD
                            //      → TO "GET" THE "SELECTED DATA" (THE "IMAGE")
                            //      → FROM "GALLERY" AND "DISPLAY IT" ▬▬
                            @Override
                            public void onActivityResult(ActivityResult result) {
                                // ▼ WE "MAKE SURE" THAT THE "USER SELECTED" AN "IMAGE" ▼
                                if (result.getResultCode() == Activity.RESULT_OK) {
                                    // ▼ GET "SELECTED DATA" FROM "GALLERY" ▼
                                    //Uri image_uri = result.getData().getData();
                                    image_uri = result.getData().getData();

                                    // ▼ DISPLAY "SELECTED DATA" IN "IMAGE VIEW" ▼
                                    //imageView.setImageURI(image_uri);


                                    // ▼ CONVERTING "SELECTED DATA" TO "BITMAP" ▼
                                    // ▼ GET "SELECTED DATA" FROM "CAMERA" ▼
                                    Bitmap inputImage = uriToBitmap(image_uri);

                                    // ▼ "REMOVING" THE "ROTATION" ▼
                                    Bitmap rotated = rotateBitmap(inputImage);

                                    // ▼ "DISPLAYING" THE "CAPTURED IMAGE" ▼
                                    casa_photo.setImageBitmap(rotated);
                                }
                            }
                        }
                );

        // ▼ "ASKING" FOR "PERMISSION" OF "CAMERA" UPON "FIRST LAUNCH" OF "APP"  ▼
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED){
                String[] permission = {android.Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permission, 112);
            }
        }

        // ▼ "SET ON CLICK LISTENERS" → FOR "GALLERY BUTTON"
        //      → FOR "CHOOSING IMAGE" FROM "GALLERY" ▼
        photo.setOnClickListener(new View.OnClickListener() {

            // ▬▬ "ON CLICK()" METHOD ▬▬
            @Override
            public void onClick(View v) {
                // ▼ OPEN "GALLERY"
                //      → BY SPECIFYING "ACTION PICK"
                //      → AND SPECIFYING "MEDIA" TYPE,
                //      → WHICH WE WANT TO OPEN ▼
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                // ▼ CALL "LAUNCHER" FOR "GALLERY" ▼
                galleryActivityResultLauncher.launch(galleryIntent);
            }
        });

        // ▼ "SET ON CLICK LISTENERS" → FOR "CAMERA BUTTON"
        //      → FOR "CAPTURE IMAGE" USING "CAMERA" ▼
        camara.setOnClickListener(new View.OnClickListener() {

            // ▬▬ "ON CLICK()" METHOD ▬▬
            @Override
            public void onClick(View view) {

                // ▼ "CHECKING" FOR "PERMISSION" OF "CAMERA" UPON "FIRST LAUNCH" OF "APP" ▼
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                    // ▼ "ASKING" FOR "PERMISSION" OF "CAMERA" UPON "FIRST LAUNCH" OF "APP"
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED){
                        String[] permission = {
                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        };
                        requestPermissions(permission, 112);

                    } else {
                        // ▼ "CALLING" THE "METHOD" ▼
                        openCamera();
                    }
                }
                else {
                    // ▼ "CALLING" THE "METHOD" ▼
                    openCamera();
                }
            }
        });


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

    // ▬▬ "OPEN CAMERA()" METHOD
    //      → SO THAT THE USER CAN CAPTURE IMAGE USING "CAMERA" ▬▬
    private void openCamera() {

        // ▼ CREATING A "CONTENT VALUES" OBJECT ▼
        ContentValues values = new ContentValues();

        // ▼ ADDING A "TITLE" AND "DESCRIPTION" TO THE "CONTENT VALUES" OBJECT ▼
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");

        // STORING THE "CAPTURED IMAGE" IN "IMAGE_URI" VARIABLE ▼
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);



        // ▼ THE "ACTION" OF "CAPTURE IMAGE" USING "CAMERA" ▼
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // ▼ STORING "IMAGE" IN "IMAGE_URI" VARIABLE ▼
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);

        // ▼ CALLING THE "CAMERA ACTIVITY RESULT LAUNCHER" ▼
        cameraActivityResultLauncher.launch(cameraIntent);
    }




    // ▼ "CAMERA ACTIVITY RESULT LAUNCHER" OBJECT ("DECLARATION" & "INITIALIZATION")
    //      → TO "GETTING" THE "IMAGE"
    //      → BY USING "CAMERA" AND "DISPLAY IT" ▼
    ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                // ▬▬ "ON ACTIVITY RESULT()" METHOD
                @Override
                public void onActivityResult(ActivityResult result) {

                    // ▼ IF "USER CAPTURED" THE "IMAGE"
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // ▼ GET "SELECTED DATA" FROM "CAMERA" ▼
                        Bitmap inputImage = uriToBitmap(image_uri);

                        // ▼ ROTATING THE "CAPTURED IMAGE" ▼
                        Bitmap rotated = rotateBitmap(inputImage);

                        // ▼ DISPLAYING THE "CAPTURED IMAGE" ▼
                        casa_photo.setImageBitmap(rotated);
                    }
                }
            });





    // ▬▬ "URI TO BITMAP()" METHOD
    //      → TO "TAKES URI" OF THE "IMAGE" AND "RETURNS BITMAP" ▬▬
    private Bitmap uriToBitmap(Uri selectedFileUri) {

        try {
            ParcelFileDescriptor parcelFileDescriptor =
                    getContentResolver().openFileDescriptor(selectedFileUri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);

            parcelFileDescriptor.close();
            return image;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }






    // ▬▬ "ROTATE BITMAP()" METHOD
    //      → FOR "ROTATING" THE "CAPTURED IMAGE" IN A "PORTRAIT" MODE,
    //      → IF "IMAGE" IS "CAPTURED" ON "SAMSUNG DEVICES"
    //      → ( MOST "PHONES CAMERAS" ARE "LANDSCAPE",
    //      →   "MEANING" IF YOU "TAKE" THE "PHOTO" IN "PORTRAIT",
    //      →   THE RESULTING PHOTOS WILL BE ROTATED 90 DEGREES) ▬▬
    @SuppressLint("Range")
    public Bitmap rotateBitmap(Bitmap input){
        String[] orientationColumn = { MediaStore.Images.Media.ORIENTATION };
        Cursor cur = getContentResolver().query(image_uri, orientationColumn, null, null, null);
        int orientation = -1;

        // ▼ IF THERE IS AN "IMAGE" ▼
        if (cur != null && cur.moveToFirst()) {
            // ▼ GETTING THE "ORIENTATION" OF THE "IMAGE" ▼
            orientation = cur.getInt(cur.getColumnIndex(orientationColumn[0]));
        }

        Log.d("tryOrientation",orientation+"");
        Matrix rotationMatrix = new Matrix();
        rotationMatrix.setRotate(orientation);
        Bitmap cropped = Bitmap.createBitmap(input,0,0, input.getWidth(), input.getHeight(), rotationMatrix, true);
        return cropped;
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
