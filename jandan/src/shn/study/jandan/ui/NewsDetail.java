package shn.study.jandan.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import shn.study.jandan.AppContext;
import shn.study.jandan.R;
import shn.study.jandan.util.HTTPClientHelper;
import shn.study.jandan.util.StringHelper;

import java.io.IOException;

/**
 * Created by shn7798 on 2014/9/10.
 */
public class NewsDetail extends BaseActivity {

    private String title;
    private String link;
    private String previewBody;

    private TextView tvNewsDetailBody;

    private String parseNewsDetail(String body){
        String result = new String(body);
        return result.replace("</p>","\r\n")
                .replace("</P>","\r\n")
                .replace("<p>","")
                .replace("<P>","");
    }
    private String getNewsPage(String URL){
        HttpResponse response = HTTPClientHelper.getFromURL(URL);
        HttpEntity entity = response.getEntity();
        String page;
        try {
            page = HTTPClientHelper.InputStreamTOString(entity.getContent());
        } catch (Exception e){
            page = "";
            e.printStackTrace();
        }
        return page;
    }
    private void updateNewsDetail(String page){
        String first6 = this.previewBody.substring(0,5);
        String body = StringHelper.getSubString(page,"<p>"+first6,"<p><em>",new StringHelper.StringCursor(0),false);
        if(body.length() > 0){
            body = first6+body;
            body = parseNewsDetail(body);
            tvNewsDetailBody.setText(first6+body);
        }
    }
    private void initNewsDetailView(View layNewsDetail){
        tvNewsDetailBody = (TextView)layNewsDetail.findViewById(R.id.news_detail_body);
        // 先加载预览的数据, 模仿 知乎
        tvNewsDetailBody.setText(previewBody);
    }

    private void initIntentData(){
        Intent intent = getIntent();
        this.title = intent.getStringExtra("title");
        this.link = intent.getStringExtra("link");
        this.previewBody = intent.getStringExtra("previewBody");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View layNewsDetail = getLayoutInflater().inflate(R.layout.news_detail,null);

        initIntentData();
        initNewsDetailView(layNewsDetail);
        // 加载数据
        final Handler handler = new Handler(){
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
                }
            }
        };

        new Thread(){
            @Override
            public void run() {
                super.run();
                Message msg = new Message();
                msg.obj = getNewsPage(link);
                msg.what = AppContext.MSG_NEWS_DETAIL_LOAD_DONE;
                handler.sendMessage(msg);
            }
        }.start();
        setContentView(layNewsDetail);
    }
}
