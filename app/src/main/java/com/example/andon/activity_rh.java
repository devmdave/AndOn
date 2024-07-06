package com.example.andon;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;

public class activity_rh extends AppCompatActivity implements View.OnClickListener {

    Button[] buttons;
    String[] button_ids = new String[] {"RHVFD20","RHFD10","RHFD20","RHFD30","RHFD40","RHFD50","RHFD60","RHFD70","RHFD80","RHFD90","RHFD100",
            "RHVRD20","RHRD10","RHRD20","RHRD30","RHRD40","RHRD50","RHRD60","RHRD70","RHRD80","RHRD90","RHRD100" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rh);
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

        try {
            for(int i = 0; i< buttons.length; i++)
            {
                String buttonID =  button_ids[i];
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i] = ((Button) findViewById(resID));

                buttons[i].setOnClickListener(this);

            }

        }
        catch (Exception e)
        {
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();

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