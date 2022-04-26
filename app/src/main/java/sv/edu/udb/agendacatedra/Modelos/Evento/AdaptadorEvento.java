package sv.edu.udb.agendacatedra.Modelos.Evento;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import sv.edu.udb.agendacatedra.R;

public class AdaptadorEvento extends ArrayAdapter<Evento> {

    public AdaptadorEvento(@NonNull Context context, ArrayList<Evento> eventos) {
        super(context, 0,eventos);
    }

    @Override
    public View getView(int posicion, View VistaConvertida, ViewGroup Pariente)
    {
        Evento evento = getItem(posicion);
        if(VistaConvertida==null)
        {
            VistaConvertida = LayoutInflater.from(getContext()).inflate(R.layout.item_evento,Pariente,false);
        }
        TextView verTitulo = (TextView) VistaConvertida.findViewById(R.id.txtTituloValue);
        TextView verUbicacion = (TextView) VistaConvertida.findViewById(R.id.txtUbicacionValue);
        TextView verDescripcion = (TextView) VistaConvertida.findViewById(R.id.txtDescripcionValue);
        TextView verFecha = (TextView) VistaConvertida.findViewById(R.id.txtFechaValue);

        verTitulo.setText(evento.Titulo);
        verUbicacion.setText(evento.Ubicacion);
        verFecha.setText(evento.Fecha);
        verDescripcion.setText(evento.Descripcion);

        return VistaConvertida;

    }

}
