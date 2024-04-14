package com.cesar.geoapoyos3;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SolicitanteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_solicitante);

        // Spinner setup
        Spinner dropdownSpinner = findViewById(R.id.dropdown_spinner); // Replace with your spinner's ID

        // Assuming you have a string array defined in your resources (e.g., strings.xml)
        String[] options = getResources().getStringArray(R.array.options_array); // Replace with your array resource ID

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, options);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        dropdownSpinner.setAdapter(adapter);

        // Handle on item selected event (optional)
        dropdownSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle item selection here (e.g., display a toast message)
                String selectedItem = (String) parent.getItemAtPosition(position);
                // ...
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle no selection here (optional)
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
