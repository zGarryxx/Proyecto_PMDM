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
        Carta("https://drive.google.com/uc?id=1oYV9tQXmY2rP1_ta-XBlYz754_b-yedw", "Adri Embarba", "Espanyol", "comun", 32, "Delantero"),
        Carta("https://drive.google.com/uc?id=1Ygd-MwIcyrULsGCECv8geefBqRdLzyNk", "Aitor Fernández", "Osasuna", "comun", 32, "Delantero"),
        Carta("https://drive.google.com/uc?id=1BTOx4-7KWjP7LkjiILjLfkpFT4DwM9y3", "Aleix Garcia", "Girona", "épica", 37, "Mediocampo"),
        Carta("https://drive.google.com/uc?id=1881mtoo4kgOebC2KaUEMR9_4L0QCGqif", "Alex Remiro", "Real Sociedad", "comun", 29, "Portero"),
        Carta("https://drive.google.com/uc?id=1RC7j8xCoZuXMizrQ5_mBIlCrjmw0nacm", "Allan Nyom", "Leganes", "épica", 36, "Defensa"),
        Carta("https://drive.google.com/uc?id=1RFW0cOpf9iNgVl0XsdItx93XAf_1Xw0l", "Alvaro Garcia", "Rayo Vallecano", "comun", 31, "Delantero"),
        Carta("https://drive.google.com/uc?id=1sUPTpi2bpP8j7ouB6lntLH9iNjHApebl", "Alvaro Valles", "Las Palmas", "comun", 27, "Portero"),
        Carta("https://drive.google.com/uc?id=1cKkJHm24gfOKI9iROCnsvhkplG_WQ0By", "Angel Correa", "Atletico de Madrid", "comun", 29, "Delantero"),
        Carta("https://drive.google.com/uc?id=1LPqbF0X1104eqIJrKTCGfZWcno566KdV", "Antonio Blanco", "Deportivo Alaves", "comun", 23, "Mediocampo"),
        Carta("https://drive.google.com/uc?id=1WiygPYHtnznmiTKuAx-_k03XPeeAYwT4", "Antonio Raillo", "Mayorca", "épica", 32, "Defensa"),
        Carta("https://drive.google.com/uc?id=1AlM04d-ank2UKqlJZ96wG-nldOqs52CT", "Arnau Martinez", "Girona", "épica", 33, "Defensa"),
        Carta("https://drive.google.com/uc?id=1fFBnAICXCoeclcS-WKI-3MrXUSVT-0oI", "Azpilicueta", "Atletico de Madrid", "épica", 35, "Lateral"),
        Carta("https://drive.google.com/uc?id=11hDRMjBN5_6bAJvHF2wvFq7K5zSsy_h_", "Benjamin Lecomte", "Espanyol", "comun", 33, "Portero"),
        Carta("https://drive.google.com/uc?id=1JmnLLHcPpKPA7I06gjJ7ercD3ZI1mYZ_", "Borja Bastón", "Leganes", "comun", 31, "Delantero"),
        Carta("https://drive.google.com/uc?id=1WNHmXVHjsh-CAwP1v4E6XSCqmVViZoGt", "Borja Iglesias", "Real Betis", "épica", 31, "Delantero"),
        Carta("https://drive.google.com/uc?id=1eB0PIRTEWe44fNYl4jCnb0Ak0sBfQ-OP", "Carles Aleña", "Getafe", "comun", 26, "Mediocampo"),
        Carta("https://drive.google.com/uc?id=18RtFdVmzqnvrJDz34DmVqATzkEqHrTqp", "Carlos Benavidez", "Deportivo Alaves", "épica", 26, "Mediocampo"),
        Carta("https://drive.google.com/uc?id=10lhuYfUgPAg5lEKRcIv4SDLfaXqanlMq", "Carvajal", "Real Madrid", "leyenda", 32, "Lateral Derecho"),
        Carta("https://drive.google.com/uc?id=1zNKEXJRf-sxiLnKLKyhtzybAM0OAe4CZ", "Chimy Avila", "Real Madrid", "épica", 31, "Delantero"),
        Carta("https://drive.google.com/uc?id=13DMojgm5SMte2Q5T7XX7VlUr0pvBVWIX", "Claudio Bravo", "Real Betis", "leyenda", 41, "Portero"),
        Carta("https://drive.google.com/uc?id=1eypXQ8X_RVacXZToKjB5Qq0ZZ8lpVbNk", "Cristhian Stuani", "Girona", "épica", 28, "Delantero"),
        Carta("https://drive.google.com/uc?id=1622Fa3f96oH4JIg9qmyi4_R4XaUmz94j", "Dani Parejo", "Villareal", "épica", 29, "Mediocampo"),
        Carta("https://drive.google.com/uc?id=1jJBUWuSHOxX2WzE4Q2234WEJdI9oAlVg", "David Garcia", "Osasuna", "épica", 28, "Defensa"),
        Carta("https://drive.google.com/uc?id=14xpIarWpU2hXdupwNAWTE9LZwuAI6lXa", "David Silva", "Real Sociedad", "leyenda", 38, "Mediocampo"),
        Carta("https://drive.google.com/uc?id=1O8lsIcx6yn5ILQPwvtygJ72FiGDQMBTx", "David Soria", "Getafe", "comun", 31, "Portero"),
        Carta("https://drive.google.com/uc?id=1WkEBcj1LnMiejwkplCzSjNs0LxEYgd_v", "Dimitri Foulquier", "Valencia", "comun", 31, "Defensa"),
        Carta("https://drive.google.com/uc?id=1BQzCo2wntcPDCe6U5RpAuHOGC3-6VyXr", "Djene Dakonam", "Getafe", "épica", 32, "Defensa"),
        Carta("https://drive.google.com/uc?id=1UYXHR5q1rmSVQC0U9i0L32sqmnSZKbH0", "Enes Ünal", "Getafe", "épica", 27, "Defensa"),
        Carta("https://drive.google.com/uc?id=1P815TJKEhSJFM4jrtkcxGkn-4Wxj0jA2", "Eric Curbelo", "Las Palmas", "comun", 30, "Defensa"),
        Carta("https://drive.google.com/uc?id=1dtjnh7-Q2FWudlDwxtdiJCbGN1ZQTTRu", "Fernando Pacheco", "Deportivo Alaves", "comun", 33, "Portero"),
        Carta("https://drive.google.com/uc?id=1s0cldWZMqJHJC3CXJbSuMAQ-NXCJA3Ar", "Fran Beltran", "Celta de Vigo", "comun", 25, "Mediocampo"),
        Carta("https://drive.google.com/uc?id=1HyOv5HfDnNU7eqXkDoQKTdpFyl25RR-B", "Gabri Veiga", "Celta de Vigo", "épica", 22, "Mediocampo"),
        Carta("https://drive.google.com/uc?id=1MmLrEnKb6pq2a75XdlfulrofRxOTfXU4", "Gavi", "Barcelona", "comun", 20, "Mediocampo"),
        Carta("https://drive.google.com/uc?id=1bOLhMtyRG6pmg0bi2i72Isq09UZQiTbK", "Gerard Moreno", "Villareal", "épica", 33, "Delantero"),
        Carta("https://drive.google.com/uc?id=1s7iNHhcGdCWqiwRhjNABD0bWXqlSpG1w", "Giorgi Mamardashvili", "Villareal", "comun", 24, "Portero"),
        Carta("https://drive.google.com/uc?id=1SmXO21xdKoW7WiFR2Xiol9jkSt3hGWB_", "Griezmann", "Atletico de Madrid", "leyenda", 33, "Delantero"),
        Carta("https://drive.google.com/uc?id=18m7a3tv2mH9eHvqMobQBVgg-M6SE4Wy4", "Guido Rodriguez", "Real Betis", "épica", 30, "Mediocampo"),
        Carta("https://drive.google.com/uc?id=1Va55kRGqkWcgxTDSROgWD9QyZxRqkwti", "Hugo Duro", "Valencia", "comun", 25, "Delantero"),
        Carta("https://drive.google.com/uc?id=1vhD3CFVuUBW7Yxb-y2MiWXTBHGrSh6hY", "Iago Aspas", "Celta de Vigo", "leyenda", 37, "Delantero"),
        Carta("https://drive.google.com/uc?id=1EC2hCrMxiyddApRhxnsVt2xypOogSfk4", "Iker Muniain", "Athletic de Bilbao", "leyenda", 27, "Mediocampo"),
        Carta("https://drive.google.com/uc?id=1tqUXk9EmgsJ_t5Ew2znn3PCw968u74kW", "Iñaki Williams", "Athletic de Bilbao", "épica", 23, "Delantero"),
        Carta("https://drive.google.com/uc?id=1eY0PWvpPYanrPvSLbiknvwzUa3I36K13", "Isi Palazon", "Rayo Vallecano", "épica", 29, "Mediocampo"),
        Carta("https://drive.google.com/uc?id=1HxfYUxYbVOdYdBEPxw7BN59u7zwZzDZP", "Ivan Cuellar", "Leganes", "comun", 40, "Portero"),
            Carta("https://drive.google.com/uc?id=1rF3MfqTvU5GIMAg20-fm6I17QdN3ASHU", "Ivan Rakitic", "Sevilla", "leyenda", 36, "Mediocampo"),
            Carta("https://drive.google.com/uc?id=1i29Gt44Sf19jBvhI_yRC7tzn8JNXbf14", "Ivan Villar", "Celta de Vigo", "comun", 27, "Portero"),
            Carta("https://drive.google.com/uc?id=1flO-e__oxiU546mTzr_I0MP6MGr2we2y", "Jan Oblak", "Atletico de Madrid", "épica", 31, "Portero"),
            Carta("https://drive.google.com/uc?id=1dyVunStH9of7464GJB78USAgPpcfFZu1", "Jaume Costa", "Mayorca", "comun", 36, "Defensa"),
            Carta("https://drive.google.com/uc?id=19Tt6-HCcIXuPl5Sw6Ncs4nEKMcesWazO", "Javi Galan", "Celta de Vigo", "comun", 29, "Defensa"),
            Carta("https://drive.google.com/uc?id=1AiugVxBzd8jmNERo2SUQrkFY0TjPBftt", "Javi Guerra", "Valencia", "épica", 21, "Mediocampo"),
            Carta("https://drive.google.com/uc?id=1Ys3t-mTla4I3aI-5AWKjj2brPkbLnTx0", "Javi Sanchez", "Real Valladolid", "épica", 27, "Defensa"),
            Carta("https://drive.google.com/uc?id=1Sqa0a0husnveSXRhqd5QEMViRBYtw-lJ", "Jesus Navas", "Sevilla", "leyenda", 38, "Defensa"),
            Carta("https://drive.google.com/uc?id=1UKGWswcXgcgoFwXwIZK9aqMAxzxNC6JW", "Jonathan Viera", "Las Palmas", "épica", 35, "Mediocampo"),
            Carta("https://drive.google.com/uc?id=12qxhp4Gn0LQ-dDXJ9qk9wTrxpNP5i1Fq", "Jordi Masiq", "Real Valladolid", "comun", 35, "Portero"),
            Carta("https://drive.google.com/uc?id=1YXYRrntmHHHPwn4Y0sQ1Li5xhiM5JWjo", "Jose Gaya", "Valencia", "épica", 29, "Defensa"),
            Carta("https://drive.google.com/uc?id=1hfDnhaEZ4SwTSaF81wiYWFNaYKP8YEZQ", "Youssef En-Nesyri", "Sevilla", "épica", 27, "Delantero"),
            Carta("https://drive.google.com/uc?id=1HbDrJCxNtnfcjiL5lz44XAvJi75V8Mnf", "Yeray Álvarez", "Athletic del Bilbao", "épica", 23, "Defensa"),
            Carta("https://drive.google.com/uc?id=1t0eWJ2S99uICQJKd_op2QF265RFW98Hu", "Víctor Laguardia", "Deportivo Alavés", "épica", 34, "Defensa"),
            Carta("https://drive.google.com/uc?id=1X7LGXysnNUnr4img73dtlrVTdHr23BFU", "Vedat Muriqui", "Mallorca", "épica", 30, "Delantero"),
            Carta("https://drive.google.com/uc?id=1MknctmX8IVqi3z0B1fwJRLwnS9EqhkoO", "Vallejo", "Real Madrid", "leyenda", 27, "Defensa Central"),
            Carta("https://drive.google.com/uc?id=1HbRSLnvDwYLVvvV1bK37QFKjwtUOR6eo", "Unai Simón", "Athletic del Bilbao", "épica", 30, "Portero"),
            Carta("https://drive.google.com/uc?id=1b01M-QKb888kVuTa93nTDcRoaYkK0W2H", "Takefusa Kubo", "Real Sociedad", "épica", 23, "Delantero"),
            Carta("https://drive.google.com/uc?id=1_W6xARvGOZGpO2q0_eck0qByFAVsJdgN", "Stole Dimitrievski", "Rayo Vallecano", "comun", 31, "Portero"),
            Carta("https://drive.google.com/uc?id=1UpFBadj7PYwFjmbhaR4NdSb5tJhVQO4v", "Sergio León", "Real Valladolid", "comun", 35, "Delantero"),
            Carta("https://drive.google.com/uc?id=1IahuYWWX3Ngql5pzCF98ZIViUJxY4TMX", "Sergio González", "Leganés", "comun", 31, "Defensa"),
            Carta("https://drive.google.com/uc?id=1OdyTT6ZY0W2YeSHnH8Sa5ClZVqoKjO_G", "Sergi Darder", "Espanyol", "épica", 30, "Mediocampo"),
            Carta("https://drive.google.com/uc?id=1J3y6WYOJ47RH85qB-aLV9OgYeBJ3wnFT", "Sandro Ramírez", "Las Palmas", "épica", 29, "Delantero"),
            Carta("https://drive.google.com/uc?id=1CP547JJxQe9sjuLaG-aWWAKE9OJxIbWC", "Samuel Chukwueze", "Villareal", "comun", 28, "Delantero"),
            Carta("https://drive.google.com/uc?id=1hQjyhNm4tKqAdIST69_6fOCG36chIVTD", "Rubén Peña", "Osasuna", "comun", 30, "Defensa"),
            Carta("https://drive.google.com/uc?id=1xB8AwrU8L6uS2DHWOTkTWEtZgnr0BnYQ", "Rubén Pardo", "Leganés", "épica", 32, "Mediocampo"),
            Carta("https://drive.google.com/uc?id=1SKjqOELgOn6kVVm712lb3VxYnUozySRs", "Robin Le Normand", "Real Sociedad", "épica", 27, "Defensa"),
            Carta("https://drive.google.com/uc?id=1NAnwA2c-bf_40bmNYdyZf-D5mVoLTWxP", "Raúl de Tomás", "Rayo Vallecano", "épica", 30, "Delantero"),
            Carta("https://drive.google.com/uc?id=1ov2w3ANS7hwjjCIb0UeGpds9rUC9spQc", "Rajkovic", "Mallorca", "comun", 29, "Portero"),
            Carta("https://drive.google.com/uc?id=1mqKf5BO-BMDNhbH1xCVLWqgKYETAxQWo", "Pepe Reina", "Villareal", "leyenda", 24, "Portero"),
            Carta("https://drive.google.com/uc?id=1gOUJOIXcjrRh9WZr4YQB1hEuyeBCf0nD", "Paulo Gazzaniga", "Girona", "comun", 40, "Portero"),
            Carta("https://drive.google.com/uc?id=1zmOuclEGYGBs5NHL8FdaR90A3qj7i1aP", "Pau Torres", "Virrareal", "épica", 32, "Defensa"),
            Carta("https://drive.google.com/uc?id=14shh6NVvRpt558e0T1B1t7WnaDQ8tsIS", "Óscar Valentín", "Rayo Vallecano", "comun", 29, "Mediocampo"),
            Carta("https://drive.google.com/uc?id=1_AZvp9x1U1_NaZxuI7Gm-C9dUgddPamT", "Óscar Plano", "Real Valladolid", "comun", 33, "Mediocampo"),
            Carta("https://drive.google.com/uc?id=1JP-xIbr1s3lo74PAptB2F-emJYgjS_pY", "Nico Williams", "Athletic del Bilbao", "comun", 33, "Delantero"),
            Carta("https://drive.google.com/uc?id=1sG40DoSTYWYYo4H5bfimz_SbQFMgNHFP", "Nabil Fekir", "Real Betis", "épica", 30, "Mediocampo"),
            Carta("https://drive.google.com/uc?id=10XZOFkL7SoQngN_A_-Z4dTRmQLua97gG", "Monchu", "Real Valladolid", "comun", 25, "Mediocampo"),
            Carta("https://drive.google.com/uc?id=1bYB_CNSHhiqT25Umr_lPdWlW1egX7f5V", "Moi Gómez", "Osasuna", "comun", 32, "Mediocampo"),
            Carta("https://drive.google.com/uc?id=1OAQSVJEOar5KKGLMwgwNTlDrrGo32rOF", "Mikel Oyarzabal", "Real Sociedad", "épica", 27, "Delantero"),
            Carta("https://drive.google.com/uc?id=1Y-aqYIvwdKNtBq5GsgyQgnUWtSwnLVav", "Marko Dmitrović", "Sevilla", "comun", 32, "Portero"),
            Carta("https://drive.google.com/uc?id=1q3xPb7U-e1rNt5xJW0TB-UQxqzwTgZwj", "Marcos Acuña", "Sevilla", "épica", 33, "Defensa"),
            Carta("https://drive.google.com/uc?id=1JWfjrWS0i2OwanDL-MjAOcZtJQvTo0FP", "Maksimović", "Getafe", "comun", 29, "Mediocampo"),
            Carta("https://drive.google.com/uc?id=1BOxGBmd5IzySiymrqbyIPSk-8MEWIxbp", "Lunin", "Real Madrid", "comun", 25, "Portero"),
            Carta("https://drive.google.com/uc?id=14tJ8v4UaxCF6DrrPxN9omBjK5m6oWs-N", "Luis Rioja", "Alavés", "épica", 30, "Delantero"),
            Carta("https://drive.google.com/uc?id=1Z3OmuhBlpoaoF1zJTznfjdQdZgxjdB71", "Leandro Cabrera", "Espanyol", "comun", 33, "Defensa"),
            Carta("https://drive.google.com/uc?id=1KhbQETt0eXSrF0yfj95iT0Nu4No887QE", "Lamine Yamal", "Barcelona", "raro", 26, "Mediocampo"),
            Carta("https://drive.google.com/uc?id=1ScnLdRuAXnGkeIqwbEutr5LPrACNKB8P", "Koke", "Atlético del Madrid", "épica", 32, "Mediocampo"),
            Carta("https://drive.google.com/uc?id=1ip7vITszqUuFa_4pR8pQEXsnXu79quTT", "Kirian Rodríguez", "Las Palmas", "comun", 28, "Mediocampo"),
            Carta("https://drive.google.com/uc?id=1rpt5ahr3XiziZi6HbAUDVQfW-YVhV1np", "Kang-in Lee", "Mallorca", "épica", 23, "Mediocampo"),
            Carta("https://drive.google.com/uc?id=19Ppq48NGcD7SAf2QWP8AgW-UjwDH4YZ-", "Juanpe", "Girona", "comun", 25, "Defensa"),
            Carta("https://drive.google.com/uc?id=1G2bNM-gdgdmntwNGv8D-8h3qIC9_m5aA", "Juan Miranda", "Real Betis", "comun", 24, "Defensa"),
            Carta("https://drive.google.com/uc?id=170H4veYvDg-5aiaLEjsKnhB7RQY6PKgh", "Joselu", "Espanyol", "épica", 24, "Delantero")
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