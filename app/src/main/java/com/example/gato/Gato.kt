package com.example.gato

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.method.Touch
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast

class Gato : View {
    val VACIA = 0
    val FICHA_O = 1
    val FICHA_X = 2
    private var tablero:Array<IntArray>? = null
    private var fichaActiva: Int = 0
    private var xColor:Int=0
    private var oColor:Int=0

    /*Sobre escritura de constructores de View*/
    constructor(context: Context) : super(context) {
        this.inicializacion()
    }

    constructor(
        context: Context,
        attrs: AttributeSet
    ) : super(context, attrs) {
        this.inicializacion()
        // Procesamos los atributos XML personalizados
        val a = getContext().obtainStyledAttributes(
            attrs,
            R.styleable.TresEnRaya
        )
        this.oColor = a.getColor(
            R.styleable.TresEnRaya_ocolor, Color.BLUE
        )
        this.xColor = a.getColor(
            R.styleable.TresEnRaya_xcolor, Color.RED
        )
        a.recycle()
    }

    constructor(
        context: Context,
        attrs: AttributeSet, defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        this.inicializacion()
        // Procesamos los atributos XML personalizados
        val a = getContext().obtainStyledAttributes(
            attrs,
            R.styleable.TresEnRaya
        )
        this.oColor = a.getColor(
            R.styleable.TresEnRaya_ocolor, Color.BLUE
        )
        this.xColor = a.getColor(
            R.styleable.TresEnRaya_xcolor, Color.RED
        )
        a.recycle()
    }

    private fun inicializacion() {
    //instanciamos el tablero como un array de arrays de tamano 3x3
        tablero = Array(3) { IntArray(3) }
        limpiar()
        fichaActiva = FICHA_X
        xColor = Color.RED
        oColor = Color.BLUE
    }
    //funcion para inicializar el tablero como vacio
    fun limpiar() {
        for (i in 0..2) {
            for (j in 0..2) {
                this.tablero!![i][j] = VACIA
            }
        }
    }

    // funciones de acceso a las propiedades definidas
    fun setFichaActiva(ficha: Int) {
            this.fichaActiva = ficha
    }

    fun getFichaActiva(): Int {
        return this.fichaActiva
    }
    fun alternarFichaActiva() {
        if (this.fichaActiva === this.FICHA_O) {
            this.fichaActiva = this.FICHA_X
        }
        else {
            this.fichaActiva = this.FICHA_O
        }
    }
    fun setXColor(color: Int) {
        this.xColor = color
    }
    fun getXColor(): Int {
        return this.xColor
    }
    fun setOColor(color: Int) {
        this.oColor = color
    }
    fun getOColor(): Int {
        return this.oColor
    }
    fun setCasilla(fil: Int, col: Int, valor: Int) {
        this.tablero!![fil][col] = valor
    }
    fun getCasilla(fil: Int, col: Int): Int {
        return this.tablero!![fil][col]
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var ancho = calcularAncho(widthMeasureSpec)
        var alto = calcularAlto(heightMeasureSpec)
        if (ancho < alto) {
            alto = ancho
        } else {
            ancho = alto
        }
        setMeasuredDimension(ancho, alto)
    }
    private fun calcularAlto(limitesSpec: Int): Int {
        var res = 100 //Alto por defecto
        val modo = View.MeasureSpec.getMode(limitesSpec)
        val limite = View.MeasureSpec.getSize(limitesSpec)
        if (modo == View.MeasureSpec.AT_MOST) {
            res = limite
        } else if (modo == View.MeasureSpec.EXACTLY) {
            res = limite
        }
        return res
    }
    private fun calcularAncho(limitesSpec: Int): Int {
        var res = 100 //Ancho por defecto
        val modo = View.MeasureSpec.getMode(limitesSpec)
        val limite = View.MeasureSpec.getSize(limitesSpec)
        if (modo == View.MeasureSpec.AT_MOST) {
            res = limite
        } else if (modo == View.MeasureSpec.EXACTLY) {
            res = limite
        }
        return res
    }

