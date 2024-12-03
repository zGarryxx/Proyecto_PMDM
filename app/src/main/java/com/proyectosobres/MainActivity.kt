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
        Carta("https://drive.google.com/file/d/1oYV9tQXmY2rP1_ta-XBlYz754_b-yedw/view?usp=drive_link", "Adri Embarba", "Espanyol", "comun", 32, "Delantero"),
        Carta("https://drive.google.com/file/d/1Ygd-MwIcyrULsGCECv8geefBqRdLzyNk/view?usp=drive_link", "Aitor Fernández", "Osasuna", "comun", 32, "Delantero"),
        Carta("https://drive.google.com/file/d/1BTOx4-7KWjP7LkjiILjLfkpFT4DwM9y3/view?usp=drive_link", "Aleix Garcia", "Girona", "épica", 37, "Mediocampo"),
        Carta("https://drive.google.com/file/d/1881mtoo4kgOebC2KaUEMR9_4L0QCGqif/view?usp=drive_link", "Alex Remiro", "Real Sociedad", "comun", 29, "Portero"),
        Carta("https://drive.google.com/file/d/1RC7j8xCoZuXMizrQ5_mBIlCrjmw0nacm/view?usp=drive_link", "Allan Nyom", "Leganes", "épica", 36, "Defensa"),
        Carta("https://drive.google.com/file/d/1RFW0cOpf9iNgVl0XsdItx93XAf_1Xw0l/view?usp=drive_link", "Alvaro Garcia", "Rayo Vallecano", "comun", 31, "Delantero"),
        Carta("https://drive.google.com/file/d/1sUPTpi2bpP8j7ouB6lntLH9iNjHApebl/view?usp=drive_link", "Alvaro Valles", "Las Palmas", "comun", 27, "Portero"),
        Carta("https://drive.google.com/file/d/1cKkJHm24gfOKI9iROCnsvhkplG_WQ0By/view?usp=drive_link", "Angel Correa", "Atletico de Madrid", "comun", 29, "Delantero"),
        Carta("https://drive.google.com/file/d/1LPqbF0X1104eqIJrKTCGfZWcno566KdV/view?usp=drive_link", "Antonio Blanco", "Deportivo Alaves", "comun", 23, "Mediocampo"),
        Carta("https://drive.google.com/file/d/1WiygPYHtnznmiTKuAx-_k03XPeeAYwT4/view?usp=drive_link", "Antonio Raillo", "Mayorca", "épica", 32, "Defensa"),
        Carta("https://drive.google.com/file/d/1AlM04d-ank2UKqlJZ96wG-nldOqs52CT/view?usp=drive_link", "Arnau Martinez", "Girona", "épica", 33, "Defensa"),
        Carta("https://drive.google.com/file/d/1fFBnAICXCoeclcS-WKI-3MrXUSVT-0oI/view?usp=drive_link", "Azpilicueta", "Atletico de Madrid", "épica", 35, "Lateral"),
        Carta("https://drive.google.com/file/d/11hDRMjBN5_6bAJvHF2wvFq7K5zSsy_h_/view?usp=drive_link", "Benjamin Lecomte", "Espanyol", "comun", 33, "Portero"),
        Carta("https://drive.google.com/file/d/1JmnLLHcPpKPA7I06gjJ7ercD3ZI1mYZ_/view?usp=drive_link", "Borja Bastón", "Leganes", "comun", 31, "Delantero"),
        Carta("https://drive.google.com/file/d/1WNHmXVHjsh-CAwP1v4E6XSCqmVViZoGt/view?usp=drive_link", "Borja Iglesias", "Real Betis", "épica", 31, "Delantero"),
        Carta("https://drive.google.com/file/d/1eB0PIRTEWe44fNYl4jCnb0Ak0sBfQ-OP/view?usp=drive_link", "Carles Aleña", "Getafe", "comun", 26, "Mediocampo"),
        Carta("https://drive.google.com/file/d/18RtFdVmzqnvrJDz34DmVqATzkEqHrTqp/view?usp=drive_link", "Carlos Benavidez", "Deportivo Alaves", "épica", 26, "Mediocampo"),
        Carta("https://drive.google.com/file/d/10lhuYfUgPAg5lEKRcIv4SDLfaXqanlMq/view?usp=drive_link", "Carvajal", "Real Madrid", "leyenda", 32, "Lateral Derecho"),
        Carta("https://drive.google.com/file/d/1zNKEXJRf-sxiLnKLKyhtzybAM0OAe4CZ/view?usp=drive_link", "Chimy Avila", "Real Madrid", "épica", 31, "Delantero"),
        Carta("https://drive.google.com/file/d/13DMojgm5SMte2Q5T7XX7VlUr0pvBVWIX/view?usp=drive_link", "Claudio Bravo", "Real Betis", "leyenda", 41, "Portero"),
        Carta("https://drive.google.com/file/d/1eypXQ8X_RVacXZToKjB5Qq0ZZ8lpVbNk/view?usp=drive_link", "Cristhian Stuani", "Girona", "épica", 28, "Delantero"),
        Carta("https://drive.google.com/file/d/1622Fa3f96oH4JIg9qmyi4_R4XaUmz94j/view?usp=drive_link", "Dani Parejo", "Villareal", "épica", 29, "Mediocampo"),
        Carta("https://drive.google.com/file/d/1jJBUWuSHOxX2WzE4Q2234WEJdI9oAlVg/view?usp=drive_link", "David Garcia", "Osasuna", "épica", 28, "Defensa"),
        Carta("https://drive.google.com/file/d/14xpIarWpU2hXdupwNAWTE9LZwuAI6lXa/view?usp=drive_link", "David Silva", "Real Sociedad", "leyenda", 38, "Mediocampo"),
        Carta("https://drive.google.com/file/d/1O8lsIcx6yn5ILQPwvtygJ72FiGDQMBTx/view?usp=drive_link", "David Soria", "Getafe", "comun", 31, "Portero"),
        Carta("https://drive.google.com/file/d/1WkEBcj1LnMiejwkplCzSjNs0LxEYgd_v/view?usp=drive_link", "Dimitri Foulquier", "Valencia", "comun", 31, "Defensa"),
        Carta("https://drive.google.com/file/d/1BQzCo2wntcPDCe6U5RpAuHOGC3-6VyXr/view?usp=drive_link", "Djene Dakonam", "Getafe", "épica", 32, "Defensa"),
        Carta("https://drive.google.com/file/d/1UYXHR5q1rmSVQC0U9i0L32sqmnSZKbH0/view?usp=drive_link", "Enes Ünal", "Getafe", "épica", 27, "Defensa"),
        Carta("https://drive.google.com/file/d/1P815TJKEhSJFM4jrtkcxGkn-4Wxj0jA2/view?usp=drive_link", "Eric Curbelo", "Las Palmas", "comun", 30, "Defensa"),
        Carta("https://drive.google.com/file/d/1dtjnh7-Q2FWudlDwxtdiJCbGN1ZQTTRu/view?usp=drive_link", "Fernando Pacheco", "Deportivo Alaves", "comun", 33, "Portero"),
        Carta("https://drive.google.com/file/d/1s0cldWZMqJHJC3CXJbSuMAQ-NXCJA3Ar/view?usp=drive_link", "Fran Beltran", "Celta de Vigo", "comun", 25, "Mediocampo"),
        Carta("https://drive.google.com/file/d/1HyOv5HfDnNU7eqXkDoQKTdpFyl25RR-B/view?usp=drive_link", "Gabri Veiga", "Celta de Vigo", "épica", 22, "Mediocampo"),
        Carta("https://drive.google.com/file/d/1MmLrEnKb6pq2a75XdlfulrofRxOTfXU4/view?usp=drive_link", "Gavi", "Barcelona", "comun", 20, "Mediocampo"),
        Carta("https://drive.google.com/file/d/1bOLhMtyRG6pmg0bi2i72Isq09UZQiTbK/view?usp=drive_link", "Gerard Moreno", "Villareal", "épica", 33, "Delantero"),
        Carta("https://drive.google.com/file/d/1s7iNHhcGdCWqiwRhjNABD0bWXqlSpG1w/view?usp=drive_link", "Giorgi Mamardashvili", "Villareal", "comun", 24, "Portero"),
        Carta("https://drive.google.com/file/d/1SmXO21xdKoW7WiFR2Xiol9jkSt3hGWB_/view?usp=drive_link", "Griezmann", "Atletico de Madrid", "leyenda", 33, "Delantero"),
        Carta("https://drive.google.com/file/d/18m7a3tv2mH9eHvqMobQBVgg-M6SE4Wy4/view?usp=drive_link", "Guido Rodriguez", "Real Betis", "épica", 30, "Mediocampo"),
        Carta("https://drive.google.com/file/d/1Va55kRGqkWcgxTDSROgWD9QyZxRqkwti/view?usp=drive_link", "Hugo Duro", "Valencia", "comun", 25, "Delantero"),
        Carta("https://drive.google.com/file/d/1vhD3CFVuUBW7Yxb-y2MiWXTBHGrSh6hY/view?usp=drive_link", "Iago Aspas", "Celta de Vigo", "leyenda", 37, "Delantero"),
        Carta("https://drive.google.com/file/d/1EC2hCrMxiyddApRhxnsVt2xypOogSfk4/view?usp=drive_link", "Iker Muniain", "Athletic de Bilbao", "leyenda", 27, "Mediocampo"),
        Carta("https://drive.google.com/file/d/1tqUXk9EmgsJ_t5Ew2znn3PCw968u74kW/view?usp=drive_link", "Iñaki Williams", "Athletic de Bilbao", "épica", 23, "Delantero"),
        Carta("https://drive.google.com/file/d/1eY0PWvpPYanrPvSLbiknvwzUa3I36K13/view?usp=drive_link", "Isi Palazon", "Rayo Vallecano", "épica", 29, "Mediocampo"),
        Carta("https://drive.google.com/file/d/1HxfYUxYbVOdYdBEPxw7BN59u7zwZzDZP/view?usp=drive_link", "Ivan Cuellar", "Leganes", "comun", 40, "Portero"),
        Carta("https://drive.google.com/file/d/1rF3MfqTvU5GIMAg20-fm6I17QdN3ASHU/view?usp=drive_link", "Ivan Rakitic", "Sevilla", "leyenda", 36, "Mediocampo"),
        Carta("https://drive.google.com/file/d/1i29Gt44Sf19jBvhI_yRC7tzn8JNXbf14/view?usp=drive_link", "Ivan Villar", "Celta de Vigo", "comun", 27, "Portero"),
        Carta("https://drive.google.com/file/d/1flO-e__oxiU546mTzr_I0MP6MGr2we2y/view?usp=drive_link", "Jan Oblak", "Atletico de Madrid", "épica", 31, "Portero"),
        Carta("https://drive.google.com/file/d/1dyVunStH9of7464GJB78USAgPpcfFZu1/view?usp=drive_link", "Jaume Costa", "Mayorca", "comun", 36, "Defensa"),
        Carta("https://drive.google.com/file/d/19Tt6-HCcIXuPl5Sw6Ncs4nEKMcesWazO/view?usp=drive_link", "Javi Galan", "Celta de Vigo", "comun", 29, "Defensa"),
        Carta("https://drive.google.com/file/d/1AiugVxBzd8jmNERo2SUQrkFY0TjPBftt/view?usp=drive_link", "Javi Guerra", "Valencia", "épica", 21, "Mediocampo"),
        Carta("https://drive.google.com/file/d/1Ys3t-mTla4I3aI-5AWKjj2brPkbLnTx0/view?usp=drive_link", "Javi Sanchez", "Real Valladolid", "épica", 27, "Defensa"),
        Carta("https://drive.google.com/file/d/1Sqa0a0husnveSXRhqd5QEMViRBYtw-lJ/view?usp=drive_link", "Jesus Navas", "Sevilla", "leyenda", 38, "Defensa"),
        Carta("https://drive.google.com/file/d/1UKGWswcXgcgoFwXwIZK9aqMAxzxNC6JW/view?usp=drive_link", "Jonathan Viera", "Las Palmas", "épica", 35, "Mediocampo"),
        Carta("https://drive.google.com/file/d/12qxhp4Gn0LQ-dDXJ9qk9wTrxpNP5i1Fq/view?usp=drive_link", "Jordi Masiq", "Real Valladolid", "comun", 35, "Portero"),
        Carta("https://drive.google.com/file/d/1YXYRrntmHHHPwn4Y0sQ1Li5xhiM5JWjo/view?usp=drive_link", "Jose Gaya", "Valencia", "épica", 29, "Defensa"),
        Carta("https://drive.google.com/file/d/1hfDnhaEZ4SwTSaF81wiYWFNaYKP8YEZQ/view?usp=drive_link", "Youssef En-Nesyri", "Sevilla", "épica", 27, "Delantero"),
        Carta("https://drive.google.com/file/d/1HbDrJCxNtnfcjiL5lz44XAvJi75V8Mnf/view?usp=sharing", "Yeray Álvarez", "Athletic del Bilbao", "épica", 23, "Defensa"),
        Carta("https://drive.google.com/file/d/1t0eWJ2S99uICQJKd_op2QF265RFW98Hu/view?usp=sharing", "Víctor Laguardia", "Deportivo Alavés", "épica", 34, "Defensa"),
        Carta("https://drive.google.com/file/d/1X7LGXysnNUnr4img73dtlrVTdHr23BFU/view?usp=sharing", "Vedat Muriqui", "Mallorca", "épica", 30, "Delantero"),
        Carta("https://drive.google.com/file/d/1MknctmX8IVqi3z0B1fwJRLwnS9EqhkoO/view?usp=sharing", "Vallejo", "Real Madrid", "leyenda", 27, "Defensa Central"),
        Carta("https://drive.google.com/file/d/1HbRSLnvDwYLVvvV1bK37QFKjwtUOR6eo/view?usp=sharing", "Unai Simón", "Athletic del Bilbao", "épica", 30, "Portero"),
        Carta("https://drive.google.com/file/d/1b01M-QKb888kVuTa93nTDcRoaYkK0W2H/view?usp=sharing", "Takefusa Kubo", "Real Sociedad", "épica", 23, "Delantero"),
        Carta("https://drive.google.com/file/d/1_W6xARvGOZGpO2q0_eck0qByFAVsJdgN/view?usp=sharing", "Stole Dimitrievski", "Rayo Vallecano", "comun", 31, "Portero"),
        Carta("https://drive.google.com/file/d/1UpFBadj7PYwFjmbhaR4NdSb5tJhVQO4v/view?usp=sharing", "Sergio León", "Real Valladolid", "comun", 35, "Delantero"),
        Carta("https://drive.google.com/file/d/1IahuYWWX3Ngql5pzCF98ZIViUJxY4TMX/view?usp=sharing", "Sergio González", "Leganés", "comun", 31, "Defensa"),
        Carta("https://drive.google.com/file/d/1OdyTT6ZY0W2YeSHnH8Sa5ClZVqoKjO_G/view?usp=sharing", "Sergi Darder", "Espanyol", "épica", 30, "Mediocampo"),
        Carta("https://drive.google.com/file/d/1J3y6WYOJ47RH85qB-aLV9OgYeBJ3wnFT/view?usp=sharing", "Sandro Ramírez", "Las Palmas", "épica", 29, "Delantero"),
        Carta("https://drive.google.com/file/d/1CP547JJxQe9sjuLaG-aWWAKE9OJxIbWC/view?usp=sharing", "Samuel Chukwueze", "Villareal", "comun", 28, "Delantero"),
        Carta("https://drive.google.com/file/d/1hQjyhNm4tKqAdIST69_6fOCG36chIVTD/view?usp=sharing", "Rubén Peña", "Osasuna", "comun", 30, "Defensa"),
        Carta("https://drive.google.com/file/d/1xB8AwrU8L6uS2DHWOTkTWEtZgnr0BnYQ/view?usp=sharing", "Rubén Pardo", "Leganés", "épica", 32, "Mediocampo"),
        Carta("https://drive.google.com/file/d/1SKjqOELgOn6kVVm712lb3VxYnUozySRs/view?usp=sharing", "Robin Le Normand", "Real Sociedad", "épica", 27, "Defensa"),
        Carta("https://drive.google.com/file/d/1NAnwA2c-bf_40bmNYdyZf-D5mVoLTWxP/view?usp=sharing", "Raúl de Tomás", "Rayo Vallecano", "épica", 30, "Delantero"),
        Carta("https://drive.google.com/file/d/1ov2w3ANS7hwjjCIb0UeGpds9rUC9spQc/view?usp=sharing", "Rajkovic", "Mallorca", "comun", 29, "Portero"),
        Carta("https://drive.google.com/file/d/1mqKf5BO-BMDNhbH1xCVLWqgKYETAxQWo/view?usp=sharing", "Pepe Reina", "Villareal", "leyenda", 24, "Portero"),
        Carta("https://drive.google.com/file/d/1gOUJOIXcjrRh9WZr4YQB1hEuyeBCf0nD/view?usp=sharing", "Paulo Gazzaniga", "Girona", "comun", 40, "Portero"),
        Carta("https://drive.google.com/file/d/1zmOuclEGYGBs5NHL8FdaR90A3qj7i1aP/view?usp=sharing", "Pau Torres", "Virrareal", "épica", 32, "Defensa"),
        Carta("https://drive.google.com/file/d/14shh6NVvRpt558e0T1B1t7WnaDQ8tsIS/view?usp=sharing", "Óscar Valentín", "Rayo Vallecano", "comun", 29, "Mediocampo"),
        Carta("https://drive.google.com/file/d/1_AZvp9x1U1_NaZxuI7Gm-C9dUgddPamT/view?usp=sharing", "Óscar Plano", "Real Valladolid", "comun", 33, "Mediocampo"),
        Carta("https://drive.google.com/file/d/1JP-xIbr1s3lo74PAptB2F-emJYgjS_pY/view?usp=sharing", "Nico Williams", "Athletic del Bilbao", "comun", 33, "Delantero"),
        Carta("https://drive.google.com/file/d/1sG40DoSTYWYYo4H5bfimz_SbQFMgNHFP/view?usp=sharing", "Nabil Fekir", "Real Betis", "épica", 30, "Mediocampo"),
        Carta("https://drive.google.com/file/d/10XZOFkL7SoQngN_A_-Z4dTRmQLua97gG/view?usp=sharing", "Monchu", "Real Valladolid", "comun", 25, "Mediocampo"),
        Carta("https://drive.google.com/file/d/1bYB_CNSHhiqT25Umr_lPdWlW1egX7f5V/view?usp=sharing", "Moi Gómez", "Osasuna", "comun", 32, "Mediocampo"),
        Carta("https://drive.google.com/file/d/1OAQSVJEOar5KKGLMwgwNTlDrrGo32rOF/view?usp=sharing", "Mikel Oyarzabal", "Real Sociedad", "épica", 27, "Delantero"),
        Carta("https://drive.google.com/file/d/1Y-aqYIvwdKNtBq5GsgyQgnUWtSwnLVav/view?usp=sharing", "Marko Dmitrović", "Sevilla", "comun", 32, "Portero"),
        Carta("https://drive.google.com/file/d/1q3xPb7U-e1rNt5xJW0TB-UQxqzwTgZwj/view?usp=sharing", "Marcos Acuña", "Sevilla", "épica", 33, "Defensa"),
        Carta("https://drive.google.com/file/d/1JWfjrWS0i2OwanDL-MjAOcZtJQvTo0FP/view?usp=sharing", "Maksimović", "Getafe", "comun", 29, "Mediocampo"),
        Carta("https://drive.google.com/file/d/1BOxGBmd5IzySiymrqbyIPSk-8MEWIxbp/view?usp=sharing", "Lunin", "Real Madrid", "comun", 25, "Portero"),
        Carta("https://drive.google.com/file/d/14tJ8v4UaxCF6DrrPxN9omBjK5m6oWs-N/view?usp=sharing", "Luis Rioja", "Alavés", "épica", 30, "Delantero"),
        Carta("https://drive.google.com/file/d/1Z3OmuhBlpoaoF1zJTznfjdQdZgxjdB71/view?usp=sharing", "Leandro Cabrera", "Espanyol", "comun", 33, "Defensa"),
        Carta("https://drive.google.com/file/d/1KhbQETt0eXSrF0yfj95iT0Nu4No887QE/view?usp=sharing", "Lamine Yamal", "Barcelona", "raro", 26, "Mediocampo"),
        Carta("https://drive.google.com/file/d/1ScnLdRuAXnGkeIqwbEutr5LPrACNKB8P/view?usp=sharing", "Koke", "Atlético del Madrid", "épica", 32, "Mediocampo"),
        Carta("https://drive.google.com/file/d/1ip7vITszqUuFa_4pR8pQEXsnXu79quTT/view?usp=sharing", "Kirian Rodríguez", "Las Palmas", "comun", 28, "Mediocampo"),
        Carta("https://drive.google.com/file/d/1rpt5ahr3XiziZi6HbAUDVQfW-YVhV1np/view?usp=sharing", "Kang-in Lee", "Mallorca ", "épica", 23, "Mediocampo"),
        Carta("https://drive.google.com/file/d/19Ppq48NGcD7SAf2QWP8AgW-UjwDH4YZ-/view?usp=sharing", "Juanpe", "Girona ", "comun", 25, "Defensa"),
        Carta("https://drive.google.com/file/d/1G2bNM-gdgdmntwNGv8D-8h3qIC9_m5aA/view?usp=sharing", "Juan Miranda", "Real Betis", "comun", 24, "Defensa"),
        Carta("https://drive.google.com/file/d/170H4veYvDg-5aiaLEjsKnhB7RQY6PKgh/view?usp=sharing", "Joselu", "Espanyol ", "épica", 24, "Delantero")
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