package com.example.ayush.missionias;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class HomeScreen extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    ArrayList<String> names;

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("tag","OnPause");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("tag","OnDestroyed");
    }

    CustomAdapter customAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager linearLayoutManager;
    DatabaseReference databaseReference;
    JSONObject jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.card_grey));
        setSupportActionBar(toolbar);
        names=new ArrayList<>();
        recyclerView=findViewById(R.id.recv_content);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        Log.i("tag","OnStopped");
        //names.clear();
    }

    public void populate(){
            Iterator<String> iterator=  jsonObject.keys();
            while (iterator.hasNext()){
                String key=iterator.next();
                names.add(key);
         }
        customAdapter=new CustomAdapter(names,HomeScreen.this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(customAdapter);
        customAdapter.setOnClickListener(new CustomAdapter.OnItemClickListener() {
            FragmentManager fragmentManager;
            FragmentTransaction fragmentTransaction;
            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public void onItemClick(int pos) {
              String str=null;
              Log.i("tag",String.valueOf(pos));
                try {
                    str=jsonObject.getString(names.get(pos));
                    if(jsonObject.get(names.get(pos)).getClass().toString().equals("class java.lang.String")){
                        //Toast.makeText(HomeScreen.this,str, Toast.LENGTH_SHORT).show();
                        Uri u=Uri.parse(str);
                        Intent i=new Intent(Intent.ACTION_VIEW,u);
                        startActivity(i);
                    }
                    else{

                        //Toast.makeText(HomeScreen.this,str,Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putString("json",jsonObject.getString(names.get(pos)));
                        Frame f=new Frame();
                        f.setArguments(bundle);
                        fragmentManager= getSupportFragmentManager();
                        fragmentTransaction=fragmentManager.beginTransaction();
                        fragmentTransaction.add(R.id.frg,f);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                try {
//                    String str=jsonObject.getString(names.get(pos));
//                    JSONObject jo=new JSONObject(str);
//                    //Toast.makeText(HomeScreen.this,String.valueOf(jo.length()),Toast.LENGTH_SHORT).show();
//                if(String.valueOf(jo.length())==null) {
//                    Intent i = new Intent();
//                    i.setAction(Intent.ACTION_VIEW);
//                    i.setData(Uri.parse(jsonObject.getString(names.get(pos))));
//                }
//                    Toast.makeText(HomeScreen.this,jsonObject.toString(), Toast.LENGTH_SHORT).show();
//
//
//
//                }
//                catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }

        });

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



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            Intent sharingIntent=new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody="here is share content";
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT,"Subject Here");
            sharingIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
            startActivity(Intent.createChooser(sharingIntent,"Share via"));
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
