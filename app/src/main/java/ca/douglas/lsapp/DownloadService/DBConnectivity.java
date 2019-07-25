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


//THIS CLASS IS TO READ THE JSON ARRAY RETURNED FROM THE NODE.JS. WE CAN CHANGE HOW WE GONNA EXTRACT EACH DATA AFTER..

public class DBConnectivity extends BroadcastReceiver {

    private TextView result;
    private TableLayout tbl;
    private Context context;
    Context c;
    public static final String STATUS_DONE = "ALL_DONE";

    public DBConnectivity(TextView result, TableLayout tbl, Context context) {
        this.result = result;
        this.tbl = tbl;
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(STATUS_DONE)) {

            String text = intent.getStringExtra("output_data");
            String columns[] = {"ProductID","Name","Description","Category","PictureURL","Price","Highlight"};
//            String columns[] = {"StoreID","Email","Phone","Name","Address","LogoURL","Lat","Lng"};
            Log.d("DB - onReceive",text);

            try {
                Log.d("data",text);
                JSONArray ar = new JSONArray(text);
                JSONObject jobj;
                TableRow tr;
                TextView txt;
                //DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                //DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd",Locale.ENGLISH);

                String s;

                tbl.removeAllViews();
                // creating the header
                tr = new TableRow(context);
//                TableLayout.LayoutParams lp =
//                        new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
//                                TableLayout.LayoutParams.WRAP_CONTENT);

//                lp.setMargins(10,5,15,5); // left, top, right, bottom
//                tr.setLayoutParams(lp);
                for (int y=0; y < columns.length; y++) {
                    txt = new TextView(context);
                    txt.setPadding(0,0,15,0);
                    txt.setText(columns[y]);
                    tr.addView(txt);
                }
                tbl.addView(tr);
                for (int x=0; x < ar.length(); x++) {
                    tr = new TableRow(context);
                   // tr.setLayoutParams(lp);
                    jobj = ar.getJSONObject(x);

                    // getting the columns
                    for (int y=0; y < columns.length; y++) {
                        txt = new TextView(context);
                        txt.setPadding(0,0,15,0);
                        s =jobj.getString(columns[y]);
//                        if (columns[y].equalsIgnoreCase("birth")) {
//                            s = outputFormatter.format(LocalDate.parse(s, sdf));
//
//                        }
                        txt.setText(s);
                        tr.addView(txt);
                    }
                    tbl.addView(tr);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            result.setText("result");
        }
    }
}
