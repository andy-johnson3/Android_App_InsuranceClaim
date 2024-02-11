package edu.lakeland.insuranceclaim;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

public class DriverDataSource {
    private SQLiteDatabase database;
    private ClaimDBHelper dbHelper;
    public static final String TAG = "DriverDataSource";

        public DriverDataSource(Context context) {
            dbHelper = new ClaimDBHelper(context);
        }

        public void open() throws SQLException {
            database = dbHelper.getWritableDatabase();
        }

        public void close() {
            dbHelper.close();
        }

        public ArrayList<Driver> getDrivers() {
            ArrayList<Driver> driverList = new ArrayList<Driver>();
            try {
                String query = "SELECT * FROM drivers";
                Log.d(TAG, "getDrivers: " + query);
                Cursor cursor = database.rawQuery(query, null);

                Driver newDriver;
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    newDriver = new Driver();
                    newDriver.setfName(cursor.getString(1));
                    newDriver.setlName(cursor.getString(2));
                    newDriver.setbDay(cursor.getString(3));
                    driverList.add(newDriver);
                    cursor.moveToNext();
                }
                cursor.close();
                Log.d(TAG, "getAutos: " + driverList.size());
            }
            catch (Exception e) {
                driverList = new ArrayList<Driver>();
            }
            return driverList;
        }

}
