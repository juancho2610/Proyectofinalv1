package co.unipiloto.proyect;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VerHorarioAdapter extends RecyclerView.Adapter<VerHorarioAdapter.HorarioViewHolder> {

    private Context context;
    private ArrayList<Integer> ids;
    private ArrayList<String> fechas, horas,localidades, estados;
    private OnItemClickListener onItemClickListener;
    private OnDeleteClickListener onDeleteClickListener;


    // Interface para manejar el clic en los ítems
    public interface OnItemClickListener {
        void onItemClick(int id);  // Pasamos el ID del horario seleccionado
    }

    // Interface para manejar el clic en el botón "Eliminar"
    public interface OnDeleteClickListener {
        void onDeleteClick(int id);
    }

    public VerHorarioAdapter(Context context, ArrayList<Integer> ids, ArrayList<String> fechas, ArrayList<String>  horas, ArrayList<String> localidades, OnItemClickListener listener, OnDeleteClickListener deletelistener){
        this.context = context;
        this.ids = ids;
        this.fechas = fechas;
        this.horas = horas;
        this.localidades = localidades;
        this.onItemClickListener = listener;
        this.onDeleteClickListener = deletelistener;

    }

    @NonNull
    @Override
    public HorarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.item_ver_horario_activity,parent,false);
        return  new HorarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorarioViewHolder holder, int position) {

            // Mostrar los datos en los TextViews
            holder.textViewFecha.setText(fechas.get(position));
            holder.textViewHora.setText(horas.get(position));
            holder.textViewLocalidad.setText(localidades.get(position));

            //Manejar el click en el item modificar
        holder.itemView.setOnClickListener(view -> {
            onItemClickListener.onItemClick(ids.get(position));
        });

        //Manejar el click en el boton eliminar
        holder.btnEliminar.setOnClickListener(v ->{
            onDeleteClickListener.onDeleteClick(ids.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return fechas.size();
    }

    public class HorarioViewHolder extends RecyclerView.ViewHolder {
        TextView textViewFecha, textViewHora, textViewLocalidad;
        Button btnEliminar;

        public HorarioViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewFecha = itemView.findViewById(R.id.textViewFecha_item_horario);
            textViewHora = itemView.findViewById(R.id.textViewHora_item_horario);
            textViewLocalidad = itemView.findViewById(R.id.textViewLocalidad_item_horario);
            btnEliminar = itemView.findViewById(R.id.btnEliminar_item_horario);

        }
    }

}
