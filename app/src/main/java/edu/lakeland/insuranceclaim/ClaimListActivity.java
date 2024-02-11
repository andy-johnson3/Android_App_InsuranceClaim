package edu.lakeland.insuranceclaim;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ClaimListActivity extends AppCompatActivity {
    public static final String TAG = "ClaimListActivity";
    ArrayList<Claim> claims;
    ClaimAdaptor claimAdaptor;

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            int claimID = claims.get(position).getClaimID();
            Intent intent = new Intent(ClaimListActivity.this, ViewClaim.class);
            intent.putExtra("claimID", claimID);
            startActivity(intent);
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_list);
        initBackButton();

        ClaimDataSource ds = new ClaimDataSource(this);

        try {
            ds.open();
            claims = ds.getClaims();
            Log.d(TAG, "onCreate: " + claims.size());
            ds.close();
            if (claims.size() > 0) {
                RecyclerView claimList = findViewById(R.id.rvClaims);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                claimList.setLayoutManager(layoutManager);
                claimAdaptor = new ClaimAdaptor(claims, this);
                claimAdaptor.setOnItemClickListener(onItemClickListener);
                claimList.setAdapter(claimAdaptor);
            }
            else {
                Toast.makeText(this, "No claims found", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ClaimListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
        catch (Exception e) {
            Toast.makeText(this, "Error retrieving contacts", Toast.LENGTH_LONG).show();
        }
    }

    private void initBackButton() {
        Button btnBack = findViewById(R.id.btnClaimListBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClaimListActivity.this, MainActivity.class));
            }
        });
    }
}
