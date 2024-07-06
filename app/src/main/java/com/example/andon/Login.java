package com.example.andon;
import com.example.andon.User;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;

import kotlin.sequences.USequencesKt;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView txt_username  = findViewById(R.id.txt_username);
        TextView txt_password = findViewById(R.id.txt_password);
        View login_btn = findViewById(R.id.btn_login);
        User u = new User();
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.login_buff);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 300);
        dialog.setCancelable(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You entered an incorrect username or password.");
        builder.setTitle("Authentication Failed!");
        builder.setCancelable(false);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if(u.user.get(0).toString() == "true")
                                    {
                                        dialog.dismiss();
                                        u.saveLogin(getApplicationContext(),u.user.getString(1),u.user.getString(2));
                                        Intent i = new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(i);
                                    }
                                    else if(u.user.get(0).toString() == "false") {
                                        dialog.dismiss();
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }

                                } catch (Exception ignored) {
//                                    throw new RuntimeException(e);
                                }


                            }
                        });
                    }
                });

                u.authenticate(getApplicationContext(),txt_username.getText().toString(),txt_password.getText().toString(),t);
            }
        });




    }



}