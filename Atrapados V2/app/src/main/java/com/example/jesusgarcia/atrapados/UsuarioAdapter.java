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
public class UsuarioAdapter extends AbstractAdapter<Usuario> {

    public UsuarioAdapter(Context context, List<Usuario> usuariosPasados) {

        super(context, usuariosPasados);
        this.myItems = usuariosPasados;

    }

    @Override
    public View getView(int posicion, View convertView, ViewGroup parent) {

        Usuario usuarioObtenido = myItems.get(posicion);

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);

        }


        // Lookup view for data population
        TextView nombreUsuario = (TextView) convertView.findViewById(R.id.textView_nombreUser_User);
        TextView nombre = (TextView) convertView.findViewById(R.id.textView_nombre_User);
        TextView apellidos = (TextView) convertView.findViewById(R.id.textView_apellidos_User);
        TextView email = (TextView) convertView.findViewById(R.id.textView_email_User);
        TextView pass = (TextView) convertView.findViewById(R.id.textView_contrasena_User);
        TextView puntuacion = (TextView) convertView.findViewById(R.id.textView_puntuacion_User);
        TextView tipo = (TextView) convertView.findViewById(R.id.textView_tipo_User);


        nombreUsuario.setText(usuarioObtenido.getNombre_usuario());
        nombre.setText(usuarioObtenido.getNombre());
        apellidos.setText(usuarioObtenido.getApellidos());
        email.setText(usuarioObtenido.getEmail());
        pass.setText(usuarioObtenido.getPass());
        puntuacion.setText(Integer.toString(usuarioObtenido.getPuntos()));
        tipo.setText(obtenerTipo(usuarioObtenido.getTipo()));



        return convertView;
    }

    private String obtenerTipo(int tipo) {

        String tipoString = "Usuario";

        if(tipo==2){

            tipoString="Administrador";
        }

        return tipoString;
    }
}
