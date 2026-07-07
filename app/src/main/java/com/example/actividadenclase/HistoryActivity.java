package com.example.actividadenclase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView rvHistorial;
    private SurveyHistoryAdapter adapter;
    private EncuestaDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        rvHistorial = findViewById(R.id.rv_historial);
        rvHistorial.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new EncuestaDbHelper(this);

        cargarHistorial();
    }

    private void cargarHistorial() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                EncuestaContract.RespuestasEntry.TABLE_NAME,
                null, null, null, null, null,
                EncuestaContract.RespuestasEntry.COLUMN_FECHA + " DESC"
        );

        List<SurveyHistoryAdapter.SurveyResponse> responses = new ArrayList<>();
        while (cursor.moveToNext()) {
            String fecha = cursor.getString(cursor.getColumnIndexOrThrow(EncuestaContract.RespuestasEntry.COLUMN_FECHA));
            String respuesta = cursor.getString(cursor.getColumnIndexOrThrow(EncuestaContract.RespuestasEntry.COLUMN_RESPUESTA));
            int idPreg = cursor.getInt(cursor.getColumnIndexOrThrow(EncuestaContract.RespuestasEntry.COLUMN_ID_PREG_FK));
            
            responses.add(new SurveyHistoryAdapter.SurveyResponse(fecha, "Pregunta ID: " + idPreg + " - Respuesta: " + respuesta));
        }
        cursor.close();

        adapter = new SurveyHistoryAdapter(responses);
        rvHistorial.setAdapter(adapter);
    }
}
