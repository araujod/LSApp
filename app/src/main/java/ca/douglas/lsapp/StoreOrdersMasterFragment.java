package ca.douglas.lsapp;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class StoreOrdersMasterFragment extends Fragment {

    private ListView lv ;

    public StoreOrdersMasterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("onCreateView","MasterFragment");
        return inflater.inflate(R.layout.activity_store_orders_master_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d("onViewCreated","MasterFragment");
        lv = (ListView) view.findViewById(R.id.theList);  // it is in fragment_master.xml

    }

    public void setTheData(ArrayList<Integer> p) {
        Log.d("setTheData","MasterFragment");
        ArrayAdapter<Integer> adapterNames;

        Log.d("setTheDataNEW",p.toString());

        adapterNames = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_list_item_1, p);
        lv.setAdapter(adapterNames);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("onItemClick","MasterFragment");

                // DetailFragment class should be created first
                ProductDetailFragment df = new ProductDetailFragment();
                Bundle b = new Bundle();
                b.putString("data",lv.getItemAtPosition(position).toString());
                b.putInt("position",position);

                df.setArguments(b);
                getFragmentManager().beginTransaction()
                        .replace(R.id.theDetail,df)   // R.id.theDetail refers to the <FrameLayout>
                        .commit();

            }
        });


    }
}
