package co.unipiloto.proyect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ActividadesconVotosAdapter extends RecyclerView.Adapter<ActividadesconVotosAdapter.ActividadesVotosViewHolder> {

    private Context context;
    private ArrayList<String> nombres;
    private ArrayList<String> descripciones;
    private ArrayList<String> localidades;
    private ArrayList<String> estados;
    private ArrayList<Integer> actividadIds;
    private ArrayList<Integer> votos;

    public ActividadesconVotosAdapter(Context context, ArrayList<String> nombres,ArrayList<String> descripciones,ArrayList<String> localidades,ArrayList<String> estados, ArrayList<Integer> votos) {

        this.context = context;
        this.nombres = nombres;
        this.descripciones = descripciones;
        this.localidades = localidades;
        this.estados = estados;
        this.votos = votos;

    }


    @NonNull
    @Override
    public ActividadesconVotosAdapter.ActividadesVotosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.actividadesconvotos_item_activity, parent, false);
        return new ActividadesVotosViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ActividadesVotosViewHolder holder, int position) {

        holder.textViewNombre.setText("NOMBRE: " + nombres.get(position));
        holder.textViewDescripcion.setText("DESCRIPCION: " + descripciones.get(position));
        holder.textViewLocalidad.setText("LOCALIDAD: " + localidades.get(position));
        holder.textViewEstado.setText("ESTADO: " + estados.get(position));
        holder.textViewVotos.setText("VOTOS: " + votos.get(position));

    }

    @Override
    public int getItemCount() {
        return nombres.size();
    }

    public class ActividadesVotosViewHolder extends  RecyclerView.ViewHolder {

        TextView textViewNombre,textViewDescripcion,textViewLocalidad,textViewEstado, textViewVotos;

        public ActividadesVotosViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNombre = itemView.findViewById(R.id.textViewNombre_item_actividadesconvotos);
            textViewDescripcion = itemView.findViewById(R.id.textViewDescripcion_item_actividadesconvotos);
            textViewLocalidad = itemView.findViewById(R.id.textViewLocalidad_item_actividadesconvotos);
            textViewEstado = itemView.findViewById(R.id.textViewEstado_item_actividadesconvotos);
            textViewVotos = itemView.findViewById(R.id.textViewVotos_item_actividadesconvotos);
        }

    }
}
