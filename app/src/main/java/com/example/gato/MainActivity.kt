package com.example.gato

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    private var tablero: Gato? = null
    private var txtCasilla: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tablero = findViewById(R.id.tablero) as Gato
        txtCasilla = findViewById(R.id.txtCasilla) as TextView
        tablero!!.setOnCasillaSeleccionadaListener(object : Gato.OnCasillaSeleccionadaListener {
            override fun onCasillaSeleccionada(fila: Int, columna: Int) {
                txtCasilla!!.setText(
                    "Última casilla seleccionada: ($fila,$columna)")
            }
        })
    }
}
