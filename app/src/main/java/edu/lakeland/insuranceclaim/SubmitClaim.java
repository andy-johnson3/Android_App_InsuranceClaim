package edu.lakeland.insuranceclaim;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class SubmitClaim extends AppCompatActivity implements OnMapReadyCallback {
    public static final String TAG = "SubmitClaim";
    Claim currentClaim;
    final int PERMISSION_REQUEST_CAMERA = 103;
    final int CAMERA_REQUEST = 1888;
    final int MAP_REQUEST = 0;
    GoogleMap gMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_claim);

        initTextChangedEvents();
        initSubmitClaimButton();
        initSpinnerAuto();
        initSpinnerDriver();
        initImageButton();
        initCancelButton();
        initViewClaimLocationButton();
        currentClaim = new Claim();

        hideKeyboard();

    }

    private void initSpinnerDriver() {
        Spinner spinnerDriver = (Spinner) findViewById(R.id.spinnerDriver);

        DriverDataSource dds = new DriverDataSource(SubmitClaim.this);
        ArrayList<String> driverNames = new ArrayList<String>();

        try{
            dds.open();
            ArrayList<Driver> drivers = dds.getDrivers();
            dds.close();
            Log.d(TAG, "initSpinner: " + drivers.size() + driverNames.size());
            for (Driver d : drivers){
                driverNames.add(d.getfName() + " " + d.getlName());
                Log.d(TAG, d.getfName() + " " + d.getlName());
            }
        } catch (Exception e) {
            Toast.makeText(SubmitClaim.this, "Error: Autos not found", Toast.LENGTH_LONG).show();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, driverNames);

        spinnerDriver.setAdapter(adapter);

        spinnerDriver.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.d("item", (String) parent.getItemAtPosition(position));
                currentClaim.setDriverName((String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    private void initSpinnerAuto() {

        Spinner spinnerAuto = (Spinner) findViewById(R.id.spinnerAuto);

        AutoDataSource ads = new AutoDataSource(SubmitClaim.this);
        ArrayList<String> autoNames = new ArrayList<String>();

        try{
            ads.open();
            ArrayList<Auto> autos = ads.getAutos();
            ads.close();
            Log.d(TAG, "initSpinner: " + autos.size() + autoNames.size());
            for (Auto a : autos){
                autoNames.add(a.getMake() + " " + a.getModel());
                Log.d(TAG, a.getMake() + " " + a.getModel());
            }
        } catch (Exception e) {
            Toast.makeText(SubmitClaim.this, "Error: Autos not found", Toast.LENGTH_LONG).show();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, autoNames);

        spinnerAuto.setAdapter(adapter);

        spinnerAuto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.d("item", (String) parent.getItemAtPosition(position));
                currentClaim.setAuto((String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    private void refreshMap(){
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentMap);
        mapFragment.getMapAsync(this);
    }

    private void initTextChangedEvents() {
//        final EditText etDriver = findViewById(R.id.etDriver);
//        etDriver.addTextChangedListener(new TextWatcher() {
//
//            public void afterTextChanged(Editable s) {
//                currentClaim.setDriverName(etDriver.getText().toString());
//            }
//
//            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//                //  Auto-generated method stub
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                //  Auto-generated method stub
//            }
//        });

//        final EditText etAuto = findViewById(R.id.etAuto);
//        etAuto.addTextChangedListener(new TextWatcher() {
//            public void afterTextChanged(Editable s) {
//                currentClaim.setAuto(etAuto.getText().toString());
//            }
//
//            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//                //  Auto-generated method stub
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                //  Auto-generated method stub
//            }
//        });

        final EditText etDate = findViewById(R.id.etDate);
        etDate.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
                currentClaim.setDate(etDate.getText().toString());
            }
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
        });

        final TextView tvMainLatitude = findViewById(R.id.tvMainLatitude);
        tvMainLatitude.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
                double lat = Double.parseDouble(tvMainLatitude.getText().toString());
                currentClaim.setLatitude(lat);
            }
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
        });

        final TextView tvMainLongitude = findViewById(R.id.tvMainLongitude);
        tvMainLongitude.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
                double longitude = Double.parseDouble(tvMainLongitude.getText().toString());
                currentClaim.setLongitude(longitude);
            }
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
        });
    }

    private void initImageButton() {
        ImageButton ib = findViewById(R.id.imageMeal);
        ib.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (ContextCompat.checkSelfPermission(SubmitClaim.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(SubmitClaim.this, android.Manifest.permission.CAMERA)) {
                            Snackbar.make(findViewById(R.id.activity_main), "The app needs permission to take pictures.", Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            ActivityCompat.requestPermissions(SubmitClaim.this, new String[]{ android.Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
                                        }
                                    })
                                    .show();
                        } else {
                            ActivityCompat.requestPermissions(SubmitClaim.this, new String[]{android.Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
                        }
                    }
                    else {
                        takePhoto();
                    }
                } else {
                    takePhoto();
                }
            }
        });
    }

    public void takePhoto(){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                } else {
                    Toast.makeText(SubmitClaim.this, "You will not be able to save contact pictures from this app", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    private void initSubmitClaimButton() {
        Button saveButton = findViewById(R.id.btnSubmitClaim);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public static final String TAG = "MainActivity";
            @Override
            public void onClick(View view) {

                ClaimDataSource cds = new ClaimDataSource(SubmitClaim.this);
                try {
                    cds.open();

                    if (cds.insertClaim(currentClaim)){
                        Toast.makeText(SubmitClaim.this, "Claim Submitted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(SubmitClaim.this, "Error: Claim not Submitted", Toast.LENGTH_LONG).show();
                    }

                    cds.close();

                    startActivity(new Intent(SubmitClaim.this, MainActivity.class));
                }
                catch (Exception e) {
                    Toast.makeText(SubmitClaim.this, "Error: Claim not Submitted", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initCancelButton() {
        Button ratingsList = findViewById(R.id.btnCancel);
        ratingsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubmitClaim.this, MainActivity.class));
            }
        });
    }

    private void initViewClaimLocationButton() {
        Button btnSetLocation = findViewById(R.id.btnSetLocation);
        btnSetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubmitClaim.this, ClaimLocationActivity.class);
                startActivityForResult(intent,MAP_REQUEST);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MAP_REQUEST){
            if (resultCode == RESULT_OK){
                double latitude = data.getDoubleExtra("latitude", 0.0);
                currentClaim.setLatitude(latitude);
                TextView tvMainLatitude = findViewById(R.id.tvMainLatitude);
                tvMainLatitude.setText(currentClaim.getLatitude().toString());

                double longitude = data.getDoubleExtra("longitude", 0.0);
                currentClaim.setLongitude(longitude);
                TextView tvMainLongitude = findViewById(R.id.tvMainLongitude);
                tvMainLongitude.setText(currentClaim.getLongitude().toString());

                refreshMap();
            }
        }

        if (requestCode == CAMERA_REQUEST) {
            if (resultCode == RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                Bitmap scaledPhoto = Bitmap.createScaledBitmap(photo, 144, 144, true);
                ImageButton imageContact = findViewById(R.id.imageMeal);
                imageContact.setImageBitmap(scaledPhoto);
                currentClaim.setPicture(scaledPhoto);
            }
        }
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        EditText etRestaurant = findViewById(R.id.etDriver);
//        imm.hideSoftInputFromWindow(etRestaurant.getWindowToken(), 0);
//        EditText etDish = findViewById(R.id.etAuto);
//        imm.hideSoftInputFromWindow(etDish.getWindowToken(), 0);
        EditText etDate = findViewById(R.id.etDate);
        imm.hideSoftInputFromWindow(etDate.getWindowToken(), 0);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        Point size = new Point();
        WindowManager w = getWindowManager();
        w.getDefaultDisplay().getSize(size);
        int measuredWidth = size.x;
        int measuredHeight = size.y;

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        LatLng point = new LatLng(currentClaim.getLatitude(), currentClaim.getLongitude());
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(point));

        gMap.addMarker(new MarkerOptions().position(point).title(currentClaim.getAuto()));
        builder.include(point);

        gMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), measuredWidth, measuredHeight, 450));

    }
}
