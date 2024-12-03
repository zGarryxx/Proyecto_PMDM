import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.proyectosobres.Carta

class DBcontrol(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "basededatosfutbol.db"
        private const val DATABASE_VERSION = 3
    }

    override fun onCreate(db: SQLiteDatabase) {
        Log.d("DBcontrol", "Creando las tablas de la base de datos...")

        // Crear tabla usuarios
        val createUsuariosTable = """
            CREATE TABLE usuarios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                correo TEXT NOT NULL,
                password TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createUsuariosTable)

        // Crear tabla cartas
        val createCartasTable = """
        CREATE TABLE cartas (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            imagen TEXT,
            jugador TEXT,
            equipo TEXT,
            rareza TEXT CHECK(rareza IN ('comun', 'raro', 'épica', 'leyenda')),
            edad INTEGER,
            posicion TEXT
        )
    """.trimIndent()
        db.execSQL(createCartasTable)

        // Crear tabla intermedia usuario_cartas
        val createUsuarioCartasTable = """
            CREATE TABLE usuario_cartas (
                usuario_id INTEGER,
                carta_id INTEGER,
                PRIMARY KEY (usuario_id, carta_id),
                FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
                FOREIGN KEY (carta_id) REFERENCES cartas(id)
            )
        """.trimIndent()
        db.execSQL(createUsuarioCartasTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 3) {
            // Actualizar la base de datos a la versión 3
            db.execSQL("DROP TABLE IF EXISTS usuario_cartas")
            db.execSQL("DROP TABLE IF EXISTS usuarios")
            db.execSQL("DROP TABLE IF EXISTS cartas")
            onCreate(db)
        }
    }

    // Insertar usuario
    fun insertUsuario(correo: String, password: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("correo", correo)
            put("password", password)
        }
        val result = db.insert("usuarios", null, values)
        Log.d("DBcontrol", "Insertar usuario: Resultado = $result")
        return result
    }

    // Asignar carta a usuario
    fun assignCartaToUsuario(usuarioId: Long, cartaId: Long): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("usuario_id", usuarioId)
            put("carta_id", cartaId)
        }
        val result = db.insert("usuario_cartas", null, values)
        Log.d("DBcontrol", "Asignar carta a usuario: Resultado = $result")
        return result
    }

    // Insertar carta
    fun insertCarta(imagen: String, jugador: String, equipo: String, rareza: String, edad: Int, posicion: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("imagen", imagen)
            put("jugador", jugador)
            put("equipo", equipo)
            put("rareza", rareza)
            put("edad", edad)
            put("posicion", posicion)
        }
        val result = db.insert("cartas", null, values)
        Log.d("DBcontrol", "Insertar carta: Resultado = $result")
        return result
    }

    fun cartaExists(jugador: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT 1 FROM cartas WHERE jugador = ?", arrayOf(jugador))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    fun updateCarta(carta: Carta) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("imagen", carta.imagen)
            put("jugador", carta.jugador)
            put("equipo", carta.equipo)
            put("rareza", carta.rareza)
            put("edad", carta.edad)
            put("posicion", carta.posicion)
        }
        db.update("cartas", values, "jugador = ?", arrayOf(carta.jugador))
    }
}