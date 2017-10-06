package au.edu.csu.itc209.letseat.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import au.edu.csu.itc209.letseat.models.User;


public class UserDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "LetsEat";
    private static final int DB_VERSION = 1;
    private static final String TABLE_USER = "USER";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_FNAME = "FNAME";
    private static final String COLUMN_LNAME = "LNAME";
    private static final String COLUMN_EMAIL = "EMAIL";
    private static final String COLUMN_PROFILE_URL = "PROFILE_URL";
    private static final String COLUMN_DATECREATED = "DATECREATED";
    private static final String COLUMN_PASSWORD = "PASSWORD";

    public UserDatabaseHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void setWriteAheadLoggingEnabled(boolean enabled) {
        super.setWriteAheadLoggingEnabled(enabled);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDb = "CREATE TABLE " + TABLE_USER + " ( "
                + COLUMN_ID + " INTEGER(4) PRIMARY KEY, "
                + COLUMN_FNAME + " TEXT, "
                + COLUMN_LNAME + " TEXT, "
                + COLUMN_EMAIL + " TEXT"
                + COLUMN_PROFILE_URL + " TEXT"
                + COLUMN_PASSWORD + " TEXT"
                + COLUMN_DATECREATED + " TIMESTAMP"
                + " )";
        db.execSQL(createDb);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<User> getAllUsers(){

        return null;
    }

    public User getUserWithId(int id) {


        return null;
    }

    public void updateUser(int id, String fName, String lName, String email) {

    }
}
