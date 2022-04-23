package sv.edu.udb.agendacatedra;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AgregarEvento extends AppCompatActivity implements View.OnClickListener {

    private int Dia;
    private int Mes;
    private int anio;
    private EditText editFecha;
    private String Correo;

    private EditText Titulo;
    private EditText ubicacion;
    private EditText descripcion;

    private Button btnGuardar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_evento);
        editFecha = findViewById(R.id.editFecha);

        Dia =  getIntent().getExtras().getInt("dia");
        Mes = getIntent().getExtras().getInt("mes");
        anio = getIntent().getExtras().getInt("anio");
        Correo = getIntent().getExtras().getString("correo");

        editFecha.setText(String.valueOf(Dia)+"-"+String.valueOf(Mes)+"-"+String.valueOf(anio));
        Titulo = (EditText) findViewById(R.id.editTitulo);
        ubicacion = (EditText) findViewById(R.id.editUbicacion);
        descripcion = (EditText) findViewById(R.id.editDescripcion);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==btnGuardar.getId())
        {//Guardamos los datos
            DBSQLite bd = new DBSQLite(getApplication(),"Agenda",null,1);
            SQLiteDatabase db = bd.getWritableDatabase();
            String sqlQuery = "insert into eventos"+
                    "(nombreEvento,ubicacion,fecha,descripcion,usuario)"+
                    "values('"+Titulo.getText()+"' , '"+ubicacion.getText()+"' , '"+
                    editFecha.getText()+"','"+descripcion.getText()+"' , '"+Correo+"')";

            try {
                db.execSQL(sqlQuery);

                Titulo.setText("");
                ubicacion.setText("");
                descripcion.setText("");
            }
            catch (Exception e)
            {
                Toast.makeText(getApplication(),"Error: "+e.getMessage(),Toast.LENGTH_SHORT).show();

            }
            this.finish();
        }
    }
}