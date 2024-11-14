package co.unipiloto.proyect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EliminarVotoAdapter extends RecyclerView.Adapter<EliminarVotoAdapter.ActividadViewHolder> {

    private Context context;
    private ArrayList<Integer> ids;
    private ArrayList<String> nombres, descripciones;
    private OnEliminarVotoClickListener onEliminarVotoClickListener;

    // Interface para manejar el clic en el botón "Eliminar Voto"
    public interface OnEliminarVotoClickListener {
        void onEliminarVotoClick(int actividadId);
    }

    public EliminarVotoAdapter(Context context, ArrayList<Integer> ids, ArrayList<String> nombres, ArrayList<String> descripciones, OnEliminarVotoClickListener listener) {
        this.context = context;
        this.ids = ids;
        this.nombres = nombres;
        this.descripciones = descripciones;
        this.onEliminarVotoClickListener = listener;
    }

    @NonNull
    @Override
    public ActividadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_eliminar_voto_activity, parent, false);
        return new ActividadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActividadViewHolder holder, int position) {

        holder.nombre.setText(nombres.get(position));
        holder.descripcion.setText(descripciones.get(position));

        // Manejar el clic en el botón "Eliminar Voto"
        holder.btnEliminarVoto.setOnClickListener(v -> onEliminarVotoClickListener.onEliminarVotoClick(ids.get(position)));

    }

    @Override
    public int getItemCount() {
        return nombres.size();
    }

    public class ActividadViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, descripcion;
        Button btnEliminarVoto;

        public ActividadViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.textViewNombre_item_eliminar_votar);
            descripcion = itemView.findViewById(R.id.textViewDescripcion_item_eliminar_votar);
            btnEliminarVoto = itemView.findViewById(R.id.buttonVoto_eliminar);
        }
    }
}
