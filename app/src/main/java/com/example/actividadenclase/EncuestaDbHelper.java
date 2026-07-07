package com.example.actividadenclase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EncuestaDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Encuestas.db";

    public EncuestaDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EncuestaContract.SQL_CREATE_PREGUNTAS);
        db.execSQL(EncuestaContract.SQL_CREATE_RESPUESTAS);

        // --- INSERTAR PREGUNTAS DIRECTAMENTE EN LA BASE ---
        db.execSQL("INSERT INTO " + EncuestaContract.PreguntasEntry.TABLE_NAME +
                " (" + EncuestaContract.PreguntasEntry.COLUMN_ID_PREG + ", " +
                EncuestaContract.PreguntasEntry.COLUMN_TEXTO + ") VALUES (1, 'Como califica la atencion?')");
        db.execSQL("INSERT INTO " + EncuestaContract.PreguntasEntry.TABLE_NAME +
                " (" + EncuestaContract.PreguntasEntry.COLUMN_ID_PREG + ", " +
                EncuestaContract.PreguntasEntry.COLUMN_TEXTO + ") VALUES (2, 'Recomendaria nuestro servicio?')");
        db.execSQL("INSERT INTO " + EncuestaContract.PreguntasEntry.TABLE_NAME +
                " (" + EncuestaContract.PreguntasEntry.COLUMN_ID_PREG + ", " +
                EncuestaContract.PreguntasEntry.COLUMN_TEXTO + ") VALUES (3, 'Que le parecio la rapidez del tramite?')");
        db.execSQL("INSERT INTO " + EncuestaContract.PreguntasEntry.TABLE_NAME +
                " (" + EncuestaContract.PreguntasEntry.COLUMN_ID_PREG + ", " +
                EncuestaContract.PreguntasEntry.COLUMN_TEXTO + ") VALUES (4, 'Tiene alguna sugerencia adicional?')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(EncuestaContract.SQL_DELETE_PREGUNTAS);
        db.execSQL(EncuestaContract.SQL_DELETE_RESPUESTAS);
        onCreate(db);
    }
}
