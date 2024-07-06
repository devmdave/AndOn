package com.example.andon;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;

public class activity_ubc extends AppCompatActivity implements View.OnClickListener {
    Button[] buttons;
    String[] button_ids = new String[] {"UBC10","UBC20","UBC30","UBC40","UBC50","UBC60","UBC70","UBC80","UBC90","UBC100",
            "UBC110","UBC120","UBC130","UBC140","UBC150","UBC160","UBC170","UBC180"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ubc);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //set animation on buttons
        Station st = new Station();
        try {
            st.initialise(getApplicationContext(),findViewById(R.id.main));
            st.setSubStationAnimationHandler(getApplicationContext());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        buttons = new Button[button_ids.length];
        for(int i = 0; i< buttons.length; i++)
        {
            String buttonID =  button_ids[i];
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = ((Button) findViewById(resID));
            buttons[i].setOnClickListener(this);
        }
    }
    @Override
    public void onClick(View v) {
        //creating equipmentlist
        String btn_name = getResources().getResourceEntryName(v.getId());
        Equipments eq = new Equipments();
        eq.getEquipments(getApplicationContext(), btn_name.toUpperCase().trim());
        ArrayAdapter<String> adapter;
        setContentView(R.layout.equipmentlist);
        ListView eq_list_view = (ListView) findViewById(R.id.equipmentlistview);
//        adapter=new ArrayAdapter<String>(getApplicationContext(),
//                android.R.layout.simple_list_item_1,
//                eq.equipments);
//        eq_list_view.setAdapter(adapter);
    }

}