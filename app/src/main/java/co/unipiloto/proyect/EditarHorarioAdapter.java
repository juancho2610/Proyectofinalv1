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

public class EditarHorarioAdapter extends RecyclerView.Adapter<EditarHorarioAdapter.HorarioViewHolder> {

    private Context context;
    private ArrayList<Integer> ids;
    private ArrayList<String> fechas, horas, localidades;
    private OnItemClickListener onItemClickListener;

    // Interface para manejar el clic en los ítems
    public interface OnItemClickListener {
        void onItemClick(int id);

    }

    public EditarHorarioAdapter(Context context, ArrayList<Integer> ids, ArrayList<String> fechas, ArrayList<String> horas, ArrayList<String> localidades, OnItemClickListener listener){

        this.context = context;
        this.ids = ids;
        this.fechas = fechas;
        this.horas = horas;
        this.localidades = localidades;
        this.onItemClickListener = listener;

    }

    @NonNull
    @Override
    public HorarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_modificar_horario_activity, parent, false);
        return new HorarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorarioViewHolder holder, int position) {
        holder.textViewFecha.setText(fechas.get(position));
        holder.textViewHora.setText(horas.get(position));
        holder.textViewLocalidad.setText(localidades.get(position));

        // Manejar el clic en el ítem
        holder.itemView.setOnClickListener(v -> {
            onItemClickListener.onItemClick(ids.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return fechas.size();  // Tamaño de la lista
    }

    public class HorarioViewHolder extends RecyclerView.ViewHolder {
        TextView textViewFecha, textViewHora, textViewLocalidad;

        public HorarioViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewFecha = itemView.findViewById(R.id.textViewFecha_item_horario_mod);
            textViewHora = itemView.findViewById(R.id.textViewHora_item_horario_mod);
            textViewLocalidad = itemView.findViewById(R.id.textViewLocalidad_item_horario_mod);
        }
    }

}
