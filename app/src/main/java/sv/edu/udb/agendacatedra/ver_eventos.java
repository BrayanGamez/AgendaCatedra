package sv.edu.udb.agendacatedra;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import sv.edu.udb.agendacatedra.Modelos.Evento.AdaptadorEvento;
import sv.edu.udb.agendacatedra.Modelos.Evento.Evento;

public class ver_eventos extends AppCompatActivity implements AdapterView.OnItemLongClickListener {

    private ListView listview;
    private AdaptadorEvento arrayAdapter;
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
           ArrayList<Evento> arregloEventos = new ArrayList<Evento>();
            arrayAdapter = new AdaptadorEvento(this,arregloEventos);
            if(c.moveToFirst())
            {
                do {
                    titulo = c.getString(1);
                    ubicacion = c.getString(2);
                    fecha = c.getString(3);
                    descripcion = c.getString(4);
                    arrayAdapter.add(new Evento(titulo,ubicacion,descripcion,fecha));
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



    private void eliminar(Evento dato)
    {

        String sqlQuery = "delete from eventos where nombreEvento="+"'"+dato.Titulo+"'"+"and"+
                " ubicacion="+"'"+dato.Ubicacion+"'"+"and"+" fecha="+"'"+dato.Fecha+"'"+"and"+" descripcion="+
                "'"+dato.Descripcion+"'"+"and"+" usuario="+"'"+Correo+"'";
        try {
            arrayAdapter.remove(dato);
            listview.setAdapter(arrayAdapter);
                    db.execSQL(sqlQuery);
            Toast.makeText(getApplication(),"Evento eliminado",Toast.LENGTH_SHORT).show();
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
                                    eliminar((Evento) adapterView.getItemAtPosition(i));
                                }
                            }
                        }
                );
        AlertDialog dialog = builder.create();
        dialog.show();
        return false;
    }
}