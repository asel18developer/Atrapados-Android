package com.example.jesusgarcia.atrapados;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class VentanaSugerencia extends AppCompatActivity  implements OnClickListener {

    private Button buttonEnviar;
    private EditText editTextPregunta, editTextRespuestaCorrecta;
    private String nombreUsuario, pregunta, respuesta;
    private Toast notificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_sugerencia);

        buttonEnviar = (Button)  findViewById(R.id.button_enviar_ventanaSugerencia);
        buttonEnviar.setOnClickListener(this);

        editTextRespuestaCorrecta = (EditText)  findViewById(R.id.editText_respuestaCorrecta_ventanaSugerencia);
        editTextPregunta = (EditText)  findViewById(R.id.editText_Pregunta_ventanaSugerencia);


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

                mostrarAlerta(Constantes.AYUDA_VENTANA_SUGERENCIA);

                return true;

            case R.id.ayuda_text:

                mostrarAlerta(Constantes.AYUDA_VENTANA_SUGERENCIA);

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
     *
     * Metodo utilizado para capturar las pulsaciones y asignarles una acción
     *
     * @param v
     */
    @Override
    public void onClick(View v) {


        if (v.getId() == buttonEnviar.getId()){

            pregunta = editTextPregunta.getText().toString();
            respuesta = editTextRespuestaCorrecta.getText().toString();

            if(comprobar()) {

                Conector conector = Conector.getConector(this.getBaseContext());
                nombreUsuario = conector.getUsuario().getNombre_usuario();
                Sugerencia sugerencia = new Sugerencia(pregunta, respuesta, nombreUsuario);

                conector.añadirSugerencia(sugerencia);

                notificacion = Toast.makeText(this, Constantes.SUGERENCIA_EXITO,
                        Toast.LENGTH_LONG);
                notificacion.show();

                close();

            }else{

                notificacion = Toast.makeText(this, Constantes.RELLENAR_CAMPOS,
                        Toast.LENGTH_LONG);
                notificacion.show();

            }

        }


    }

    /**
     *
     * Metodo para cerrar la ventana
     *
     */
    private void close() {

        this.finish();

    }

    /**
     *
     * Metodo que devuelve un boolean informando del estado de los campos obligatorios, si alguno
     * de los campos no esta rellenado devuelve False, si todos estan correctamente devuelve True
     *
     * @return
     */
    private boolean comprobar() {

        boolean camposVacios = true;

        if (pregunta.isEmpty() || respuesta.isEmpty()) {


            return !camposVacios;

        }

        return camposVacios;


    }


}
