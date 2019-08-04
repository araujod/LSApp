package ca.douglas.lsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.res.AssetManager;

import java.io.InputStream;
import java.util.List;

import ca.douglas.lsapp.DB.Product;
import ca.douglas.lsapp.Shared.Commom;

public class ProductAdapter extends ArrayAdapter<Product> {
    private Context mContext;
    private int mResource;

    public ProductAdapter(Context context) {
        super(context, R.layout.adapter_product_layout);
        mContext = context;
        mResource = R.layout.adapter_product_layout;
    }

    public void setProducts(List<Product> products) {
        addAll(products);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product product = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        ImageView imageView = convertView.findViewById(R.id.imgImage);
        TextView txtName = convertView.findViewById(R.id.txtName);
        TextView txtDescription = convertView.findViewById(R.id.txtDescription);
        TextView txtPrice = convertView.findViewById(R.id.txtTotalPrice);

        imageView.setImageResource(mContext.getResources().getIdentifier(product.getPictureURL(),"drawable", mContext.getPackageName()));
        /*
        if (product.getPictureURL() != null) {
            AssetManager assetManager = mContext.getAssets();

            try {
                InputStream is = assetManager.open(product.getPictureURL());
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                imageView.setImageBitmap(bitmap);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }*/
        txtName.setText(product.getName());
        txtDescription.setText(product.getCategory());
        txtPrice.setText(Commom.getCurrencyFormatted(product.getPrice()));

        return convertView;
    }
}
