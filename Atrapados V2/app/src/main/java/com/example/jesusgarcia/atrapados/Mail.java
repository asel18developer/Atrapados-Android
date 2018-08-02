package com.example.jesusgarcia.atrapados;


import android.content.Context;

import java.util.Date;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class Mail extends javax.mail.Authenticator {
    private static final String CADENA_VACIA = "";


    private String _user;
    private String _pass;

    private String[] _to;
    private String _from;

    private String _port;
    private String _sport;

    private String _host;

    private String _subject;
    private String _body;

    private boolean _auth;

    private boolean _debuggable;

    private Multipart _multipart;


    private Mail() {
        _host = "smtp.gmail.com"; // default smtp server
        _port = "465"; // default smtp port
        _sport = "465"; // default socketfactory port

        _user = CADENA_VACIA; // username
        _pass = CADENA_VACIA; // password
        _from = CADENA_VACIA; // email sent from
        _subject = CADENA_VACIA; // email subject
        _body = CADENA_VACIA; // email body

        _debuggable = false; // debug mode on or off - default off
        _auth = true; // smtp authentication - default on

        _multipart = new MimeMultipart();

        // There is something wrong with MailCap, javamail can not find a handler for the multipart/mixed part, so this bit needs to be added.
        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(mc);
    }

    public Mail(String user, String pass) {

        /*
         * Llamo al constructor que no recibe parametros para asignar todos los valores necesarios
         * una vez llamado, le asigno los valores pasados como parametros
         */
        this();

        _user = user;
        _from = user;
        _pass = pass;

    }

    public boolean send() throws MessagingException {

        Properties props = _setProperties();



        if(seCumplenCondiciones()) {

            Session session = Session.getInstance(props, this);

            MimeMessage msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(_from));

            InternetAddress[] addressTo = new InternetAddress[_to.length];
            for (int i = 0; i < _to.length; i++) {
                addressTo[i] = new InternetAddress(_to[i]);
            }
            msg.setRecipients(MimeMessage.RecipientType.TO, addressTo);

            msg.setSubject(_subject);
            msg.setSentDate(new Date());

            // setup message body
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(_body);
            _multipart.addBodyPart(messageBodyPart);

            // Put parts in message
            msg.setContent(_multipart);

            // send email
            Transport.send(msg);

            return true;

        } else {

            return false;

        }
    }

    private boolean seCumplenCondiciones() {

        boolean cumpleCondiciones = false;

        if (!_user.equals(CADENA_VACIA) && !_pass.equals(CADENA_VACIA) && _to.length > 0
                && !_from.equals(CADENA_VACIA) && !_subject.equals(CADENA_VACIA)
                && !_body.equals(CADENA_VACIA))
        {

            cumpleCondiciones = true;

        }

        return cumpleCondiciones;
    }


    @Override
    public PasswordAuthentication getPasswordAuthentication() {

        return new PasswordAuthentication(_user, _pass);

    }



    private Properties _setProperties() {
        Properties props = new Properties();

        props.put("mail.smtp.host", _host);

        if(_debuggable) {
            props.put("mail.debug", "true");
        }

        if(_auth) {
            props.put("mail.smtp.auth", "true");
        }

        props.put("mail.smtp.port", _port);
        props.put("mail.smtp.socketFactory.port", _sport);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        return props;
    }

    /*
     *
     * Metodos get y set para los atributos de la clase, para posible reutilizacion del codigo en
     * un futuro
     *
     */

    public String getBody() {
        return _body;
    }

    public void setBody(String _body) {
        this._body = _body;
    }

    public void setTo(String[] toArr) {
        this._to = toArr;
    }

    public void setFrom(String string) {
        this._from = string;
    }

    public void setSubject(String string) {
        this._subject = string;
    }


    public boolean recuperacionContraseña(String nombreUsuario) {

        Conector consultor = Conector.getConector(null);

        boolean enviarCorreo = false;




        String contraseñaRecuperada = consultor.getContraseña(nombreUsuario);

        if (!contraseñaRecuperada.equals(Constantes.VACIA)){

            enviarCorreo = true;
            String[] toArr = {consultor.obtenerEmail(nombreUsuario)}; // This is an array, you can add more emails, just separate them with a coma
            setTo(toArr); // load array to setTo function
            setSubject(Constantes.RECUPERACION_CONTRASEÑA);

        }


        setBody("Hola estimado cliente.\n\nLa contraseña de su usuario es la siguiente: "
                +contraseñaRecuperada+"\n\nGracias por eligir atrapados");

        return enviarCorreo;

    }
}

