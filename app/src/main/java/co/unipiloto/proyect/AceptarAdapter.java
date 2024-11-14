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

public class AceptarAdapter extends RecyclerView.Adapter<AceptarAdapter.ActividadViewHolder> {

    private Context context;
    private ArrayList<Integer> ids;
    private ArrayList<String> nombres,descripciones, localidades, estados;
    private OnAceptarClickListener onAceptarClickListener;

    //Interfaz para mnaejr el click en el boton "aceptar"
    public interface OnAceptarClickListener{
        void onAceptarClick(int id);
    }

    public AceptarAdapter(Context context, ArrayList<Integer> ids, ArrayList<String> nombres, ArrayList<String> descripciones, ArrayList<String> localidades, ArrayList<String> estados, OnAceptarClickListener listener) {
        this.context = context;
        this.ids = ids;
        this.nombres = nombres;
        this.descripciones = descripciones;
        this.localidades = localidades;
        this.estados = estados;
        this.onAceptarClickListener = listener;
    }

    @NonNull
    @Override
    public ActividadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_aceptar_activity, parent, false);
        return new ActividadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActividadViewHolder holder, int position) {

        holder.nombre.setText(nombres.get(position));
        holder.descripcion.setText(descripciones.get(position));
        holder.localidad.setText(localidades.get(position));
        holder.estado.setText(estados.get(position));

        // Manejar el clic en el botón "Aceptar"
        holder.btnAceptar.setOnClickListener(v -> onAceptarClickListener.onAceptarClick(ids.get(position)));

    }

    @Override
    public int getItemCount() {
        return nombres.size();
    }

    public class ActividadViewHolder extends RecyclerView.ViewHolder{
        TextView nombre, descripcion, localidad,estado;
        Button btnAceptar;

        public ActividadViewHolder(@NonNull View itemView){
            super(itemView);
            nombre = itemView.findViewById(R.id.textViewNombre_item_aceptar);
            descripcion = itemView.findViewById(R.id.textViewDescripcion_item_aceptar);
            localidad = itemView.findViewById(R.id.textViewLocalidad_item_aceptar);
            estado = itemView.findViewById(R.id.textViewEstado_item_aceptar);
            btnAceptar = itemView.findViewById(R.id.buttonACEPTAR_acpetar);
        }
    }
}
