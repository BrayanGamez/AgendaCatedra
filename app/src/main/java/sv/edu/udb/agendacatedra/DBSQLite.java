package sv.edu.udb.agendacatedra;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBSQLite extends SQLiteOpenHelper {

    private String sql = "create table eventos ("+
            "idEvento int identity,"+
            "nombreEvento varchar(40),"+
            "ubicacion varchar(60),"+
            "fecha date,"+
            "descripcion varchar(60),"+
            "usuario varchar(50))";

    public DBSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "Agenda.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
