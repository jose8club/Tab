package jose.tab.service;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
/**
 * Created by Alicia on 20/01/2017.
 */
public class DatabaseOpenHelper extends SQLiteAssetHelper{

    private static final String DATABASE_NAME = "Prueba.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
