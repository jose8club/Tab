package jose.tab.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alicia on 20/01/2017.
 */
public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Constructor privado para evitar creacion de clases externas
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Retorna una instancia singleton de DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Abre la conexion a la base de datos
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }


    /**
     * Cierra la conexion a la base de datos
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Llena la lista con los ID de cada de obra de arte
     * @return
     */
    public ArrayList<String> list_id() {
        ArrayList<String> lista = new ArrayList<>();
        String q = "SELECT * FROM Arte";
        Cursor registros = database.rawQuery(q, null);
        if(registros.moveToFirst()){
            do{
                lista.add(registros.getString(0));
                //lista.add(registros.getString(1));
            }while(registros.moveToNext());
        }
        return lista;
    }

    /**
     * Busqueda de cada obra de arte de acuerdo a su idobra
     * @param idobra
     * @return
     */
    public String[] search(String idobra) {
        String[] datos= new String[14];
        String q = "SELECT * FROM Arte WHERE idobra ='"+idobra+"'";
        //String q = "SELECT * FROM Arte";
        Cursor registros = database.rawQuery(q, null);
        if(registros.moveToFirst()){
            for(int i = 0 ; i<13;i++){
                datos[i]= registros.getString(i);

            }
            datos[13]= Integer.toString(registros.getCount());
        }else{

            datos[13]= "No encontrado: " + Integer.toString(registros.getCount());
        }
        database.close();
        return datos;
    }
}
