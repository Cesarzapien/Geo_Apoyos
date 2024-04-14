package com.cesar.geoapoyos3.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cesar.geoapoyos3.R;
import com.cesar.geoapoyos3.model.Solicitante;

import java.util.List;

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.UserViewHolder> {

    private List<Solicitante> solicitantes; // Replace with your data source (List of Solicitante)

    public UserRecyclerAdapter(List<Solicitante> solicitantes) {
        this.solicitantes = solicitantes;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_recycler_row, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Solicitante solicitante = solicitantes.get(position);
        holder.nombre_solicitante.setText(solicitante.getNombre() + " " + solicitante.getPrimerApellido());
        holder.institucion.setText(solicitante.getInstitucion());
        // Set profile image and status based on data (if applicable)
        // Change background color of status ImageView based on status
        if (solicitante.getEstatus().equalsIgnoreCase("Inactivo")) {
            holder.status.setImageResource(R.drawable.circle_shape_inactive); // Cambia por tu recurso para estado inactivo
        } else {
            holder.status.setImageResource(R.drawable.circle_shape_active); // Cambia por tu recurso para estado activo
        }
    }

    @Override
    public int getItemCount() {
        return solicitantes.size();
    }
    public class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView nombre_solicitante;
        public TextView institucion;
        public ImageView profilePic;
        public ImageView status;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            // Check for null references before findViewById
            nombre_solicitante = itemView.findViewById(R.id.txtNombreSolicitante);
            institucion = itemView.findViewById(R.id.txtInstitucionSolicitante);
            profilePic = itemView.findViewById(R.id.profile_pic_image_view);
            status = itemView.findViewById(R.id.status_solicitante);
        }
    }
}
