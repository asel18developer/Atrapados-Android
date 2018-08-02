package com.example.jesusgarcia.atrapados;

/**
 * Created by jesusgarcia on 26/12/15.
 */
public class Sugerencia {


    /*
     * Atributos de la clase Sugerencia
     */
    private String pregunta;
    private String respuesta;
    private String nombreUsuario;
    private int id;


    /**
     *
     * Consturctor de la clase Sugerencia para el caso en el que no se conoce su id
     * en la base de datos, ya que cuando un usario la sugiere, no se sabe que id
     * va a tener asignada esta sugerencia en la base de datos.
     *
     * @param _pregunta
     * @param _respuesta
     * @param _nombreUsuario
     */
    public Sugerencia(String _pregunta, String _respuesta, String _nombreUsuario){

        this.pregunta = _pregunta;
        this.respuesta = _respuesta;
        this.nombreUsuario = _nombreUsuario;


    }

    /**
     *
     * Constructor de la clase Sugerencia
     *
     * @param _pregunta
     * @param _respuesta
     * @param _nombreUsuario
     * @param _id
     */
    public Sugerencia(String _pregunta, String _respuesta, String _nombreUsuario, int _id){

        this.pregunta = _pregunta;
        this.respuesta = _respuesta;
        this.nombreUsuario = _nombreUsuario;
        this.id = _id;

    }



    /*
     * Metodos geters and seter de la clase Sugerencia
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String usuario) {
        this.nombreUsuario = usuario;
    }
}
