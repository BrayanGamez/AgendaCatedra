package sv.edu.udb.agendacatedra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import sv.edu.udb.agendacatedra.Logeo.Logeo;

public class Inicio extends AppCompatActivity implements CalendarView.OnDateChangeListener {

    private CalendarView calendario;
    private String Nombre;
    private String Correo;
    private Uri Img;
    private TextView txtNombre;
    private ImageView imgFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        calendario = (CalendarView) findViewById(R.id.calendario);
        calendario.setOnDateChangeListener(this);

        txtNombre = findViewById(R.id.txtNombre);
        imgFoto = findViewById(R.id.imgFoto);
        findViewById(R.id.btnCerrarSesion).setOnClickListener(this::onClick);
        //Cargamos los datos pasados
        Nombre = getIntent().getExtras().getString("Nombre");
        Img = Uri.parse(getIntent().getExtras().getString("Img"));
        Correo = getIntent().getExtras().getString("Correo");

        txtNombre.setText(Nombre);
        Picasso.get().load(Img).into(imgFoto);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            // ...
            case R.id.btnCerrarSesion:
                signOut();
                break;
            // ...
        }
    }

    private void signOut() {
        Intent main = new Intent(this,MainActivity.class);
        startActivity(main);
    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
        AlertDialog.Builder constructor = new AlertDialog.Builder(this);
        CharSequence[] items = new CharSequence[3];
        items[0] = "Agregar Evento";
        items[1] = "ver Eventos";
        items[2] = "Cancelar";

        int dia,mes,anio;
        dia = i;
        mes = i1+1;
        anio = i2;
        //por defecto el mes inicia en cero por lo que aumentamos uno

        constructor.setTitle("Seleccione una opcion")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i==0)
                        {
                            //Agregamos evento
                            Intent intent = new Intent(getApplication(),AgregarEvento.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("dia",dia);
                            bundle.putInt("mes",mes);
                            bundle.putInt("anio",anio);
                            bundle.putString("usuario",Correo);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                        else if(i==1)
                        {
                            //Vemos los eventos de ese dia
                        }
                        else
                        {
                            //cerrar las opciones
                            return;
                        }
                    }
                });
        AlertDialog mensajeOpcion = constructor.create();
        mensajeOpcion.show();
    }
}