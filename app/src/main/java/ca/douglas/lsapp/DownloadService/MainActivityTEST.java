package ca.douglas.lsapp.DownloadService;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import ca.douglas.lsapp.R;

public class MainActivityTEST extends AppCompatActivity {

    private DBConnectivity receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button b_add = (Button) findViewById(R.id.btnAdd);



        final TextView txtResult = (TextView) findViewById(R.id.txtResult);
        final Spinner spin = (Spinner) findViewById(R.id.spinner);
        final TableLayout tbl = (TableLayout) findViewById(R.id.tblResult);
        // instantiate
        receiver = new DBConnectivity(txtResult,tbl,this);

                b_add.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                Intent i = new Intent(MainActivityTEST.this,DownloadService.class);
                //i.putExtra("where_value",spin.getSelectedItem().toString());
                i.putExtra("table","Store");
                i.putExtra("where_key","StoreID");
                i.putExtra("where_value","1");
                i.putExtra("email","test@gmail.com");
                i.putExtra("column_name","email");
                i.putExtra("new_value","ANDRESA@gmail.com");
                i.putExtra("id","1");
                txtResult.setText("About to do background service");
                startService(i);
            }
        });
    }


    @Override
    protected void onPause() {
        // Unregister since the activity is paused.
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // An IntentFilter can match against actions, categories, and data
        IntentFilter filter = new IntentFilter(DBConnectivity.STATUS_DONE);
          /*
        Intent registerReceiver (BroadcastReceiver receiver, IntentFilter filter)
        Register a BroadcastReceiver to be run in the main activity thread.
        The receiver will be called with any broadcast Intent that matches filter,
        in the main application thread.
         */

        registerReceiver(receiver,filter);
    }
}
