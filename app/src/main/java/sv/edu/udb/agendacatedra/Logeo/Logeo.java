package sv.edu.udb.agendacatedra.Logeo;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;

import sv.edu.udb.agendacatedra.MainActivity;

public class Logeo implements Serializable
{
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    private MainActivity Actividad;
    // [END declare_auth]

    private GoogleSignInClient mGoogleSignInClient;
    public Logeo(MainActivity v)
    {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        Actividad = v;
        mGoogleSignInClient = GoogleSignIn.getClient(v, gso);
    }

    public GoogleSignInAccount sesionAbierta()
    {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(Actividad);
        return account;
    }

    public Intent iniciarSesion()
    {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        return signInIntent;
    }

    public GoogleSignInClient cerrarSesion()
    {
      return  mGoogleSignInClient;

    }

    public int getRcSignIn()
    {
        return RC_SIGN_IN;
    }

    public String getTag()
    {
        return TAG;
    }

}
