package ca.douglas.lsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import ca.douglas.lsapp.DB.OrderDetail;
import ca.douglas.lsapp.DB.Product;
import ca.douglas.lsapp.Shared.Commom;

class OrderDetailAdapter extends ArrayAdapter<OrderDetail> {
    private OrderDetailView mContext;
    private HashMap<Integer, Product> productsHashMap;
    private int mResource;
    private boolean isOrderHistory;

    public OrderDetailAdapter(Context context, boolean isOrderHistory) {
        super(context, R.layout.adapter_order_detail_layout);
        mContext = (OrderDetailView) context;
        mResource = R.layout.adapter_order_detail_layout;
        productsHashMap = new HashMap<>();
        this.isOrderHistory = isOrderHistory;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        getProducts(orderDetails);
        clear();
        addAll(orderDetails);
        notifyDataSetChanged();
    }

    public Product getProductFromListByID(int ID){
        for (Product product :
                Commom.productListFromSelectedStore ) {
            if(product.getProductID()==ID){
                return product;
            }
        }
        return null;
    }

    public void getProducts(List<OrderDetail> orderDetails) {

        Product product;
        for (OrderDetail orderDetail : orderDetails) {
            if (!productsHashMap.containsKey(orderDetail.getProductID())) {
                product = getProductFromListByID(orderDetail.getProductID());
                productsHashMap.put(product.getProductID(), product);
            }
        }
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        OrderDetail orderDetail = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView txtQuantity = convertView.findViewById(R.id.txtQuantity);
        TextView txtProduct = convertView.findViewById(R.id.txtProduct);
        TextView txtDescription = convertView.findViewById(R.id.txtDescription);
        TextView txtSpecialInstructions = convertView.findViewById(R.id.txtSpecialInstructions);
        TextView txtTotalPrice = convertView.findViewById(R.id.txtTotalPrice);
        ImageButton ibtnClear = convertView.findViewById(R.id.ibtnClear);

        txtQuantity.setText(Integer.toString(orderDetail.getQuantity()) + "  X");

        txtTotalPrice.setText(Commom.getCurrencyFormatted(orderDetail.getSubTotal()));

        Product product = productsHashMap.get(orderDetail.getProductID());
        if (product != null) {
            txtProduct.setText(product.getName());
            if (product.getDescription() == null || product.getDescription().equals(""))
                Commom.hideComponentInConstraintLayout(txtDescription);
            else
                txtDescription.setText(mContext.getString(R.string.productDescriptionFormat, product.getDescription()));
        }

        if (isOrderHistory)
            Commom.hideComponentInConstraintLayout(ibtnClear);
        ibtnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.deleteOrderDetail(position);
            }
        });

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return !isOrderHistory;
    }
}