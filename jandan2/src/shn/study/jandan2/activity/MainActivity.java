package shn.study.jandan2.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import shn.study.jandan2.R;

/**
 * Created by shn7798 on 15-10-31.
 */
public class MainActivity extends Activity {
    private Context context;
    private View rootView;
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        rootView = View.inflate(context, R.layout.main, null);

        //fgNewsInfo = View.inflate(context, R.layout.fragment_news_info, null);

        setContentView(rootView);
    }
}
