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


public class ComerciosRecyclerViewAdapter extends RecyclerView.Adapter<ComerciosRecyclerViewAdapter.ViewHolder>{
    private LayoutInflater mInflater;
    private List<ItemBasico> items;
    private boolean ocultarBotones;
    private Context context;
    private DatoBasicoItemClickListener  mClickListener;

    public ComerciosRecyclerViewAdapter(Context context, List<ItemBasico> emplazamientos) {
        this.mInflater = LayoutInflater.from(context);
        this.items = emplazamientos;
        this.context = context;
        this.ocultarBotones = ocultarBotones;
    }

    @Override
    public ComerciosRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.mensaje_comercio_item_list, parent, false);
        context = parent.getContext();
        return new ComerciosRecyclerViewAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ComerciosRecyclerViewAdapter.ViewHolder holder, final int position) {
        final ItemBasico c = items.get(position);
        holder.text_descripcion.setText(c.getDescripcion());
        //holder.text_mensaje_date.setText(c.getFecha());
    }


    // total number of rows
    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        @BindView(R.id.text_descripcion)
        TextView text_descripcion;
        //@BindView(R.id.text_mensaje_date) TextView text_mensaje_date;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

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
        void onClickEstrella(View view, int position);
    }

}