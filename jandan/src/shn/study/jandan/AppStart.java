package shn.study.jandan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.*;
import android.os.Handler;
import android.util.Log;
import org.apache.http.HttpResponse;
import shn.study.jandan.adapter.ListViewNewsAdapter;
import shn.study.jandan.api.NewsAPI;
import shn.study.jandan.bean.News;
import shn.study.jandan.ui.Main;
import shn.study.jandan.util.HTTPClientHelper;

import java.util.ArrayList;
import java.util.List;


public class AppStart extends Activity {

    public static final String TAG = "AppStart";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);

        redirectToNews();
    }

    private void redirectToNews(){
        Intent intent = new Intent(this, Main.class);
        this.startActivity(intent);
        finish();
    }
}
