package com.example.jesusgarcia.atrapados;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class VentanaPartida extends AppCompatActivity implements View.OnClickListener {


    private EditText editTextPregunta, editTextRespuestaCorrecta, editTextRespuesta2,
            editTextRespuesta3, editTextRespuesta4;
    private Button buttonGuardar, buttonEliminar, buttonAñadir, buttonBuscar;
    private Conector consultor;
    private int idPartida;
    private boolean partidaNueva,esSugerencia;
    private Partida partidaPasada, partidaVentana;
    private Sugerencia sugerenciaPasada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_partida);

        consultor = Conector.getConector(getBaseContext());
        partidaNueva = getIntent().getExtras().getBoolean("partidaNueva");

        editTextPregunta = (EditText) findViewById(R.id.editText_pregunta_ventanaPartida);
        editTextRespuestaCorrecta = (EditText) findViewById(R.id.editText_respuestaCorrecta_ventanaPartida);
        editTextRespuesta2 = (EditText) findViewById(R.id.editText_respuesta2_ventanaPartida);
        editTextRespuesta3 = (EditText) findViewById(R.id.editText_respuesta3_ventanaPartida);
        editTextRespuesta4 = (EditText) findViewById(R.id.editText_respuesta4_ventanaPartida);

        buttonEliminar = (Button) findViewById(R.id.button_eliminar_ventanaPartida);
        buttonGuardar = (Button) findViewById(R.id.button_guardarCambios_ventanaPartida);
        buttonAñadir = (Button) findViewById(R.id.button_añadir_ventanaPartida);
        buttonBuscar = (Button) findViewById(R.id.button_buscar_ventanaPartida);

        buttonEliminar.setOnClickListener(this);
        buttonGuardar.setOnClickListener(this);
        buttonAñadir.setOnClickListener(this);
        buttonBuscar.setOnClickListener(this);

        mostrarPartidaPasada();




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

                mostrarAlerta(Constantes.AYUDA_VENTANA_PARTIDA);

                return true;

            case R.id.ayuda_text:

                mostrarAlerta(Constantes.AYUDA_VENTANA_PARTIDA);

                return true;


            default:
                return super.onOptionsItemSelected(item);

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
     * Metodo utilizado para poder utilizar esta ventana tanto para modificar como para añadir
     * partidas
     */
    private void mostrarPartidaPasada() {

        if (!partidaNueva) {

            obtenerPartidaCliclada();

            editTextPregunta.setText(partidaPasada.getPregunta());
            editTextRespuestaCorrecta.setText(partidaPasada.getRespuestaCorrecta());
            editTextRespuesta2.setText(partidaPasada.getRespuesta2());
            editTextRespuesta3.setText(partidaPasada.getRespuesta3());
            editTextRespuesta4.setText(partidaPasada.getRespuesta4());

        }else{

            buttonEliminar.setVisibility(View.INVISIBLE);
            buttonGuardar.setVisibility(View.INVISIBLE);
            buttonAñadir.setVisibility(View.VISIBLE);

        }


    }

    /**
     * Metodo para obtener informacion de la partida con el id pasado a la ventana
     */
    private void obtenerPartidaCliclada() {

        esSugerencia = getIntent().getExtras().getBoolean("esSugerencia");
        idPartida = getIntent().getExtras().getInt("idPartida");
        Conector consultor = Conector.getConector(getBaseContext());

        if(!esSugerencia) {


            partidaPasada = consultor.obtenerPartida(idPartida);


        }else{



            buttonGuardar.setVisibility(View.INVISIBLE);
            buttonAñadir.setVisibility(View.VISIBLE);
            buttonBuscar.setVisibility(View.VISIBLE);
            sugerenciaPasada = consultor.obtenerSugerencia(idPartida);

            partidaPasada = new Partida(sugerenciaPasada.getPregunta(),
                    sugerenciaPasada.getRespuesta(),Constantes.VACIA, Constantes.VACIA, Constantes.VACIA);

        }



    }

    /**
     *
     * Metodo para obtener la partida de la ventana
     *
     */
    private void obtenerPartidaVentana(){


        String pregunta = editTextPregunta.getText().toString();
        String respuestaCorrecta = editTextRespuestaCorrecta.getText().toString();
        String respuesta2 = editTextRespuesta2.getText().toString();
        String respuesta3 = editTextRespuesta3.getText().toString();
        String respuesta4 = editTextRespuesta4.getText().toString();


        if (partidaNueva) {

            partidaVentana = new Partida(pregunta, respuestaCorrecta, respuesta2, respuesta3,
                    respuesta4);

        }else{

            partidaVentana = new Partida(pregunta, respuestaCorrecta, respuesta2, respuesta3,
                    respuesta4, idPartida);
        }

    }


    /**
     * Metodo utilizado para asignar acciones a a la pulsacion de los botones
     * @param v
     */
    @Override
    public void onClick(View v) {

        if (v.getId() == buttonGuardar.getId()){

            actualizarPartida();

        }else if(v.getId() == buttonEliminar.getId()){


            if (esSugerencia){

                consultor.eliminarSugerencia(idPartida);

            }else {
                consultor.eliminarPartida(idPartida);
            }
            setResult(RESULT_OK, null);
            this.finish();



        }else if(v.getId() == buttonAñadir.getId()){


            añadirPartida();

        }else if(v.getId() == buttonBuscar.getId()){

            obtenerPartidaVentana();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(Constantes.NAVEGADOR+partidaVentana.getPregunta().toString()));
            this.startActivity(intent);

        }


    }

    /**
     * Metodo para añadir una aptida a la base de datos
     */
    private void añadirPartida() {

        obtenerPartidaVentana();

        if (partidaVentana.camposObligatorios()) {

            consultor.añadirPartida(partidaVentana);

            if (esSugerencia){

                consultor.eliminarSugerencia(sugerenciaPasada.getId());

            }

            setResult(RESULT_OK, null);
            this.finish();

        }else{

            mostrarAlerta();

        }

    }

    /**
     * Metodo utilizado para actualizar la informacion de la partida en la base de datos
     * si alguno de los datos es incorrecto lo muestra mediante una alerta
     */
    private void actualizarPartida() {

        obtenerPartidaVentana();

        if (partidaVentana.camposObligatorios()) {

            consultor.actualizarTablaPartida(partidaVentana);
            setResult(RESULT_OK, null);
            this.finish();

        }else{

            mostrarAlerta();

        }
    }

    /**
     * Metodo para mostrar una alerta indicando que rellene los campos obligatorios
     */
    private void mostrarAlerta() {

        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setMessage(Constantes.RELLENAR_CAMPOS);
        dialogo.setPositiveButton(android.R.string.ok,null);
        dialogo.show();

    }
}
