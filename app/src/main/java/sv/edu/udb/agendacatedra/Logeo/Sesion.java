package sv.edu.udb.agendacatedra.Logeo;

import android.net.Uri;

public class Sesion
{
    private String Nombre;
    private  String Correo;
    private Uri imgFoto;

    public void setNombre(String nombre)
    {
        this.Nombre = nombre;
    }

    public void  setCorreo(String correo)
    {
        this.Correo = correo;
    }

    public  void setImgFoto(Uri foto)
    {
        this.imgFoto = foto;
    }

    public String getNombre()
    {
        return Nombre;
    }

    public String getCorreo()
    {
        return Correo;
    }

    public  Uri getImgFoto()
    {
        return imgFoto;
    }

}
