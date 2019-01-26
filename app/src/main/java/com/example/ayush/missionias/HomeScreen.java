package com.example.ayush.missionias;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Iterator;

public class HomeScreen extends AppCompatActivity {
    ArrayList<String> names;
    CustomAdapter customAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager linearLayoutManager;
    DatabaseReference databaseReference;
    JSONObject jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("tag","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.card_grey));
        setSupportActionBar(toolbar);
        names=new ArrayList<>();
        recyclerView=findViewById(R.id.recv_content);
        load_data();

    }
    public JSONObject createJsonArray(DataSnapshot dataSnapshot){
        JSONObject jsonObject=new JSONObject();
        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
//            JSONObject jsonObject=new JSONObject();
            try {
                if (snapshot.getChildrenCount() > 0) {
                    jsonObject.put(snapshot.getKey(), createJsonArray(snapshot));
                } else {
                    jsonObject.put(snapshot.getKey(), snapshot.getValue());
                }
            }catch (Exception e){

            }
//            jsonArray.put(jsonObject);
        }
        //Log.i("tag",jsonArray.toString());
        return jsonObject;

    }
    public void  load_data(){
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Structure");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("tag","ONDatachanged");
                Log.i("tag",String.valueOf(dataSnapshot.getChildrenCount()));
                jsonObject=createJsonArray(dataSnapshot);
                populate();
                try {
                   // Log.i("tag",jsonArray.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        names.clear();
    }

    public void populate(){
            Iterator<String> iterator=  jsonObject.keys();
            while (iterator.hasNext()){
                String key=iterator.next();
                names.add(key);
         }
        customAdapter=new CustomAdapter(names);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(customAdapter);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
