package com.example.andon;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyAPI extends MainActivity {
    private final String base_url = "https://script.google.com/macros/s/AKfycbwWPNAk3Vnq97hFbV3YNFXewr2Kh-DrurzqyAgRoeKtEsYU3CEA2-m1Moyw_STeQQiY/exec?";
    public JSONObject data;
    private AlertPopup ap = new AlertPopup();

    private final User u = new User();
    public JSONArray station_alerts;
    public JSONArray station_ack;
    public JSONArray station_ok;
    public String[] alert_equipments;
    public void getData(Context con,Thread t)
    {
        RequestQueue queue;
        String URL = base_url+"action=GETDATA";
        queue = Volley.newRequestQueue(con);
        Dialog dg = ap.showBuffering(con);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response){
                try {
                    //noinspection Since15
                    if(!response.isBlank())
                    {

                        data = new JSONObject(response);
                        station_alerts = data.getJSONArray("ALERT");
                        station_ack = data.getJSONArray("ACK");
//                        station_ok = data.getJSONArray("OK");
                        alert_equipments = new String[station_alerts.length()];
                        for(int i = 0;i < station_alerts.length();i++)
                        {
                            alert_equipments[i] = station_alerts.getJSONArray(i).getString(3).replaceAll("\\s", "");
                        }
                        ap.stopBuff(dg);
                    }
//                    Toast.makeText(con, response.toString().trim(), Toast.LENGTH_SHORT).show();
                    ap.stopBuff(dg);
                    t.start();

                } catch (Exception ignored) {
                    ap.stopBuff(dg);
                    t.start();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ap.stopBuff(dg);
                t.start();
                Log.d("VOLLEY ERROR",error.toString());
            }
        });
        queue.add(request);
    }
    public void setStationAlert(Context context,String mstation,String substation,String equipment,String eqid,String breakdown)
    {
        RequestQueue queue;
        String s = base_url+"action=SETALERT&mstation=%s&substation=%s&equipment=%s&eqid=%s&user="+u.getUserName(context)+"&breakdown="+breakdown;
        String URL = String.format(s, mstation,substation,equipment,eqid);
        queue = Volley.newRequestQueue(context);
        Dialog dg = ap.showBuffering(context);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response){
                try {
                    Toast.makeText(context, "booked", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                ap.stopBuff(dg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ap.stopBuff(dg);
                Log.d("VOLLEY ERROR",error.toString());
            }
        });
        queue.add(request);
    }

    public void setStationAck(Context context,String station,String eqid)
    {
        RequestQueue queue;
        String s = base_url+"action=SETACK&station=%s&eqid=%s&user="+u.getUserName(context);
        String URL = String.format(s, station,eqid);
        Dialog dg = ap.showBuffering(context);

        queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response){

                try {
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                ap.stopBuff(dg);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ap.stopBuff(dg);
                Log.d("VOLLEY ERROR",error.toString());
            }
        });
        queue.add(request);
    }
    public void setStationOK(Context context,String station,String eqid,String ica)
    {
        RequestQueue queue;
        String s = base_url+"action=SETOK&station=%s&eqid=%s&user="+u.getUserName(context)+"&ica="+ica;
        String URL = String.format(s, station,eqid);
        Dialog dg = ap.showBuffering(context);
        queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response){
                try {
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                ap.stopBuff(dg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ap.stopBuff(dg);
                Log.d("VOLLEY ERROR",error.toString());
            }
        });
        queue.add(request);
    }
}
