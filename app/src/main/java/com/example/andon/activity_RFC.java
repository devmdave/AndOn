package com.example.andon;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class activity_RFC extends AppCompatActivity implements View.OnClickListener {
    Button[] buttons;
    private final MyAPI myapi = new MyAPI();
    private final LocalDB.Station LocalDbSt = new LocalDB.Station();

    private String substation;
    String[] button_ids = new String[] {"RFC15","RFC20","RFC20L","RFC30","RFC50","RFC60","RFC70","RFC80","RFCAPC"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rfc);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //set on animation buttons
        Station st = new Station();
        LocalDbSt.initialiseLocalDB(getApplicationContext());
        try {
            st.initialise(getApplicationContext(),findViewById(R.id.main));
            st.setSubStationAnimationHandler(getApplicationContext());
        } catch (Exception ignored) {}

        buttons = new Button[button_ids.length];


        for(int i = 0; i< buttons.length; i++)
        {
            String buttonID = button_ids[i];
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = ((Button) findViewById(resID));

            buttons[i].setOnClickListener(this);

        }
    }

    @Override
    public void onClick(View v) {

        //creating equipmentlist

        ArrayList<String> equipments = new ArrayList<>();
        String btn_name = getResources().getResourceEntryName(v.getId());
        substation = btn_name.replace(" ","");

        Equipments eq = new Equipments();
        equipments = eq.getEquipments(getApplicationContext(), btn_name.toUpperCase().trim());
        setContentView(R.layout.equipmentlist);
        ListView eq_list_view = (ListView) findViewById(R.id.equipmentlistview);
        eq.initialiseLocalDB(getApplicationContext());

        try {
            eq_list_view.setAdapter(new ArrayAdapter<String>(this,R.layout.list_item,R.id.txt_list_item , equipments) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View row = super.getView(position, convertView, parent);
                    try {
                        int eqid = eq.equipments.get(position).getInt("id");
                        row.setId(eqid);
                        row.setTag(eqid);
                        boolean is_alert = eq.isEquipmentAlert(eqid);
                        boolean is_ack = eq.isEquipmentAck(eqid);
                        if(is_alert)
                        {
                            row.setBackgroundResource(R.drawable.btnstation_red);
                        }
                        else if(is_ack)
                        {
                            row.setBackgroundResource(R.drawable.btn_yellow);
                        }
                    } catch (Exception ignored) {}
                    return row;
                }
            });
        }
        catch (Exception e)
        {
            Log.e("Error",e.toString());
        }
        ArrayList<Integer> clicked = new ArrayList<Integer>();
        //managing onclick on equipments
        eq_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                activity_RFC.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        boolean is_alert = eq.isEquipmentAlert((Integer) view.getTag());
                        boolean is_ack = eq.isEquipmentAck((Integer) view.getTag());

                        if(!is_alert && is_ack && !clicked.contains(view.getId()))
                        {
                            View input_desc = getLayoutInflater().inflate(R.layout.input_desc, null);
                            EditText txt = (EditText) input_desc.findViewById(R.id.txt_ica);
                            Button submit_ica = (Button) input_desc.findViewById(R.id.submit_ica);
                            AlertDialog.Builder AlertDialogBuilder = new AlertDialog.Builder(view.getContext());
                            AlertDialogBuilder.setView(input_desc);
                            AlertDialog dialog = AlertDialogBuilder.create();
                            dialog.show();
                            submit_ica.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    myapi.setStationOK(view.getContext(),substation,view.getTag().toString(),txt.getText().toString());
                                    clicked.add(view.getId());
                                    view.setBackgroundColor(Color.WHITE);
                                    Log.e("Clicked List",clicked.toString());
                                    Log.e("IS contains",String.valueOf(clicked.contains(view.getId())));
                                }
                            });
                        }
                        else if(!is_ack && is_alert && !clicked.contains(view.getId()))
                        {
                            view.setBackgroundResource(R.drawable.btn_yellow);
                            myapi.setStationAck(view.getContext(),substation,view.getTag().toString());
                            clicked.add(view.getId());
                            Log.e("Clicked List",clicked.toString());
                            Log.e("IS contains",String.valueOf(clicked.contains(view.getId())));
                        }
                        else if(!is_ack && !is_alert && !clicked.contains(view.getId()))
                        {
                            String[] arr = new String[]{"madhav","vedant","pranav"};
                            View mRowList = getLayoutInflater().inflate(R.layout.breakdown_list, null);
                            ListView mListView =(ListView) mRowList.findViewById(R.id.breakdown_listveiw);
                            AlertDialog.Builder mAlertDialogBuilder = new AlertDialog.Builder(mListView.getContext());
                            ArrayAdapter mAdapter = new  ArrayAdapter(mListView.getContext(),R.layout.list_item,R.id.breakdown_list_item, arr);
                            mListView.setAdapter(mAdapter);

                            mAdapter.notifyDataSetChanged();
                            mAlertDialogBuilder.setView(mRowList);
                            AlertDialog dialog = mAlertDialogBuilder.create();
                            dialog.show();

                            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View list_item_view, int position, long id) {
                                    myapi.setStationAlert(view.getContext(),"RFC",substation,item,view.getTag().toString(),mListView.getItemAtPosition(position).toString());
                                    dialog.dismiss();
                                    view.setBackgroundResource(R.drawable.btnstation_red);
                                    clicked.add(view.getId());
                                    Log.e("Clicked List",clicked.toString());
                                    Log.e("IS contains",String.valueOf(clicked.contains(view.getId())));
                                }
                            });
                        }
                        else{
                            AlertPopup ap = new AlertPopup();
                            ap.showDoubleClickError(view.getContext());
                        }
                    }
                });

            }

        });

    }



}