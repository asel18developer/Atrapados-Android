package com.example.jesusgarcia.atrapados;

/**
 * Created by jesusgarcia on 12/11/15.
 */
public class Partida {



    /*
     * Atributos de la clase Partida
     */
    private String pregunta;
    private String respuestaCorrecta;
    private String respuesta2;
    private String respuesta3;
    private String respuesta4;
    private int id;


    /**
     *
     * Constructor de la clase pregunta, para el caso de no conocer su id en la base
     * de datos, ya que cuando se crea desde la vista de Administrador, no sabes que
     * id va a tener asignada en la base de datos
     *
     *
     * @param _pregunta
     * @param _respuestaCorrecta
     * @param _respuesta2
     * @param _respuesta3
     * @param _respuesta4
     */
    public Partida(String _pregunta, String _respuestaCorrecta, String _respuesta2,
                   String _respuesta3, String _respuesta4) {

        this.pregunta = _pregunta;
        this.respuestaCorrecta = _respuestaCorrecta;
        this.respuesta2 = _respuesta2;
        this.respuesta3 = _respuesta3;
        this.respuesta4 = _respuesta4;


    }


    /**
     *
     * Constructor de la clase Partida
     *
     * @param _pregunta
     * @param _respuestaCorrecta
     * @param _respuesta2
     * @param _respuesta3
     * @param _respuesta4
     * @param _id
     */
    public Partida(String _pregunta, String _respuestaCorrecta, String _respuesta2,
                   String _respuesta3, String _respuesta4, int _id) {

        this.pregunta = _pregunta;
        this.respuestaCorrecta = _respuestaCorrecta;
        this.respuesta2 = _respuesta2;
        this.respuesta3 = _respuesta3;
        this.respuesta4 = _respuesta4;
        this.id = _id;

    }


    /*
     * Metodos geters and seters de la clase Partida
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

    public String getRespuestaCorrecta() {

        return respuestaCorrecta;

    }

    public void setRespuestaCorrecta(String respuestaCorrecta) {

        this.respuestaCorrecta = respuestaCorrecta;

    }

    public String getRespuesta2() {

        return respuesta2;

    }

    public void setRespuesta2(String respuesta2) {

        this.respuesta2 = respuesta2;

    }

    public String getRespuesta3() {

        return respuesta3;

    }

    public void setRespuesta3(String respuesta3) {

        this.respuesta3 = respuesta3;

    }

    public String getRespuesta4() {

        return respuesta4;

    }

    public void setRespuesta4(String respuesta4) {

        this.respuesta4 = respuesta4;

    }


    /**
     *
     * Metodo utilizado para comprobar que una pregunta tiene todos sus campos completos
     * si algun de sus campos con excepcion del id esta vacio devuelve false.
     * Por contrario si todos sus campos tienen datos devuelve True
     *
     * @return
     */
    public boolean camposObligatorios() {

        boolean camposObligatorios = true;

        if (this.pregunta.isEmpty()||this.respuestaCorrecta.isEmpty()||this.respuesta2.isEmpty()
                ||this.respuesta3.isEmpty()||this.respuesta4.isEmpty()){

            camposObligatorios=false;

        }

        return camposObligatorios;
    }
}