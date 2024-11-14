package co.unipiloto.proyect;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user_accounts.db, activities.db, votos.db, horarios.db, genhorarios.db, reservas.db, marcadores.db, rutas.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_ACTIVITIES = "activities";
    public static final String TABLE_NAME = "users";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_FULL_NAME = "full_name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_CUIDAD = "cuidad";
    public static final String COLUMN_FECHANACIMIENTO = "fechanacimiento";
    public static final String COLUMN_GENDER = "gender";

    //Lista de deportes
    private static final String[] DEPORTES = {"Fútbol", "Baloncesto", "Voleibol", "Natación"};


    private static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_USERNAME + " TEXT PRIMARY KEY," +
                    COLUMN_PASSWORD + " TEXT," +
                    COLUMN_FULL_NAME + " TEXT," +
                    COLUMN_EMAIL + " TEXT," +
                    COLUMN_CUIDAD + " TEXT," +
                    COLUMN_FECHANACIMIENTO + " TEXT," +
                    COLUMN_GENDER + " TEXT)";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
        String createActivitiesTable = "CREATE TABLE " + TABLE_ACTIVITIES + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT, " +
                "descripcion TEXT, " +
                "ubicacion TEXT, " +
                "estado TEXT DEFAULT 'Pendiente')";
        db.execSQL(createActivitiesTable);

        //Crear la tabla de votos por usuario y actividad
        String createVotosTable = "CREATE TABLE votos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "usuario_id TEXT, " +
                "actividad_id INTEGER, " +
                "FOREIGN KEY (actividad_id) REFERENCES activities(id))";
        db.execSQL(createVotosTable);

        // Crear la tabla de horarios si no existe
        String createHorariosTable = "CREATE TABLE horarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "fecha TEXT, " +
                "hora TEXT, " +
                "localidad TEXT)";
        db.execSQL(createHorariosTable);

        // Crear la tabla de horarios si no existe
        String createGenHorariosTable = "CREATE TABLE genhorarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "fecha TEXT, " +
                "hora TEXT, " +
                "deporte TEXT, " +
                "estado TEXT DEFAULT 'disponible', " +
                "usuario TEXT DEFAULT NULL, " +
                "UNIQUE(fecha, hora, deporte))";
        db.execSQL(createGenHorariosTable);

        //Crear la tabla de rutas para el mapa
        String CREATE_TABLE_MARCADORES = "CREATE TABLE marcadores (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "latitud REAL, " +
                "longitud REAL, " +
                "comentario TEXT)";
        db.execSQL(CREATE_TABLE_MARCADORES);

        //Crear la tabla de marcadores para el mapa
        String CREATE_TABLE_RUTAS = "CREATE TABLE rutas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "inicio_latitud REAL, " +
                "inicio_longitud REAL, " +
                "fin_latitud REAL, " +
                "fin_longitud REAL, " +
                "comentario TEXT)";
        db.execSQL(CREATE_TABLE_RUTAS);

        //Crear la tabla de marcadores para la reserva
        String createReservasTable = "CREATE TABLE reservas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "fecha TEXT, " +
                "hora TEXT, " +
                "estado TEXT DEFAULT 'disponible', " +
                "usuario TEXT DEFAULT NULL, " +
                "UNIQUE(fecha, hora))";
        db.execSQL(createReservasTable);


    }

    // Método para agregar una nueva actividad
    public boolean addActivity(String nombre, String descripcion, String ubicacion, String estado) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nombre", nombre);
        contentValues.put("descripcion", descripcion);
        contentValues.put("ubicacion", ubicacion);
        contentValues.put("estado", estado);

        long result = db.insert(TABLE_ACTIVITIES, null, contentValues);
        return result != -1; // Retorna true si la inserción fue exitosa
    }

    // Método para obtener todas las actividades
    public Cursor getAllActivities() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM activities", null);
    }

    public boolean updateActivity(int id, String nombre, String descripcion, String ubicacion, String estado) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nombre", nombre);
        contentValues.put("descripcion", descripcion);
        contentValues.put("ubicacion", ubicacion);
        contentValues.put("estado", estado);

        return db.update("activities", contentValues, "id = ?", new String[]{String.valueOf(id)}) > 0;
    }

    // Método para obtener una actividad por su ID
    public Cursor getActivityById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM activities WHERE id = ?", new String[]{String.valueOf(id)});
    }

    // Método para eliminar una actividad
    public boolean deleteActivity(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("activities", "id = ?", new String[]{String.valueOf(id)}) > 0;
    }

    // Método para actualizar el estado de una actividad
    public boolean updateActivityEstado(int id, String nuevoEstado) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("estado", nuevoEstado);

        return db.update("activities", contentValues, "id = ?", new String[]{String.valueOf(id)}) > 0;
    }

    /////////////////////////////----------------Clase Voto----------///////////////////////////
    // Verificar si el usuario ya ha votado por la actividad
    public boolean yaHaVotado(String usuarioId, int actividadId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM votos WHERE usuario_id = ? AND actividad_id = ?",
                new String[]{usuarioId, String.valueOf(actividadId)});
        boolean yaVotado = cursor.getCount() > 0;
        cursor.close();
        return yaVotado;
    }

    // Registrar el voto del usuario para una actividad
    public boolean registrarVoto(String usuarioId, int actividadId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("usuario_id", usuarioId);
        contentValues.put("actividad_id", actividadId);
        long result = db.insert("votos", null, contentValues);
        return result != -1;  // Retorna true si la inserción fue exitosa
    }

    // Obtener las actividades en las que el usuario ha votado
    public Cursor getActividadesVotadasPorUsuario(String usuarioId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT activities.id, activities.nombre, activities.descripcion FROM activities " +
                "INNER JOIN votos ON activities.id = votos.actividad_id WHERE votos.usuario_id = ?", new String[]{usuarioId});
    }

    // Eliminar un voto de un usuario para una actividad específica
    public boolean eliminarVoto(String usuarioId, int actividadId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("votos", "usuario_id = ? AND actividad_id = ?", new String[]{usuarioId, String.valueOf(actividadId)}) > 0;
    }


    // Obtener la cantidad de votos por cada actividad
    public Cursor getCantidadVotosPorActividad() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT activities.nombre, COUNT(votos.actividad_id) AS cantidad_votos " +
                "FROM activities LEFT JOIN votos ON activities.id = votos.actividad_id " +
                "GROUP BY activities.id";
        return db.rawQuery(query, null);
    }

    // Obtener los usuarios registrados
    public Cursor getUsuariosRegistrados() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT username FROM users", null);
    }


    ////////////////////////////////------Crear Horario-------------------////////////////////////
    public boolean insertarHorario(String fecha, String hora, String localidad) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fecha", fecha);
        contentValues.put("hora", hora);
        contentValues.put("localidad", localidad);

        long resultado = db.insert("horarios", null, contentValues);
        return resultado != -1;  // Retorna true si la inserción fue exitosa
    }

    // Método para obtener los horarios creados
    public Cursor obtenerHorarios() {
        SQLiteDatabase db = this.getReadableDatabase();
        // Consulta todos los horarios ordenados por el más reciente
        return db.rawQuery("SELECT * FROM horarios ORDER BY id DESC", null);
    }

    // Método para obtener un horario específico por ID
    public Cursor obtenerHorarioPorId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM horarios WHERE id = ?", new String[]{String.valueOf(id)});
    }

    // Método para actualizar un horario
    public boolean actualizarHorario(int id, String fecha, String hora, String localidad) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fecha", fecha);
        contentValues.put("hora", hora);
        contentValues.put("localidad", localidad);

        return db.update("horarios", contentValues, "id = ?", new String[]{String.valueOf(id)}) > 0;
    }

    // Método para eliminar un horario de la base de datos
    public boolean eliminarHorario(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("horarios", "id = ?", new String[]{String.valueOf(id)}) > 0;
    }

    ////----------------- Prueba generar horario ----/////

    public void generarHorariosAleatoriosDelMes() {
        SQLiteDatabase db = this.getWritableDatabase();
        Calendar calendario = Calendar.getInstance();

        int mes = calendario.get(Calendar.MONTH);
        int anio = calendario.get(Calendar.YEAR);
        int ultimoDiaDelMes = calendario.getActualMaximum(Calendar.DAY_OF_MONTH);

        String[] horas = {"12:00", "15:00","18:00"};

        for (int dia = 1; dia <= ultimoDiaDelMes; dia++) {
            calendario.set(anio, mes, dia);
            int diaDeLaSemana = calendario.get(Calendar.DAY_OF_WEEK);

            // Solo generar horarios de lunes a viernes
            if (diaDeLaSemana != Calendar.SATURDAY && diaDeLaSemana != Calendar.SUNDAY) {
                for (String hora : horas) {
                    String fecha = dia + "/" + (mes + 1) + "/" + anio;

                    // Asignar un deporte aleatoriamente
                    String deporte = DEPORTES[new Random().nextInt(DEPORTES.length)];

                    ContentValues contentValues = new ContentValues();
                    contentValues.put("fecha", fecha);
                    contentValues.put("hora", hora);
                    contentValues.put("deporte", deporte);
                    contentValues.put("estado", "disponible");
                    contentValues.put("usuario", (String) null);

                    try {
                        db.insertOrThrow("genhorarios", null, contentValues);
                    } catch (SQLiteConstraintException e) {
                        // Si ya existe esa combinación de fecha y hora, ignorar y continuar
                    }
                }
            }
        }
    }

    public boolean registrarHorariov(int id, String usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("estado", "reservado");
        contentValues.put("usuario", usuario);

        int rowsUpdated = db.update("genhorarios", contentValues, "id = ? AND estado = ?", new String[]{String.valueOf(id), "disponible"});
        return rowsUpdated > 0;
    }

    public boolean cancelarHorariov(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("estado", "disponible");
        contentValues.put("usuario", (String) null);

        int rowsUpdated = db.update("genhorarios", contentValues, "id = ? AND estado = ?", new String[]{String.valueOf(id), "reservado"});
        return rowsUpdated > 0;
    }

    public Cursor obtenerHorariosv() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT id, fecha, hora, deporte, estado, usuario FROM genhorarios";
        return db.rawQuery(query, null);
    }


    ////////////////////////////--------------Reserva--------------///////////////////////
    public void generarReservasMuseo() {
        SQLiteDatabase db = this.getWritableDatabase();
        Calendar calendario = Calendar.getInstance();

        int mes = calendario.get(Calendar.MONTH);
        int anio = calendario.get(Calendar.YEAR);
        int ultimoDiaDelMes = calendario.getActualMaximum(Calendar.DAY_OF_MONTH);

        String[] horas = {"10:00", "12:00","15:00"};

        for (int dia = 1; dia <= ultimoDiaDelMes; dia++) {
            calendario.set(anio, mes, dia);
            int diaDeLaSemana = calendario.get(Calendar.DAY_OF_WEEK);

            if (diaDeLaSemana != Calendar.SATURDAY && diaDeLaSemana != Calendar.SUNDAY) {
                for (String hora : horas) {
                    String fecha = dia + "/" + (mes + 1) + "/" + anio;

                    ContentValues contentValues = new ContentValues();
                    contentValues.put("fecha", fecha);
                    contentValues.put("hora", hora);
                    contentValues.put("estado", "disponible");
                    contentValues.put("usuario", (String) null);

                    try {
                        db.insertOrThrow("reservas", null, contentValues);
                    } catch (SQLiteConstraintException e) {
                        // Manejar duplicados si ya existe la fecha y hora
                    }
                }
            }
        }

    }

    public boolean reservarHorario(int id, String usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("estado", "reservado");
        contentValues.put("usuario", usuario);

        return db.update("reservas", contentValues, "id = ? AND estado = 'disponible'", new String[]{String.valueOf(id)}) > 0;
    }

    public boolean cancelarReserva(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("estado", "disponible");
        contentValues.put("usuario", (String) null);

        return db.update("reservas", contentValues, "id = ?", new String[]{String.valueOf(id)}) > 0;
    }

    public Cursor obtenerReservas() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM reservas ORDER BY fecha, hora", null);
    }




    ////////////////////////////////////////////////-----Grafico Estadistico-----//////////////////////////////
    // Método para obtener el total de votos por localidad
    public Map<String, Integer> obtenerVotosPorLocalidad() {
        SQLiteDatabase db = this.getReadableDatabase();
        Map<String, Integer> votosPorLocalidad = new HashMap<>();
        Cursor cursor = db.rawQuery("SELECT localidad, SUM(votos) AS totalVotos FROM activities GROUP BY localidad", null);

        while (cursor.moveToNext()) {
            String localidad = cursor.getString(0);
            int totalVotos = cursor.getInt(1);
            votosPorLocalidad.put(localidad, totalVotos);
        }
        cursor.close();
        return votosPorLocalidad;
    }

    ///////////////////////////////////////---------------MAPA--------------------///////////////////////////////
    public boolean guardarRuta(LatLng puntoInicio, LatLng puntoFin, double distancia) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("inicio_lat", puntoInicio.latitude);
        values.put("inicio_lng", puntoInicio.longitude);
        values.put("fin_lat", puntoFin.latitude);
        values.put("fin_lng", puntoFin.longitude);
        values.put("distancia", distancia);

        return db.insert("rutas", null, values) != -1;
    }

    ////////////////////////////////////////----------------Consultas------------------//////////////////////////////////
    public Cursor obtenerActividadesConVotos(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT activities.id AS actividad_id, activities.nombre, activities.descripcion, " +
                " activities.ubicacion, activities.estado, COUNT(votos.usuario_id) AS votos " +
                " FROM activities" +
                " LEFT JOIN votos ON activities.id = votos.actividad_id " +
                " GROUP BY activities.id, activities.nombre, activities.descripcion, " +
                " activities.ubicacion, activities.estado";
        return db.rawQuery(query, null);
    }






    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
