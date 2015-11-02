package shn.study.jandan2.activity;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import shn.study.jandan2.R;
import shn.study.jandan2.adapter.NewsInfoListAdapter;
import shn.study.jandan2.api.NewsInfo.JandanNewsInfo;
import shn.study.jandan2.beans.NewsInfo;
import shn.study.jandan2.utils.HttpHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shn7798 on 15-11-1.
 */
public class NewsInfoActivity extends Activity{
    private Context context;
    private View rootView;
    private ListView lvNewsInfoList;
    private NewsInfoListAdapter lvNewsInfoListAdapter;
    private List<NewsInfo> newsInfoListData = new ArrayList<>();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        rootView = View.inflate(context, R.layout.fragment_news_info, null);
        lvNewsInfoList = (ListView)rootView.findViewById(R.id.lvNewsInfoList);
        lvNewsInfoListAdapter = new NewsInfoListAdapter(context, newsInfoListData);
        lvNewsInfoList.setAdapter(lvNewsInfoListAdapter);

        //loadData();
        new LoadHtmlTask().execute();
        setContentView(rootView);
    }

    public void loadData(Document doc){
        newsInfoListData.clear();
        if(doc != null){
            try {
                Elements es = doc.select("#content > div.post.f.list-post");
                for(Element e : es) {
                    try {
                        NewsInfo newsInfo = JandanNewsInfo.parse(e);
                        newsInfoListData.add(newsInfo);
                    }
                    catch (NullPointerException npe){
                        npe.printStackTrace();
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        lvNewsInfoListAdapter.notifyDataSetChanged();
    }

    private class LoadHtmlTask extends AsyncTask{
        @Override
        protected void onPostExecute(Object o) {
            loadData((Document) o);
        }

        @Override
        protected Object doInBackground(Object[] params) {
            Document doc = null;
            try {
                String url = "http://jandan.net/";
                //String url = "http://192.168.5.250:8888/";
                String html = HttpHelper.fetchJandanHtml(url);
                doc = Jsoup.parse(html,url);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return doc;
        }
    }

}
