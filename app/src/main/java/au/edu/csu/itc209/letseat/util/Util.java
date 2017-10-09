package au.edu.csu.itc209.letseat.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.support.design.widget.TextInputLayout;
import android.util.Patterns;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;

import java.text.SimpleDateFormat;
import java.util.Date;

import au.edu.csu.itc209.letseat.R;
import au.edu.csu.itc209.letseat.constant.Constants;
import au.edu.csu.itc209.letseat.database.LocationDatabaseHelper;
import au.edu.csu.itc209.letseat.database.LocationDatabaseMetadata;

public final class Util {

    private static MaterialDialog.Builder dialogBuilder;
    private static MaterialDialog dialog;

    /* private constructor to prevent instantiantion*/
    private Util(){}

    public static boolean isValidEmail(EditText edtEmail, TextInputLayout wrapper) {
        final String email = edtEmail.getText().toString().trim();
        boolean goodEmail = (email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches());

        if(!goodEmail) {
            if (wrapper != null) {
                wrapper.setError("Please enter a valid email address");
                wrapper.setErrorEnabled(true);
            } else {
                edtEmail.setError("Please enter a valid email address!");
            }
            return false;
        }

        if(wrapper != null) {
            wrapper.setErrorEnabled(false);
        }

        return goodEmail;
    }

    public static boolean isEmptyOrNull(EditText edt, TextInputLayout wrapper) {
        final String txt = edt.getText().toString().trim();
        boolean valid = (txt != null && !txt.equals(""));

        if(!valid) {
            if(wrapper != null) {
                wrapper.setError("Please enter value!");
                wrapper.setErrorEnabled(true);
            } else {
                edt.setError("Please enter value!");
            }
            return false;
        }

        if(wrapper!= null) {wrapper.setErrorEnabled(false);}

        return valid;
    }

    public static String getStringTime() {
        return new SimpleDateFormat(Constants.SIMPLE_DATE_FORMAT_FOR_IMAGE_FILE_NAME).format(new Date());
    }

    public static void showLoading(Context ctx) {
        initDialog(ctx);
        dialog.show();
    }

    public static void hideLoading(Context ctx) {
        initDialog(ctx);
        dialog.dismiss();
    }

    private static void initDialog(Context ctx) {
        initBuilder(ctx);
        if(dialog==null) {
            dialog = dialogBuilder.build();
        }
    }

    private static void initBuilder(Context ctx) {
        if(dialogBuilder==null) {
            dialogBuilder = new MaterialDialog.Builder(ctx)
                    .title(R.string.progress_dialog)
                    .content(R.string.please_wait)
                    .progress(true, 0);
        }
    }

    public static int saveLocation(Context ctx, Location location) {
        if(location == null) { return -1; }
        SQLiteDatabase db = (new LocationDatabaseHelper(ctx)).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LocationDatabaseMetadata.COLUMN_NAME_LATITUDE, location.getLatitude());
        values.put(LocationDatabaseMetadata.COLUMN_NAME_LONGITUDE, location.getLongitude());

        return (int) db.insert(LocationDatabaseMetadata.TABLE_NAME, null, values);
    }

    public static Location getLocation(Context ctx) {
        SQLiteDatabase db = (new LocationDatabaseHelper(ctx)).getWritableDatabase();

        String [] fields = {LocationDatabaseMetadata.COLUMN_NAME_LATITUDE, LocationDatabaseMetadata.COLUMN_NAME_LONGITUDE};

        Cursor cursor = db.query(LocationDatabaseMetadata.TABLE_NAME, fields, null, null, null, null, null);

        Location location = new Location("");
        location.setLatitude(0.0);
        location.setLongitude(0.0);

        while (cursor.moveToNext()) {
            location.setLatitude(cursor.getDouble(cursor.getColumnIndexOrThrow(LocationDatabaseMetadata.COLUMN_NAME_LATITUDE)));
            location.setLongitude(cursor.getDouble(cursor.getColumnIndexOrThrow(LocationDatabaseMetadata.COLUMN_NAME_LONGITUDE)));
        }

        return location;
    }
}
