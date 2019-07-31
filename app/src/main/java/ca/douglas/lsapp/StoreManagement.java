package ca.douglas.lsapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import ca.douglas.lsapp.Shared.Commom;

public class StoreManagement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_management);

        final Button btnProd = (Button) findViewById(R.id.btn_Products);
        final Button btnOrd = (Button) findViewById(R.id.btn_Orders);
        final TextView tvStoreName = (TextView) findViewById(R.id.tvStoreName);

        tvStoreName.setText(Commom.currentUser.getName());

        Log.d("StoreManagement", "onCreate: StoreManagement");

        btnProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Management of Orders
                Log.d("StoreManagement", "onClick: Listener btnProd");
                Intent i;
                i = new Intent(StoreManagement.this, ProcessProducts.class);
                startActivity(i);
            }
        });

        btnOrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Management of Orders
                Intent i;
                i = new Intent(StoreManagement.this, ProcessProducts.class);
                startActivity(i);
                finish();
            }
        });
    }
}
