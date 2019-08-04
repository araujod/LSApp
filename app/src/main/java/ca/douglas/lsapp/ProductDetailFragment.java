package ca.douglas.lsapp;

import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import ca.douglas.lsapp.DB.Product;
import ca.douglas.lsapp.DB.StoreProduct;
import ca.douglas.lsapp.DownloadService.DownloadService;
import ca.douglas.lsapp.Shared.Commom;
import dmax.dialog.SpotsDialog;

public class ProductDetailFragment extends Fragment {

    public ProductDetailFragment() {
        // Required empty public constructor
    }
    private ProductDetailFragment.DBConnectivityProductDetail receiver = new ProductDetailFragment.DBConnectivityProductDetail(getActivity());
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("onCreateView","DetailFragment");
        View v = inflater.inflate(R.layout.activity_product_detail_fragment, null);
        String recvData = getArguments().getString("data");
       final int pos = getArguments().getInt("position");
        final String Status;
        TextView textCat = (TextView) v.findViewById(R.id.txtCategory); // this is referring to the item inside fragment_detail
        TextView textPrice = (TextView) v.findViewById(R.id.txtPrice); // this is referring to the item inside fragment_detail
        TextView textAvail = (TextView) v.findViewById(R.id.txtAvailable); // this is referring to the item inside fragment_detail
        ImageView pic = v.findViewById(R.id.imageView3);
        final Button btn_change = v.findViewById(R.id.btn_changeAvail);

        if(((Commom.storeProducts.get(pos).getAvailable())==false)){
            Status ="Out Of Stock!";
            textAvail.setTextColor(Color.RED);
            textAvail.setTextSize(13);

        }
        else{
            Status = "Available!";
            textAvail.setTextColor(Color.rgb(36, 133, 60));

        }


          textCat.setText( Commom.products.get(pos).getCategory());

          textAvail.setText("Status: "+ Status);
          textPrice.setText("Price: $" + Commom.products.get(pos).getPrice());

        String imageUrl = Commom.products.get(pos).getPictureURL();
        imageUrl=  imageUrl.toLowerCase();
       String[] imageUrl2 = imageUrl.split(".png");

        Log.d("imageUrl",imageUrl);

        // load image
        int resId = getResources().getIdentifier(imageUrl2[0], "drawable", getActivity().getPackageName());
        pic.setImageResource(resId);


        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String update;
                if(Status.equals("Out Of Stock!")){
                    update = "true";
                    Commom.storeProducts.get(pos).setAvailable(true);
                }

                else{
                    update = "false";
                    Commom.storeProducts.get(pos).setAvailable(false);
                }
                Intent i = new Intent(ProductDetailFragment.this.getActivity(), DownloadService.class);


                i.putExtra("table","");
                i.putExtra("where_key","");
                i.putExtra("where_value",String.valueOf(Commom.storeProducts.get(pos).getProductID()));
                i.putExtra("where_value2",String.valueOf(Commom.storeProducts.get(pos).getStoreID()));
                i.putExtra("column_name","");
                i.putExtra("new_value",update);
                i.putExtra("id","");
                i.putExtra("method","PUT_PRODAV");
                i.putExtra("setAction","ProdDetailAvailable");


                getActivity().startService(i);


            }

        });






        return v;
    }


    public class DBConnectivityProductDetail extends BroadcastReceiver {

        private Context context;
        Context c;
        public static final String STATUS_DONE = "ALL_DONE";

        public DBConnectivityProductDetail(Context context) {
            this.context = context;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(STATUS_DONE)) {


                String text = intent.getStringExtra("output_data");

                Toast.makeText(getActivity(), "Availability Changed",
                        Toast.LENGTH_SHORT).show();

                getFragmentManager().beginTransaction().detach(ProductDetailFragment.this).attach(ProductDetailFragment.this).commit();




            }
        }
    }

    @Override
    public void onPause() {
        // Unregister since the activity is paused.
        super.onPause();
        getActivity().unregisterReceiver(receiver);
//        final SpotsDialog waitingDialog = new SpotsDialog(ProductDetailFragment.this.getActivity());
//        waitingDialog.show();
//        waitingDialog.setMessage("Please wait...");
    }

    @Override
    public void onResume() {
        super.onResume();
        // An IntentFilter can match against actions, categories, and data
        IntentFilter filter = new IntentFilter(ProductDetailFragment.DBConnectivityProductDetail.STATUS_DONE);

        //Intent registerReceiver (BroadcastReceiver receiver, IntentFilter filter)
        //Register a BroadcastReceiver to be run in the main activity thread.
        //The receiver will be called with any broadcast Intent that matches filter,
        //in the main application thread.

        getActivity().registerReceiver(receiver,filter);

        Log.d("DB - onReceive","IM HERE NEW!!");
//        final SpotsDialog waitingDialog = new SpotsDialog(ProductDetailFragment.this.getActivity());
//        waitingDialog.dismiss();


    }
}








    
