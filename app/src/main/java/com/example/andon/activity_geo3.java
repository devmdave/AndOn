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

public class activity_geo3 extends AppCompatActivity  implements  View.OnClickListener{

    Button[] buttons;
    String[] button_ids = new String[] {"GEO3CV1","GEO3CV2","GEO3CV3","GEO3CV4","GEO3CV5","GEO3CV6","GEO3CV7","GEO3CV8","GEO3CV9","GEO3CV10","GEO3CV11",
            "GEO3CV12","GEO3CV13","GEO3CV14","GEO3CV15","GEO3CV16","GEO3CV17","GEO3CV18","GEO3CV19","GEO3CV20"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_geo3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //set on animation buttons

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