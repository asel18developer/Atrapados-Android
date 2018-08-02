package com.example.jesusgarcia.atrapados;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jesusgarcia on 2/1/16.
 */
public class SugerenciaAdapter extends AbstractAdapter<Sugerencia> {


    public SugerenciaAdapter(Context context, List<Sugerencia> sugerenciasPasadas) {

        super(context, sugerenciasPasadas);
        this.myItems = sugerenciasPasadas;

    }


    @Override
    public View getView(int posicion, View convertView, ViewGroup parent) {

        Sugerencia sugerenciaObtenida = myItems.get(posicion);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_sugerencia, parent, false);
        }



        TextView id = (TextView) convertView.findViewById(R.id.textView_id_Sugerencia);
        TextView pregunta = (TextView) convertView.findViewById(R.id.textView_pregunta_Sugerencia);
        TextView respuesta = (TextView) convertView.findViewById(R.id.textView_respuesta_Sugerencia);
        TextView nombreUsuario = (TextView) convertView.findViewById(R.id.textView_nombreUsuario_Sugerencia);



        id.setText(Integer.toString(sugerenciaObtenida.getId()));
        pregunta.setText(sugerenciaObtenida.getPregunta());
        respuesta.setText(sugerenciaObtenida.getRespuesta());
        nombreUsuario.setText(sugerenciaObtenida.getNombreUsuario());


        return convertView;
    }
}
