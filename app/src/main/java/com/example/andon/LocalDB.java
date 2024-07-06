package com.example.andon;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.andon.Equipments;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class LocalDB {
    public final String pref_key = "AndonLocalDB";
//    public final Equipments eqlist = new Equipments();
    public final int INDEX_EQID = 2;
    public  SharedPreferences pref = null;
    public  SharedPreferences.Editor editor = null;
    static class Station extends LocalDB{
        final String key_st_alerts = "station_alerts";
        final String key_st_acks = "station_ack";
        public void initialiseLocalDB(Context con)
        {
            this.pref = con.getSharedPreferences(this.pref_key, 0); // 0 - for private mode
            this.editor = pref.edit();
        }
        public void saveAlerts(String st_alerts)
        {
            this.editor.putString(this.key_st_alerts,st_alerts);
        }
        public void saveAcks(String st_acks)
        {
            this.editor.putString(this.key_st_acks,st_acks);
        }
        public ArrayList<Integer> getAlerts()
        {
            ArrayList<Integer> alert_equipments = new ArrayList<>();
            try {
                String station_alerts_str = pref.getString("station_alerts", null);
                JSONArray station_alerts = new JSONArray(station_alerts_str); // getting String
                for(int i = 0;i < station_alerts.length();i++)
                {
                    alert_equipments.add(station_alerts.getJSONArray(i).getInt(this.INDEX_EQID));
                }
            } catch (Exception ignored) {

            }
            return alert_equipments;

        }
        public ArrayList<Integer> getAcks()
        {
            ArrayList<Integer> ack_equipments = new ArrayList<>();
            try {
                JSONArray station_ack = new JSONArray(pref.getString("station_ack", null)); // getting String
                for(int k=0;k < station_ack.length();k++)
                {
                    ack_equipments.add(station_ack.getJSONArray(k).getInt(this.INDEX_EQID));
                }
            } catch (Exception ignored) {

            }
            return  ack_equipments;
        }
        public void saveLocalDB()
        {
            this.editor.apply();
        }
        public JSONArray getBreakdownList(Context con)
        {
            String myOutput;
            JSONObject eq_obj = new JSONObject();
            JSONArray breakdownlist = new JSONArray();
            InputStream myInputStream;
            try {
                myInputStream = con.getAssets().open("breakdown.json");
                int size = myInputStream.available();
                byte[] buffer = new byte[size];
                myInputStream.read(buffer);
                myInputStream.close();
                String json = new String(buffer, StandardCharsets.UTF_8);
                breakdownlist = new JSONArray(json);
            }
            catch (Exception e)
            {
                Log.e("Equipment List",e.toString());
            }
            return breakdownlist;
        }
    }









}
