package com.app.Validator;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by ericbasendra on 11/07/15.
 */
public class EmptyTextListener implements TextView.OnEditorActionListener {
    private EditText et;

    public EmptyTextListener(EditText editText) {
        this.et = editText;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
            // Called when user press Next button on the soft keyboard

            if (et.getText().toString().equals(""))
                et.setError("Oops! empty.");
        }
        return false;
    }
}
