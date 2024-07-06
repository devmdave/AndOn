package com.example.andon;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Toast;

import java.lang.RuntimeException;
import java.lang.reflect.Array;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Station  {
    Button[] buttons;
    AnimationDrawable an;
    Context con = null;
    View view = null;
    public void initialise(Context con,View view)
    {
        this.con = con;
        this.view = view;

    }
    public void resetHandler(String[] buttons)
    {
        if(buttons.length != 0)
        {
            for(int i=0;i < buttons.length;i++)
            {
                try {
                    int resID = con.getResources().getIdentifier(buttons[i], "id", con.getPackageName());
                    Button button = ((Button) view.findViewById(resID));
                    button.setBackgroundResource(R.drawable.btnstation);
//                an=(AnimationDrawable) button.getBackground();
//                an.start();
                } catch (Exception e) {
                    Log.e("Station",e.toString());
                }
            }

        }

    }
    public void setAnimationHandler(JSONArray st_alerts,JSONArray st_ack) throws JSONException{

        if(this.con != null && this.view != null)
        {
            // for alert stations blinking set
            for(int i=0;i < st_alerts.length();i++)
            {
                try {
                    int resID = con.getResources().getIdentifier("btn_".concat(st_alerts.getJSONArray(i).getString(0)), "id", con.getPackageName());
                    Button button = ((Button) view.findViewById(resID));
                    button.setBackgroundResource(R.drawable.animations);
                    an=(AnimationDrawable) button.getBackground();
                    an.start();
                } catch (Exception e) {
                    Log.e("Station",e.toString());
                }
            }
            // for acknowledge buttons
            for(int i=0;i < st_ack.length();i++)
            {
                boolean found = false;
                for(int j=0;j < st_alerts.length(); j++)
                {
                    if(Objects.equals(st_ack.getJSONArray(i).getString(0), st_alerts.getJSONArray(j).getString(0)))
                    {
                        found = true;
                        break;
                    }
                }
                try {
                    if(!found)
                    {
                        int resID = con.getResources().getIdentifier("btn_".concat(st_ack.getJSONArray(i).getString(0)), "id", con.getPackageName());
                        Button button = ((Button) view.findViewById(resID));
                        button.setBackgroundResource(R.drawable.btnstation_red);
                    }
                } catch (Exception e) {
                    Log.e("Station",e.toString());
                }
            }
        }
    }
    public void setSubStationAnimationHandler(Context con) throws JSONException{
        JSONArray st_alerts,st_ack;
        SharedPreferences pref = con.getSharedPreferences("AndonLocalDB", 0); // 0 - for private mode
        String station_alerts_str = pref.getString("station_alerts", null); // getting String
        String station_ack_str = pref.getString("station_ack", null); // getting String
        st_alerts = new JSONArray(station_alerts_str);
        st_ack = new JSONArray(station_ack_str);

        if(this.con != null && this.view != null)
        {
            // for alert stations blinking set
            for(int i=0;i < st_alerts.length();i++)
            {
                try {
                    @SuppressLint("DiscouragedApi") int resID = con.getResources().getIdentifier((st_alerts.getJSONArray(i).getString(1)), "id", con.getPackageName());
                    Button button = ((Button) view.findViewById(resID));
                    ObjectAnimator animator = ObjectAnimator.ofInt(button, "backgroundColor", Color.RED, Color.GREEN);
                    animator.setDuration(500);
                    animator.setEvaluator(new ArgbEvaluator());
                    animator.setRepeatCount(Animation.REVERSE);
                    animator.setRepeatCount(Animation.INFINITE);
                    animator.start();
                } catch (Exception e) {
                    Log.e("Station",e.toString());
                }
            }
            // for acknowledge buttons
            for(int i=0;i < st_ack.length();i++)
            {
                boolean found = false;
                for(int j=0;j < st_alerts.length(); j++)
                {
                    if(Objects.equals(st_ack.getJSONArray(i).getString(1), st_alerts.getJSONArray(j).getString(1)))
                    {
                        found = true;
                        break;
                    }
                }
                try {
                    if(!found)
                    {
                        @SuppressLint("DiscouragedApi") int resID = con.getResources().getIdentifier((st_ack.getJSONArray(i).getString(1)), "id", con.getPackageName());
                        Button button = ((Button) view.findViewById(resID));
                        button.setBackgroundColor(Color.RED);
                    }
                } catch (Exception e) {
                    Log.e("Station",e.toString());
                }
            }
        }
    }

}
