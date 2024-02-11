package edu.lakeland.insuranceclaim;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;


public class ViewClaim extends AppCompatActivity implements OnMapReadyCallback {
    public static final String TAG = "ViewClaim";
    private Claim currentClaim;
    GoogleMap gMap;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_claim);
        initBackButton();

        ClaimDataSource ds = new ClaimDataSource(ViewClaim.this);
        Bundle extras = getIntent().getExtras();

        try{
            ds.open();
            currentClaim = ds.getSpecificClaim(extras.getInt("claimID"));
            ds.close();

        } catch (Exception e) {
            Toast.makeText(this, "Error retrieving contacts", Toast.LENGTH_LONG).show();
        }

        TextView tvDate = findViewById(R.id.tvViewClaimDate);
        TextView tvDriver = findViewById(R.id.tvViewClaimDriver);
        TextView tvAuto = findViewById(R.id.tvViewClaimAuto);
        TextView tvLatitude = findViewById(R.id.tvViewClaimLatitude);
        TextView tvLongitude = findViewById(R.id.tvViewClaimLongitude);
        ImageView photo = findViewById(R.id.ivViewClaimPhoto);

        tvDate.setText(currentClaim.getClaimDate());
        tvDriver.setText(currentClaim.getDriverName());
        tvAuto.setText(currentClaim.getAuto());
        tvLatitude.setText(String.valueOf(currentClaim.getLatitude()));
        tvLongitude.setText(String.valueOf(currentClaim.getLongitude()));
        if (currentClaim.getPicture() != null) {
            Log.d(TAG, "onCreate: Picture not null" );
            photo.setImageBitmap(currentClaim.getPicture());
        }
        else {
            Log.d(TAG, "onCreate: Picture  null" );
            photo.setImageResource(R.drawable.noimage);
        }

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentViewClaimMap);
        mapFragment.getMapAsync(this);

    }

    private void initClaim(int id){

    }

    private void initBackButton() {
        Button btnRateAMeal = findViewById(R.id.btnViewClaimBack);
        btnRateAMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewClaim.this, ClaimListActivity.class));
            }
        });
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
