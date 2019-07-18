package ca.douglas.lsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Intent i;

        i = new Intent(this, MainActivity.class);

        try{
            Thread.sleep(2000);
        }
        catch(Exception ex){}

        startActivity(i);
        finish();
    }
}
