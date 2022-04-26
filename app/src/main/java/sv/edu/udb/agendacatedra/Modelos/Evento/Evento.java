package sv.edu.udb.agendacatedra.Modelos.Evento;

public class Evento {
    public String Titulo;
    public String Ubicacion;
    public String Descripcion;
    public  String Fecha;

    public Evento(String titulo, String ubicacion,String descripcion,String fecha) {
        this.Titulo = titulo;
        this.Fecha = fecha;
        this.Descripcion = descripcion;
        this.Ubicacion = ubicacion;

    }
}
