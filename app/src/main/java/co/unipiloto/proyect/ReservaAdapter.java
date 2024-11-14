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

public class ReservaAdapter extends RecyclerView.Adapter<ReservaAdapter.ReservaViewHolder> {

    private Context context;
    private ArrayList<Integer> ids;
    private ArrayList<String> fechas, horas, estados;
    private OnReservaClickListener onReservaClickListener;

    public interface OnReservaClickListener{
        void onReservaClick(int id, String accion);
    }

    public ReservaAdapter(Context context, ArrayList<Integer> ids, ArrayList<String> fechas, ArrayList<String> horas, ArrayList<String> estados, OnReservaClickListener listener ){
        this.context = context;
        this.ids = ids;
        this.fechas = fechas;
        this.horas = horas;
        this.estados = estados;
        this.onReservaClickListener = listener;
    }

    @NonNull
    @Override
    public ReservaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reserva_activity, parent, false);
        return new ReservaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservaViewHolder holder, int position) {
        holder.textViewFecha.setText(fechas.get(position));
        holder.textViewHora.setText(horas.get(position));
        holder.textViewEstado.setText(estados.get(position));

        // Lógica para mostrar "Reservar" o "Cancelar" según el estado y la disponibilidad

        if (estados.get(position).equals("disponible")) {
            holder.textViewEstado.setTextColor(Color.GREEN);
            holder.btnReservar.setVisibility(View.VISIBLE);
            holder.btnCancelar.setVisibility(View.GONE);
        } else {
            holder.textViewEstado.setTextColor(Color.RED);
            holder.btnReservar.setVisibility(View.GONE);
            holder.btnCancelar.setVisibility(View.VISIBLE);
        }

        holder.btnReservar.setOnClickListener(v -> {
            onReservaClickListener.onReservaClick(ids.get(position), "reservar");
        });

        holder.btnCancelar.setOnClickListener(v -> {
            onReservaClickListener.onReservaClick(ids.get(position), "cancelar");
        });

    }

    @Override
    public int getItemCount() {
        return fechas.size();
    }

    public class ReservaViewHolder extends RecyclerView.ViewHolder {
        TextView textViewFecha, textViewHora, textViewEstado;
        Button btnReservar, btnCancelar;

        public ReservaViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewFecha = itemView.findViewById(R.id.textViewFecha_item_reserva);
            textViewHora = itemView.findViewById(R.id.textViewHora_item_reserva);
            textViewEstado = itemView.findViewById(R.id.textViewEstado_item_reserva);
            btnReservar = itemView.findViewById(R.id.btnReservar_item_reserva);
            btnCancelar = itemView.findViewById(R.id.btnCancelar_item_reserva);
        }
    }


}
