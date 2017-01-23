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
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    public DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
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
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Verifica si se abrio la base de datos
     * @return
     */
    public String isOpen(){
        if(this.database.isOpen()){
            return "Abierto";
        }else{
            return "Cerrado";
        }
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Busqueda de cada obra de arte deacuerdo a su idobra
     * @param idobra
     * @return
     */
    public String[] search(String idobra) {
        String[] datos= new String[14];
        String q = "SELECT * FROM Arte WHERE idobra ='"+idobra+"'";
        Cursor registros = database.rawQuery(q, null);
        if(registros.moveToFirst()){
            for(int i = 0 ; i<13;i++){
                datos[i]= registros.getString(i);

            }
            datos[13]= "Encontrado";
        }else{
            datos[13] = q;
            //datos[13]= "No se encontro a "+idobra;
        }
        database.close();
        return datos;
    }
}
