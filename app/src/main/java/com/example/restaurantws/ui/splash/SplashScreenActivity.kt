package com.example.restaurantws.ui.splash

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.restaurantws.R
import com.example.restaurantws.core.pedidosFakeResponse
import com.example.restaurantws.core.privacyFakeResponse
import com.example.restaurantws.ui.MainActivity
import com.example.restaurantws.ui.login.LoginActivity
import com.example.restaurantws.utils.goToActivity
import org.koin.android.ext.android.inject

class SplashScreenActivity : AppCompatActivity() {
    private val prefs: SharedPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            val isSessionActive = prefs.getBoolean(getString(R.string.is_saved_current_user), false)

            if (isSessionActive) goToActivity<MainActivity>() else goToActivity<LoginActivity>()
        }, 1000)

        val categoriesFakeResponse = "" +
                "{\n" +
                "    \"respuesta\": \"OK\",\n" +
                "    \"datos\": [\n" +
                "        {\n" +
                "            \"id\": 1,\n" +
                "            \"nombre\": \"Desayunos\",\n" +
                "            \"descripcion\": \"En esta categoría encontrará los desayunos\",\n" +
                "            \"url_imagen\": ${R.drawable.desayunos}" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 2,\n" +
                "            \"nombre\": \"Almuerzos\",\n" +
                "            \"descripcion\": \"En esta categoría encontrará los almuerzos\",\n" +
                "            \"url_imagen\": ${R.drawable.almuerzos}" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 3,\n" +
                "            \"nombre\": \"Cena\",\n" +
                "            \"descripcion\": \"En esta categoría encontrará los Cenas\",\n" +
                "            \"url_imagen\": ${R.drawable.cena}" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 4,\n" +
                "            \"nombre\": \"Bebidas\",\n" +
                "            \"descripcion\": \"En esta categoría encontrará las bebidas\",\n" +
                "            \"url_imagen\": ${R.drawable.bebidas}" +
                "        }\n" +
                "    ]\n" +
                "}"


        val especialidadFakeResponse = "{\n" +
                "    \"respuesta\": \"OK\",\n" +
                "    \"datos\": {\n" +
                "        \"nombre\": \"Bandeja montañera\",\n" +
                "        \"descripcion\": \"Con frijol, carne asada, carne molida o chicharrón, huevo frito, papa, yuca, ensalada, tajada de maduro, arroz y arepa.\",\n" +
                "        \"precio\": 21400,\n" +
                "        \"url_foto\": ${R.drawable.desayunos}" +
                "    }\n" +
                "}"


        saveInPrefs("saved_policies", privacyFakeResponse)
        saveInPrefs("saved_especialidad", especialidadFakeResponse)
        saveInPrefs("saved_categories", categoriesFakeResponse)
        saveInPrefs("saved_products", getProductsResponse())
        saveInPrefs("saved_pedidos", pedidosFakeResponse)
    }

    private fun saveInPrefs(key: String, value: String) {
        with(prefs.edit())
        {
            putString(key, value)
            commit()
        }
    }

    private fun getProductsResponse(): String {
        return "{\n" +
                "    \"respuesta\": \"OK\",\n" +
                "    \"productos\": [\n" +
                "        {\n" +
                "            \"id\": 1,\n" +
                "            \"nombre\": \"Pechuga a la plancha\",\n" +
                "            \"descripcion\": \"Todas nuestras pechugas vienen acompañadas de papas a la francesa, arepa y ensalada.\",\n" +
                "            \"precio\": 28600,\n" +
                "            \"url_imagen\": ${R.drawable.pechuga}" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 2,\n" +
                "            \"nombre\": \"Ensalada con pollo\",\n" +
                "            \"descripcion\": \"Ensalada verde con maíz tierno, tomate, pasas aguacate, tocineta, ajonjolí, queso, frijoles y lonjas de pollo.\",\n" +
                "            \"precio\": 19500,\n" +
                "            \"url_imagen\": ${R.drawable.ensalada_pollo}" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 3,\n" +
                "            \"nombre\": \"Ensalada Vegetariana\",\n" +
                "            \"descripcion\": \"Con lechuga, máiz, tomate, aguacate, pasas, ajonjolí, queso y fríjoles.\",\n" +
                "            \"precio\": 23500,\n" +
                "            \"url_imagen\": ${R.drawable.ensalada_vegetariana}" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 4,\n" +
                "            \"nombre\": \"Filete de pescado marinera\",\n" +
                "            \"descripcion\": \"Filete de pescado en salsa marinera de\\ncamarón, calamar y pulpo gratinado.\",\n" +
                "            \"precio\": 34500,\n" +
                "            \"url_imagen\": ${R.drawable.filete_pescado}" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 5,\n" +
                "            \"nombre\": \"Mixto Carnes\",\n" +
                "            \"descripcion\": \"Medallón de cerdo 120g, solomito de res 10g, mini chorizo, mini morcilla con papa en salsa agria y ensalada.\",\n" +
                "            \"precio\": 16500,\n" +
                "            \"url_imagen\": ${R.drawable.mixto_carnes}" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 6,\n" +
                "            \"nombre\": \"Bandeja Paisa Grande\",\n" +
                "            \"descripcion\": \"Con frijol, arroz, carne molida, chorizo, morcilla, chicharrón, huevo frito, tajada de maduro, ensalada, aguacate y arepa.\",\n" +
                "            \"precio\": 30900,\n" +
                "            \"url_imagen\": ${R.drawable.bandeja_paisa}" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 7,\n" +
                "            \"nombre\": \"Bandeja Mediana\",\n" +
                "            \"descripcion\": \"Con frijol, arroz, carne molida, chorizo, morcilla, chicharrón, huevo frito, tajada de maduro, ensalada, aguacate y arepa.\",\n" +
                "            \"precio\": 27500,\n" +
                "            \"url_imagen\": ${R.drawable.bandeja_mediana}" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 8,\n" +
                "            \"nombre\": \"Bandeja de Lentejas\",\n" +
                "            \"descripcion\": \"Con chicharrón, arroz, carne molida, chorizo, huevo frito, tajada de maduro, ensalada, aguacate y arepa.\",\n" +
                "            \"precio\": 25500,\n" +
                "            \"url_imagen\": ${R.drawable.bandeja_lentejas}" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 9,\n" +
                "            \"nombre\": \"Mondongo Mediana\",\n" +
                "            \"descripcion\": \"Con arroz, banano, aguacate, cilantro y arepa.\",\n" +
                "            \"precio\": 18800,\n" +
                "            \"url_imagen\": ${R.drawable.mondongo}" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 10,\n" +
                "            \"nombre\": \"Sancocho de Res\",\n" +
                "            \"descripcion\": \"Con arroz, banano, aguacate, cilantro y arepa.\",\n" +
                "            \"precio\": 27500,\n" +
                "            \"url_imagen\": ${R.drawable.sancocho_res}" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 11,\n" +
                "            \"nombre\": \"Picada Colombiana\",\n" +
                "            \"descripcion\": \"Chicharrón, mini chorizo, mini morcilla,\\npatacón, empanada, chip de plátano, tomate, hogao y guacamole.\",\n" +
                "            \"precio\": 51500,\n" +
                "            \"url_imagen\": ${R.drawable.picada}" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 12,\n" +
                "            \"nombre\": \"Salmón a la Plancha\",\n" +
                "            \"descripcion\": \"Con bolitas de yuca y vegetales calientes.\",\n" +
                "            \"precio\": 39500,\n" +
                "            \"url_imagen\": ${R.drawable.salmon}" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 13,\n" +
                "            \"nombre\": \"Mazamorra\",\n" +
                "            \"descripcion\": \"Bebida típica de maíz cocido acompañada con panela.\",\n" +
                "            \"precio\": 6100,\n" +
                "            \"url_imagen\": ${R.drawable.mazamorra}" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 14,\n" +
                "            \"nombre\": \"Costillas BBQ\",\n" +
                "            \"descripcion\": \"Con papa salada, tomate y arepa.\",\n" +
                "            \"precio\": 38500,\n" +
                "            \"url_imagen\": ${R.drawable.costillas}" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 15,\n" +
                "            \"nombre\": \"Parrillada Grande\",\n" +
                "            \"descripcion\": \"200g de solomito, 220g de punta de anca, 120g de carne de res, 120g de pechuga de pollo, chicharrón, mini chorizo, mini morcilla, arepa, medallones de plátano maduro y papas a la francesa.\",\n" +
                "            \"precio\": 88300,\n" +
                "            \"url_imagen\": ${R.drawable.parrillada}" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 16,\n" +
                "            \"nombre\": \"Jugos en Agua\",\n" +
                "            \"descripcion\": \"Jugos naturales en agua (Mora, Mango, Guanábana, Fresa ó Maracuyá).\",\n" +
                "            \"precio\": 5900,\n" +
                "            \"url_imagen\": ${R.drawable.jugos_agua}" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 17,\n" +
                "            \"nombre\": \"Jugos en Leche\",\n" +
                "            \"descripcion\": \"Jugos naturales en leche (Mora, Mango, Guanábana, Fresa ó Maracuyá).\",\n" +
                "            \"precio\": 7000,\n" +
                "            \"url_imagen\": ${R.drawable.jugos_leche}" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 18,\n" +
                "            \"nombre\": \"Guandolo\",\n" +
                "            \"descripcion\": \"Bebida Tradicional colombiana de panela y limón.\",\n" +
                "            \"precio\": 3000,\n" +
                "            \"url_imagen\": ${R.drawable.guandolo}" +
                "        }\n" +
                "    ]\n" +
                "}"

    }
}