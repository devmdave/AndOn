package com.example.andon;
import static com.example.andon.R.*;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.andon.MyAPI;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;


import org.json.JSONArray;
import org.json.JSONException;

import java.util.Objects;
public class MainActivity extends AppCompatActivity {
    public static Station st;
    String[] button_ids = new String[] {"btn_BC", "btn_BSLH", "btn_BSRH", "btn_FFFN", "btn_FFN", "btn_GEO1", "btn_GEO2", "btn_GEO3", "btn_HDTG", "btn_LH", "btn_MCE", "btn_MFI", "btn_MFO", "btn_MR", "btn_RFC", "btn_RH", "btn_SRL1", "btn_SRL2", "btn_UBC", "btn_UBG"};
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar tb = findViewById(id.maintoolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("WeldShop");
        drawerLayout = (DrawerLayout) findViewById(id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout, string.open, string.close);
        actionBarDrawerToggle.setDrawerSlideAnimationEnabled(true);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

//        getSupportActionBar().setHomeAsUpIndicator(drawable.menu);
        Button btn_BC = (Button)findViewById(R.id.btn_BC);
        Button btn_BSLH = (Button)findViewById(R.id.btn_BSLH);
        Button btn_BSRH = (Button)findViewById(R.id.btn_BSRH);
        Button btn_FFFN = (Button)findViewById(R.id.btn_FFFN);
        Button btn_FFN = (Button)findViewById(R.id.btn_FFN);
        Button btn_GEO1 = (Button)findViewById(R.id.btn_GEO1);
        Button btn_GEO2 = (Button)findViewById(R.id.btn_GEO2);
        Button btn_GEO3 = (Button)findViewById(R.id.btn_GEO3);
        Button btn_HDTG = (Button)findViewById(R.id.btn_HDTG);
        Button btn_LH = (Button)findViewById(R.id.btn_LH);
        Button btn_MCE = (Button)findViewById(R.id.btn_MCE);
        Button btn_MFI = (Button)findViewById(R.id.btn_MFI);
        Button btn_MFO = (Button)findViewById(R.id.btn_MFO);
        Button btn_MR = (Button)findViewById(R.id.btn_MR);
        Button btn_RFC = (Button)findViewById(R.id.btn_RFC);
        Button btn_RH = (Button)findViewById(R.id.btn_RH);
        Button btn_SRL1 = (Button)findViewById(R.id.btn_SRL1);
        Button btn_SRL2 = (Button)findViewById(R.id.btn_SRL2);
        Button btn_UBC = (Button)findViewById(R.id.btn_UBC);
        Button btn_UBG = (Button)findViewById(R.id.btn_UBG);

        btn_BC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), activity_bc.class);
                startActivity(i);
            }
        });
        btn_BSLH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), activity_BSLH.class);
                startActivity(i);
            }
        });
        btn_BSRH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), activity_BSRH.class);
                startActivity(i);
            }
        });
        btn_FFFN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), activity_FFFN.class);
                startActivity(i);
            }
        });
        btn_FFN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), activity_FFN.class);
                startActivity(i);
            }
        });
        btn_GEO1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), activity_geo1.class);
                startActivity(i);
            }
        });
        btn_GEO2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), activity_geo2.class);
                startActivity(i);
            }
        });
        btn_GEO3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), activity_geo3.class);
                startActivity(i);
            }
        });
        btn_HDTG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), activity_HDTG.class);
                startActivity(i);
            }
        });
        btn_LH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), activity_lh.class);
                startActivity(i);
            }
        });
        btn_RH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), activity_rh.class);
                startActivity(i);
            }
        });
        btn_MCE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), activity_MCE.class);
                startActivity(i);
            }
        });
        btn_MFI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), activity_mfi.class);
                startActivity(i);
            }
        });
        btn_MFO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), activity_mfo.class);
                startActivity(i);
            }
        });
        btn_MR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), activity_mr.class);
                startActivity(i);
            }
        });
        btn_RFC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), activity_RFC.class);
                startActivity(i);
            }
        });
        btn_SRL1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), activity_srl1.class);
                startActivity(i);
            }
        });
        btn_SRL2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), activity_srl2.class);
                startActivity(i);
            }
        });
        btn_UBC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), activity_ubc.class);
                startActivity(i);
            }
        });

        btn_UBG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), activity_ubg.class);
                startActivity(i);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onResume() {
        super.onResume();
        Station st = new Station();
        st.initialise(getApplicationContext(),findViewById(R.id.main));
        st.resetHandler(button_ids);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("AndonLocalDB", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("station_alerts");
        editor.remove("station_ack");
        editor.apply();
        MyAPI m = new MyAPI();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            st.setAnimationHandler(m.station_alerts,m.station_ack);
                            editor.putString("station_alerts", m.station_alerts.toString());
                            editor.putString("station_ack",m.station_ack.toString());
                            editor.apply();
                        }
                        catch (Exception e)
                        {
                            Log.d("Main Activity",e.toString());
                        }
                    }
                });
            }
        });
        m.getData(this,t1);
    }


}
