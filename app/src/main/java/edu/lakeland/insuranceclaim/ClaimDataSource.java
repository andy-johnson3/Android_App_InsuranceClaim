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

public class ClaimDataSource {
    private SQLiteDatabase database;
    private ClaimDBHelper dbHelper;
    public static final String TAG = "ClaimDataSource";

        public ClaimDataSource(Context context) {
            dbHelper = new ClaimDBHelper(context);
        }

        public void open() throws SQLException {
            database = dbHelper.getWritableDatabase();
        }

        public void close() {
            dbHelper.close();
        }

        public boolean insertClaim(Claim c) {
            boolean didSucceed = false;
            try {
                ContentValues initialValues = new ContentValues();

                initialValues.put("date", c.getClaimDate());
                initialValues.put("driver", c.getDriverName());
                initialValues.put("auto", c.getAuto());
                initialValues.put("latitude", c.getLatitude());
                initialValues.put("longitude", c.getLongitude());
                if (c.getPicture() != null) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    c.getPicture().compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] photo = baos.toByteArray();
                    initialValues.put("photo", photo);
                }

                didSucceed = database.insert("claims", null, initialValues) > 0;
                Log.d(TAG, "insertClaim: " + didSucceed);
            }
            catch (Exception e) {
                //Do nothing -will return false if there is an exception
            }
            return didSucceed;
        }

        public Claim getSpecificClaim(int claimID) {
            Claim claim = new Claim();
            String query = "SELECT * FROM claims WHERE _id =" + claimID;
            Cursor cursor = database.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                claim.setClaimID(cursor.getInt(0));
                claim.setDate(cursor.getString(1));
                claim.setDriverName(cursor.getString(2));
                claim.setAuto(cursor.getString(3));
                claim.setLatitude(cursor.getDouble(4));
                claim.setLongitude(cursor.getDouble(5));
                byte[] photo = cursor.getBlob(6);
                if (photo != null) {
                    ByteArrayInputStream imageStream = new ByteArrayInputStream(photo);
                    Bitmap thePicture= BitmapFactory.decodeStream(imageStream);
                    claim.setPicture(thePicture);
                }
            }
            return claim;
        }

//        public int getLastclaimID() {
//            int lastId;
//            try {
//                String query = "Select MAX(_id) from contact";
//                Cursor cursor = database.rawQuery(query, null);
//
//                cursor.moveToFirst();
//                lastId = cursor.getInt(0);
//                cursor.close();
//            }
//            catch (Exception e) {
//                lastId = -1;
//            }
//            return lastId;
//        }

    public ArrayList<Claim> getClaims() {
        ArrayList<Claim> claimList = new ArrayList<Claim>();
        try {
            String query = "SELECT * FROM claims";
            Log.d(TAG, "getClaims: " + query);
            Cursor cursor = database.rawQuery(query, null);

            Claim newClaim;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                newClaim = new Claim();
                newClaim.setClaimID(cursor.getInt(0));
                newClaim.setDate(cursor.getString(1));
                newClaim.setDriverName(cursor.getString(2));
                newClaim.setAuto(cursor.getString(3));
                newClaim.setLatitude(cursor.getDouble(4));
                newClaim.setLongitude(cursor.getDouble(5));
                byte[] photo = cursor.getBlob(6);
                if (photo != null) {
                    ByteArrayInputStream imageStream = new ByteArrayInputStream(photo);
                    Bitmap thePicture= BitmapFactory.decodeStream(imageStream);
                    newClaim.setPicture(thePicture);
                }
                claimList.add(newClaim);
                cursor.moveToNext();
            }
            cursor.close();
            Log.d(TAG, "getClaims: " + claimList.size());
        }
        catch (Exception e) {
            claimList = new ArrayList<Claim>();
        }
        return claimList;
    }


}
