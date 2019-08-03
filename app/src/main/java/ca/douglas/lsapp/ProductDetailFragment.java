package ca.douglas.lsapp;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import ca.douglas.lsapp.Shared.Commom;

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
        int pos = getArguments().getInt("position");
        String Status;

        if(("false").equals(Commom.storeProducts.get(pos).getAvailable())){
            Status ="Not Available!";
        }
        else{
            Status = "Available!";
        }



        TextView textCat = (TextView) v.findViewById(R.id.txtCategory); // this is referring to the item inside fragment_detail
        TextView textTitle = (TextView) v.findViewById(R.id.txtTitle); // this is referring to the item inside fragment_detail
        TextView textPrice = (TextView) v.findViewById(R.id.txtPrice); // this is referring to the item inside fragment_detail
        TextView textAvail = (TextView) v.findViewById(R.id.txtAvailable); // this is referring to the item inside fragment_detail
        ImageView pic = v.findViewById(R.id.imageView3);

        String s = "Some data that you want to display";
//        textV.setText("Welcome " + recvData + "\n" + Categ);
          textCat.setText( Commom.products.get(pos).getCategory());
          textTitle.setText(recvData);
          textAvail.setText("Status: "+ Status);
          textPrice.setText("Price: $" + Commom.products.get(pos).getPrice());

        String imageUrl = Commom.products.get(pos).getPictureURL();
        Log.d("imageUrl",imageUrl);

        // load image
        try {
            // get input stream
            InputStream ims = getActivity().getAssets().open(imageUrl);

            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            pic.setImageDrawable(d);
        }
        catch(IOException ex) {
            return v;
        }

        return v;
    }



}


    
