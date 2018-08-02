package com.example.jesusgarcia.atrapados;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;


public class VentanaUsuario extends AppCompatActivity implements OnClickListener{



    private Usuario usuarioLogeado;
    private Conector consultor;
    private TextView textViewPuntos, textViewNombreUsuario;
    private Button button_jugar, button_sugerir,
    button_gestionUsuarios, button_gestionPartidas, button_gestionSugerencias;
    private String nombreUserLogeado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_user);

        obtenerDatosVentana();

    }

    /**
     *
     * Metodo para crear un menu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_ayuda, menu);
        return super.onCreateOptionsMenu(menu);


    }

    /**
     *
     * Metodo utilizado para asignar una accion a las opciones del menu
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.ayuda_icon:

                mostrarAyuda();

                return true;

            case R.id.ayuda_text:

                mostrarAyuda();

                return true;


            default:
                return super.onOptionsItemSelected(item);

        }

    }

    /**
     *
     * Metodo para mostrar la ayuda para esta ventana
     *
     */
    private void mostrarAyuda() {

        if (usuarioLogeado.getTipo() == 2) {


            mostrarAlerta(Constantes.AYUDA_VENTANA_ADMIN);

        } else {

            mostrarAlerta(Constantes.AYUDA_VENTANA_USUARIO);

        }

    }

    /**
     *
     * Metodo para mostrar una alerta con el texto pasado
     *
     * @param textoPasado
     */
    private void mostrarAlerta(String textoPasado) {

        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setMessage(textoPasado);
        dialogo.setPositiveButton(android.R.string.ok, null);
        dialogo.show();

    }

    /**
     *
     * Metodo para asignar a mis atributos los elementos de la ventana
     *
     */
    private void obtenerDatosVentana() {

        textViewPuntos = (TextView) findViewById(R.id.textView_Puntos_ventanaUser);
        textViewNombreUsuario = (TextView) findViewById(R.id.textView_nombreUser_ventanaUser);
        button_jugar = (Button) findViewById(R.id.button_jugar_ventanaUser);
        button_sugerir = (Button) findViewById(R.id.button_sugerir_ventanaUser);
        button_gestionPartidas =(Button) findViewById(R.id.button_gestion_partidas_ventanaUser);
        button_gestionUsuarios =(Button) findViewById(R.id.button_gestion_usuarios_ventanaUser);
        button_gestionSugerencias =(Button) findViewById(R.id.button_gestion_sugerencias_ventanaUser);

        button_jugar.setOnClickListener(this);
        button_sugerir.setOnClickListener(this);

        button_gestionPartidas.setOnClickListener(this);
        button_gestionUsuarios.setOnClickListener(this);
        button_gestionSugerencias.setOnClickListener(this);

        consultor = Conector.getConector(getBaseContext());
        usuarioLogeado = consultor.getUsuario();

        nombreUserLogeado = usuarioLogeado.getNombre_usuario();

        actualizarInfoUsuario();

    }

    /**
     *
     * Metodo utilizado para ac tualizar la información del usuario que muestro en esta ventana
     * que son el nombre y la puntuación, y si es administrador actualizo los botones que puede
     * ver.
     *
     */
    private void actualizarInfoUsuario() {

        textViewPuntos.setText(String.valueOf(usuarioLogeado.getPuntos()));

        textViewNombreUsuario.setText(Constantes.USUARIO + usuarioLogeado.getNombre_usuario());

        /*
        * Si es administrador
        */
        if (usuarioLogeado.getTipo()==2){

            button_gestionPartidas.setVisibility(View.VISIBLE);
            button_gestionSugerencias.setVisibility(View.VISIBLE);
            button_gestionUsuarios.setVisibility(View.VISIBLE);
        }

    }


    /**
     *
     * Metodo utilizado para capturar las respuestas de la ventana invocada, con este metodo
     * se si se ha cerrado la ventana abierta.
     *
     * Utilizo este metedo para tener la puntuación del usuario actualizada en todo momento.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ((requestCode == Constantes.REQUEST_CODE) && (resultCode == RESULT_OK)){

            usuarioLogeado = consultor.obtenerUsuario(nombreUserLogeado);
            actualizarInfoUsuario();
        
        }
    }


    /**
     *
     * Metodo utilizado para capturar la pulsación de la tecla retroceso
     * al hacer esto se cierra la ventana como debería hacer normalente, pero
     * a mayores envia un resultado a la ventana que lo abrio.
     *
     */
    @Override
    public void onBackPressed() {

        setResult(RESULT_OK, null);
        this.finish();

    }


    /**
     *
     * Metodo utilizado para capturar la pulsación y asociarle una actividad
     * a dicha pulsacion
     *
     * @param v
     */
    @Override
    public void onClick(View v) {



        if (v.getId() == button_jugar.getId()){

            Intent i = new Intent(VentanaUsuario.this, VentanaJuego.class);
            startActivityForResult(i,Constantes.REQUEST_CODE);


        }else if (v.getId() == button_sugerir.getId()){

            Intent i = new Intent(VentanaUsuario.this, VentanaSugerencia.class);
            startActivity(i);


        }else if(v.getId() == button_gestionPartidas.getId()){

            Intent i = new Intent(VentanaUsuario.this, VentanaGestionPartidas.class);
            startActivity(i);

        }else if(v.getId() == button_gestionUsuarios.getId()){

            Intent i = new Intent(VentanaUsuario.this, VentanaGestionUsuarios.class);
            startActivityForResult(i,Constantes.REQUEST_CODE);

        }else if(v.getId() == button_gestionSugerencias.getId()){

            Intent i = new Intent(VentanaUsuario.this, VentanaGestionSugerencias.class);
            startActivity(i);

        }
    }
}
