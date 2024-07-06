package com.example.andon;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONObject;

public class activity_FFN extends AppCompatActivity {
    Button[] buttons;
    String[] button_ids = new String[] {"FFN10","FFN20","FFN30","FFN40","FFN50","FFN70","FFN130","FFN140"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ffn);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//        EquipmentList eq = new EquipmentList();
//        JSONObject obj = eq.getEquipmentByID(getApplicationContext(),"193");
//        Toast.makeText(getApplicationContext(), obj.toString(), Toast.LENGTH_SHORT).show();


    }
}