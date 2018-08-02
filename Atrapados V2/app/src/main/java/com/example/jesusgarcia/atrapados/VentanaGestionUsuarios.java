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


public class VentanaGestionUsuarios extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {



    private ListView listViewUsuarios;
    private int request_code = 1;
    private Button buttonAñadir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_gestion_usuarios);

        listViewUsuarios = (ListView) findViewById(R.id.listView_Usuarios);
        buttonAñadir = (Button) findViewById(R.id.button_añadir_ventanaGestionUsuarios);

        actualizarVentana();

        listViewUsuarios.setOnItemClickListener(this);
        buttonAñadir.setOnClickListener(this);



    }


    /**
     * Metodo para crear un menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_gestion_usuarios, menu);
        return super.onCreateOptionsMenu(menu);


    }

    /**
     *
     * Metodo para asignar una una accion a los items del menu
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

            case R.id.añadir_usuario_text:

                añadirUsuario();

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    /**
     * metodo para mostrar la ayuda de la ventana
     */
    private void mostrarAyuda() {

        mostrarAlerta(Constantes.AYUDA_VENTANA_GESTION_USUARIOS);

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
     * Metodo para capturar la pulsación del voton atras y asignarle una accion
     *
     */
    @Override
    public void onBackPressed() {

        setResult(RESULT_OK, null);
        this.finish();

    }

    /**
     *
     * Metodo para capturar cuando la ventana abierta por esta se cierra
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
     * Metodo para actualizar la ventana
     *
     */
    private void actualizarVentana() {

        Conector consultor = Conector.getConector(getBaseContext());
        ArrayList<Usuario> usuarios = consultor.obtenerUsuarios();


        UsuarioAdapter adapter = new UsuarioAdapter(this, usuarios);

        listViewUsuarios.setAdapter(adapter);

    }

    /**
     *
     * Metodo para capturar la pulsacion sobre un item y asignarle una accion
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        String nombreUsuario = ((Usuario) parent.getItemAtPosition(position)).getNombre_usuario();
        Intent i = new Intent(VentanaGestionUsuarios.this, VentanaRegistro.class);
        i.putExtra(Constantes.NOMBRE_USUARIO, nombreUsuario);
        i.putExtra(Constantes.ADMIN, true);
        i.putExtra(Constantes.ES_SUGERENCIA,true);
        startActivityForResult(i, request_code);


    }

    /**
     *
     * Metodo para capturar la pulsación de un boton y asignarle una acción
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        if (v.getId()==buttonAñadir.getId()){

            añadirUsuario();


        }

    }

    /**
     *
     * Metodo para añdir un suario nuevo a la base e datos
     *
     */
    private void añadirUsuario() {

        Intent i = new Intent(VentanaGestionUsuarios.this, VentanaRegistro.class);
        i.putExtra(Constantes.ADMIN, true);
        i.putExtra(Constantes.NOMBRE_USUARIO, Constantes.VACIA);
        startActivityForResult(i, request_code);

    }
}
