package sv.edu.udb.agendacatedra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AgregarEvento extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {
    private GoogleMap mMap;

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

        //Obteniendo el fragmento del mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getLocalizacion();
    }

    private void getLocalizacion() {
        int permiso = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if(permiso == PackageManager.PERMISSION_DENIED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }

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
/*
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        // agregando el marco y las posiciones
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,10));
    }
 */
@Override
public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;
    //Verificando Permisos
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        return;
    }
    mMap.setMyLocationEnabled(true);

    mMap.getUiSettings().setMyLocationButtonEnabled(false);
    //Aca es donde busco el contenedor de google maps en caso de que este en la activity actual, en caos de no servir este codigo Ver el comentado de arriba
    LocationManager locationManager = (LocationManager) AgregarEvento.this.getSystemService(Context.LOCATION_SERVICE);
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            LatLng miUbicacion = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(miUbicacion).title("ubicacion actual"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(miUbicacion));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(miUbicacion)
                    .zoom(14)
                    .bearing(90)
                    .tilt(45)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

}

}