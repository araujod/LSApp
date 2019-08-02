package ca.douglas.lsapp.DownloadService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

import ca.douglas.lsapp.Shared.Commom;


//THIS CLASS IS TO READ THE JSON ARRAY RETURNED FROM THE NODE.JS. WE CAN CHANGE HOW WE GONNA EXTRACT EACH DATA AFTER..

public class DBConnectivity extends BroadcastReceiver {

    private Context context;
    Context c;
    public static final String STATUS_DONE = "ALL_DONE";

    public DBConnectivity(Context context) {
        //this.result = result;
        //this.tbl = tbl;
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(STATUS_DONE)) {

            String text = intent.getStringExtra("output_data");
            String columns[] = {"ProductID","Name","Description","Category","PictureURL","Price","Highlight"};
            // String columns[] = {"StoreID","Email","Phone","Name","Address","LogoURL","Lat","Lng"};
            Log.d("DB - onReceive",text);

            try {
                Log.d("data",text);
                JSONArray ar = new JSONArray(text);
                JSONObject jobj;

                for (int x=0; x < ar.length(); x++) {
                    jobj = ar.getJSONObject(x);
                    // getting the columns
                    for (int y=0; y < columns.length; y++) {
                        Commom.Products[x][y] = jobj.getString(columns[y]);
                    }
                }



            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
