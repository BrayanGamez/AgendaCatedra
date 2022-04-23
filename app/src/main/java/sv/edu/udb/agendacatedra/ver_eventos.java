package sv.edu.udb.agendacatedra;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ver_eventos extends AppCompatActivity implements AdapterView.OnItemLongClickListener {

    private ListView listview;
    private ArrayAdapter<String> arrayAdapter;
    private SQLiteDatabase db;
    private int Dia;
    private int Mes;
    private int anio;
    private String Correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_eventos);
        listview = (ListView) findViewById(R.id.listEvents);
        listview.setOnItemLongClickListener(this);
        Dia =  getIntent().getExtras().getInt("dia");
        Mes = getIntent().getExtras().getInt("mes");
        anio = getIntent().getExtras().getInt("anio");
        Correo = getIntent().getExtras().getString("correo");

        DBSQLite bd = new DBSQLite(getApplication(),"Agenda",null,1);
        db= bd.getReadableDatabase();


        String cadena = Dia+"-"+Mes+"-"+anio;
        String sqlQuery = "select * from eventos where fecha = '"+cadena+"' and usuario ='"+Correo+"'";
        Cursor c;

        String titulo,ubicacion,fecha,descripcion;
        try {
            c=db.rawQuery(sqlQuery,null);
            arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
            if(c.moveToFirst())
            {
                do {
                    titulo = c.getString(1);
                    ubicacion = c.getString(2);
                    fecha = c.getString(3);
                    descripcion = c.getString(4);
                    arrayAdapter.add(titulo+","+ubicacion+","+fecha+","+descripcion);
                }while(c.moveToNext());
                listview.setAdapter(arrayAdapter);
            }
            else
            {
                this.finish();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplication(),"Error: "+e.getMessage(),Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }



    private void eliminar(String dato)
    {
        try {
            arrayAdapter.remove(dato);
            listview.setAdapter(arrayAdapter);
        }
        catch (Exception e)
        {
            Toast.makeText(getApplication(),"Error: "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        CharSequence []items = new CharSequence[2];
        items[0] = "Eliminar Evento";
        items[1] = "Cancelar";
        builder.setTitle("Eliminar evento").
                setItems(
                        items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(i==0)
                                {
                                    //Opcion para eliminar el evento pulsado
                                }
                            }
                        }
                );
        AlertDialog dialog = builder.create();
        dialog.show();
        return false;
    }
}