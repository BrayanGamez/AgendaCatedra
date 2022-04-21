package sv.edu.udb.agendacatedra;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

public class AgregarEvento extends AppCompatActivity {

    private int Dia;
    private int Mes;
    private int anio;
    private EditText editFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_evento);
        editFecha = findViewById(R.id.editFecha);

        Dia =  getIntent().getExtras().getInt("dia");
        Mes = getIntent().getExtras().getInt("mes");
        anio = getIntent().getExtras().getInt("anio");

        editFecha.setText(String.valueOf(Dia)+"/"+String.valueOf(Mes)+"/"+String.valueOf(anio));
    }

}