package com.cesar.geoapoyos3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cesar.geoapoyos3.adapter.UserRecyclerAdapter;
import com.cesar.geoapoyos3.model.ResponseSolicitantes;
import com.cesar.geoapoyos3.model.Solicitante;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class UserFragment extends Fragment {

    private RecyclerView recyclerView;
    private String idUsuario;
    private UserRecyclerAdapter adapter;
    private List<Solicitante> solicitantes;
    TextView idusuario;



    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the list of solicitantes

        solicitantes = new ArrayList<>();
        // Add some sample data for testing
        /*solicitantes.add(new Solicitante(1, "Juan", "Perez", "Gomez", "Masculino", "30", "Universidad Tecnológica de México", "Grado X", "Apoyo X", "Activo", "juan@example.com"));
        solicitantes.add(new Solicitante(2, "Maria", "Garcia", "Lopez", "Femenino", "25", "Universidad Tecnológica de León", "Grado Y", "Apoyo Y", "Inactivo", "maria@example.com"));
        solicitantes.add(new Solicitante(3, "Carlos", "Lopez", "Gutierrez", "Masculino", "35", "Universidad Autónoma de México", "Grado Z", "Apoyo Z", "Activo", "carlos@example.com"));
        solicitantes.add(new Solicitante(4, "Ana", "Martinez", "Fernandez", "Femenino", "28", "Universidad de Guanajuato", "Grado W", "Apoyo W", "Activo", "ana@example.com"));
        solicitantes.add(new Solicitante(5, "Pedro", "Sanchez", "Diaz", "Masculino", "32", "Universidad de León", "Grado V", "Apoyo V", "Inactivo", "pedro@example.com"));
        solicitantes.add(new Solicitante(6, "Laura", "Gomez", "Rodriguez", "Femenino", "29", "Instituto Politécnico Nacional", "Grado Q", "Apoyo Q", "Activo", "laura@example.com"));
        solicitantes.add(new Solicitante(7, "Luis", "Hernandez", "Santos", "Masculino", "31", "Universidad Autónoma de México", "Grado P", "Apoyo P", "Inactivo", "luis@example.com"));
        solicitantes.add(new Solicitante(8, "Elena", "Fernandez", "Garcia", "Femenino", "27", "Universidad de León", "Grado O", "Apoyo O", "Activo", "elena@example.com"));
        solicitantes.add(new Solicitante(9, "Raul", "Diaz", "Alvarez", "Masculino", "33", "Universidad de la Salle", "Grado N", "Apoyo N", "Inactivo", "raul@example.com"));
        solicitantes.add(new Solicitante(10, "Sofia", "Perez", "Hernandez", "Femenino", "26", "Universidad Tecnológica de León", "Grado M", "Apoyo M", "Activo", "sofia@example.com"));*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        //idusuario = view.findViewById(R.id.textView_id_usuario_fragment);
        //Bundle data = getArguments();
        //if(data != null){
            //idUsuario = data.getString("idusuariofragment");
        //}

        //idusuario.setText(idUsuario);


        recyclerView = view.findViewById(R.id.visitantes_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new UserRecyclerAdapter(solicitantes);
        recyclerView.setAdapter(adapter);
        // Llamada a la API para obtener la lista de solicitantes
        obtenerSolicitantesDesdeAPI();
        return view;
    }

    private void obtenerSolicitantesDesdeAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jellyfish-app-jr47t.ondigitalocean.app/api/GeoA/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GeoAService service = retrofit.create(GeoAService.class);
        Call<ResponseSolicitantes> call = service.obtenerSolicitantes();

        call.enqueue(new Callback<ResponseSolicitantes>() {
            @Override
            public void onResponse(Call<ResponseSolicitantes> call, Response<ResponseSolicitantes> response) {
                if (response.isSuccessful()) {
                    ResponseSolicitantes responseSolicitantes = response.body();
                    if (responseSolicitantes != null && responseSolicitantes.getSolicitantes() != null) {
                        solicitantes.clear(); // Limpiar la lista actual
                        solicitantes.addAll(responseSolicitantes.getSolicitantes()); // Agregar nuevos solicitantes
                        adapter.notifyDataSetChanged(); // Notificar al adaptador sobre el cambio en los datos
                    }
                } else {
                    // Manejar respuesta fallida
                    Toast.makeText(getContext(), "Error al obtener solicitantes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseSolicitantes> call, Throwable t) {
                // Manejar fallo en la solicitud
                Toast.makeText(getContext(), "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Interfaz para definir el método GET
    interface GeoAService {
        @GET("solicitantes")
        Call<ResponseSolicitantes> obtenerSolicitantes();
    }


}