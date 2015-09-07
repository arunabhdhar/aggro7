package com.app.Utility;

/**
 * Created by ericbasendra on 03/09/15.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.app.aggro.R;


public class CustomCategoryDialog {
    private static CustomCategoryDialog instance;
    private Context mContext;
    private Dialog customDialog;
    private ProgressDialog progressDialog;

    public static CustomCategoryDialog getInstance() {
        if (instance == null) {
            instance = new CustomCategoryDialog();
        }
        return instance;
    }

    public void createDialog(Context mContext, final OnSubmit submit) {
        this.mContext = mContext;
        customDialog = new Dialog(mContext);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        customDialog.setContentView(R.layout.custom_cat);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.show();
        init(submit);
        setCustomFont();
    }

    private void init( final OnSubmit submit) {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Loading");
        progressDialog.setCanceledOnTouchOutside(false);

        Button btn_create = (Button)customDialog.findViewById(R.id.create);
        final EditText create_ed = (EditText)customDialog.findViewById(R.id.ed_create);

        ImageView btn_cross = (ImageView)customDialog.findViewById(R.id.cross_button);

        btn_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.cancel();
            }
        });

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (create_ed.getText().toString().equals("")){
                    create_ed.setError("OOPS EMPTY!!!");
                    create_ed.requestFocus();
                }
                submit.onCreateButtonClick(create_ed.getText().toString().trim());
                customDialog.cancel();
            }
        });
    }

    private void setCustomFont() {
    }



    public void showOKAleart(Context context, String title, String message) {
        final AlertDialog alertDialog;
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setTitle("Message");
        alertDialog.setMessage(message);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                alertDialog.dismiss();
                customDialog.cancel();
            }
        });
        alertDialog.show();
    }

    public interface OnSubmit {

        public void onCreateButtonClick(String nameOfCategory);
    }
}