package com.example.bouchef.tubolsillo.tests;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bouchef.tubolsillo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MensajesRecyclerViewAdapter extends RecyclerView.Adapter<MensajesRecyclerViewAdapter.ViewHolder>
{
    private LayoutInflater mInflater;
    private List<ItemBasico> items;
    private boolean ocultarBotones;
    private Context context;
    private DatoBasicoItemClickListener  mClickListener;

    public MensajesRecyclerViewAdapter(Context context, List<ItemBasico> emplazamientos) {
        this.mInflater = LayoutInflater.from(context);
        this.items = emplazamientos;
        this.context = context;
        this.ocultarBotones = ocultarBotones;
    }

    @Override
    public MensajesRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.mensaje_compra_item_list, parent, false);
        context = parent.getContext();
        return new MensajesRecyclerViewAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(MensajesRecyclerViewAdapter.ViewHolder holder, final int position) {
        final ItemBasico c = items.get(position);
        holder.text_descripcion.setText(c.getDescripcion());
        holder.text_mensaje_date.setText(c.getFecha());


        /*if(c.isClickeado()){
            Picasso.get().load(android.R.drawable.star_big_on).into(holder.estrella);
        }else{
            Picasso.get().load(android.R.drawable.star_big_off).into(holder.estrella);
        }*/
        //holder.sincronizar_cantidad.setText(String.valueOf(c.getCantidadPendienteDeSync()));
        ///////////////////////////////////////////////////////////////
/*
        if(true){
            holder.texto.setTextColor(Color.BLACK);
            holder.texto.setTypeface(null, Typeface.BOLD);
        }
        else {
            holder.texto.setTextColor(Color.GRAY);
            holder.texto.setTypeface(null, Typeface.ITALIC);
        }*/

    }


    // total number of rows
    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        @BindView(R.id.text_descripcion) TextView text_descripcion;
        @BindView(R.id.text_mensaje_date) TextView text_mensaje_date;
        //@BindView(R.id.like) ImageView estrella;

        //@BindView(R.id.layout_ok) LinearLayout layout_ok;

        ViewHolder(View itemView) {
            super(itemView);
            //sitio = itemView.findViewById(R.id.sitio);
            ButterKnife.bind(this, itemView);
/*
            layout_ok.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return mClickListener.onLongClickEliminar(view, getAdapterPosition());
                }
            });*/

            /*estrella.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mClickListener !=null)
                        mClickListener.onClickEstrella(v, getAdapterPosition());
                    else{
                        Alerts.newToastLarge(context, "Falta implementar click");
                    }
                }
            });*/

        }
    }


    public ItemBasico getItem(int id) {
        return items.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(DatoBasicoItemClickListener   itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface DatoBasicoItemClickListener {
        //void onItemClickEmplazamiento(View view, int position);
        void onClickEstrella(View view, int position);
        //boolean onLongClickEliminar(View view, int position);
    }

}