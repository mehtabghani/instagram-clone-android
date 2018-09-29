package com.maddy.instagramclone.override;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

public class CustomEditTextView extends AppCompatEditText {

    public CustomEditTextView(Context context) {
        super(context);
    }

    public CustomEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean validate() {
        if(this.getText().equals(""))
            return false;

        return true;
    }
}
