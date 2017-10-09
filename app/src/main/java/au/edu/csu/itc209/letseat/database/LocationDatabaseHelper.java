package au.edu.csu.itc209.letseat.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class LocationDatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Letseat.db";
    private static final String SQL_CREATE_LOCATION_TABLE = String
            .format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s REAL, %s REAL)",
                    LocationDatabaseMetadata.TABLE_NAME,
                    LocationDatabaseMetadata._ID,
                    LocationDatabaseMetadata.COLUMN_NAME_LATITUDE,
                    LocationDatabaseMetadata.COLUMN_NAME_LONGITUDE);

    private static final String SQL_DELETE_LOCATION_TABLE = "DROP TABLE IF EXISTS " + LocationDatabaseMetadata.TABLE_NAME;

    public LocationDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_LOCATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_LOCATION_TABLE);
        onCreate(db);
    }

}

