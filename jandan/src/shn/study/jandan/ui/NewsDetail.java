package shn.study.jandan.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import shn.study.jandan.AppContext;
import shn.study.jandan.R;
import shn.study.jandan.api.NewsAPI;
import shn.study.jandan.util.HTTPClientHelper;
import shn.study.jandan.util.StringHelper;

import java.io.IOException;

/**
 * Created by shn7798 on 2014/9/10.
 */
public class NewsDetail extends BaseActivity {

    private Context ac;
    private Handler handler;
    private String title;
    private String link;
    private String previewBody;
    private String charset;

    private TextView tvNewsDetailBody;
    private WebView wvNewsDetailBody;
    private ProgressDialog progressDlg;


    private String fetchNewsPage(String URL){
        String page;
        try {
            page = NewsAPI.getNewsPage(URL);
        } catch (ClientProtocolException e){
            e.printStackTrace();
            handler.obtainMessage(AppContext.MSG_SHOW_TOAST,"网络连接失败！")
                    .sendToTarget();
            return "";
        } catch (IOException e){
            e.printStackTrace();
            handler.obtainMessage(AppContext.MSG_SHOW_TOAST,"加载内容失败，请重试！")
                    .sendToTarget();
            return "";
        }
        return page;
    }
    private void updateNewsDetail(String page){
        if(page.length() > 0) {
            String body = NewsAPI.getNewsBody(page);
            if(body.length() > 0) {
                body = NewsAPI.processNewsImage(body);
                Log.d("webview",body);
                wvNewsDetailBody.loadDataWithBaseURL("", body, "text/html", this.charset, "");
                return;
            }
        }

        handler.obtainMessage(AppContext.MSG_SHOW_TOAST,"加载内容失败，请重试！")
                .sendToTarget();


    }
    private void initNewsDetailView(View layNewsDetail){
        tvNewsDetailBody = (TextView)layNewsDetail.findViewById(R.id.news_detail_body);
        // 先加载预览的数据, 模仿 知乎
        tvNewsDetailBody.setText(previewBody);

        wvNewsDetailBody = (WebView)layNewsDetail.findViewById(R.id.web_news_detail_body);
        wvNewsDetailBody.loadDataWithBaseURL("",previewBody,"text/html",this.charset,"");
    }

    private void initIntentData(){
        Intent intent = getIntent();
        this.title = intent.getStringExtra("title");
        this.link = intent.getStringExtra("link");
        this.previewBody = intent.getStringExtra("previewBody");
        this.charset = intent.getStringExtra("charset");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ac = getApplicationContext();
        View layNewsDetail = getLayoutInflater().inflate(R.layout.news_detail,null);

        progressDlg = new ProgressDialog(this);
        progressDlg.setCanceledOnTouchOutside(false);
        progressDlg.setCancelable(false);
        progressDlg.hide();

        initIntentData();
        initNewsDetailView(layNewsDetail);
        // 加载数据
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case AppContext.MSG_NEWS_DETAIL_LOAD_START:{
                        progressDlg.show();
                        break;
                    }
                    case AppContext.MSG_NEWS_DETAIL_LOAD_DONE:{
                        progressDlg.hide();
                        updateNewsDetail((String) msg.obj);
                        break;
                    }
                    case AppContext.MSG_SHOW_TOAST:{
                        progressDlg.hide();
                        AppContext.showToast(NewsDetail.this,(String)msg.obj);
                        break;
                    }
                }
            }
        };

        new Thread(){
            @Override
            public void run() {
                super.run();
                handler.obtainMessage(AppContext.MSG_NEWS_DETAIL_LOAD_START)
                        .sendToTarget();
                Log.d("thread link: ",link);
                String page = fetchNewsPage(link);

                handler.obtainMessage(AppContext.MSG_NEWS_DETAIL_LOAD_DONE,page)
                        .sendToTarget();

            }
        }.start();
        setContentView(layNewsDetail);
    }

    @Override
    protected void onDestroy() {
        progressDlg.hide();
        progressDlg.dismiss();

        super.onDestroy();
    }
}
