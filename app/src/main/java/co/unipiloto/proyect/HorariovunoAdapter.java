package co.unipiloto.proyect;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HorariovunoAdapter extends RecyclerView.Adapter<HorariovunoAdapter.HorarioViewHolder> {

    private Context context;
    private ArrayList<Integer> ids;
    private ArrayList<String> fechas, horas, estados, deportes;
    private OnHorarioClickListener onHorarioClickListener;

    public interface OnHorarioClickListener {
        void onHorarioClick(int id, String accion);
    }

    public HorariovunoAdapter(Context context, ArrayList<Integer> ids, ArrayList<String> fechas,
                          ArrayList<String> horas, ArrayList<String> estados, ArrayList<String> deportes,
                          OnHorarioClickListener listener) {
        this.context = context;
        this.ids = ids;
        this.fechas = fechas;
        this.horas = horas;
        this.estados = estados;
        this.deportes = deportes;
        this.onHorarioClickListener = listener;
    }

    @NonNull
    @Override
    public HorarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.actividad_item_horario, parent, false);
        return new HorarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorarioViewHolder holder, int position) {
        holder.textViewFecha.setText(fechas.get(position));
        holder.textViewHora.setText(horas.get(position));
        holder.textViewDeporte.setText(deportes.get(position));
        holder.textViewEstado.setText(estados.get(position));

        // Cambiar el color si el horario está reservado
        if (estados.get(position).equals("disponible")) {
            holder.textViewEstado.setTextColor(Color.GREEN);
            holder.btnReservar.setVisibility(View.VISIBLE);
            holder.btnCancelar.setVisibility(View.GONE);  // Oculto si está disponible
        } else if(estados.get(position).equals("reservado")){
            holder.textViewEstado.setTextColor(Color.RED);
            holder.btnReservar.setVisibility(View.GONE);
            holder.btnCancelar.setVisibility(View.VISIBLE);

        }


        // Acciones para los botones
        holder.btnReservar.setOnClickListener(v -> {
            onHorarioClickListener.onHorarioClick(ids.get(position), "reservar");
        });

        holder.btnCancelar.setOnClickListener(v -> {
            onHorarioClickListener.onHorarioClick(ids.get(position), "cancelar");
        });
    }

    @Override
    public int getItemCount() {
        return fechas.size();
    }

    public class HorarioViewHolder extends RecyclerView.ViewHolder {
        TextView textViewFecha, textViewHora, textViewDeporte, textViewEstado;
        Button btnReservar, btnCancelar;

        public HorarioViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewFecha = itemView.findViewById(R.id.textViewFecha_ac_item_horario);
            textViewHora = itemView.findViewById(R.id.textViewHora_ac_item_horario);
            textViewDeporte = itemView.findViewById(R.id.textViewDeporte_ac_item_horario);
            textViewEstado = itemView.findViewById(R.id.textViewEstado_ac_item_horario);
            btnReservar = itemView.findViewById(R.id.btnReservar_ac_item_horario);
            btnCancelar = itemView.findViewById(R.id.btnCancelar_ac_item_horario);
        }
    }



}
