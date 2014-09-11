package shn.study.jandan.ui;

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

    private String getNewsPage(String URL){
        HttpResponse response;
        try {
            response = HTTPClientHelper.getFromURL(URL);
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
        HttpEntity entity = response.getEntity();
        String page;
        try {
            page = HTTPClientHelper.InputStreamTOString(entity.getContent());
        } catch (Exception e){
            e.printStackTrace();
            AppContext.showToast(ac, "加载内容异常！");
            return "";
        }
        return page;
    }

    private String processNewsImage(String body){
        return body.replace("<img","<img width=\"100%\"");
    }

    private void updateNewsDetail(String page){
        //String first6 = this.previewBody.substring(0,5);
        String body = StringHelper.getSubString(page,"m</div>","<p><em>",new StringHelper.StringCursor(0),false);
        if(body.length() > 0){

            body = processNewsImage(body);
            Log.d("body",body);
            wvNewsDetailBody.loadDataWithBaseURL("",body,"text/html",this.charset,"");
        }
    }
    private void initNewsDetailView(View layNewsDetail){
        tvNewsDetailBody = (TextView)layNewsDetail.findViewById(R.id.news_detail_body);
        // 先加载预览的数据, 模仿 知乎
        tvNewsDetailBody.setText(previewBody);

        wvNewsDetailBody = (WebView)layNewsDetail.findViewById(R.id.web_news_detail_body);
        //wvNewsDetailBody.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //wvNewsDetailBody.getSettings().setLoadWithOverviewMode(true);
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

        initIntentData();
        initNewsDetailView(layNewsDetail);
        // 加载数据
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case AppContext.MSG_NEWS_DETAIL_LOAD_START:{
                        break;
                    }
                    case AppContext.MSG_NEWS_DETAIL_LOAD_DONE:{
                        updateNewsDetail((String) msg.obj);
                        break;
                    }
                    case AppContext.MSG_SHOW_TOAST:{
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
                Message msg = new Message();
                String page = getNewsPage(link);
                if(!page.equals("")) {
                    msg.obj = page;
                    msg.what = AppContext.MSG_NEWS_DETAIL_LOAD_DONE;
                    handler.sendMessage(msg);
                }
            }
        }.start();
        setContentView(layNewsDetail);
    }
}
