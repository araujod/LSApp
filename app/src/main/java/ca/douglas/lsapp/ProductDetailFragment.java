package ca.douglas.lsapp;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProductDetailFragment extends Fragment {

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("onCreateView","DetailFragment");
        View v = inflater.inflate(R.layout.activity_product_detail_fragment, null);
        String recvData = getArguments().getString("data");


        TextView textV = (TextView) v.findViewById(R.id.txtSomething); // this is referring to the item inside fragment_detail
        String s = "Some data that you want to display";
        textV.setText("Welcome " + recvData + "\n" + s);
        return v;
    }
}
