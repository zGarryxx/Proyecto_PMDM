import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBcontrol(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "database.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        Log.d("DBcontrol", "Creando las tablas de la base de datos...")

        // Crear tabla equipo
        val createEquipoTable = """
            CREATE TABLE equipo (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT,
                numeroDeJugadores INTEGER,
                comunidadAutonoma TEXT,
                clasificacion INTEGER,
                logo TEXT
            )
        """
        db.execSQL(createEquipoTable)

        // Crear tabla jugador
        val createJugadorTable = """
            CREATE TABLE jugador (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT,
                dorsal INTEGER,
                posicion TEXT,
                rareza TEXT,
                equipoId INTEGER,
                edad INTEGER,
                fotoJugador TEXT,
                FOREIGN KEY(equipoId) REFERENCES equipo(id)
            )
        """
        db.execSQL(createJugadorTable)

        // Crear tabla usuario
        val createUsuarioTable = """
        CREATE TABLE usuario (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            rol TEXT CHECK(rol IN ('usuario', 'admin')) DEFAULT 'usuario',
            nombre TEXT,
            apellido TEXT,
            correo TEXT UNIQUE,
            password TEXT,
            equipoFavorito TEXT,
            tokens INTEGER
        )
    """
        db.execSQL(createUsuarioTable)

        // Crear tabla usuarios_jugadores
        val createUsuariosJugadoresTable = """
        CREATE TABLE usuarios_jugadores (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            usuarioId INTEGER,
            jugadorId INTEGER,
            FOREIGN KEY(usuarioId) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE,
            FOREIGN KEY(jugadorId) REFERENCES jugador(id) ON DELETE CASCADE ON UPDATE CASCADE
        )
    """
        db.execSQL(createUsuariosJugadoresTable)

        // Crear tabla tienda
        val createTiendaTable = """
        CREATE TABLE tienda (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombreSobre TEXT,
            precioTokens INTEGER,
            rareza TEXT CHECK(rareza IN ('común', 'épico', 'legendario')),
            cantidadJugadores INTEGER,
            descripcion TEXT
        )
    """
        db.execSQL(createTiendaTable)

        // Crear tabla usuarios_tienda
        val createUsuariosTiendaTable = """
        CREATE TABLE usuarios_tienda (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            usuarioId INTEGER,
            productoId INTEGER,
            FOREIGN KEY(usuarioId) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE,
            FOREIGN KEY(productoId) REFERENCES tienda(id) ON DELETE CASCADE ON UPDATE CASCADE
        )
    """
        db.execSQL(createUsuariosTiendaTable)

        Log.d("DBcontrol", "Tablas creadas exitosamente")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d("DBcontrol", "Actualizando base de datos de versión $oldVersion a $newVersion")
        db.execSQL("DROP TABLE IF EXISTS jugador")
        db.execSQL("DROP TABLE IF EXISTS equipo")
        onCreate(db)
    }

    // Insertar equipo
    fun insertEquipo(nombreEquipo: String, numeroDeJugadores: Int, comunidadAutonoma: String, clasificacion: Int, logo: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("nombre", nombreEquipo)
            put("numeroDeJugadores", numeroDeJugadores)
            put("comunidadAutonoma", comunidadAutonoma)
            put("clasificacion", clasificacion)
            put("logo", logo)
        }
        val result = db.insert("equipo", null, values)
        Log.d("DBcontrol", "Insertar equipo: Resultado = $result")
        return result
    }

    // Insertar jugador
    fun insertJugador(nombreJugador: String, dorsal: Int, posicion: String, rareza: String, equipoId: Int, edad: Int, fotoJugador: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("nombre", nombreJugador)
            put("dorsal", dorsal)
            put("posicion", posicion)
            put("rareza", rareza)
            put("equipoId", equipoId)
            put("edad", edad)
            put("fotoJugador", fotoJugador)
        }
        val result = db.insert("jugador", null, values)
        Log.d("DBcontrol", "Insertar jugador: Resultado = $result")
        return result
    }

    // Insertar usuario
    fun insertUsuario(nombre: String, apellido: String, correo: String, password: String, equipoFavorito: String, tokens: Int): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("rol", "usuario")
            put("nombre", nombre)
            put("apellido", apellido)
            put("correo", correo)
            put("password", password)
            put("equipoFavorito", equipoFavorito)
            put("tokens", tokens)
        }
        val result = db.insert("usuario", null, values)
        Log.d("DBcontrol", "Insertar usuario: Resultado = $result")
        return result
    }

    // Obtener el ID del equipo por nombre
    fun obtenerEquipoId(nombreEquipo: String): Long? {
        val db = readableDatabase
        val cursor = db.query("equipo", arrayOf("id"), "nombre = ?", arrayOf(nombreEquipo), null, null, null)
        val id = if (cursor.moveToFirst()) cursor.getLong(cursor.getColumnIndexOrThrow("id")) else -1L
        cursor.close()
        Log.d("DBcontrol", "Obtener ID equipo: $id")
        return id
    }

    // Obtener los datos de un jugador por ID
    fun obtenerDatosJugador(idJugador: Int): Map<String, String>? {
        val db = readableDatabase
        val cursor = db.rawQuery("""
            SELECT jugador.*, equipo.nombre AS nombre_equipo, equipo.logo AS logo
            FROM jugador
            LEFT JOIN equipo ON jugador.equipoId = equipo.id
            WHERE jugador.id = ?
        """, arrayOf(idJugador.toString()))

        return if (cursor.moveToFirst()) {
            mapOf(
                "nombre_jugador" to cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                "dorsal" to cursor.getInt(cursor.getColumnIndexOrThrow("dorsal")).toString(),
                "posicion" to cursor.getString(cursor.getColumnIndexOrThrow("posicion")),
                "rareza" to cursor.getString(cursor.getColumnIndexOrThrow("rareza")),
                "equipoId" to cursor.getInt(cursor.getColumnIndexOrThrow("equipoId")).toString(),
                "edad" to cursor.getInt(cursor.getColumnIndexOrThrow("edad")).toString(),
                "foto_jugador" to cursor.getString(cursor.getColumnIndexOrThrow("fotoJugador")),
                "nombre_equipo" to cursor.getString(cursor.getColumnIndexOrThrow("nombre_equipo")),
                "logo" to cursor.getString(cursor.getColumnIndexOrThrow("logo"))
            )
        } else {
            null
        }.also {
            cursor.close()
        }
    }

    // Vaciar la base de datos
    fun vaciarBaseDeDatos() {
        val db = writableDatabase
        db.execSQL("DELETE FROM jugador")
        db.execSQL("DELETE FROM equipo")
        db.close()
        Log.d("DBcontrol", "Base de datos vaciada.")
    }
}
