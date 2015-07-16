package com.app.Validator;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.app.aggro.R;

import java.util.regex.Pattern;

/**
 * Created by ericbasendra on 11/07/15.
 */
public class InputValidator implements TextWatcher {
    private EditText et;

    public InputValidator(EditText editText) {
        this.et = editText;
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before,
                              int count) {
        if (s.length() != 0) {
            switch (et.getId()) {
                case R.id.name: {
                    if (!Pattern.matches("^[a-z]{1,16}$", s)) {
                        et.setError("Oops! Name must have only a-z");
                    }
                }
                break;

                case R.id.username: {
                    if (!Pattern.matches("^[a-z]{1,16}$", s)) {
                        et.setError("Oops! Username must have only a-z");
                    }
                }
                break;

                case R.id.age: {
                    if (!Pattern.matches("[0-9]+", s)) {
                        et.setError("Oops! Enetr your age in digits only");
                    }
                }
                break;

                case R.id.email:{
                    String EMAIL_PATTERN =
                            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                    if (!Pattern.matches(EMAIL_PATTERN, s)) {
                        et.setError("Oops! Email is invalid");
                    }
                }
                break;

                }
            }
        }
    }

