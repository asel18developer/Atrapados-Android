package com.example.jesusgarcia.atrapados;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class VentanaGestionSugerencias extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private static final String PARAMETRO_PARTIDA_NUEVA = "partidaNueva";
    private static final String PARAMETRO_ID_PARTIDA = "idPartida";
    private static final String PARAMETRO_ES_SUGERENCIA = "esSugerencia" ;

    private ListView listViewSugerencias;
    private Conector consultor;
    private int request_code=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_gestion_sugerencias);

        listViewSugerencias = (ListView) findViewById(R.id.listView_Sugerencias);

        consultor = Conector.getConector(getBaseContext());

        actualizarVentana();

        listViewSugerencias.setOnItemClickListener(this);


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

        getMenuInflater().inflate(R.menu.menu_gestion_sugerencias, menu);
        return super.onCreateOptionsMenu(menu);


    }

    /**
     *
     * Metodo utilizado para asociar una accion a los items del menu
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

            case R.id.eliminarSugerencias_text:

                eliminarTodas();

                return true;

            case R.id.añadir_text:



                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    /**
     *
     * Metodo utilizado para eliminar todas las sugerencias d
     *
     */
    private void eliminarTodas() {

        consultor.eliminarSugerencias();
        actualizarVentana();

    }

    /**
     *
     * Metodo para actualizar la ventana en de sugerencias con cualquier modificacion
     *
     */
    private void actualizarVentana() {

        ArrayList<Sugerencia> sugerencias = consultor.obtenerSugerencias();

        SugerenciaAdapter adapter = new SugerenciaAdapter(this, sugerencias);

        listViewSugerencias.setAdapter(adapter);

    }

    /**
     *
     * Metodo para mostrar la ayuda de la ventana
     *
     */
    private void mostrarAyuda() {

        mostrarAlerta(Constantes.AYUDA_VENTANA_GESTION_SUGERENCIAS);

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
     *
     * Metodo utilizado para saber cuando la ventana abierta se ha cerrado y asi poder actulizar
     * la información de las sugerencias.
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
     * Metodo utilizado para identificar el item seleccionado y asociarle una accion
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        int idSugerencia = ((Sugerencia) parent.getItemAtPosition(position)).getId();
        Intent i = new Intent(VentanaGestionSugerencias.this, VentanaPartida.class);

        i.putExtra(PARAMETRO_ID_PARTIDA, idSugerencia);
        i.putExtra(PARAMETRO_ES_SUGERENCIA, true);
        i.putExtra(PARAMETRO_PARTIDA_NUEVA, false);

        startActivityForResult(i, request_code);


    }
}
