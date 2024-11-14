package co.unipiloto.proyect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ActividadAdapter extends RecyclerView.Adapter<ActividadAdapter.ActividadesViewHolder> {

    private Context context;
    private ArrayList<String> nombres, descripciones, localidades, estados;

    public ActividadAdapter(Context context, ArrayList<String> nombres, ArrayList<String> descripciones, ArrayList<String> localidades, ArrayList<String> estados){
        this.context = context;
        this.nombres = nombres;
        this.descripciones = descripciones;
        this.localidades = localidades;
        this.estados = estados;
    }

    @NonNull
    @Override
    public ActividadesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_activity2,parent,false);
        return new ActividadesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActividadesViewHolder holder, int position) {

        holder.nombre.setText(nombres.get(position));
        holder.descripcion.setText(descripciones.get(position));
        holder.localidades.setText(localidades.get(position));
        holder.estados.setText(estados.get(position));
    }

    @Override
    public int getItemCount() {
        return nombres.size();
    }

    public class ActividadesViewHolder extends RecyclerView.ViewHolder{
        TextView nombre, descripcion, localidades, estados;

        public ActividadesViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.textViewNombreActividad);
            descripcion = itemView.findViewById(R.id.textViewDescripcionActividad);
            localidades = itemView.findViewById(R.id.textViewUbicacionActividad);
            estados = itemView.findViewById(R.id.textViewEstadoActividad);
        }
    }
}
