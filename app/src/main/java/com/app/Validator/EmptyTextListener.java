package com.app.Validator;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by ericbasendra on 11/07/15.
 */
public class EmptyTextListener implements TextView.OnEditorActionListener {
    private EditText et,et1;

    public EmptyTextListener(EditText editText,EditText editText1) {
        this.et = editText;
        this.et1 = editText1;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
            // Called when user press Next button on the soft keyboard

            if (et.getText().toString().equals(""))
                et.setError("Oops! empty.");
            else{
                try {
                    String s = et.getText().toString().substring(0,et.getText().toString().indexOf("@"));
                    et1.setText(s);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    private void username(){
        et1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et.getText().toString().equals(""))
                    et1.setText("");
                else{
                    try {
                        String s = et.getText().toString().substring(0,et.getText().toString().indexOf("@"));
                        et1.setText(s);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
