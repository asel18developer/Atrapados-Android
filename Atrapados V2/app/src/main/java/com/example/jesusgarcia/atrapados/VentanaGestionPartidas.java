package com.example.jesusgarcia.atrapados;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class VentanaGestionPartidas extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener{

    private static final String PARAMETRO_PARTIDA_NUEVA = "partidaNueva";
    private static final String PARAMETRO_ID_PARTIDA = "idPartida";
    private static final int request_code = 1;

    private ListView listViewPartidas;
    private Button buttonAñadir;
    private Conector consultor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_partidas);

        listViewPartidas = (ListView) findViewById(R.id.listView_Partidas);
        buttonAñadir = (Button) findViewById(R.id.button_anadirPartida_ventanaGestionPartida);
        consultor = Conector.getConector(getBaseContext());

        actualizarVentana();

        listViewPartidas.setOnItemClickListener(this);
        buttonAñadir.setOnClickListener(this);

    }


    /**
     *
     * Metodo para crear el menu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_gestion_partidas, menu);
        return super.onCreateOptionsMenu(menu);


    }

    /**
     *
     * Metodo para asociar una accion a los items del menu
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


            case R.id.añadir_text:

                añadirPartida();

                return true;
            case R.id.eliminarPartidas_text:

                eliminarTodas();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    /**
     *
     * Metodo para mostrar la ayuda de la ventana
     *
     */
    private void mostrarAyuda() {

        mostrarAlerta(Constantes.AYUDA_VENTANA_GESTION_PARTIDAS);

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
     * Metodo para eliminar todas las partidas
     *
     */
    private void eliminarTodas() {

        consultor.eliminarPartidas();
        actualizarVentana();

    }


    /**
     *
     * Metodo para capturar la pulsación sobre un item de la lista y asociarle una acción
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        int identificadorPartida = ((Partida) parent.getItemAtPosition(position)).getId();
        Intent i = new Intent(VentanaGestionPartidas.this, VentanaPartida.class);
        i.putExtra(PARAMETRO_ID_PARTIDA, identificadorPartida);
        i.putExtra(PARAMETRO_PARTIDA_NUEVA, false);
        startActivityForResult(i, request_code);

    }

    /**
     *
     * Metodo utilizado para saber que la ventana abierta por esta se ha cerrado
     * y asociarle una accion a este cierre.
     *
     * Actuliza la ventana para tener la información actualizada en todo momento
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ((requestCode == request_code) && (resultCode == RESULT_OK)){

            actualizarVentana();

        }
    }

    /**
     *
     * Metodo para actualizar las modificaciones en las partidas
     *
     */
    public void actualizarVentana() {



        ArrayList<Partida> partidas = consultor.obtenerPartidas();

        PartidaAdapter adapter = new PartidaAdapter(this, partidas);

        listViewPartidas.setAdapter(adapter);


    }


    /**
     *
     * Metodo para capturar la pulsación de botones y asociarles una accion
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        if (v.getId()==buttonAñadir.getId()){

            añadirPartida();

        }

    }

    /**
     *
     * Metodo para añdir una partida a la base de datos
     *
     */
    private void añadirPartida() {

        Intent i = new Intent(VentanaGestionPartidas.this, VentanaPartida.class);
        i.putExtra(PARAMETRO_PARTIDA_NUEVA, true);
        startActivityForResult(i, request_code);

    }
}
