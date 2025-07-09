package com.example.andon;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

public class User extends LocalDB{
    public boolean auth = false;
    public JSONArray user = new JSONArray();



    public void authenticate(Context con,String username, String password, Thread t)
    {
        RequestQueue queue;
        String URL = "https://script.google.com/macros/s/AKfycbyGt8grQqsnD9DFgAF2JQYOHVWbb-CSFlp1byzgg_sGvMYn1SrGIBUIo221bSbJQzo/exec?action=AUTH&username="+username.trim()
        +"&pass="+password.trim();
        queue = Volley.newRequestQueue(con);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response){
                try {
                    user = new JSONArray(response);
//                    Toast.makeText(con,user.toString(), Toast.LENGTH_SHORT).show();
//                    Log.e("waaaah",response);
                    t.start();
                } catch (Exception ignored) {

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
    public void saveLogin(Context con,String name,String surname,String userid)
    {
        SharedPreferences pref = con.getSharedPreferences("AndonLocalDB", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("username",name);
        editor.putString("employeeid",userid);
        editor.putString("usersurname",surname);
        editor.apply();
    }
    public String getUserName(Context con)
    {
        SharedPreferences pref = con.getSharedPreferences("AndonLocalDB", 0); // 0 - for private mode
        String name = pref.getString("username",null);
        String surname = pref.getString("usersurname",null);
        return (name+surname);
    }
    public String getUserID(Context con)
    {
        SharedPreferences pref = con.getSharedPreferences("AndonLocalDB", 0); // 0 - for private mode
        return pref.getString("employeeid",null);
    }

}
