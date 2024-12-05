package com.proyectosobres

import DBcontrol
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.proyectosobres.Carta


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dbHelper = DBcontrol(this)
        val db = dbHelper.writableDatabase

        // Ejemplos de cartas
        val cartas = listOf(
            Carta("img1.png", "Adri Embarba", "Espanyol", "comun", 32, "Delantero"),
            Carta("img2.png", "Aitor Fernández", "Osasuna", "comun", 32, "Delantero"),
            Carta("img3.png", "Aleix Garcia", "Girona", "épica", 37, "Mediocampo"),
            Carta("img5.png", "Alex Remiro", "Real Sociedad", "comun", 29, "Portero"),
            Carta("img4.png", "Allan Nyom", "Leganes", "épica", 36, "Defensa"),
            Carta("img91.png", "Alvaro Garcia", "Rayo Vallecano", "comun", 31, "Delantero"),
            Carta("img92.png", "Alvaro Valles", "Las Palmas", "comun", 27, "Portero"),
            Carta("img93.png", "Angel Correa", "Atletico de Madrid", "comun", 29, "Delantero"),
            Carta("img5.png", "Antonio Blanco", "Deportivo Alaves", "comun", 23, "Mediocampo"),
            Carta("img6.png", "Antonio Raillo", "Mayorca", "épica", 32, "Defensa"),
            Carta("img7.png", "Arnau Martinez", "Girona", "épica", 33, "Defensa"),
            Carta("img8.png", "Azpilicueta", "Atletico de Madrid", "épica", 35, "Lateral"),
            Carta("img9.png", "Benjamin Lecomte", "Espanyol", "comun", 33, "Portero"),
            Carta("img10.png", "Borja Bastón", "Leganes", "comun", 31, "Delantero"),
            Carta("img11.png", "Borja Iglesias", "Real Betis", "épica", 31, "Delantero"),
            Carta("img12.png", "Carles Aleña", "Getafe", "comun", 26, "Mediocampo"),
            Carta("img13.png", "Carlos Benavidez", "Deportivo Alaves", "épica", 26, "Mediocampo"),
            Carta("img14.png", "Carvajal", "Real Madrid", "leyenda", 32, "Lateral Derecho"),
            Carta("img15.png", "Chimy Avila", "Real Madrid", "épica", 31, "Delantero"),
            Carta("img16.png", "Claudio Bravo", "Real Betis", "leyenda", 41, "Portero"),
            Carta("img17.png", "Cristhian Stuani", "Girona", "épica", 28, "Delantero"),
            Carta("img18.png", "Dani Parejo", "Villareal", "épica", 29, "Mediocampo"),
            Carta("img19.png", "David Garcia", "Osasuna", "épica", 28, "Defensa"),
            Carta("img20.png", "David Silva", "Real Sociedad", "leyenda", 38, "Mediocampo"),
            Carta("img21.png", "David Soria", "Getafe", "comun", 31, "Portero"),
            Carta("img22.png", "Dimitri Foulquier", "Valencia", "comun", 31, "Defensa"),
            Carta("img23.png", "Djene Dakonam", "Getafe", "épica", 32, "Defensa"),
            Carta("img24.png", "Enes Ünal", "Getafe", "épica", 27, "Defensa"),
            Carta("img25.png", "Eric Curbelo", "Las Palmas", "comun", 30, "Defensa"),
            Carta("img26.png", "Fernando Pacheco", "Deportivo Alaves", "comun", 33, "Portero"),
            Carta("img27.png", "Fran Beltran", "Celta de Vigo", "comun", 25, "Mediocampo"),
            Carta("img28.png", "Gabri Veiga", "Celta de Vigo", "épica", 22, "Mediocampo"),
            Carta("img29.png", "Gavi", "Barcelona", "comun", 20, "Mediocampo"),
            Carta("img30.png", "Gerard Moreno", "Villareal", "épica", 33, "Delantero"),
            Carta("img31.png", "Giorgi Mamardashvili", "Villareal", "comun", 24, "Portero"),
            Carta("img32.png", "Griezmann", "Atletico de Madrid", "leyenda", 33, "Delantero"),
            Carta("img33.png", "Guido Rodriguez", "Real Betis", "épica", 30, "Mediocampo"),
            Carta("img34.png", "Hugo Duro", "Valencia", "comun", 25, "Delantero"),
            Carta("img35.png", "Iago Aspas", "Celta de Vigo", "leyenda", 37, "Delantero"),
            Carta("img36.png", "Iker Muniain", "Athletic de Bilbao", "leyenda", 27, "Mediocampo"),
            Carta("img41.png", "Iñaki Williams", "Athletic de Bilbao", "épica", 23, "Delantero"),
            Carta("img37.png", "Isi Palazon", "Rayo Vallecano", "épica", 29, "Mediocampo"),
            Carta("img39.png", "Ivan Cuellar", "Leganes", "comun", 40, "Portero"),
            Carta("img38.png", "Ivan Rakitic", "Sevilla", "leyenda", 36, "Mediocampo"),
            Carta("img40.png", "Ivan Villar", "Celta de Vigo", "comun", 27, "Portero"),
            Carta("img42.png", "Jan Oblak", "Atletico de Madrid", "épica", 31, "Portero"),
            Carta("img43.png", "Jaume Costa", "Mayorca", "comun", 36, "Defensa"),
            Carta("img44.png", "Javi Galan", "Celta de Vigo", "comun", 29, "Defensa"),
            Carta("img45.png", "Javi Guerra", "Valencia", "épica", 21, "Mediocampo"),
            Carta("img46.png", "Javi Sanchez", "Real Valladolid", "épica", 27, "Defensa"),
            Carta("img47.png", "Jesus Navas", "Sevilla", "leyenda", 38, "Defensa"),
            Carta("img48.png", "Jonathan Viera", "Las Palmas", "épica", 35, "Mediocampo"),
            Carta("img49.png", "Jordi Masiq", "Real Valladolid", "comun", 35, "Portero"),
            Carta("img51.png", "Jose Gaya", "Valencia", "épica", 29, "Defensa"),
            Carta("img89.png", "Youssef En-Nesyri", "Sevilla", "épica", 27, "Delantero"),
            Carta("img88.png", "Yeray Álvarez", "Athletic del Bilbao", "épica", 23, "Defensa"),
            Carta("img87.png", "Víctor Laguardia", "Deportivo Alavés", "épica", 34, "Defensa"),
            Carta("img86.png", "Vedat Muriqui", "Mallorca", "épica", 30, "Delantero"),
            Carta("img85.png", "Vallejo", "Real Madrid", "leyenda", 27, "Defensa Central"),
            Carta("img84.png", "Unai Simón", "Athletic del Bilbao", "épica", 30, "Portero"),
            Carta("img83.png", "Takefusa Kubo", "Real Sociedad", "épica", 23, "Delantero"),
            Carta("img82.png", "Stole Dimitrievski", "Rayo Vallecano", "comun", 31, "Portero"),
            Carta("img81.png", "Sergio León", "Real Valladolid", "comun", 35, "Delantero"),
            Carta("img80.png", "Sergio González", "Leganés", "comun", 31, "Defensa"),
            Carta("img79.png", "Sergi Darder", "Espanyol", "épica", 30, "Mediocampo"),
            Carta("img78.png", "Sandro Ramírez", "Las Palmas", "épica", 29, "Delantero"),
            Carta("img77.png", "Samuel Chukwueze", "Villareal", "comun", 28, "Delantero"),
            Carta("img76.png", "Rubén Peña", "Osasuna", "comun", 30, "Defensa"),
            Carta("img75.png", "Rubén Pardo", "Leganés", "épica", 32, "Mediocampo"),
            Carta("img74.png", "Robin Le Normand", "Real Sociedad", "épica", 27, "Defensa"),
            Carta("img73.png", "Raúl de Tomás", "Rayo Vallecano", "épica", 30, "Delantero"),
            Carta("img72.png", "Rajkovic", "Mallorca", "comun", 29, "Portero"),
            Carta("img71.png", "Pepe Reina", "Villareal", "leyenda", 24, "Portero"),
            Carta("img70.png", "Paulo Gazzaniga", "Girona", "comun", 40, "Portero"),
            Carta("img69.png", "Pau Torres", "Virrareal", "épica", 32, "Defensa"),
            Carta("img95.png", "Óscar Valentín", "Rayo Vallecano", "comun", 29, "Mediocampo"),
            Carta("img94.png", "Óscar Plano", "Real Valladolid", "comun", 33, "Mediocampo"),
            Carta("img68.png", "Nico Williams", "Athletic del Bilbao", "comun", 33, "Delantero"),
            Carta("img67.png", "Nabil Fekir", "Real Betis", "épica", 30, "Mediocampo"),
            Carta("img66.png", "Monchu", "Real Valladolid", "comun", 25, "Mediocampo"),
            Carta("img65.png", "Moi Gómez", "Osasuna", "comun", 32, "Mediocampo"),
            Carta("img64.png", "Mikel Oyarzabal", "Real Sociedad", "épica", 27, "Delantero"),
            Carta("img63.png", "Marko Dmitrović", "Sevilla", "comun", 32, "Portero"),
            Carta("img62.png", "Marcos Acuña", "Sevilla", "épica", 33, "Defensa"),
            Carta("img61.png", "Maksimović", "Getafe", "comun", 29, "Mediocampo"),
            Carta("img60.png", "Lunin", "Real Madrid", "comun", 25, "Portero"),
            Carta("img59.png", "Luis Rioja", "Alavés", "épica", 30, "Delantero"),
            Carta("img58.png", "Leandro Cabrera", "Espanyol", "comun", 33, "Defensa"),
            Carta("img57.png", "Lamine Yamal", "Barcelona", "raro", 26, "Mediocampo"),
            Carta("img56.png", "Koke", "Atlético del Madrid", "épica", 32, "Mediocampo"),
            Carta("img55.png", "Kirian Rodríguez", "Las Palmas", "comun", 28, "Mediocampo"),
            Carta("img54.png", "Kang-in Lee", "Mallorca", "épica", 23, "Mediocampo"),
            Carta("img53.png", "Juanpe", "Girona", "comun", 25, "Defensa"),
            Carta("img52.png", "Juan Miranda", "Real Betis", "comun", 24, "Defensa"),
            Carta("img50.png", "Joselu", "Espanyol", "épica", 24, "Delantero")
        )

        for (carta in cartas) {
            if (dbHelper.cartaExists(carta.jugador)) {
                dbHelper.updateCarta(carta)
            } else {
                dbHelper.insertCarta(carta.imagen, carta.jugador, carta.equipo, carta.rareza, carta.edad, carta.posicion)
            }
        }


        val backgroundImage = findViewById<ImageView>(R.id.backgroundImage)
        backgroundImage.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
}