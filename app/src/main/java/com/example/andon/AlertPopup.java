package com.example.andon;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

public class AlertPopup {
    public void showInternetError(Context con)
    {
        Dialog dg = new Dialog(con);
        dg.setContentView(R.layout.neterror_layout);
        dg.setTitle("Internet Error");
        dg.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button dismiss = dg.findViewById(R.id.ok_btn);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dg.dismiss();
            }
        });
        dg.show();
    }
    public void showDoubleClickError(Context con)
    {
        Dialog dg = new Dialog(con);
        dg.setContentView(R.layout.clickerror_layout);
        dg.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button dismiss = dg.findViewById(R.id.ok_btn);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dg.dismiss();
            }
        });
        dg.show();
    }
    public void showAuthError(Context con)
    {
        Dialog dg = new Dialog(con);
        dg.setContentView(R.layout.neterror_layout);
        dg.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button dismiss = dg.findViewById(R.id.ok_btn);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dg.dismiss();
            }
        });
        dg.show();
    }
    public Dialog showBuffering(Context con){
        Dialog dialog = new Dialog(con);
        dialog.setContentView(R.layout.loading);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 300);
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }
    public void stopBuff(Dialog dg)
    {
        dg.dismiss();
    }

}
