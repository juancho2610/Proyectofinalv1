package co.unipiloto.proyect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public abstract class EliminarAdapter extends RecyclerView.Adapter<EliminarAdapter.ActividadViewHolder> {

    private Context context;
    private ArrayList<Integer> ids;
    private ArrayList<String> nombres, descripciones, localidades, estados;
    private OnItemClickListener onItemClickListener;

    public abstract void onItemClick(int id);

    // Interface para detectar clic en el ítem
    public interface OnItemClickListener {
        void onItemClick(int id);
    }

    public EliminarAdapter(Context context, ArrayList<Integer> ids, ArrayList<String> nombres, ArrayList<String> descripciones, ArrayList<String> localidades, ArrayList<String> estados, OnItemClickListener listener) {
        this.context = context;
        this.ids = ids;
        this.nombres = nombres;
        this.descripciones = descripciones;
        this.localidades = localidades;
        this.estados = estados;
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ActividadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_activity2, parent, false);
        return new ActividadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActividadViewHolder holder, int position) {
        holder.nombre.setText(nombres.get(position));
        holder.descripcion.setText(descripciones.get(position));
        holder.ubicacion.setText(localidades.get(position));
        holder.estado.setText(estados.get(position));

        // Configurar el clic en el ítem para eliminar
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(ids.get(position)));
    }

    @Override
    public int getItemCount() {
        return nombres.size();
    }

    public class ActividadViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, descripcion, ubicacion, estado;

        public ActividadViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.textViewNombreActividad);
            descripcion = itemView.findViewById(R.id.textViewDescripcionActividad);
            ubicacion = itemView.findViewById(R.id.textViewUbicacionActividad);
            estado = itemView.findViewById(R.id.textViewEstadoActividad);
        }
    }




}
