package sv.edu.udb.agendacatedra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;

import sv.edu.udb.agendacatedra.Logeo.Logeo;

public class MainActivity extends AppCompatActivity {

    private Logeo sesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         sesion = new Logeo(this);
        findViewById(R.id.sign_in_button).setOnClickListener(this::onClick);
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount account = sesion.sesionAbierta();
        if(account!=null)
        {
            ActualizarIU(account.getEmail(),account.getDisplayName(),account.getPhotoUrl());
        }
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                Logearse();
                break;
        }
    }

    private void Logearse() {
        Intent signInIntent = sesion.iniciarSesion();
        startActivityForResult(signInIntent, sesion.getRcSignIn());
    }

    @Override
    //Api Auth GoogleSigIng Codigo de su documentacion
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == sesion.getRcSignIn()) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    //Api Auth GoogleSigIng Codigo de su documentacion
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            ActualizarIU(account.getEmail(),account.getDisplayName(),account.getPhotoUrl());
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(sesion.getTag(), "signInResult:failed code=" + e.getStatusCode());
            ActualizarIU("","",null);
        }
    }

    private void ActualizarIU(String Correo,String Nombre, Uri Img)
    {
        if(Correo == "")
        {
            setContentView(R.layout.activity_main);
        }
        else {
            String foto = "https://www.seekpng.com/png/detail/46-463314_v-th-h-user-profile-icon.png";
            if (Img!=null)
            {
                foto = Img.toString();
            }
            Bundle extras = new Bundle();
            extras.putString("Correo",Correo);
            extras.putString("Nombre",Nombre);
            extras.putString("Img",foto);

            Intent intent = new Intent(this,Inicio.class);
            intent.putExtras(extras);
            sesion.cerrarSesion().signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    startActivity(intent);
                }
            });
        }
    }


}