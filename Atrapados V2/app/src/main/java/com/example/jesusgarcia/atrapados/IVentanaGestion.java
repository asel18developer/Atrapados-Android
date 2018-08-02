package com.example.jesusgarcia.atrapados;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by jesusgarcia on 24/1/16.
 */
interface IVentanaGestion {

    ListView listViewItem = null;
    Conector consultor = null;

    void mostrarAyuda();
    void actualizarVentana();



}
