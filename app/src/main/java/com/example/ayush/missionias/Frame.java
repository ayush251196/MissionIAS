package com.example.ayush.missionias;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class Frame extends Fragment {
    JSONObject jsonObject;
    ArrayList<String> names;
    CustomAdapter customAdapter;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_layout,container,false);
        String j=getArguments().getString("json");
        names=new ArrayList<>();
        recyclerView=v.findViewById(R.id.recycler_view);
        try {
             jsonObject=new JSONObject(j);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Iterator<String> iterator=  jsonObject.keys();
       while (iterator.hasNext()){
            String key=iterator.next();
            names.add(key);
        }
         //ArrayAdapter arr=new ArrayAdapter<String>(getContext(),R.layout.list_item_card_view2,names);
         //ls2.setAdapter(arr);
        customAdapter=new CustomAdapter(names,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(customAdapter);
        customAdapter.setOnClickListener(new CustomAdapter.OnItemClickListener() {
            FragmentManager fragmentManager;
            FragmentTransaction fragmentTransaction;
            @Override
            public void onItemClick(int pos) {
                String str = null;
                Log.i("tag", String.valueOf(pos));
                try {
                    str = jsonObject.getString(names.get(pos));
                    if (jsonObject.get(names.get(pos)).getClass().toString().equals("class java.lang.String")) {
                        //Toast.makeText(HomeScreen.this,str, Toast.LENGTH_SHORT).show();
                        Uri u = Uri.parse(str);
                        Intent i = new Intent(Intent.ACTION_VIEW, u);
                        startActivity(i);
                    } else {

                        //Toast.makeText(HomeScreen.this,str,Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putString("json", jsonObject.getString(names.get(pos)));
                        Frame f = new Frame();
                        f.setArguments(bundle);
                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.add(R.id.frg, f);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });
        return v;
    }
}
