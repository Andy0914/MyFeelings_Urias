package urias.andres.myfeelings.utilities

import android.content.Context
import android.util.Log
import java.io.IOException

class jsonfile {
    val MY_FEELINGS = "data.json"

    constructor(){

    }
    //Este método nos permite guardar en el archivo JSON.
    fun saveData(context: Context, json: String){
        try {
            context.openFileOutput(MY_FEELINGS, Context.MODE_PRIVATE).use {
                it.write(json.toByteArray())
            }
        }catch (e: IOException){
            Log.e("Guardar","Error in writting: "+ e.localizedMessage)
        }
    }
    //Método que perimte leer el archivo JSON de manera local
    fun getData(context: Context):String{
        return try {
            context.openFileInput(MY_FEELINGS).bufferedReader().readLine()
        }catch (e: IOException){
            Log.e("Guardar","Error in writting: "+ e.localizedMessage)
            ""
        }
    }
}