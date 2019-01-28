package com.example.ayush.missionias;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Frame extends Fragment {
    JSONObject jsonObject;
    ArrayList<String> names;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_layout,container,false);
        String j=getArguments().getString("json");
        try {
             jsonObject=new JSONObject(j);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ListView ls2=v.findViewById(R.id.ls2);
        Toast.makeText(getActivity(),"hello"+jsonObject.toString(),Toast.LENGTH_SHORT).show();
        //Iterator<String> iterator=  jsonObject.keys();
      /*  while (iterator.hasNext()){
            String key=iterator.next();
            names.add(key);
        }*/
      //  ArrayAdapter arr=new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,names);
       // ls2.setAdapter(arr);
        return v;
    }
}
