package urias.andres.myfeelings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject
import kotlinx.android.synthetic.main.activity_main.*
import urias.andres.myfeelings.utilities.customcircledrawable
import urias.andres.myfeelings.utilities.custombardrawable
import urias.andres.myfeelings.utilities.emociones
import urias.andres.myfeelings.utilities.jsonfile
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private var jsonFile: jsonfile? = null
    private var veryHappy = 0.0f
    private var happy = 0.0f
    private var neutral = 0.0f
    private var sad = 0.0f
    private var verySad = 0.0f
    private var data:Boolean = false
    private var list = ArrayList<emociones>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        jsonFile = jsonfile()
        fetchingData()
        if(!data){
            var emociones = ArrayList<emociones>()
            val fondo = customcircledrawable(this,emociones)
            graph.background = fondo
            graphVeryHappy.background = custombardrawable(this,emociones("Muy feliz",0.0f,R.color.mustard,veryHappy))
            graphHappy.background = custombardrawable(this,emociones("Feliz",0.0f,R.color.orange,happy))
            graphNeutral.background = custombardrawable(this,emociones("Neutral",0.0f,R.color.greenie,neutral))
            graphSad.background = custombardrawable(this,emociones("Triste",0.0f,R.color.blue,sad))
            graphVerySad.background = custombardrawable(this,emociones("Muy Triste",0.0f,R.color.deepBlue,verySad))
        }else{
            actualizaGrafica()
            iconoMayoria()
        }
        saveButton.setOnClickListener {
            guardar()
        }

        veryHappyButton.setOnClickListener{
            veryHappy++
            iconoMayoria()
            actualizaGrafica()
        }
        happyButton.setOnClickListener {
            happy++
            iconoMayoria()
            actualizaGrafica()
        }

        neutralButton.setOnClickListener {
            neutral++
            iconoMayoria()
            actualizaGrafica()
        }
        sadButton.setOnClickListener {
            sad++
            iconoMayoria()
            actualizaGrafica()
        }
        verySadButton.setOnClickListener {
            verySad++
            iconoMayoria()
            actualizaGrafica()
        }
    }

    fun fetchingData(){
        try {
            var json : String = jsonFile?.getData(this) ?: ""
            if (json != ""){
                this.data = true
                var jsonArray: JSONArray = JSONArray(json)
                this.list = parseJson(jsonArray)

                for (i in list){
                    when(i.nombre){
                        "Muy Feliz" -> veryHappy = i.total
                        "Feliz" -> happy = i.total
                        "Neutral" -> neutral = i.total
                        "Triste" -> sad = i.total
                        "Muy triste" -> verySad = i.total
                    }
                }
            }else{
                this.data = false
            }
        }catch (ex: Exception){
            ex.printStackTrace()
        }
    }
    fun iconoMayoria(){
        if (happy > veryHappy && happy > neutral && happy > sad && happy >verySad){
            icon.setImageDrawable(resources.getDrawable(R.drawable.ic_happy))
        }
        if (veryHappy > happy && veryHappy > neutral && veryHappy > sad && veryHappy >verySad){
            icon.setImageDrawable(resources.getDrawable(R.drawable.ic_veryhappy))
        }
        if (neutral > veryHappy && neutral > happy && neutral > sad && neutral >verySad){
            icon.setImageDrawable(resources.getDrawable(R.drawable.ic_neutral))
        }
        if (sad > veryHappy && sad > neutral && sad > happy && sad >verySad){
            icon.setImageDrawable(resources.getDrawable(R.drawable.ic_sad))
        }
        if (verySad > happy && verySad > neutral && verySad > sad && veryHappy < verySad){
            icon.setImageDrawable(resources.getDrawable(R.drawable.ic_verysad))
        }
    }
    fun actualizaGrafica(){
        val total = veryHappy+happy+neutral+sad+verySad

        var pVH:Float = (veryHappy*100/total).toFloat()
        var pH:Float = (happy*100/total).toFloat()
        var pN:Float = (neutral*100/total).toFloat()
        var pS:Float = (sad*100/total).toFloat()
        var pVS:Float = (verySad*100/total).toFloat()

        Log.d("porcentajes","very happy :"+pVH )
        Log.d("porcentajes","happy :"+pH )
        Log.d("porcentajes","neutral :"+pN )
        Log.d("porcentajes","sad :"+pS )
        Log.d("porcentajes","very sad :"+pVS )

        list.clear()
        list.add(emociones("Muy feliz",pVH,R.color.mustard,veryHappy))
        list.add(emociones("Feliz",pH,R.color.orange,happy))
        list.add(emociones("Neutral",pN,R.color.greenie,neutral))
        list.add(emociones("Triste",pS,R.color.blue,sad))
        list.add(emociones("Muy Triste",pVS,R.color.deepBlue,verySad))

        val fondo = customcircledrawable(this,list)
        graphVeryHappy.background = custombardrawable(this,emociones("Muy feliz",pVH,R.color.mustard,veryHappy))
        graphHappy.background = custombardrawable(this,emociones("Feliz",pH,R.color.orange,happy))
        graphNeutral.background = custombardrawable(this,emociones("Neutral",pN,R.color.greenie,neutral))
        graphSad.background = custombardrawable(this,emociones("Triste",pS,R.color.blue,sad))
        graphVerySad.background = custombardrawable(this,emociones("Muy Triste",pVS,R.color.deepBlue,verySad))

        graph.background = fondo

    }
    fun parseJson(jsonArray: JSONArray):ArrayList<emociones>{
        var lista = ArrayList<emociones>()

        for (i  in 0..jsonArray.length()){
            try {
                val nombre = jsonArray.getJSONObject(i).getString("nombre")
                val porcentaje = jsonArray.getJSONObject(i).getDouble("porcentaje").toFloat()
                val color = jsonArray.getJSONObject(i).getInt("color")
                val total = jsonArray.getJSONObject(i).getDouble("total").toFloat()
                var emocion = emociones(nombre, porcentaje,color,total)
                lista.add(emocion)
            }catch (ex: Exception){
                ex.printStackTrace()
            }
        }
        return lista
    }
    fun guardar(){

        var jsonArray = JSONArray()
        var o : Int = 0
        for(i in list){
            Log.d("objetos",i.toString())
            var j: JSONObject = JSONObject()
            j.put("nombre",i.nombre)
            j.put("porcentaje",i.porcentaje)
            j.put("color",i.color)
            j.put("total",i.total)

            jsonArray.put(o,j)
            o++
        }
        jsonFile?.saveData(this,jsonArray.toString())
        Toast.makeText(this,"Datos guardados", Toast.LENGTH_SHORT).show()
    }
}