package com.example.bouchef.tubolsillo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bouchef.tubolsillo.R;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelResponse;
import com.example.bouchef.tubolsillo.tests.ItemBasico;

import java.util.ArrayList;
import java.util.List;

public class MensajesCompraAdapter extends RecyclerView.Adapter<MensajesCompraAdapter.ViewHolder> {
    private List<MensajeViewModelResponse> listaItems;

    private Context mContext;

    private OnItemClickListener mOnItemClickListener;

    interface OnItemClickListener{

        void onItemClick(MensajeViewModelResponse clickedMensaje);

        void onLeerMensaje(MensajeViewModelResponse leerMensaje);

    }

    public MensajesCompraAdapter(Context context, List<MensajeViewModelResponse> items) {
        listaItems = items;
        mContext = context;
    }

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void swapItems(List<MensajeViewModelResponse> appointments) {
        if (appointments == null) {
            listaItems = new ArrayList<>(0);
        } else {
            listaItems = appointments;
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.mensaje_compra_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MensajeViewModelResponse mensaje_compra = listaItems.get(position);

        View statusIndicator = holder.statusIndicator;

        // estado: se colorea indicador según el estado
        /*switch (appointment.getStatus()) {
            case "Activa":
                // mostrar botón
                holder.cancelButton.setVisibility(View.VISIBLE);
                statusIndicator.setBackgroundResource(R.color.activeStatus);
                break;
            case "Cumplida":
                // ocultar botón
                holder.cancelButton.setVisibility(View.GONE);
                statusIndicator.setBackgroundResource(R.color.completedStatus);
                break;
            case "Cancelada":
                // ocultar botón
                holder.cancelButton.setVisibility(View.GONE);
                statusIndicator.setBackgroundResource(R.color.cancelledStatus);
                break;
        }*/

        /*holder.date.setText(mensaje_compra.getFechaAlta());
        holder.service.setText(appointment.getService());
        holder.doctor.setText(appointment.getDoctor());
        holder.medicalCenter.setText(appointment.getMedicalCenter());*/
    }

    @Override
    public int getItemCount() {
        return listaItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView date;
        public TextView descripcion;
        public TextView tituloMensaje;
        public TextView medicalCenter;
        public Button leidoButton;
        public View statusIndicator;

        public ViewHolder(View itemView) {
            super(itemView);

            statusIndicator = itemView.findViewById(R.id.indicador_mensaje_status);
            date = (TextView) itemView.findViewById(R.id.text_mensaje_date);
            descripcion = (TextView) itemView.findViewById(R.id.text_descripcion);
            tituloMensaje = (TextView) itemView.findViewById(R.id.text_mensaje);
            medicalCenter = (TextView) itemView.findViewById(R.id.text_medical_center);
            leidoButton = (Button) itemView.findViewById(R.id.button_leer_mensaje);

            leidoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mOnItemClickListener.onLeerMensaje(listaItems.get(position));
                    }
                }
            });
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                mOnItemClickListener.onItemClick(listaItems.get(position));
            }
        }
    }

}
