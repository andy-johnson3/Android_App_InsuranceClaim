package edu.lakeland.insuranceclaim;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

public class AutoDataSource {
    private SQLiteDatabase database;
    private ClaimDBHelper dbHelper;
    public static final String TAG = "AutoDataSource";

        public AutoDataSource(Context context) {
            dbHelper = new ClaimDBHelper(context);
        }

        public void open() throws SQLException {
            database = dbHelper.getWritableDatabase();
        }

        public void close() {
            dbHelper.close();
        }

        public ArrayList<Auto> getAutos() {
            ArrayList<Auto> autoList = new ArrayList<Auto>();
            try {
                String query = "SELECT * FROM autos";
                Log.d(TAG, "getAutos: " + query);
                Cursor cursor = database.rawQuery(query, null);

                Auto newAuto;
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    newAuto = new Auto();
                    newAuto.setMake(cursor.getString(1));
                    newAuto.setModel(cursor.getString(2));
                    newAuto.setLicensePlate(cursor.getString(3));
                    autoList.add(newAuto);
                    cursor.moveToNext();
                }
                cursor.close();
                Log.d(TAG, "getAutos: " + autoList.size());
            }
            catch (Exception e) {
                autoList = new ArrayList<Auto>();
            }
            return autoList;
        }

}
