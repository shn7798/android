package shn.study.jandan.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by shn7798 on 2014/9/10.
 */
public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
}
