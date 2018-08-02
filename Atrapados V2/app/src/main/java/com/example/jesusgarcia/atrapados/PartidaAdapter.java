package com.example.jesusgarcia.atrapados;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by jesusgarcia on 29/12/15.
 */
public class PartidaAdapter extends AbstractAdapter<Partida> {


    public PartidaAdapter(Context context, List<Partida> itemsPasados) {

        super(context, itemsPasados);
        this.myItems = itemsPasados;
    }

    public View getView(int posicion, View convertView, ViewGroup parent){

        Partida partidaObtenida = myItems.get(posicion);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_partida, parent, false);
        }


        // Lookup view for data population
        TextView id = (TextView) convertView.findViewById(R.id.textView_id_Partida);
        TextView pregunta = (TextView) convertView.findViewById(R.id.textView_pregunta_Partida);
        TextView respuestaCorrecta = (TextView) convertView.findViewById(R.id.textView_respuestaCorrecta_Partida);
        TextView respuesta2 = (TextView) convertView.findViewById(R.id.textView_respuesta2_Partida);
        TextView respuesta3 = (TextView) convertView.findViewById(R.id.textView_respuesta3_Partida);
        TextView respuesta4 = (TextView) convertView.findViewById(R.id.textView_respuesta4_Partida);


        id.setText(Integer.toString(partidaObtenida.getId()));
        pregunta.setText(partidaObtenida.getPregunta());
        respuestaCorrecta.setText(partidaObtenida.getRespuestaCorrecta());
        respuesta2.setText(partidaObtenida.getRespuesta2());
        respuesta3.setText(partidaObtenida.getRespuesta3());
        respuesta4.setText(partidaObtenida.getRespuesta4());
        // Return the completed view to render on screen
        return convertView;

    }



}
