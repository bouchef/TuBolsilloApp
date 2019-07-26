package com.example.bouchef.tubolsillo.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.bouchef.tubolsillo.R.id;
import static com.example.bouchef.tubolsillo.R.layout;

/**
 * Created by Cesar on 12/10/2015.
 */
public class LenguajeListAdapterFavoritos extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final Integer[] integers;
    private final Integer[] imagenes;

    public LenguajeListAdapterFavoritos(Activity context, String[] itemname, Integer[] integers, Integer[] imagenes) {
        super(context, layout.fila_lista, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.integers=integers;
        this.imagenes=imagenes;
    }

    public View getView(int posicion, View view, ViewGroup parent){

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(layout.fila_lista,null,true);

        TextView txtTitle = (TextView) rowView.findViewById(id.texto_principal);
        ImageView imageView = (ImageView) rowView.findViewById(id.icon);
        TextView etxDescripcion = (TextView) rowView.findViewById(id.texto_secundario);
        ImageView imageViewFavorito = (ImageView) rowView.findViewById(id.favorito);

        txtTitle.setText(itemname[posicion]);
        imageView.setImageResource(integers[posicion]);
        etxDescripcion.setText("Description "+itemname[posicion]);
        imageViewFavorito.setImageResource(imagenes[posicion]);

        return rowView;
    }


}
