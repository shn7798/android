package shn.study.jandan;

import android.app.Activity;
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
import shn.study.jandan.util.HTTPClientHelper;

import java.util.ArrayList;
import java.util.List;


public class AppStart extends Activity {

    public static final String TAG = "AppStart";
    private static final String URL_NEWS_TODAY = "http://jandan.net/";
    private static final String URL_NEWS_ONEDAYSAGO = "http://jandan.net/";

    private ListView lvNews;
    private ListViewNewsAdapter lvNewsAdapter;
    private AppContext ac;
    private List<News> lvNewsData;
    private Button btnTodayNews;
    private Button btnOnedayagoNews;
    private Button btnTwodaysagoNews;

    private Handler lvNewsHandler;


    private void updateNews(int day,List<News> newsList,Handler handler){
        String URL = NewsAPI.getDayNewsURL(day);
        Log.d(TAG,"URL = [" +URL + "]");
        HttpResponse response = HTTPClientHelper.getFromURL(URL);
        String body = NewsAPI.getPreviewNewsBody(URL);
        List<News> newsListData = NewsAPI.getPreviewNewsItems(body);
        for(int i=0;i<newsListData.size();i++){
            if(i >= newsList.size()){
                newsList.add(newsListData.get(i));
            } else {
                newsList.set(i, newsListData.get(i));
            }
        }

        for(int i = newsList.size(); i > newsListData.size(); i--){
            newsList.remove(i-1);
        }


        Message msg = new Message();
        msg.what = NewsAPI.API_SUCEESS;
        handler.sendMessage(msg);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ac = (AppContext)getApplication();


        lvNewsData = new ArrayList<News>();


        lvNews = (ListView)findViewById(R.id.lv_news);
        lvNewsAdapter = new ListViewNewsAdapter(ac,0,lvNewsData,R.layout.news_listitem);
        lvNews.setAdapter(lvNewsAdapter);

        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private Toast toast = new Toast(getApplicationContext());
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = null;
                if(view instanceof TextView){
                    news = (News) view.getTag();
                } else {
                    TextView tv = (TextView)view.findViewById(R.id.news_listitem_title);
                    news = (News) tv.getTag();
                }
                if(news == null) return;
                Toast.makeText(ac,news.getBody(),Toast.LENGTH_LONG)
                        .show();
            }
        });

        lvNewsHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case NewsAPI.API_SUCEESS:
                        lvNewsAdapter.notifyDataSetChanged();
                }
            }
        };
        btnTodayNews = (Button)findViewById(R.id.catlog_today);
        btnTodayNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        updateNews(0,lvNewsData, lvNewsHandler);
                    }
                }.start();

            }
        });

        btnOnedayagoNews = (Button)findViewById(R.id.catlog_onedayago);
        btnOnedayagoNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        updateNews(-1,lvNewsData, lvNewsHandler);
                    }
                }.start();

            }
        });

        btnTwodaysagoNews = (Button)findViewById(R.id.catlog_twodaysago);
        btnTwodaysagoNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        updateNews(-2,lvNewsData, lvNewsHandler);
                    }
                }.start();

            }
        });

    }
}
