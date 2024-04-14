package com.cesar.geoapoyos3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cesar.geoapoyos3.adapter.UserRecyclerAdapter;
import com.cesar.geoapoyos3.model.Solicitante;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserRecyclerAdapter adapter;
    private List<Solicitante> solicitantes;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the list of solicitantes
        solicitantes = new ArrayList<>();
        // Add some sample data for testing
        solicitantes.add(new Solicitante(1, "Juan", "Perez", "Gomez", "Masculino", "30", "Universidad X", "Grado X", "Apoyo X", "Activo", "juan@example.com"));
        solicitantes.add(new Solicitante(2, "Maria", "Garcia", "Lopez", "Femenino", "25", "Colegio Y", "Grado Y", "Apoyo Y", "Inactivo", "maria@example.com"));
        solicitantes.add(new Solicitante(3, "Carlos", "Lopez", "Gutierrez", "Masculino", "35", "Instituto Z", "Grado Z", "Apoyo Z", "Activo", "carlos@example.com"));
        solicitantes.add(new Solicitante(4, "Ana", "Martinez", "Fernandez", "Femenino", "28", "Escuela W", "Grado W", "Apoyo W", "Activo", "ana@example.com"));
        solicitantes.add(new Solicitante(5, "Pedro", "Sanchez", "Diaz", "Masculino", "32", "Colegio V", "Grado V", "Apoyo V", "Inactivo", "pedro@example.com"));
        solicitantes.add(new Solicitante(6, "Laura", "Gomez", "Rodriguez", "Femenino", "29", "Universidad Q", "Grado Q", "Apoyo Q", "Activo", "laura@example.com"));
        solicitantes.add(new Solicitante(7, "Luis", "Hernandez", "Santos", "Masculino", "31", "Colegio P", "Grado P", "Apoyo P", "Inactivo", "luis@example.com"));
        solicitantes.add(new Solicitante(8, "Elena", "Fernandez", "Garcia", "Femenino", "27", "Escuela O", "Grado O", "Apoyo O", "Activo", "elena@example.com"));
        solicitantes.add(new Solicitante(9, "Raul", "Diaz", "Alvarez", "Masculino", "33", "Instituto N", "Grado N", "Apoyo N", "Inactivo", "raul@example.com"));
        solicitantes.add(new Solicitante(10, "Sofia", "Perez", "Hernandez", "Femenino", "26", "Universidad M", "Grado M", "Apoyo M", "Activo", "sofia@example.com"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        recyclerView = view.findViewById(R.id.visitantes_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new UserRecyclerAdapter(solicitantes);
        recyclerView.setAdapter(adapter);
        return view;
    }
}