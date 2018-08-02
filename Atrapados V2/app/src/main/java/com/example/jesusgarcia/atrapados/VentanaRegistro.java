package com.example.jesusgarcia.atrapados;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class VentanaRegistro extends AppCompatActivity implements View.OnClickListener{




    private EditText editText_nombre_usuario, editText_contrasena, editText_contrasena_comprobar,
            editText_email, editText_nombre_real, editText_apellidos, editText_puntos;
    private TextView textViewTitulo, textViewTipo,textViewPuntos;
    private Button button_registro, button_eliminar, button_guardar;
    private Toast notificacion;
    private Spinner spinner_tipo;
    private boolean userAdmin, añadir;
    private Usuario usuario_registrado;
    private String nombreUsuario, pass, nombre, apellidos, email, pass_verificacion;
    private int tipo, puntos;
    private Conector conector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        this.editText_nombre_usuario = (EditText) findViewById(R.id.editText_nombreUsuario_ventanaRegistro);
        this.editText_contrasena = (EditText) findViewById(R.id.editText_contrasena1_ventanaRegistro);
        this.editText_contrasena_comprobar = (EditText) findViewById(R.id.editText_contrasena2_ventanaRegistro);
        this.editText_puntos = (EditText) findViewById(R.id.editText_puntuacion_ventanaRegistro);
        this.button_registro = (Button) findViewById(R.id.boton_registro_ventanaRegistro);
        this.button_eliminar = (Button) findViewById(R.id.button_eliminar_ventanaRegistro);
        this.button_guardar = (Button) findViewById(R.id.button_guardar_ventanaRegistro);
        this.editText_email = (EditText) findViewById(R.id.editText_email_ventanaRegistro);
        this.editText_nombre_real = (EditText) findViewById(R.id.editText_nombre_ventanaRegistro);
        this.editText_apellidos = (EditText) findViewById(R.id.editText_apellidos_ventanaRegistro);
        this.spinner_tipo = (Spinner) findViewById(R.id.spinner_tipo_ventanaRegistro);
        this.textViewTitulo = (TextView) findViewById(R.id.textView_titulo_ventanaRegistro);
        this.textViewTipo = (TextView) findViewById(R.id.textView_tipoUser_ventanaRegistro);
        this.textViewPuntos = (TextView) findViewById(R.id.textView_puntuacion_ventanaRegistro);

        userAdmin = getIntent().getExtras().getBoolean(Constantes.ADMIN);
        añadir = true;

        this.button_registro.setOnClickListener(this);
        this.button_eliminar.setOnClickListener(this);
        this.button_guardar.setOnClickListener(this);

        conector = Conector.getConector(getBaseContext());

        cargarSpinner();

        StateRegistrarOModificar();

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

    private void mostrarAyuda() {

        mostrarAlerta(Constantes.AYUDA_VENTANA_REGISTRO);

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
     * Segun los datos pasados a la ventana modifico la ventana para que pueda usarse tanto para
     * añadir como para actualizar la informacion del usuario
     */
    private void StateRegistrarOModificar() {

        if (userAdmin){

            spinner_tipo.setVisibility(View.VISIBLE);
            textViewTipo.setVisibility(View.VISIBLE);
            textViewPuntos.setVisibility(View.VISIBLE);
            editText_puntos.setVisibility(View.VISIBLE);

            nombreUsuario = getIntent().getExtras().getString(Constantes.NOMBRE_USUARIO);

            if (!nombreUsuario.isEmpty()){

                añadir = false;
                textViewTitulo.setText(Constantes.MODIFICAR_USER);
                button_registro.setVisibility(View.INVISIBLE);
                button_guardar.setVisibility(View.VISIBLE);
                button_eliminar.setVisibility(View.VISIBLE);

                mostrarInfoUsuario();

            }

        }

    }

    /**
     *
     * Metodo utilizado cuando se quiere modificar la informacion de un usuario por lo tanto se
     * carga dicha informacion por pantalla.
     *
     */
    private void mostrarInfoUsuario() {

        Usuario usuarioPasado = conector.obtenerUsuario(nombreUsuario);

        editText_nombre_usuario.setText(usuarioPasado.getNombre_usuario());
        editText_nombre_usuario.setKeyListener(null);
        editText_nombre_real.setText(usuarioPasado.getNombre());
        editText_puntos.setText(Integer.toString(usuarioPasado.getPuntos()));
        editText_apellidos.setText(usuarioPasado.getApellidos());
        editText_email.setText(usuarioPasado.getEmail());
        editText_contrasena.setText(usuarioPasado.getPass());
        editText_contrasena_comprobar.setText(usuarioPasado.getPass());
        spinner_tipo.setSelection(usuarioPasado.getTipo() - 1);

    }

    /**
     *
     * Metodo utilizado para crear un spinner que permita seleccionar el tipo e usuario
     *
     */
    private void cargarSpinner() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.array_tipos_usuarios, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.spinner_tipo.setAdapter(adapter);


    }


    /**
     * Metodo utilizado para asignar una accion al click de un boton
     * @param v
     */
    @Override
    public void onClick(View v) {

        obtenerDatos();

        if (v.getId() == button_registro.getId()){

            añadirOActualizarUsuario();

        }else if(v.getId() == button_eliminar.getId()){


            conector.eliminarUsuario(nombreUsuario);


            setResult(RESULT_OK, null);
            close();

        }else if(v.getId() == button_guardar.getId()){

            añadirOActualizarUsuario();

        }




    }

    /**
     * Metodo utilizado para añadir o actualizar la información de usuario rellenada
     */
    private void añadirOActualizarUsuario() {

        String estado = datosCorrectos();

        notificacion = Toast.makeText(this,estado,Toast.LENGTH_LONG);
        notificacion.show();


        if(estado.equals(Constantes.USUARIO_CORRECTO)){

            usuario_registrado = new Usuario
                    (nombre, apellidos, email, nombreUsuario, pass, tipo, puntos);


            if (añadir){

                conector.añadirUsuario(usuario_registrado);

            }else{

                conector.actualizaUsuario(usuario_registrado);
            }


            setResult(RESULT_OK, null);
            close();


        }



    }

    /**
     * Metodo para cerrar la ventana
     */
    private void close() {

        this.finish();

    }

    /**
     *
     * Metodo para obetner los datos de la ventana introducidos
     *
     */
    private void obtenerDatos() {



        nombreUsuario = editText_nombre_usuario.getText().toString();
        pass = editText_contrasena.getText().toString();
        pass_verificacion = editText_contrasena_comprobar.getText().toString();
        nombre = editText_nombre_real.getText().toString();
        apellidos = editText_apellidos.getText().toString();
        email = editText_email.getText().toString();
        tipo = obtenerTipoSpinner();

        puntos = 0;

        String puntosString = editText_puntos.getText().toString();

        if (!puntosString.isEmpty()) {

            puntos = Integer.parseInt(puntosString);

        }


    }

    /**
     *
     * Metodo para obtener el tipo que se ha marcado en el spinner
     *
     * @return
     */
    private int obtenerTipoSpinner() {

        int tipo = 1;
        String tipo_string = spinner_tipo.getSelectedItem().toString();

        if (tipo_string.equals(Constantes.ADMINISTRADOR)){

            tipo = 2;

        }

        return tipo;

    }

    /**
     *
     * Metodo para comprobar que todos los campos se han rellenado de la forma correcta
     * y si no se devuelve la información correspondiente al error.
     *
     * @return
     */
    private String datosCorrectos() {

        String estado=Constantes.VACIA;


        if (nombreUsuario.isEmpty()) {

            estado="Introduce un nombre de usuario válido";

        }else if (email.isEmpty()) {

            estado="Introduce un email válido";

        }else if (nombre.isEmpty()) {

            estado="Introduce un nombre válido";

        }else if (apellidos.isEmpty()) {

            estado="Introduce unos apellidos válidos";

        }else if (pass.length()<6) {

            estado="Introduce una contraseña de más de 6 dígitos";

        }else if (!pass.equals(pass_verificacion)) {

            estado="Las contraseñas no coinciden";

        }else if (conector.existeUsuario(nombreUsuario) && añadir) {

            estado="Nombre de usuario no disponible";

        } else{

            estado = "Usuario correcto";

        }

        return estado;
    }


}