    //Funcion para dibujar nuestro control
    override fun onDraw(canvas: Canvas) {
        //Obtenemos las dimensiones del control
        var alto: Float = measuredHeight.toFloat()
        var ancho: Float = measuredWidth.toFloat()
        //Lineas
        val pBorde = Paint()
        pBorde.setStyle(Paint.Style.STROKE)
        pBorde.setColor(Color.BLACK)
        pBorde.setStrokeWidth(2f)
        canvas.drawLine(ancho / 3f, 0f, ancho / 3f, alto, pBorde)
        canvas.drawLine(2f * ancho / 3f, 0f, 2f * ancho / 3f, alto, pBorde)
        canvas.drawLine(0f, alto / 3f, ancho, alto / 3, pBorde)
        canvas.drawLine(0f, 2f * alto / 3f, ancho, 2f * alto / 3f, pBorde)
        //Marco
        canvas.drawRect(0f, 0f, ancho, alto, pBorde)
        //Marcas
        val pMarcaO = Paint()
        pMarcaO.setStyle(Paint.Style.STROKE)
        pMarcaO.setStrokeWidth(8f)
        pMarcaO.setColor(oColor)
        val pMarcaX = Paint()
        pMarcaX.setStyle(Paint.Style.STROKE)
        pMarcaX.setStrokeWidth(8f)
        pMarcaX.setColor(xColor)
        //Casillas Seleccionadas
        for (fil in 0..2) {
            for (col in 0..2) {
                if (this.tablero!![fil][col] === this.FICHA_X) {
                    //Cruz
                    canvas.drawLine(
                        col * (ancho / 3) + ancho / 3 * 0.1f,
                        fil * (alto / 3) + alto / 3 * 0.1f,
                        col * (ancho / 3) + ancho / 3 * 0.9f,
                        fil * (alto / 3) + alto / 3 * 0.9f,
                        pMarcaX
                    )
                    canvas.drawLine(
                        col * (ancho / 3) + ancho / 3 * 0.1f,
                        fil * (alto / 3) + alto / 3 * 0.9f,
                        col * (ancho / 3) + ancho / 3 * 0.9f,
                        fil * (alto / 3) + alto / 3 * 0.1f,
                        pMarcaX
                    )
                } else if (this.tablero!![fil][col] === this.FICHA_O) {
                    //Circulo
                    canvas.drawCircle(
                        col * (ancho / 3) + ancho / 6,
                        fil * (alto / 3) + alto / 6,
                        ancho / 6 * 0.8f, pMarcaO
                    )
                }
            }
        }
    }


    interface OnCasillaSeleccionadaListener {
        fun onCasillaSeleccionada(fila: Int, columna: Int)
    }

    private var listener: OnCasillaSeleccionadaListener? = null
    //...
    fun setOnCasillaSeleccionadaListener(l: OnCasillaSeleccionadaListener) {
        listener = l
    }

    //Funcion para definir la funcionalidad cuando el usuario toca el control
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val fil = (event.y / (measuredHeight / 3)).toInt()
        val col = (event.x / (measuredWidth / 3)).toInt()
        var aux=true
        //Actualizamos el tablero
        tablero!![fil][col] = fichaActiva
        alternarFichaActiva()
        //Lanzamos el evento de pulsación
        if (listener != null) {
            listener!!.onCasillaSeleccionada(fil, col);
        }
        //Refrescamos el control
        this.invalidate()
        //Mandamos mensaje si gana
        for(i in 0..2){
            if(tablero!![i][0]==1 && tablero!![i][1]==1 && tablero!![i][2]==1){
                Toast.makeText(
                    this.context,
                    "Ganan O",
                    Toast.LENGTH_SHORT
                ).show()
                limpiar()
            }
            if(tablero!![0][i]==1 && tablero!![1][i]==1 && tablero!![2][i]==1){
                Toast.makeText(
                    this.context,
                    "Ganan O",
                    Toast.LENGTH_SHORT
                ).show()
                limpiar()
            }
        }
        for(i in 0..2){
            if(tablero!![i][0]==2 && tablero!![i][1]==2 && tablero!![i][2]==2){
                Toast.makeText(
                    this.context,
                    "Ganan X",
                    Toast.LENGTH_SHORT
                ).show()
                limpiar()
            }
            if(tablero!![0][i]==2 && tablero!![1][i]==2 && tablero!![2][i]==2){
                Toast.makeText(
                    this.context,
                    "Ganan X",
                    Toast.LENGTH_SHORT
                ).show()
                limpiar()
            }
        }


        for(fil in 0..2){
            for(col in 0..2){
                if(tablero!![fil][col]==0){
                    aux=false
                }
            }
        }
        if(aux){
            limpiar()
        }
        return super.onTouchEvent(event)
    }
}