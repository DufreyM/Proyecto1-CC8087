package com.example.waterreminder

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
        private val sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)

    // Getter y setter para la ruta de la imagen
    var mainMascota: Int
        get() = sharedPreferences.getInt("mainMascota", R.drawable.hipopotamo) // Valor predeterminado
        set(value) {
            sharedPreferences.edit().putInt("mainMascota", value).apply()
        }


    // Getter y setter para el primer valor numérico
    var goal: Float
        get() = sharedPreferences.getFloat("goal", 0f) // Valor predeterminado de 0f
        set(value) = sharedPreferences.edit().putFloat("goal", value).apply()

    // Getter y setter para el segundo valor numérico
    var waterConsumed: Int
        get() = sharedPreferences.getInt("water_consumed", 0)
        set(value) = sharedPreferences.edit().putInt("water_consumed", value).apply()

    /**
     * Elimina una clave específica de las preferencias.
     * @param clave La clave que se desea eliminar.
     */
    fun eliminarClave(clave: String) {
        sharedPreferences.edit().remove(clave).apply()
    }

    /**
     * Limpia todas las preferencias almacenadas.
     */
    fun limpiarTodo() {
        sharedPreferences.edit().clear().apply()
    }
}
