package ca.douglas.lsapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.douglas.lsapp.DB.User;
import ca.douglas.lsapp.Shared.Commom;
import dmax.dialog.SpotsDialog;

import static ca.douglas.lsapp.Shared.Commom.currentUser;

public class MainActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 1111; //Define any number
    List<AuthUI.IdpConfig> providers;
    Button btnSignOut;
    TextView tvUserName;
    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db;
    String userCollection = "users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        btnSignOut = findViewById(R.id.btn_sign_out);
        tvUserName = findViewById(R.id.tvUserName);

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Logout
                AuthUI.getInstance()
                        .signOut(MainActivity.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                btnSignOut.setEnabled(false);
                                tvUserName.setText("Please login first!");
                                showSignInOptions();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        //Init providers
        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                //new AuthUI.IdpConfig.PhoneBuilder().build(),
                //new AuthUI.IdpConfig.FacebookBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
                //,new AuthUI.IdpConfig.TwitterBuilder().build()
        );

        showSignInOptions();
    }

    private void showSignInOptions() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.MyTheme)
                        .build(), MY_REQUEST_CODE
        );
    }

    private void showRegisterDialog(final String userID, final String userName){
        Log.d("Register", "Entering");
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("REGISTER");

        Log.d("Register", "Inflating");
        LayoutInflater inflater = this.getLayoutInflater();
        View register_layout = inflater.inflate(R.layout.register_layout,null);

        final MaterialEditText edtName = register_layout.findViewById(R.id.edt_name);
        final MaterialEditText edtAddress = register_layout.findViewById(R.id.edt_address);
        final MaterialEditText edtBirthdate = register_layout.findViewById(R.id.edt_birthdate);
        Button btnRegister = register_layout.findViewById(R.id.btn_register);

        if(!TextUtils.isEmpty(userName)){
            edtName.setText(userName);
            edtName.setEnabled(false);
        }
        builder.setView(register_layout);
        final AlertDialog registerDialog = builder.create();

        Log.d("Register", "Adding Listeners");
        edtBirthdate.addTextChangedListener(new PatternedTextWatcher("####-##-##"));

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Register", "onClick");
                //Closing register layout
                registerDialog.dismiss();
                Log.d("Register", "Alert dismissed");
                if(TextUtils.isEmpty(edtName.getText().toString()))
                {
                    Log.d("Register", "Blank name");
                    Toast.makeText(MainActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(edtAddress.getText().toString()))
                {
                    Log.d("Register", "Blank Address");
                    Toast.makeText(MainActivity.this, "Please enter your Address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(edtBirthdate.getText().toString()))
                {
                    Log.d("Register", "Blank birthdate");
                    Toast.makeText(MainActivity.this, "Please enter your birthday", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d("Register", "Wating dialog");

                final SpotsDialog waitingDialog = new SpotsDialog(MainActivity.this);
                waitingDialog.show();
                waitingDialog.setMessage("Please wait...");

                // Create a new user with a first and last name
                Map<String, Object> user = new HashMap<>();
                user.put("Name", edtName.getText().toString());
                user.put("Address", edtAddress.getText().toString());
                user.put("Birthdate", edtBirthdate.getText().toString());
                user.put("Type", User.CLIENT_TYPE); //Always Client when created trough app

                Log.d("Register", "Content added");
                // Add a new document with User ID authenticated
                db.collection(userCollection).document(userID)
                        .set(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                waitingDialog.dismiss();
                                Log.d("Register", "Success");
                                Toast.makeText(MainActivity.this, "User successfully registered!", Toast.LENGTH_SHORT).show();
                                Log.d("Register", "User successfully registered!");
                                Commom.currentUser.setAddress(edtAddress.getText().toString());
                                Commom.currentUser.setName(edtName.getText().toString());
                                Commom.currentUser.setBirthday(edtBirthdate.getText().toString());
                                Commom.currentUser.setType(User.CLIENT_TYPE);//Always Client when created trough app
                                tvUserName.setText("Welcome\n"+Commom.currentUser.getName()+"\nAddress:"+Commom.currentUser.getAddress());
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                finish();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                waitingDialog.dismiss();
                                Log.d("Register", "Alert failure");
                                Toast.makeText(MainActivity.this, "Error registering user!", Toast.LENGTH_LONG).show();
                                Log.w("Register", "Error registering user", e);
                            }
                        });


            }
        });

        registerDialog.show();
    }

    private void checkRegisteredUser(final String userID, final String userName){
        final String TAG = "CheckUser";
        final SpotsDialog waitingDialog = new SpotsDialog(MainActivity.this);
        waitingDialog.show();
        waitingDialog.setMessage("Please wait...");

        DocumentReference docRef = db.collection(userCollection).document(userID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                waitingDialog.dismiss();
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "User found data: " + document.getData());
                        Commom.currentUser.setAddress(document.getString("Address"));
                        Commom.currentUser.setName(document.getString("Name"));
                        Commom.currentUser.setBirthday(document.getString("Birthdate"));
                        Commom.currentUser.setType(document.getString("Type"));
                        tvUserName.setText("Welcome\n"+Commom.currentUser.getName()+"\nAddress:"+Commom.currentUser.getAddress());
                        if (Commom.currentUser.getType()=="C") //Customer
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        else //Store
                            startActivity(new Intent(MainActivity.this, StoreManagement.class));
                        finish();
                    } else {
                        Log.d(TAG, "User not found");
                        //If no user is found, show the Register dialog
                        showRegisterDialog(userID,userName);
                    }
                } else {
                    Log.d(TAG, "Error looking for user ", task.getException());
                    //Not sure what to do here, maybe show error and logout user..
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==MY_REQUEST_CODE){
            IdpResponse resp = IdpResponse.fromResultIntent(data);
            if(resultCode == RESULT_OK){
                //Get User
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                //If the user is not in our database call the register Dialog
                checkRegisteredUser(user.getUid(),user.getDisplayName());

                //Enable Button Sign out.
                btnSignOut.setEnabled(true);
            }
            else {
                Toast.makeText(this, "Something is wrong: \n"+resp.getError().getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
