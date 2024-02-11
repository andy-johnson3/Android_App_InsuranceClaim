package edu.lakeland.insuranceclaim;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ClaimDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "insurance.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String CREATE_TABLE_CLAIMS =
            "create table claims (_id integer primary key autoincrement, "
                    + "date text, "
                    + "driver text, "
                    + "auto text, "
                    + "latitude double, "
                    + "longitude double, "
                    + "photo blob);";

    public static final String CREATE_TABLE_DRIVERS =
            "create table drivers (_id integer primary key autoincrement, "
                    + "fName text, "
                    + "lName text, "
                    + "birthday text);";

    public static final String CREATE_TABLE_AUTOS =
            "create table autos (_id integer primary key autoincrement, "
                    + "make text, "
                    + "model text, "
                    + "licensePlate text);";

    public static final String INSERT_DRIVERS =
            "INSERT into drivers (fName, lName, birthday) VALUES "
                    + "('Andy', 'Johnson', '7/30/1988'), "
                    + "('Anna', 'Johnson', '2/29/1988')";

    public static final String INSERT_AUTOS =
            "INSERT into autos (make, model, licensePlate) VALUES "
                    + "('Chevrolet', 'Sierra 1500', 'aaa-123'), "
                    + "('Subara', 'Outback', 'aaa-321')";


    public ClaimDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_CLAIMS);
        db.execSQL(CREATE_TABLE_DRIVERS);
        db.execSQL(CREATE_TABLE_AUTOS);
        db.execSQL(INSERT_DRIVERS);
        db.execSQL(INSERT_AUTOS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ClaimDBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS claims");
        db.execSQL("DROP TABLE IF EXISTS drivers");
        db.execSQL("DROP TABLE IF EXISTS autos");
        onCreate(db);
    }
}
