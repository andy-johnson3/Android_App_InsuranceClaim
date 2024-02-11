package edu.lakeland.insuranceclaim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    final int PERMISSION_REQUEST_PHONE = 102;
    boolean isDarkModeOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAgent();
        initCoverageDetails();
        initDrivers();
        initAutos();
        initDarkModeButton();
        initSubmitAClaimButton();
        initViewClaimsButton();
        initCallFunction();
    }

    private void initDarkModeButton() {
        Button darkModeBtn = findViewById(R.id.btnDarkMode);

        if (getSharedPreferences("InsuranceClaimPreferences",
                Context.MODE_PRIVATE).getInt("darkMode", 0) == AppCompatDelegate.MODE_NIGHT_NO){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            isDarkModeOn = false;
            darkModeBtn.setText("Enable Dark Mode");
        }
        else if (getSharedPreferences("InsuranceClaimPreferences",
                Context.MODE_PRIVATE).getInt("darkMode", 0) == AppCompatDelegate.MODE_NIGHT_YES){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            isDarkModeOn = true;
            darkModeBtn.setText("Disable Dark Mode");
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        darkModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDarkModeOn){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    darkModeBtn.setText("Enable Dark Mode");
                    getSharedPreferences("InsuranceClaimPreferences",
                            Context.MODE_PRIVATE).edit().putInt("darkMode", 1).apply();
                    isDarkModeOn = false;
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    darkModeBtn.setText("Disable Dark Mode");
                    getSharedPreferences("InsuranceClaimPreferences",
                            Context.MODE_PRIVATE).edit().putInt("darkMode", 2).apply();
                    isDarkModeOn = true;
                }
            }

        });
    }

    private void initAgent() {
        TextView tvAgentName = findViewById(R.id.tvAgent);
        tvAgentName.setText("Bob Miller");
        TextView tvAgentPhoneNumber = findViewById(R.id.tvAgentPhoneNumber);
        tvAgentPhoneNumber.setText("888-888-1234");

    }

    private void initCoverageDetails() {
        TextView tvLiability = findViewById(R.id.tvLiabilityAmount);
        TextView tvDeductible = findViewById(R.id.tvDeductibleAmount);

        tvLiability.setText("$100k/$300k");
        tvDeductible.setText("$500");
    }

    private void initDrivers() {
        DriverDataSource dds = new DriverDataSource(this);

        try {
            dds.open();
            ArrayList<Driver> drivers = dds.getDrivers();
            Log.d(TAG, "getDrivers: " + drivers.size());
            dds.close();
            if (drivers.size() > 0) {
                RecyclerView rvDrivers = findViewById(R.id.rvDrivers);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                rvDrivers.setLayoutManager(layoutManager);
                DriversAdapter driversAdapter = new DriversAdapter(drivers, this);
                rvDrivers.setAdapter(driversAdapter);
            }
            else {
                Toast.makeText(this, "No drivers found", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e) {
            Toast.makeText(this, "Error retrieving drivers", Toast.LENGTH_LONG).show();
        }
    }

    private void initAutos() {
        AutoDataSource ads = new AutoDataSource(this);

        try {
            ads.open();
            ArrayList<Auto> autos = ads.getAutos();
            Log.d(TAG, "getAutos: " + autos.size());
            ads.close();
            if (autos.size() > 0) {
                RecyclerView rvAutos = findViewById(R.id.rvAutos);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                rvAutos.setLayoutManager(layoutManager);
                AutosAdapter autosAdapter = new AutosAdapter(autos, this);
                rvAutos.setAdapter(autosAdapter);
            }
            else {
                Toast.makeText(this, "No drivers found", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e) {
            Toast.makeText(this, "Error retrieving drivers", Toast.LENGTH_LONG).show();
        }
    }

    private void initCallFunction() {
        TextView tvAgentPhoneNumber = findViewById(R.id.tvAgentPhoneNumber);
        tvAgentPhoneNumber.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View arg0) {
                checkPhonePermission(tvAgentPhoneNumber.getText().toString());
                return false;
            }
        });
    }

    private void checkPhonePermission(String phoneNumber) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.CALL_PHONE)) {

                    Snackbar.make(findViewById(R.id.activity_main), "This app requires this permission to place a call.", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.CALL_PHONE },PERMISSION_REQUEST_PHONE);
                                }
                            })
                            .show();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.CALL_PHONE},PERMISSION_REQUEST_PHONE);
                }
            } else {
                callContact(phoneNumber);
            }
        } else {
            callContact(phoneNumber);
        }
    }

    private void callContact(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if ( Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return  ;
        }
        else {
            startActivity(intent);
        }
    }

    private void initSubmitAClaimButton() {
        Button submitClaimButton = findViewById(R.id.btnSubmitClaim);
        submitClaimButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SubmitClaim.class));
            }
        });
    }

    private void initViewClaimsButton() {
        Button viewClaimsButton = findViewById(R.id.btnViewClaims);
        viewClaimsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ClaimListActivity.class));
            }
        });
    }


}