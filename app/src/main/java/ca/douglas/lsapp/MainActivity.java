package ca.douglas.lsapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 1111; //Define any number
    List<AuthUI.IdpConfig> providers;
    Button btnSignOut;
    TextView tvUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                                tvUserName.setText("Login First!");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==MY_REQUEST_CODE){
            IdpResponse resp = IdpResponse.fromResultIntent(data);
            if(resultCode == RESULT_OK){
                //Get User
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                //Show email on Toast
                tvUserName.setText("Welcome\n"+user.getDisplayName());
                //Toast.makeText(this, "Seja bem vindo \n"+user.getEmail(),Toast.LENGTH_LONG).show();

                //Enable Button Sign out.
                btnSignOut.setEnabled(true);
            }
            else {
                Toast.makeText(this, "Algo deu errado: \n"+resp.getError().getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
