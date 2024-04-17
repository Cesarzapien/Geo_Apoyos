package com.cesar.geoapoyos3;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.here.sdk.core.GeoCoordinates;
import com.here.sdk.core.engine.SDKNativeEngine;
import com.here.sdk.core.engine.SDKOptions;
import com.here.sdk.core.errors.InstantiationErrorException;
import com.here.sdk.mapview.LocationIndicator;
import com.here.sdk.mapview.MapError;
import com.here.sdk.mapview.MapMeasure;
import com.here.sdk.mapview.MapScene;
import com.here.sdk.mapview.MapScheme;
import com.here.sdk.mapview.MapView;

import java.util.Date;

public class MapActivity extends AppCompatActivity {

    private final int FINE_PERMISSION_CODE = 1;
    private MapView mapView;
    private Context context;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Usually, you need to initialize the HERE SDK only once during the lifetime of an application.
        initializeHERESDK();

        setContentView(R.layout.activity_map);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();


        // Get a MapView instance from the layout.
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        loadMapScene();
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},FINE_PERMISSION_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location !=null){
                    currentLocation = location;
                    mapView = findViewById(R.id.map_view);
                    loadMapScene();
                }
            }
        });
    }

    private void initializeHERESDK() {
        // Set your credentials for the HERE SDK.
        String accessKeyID = "N7Dt1R0QVm69aR07WlUb3g";
        String accessKeySecret = "LUmzPMD8pozONpSf0dmXnvgi7rZ2b4orEE9USM-u5aKAudFBHwLhm16mHFpls5nxR7h8HrvR9_P7SS3lfv6MsA";
        SDKOptions options = new SDKOptions(accessKeyID, accessKeySecret);
        try {
            Context context = this;
            SDKNativeEngine.makeSharedInstance(context, options);
        } catch (InstantiationErrorException e) {
            throw new RuntimeException("Initialization of HERE SDK failed: " + e.error.name());
        }
    }

    private void loadMapScene() {
        // Load a scene from the HERE SDK to render the map with a map scheme.
        mapView.getMapScene().loadScene(MapScheme.NORMAL_DAY, new MapScene.LoadSceneCallback() {
            @Override
            public void onLoadScene(@Nullable MapError mapError) {
                if (mapError == null) {
                    double distanceInMeters = 1000 * 10;
                    MapMeasure mapMeasureZoom = new MapMeasure(MapMeasure.Kind.DISTANCE, distanceInMeters);
                    mapView.getCamera().lookAt(
                            new GeoCoordinates(currentLocation.getLatitude(),currentLocation.getLongitude()), mapMeasureZoom);
                    addLocationIndicator(new GeoCoordinates(currentLocation.getLatitude(),currentLocation.getLongitude()),LocationIndicator.IndicatorStyle.PEDESTRIAN);
                } else {
                    Log.d("loadMapScene()", "Loading map failed: mapError: " + mapError.name());
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == FINE_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }else{
                Toast.makeText(this,"Location permission is denied",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addLocationIndicator(GeoCoordinates geoCoordinates,
                                      LocationIndicator.IndicatorStyle indicatorStyle) {
        LocationIndicator locationIndicator = new LocationIndicator();
        locationIndicator.setLocationIndicatorStyle(indicatorStyle);

        // A LocationIndicator is intended to mark the user's current location,
        // including a bearing direction.
        com.here.sdk.core.Location location = new com.here.sdk.core.Location(geoCoordinates);
        location.time = new Date();
        location.bearingInDegrees = getRandom(0, 360);

        locationIndicator.updateLocation(location);

        // Show the indicator on the map view.
        locationIndicator.enable(mapView);
    }

    private double getRandom(double min, double max) {
        return min + Math.random() * (max - min);
    }

    private void disposeHERESDK() {
        // Free HERE SDK resources before the application shuts down.
        // Usually, this should be called only on application termination.
        // Afterwards, the HERE SDK is no longer usable unless it is initialized again.
        SDKNativeEngine sdkNativeEngine = SDKNativeEngine.getSharedInstance();
        if (sdkNativeEngine != null) {
            sdkNativeEngine.dispose();
            // For safety reasons, we explicitly set the shared instance to null to avoid situations,
            // where a disposed instance is accidentally reused.
            SDKNativeEngine.setSharedInstance(null);
        }
    }


    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        disposeHERESDK();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        mapView.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

}