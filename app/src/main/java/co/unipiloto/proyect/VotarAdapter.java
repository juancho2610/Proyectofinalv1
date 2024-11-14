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

public class VotarAdapter extends RecyclerView.Adapter<VotarAdapter.ActividadViewHolder> {

    private Context context;
    private ArrayList<Integer> ids;
    private ArrayList<String> nombres, descripciones;
    private OnVotarClickListener onVotarClickListener;

    // Interface para manejar el clic en el botón "Aceptar"
    public interface OnVotarClickListener {
        void onVotarClick(int actividadId);
    }

    public VotarAdapter(Context context, ArrayList<Integer> ids, ArrayList<String> nombres, ArrayList<String> descripciones, OnVotarClickListener listener) {
        this.context = context;
        this.ids = ids;
        this.nombres = nombres;
        this.descripciones = descripciones;
        this.onVotarClickListener = listener;
    }

    @NonNull
    @Override
    public ActividadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_votar_activity,parent, false);
        return new ActividadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActividadViewHolder holder, int position) {

        holder.nombre.setText(nombres.get(position));
        holder.descripcion.setText(descripciones.get(position));

        // Manejar el clic en el botón "Aceptar"
        holder.btnVotar.setOnClickListener(v -> onVotarClickListener.onVotarClick(ids.get(position)));

    }

    @Override
    public int getItemCount() {
        return nombres.size();
    }

    public class ActividadViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, descripcion;
        Button btnVotar;

        public ActividadViewHolder(@NonNull View itemview){
            super(itemview);
            nombre = itemview.findViewById(R.id.textViewNombre_item_votar);
            descripcion = itemview.findViewById(R.id.textViewDescripcion_item_votar);
            btnVotar = itemview.findViewById(R.id.buttonVoto);
        }
    }
}
