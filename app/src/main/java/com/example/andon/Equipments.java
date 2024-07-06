package com.example.andon;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.*;
import androidx.appcompat.app.AppCompatActivity;


import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
public class Equipments extends LocalDB.Station {
    private ListView lv;
    private JSONArray EquipmentList;

    public ArrayList<JSONObject> equipments = new ArrayList<>();

    public JSONArray ReadFromFile(Context con)
    {
        String myOutput;
        JSONObject eq_obj = new JSONObject();
        InputStream myInputStream;
        try {
            myInputStream = con.getAssets().open("equipment.json");
            int size = myInputStream.available();
            byte[] buffer = new byte[size];
            myInputStream.read(buffer);
            myInputStream.close();
            String json = new String(buffer, StandardCharsets.UTF_8);
            this.EquipmentList = new JSONArray(json);
        }
        catch (Exception e)
        {
            Log.e("Equipment List",e.toString());
        }
        return this.EquipmentList;
    }

    public ArrayList<String> getEquipments(Context context, String station) {
        ArrayList<String> stringEquipments = new ArrayList<>();
        try {
            JSONArray jsonArray = this.ReadFromFile(context);
//            Toast.makeText(context, jsonArray.toString(), Toast.LENGTH_SHORT).show();
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject obj = jsonArray.getJSONObject(i);
                if(obj.getString("station").equals(station))
                {
                    equipments.add(obj);
                }
            }
            for(int j=0;j<equipments.size();j++)
            {
                stringEquipments.add(equipments.get(j).getString("equipment"));
            }
        }
        catch (Exception e)
        {
            Log.e("Equipment List",e.toString());
        }
        return stringEquipments;
    }

    public ArrayList<String> getEquipmentByID(Context context,String eqid)
    {
        String[] keys = new String[]{"id","station","equipment"};
        ArrayList<String> arr = new <String>ArrayList();
        try{
            this.ReadFromFile(context);
            for(int i=0;i<this.EquipmentList.length();i++)
            {
                int id = this.EquipmentList.getJSONObject(i).getInt("id");
                if(id == Integer.parseInt(eqid))
                {
                    JSONObject eq = this.EquipmentList.getJSONObject(i);
                    arr.add(eq.getString("id"));
                    arr.add(eq.getString("station"));
                    arr.add(eq.getString("equipment"));
                    break;
                }
            }
            Toast.makeText(context, arr.toString(), Toast.LENGTH_SHORT).show();
        }
        catch(Exception ignored){}


        return arr;

    }
    public boolean isEquipmentAlert(Integer id)
    {
        boolean is_alert = false;
        ArrayList<Integer> arr = this.getAlerts();
//        Toast.makeText(con,arr.toString(),Toast.LENGTH_LONG).show();
        for (int j =0; j < arr.size();j++)
        {
            if(arr.get(j) == id)
            {
                is_alert = true;

            }
        }
        return is_alert;
    }
    public boolean isEquipmentAck(Integer id)
     {
        boolean is_ack = false;
        ArrayList<Integer> arr = this.getAcks();

        for(int k=0;k<arr.size();k++)
        {
            if(arr.get(k) == id)
            {
                is_ack = true;
            }

        }
        return is_ack;

    }

    public void addAlertEquipmentInLocalStorage(Context con,int eqid) {

//        ArrayList<String> eq = this.getEquipmentByID(con,Integer.toString(eqid));
//        Toast.makeText(con, eq.toString(), Toast.LENGTH_SHORT).show();
        SharedPreferences pref = con.getSharedPreferences("AndonLocalDB", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        try {
            JSONArray st_alerts = new JSONArray(pref.getString("station_alerts", null));
            Log.e("Before Saving",st_alerts.toString());
//            st_alerts.put(eq);

//            editor.putString("station_alerts",st_alerts.toString());
//            Log.e("After Saving",st_alerts.toString());

        } catch (Exception ignored){}
        editor.apply();
    }

}
