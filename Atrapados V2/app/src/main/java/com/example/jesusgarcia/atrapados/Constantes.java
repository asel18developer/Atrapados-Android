package com.example.jesusgarcia.atrapados;

/**
 * Created by jesusgarcia on 23/1/16.
 */
public class Constantes {

    //CLASE UTILIZADA PARA OCULTAR LA COMPLEJIDAD

    /*********************************************************************************************/
    /*                                                                                           */
    /*                                Constantes del proyecto                                    */
    /*                                                                                           */
    /*********************************************************************************************/

    public static final String QUERY_COMPROBAR_USER = "SELECT pass FROM usuario " +
            "WHERE nombre_usuario = ? ";
    public static final String QUERY_COMPROBAR_EXISTENCIA = "SELECT nombre_usuario " +
            "FROM usuario WHERE nombre_usuario = ? ";
    public static final String QUERY_RECUPERAR_CONTRASEÑA = "SELECT pass " +
            "FROM usuario WHERE nombre_usuario = ? ";
    public static final String QUERY_OBTENER_EMAIL = "SELECT email " +
            "FROM usuario WHERE nombre_usuario = ? ";
    public static final String DATABASE_FOLDER = "/data/data/com.example.jesusgarcia.atrapados" +
            "/databases/";
    public static final String DATABASE_NAME = "Atrapados.sqlite";
    public static final String MYPATH = DATABASE_FOLDER+DATABASE_NAME;
    public static final int DATABASE_VERSION = 3;
    public static final String QUERY_OBTENER_USUARIO = "SELECT nombre_usuario, nombre, apellidos" +
            ", email, puntos, pass, tipo FROM usuario WHERE nombre_usuario = ? ";

    public static final String QUERY_OBTENER_PARTIDA = "SELECT id, pregunta, respuesta_correcta" +
            ", respuesta2, respuesta3, respuesta4 FROM partida WHERE id = ? ";
    public static final String QUERY_OBTENER_SUGERENCIA = "SELECT id, pregunta, respuesta" +
            ",nombre_usuario FROM sugerencia WHERE id = ? ";
    public static final String ERROR_COPIADO = "Error copiando database";
    public static final String VACIA = "";

    public static final String USER_OK = "Usuario logeado";
    public static final String USER_ERROR = "Usuario/Contraseña erroneo";
    public static final String USER_NO_REGISTRADO ="Usuario no registrado";



    public static final String RECUPERACION_CONTRASEÑA = "Recuperación de contraseña";

    public static final String VACIO = "";
    public static final String ESCOJA_RESPUESTA = "Debe seleccionar una respuesta, para continuar.";

    public static final String BIENVENIDO = "Bienvenido a Atrapados";
    public static final int REQUEST_CODE = 1;
    public static final String EMAIL = "atrapados.servicio.tecnico@gmail.com";
    public static final String EMAIL_PASS = "atrapados";
    public static final String NO_EXISTE_USER = "No existe ningun usuario con ese nombre.";
    public static final String EMAIL_ERROR = "Ha ocurrido un error al enviar el correo";
    public static final String EMAIL_OK = "Le llegara un correo electronico";
    public static final String OK = "Enviar";
    public static final String CANCELAR = "Cancelar";
    public static final String INTRODUZCA_USUARIO = "Por favor introduzca el nombre de su usuario";
    public static final String ADMINISTRADOR  = "Administrador";
    public static final String ADMIN = "Admin";
    public static final String VENTANA_REGISTRO = "VentanaRegistro";
    public static final String AYUDA_VENTANA_LOGGIN = "Ayuda para la ventana de inicio de sesión\n\n" +
            "A) Puedes iniciar sesión introduciendo el nombre de usuario y contraseña correspondiente, " +
            "y pulsando el boton de incio de sesion, si los datos son incorrestos se informara " +
            "al igual que si no se rellena alguno de los campos." +
            "\n\nB) Puedes registrarte si no dispones de usuario o quieres crearte uno nuevo, pulsando" +
            "el boton de registrarse. Se abrira la ventana de registro\n\n" +
            "C) Puedes recuperar la contraseña, para ello pulsa encima de olvide contraseña, te pedira" +
            " que introduzcas el nombre de usuario, para el cual has olvidado la contraseña, y se enviara" +
            " automaticamente un email al correo asociado al usuario con la contraseña de este.";
    public static final String AYUDA_INFO = "Nombre: Atrapados\nAño: 2016\nDesarrolladores: " +
            "Jesús García Potes y Cesar Guitiérrez Pérez";
    public static final String AYUDA_VENTANA_PARTIDA = "Ayuda para la ventana de registro de partidas" +
            "\n\n Para registrar una partida hay que rellenar todos los campos y pulsar el boton" +
            " añadir, automaticamente ya quedara registrada en la base de datos.";
    public static final String AYUDA_VENTANA_REGISTRO = "Ayuda para registrarse como usuario" +
            "\n\nEs necesario rellenar todos los campos y de forma correcta: \n\n" +
            "A) La contraseña debe contener al menos 6 caracteres.\n\n" +
            "B) El nombre de usario no puede estar registrado previamente.\n\n" +
            "C) Ningun campo puede quedarse vacio.\n\n" +
            "Cualquiera de esta información que no se cumpla se mostrara por pantalla al pulsar " +
            "el boton registrarse.";
    public static final String AYUDA_VENTANA_SUGERENCIA = "Ayuda para añadir una sugerencia: \n\n" +
            "Se deben rellenar todos los campos y pulsar el boton añadir, esta sugerencia sera" +
            " enviada automaticamente a los revisadores del juego.";
    public static final String AYUDA_VENTANA_JUEGO = "Ayuda ventana de juego:\n\nPara comenzar a jugar " +
            "es necesario pulsar el boton comenzar, una vez iniciado el juego apareceran preguntas " +
            "con cuatro respuestas, al marcar una se pulsa el boton Validar respuesta, el cual " +
            "muestra coloreando tu respuesta de rojo o verde si es correcta o incorrecta a la vez" +
            " que suma o resta puntos. Una vez validada la respuesta se pasa a la siguiente." +
            "\n\nSi cuando se pulsa validar respuesta no hay ninguna respuesta marcada lo muestra" +
            " por pantalla y no salta a la siguiente.";
    public static final String USUARIO_CORRECTO = "Usuario correcto";
    public static final String AYUDA_VENTANA_USUARIO = "Ayuda de ventana:\n\nSi se desa jugar, " +
            "se pulsara el boton jugar y se abrira automaticamente la ventana de juego.\n\n" +
            "Se puede sugerir preguntas desde el boton sugerir preguntas.";

