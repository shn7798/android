package shn.study.jandan.ui;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import org.apache.http.HttpResponse;
import shn.study.jandan.AppContext;
import shn.study.jandan.R;
import shn.study.jandan.adapter.ListViewNewsAdapter;
import shn.study.jandan.api.NewsAPI;
import shn.study.jandan.bean.News;
import shn.study.jandan.util.HTTPClientHelper;
import shn.study.jandan.util.ImageHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shn7798 on 2014/9/10.
 */
public class Main extends BaseActivity {
    public final static String TAG = "MainUI";

    private ListView lvNews;

    private Button btnToday;
    private Button btnOnedayago;
    private Button btnTwodaysago;

    private ProgressDialog progressDlg;

    private Handler lvNewsHandler;
    private ListViewNewsAdapter lvNewsAdapter;

    private View.OnClickListener btnTodayOnClick;
    private View.OnClickListener btnOnedayagoOnClick;
    private View.OnClickListener btnTwodaysagoOnClick;

    private List<News> lvNewsData = new ArrayList<News>();


    private void initNewsView(View layNews){
        View layNewsItem = getLayoutInflater().inflate(R.layout.news_listitem, null);

        btnToday = (Button)layNews.findViewById(R.id.catlog_today);
        btnOnedayago = (Button) layNews.findViewById(R.id.catlog_onedayago);
        btnTwodaysago = (Button) layNews.findViewById(R.id.catlog_twodaysago);
        lvNews = (ListView) layNews.findViewById(R.id.lv_news);

        lvNewsHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.d(TAG,"msg.what: "+msg.what);
                switch (msg.what){
                    case AppContext.MSG_NEWS_TEXT_LOAD_START:
                        progressDlg.show();
                        break;
                    case AppContext.MSG_NEWS_TEXT_LOAD_DONE:
                        progressDlg.hide();
                        List<News> newsData = (List<News>)msg.obj;
                        if(newsData != null && newsData.size() > 0){
                            lvNewsData.clear();
                            lvNewsAdapter.notifyDataSetChanged();
                            for(int i=0;i<newsData.size();i++){
                                lvNewsData.add(newsData.get(i));
                            }
                        }
                        lvNewsAdapter.notifyDataSetChanged();
                        break;
                    case AppContext.MSG_NEWS_PIC_LOAD_START:
                        //progressDlg.show();
                        break;
                    case AppContext.MSG_NEWS_PIC_LOAD_DONE:
                        //progressDlg.hide();
                        Bundle data = msg.getData();
                        if(msg.obj != null && data != null){
                            ((News)msg.obj).setPicObj((Bitmap)data.getParcelable("image"));
                            lvNewsAdapter.notifyDataSetChanged();
                        }
                        break;
                }
            }
        };

        //lvNewsAdapter = new ListViewNewsAdapter(this,layNewsItem,News.NEWS_TYPE_NORMAL,lvNewsData,lvNewsHandler);
        lvNewsAdapter = new ListViewNewsAdapter(this,R.layout.news_listitem,News.NEWS_TYPE_NORMAL,lvNewsData,lvNewsHandler);
        lvNews.setAdapter(lvNewsAdapter);

        btnToday = (Button)layNews.findViewById(R.id.catlog_today);
        btnToday.setOnClickListener(new View.OnClickListener() {
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

        btnOnedayago = (Button)layNews.findViewById(R.id.catlog_onedayago);
        btnOnedayago.setOnClickListener(new View.OnClickListener() {
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

        btnTwodaysago = (Button)layNews.findViewById(R.id.catlog_twodaysago);
        btnTwodaysago.setOnClickListener(new View.OnClickListener() {
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

    private void updateNews(int day,List<News> newsList,Handler handler){
        Message msg = handler.obtainMessage(AppContext.MSG_NEWS_TEXT_LOAD_START);
        handler.sendMessage(msg);
        String URL = NewsAPI.getDayNewsURL(day);
        Log.d(TAG, "URL = [" + URL + "]");
        String body = NewsAPI.getPreviewNewsBody(URL);
        List<News> newsListData = NewsAPI.getPreviewNewsItems(body);

        Message msg2 = handler.obtainMessage(AppContext.MSG_NEWS_TEXT_LOAD_DONE,newsListData);
        handler.sendMessage(msg2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View layNews = getLayoutInflater().inflate(R.layout.main,null);
        progressDlg = new ProgressDialog(this);
        progressDlg.hide();

        initNewsView(layNews);
        setContentView(layNews);


    }
}
