package me.mattlogan.imagesearch.ui;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by matthewlogan on 9/3/14.
 */
public class BaseActivity extends ActionBarActivity {

    protected void hideKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager imm =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