    public static final String AYUDA_VENTANA_ADMIN = "Ayuda de ventana:\n\nSi se desa jugar, " +
            "se pulsara el boton jugar y se abrira automaticamente la ventana de juego.\n\n" +
            "Se puede sugerir preguntas desde el boton sugerir preguntas.\n\n" +
            "Se pueden gestionar tanto las partidas, usuarios y sugerencia desde los botones" +
            " de gestión, estos abren automaticamente la representación de la tabla en la base" +
            " de datos.";
    public static final String NOMBRE_USUARIO = "nombreUsuario";
    public static final String ES_SUGERENCIA = "esSugerencia";
    public static final String AYUDA_VENTANA_GESTION_USUARIOS = "Ayuda para la gestión de usuarios:\n\n" +
            "Para modificar o eliminar la información de cualquier usuario, es necesario pinchar" +
            " sobre el usuario deseado, automaticamente se abrira una ventana con los datos de dicho" +
            " usuario cargados, pero no se podra modificar el nombre de usuario.\n\nPara añadir un" +
            " usuario hay que pulsar el boton añadir, el cual abrira la pantalla de registro, pero" +
            " con la peculiaridad, de que al ser administrador podras modificar más parametros que" +
            " el usuario normal.";
    public static final String RELLENAR_CAMPOS = "Debe rellenar todos los campos.";
    public static final String SUGERENCIA_EXITO = "Sugerencia añadida correctamente." ;
    public static final String MODIFICAR_USER = "Modificar Usuario";
    public static final String NAVEGADOR = "https://www.google.es/search?q=";
    public static final String AYUDA_VENTANA_GESTION_SUGERENCIAS = "Ayuda para la gestión de sugerencias:\n\n" +
            "Se puede añadir una sugerencia a las partidas del juego al pulsar sobre una sugerencia" +
            "y pulsando el boton añadir dentro de la ventana abierta," +
            " y esta se elimina una vez añadida.\n\n" +
            "Se puede eliminar sugerencias pulsando sobre una de ellas y pulsando el boton de eliminar" +
            " de la ventana abierta.\n\n" +
            "Se puede buscar por google la pregunta de la sugerencia, pulsando en el boton buscar" +
            " de la ventana abierta al pinchar en una sugerencia.\n\n" +
            "Se pueden eliminar todas las sugerencias desde el menu de android.";
    public static final String AYUDA_VENTANA_GESTION_PARTIDAS = "Ayuda para la gestión de partidas\n\n" +
            "Se puede modificar y eliminar una partida pulsando sobre ella, automaticamente se abre" +
            " una ventana que permite realizar estas acciones mediante la pulsación de los botones" +
            "guardar o eliminar.\n\n" +
            "Se pueden añadir nuevas partidas pulsando al boton añadir.\n\n" +
            "Se pueden borrar todas las partidas desde el menu de android.\n\n";
    public static final String PUNTUACION = "Puntuación ";
    public static String USUARIO = "Usuario: ";

   /* public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }*/
}
