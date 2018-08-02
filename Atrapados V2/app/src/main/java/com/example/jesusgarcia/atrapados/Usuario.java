package com.example.jesusgarcia.atrapados;

/**
 * Created by jesusgarcia on 11/11/15.
 */
public class Usuario {


    /*
     *
     * Atributos de la clase Usuario
     *
     */

    private String nombre;
    private String apellidos;
    private String email;
    private String nombre_usuario;
    private String pass;
    private int puntos;
    private int tipo;



    /**
     *
     * Constructor de la clase Usuario
     *
     *
     * @param _nombre
     * @param _apellidos
     * @param _email
     * @param _nombre_usuario
     * @param _contrasena
     * @param _tipo
     * @param _puntos
     */
    public Usuario(String _nombre, String _apellidos, String _email, String _nombre_usuario,
                   String _contrasena, int _tipo, int _puntos){

        this.nombre =_nombre;
        this.apellidos =_apellidos;
        this.email =_email;
        this.nombre_usuario =_nombre_usuario;
        this.pass =_contrasena;
        this.tipo =_tipo;
        this.puntos = _puntos;


    }


    /*
     * Metodo geters and seters de la clase Usuario
     */
    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String user) {
        this.nombre_usuario = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }






}