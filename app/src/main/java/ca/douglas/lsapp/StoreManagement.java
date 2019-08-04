package ca.douglas.lsapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import ca.douglas.lsapp.DB.OrderStatus;
import ca.douglas.lsapp.DB.Product;
import ca.douglas.lsapp.DB.StoreProduct;
import ca.douglas.lsapp.DB.User;
import ca.douglas.lsapp.Shared.Commom;
import dmax.dialog.SpotsDialog;

public class StoreManagement extends AppCompatActivity {

    FirebaseFirestore db;
    String OrderStatusCollection = "OrderStatus";
    private static final int MY_REQUEST_CODE = 1111; //Define any number

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_management);

        final Button btnProd = (Button) findViewById(R.id.btn_Products);
        final Button btnOrd = (Button) findViewById(R.id.btn_Orders);
        final TextView tvStoreName = (TextView) findViewById(R.id.tvStoreName);

        db = FirebaseFirestore.getInstance();

        tvStoreName.setText("Welcome Store: " + Commom.currentUser.getName());

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
                Log.d("StoreManagement", "onClick: Listener btnOrd");

                final SpotsDialog waitingDialog = new SpotsDialog(StoreManagement.this);
                waitingDialog.show();
                waitingDialog.setMessage("Please wait...");

                db.collection(OrderStatusCollection)
                        .whereEqualTo("StoreID", 1)
                        .whereEqualTo("OrderStatus", "OP")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d("StoreManagement", document.getId() + " => " + document.getData());

                                        String text = document.getData().toString();

                                        String columns[] = {"storeID","orderID","userID","orderStatus"};
                                        Log.d("DB FireBase OrderStatus",text);

                                        try {
                                            JSONArray ar = new JSONArray(text);
                                            JSONObject jobj;

                                            Commom.orderStatus.clear();

                                            for (int x=0; x < ar.length(); x++) {
                                                jobj = ar.getJSONObject(x);
                                                // getting the columns
                                                OrderStatus orderSts = new OrderStatus(Integer.parseInt(jobj.getString(columns[0])),
                                                        Integer.parseInt(jobj.getString(columns[1])),
                                                        jobj.getString(columns[2]), jobj.getString(columns[3]));

                                                Commom.orderStatus.add(orderSts);

                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        Intent i;
                                        i = new Intent(StoreManagement.this, ProcessStoreOrders.class);
                                        startActivity(i);

                                    }
                                    waitingDialog.dismiss();

                                } else {
                                    waitingDialog.dismiss();
                                    Log.d("StoreManagement", "Error getting documents: ", task.getException());
                                }
                            }
                        });
            }
        });

    }


}

