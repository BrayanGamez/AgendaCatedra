package sv.edu.udb.agendacatedra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import sv.edu.udb.agendacatedra.Logeo.Logeo;

public class Inicio extends AppCompatActivity {

    private String Correo;
    private String Nombre;
    private Uri Img;
    private TextView txtNombre;
    private ImageView imgFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        txtNombre = findViewById(R.id.txtNombre);
        imgFoto = findViewById(R.id.imgFoto);
        findViewById(R.id.btnCerrarSesion).setOnClickListener(this::onClick);
        //Cargamos los datos pasados
        Nombre = getIntent().getExtras().getString("Nombre");
        Img = Uri.parse(getIntent().getExtras().getString("Img"));

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
}