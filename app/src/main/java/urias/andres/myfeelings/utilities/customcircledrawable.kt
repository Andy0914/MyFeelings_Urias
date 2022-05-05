package urias.andres.myfeelings.utilities

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import urias.andres.myfeelings.R

class customcircledrawable: Drawable {
    var coordenadas: RectF? = null
    var anguloBarrido: Float = 0.0F
    var anguloInicio: Float = 0.0F
    var grosorMetrica:Int = 0
    var grosorFondo: Int = 0
    var context: Context? = null
    var emociones = ArrayList<emociones>()

    constructor(context: Context,emociones: ArrayList<emociones>){
        this.context = context
        grosorMetrica = context.resources.getDimensionPixelSize(R.dimen.graphWith)
        grosorFondo = context.resources.getDimensionPixelSize(R.dimen.graphBackground)
        this.emociones = emociones
    }

    override fun draw(p0: Canvas) {
        val fondo:Paint = Paint()
        fondo.style = Paint.Style.STROKE //Indica si solo se dibujara el contorno, relleno o ambos.
        fondo.strokeWidth = (this.grosorFondo).toFloat() //Determina el grosor del contorno
        fondo.isAntiAlias = true // suaviza los trazos
        fondo.strokeCap = Paint.Cap.ROUND // determina los extremos del contorno, si son redondos o cuadrados
        fondo.color = context?.resources?.getColor(R.color.gray) ?: R.color.gray // Determina el color predeterminado
        val ancho: Float = (p0.width -25).toFloat()
        val alto: Float = (p0.height -25).toFloat()

        coordenadas = RectF(25.0F,25.0F,ancho,alto)

        p0.drawArc(coordenadas!!, 0.0F,0.0F,false,fondo)

        if (emociones.size != 0){
            for (e in emociones){
                val degree :Float = (e.porcentaje*360)/100
                this.anguloBarrido = degree

                var seccion: Paint = Paint()
                seccion.style = Paint.Style.STROKE //Indica si solo se dibujara el contorno, relleno o ambos.
                seccion.strokeWidth = (this.grosorFondo).toFloat() //Determina el grosor del contorno
                seccion.isAntiAlias = true // suaviza los trazos
                seccion.strokeCap = Paint.Cap.SQUARE // determina los extremos del contorno, si son redondos o cuadrados
                seccion.color = ContextCompat.getColor(this.context!!, e.color)

                p0.drawArc(coordenadas!!,this.anguloInicio,this.anguloBarrido,false,seccion)
                this.anguloInicio += this.anguloBarrido
            }
        }
    }

    override fun setAlpha(p0: Int) {

    }

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }

    override fun setColorFilter(p0: ColorFilter?) {

    }
}