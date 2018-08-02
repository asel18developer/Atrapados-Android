package com.example.jesusgarcia.atrapados;

import android.app.Activity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VentanaJuego extends AppCompatActivity implements OnClickListener {

    private TextView textViewPregunta, textViewPuntuacion;
    private RadioButton radioButtonRespuesta1, radioButtonRespuesta2, radioButtonRespuesta3,
            radioButtonRespuesta4;
    private RadioGroup radioGroup;
    private Button buttonValidarPregunta, buttonComenzar;
    private Conector consultor;
    private ArrayList<Partida> listaPartidas;
    private Random generador;
    private Partida partidaObtenida;
    private Usuario usuario;
    private Thread hilo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_juego);

        textViewPregunta = (TextView) findViewById(R.id.textView_pregunta_ventanaJuego);
        textViewPuntuacion = (TextView) findViewById(R.id.textView_puntuacion_ventanaJuego);
        radioButtonRespuesta1 = (RadioButton) findViewById(R.id.radioButton_respuesta1_ventanaJuego);
        radioButtonRespuesta2 = (RadioButton) findViewById(R.id.radioButton_respuesta2_ventanaJuego);
        radioButtonRespuesta3 = (RadioButton) findViewById(R.id.radioButton_respuesta3_ventanaJuego);
        radioButtonRespuesta4 = (RadioButton) findViewById(R.id.radioButton_respuesta4_ventanaJuego);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup_ventanaJuego);
        buttonValidarPregunta = (Button) findViewById(R.id.button_validad_ventanaJuego);
        buttonComenzar = (Button) findViewById(R.id.button_comenzar_ventanaJuego);

        buttonValidarPregunta.setOnClickListener(this);
        buttonComenzar.setOnClickListener(this);

        consultor = Conector.getConector(getBaseContext());

        obtenerPartidas();

        usuario = consultor.getUsuario();


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
     * Metodo para mostrar la ayuda por la pantalla
     */
    private void mostrarAyuda() {


            mostrarAlerta(Constantes.AYUDA_VENTANA_JUEGO);

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
     * Metodo para utilziar una lista de las patidas de la base de datos
     */
    private void obtenerPartidas() {

        listaPartidas = consultor.obtenerPartidas();
    }

    /**
     * Metodo utilizado para cargar los datos de la partida en la ventana
     */
    private void cargarPartida() {

        textViewPuntuacion.setText(Constantes.PUNTUACION + usuario.getPuntos());

        if (listaPartidas.size()==0){

            listaPartidas=consultor.obtenerPartidas();

        }

        generador = new Random();
        int numeroGenerado = generador.nextInt(listaPartidas.size());

        partidaObtenida = listaPartidas.get(numeroGenerado);
        listaPartidas.remove(numeroGenerado);

        textViewPregunta.setText(partidaObtenida.getPregunta());

        desordenarRespuestas();


    }

    /**
     * Metodo para desordenar las respuestas y asi ponerlas de forma aletaria
     */
    private void desordenarRespuestas() {

        generador = new Random();
        List <String> respuestas = obtenerRespuestas();
        List<RadioButton> radioButtons = obtenerBotones();
        RadioButton radioButton_Aleatorio;

        while(respuestas.size()!=0){

            int numeroGenerado = generador.nextInt(respuestas.size());

            radioButton_Aleatorio=obtenerBotonAleatorio(radioButtons);
            radioButton_Aleatorio.setText(respuestas.get(numeroGenerado));

            respuestas.remove(numeroGenerado);

        }




    }

    /**
     * Metodo utilizado para obtener una lista de los botones de la ventana
     * @return
     */
    private List<RadioButton> obtenerBotones() {

        List<RadioButton> radioButtons= new ArrayList<RadioButton>();

        radioButtons.add(radioButtonRespuesta1);
        radioButtons.add(radioButtonRespuesta2);
        radioButtons.add(radioButtonRespuesta3);
        radioButtons.add(radioButtonRespuesta4);

        return radioButtons;

    }

    /**
     * Metodo para obtener una lista de las respuestas que tiene una partida
     * @return
     */
    private List<String> obtenerRespuestas() {
        List<String> respuestas = new ArrayList<String>();

        respuestas.add(partidaObtenida.getRespuestaCorrecta());
        respuestas.add(partidaObtenida.getRespuesta2());
        respuestas.add(partidaObtenida.getRespuesta3());
        respuestas.add(partidaObtenida.getRespuesta4());

        return respuestas;

    }

    /**
     * Metodo para obtener un boton de forma aleatoria de la lista pasada
     * @param radioButtons
     * @return
     */
    private RadioButton obtenerBotonAleatorio(List<RadioButton> radioButtons) {

        generador = new Random();
        int numeroGenerado = generador.nextInt(radioButtons.size());

        RadioButton radioButtonObtenido = radioButtons.get(numeroGenerado);
        radioButtons.remove(numeroGenerado);

        return radioButtonObtenido;
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == buttonValidarPregunta.getId()){

            validarPartida();

        }else if(v.getId() == buttonComenzar.getId()){

            buttonValidarPregunta.setVisibility(View.VISIBLE);
            buttonComenzar.setVisibility(View.INVISIBLE);
            radioGroup.setVisibility(View.VISIBLE);
            cargarPartida();

        }
        
        
    }

    /**
     * Metodo para mostrar un dialogo indicando que se debe seleccionar una de las respuestas
     * para poder validar la pregunta
     */
    private void mostrarDialogo() {

        Toast notificacion = Toast.makeText(this, Constantes.ESCOJA_RESPUESTA, Toast.LENGTH_LONG);

        notificacion.show();


    }


    /**
     * Metodo para capturar la pulsacion de salir de ventana y cambiar su comportmiento
     */
    @Override
    public void onBackPressed() {

        setResult(RESULT_OK, null);
        this.finish();

    }


    /*
     *
     * Metodo usado para capturar los casos en los que esta pantalla entra en estado pausada
     * en concreto lo utilizado para los casos en los que salgo de la aplicacion (Al pulsar
     * el boton de home o el de multitarea)
     *
     * En estos casos cierro la ventana de juego para que no se pueda buscar en internet la
     * la pregunta que se esta jugando
     *
     */
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    /**
     *
     * Metodo para validar la pregunta, el cual utiliza un hilo para que no se pierdan frames
     *
     */
    private void validarPartida() {


        hilo = new Thread(new Runnable() {

            RadioButton radioButtonPulsado = obtenerRadioButtonPulsado();

            public void run() {

                if (radioButtonPulsado!=null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            actualizarPuntos(radioButtonPulsado);

                        }
                    });

                    duermeHilo(hilo);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            resetearBotones();
                            cargarPartida();

                            radioButtonPulsado.setTextColor(Color.BLACK);

                        }
                    });

                }else{

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            mostrarDialogo();
                        }
                    });



                }
            }
        });

        hilo.start();



    }

    /**
     * Metodo para hacer dormir al hilo dos segundos
     * @param hilo
     */
    private void duermeHilo(Thread hilo) {

        try {
            hilo.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para acualizar los puntos del usuario restando 5 o sumando 10
     * @param radioButtonPulsado
     */
    private void actualizarPuntos(RadioButton radioButtonPulsado) {


        boolean respuestaCorrecta = comprobarRespuesta(radioButtonPulsado);

        if (respuestaCorrecta){


            usuario.setPuntos(usuario.getPuntos() + 10);
            radioButtonPulsado.setTextColor(Color.GREEN);


        }else{


            usuario.setPuntos(usuario.getPuntos()-5);
            radioButtonPulsado.setTextColor(Color.RED);


        }



        textViewPuntuacion.setText("Puntuación " + usuario.getPuntos());


        consultor.actualizaUsuario(usuario);




    }

    /**
     * Metodo que comprueba si la respuesta marcada es correcta
     * @param radioButtonPulsado
     * @return
     */
    private boolean comprobarRespuesta(RadioButton radioButtonPulsado) {

        if (radioButtonPulsado.getText().equals(partidaObtenida.getRespuestaCorrecta())){

            return true;

        }else{

           return false;

        }


    }

    /**
     *
     * Metodo para desmarcar el boton pulsado
     *
     */
    private void resetearBotones() {

        radioGroup.clearCheck();



    }

    /**
     *
     * Metodo para obtener el radio button pulsado
     *
     * @return
     */
    private RadioButton obtenerRadioButtonPulsado() {

        if (radioButtonRespuesta1.isChecked()){

            return radioButtonRespuesta1;

        }else if(radioButtonRespuesta2.isChecked()){

            return radioButtonRespuesta2;

        }else if(radioButtonRespuesta3.isChecked()){

            return radioButtonRespuesta3;

        }else if(radioButtonRespuesta4.isChecked()){

            return radioButtonRespuesta4;

        }else{

            return null;

        }


    }

}
