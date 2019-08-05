package ca.douglas.lsapp;

import android.content.Intent;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.location.Address;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Locale;

import ca.douglas.lsapp.DB.Order;
import ca.douglas.lsapp.Shared.Commom;

public class ClientHome extends AppCompatActivity {

    public String TAG = "Client Home";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);

        final EditText etAddress = findViewById(R.id.etAddress);
        final TextInputLayout tiAddress = findViewById(R.id.tiAddress);

        etAddress.setText(Commom.currentUser.getAddress());

        final Button btnSignOut = findViewById(R.id.btnLogout);

        final Button btnHistory = findViewById(R.id.btnHistory);

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ClientHome.this, History.class);
                startActivity(i);
            }
        });
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Logout
                AuthUI.getInstance()
                        .signOut(ClientHome.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                btnSignOut.setEnabled(false);
                               Intent i  = new Intent(ClientHome.this, MainActivity.class);
                               startActivity(i);
                               finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ClientHome.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


        Button btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Address address = validAddress(tiAddress, etAddress);

                if (address != null) {
                    Order order = new Order(etAddress.getText().toString(), Commom.currentUser.getId());

                    Intent i = new Intent(ClientHome.this, Restaurants.class);
                    i.putExtra("latitude", address.getLatitude());
                    i.putExtra("longitude", address.getLongitude());
                    i.putExtra("order", order);
                    startActivity(i);
                }
            }
        });

    }
    /**
     * Verify the address is valid address with google geocoder
     *
     * @param tiAddress The text input layout surrounding the text edit
     * @param etAddress The text edit of the address
     * @return The address if there is no error in the address field
     */
    public Address validAddress(TextInputLayout tiAddress, EditText etAddress) {
        String addressString = etAddress.getText().toString().trim();
        Address address = null;

        if (addressString.length() == 0)
            tiAddress.setError("Field required");
        else if (addressString.length() < 3)
            tiAddress.setError("Address too short");
        else if (addressString.length() > 200)
            tiAddress.setError("Address too long");
        else {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            addressString += ", BC";
            try {
                List<Address> addressList = geocoder.getFromLocationName(addressString, 1);
                if (addressList == null || addressList.size() == 0)
                    tiAddress.setError("Invalid address");
                else {
                    tiAddress.setError(null);
                    address = addressList.get(0);
                }
            } catch (Exception ex) {
                Log.d(TAG, ex.getMessage());
                tiAddress.setError("Check internet connection");
            }
        }

        return address;
    }
}
