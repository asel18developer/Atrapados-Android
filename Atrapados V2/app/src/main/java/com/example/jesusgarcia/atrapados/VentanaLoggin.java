package com.example.jesusgarcia.atrapados;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import javax.mail.MessagingException;

public class VentanaLoggin extends AppCompatActivity implements OnClickListener{

    private EditText editText_usuario, editText_contrasena;
    private TextView olvido_contrasena;
    private Button button_registro, button_inicio_sesion;
    private  Toast notificacion;
    private Conector consultor;
    private Mail mail;
    private String nombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mostrarNotificacion(Constantes.BIENVENIDO);
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

        getMenuInflater().inflate(R.menu.menu_main, menu);
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

                mostrarAlerta(Constantes.AYUDA_VENTANA_LOGGIN);

                return true;

            case R.id.informacion_icon:

                mostrarAlerta(Constantes.AYUDA_INFO);

                return true;

            case R.id.ayuda_text:

                mostrarAlerta(Constantes.AYUDA_VENTANA_LOGGIN);

                return true;

            case R.id.informacion_text:

                mostrarAlerta(Constantes.AYUDA_INFO);


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
     * Metodo para capturar el click de los botones y asignarles una accion
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        if (v.getId() == button_registro.getId()){

            abrirVentanaRegistro();

        }else if (v.getId() == button_inicio_sesion.getId()){


            inicioSesion();


        }else  if (v.getId() == olvido_contrasena.getId()) {


            recuperarContraseña();


        }


        }

    /**
     *
     * Metodo que se utiliza cuando se pulsa el TextView olvido contraseña
     *
     * Metodo utilizado para recuperar la contraseña, se muestra un dialogo
     * para que el usuario introduzca su nombre de usuario
     *
     */
    private void recuperarContraseña() {


        indicaUsuario();


    }

    /**
     *
     * Metodo utilizado para asignarle la accion al boton Iniciar Sesion
     *
     * Realiza el inicio de sesion del usuario y la contraseña pasada,
     * muestra por pantalla si son datos correstos o no y si lo son
     * abre la ventana de usuario
     *
     */
    private void inicioSesion() {


        //Obtengo la informacion introducida
        String nombreUsuario = editText_usuario.getText().toString();
        String contrasenaUsuario = editText_contrasena.getText().toString();

        consultor.crearYabrirBaseDatos();//Abro y creo la base de datos

        /**
         *
         * Compruebo si el usuario esta registrado, este metodo me devuelve un string
         * indicando que el usuario esta logeado o no
         *
         */
        String comprobacion = consultor.comprobarUsuario(nombreUsuario,contrasenaUsuario);


        mostrarNotificacion(comprobacion);

        /**
         *
         * Si esta logeado abro una ventana de usuario con la informacion de dicho usuario
         * no necesito pasarle la informacion del usuario, ya que queda registrado en la
         * clase Conector, tiene un atributo usuario.
         *
         */
        if (comprobacion.equals(Constantes.USER_OK)){


            abrirVentanaUsuario();

        }

    }

    /**
     *
     * Metodo utilizado para mostrar una notificacion con el texto pasado
     *
     * @param textoPasado
     */
    private void mostrarNotificacion(String textoPasado) {

        notificacion = Toast.makeText(this,textoPasado,Toast.LENGTH_LONG);
        notificacion.show();

    }


    /**
     *
     * Cuando desde la ventana abierta con startActivityForResult se cierra
     * esta envia cierta informacion a esta, si la informacion se cumple
     * reseteo la ventana, para que no quede la información del usuario.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ((requestCode == Constantes.REQUEST_CODE) && (resultCode == RESULT_OK)){

            resetarVentana();

        }
    }


    /**
     * Metodo utilizado para abrir la ventana de registro
     */
    private void abrirVentanaRegistro() {

        Intent i = new Intent(VentanaLoggin.this, VentanaRegistro.class);
        i.putExtra(Constantes.VENTANA_REGISTRO, true);
        startActivity(i);

    }

    /**
     * Metodo utilizado para abrir la ventana de juego del Usuario
     */
    private  void abrirVentanaUsuario(){


        Intent i = new Intent(VentanaLoggin.this, VentanaUsuario.class);
        startActivityForResult(i, Constantes.REQUEST_CODE);



    }

    /**
     *
     * Metodo para resetear la ventana, ya que cuando el usuario vuelve hacia la pantalla
     * de loggin se borran los datos de sesion
     *
     */
    private void resetarVentana() {


        editText_contrasena.setText(Constantes.VACIO);
        editText_usuario.setText(Constantes.VACIO);


    }


    /**
     * Metodo que utilizamos para obtener toda la informacion necesaria de la ventana
     * de inicio de sesion y tambien recogemos los botones para capturar su pulsacion.
     */
    private void obtenerDatosVentana() {


        editText_usuario = (EditText) findViewById(R.id.editText_usuario_ventanaPrincipal);
        editText_contrasena = (EditText) findViewById(R.id.editText_contrasena_ventanaPrincipal);
        olvido_contrasena = (TextView) findViewById(R.id.textView_olvido_contraseña_ventanaPrincipal);
        button_registro = (Button) findViewById(R.id.button_registrase_ventanaPrincipal);
        button_inicio_sesion = (Button) findViewById(R.id.button_inicio_sesion_ventanaPrincipal);

        button_inicio_sesion.setOnClickListener(this);
        button_registro.setOnClickListener(this);
        olvido_contrasena.setOnClickListener(this);
        consultor = Conector.getConector(getBaseContext());


    }

    /**
     *
     * Metodo utilizado para que el usuario introduzca por pantalla, el nombre de usuario
     * para el cual quiere recuperar su contraseña.
     *
     * Para ello se abre un AlertDialog con un textEdit donde se introduce el nombre de
     * usuario, una vez introducido, se le envia la contraseña de dicho usuario, al correo
     * electronico asociado.
     *
     */
    public void indicaUsuario(){


        // Creo una alerta y le asigno el titulo y el mensaje que se ha de mostrar
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(Constantes.RECUPERACION_CONTRASEÑA);
        alert.setMessage(Constantes.INTRODUZCA_USUARIO);

        // Creo un edit text que le asigno a la alerta
        final EditText entradaTexto = new EditText(this);
        alert.setView(entradaTexto);

        //Le asigno un boton para enviar y su correspondiente accion
        alert.setPositiveButton(Constantes.OK, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                //Capturo el valor introducido por teclado y se lo asigno
                //a mi variable
                String cadenaIntroducida = entradaTexto.getText().toString();
                nombreUsuario = cadenaIntroducida;

                //Cuando el usuario acepta se envie el email
                new SendMail().execute();


            }
        });


        //Añado el boton de cancelar con su correspondiente acción
        alert.setNegativeButton(Constantes.CANCELAR,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        //Cierro el dialogo, mediante dismiss
                        dialog.dismiss();

                    }
                });


        // Una vez construido el dialogo con sus correspondientes acciones lo muestro
        alert.show();


    }




    /**
     *
     * Metodo utilizado para enviar un correo electronico con la contraseña, para ello
     * utilizamos el nombre de usuario
     */
    public void sendEmail(){


        //Inicializo mail con el correo desde el que quiero enviar la información
        mail = new Mail(Constantes.EMAIL, Constantes.EMAIL_PASS);

        //Debo comprobar que el usuario existe, esta registrado
        boolean usuarioRegistrado=mail.recuperacionContraseña(nombreUsuario);

        //Si esta registrado le envio el correo
        if (usuarioRegistrado){


            try {

                // Al ejecutar send puede lanzar excepcion, lo que indica que el correo no se ha
                // podido enviar correctamente


                mail.send();
                muestraNotificacion(Constantes.EMAIL_OK);


            } catch (MessagingException e) {

                muestraNotificacion(Constantes.EMAIL_ERROR);


            }


        }else{ //Si no esta registrado lo muestro por pantalla

            muestraNotificacion(Constantes.NO_EXISTE_USER);

        }


    }

    /**
     * Metodo utilizado para que la tarea en segundo plano pueda mostrar notificaciones,
     * ya que en android solo esta permitido que el hilo principal realice cambios en
     * la interfaz gráfica.
     *
     * Para poder acceder al hilo principal desde la tarea en segundo plano se utiliza
     * el metodo runOnOiThread(), mediante el cual paramos a ejecutar dicha parte
     * en el hilo principal
     *
     * Este metodo simplemente muestra una notificación por pantalla
     *
     * @param texto: Texto que se va a mostrar
     */
    private void muestraNotificacion(final String texto) {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                Toast.makeText(VentanaLoggin.this, texto, Toast.LENGTH_LONG).show();

            }
        });



    }

    /*
     *
     * Clase utilizada para realizar tareas en segundo plano, es similar a un hilo, pero
     * especial para android, para realizar la tarea en segundo plano hay que realizar
     * una llamada al metodo execute() de esta clase, el cual llama automaticamente a
     * doInBackground() por lo que la tarea que queremos realizar en segundo plano hay
     * hay que implementarla en dicho metodo.
     *
     */
    class SendMail extends AsyncTask<String, Integer, Void> {

        protected void doInBackground() {
            sendEmail();
        }

        protected void onProgressUpdate() {
            //Se utiliza cuando el metodo background esta realizando algun proceso
        }

        @Override
        protected Void doInBackground(String... params) {
            sendEmail();
            return null;
        }


        protected void onPreExecute() {
            //Se utiliza antes de que el metodo background se inicie
        }

        protected void onPostExecute() {
            //Se utiliza cuando el metodo background termina su ejecucion
        }
    }
}





