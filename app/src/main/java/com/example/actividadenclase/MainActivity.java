package com.example.actividadenclase;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout containerPreguntas;
    private EncuestaDbHelper dbHelper;
    private List<View> responseViews = new ArrayList<>();
    private List<Integer> questionIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        containerPreguntas = findViewById(R.id.container_preguntas);
        Button btnGuardar = findViewById(R.id.btn_guardar);
        Button btnVerHistorial = findViewById(R.id.btn_ver_historial);

        dbHelper = new EncuestaDbHelper(this);

        cargarPreguntas();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarEncuesta();
            }
        });

        btnVerHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void cargarPreguntas() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                EncuestaContract.PreguntasEntry.TABLE_NAME,
                null, null, null, null, null, null
        );

        while (cursor.moveToNext()) {
            int idPreg = cursor.getInt(cursor.getColumnIndexOrThrow(EncuestaContract.PreguntasEntry.COLUMN_ID_PREG));
            String texto = cursor.getString(cursor.getColumnIndexOrThrow(EncuestaContract.PreguntasEntry.COLUMN_TEXTO));

            TextView tvPregunta = new TextView(this);
            tvPregunta.setText(texto);
            tvPregunta.setTextSize(18);
            tvPregunta.setPadding(0, 16, 0, 8);

            EditText etRespuesta = new EditText(this);
            etRespuesta.setHint("Escriba su respuesta aqui...");

            containerPreguntas.addView(tvPregunta);
            containerPreguntas.addView(etRespuesta);

            responseViews.add(etRespuesta);
            questionIds.add(idPreg);
        }
        cursor.close();
    }

    private void guardarEncuesta() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        boolean success = true;

        for (int i = 0; i < responseViews.size(); i++) {
            EditText et = (EditText) responseViews.get(i);
            String respuesta = et.getText().toString().trim();

            if (respuesta.isEmpty()) {
                Toast.makeText(this, "Por favor responda todas las preguntas", Toast.LENGTH_SHORT).show();
                return;
            }

            ContentValues values = new ContentValues();
            values.put(EncuestaContract.RespuestasEntry.COLUMN_ID_PREG_FK, questionIds.get(i));
            values.put(EncuestaContract.RespuestasEntry.COLUMN_RESPUESTA, respuesta);

            long newRowId = db.insert(EncuestaContract.RespuestasEntry.TABLE_NAME, null, values);
            if (newRowId == -1) {
                success = false;
            }
        }

        if (success) {
            Toast.makeText(this, "Encuesta guardada con exito", Toast.LENGTH_SHORT).show();
            limpiarCampos();
        } else {
            Toast.makeText(this, "Error al guardar la encuesta", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarCampos() {
        for (View view : responseViews) {
            ((EditText) view).setText("");
        }
    }
}
