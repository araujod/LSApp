package ca.douglas.lsapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

import ca.douglas.lsapp.DB.OrderDetail;
import ca.douglas.lsapp.DB.Product;
import ca.douglas.lsapp.Shared.Commom;

public class ProductDetail extends AppCompatActivity {
    private Product product;
    private OrderDetail orderDetail;
    private ImageView imgImage;
    private TextView txtDescription;
    private TextView txtQuantity;
    private Button btnLess;
    private Button btnMore;
    private ConstraintLayout clAddToOrder;
    private TextView txtTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Intent intent = getIntent();
        product = (Product) intent.getSerializableExtra("product");
        orderDetail = new OrderDetail(product.getPrice(), product.getProductID());

        // Set the image in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        // Set title of action bar
        setTitle(product.getName());

        imgImage = findViewById(R.id.imgImage);
        txtDescription = findViewById(R.id.txtDescription);
        txtQuantity = findViewById(R.id.txtQuantity);
        btnLess = findViewById(R.id.btnLess);
        btnMore = findViewById(R.id.btnMore);

        clAddToOrder = findViewById(R.id.clCheckout);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);

        if (product.getPictureURL() != null) {
            AssetManager assetManager = getAssets();

            try {
                InputStream is = assetManager.open(product.getPictureURL());
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                imgImage.setImageBitmap(bitmap);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }

        /*
        // Display image or hide it if there is no image
        if (product.getImage() != null)
            imgImage.setImageResource(getResources().getIdentifier(product.getImage(), "drawable", getPackageName()));
        else
            Helper.hideComponentInConstraintLayout(imgImage);
        */

        if (!product.getDescription().equals(""))
            txtDescription.setText(product.getDescription());
        else
            Commom.hideComponentInConstraintLayout(txtDescription);
        setQuantity(orderDetail.getQuantity());

        btnLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = orderDetail.getQuantity();
                if (quantity > 1)
                    setQuantity(--quantity);
            }
        });

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = orderDetail.getQuantity();
                if (quantity < 10)
                    setQuantity(++quantity);
            }
        });

        clAddToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("orderDetail", orderDetail);
                setResult(Activity.RESULT_OK, i);
                finish();
            }
        });
    }

    private void setQuantity(int quantity) {
        orderDetail.setQuantity(quantity);
        txtQuantity.setText(Integer.toString(orderDetail.getQuantity()));
        orderDetail.setSubTotal(quantity * product.getPrice());
        txtTotalPrice.setText(Commom.getCurrencyFormatted(orderDetail.getSubTotal()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent();
        setResult(Activity.RESULT_CANCELED, i);
        finish();
    }

}
