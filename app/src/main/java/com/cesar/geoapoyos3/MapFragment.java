package com.cesar.geoapoyos3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class MapFragment extends Fragment {

    private Button btnIniciarMapa;

    // ... other fragment code (onCreate, onCreateView, etc.)

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        // Find the button by its ID
        btnIniciarMapa = view.findViewById(R.id.btn_iniciar_mapa);

        // Set click listener for the button
        btnIniciarMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to launch the MapActivity
                Intent intent = new Intent(getActivity(), MapActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
