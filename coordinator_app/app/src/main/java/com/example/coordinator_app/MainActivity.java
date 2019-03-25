package com.example.coordinator_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import java.util.ArrayList;
import java.util.Random;

/*
TODO: panel zarządzania ratownikami
TODO: obsługa bluetooth
TODO: panel podsumowujący poszkodowanych
 */

public class MainActivity extends AppCompatActivity {

    ArrayList<Victim> victims = new ArrayList<>();
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ListView victimList = findViewById(R.id.victim_list);
        customAdapter = new CustomAdapter(getApplicationContext(), victims);
        victimList.setAdapter(customAdapter);

        ImageView imgV;
        imgV = findViewById(R.id.total_black).findViewById(R.id.label); imgV.setImageResource(R.color.colorTriageBlack);
        imgV = findViewById(R.id.total_red).findViewById(R.id.label); imgV.setImageResource(R.color.colorTriageRed);
        imgV = findViewById(R.id.total_yellow).findViewById(R.id.label); imgV.setImageResource(R.color.colorTriageYellow);
        imgV = findViewById(R.id.total_green).findViewById(R.id.label); imgV.setImageResource(R.color.colorTriageGreen);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Losowa generacja pacjenta", Toast.LENGTH_LONG ).show();
                Random r = new Random();
                long imei = r.nextLong()%1000000000000000L;
                boolean b = r.nextBoolean();
                int rate = r.nextInt(40) + 10;
                float capRefillTime = r.nextFloat()*3f + 0.5f;
                boolean w = r.nextBoolean();
                Victim.AVPU c = Victim.AVPU.values()[r.nextInt(Victim.AVPU.values().length)];
                Victim randomVictim = new Victim(imei, b, rate, capRefillTime, w, c);
                victims.add(randomVictim);
                updateVictimsData();
                customAdapter.notifyDataSetChanged();
            }
        });

        victimList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), VictimDetailsActivity.class);
                intent.putExtra("victim", victims.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        ViewFlipper vf = findViewById(R.id.layout_manager);
        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.action_bt:
                vf.setDisplayedChild(1);
                return true;
            case R.id.action_victims:
                vf.setDisplayedChild(2);
                return true;
            case R.id.action_rescuers:
                vf.setDisplayedChild(3);
                return true;
            default:
                vf.setDisplayedChild(0);
        }


        return super.onOptionsItemSelected(item);
    }

    public void updateVictimsData(){
        int b=0, r=0, y=0, g=0;
        for(Victim v : victims){
            switch(v.getColor()){
                case BLACK: b++; break;
                case RED: r++; break;
                case YELLOW: y++; break;
                case GREEN: g++; break;
            }
        }
        TextView t = findViewById(R.id.total_victims_val); t.setText(victims.size()+"");

        t = findViewById(R.id.total_black).findViewById(R.id.val); t.setText(b+"");
        t = findViewById(R.id.total_red).findViewById(R.id.val); t.setText(r+"");
        t = findViewById(R.id.total_yellow).findViewById(R.id.val); t.setText(y+"");
        t = findViewById(R.id.total_green).findViewById(R.id.val); t.setText(g+"");

    }
}
