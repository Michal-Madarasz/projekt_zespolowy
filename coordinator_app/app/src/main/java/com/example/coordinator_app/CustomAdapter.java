package com.example.coordinator_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

//klasa służąca do wyświetlania obiektów Victim w widoku ListView
public class CustomAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Victim> victimList;
    private LayoutInflater inflater;

    public CustomAdapter(Context applicationContext, ArrayList<Victim> victimList) {
        this.context = applicationContext;
        this.victimList = victimList;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return victimList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.victim_activity_listview, null);
        TextView imei = (TextView) view.findViewById(R.id.textView);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        imei.setText("IMEI: "+String.format("%015d", victimList.get(i).getTransmitterIMEI()));
        switch(victimList.get(i).getColor()){
            case BLACK: icon.setImageResource(R.color.colorTriageBlack); break;
            case RED: icon.setImageResource(R.color.colorTriageRed); break;
            case YELLOW: icon.setImageResource(R.color.colorTriageYellow); break;
            case GREEN: icon.setImageResource(R.color.colorTriageGreen); break;
        }
        return view;
    }
}
