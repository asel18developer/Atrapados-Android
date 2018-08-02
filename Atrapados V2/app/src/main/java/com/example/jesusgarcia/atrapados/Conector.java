package com.example.jesusgarcia.atrapados; /**
 * Created by jesusgarcia on 11/11/15.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import java.lang.String;
import java.util.ArrayList;

public class Conector extends  SQLiteOpenHelper{


    private static Usuario usuario;


    /*********************************************************************************************/
    /*                                                                                           */
    /*                             Atributos de la clase Conector                                */
    /*                                                                                           */
    /*********************************************************************************************/

    private Context myContext;
    private SQLiteDatabase myDataBase;
    private static Conector myConector;


    private Conector(Context context) {

        super(context, Constantes.DATABASE_NAME, null, Constantes.DATABASE_VERSION);

        this.myContext = context;

    }

    public static synchronized Conector getConector(Context context) {

        if (myConector == null) {

            myConector = new Conector(context);
        }

        return myConector;
    }


    public static Usuario getUsuario() {

        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        Conector.usuario = usuario;
    }

    /**
     *
     * No necesita de implementacion ya que se utiliza cuando no cargamos una base de datos
     * simplemente se utilizaria para crearla mediante codigo.
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) { }

    /**
     *
     * No necesita de implementacion ya que se utiliza cuando no cargamos una base de datos
     * simplemente se utilizaria para actualizar la version.
     *
     * @param db
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {	}


    private void crearBaseDeDatos() throws IOException {

        boolean dbExist = comprobarBaseDeDatos();

        if (dbExist) {
            // Si existe, no haemos nada!
        } else {
            // Llamando a este método se crea la base de datos vacía en la ruta
            // por defecto del sistema de nuestra aplicación por lo que
            // podremos sobreescribirla con nuestra base de datos.
            this.getReadableDatabase();

            try {

                copiarBaseDeDatos();

            } catch (IOException e) {

                throw new Error(Constantes.ERROR_COPIADO);
            }
        }
    }

    /**
     *
     * Metodo para comprobar que la base de datos esta en estado correcto
     *
     * @return
     */
    private boolean comprobarBaseDeDatos() {

        SQLiteDatabase checkDB = null;

        try {

            checkDB = SQLiteDatabase.openDatabase(Constantes.MYPATH, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {
            // Base de datos no creada todavia
        }

        if (checkDB != null) {

            checkDB.close();
        }

        return checkDB != null ? true : false;

    }

    /**
     *
     * Metodo para abrir la base de datos
     *
     * @throws SQLException
     */
    private void abrirBaseDeDatos() throws SQLException {

        // Open the database
        myDataBase = SQLiteDatabase.openDatabase(Constantes.MYPATH, null, SQLiteDatabase.OPEN_READONLY);

    }


    /**
     * Metodo para cerrar la base de datos utilizada
     */
    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();
    }


    /**
     *
     * Metodo que comprueba que un usuario y contraseña estan correctamente asociados
     * devolviendo un string informando de si esta asociacion es correcta o incorrecta.
     *
     * @param usuario
     * @param contrasena
     * @return
     */
    public String comprobarUsuario(String usuario, String contrasena){

        String resultado;

        myDataBase = this.getReadableDatabase();

        Cursor miCursor = myDataBase.rawQuery(Constantes.QUERY_COMPROBAR_USER, new String[]{usuario});

        miCursor.moveToFirst();

        try {

            if (miCursor.getString(0).equals(contrasena)){

                obtenerUsuario(usuario);
                resultado = Constantes.USER_OK;


            }else {

                resultado = Constantes.USER_ERROR;

            }

        }catch (Exception e){

            resultado = Constantes.USER_NO_REGISTRADO;
        }

        return resultado;

    }

    /**
     *
     * Metodo para copiar la base de datos
     *
     * @throws IOException
     */
    private void copiarBaseDeDatos() throws IOException {

        OutputStream databaseOutputStream = new FileOutputStream(Constantes.VACIA +
                Constantes.DATABASE_FOLDER + Constantes.DATABASE_NAME);
        InputStream databaseInputStream;

        byte[] buffer = new byte[1024];
        int length;

        databaseInputStream = myContext.getAssets().open(Constantes.DATABASE_NAME);
        while ((length = databaseInputStream.read(buffer)) > 0) {
            databaseOutputStream.write(buffer);
        }

        databaseInputStream.close();
        databaseOutputStream.flush();
        databaseOutputStream.close();
    }


    /**
     *
     * Metodo para añadir un usuario a la base de datos
     *
     * @param usuario_registrado
     */
    public void añadirUsuario(Usuario usuario_registrado) {

        myDataBase = this.getWritableDatabase();

        ContentValues nuevoRegistro = new ContentValues();

        nuevoRegistro.put("email",usuario_registrado.getEmail());
        nuevoRegistro.put("pass",usuario_registrado.getPass());
        nuevoRegistro.put("puntos",usuario_registrado.getPuntos());
        nuevoRegistro.put("tipo",usuario_registrado.getTipo());
        nuevoRegistro.put("apellidos",usuario_registrado.getApellidos());
        nuevoRegistro.put("nombre", usuario_registrado.getNombre());
        nuevoRegistro.put("nombre_usuario", usuario_registrado.getNombre_usuario());


        //Insertamos el registro en la base de datos
        myDataBase.insert("usuario", null, nuevoRegistro);



    }

    /**
     *
     * Metodo para añadir una sugerencia a la base de datos
     *
     * @param sugerencia
     */
    public void añadirSugerencia(Sugerencia sugerencia) {

        myDataBase = this.getWritableDatabase();

        ContentValues nuevoRegistro = new ContentValues();
        nuevoRegistro.put("pregunta",sugerencia.getPregunta());
        nuevoRegistro.put("respuesta",sugerencia.getRespuesta());
        nuevoRegistro.put("nombre_usuario",usuario.getNombre_usuario());


        //Insertamos el registro en la base de datos
        myDataBase.insert("sugerencia", null, nuevoRegistro);



    }

    /**
     *
     * Metodo para añadir una apartida a la base de datos
     *
     * @param partida
     */
    public void añadirPartida(Partida partida) {

        myDataBase = this.getWritableDatabase();

        ContentValues nuevoRegistro = new ContentValues();
        nuevoRegistro.put("pregunta", partida.getPregunta());
        nuevoRegistro.put("respuesta_correcta",partida.getRespuestaCorrecta());
        nuevoRegistro.put("respuesta2",partida.getRespuesta2());
        nuevoRegistro.put("respuesta3",partida.getRespuesta3());
        nuevoRegistro.put("respuesta4",partida.getRespuesta4());

        //Insertamos el registro en la base de datos
        myDataBase.insert("partida", null, nuevoRegistro);



    }


    /**
     *
     * Metodo para devolver una lista con todas las sugerencias
     *
     * @return
     */
    public ArrayList<Sugerencia> obtenerSugerencias(){

        ArrayList<Sugerencia> listaSugerencias = new ArrayList<Sugerencia>();

        Cursor miCursor = obtenerCursorSugerencias();


        while (miCursor.moveToNext()){

            String pregunta = miCursor.getString(miCursor.getColumnIndex("pregunta"));
            String respuesta = miCursor.getString(miCursor.getColumnIndex("respuesta"));
            String nombre_usuario = miCursor.getString(miCursor.getColumnIndex("nombre_usuario"));
            int id = miCursor.getInt(miCursor.getColumnIndex("id"));



            Log.i("LogsAndroid", "Probando sugerencias");


            Sugerencia sugerencia = new Sugerencia(pregunta,respuesta,nombre_usuario,id);

            listaSugerencias.add(sugerencia);


        }

        miCursor.close();


        return listaSugerencias;

    }



    /**
     *
     * Metodo para devolver una lista con todos los usuarios
     *
     * @return
     */
    public ArrayList<Usuario> obtenerUsuarios(){

        ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();

        Cursor miCursor = obtenerCursorUsuarios();


        while (miCursor.moveToNext()){

            String nombreUsuario = miCursor.getString(miCursor.getColumnIndex("nombre_usuario"));
            String nombre = miCursor.getString(miCursor.getColumnIndex("nombre"));
            String apellidos = miCursor.getString(miCursor.getColumnIndex("apellidos"));
            String email = miCursor.getString(miCursor.getColumnIndex("email"));
            String contrasena = miCursor.getString(miCursor.getColumnIndex("pass"));
            int puntos = miCursor.getInt(miCursor.getColumnIndex("puntos"));
            int tipo = miCursor.getInt(miCursor.getColumnIndex("tipo"));


            Log.i("LogsAndroid", "Probando usuarios");


            Usuario usuario = new Usuario(nombre, apellidos, email, nombreUsuario, contrasena,tipo,
                    puntos);

            listaUsuarios.add(usuario);


        }

        miCursor.close();


        return listaUsuarios;


    }


    /**
     *
     * Metodo para devolver una lista con todas las partidas
     *
     * @return
     */
    public ArrayList<Partida> obtenerPartidas(){

        ArrayList<Partida> listaPartidas = new ArrayList<Partida>();

        Cursor miCursor = obtenerCursorPartidas();
        //miCursor.moveToFirst();

        while (miCursor.moveToNext()){

            int id = miCursor.getInt(miCursor.getColumnIndex("id"));
            String pregunta = miCursor.getString(miCursor.getColumnIndex("pregunta"));
            String respuesta_correcta = miCursor.getString(miCursor.getColumnIndex("respuesta_correcta"));
            String respuesta2 = miCursor.getString(miCursor.getColumnIndex("respuesta2"));
            String respuesta3 = miCursor.getString(miCursor.getColumnIndex("respuesta3"));
            String respuesta4 = miCursor.getString(miCursor.getColumnIndex("respuesta4"));

            Log.i("LogsAndroid", "Probando partidas");

            Partida partida = new Partida(pregunta, respuesta_correcta, respuesta2, respuesta3, respuesta4, id);

            listaPartidas.add(partida);


        }

        miCursor.close();


        return listaPartidas;


    }

    /**
     *
     * Metodo para devolver un cursor de las sugerencias de la base de datos
     *
     * @return
     */
    private Cursor obtenerCursorSugerencias() {


        myDataBase = this.getReadableDatabase();

        return myDataBase.query("sugerencia",new String[]{"id","pregunta", "respuesta",
                "nombre_usuario"}, null, null, null, null, "id");

    }

    /**
     *
     * Metodo para devolver un cursor de las partidas de la base de datos
     *
     * @return
     */
    public Cursor obtenerCursorPartidas() {


        myDataBase = this.getReadableDatabase();

        return myDataBase.query("partida",new String[]{"id","pregunta", "respuesta_correcta",
        "respuesta2","respuesta3", "respuesta4"}, null, null, null, null, "id");



    }


    /**
     *
     * Metodo para devolver un cursor de los usuarios de la base de datos
     *
     * @return
     */
    public Cursor obtenerCursorUsuarios() {

        myDataBase = this.getReadableDatabase();

        return myDataBase.query("usuario",new String[]{"nombre_usuario","email", "nombre",
                "apellidos","pass", "tipo", "puntos"}, null, null, null, null, "nombre_usuario");

    }


    public void crearYabrirBaseDatos(){


        try {

            crearBaseDeDatos();

        } catch (IOException e) {

            e.printStackTrace();

        }

        abrirBaseDeDatos();

    }

    /**
     *
     * Metodo que devuelve un boolean comprobando la existencia de ese nombre de usuario
     * en la base de datos
     *
     * @param nombreUsuario
     * @return
     */
    public boolean existeUsuario(String nombreUsuario) {

        boolean existe;

        myDataBase = this.getReadableDatabase();

        Cursor miCursor = myDataBase.rawQuery(Constantes.QUERY_COMPROBAR_EXISTENCIA,
                new String[]{nombreUsuario});

        miCursor.moveToFirst();

        try {

            if (miCursor.getString(0).equals(nombreUsuario)){


                existe=true;


            }else{

                existe=false;

            }

        }catch (Exception e){

            existe=false;

        }

        miCursor.close();
        myDataBase.close();

        return existe;



    }


    /**
     *
     * Metodo que devuelve la partida asociada a ese id
     *
     * @param idPartida
     * @return
     */
    public Partida obtenerPartida(int idPartida){

        Partida partidaDevolver;

        myDataBase = this.getReadableDatabase();
        String idString = Integer.toString(idPartida);

        Cursor miCursor = myDataBase.rawQuery(Constantes.QUERY_OBTENER_PARTIDA,
                new String[]{idString});

        miCursor.moveToFirst();

        String pregunta = miCursor.getString(miCursor.getColumnIndex("pregunta"));
        String respuestaCorrecta = miCursor.getString(miCursor.getColumnIndex("respuesta_correcta"));
        String respuesta2 = miCursor.getString(miCursor.getColumnIndex("respuesta2"));
        String respuesta3 = miCursor.getString(miCursor.getColumnIndex("respuesta3"));
        String respuesta4 = miCursor.getString(miCursor.getColumnIndex("respuesta4"));
        int id = miCursor.getInt(miCursor.getColumnIndex("id"));

        miCursor.close();
        myDataBase.close();

        partidaDevolver = new Partida(pregunta,respuestaCorrecta,respuesta2,respuesta3
        ,respuesta4,id);

        return partidaDevolver;

    }


    /**
     *
     * Metodo que devuelve el usuario asociado a dicho nombre de usuario
     *
     * @param nombreUsuario
     * @return
     */
    public Usuario obtenerUsuario(String nombreUsuario) {

        myDataBase = this.getReadableDatabase();

        Cursor miCursor = myDataBase.rawQuery(Constantes.QUERY_OBTENER_USUARIO, new String[]{nombreUsuario});

        miCursor.moveToFirst();

        String nombre = miCursor.getString(miCursor.getColumnIndex("nombre"));
        String apellidos = miCursor.getString(miCursor.getColumnIndex("apellidos"));
        String email = miCursor.getString(miCursor.getColumnIndex("email"));
        int puntos = miCursor.getInt(miCursor.getColumnIndex("puntos"));
        String contrasena = miCursor.getString(miCursor.getColumnIndex("pass"));
        int tipo = miCursor.getInt(miCursor.getColumnIndex("tipo"));


        usuario = new Usuario(nombre , apellidos, email, nombreUsuario, contrasena,
                tipo, puntos);

        myDataBase.close();
        miCursor.close();

        Log.i("LogsAndroid", usuario.getNombre_usuario() + " ");

        return usuario;

    }

    /**
     *
     * Metodo utilizado para cambiar el usuario
     *
     * @param _usuario
     */
    public void actualizaUsuario(Usuario _usuario) {

        usuario.setPuntos(_usuario.getPuntos());
        usuario.setApellidos(_usuario.getApellidos());
        usuario.setEmail(_usuario.getEmail());
        usuario.setNombre(_usuario.getNombre());
        usuario.setNombre_usuario(_usuario.getNombre_usuario());
        usuario.setPass(_usuario.getPass());
        usuario.setTipo(_usuario.getTipo());
        
        actualizarTablaUsuario();


    }


    /**
     *
     * Metodo utilizado para actualizar al usuario en la tabla
     *
     */
    private void actualizarTablaUsuario() {


        myDataBase = this.getWritableDatabase();


        ContentValues valores = new ContentValues();
        valores.put("nombre", usuario.getNombre());
        valores.put("nombre_usuario", usuario.getNombre_usuario());
        valores.put("pass", usuario.getPass());
        valores.put("email", usuario.getEmail());
        valores.put("puntos", usuario.getPuntos());
        valores.put("tipo", usuario.getTipo());
        valores.put("apellidos", usuario.getApellidos());


        myDataBase.update("usuario", valores, "nombre_usuario='" + usuario.getNombre_usuario() + "'", null);

        myDataBase.close();
    }

    /**
     *
     * Metodo para eliminar la partida con el id asociado
     *
     * @param idPartida
     */
    public void eliminarPartida(int idPartida) {

        myDataBase = this.getWritableDatabase();

        myDataBase.delete("partida", "id=" + idPartida, null);

        myDataBase.close();

    }


    /**
     * Metodo utilizado para actualizar la partida pasada en la base de datos
     * @param partida
     */
    public void actualizarTablaPartida(Partida partida) {

        myDataBase = this.getWritableDatabase();


        ContentValues valores = new ContentValues();
        valores.put("pregunta", partida.getPregunta());
        valores.put("respuesta_correcta", partida.getRespuestaCorrecta());
        valores.put("respuesta2", partida.getRespuesta2());
        valores.put("respuesta3", partida.getRespuesta3());
        valores.put("respuesta4", partida.getRespuesta4());


        myDataBase.update("partida", valores, "id=" + partida.getId(), null);

        myDataBase.close();
    }

    /**
     *
     * Metodo utilzido para eliminar el usuario con el nombre de usuario asoaciado
     *
     * @param nombreUsuario
     */
    public void eliminarUsuario(String nombreUsuario) {

        myDataBase = this.getWritableDatabase();

        myDataBase.delete("usuario", "nombre_usuario= '" + nombreUsuario + "'", null);

        myDataBase.close();
    }

    /**
     *
     * Metodo para eliminar todas las aprtidas
     *
     */
    public void eliminarPartidas() {

        myDataBase = this.getWritableDatabase();

        myDataBase.delete("partida", null, null);

        myDataBase.close();

    }

    /**
     *
     * Metodo utilizado para eliminar las sugerencias
     *
     */
    public void eliminarSugerencias() {

        myDataBase = this.getWritableDatabase();

        myDataBase.delete("sugerencia", null , null);

        myDataBase.close();

    }


    /**
     *
     * Metodo utilziado para obtener la sugerencia asociada a dicho id
     *
     * @param idSugerencia
     * @return
     */
    public Sugerencia obtenerSugerencia(int idSugerencia) {

        Sugerencia sugerenciaObtenida;

        myDataBase = this.getReadableDatabase();

        String idString = Integer.toString(idSugerencia);

        Cursor miCursor = myDataBase.rawQuery(Constantes.QUERY_OBTENER_SUGERENCIA,
                new String[]{idString});

        miCursor.moveToFirst();

        String pregunta = miCursor.getString(miCursor.getColumnIndex("pregunta"));
        String respuestaCorrecta = miCursor.getString(miCursor.getColumnIndex("respuesta"));
        String nombreUsuario = miCursor.getString(miCursor.getColumnIndex("nombre_usuario"));


        miCursor.close();
        myDataBase.close();

        sugerenciaObtenida= new Sugerencia(pregunta,respuestaCorrecta,nombreUsuario,idSugerencia);

        return sugerenciaObtenida;

    }


    /**
     * 
     * Metodo utilizado para eliminar una sugerencia de la base de datos, 
     * para realizar esta operacion, utilizo el parametro idSugerencia
     * con el cual encuentro la sugerencia que quiero eliminar, ya que
     * este es el identificador de las preguntas
     * 
     * Eliminar la sugerencia de la base de datos que tenga el id pasado
     * 
     * @param idSugerencia
     */
    public void eliminarSugerencia(int idSugerencia) {

        myDataBase = this.getWritableDatabase();

        myDataBase.delete("sugerencia", "id=" + idSugerencia, null);

        myDataBase.close();
    }

    /**
     *
     * Metodo utilizado para obtener la contrasela asociada a un usuario
     *
     * @param nombreUsuario
     * @return
     */
    public String getContraseña(String nombreUsuario) {

        String contraseñaRecuperada=Constantes.VACIA;

        myDataBase = this.getReadableDatabase();

        Cursor miCursor = myDataBase.rawQuery(Constantes.QUERY_RECUPERAR_CONTRASEÑA,
                new String[]{nombreUsuario});

        miCursor.moveToFirst();

        try {

            contraseñaRecuperada=miCursor.getString(miCursor.getColumnIndex("pass"));


        }catch (Exception e){

            Log.w("Clase Conector", "Error al acceder a la contraseña del correo pasado");

        }

        miCursor.close();
        myDataBase.close();

        return contraseñaRecuperada;

    }

    /**
     *
     * Metodo utilizado para obtener el email asociado a un nombre de usario
     *
     * @param nombreUsuario
     * @return
     */
    public String obtenerEmail(String nombreUsuario) {

        String email = Constantes.VACIA;

        myDataBase = this.getReadableDatabase();

        Cursor miCursor = myDataBase.rawQuery(Constantes.QUERY_OBTENER_EMAIL,
                new String[]{nombreUsuario});

        miCursor.moveToFirst();

        try {

            email=miCursor.getString(miCursor.getColumnIndex("email"));


        }catch (Exception e){

            Log.w("Clase Conector", "Error al acceder al email del usuario pasado");

        }

        miCursor.close();
        myDataBase.close();

        return email;
    }
}



