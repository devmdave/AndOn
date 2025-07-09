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
    private final String base_url = "https://script.google.com/macros/s/AKfycbyGt8grQqsnD9DFgAF2JQYOHVWbb-CSFlp1byzgg_sGvMYn1SrGIBUIo221bSbJQzo/exec?";
    public JSONObject data;
    public String[] breakdowns;
    private AlertPopup ap = new AlertPopup();
    private final User u = new User();
    public JSONArray station_alerts;
    public JSONArray station_ack;
    public JSONArray station_ok;
    public String[] alert_equipments;

    public void SetBreakdown(Context con,String eqid,String brk)
    {
        RequestQueue queue;
        String s = base_url+"action=SETBREAKDOWN&eqid=%s&breakdown=%s";
        String URL = String.format(s,eqid,brk);
        queue = Volley.newRequestQueue(con);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response){
                try {
                    Toast.makeText(con, "Breakdown set!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VOLLEY ERROR",error.toString());

            }
        });
        queue.add(request);

    }




    public void getBreakdowns(Context con ,String eqid,Thread t)
    {
        RequestQueue queue;
        String s = base_url+"action=GETBREAKDOWNS&eqid=%s";
        String URL = String.format(s,eqid);
        queue = Volley.newRequestQueue(con);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response){
                try {
                    Toast.makeText(con, "OK!", Toast.LENGTH_SHORT).show();
                    Log.e("Original Response",response);
                    JSONArray brk = new JSONArray(response);
//                    brk.put(0,"New List");
                    breakdowns = new String[(brk.length()+1)];
                    breakdowns[0] = "New List";
                    for(int i=1;i<=brk.length();i++)
                    {
                        breakdowns[i] = brk.getString(i-1);
                    }
                    t.start();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VOLLEY ERROR",error.toString());

            }
        });
        queue.add(request);
    }


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
                        Toast.makeText(con, data.toString(), Toast.LENGTH_SHORT).show();
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
        String s = base_url+"action=SETALERT&mstation=%s&substation=%s&eqid=%s&equipment=%s&user=%s&breakdown=%s";
//        String s = "http://10.0.2.2:3000/SETALERT?station=%s&equip_id=%s&username=%s";
//        String URL = String.format(s,eqid,"Madhav");
        String URL = String.format(s, mstation,substation,eqid,equipment.replaceAll("#","").replaceAll("/",""),u.getUserID(context),breakdown).replaceAll(" ","");
        Log.e("URL formed",URL);
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
        String s = base_url+"action=SETACK&station=%s&eqid=%s&user="+u.getUserID(context);
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
        String s = base_url+"action=SETOK&station=%s&eqid=%s&user="+u.getUserID(context)+"&ica="+ica;
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
