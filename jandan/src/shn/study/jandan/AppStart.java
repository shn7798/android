package shn.study.jandan;

import android.app.Activity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import shn.study.jandan.adapter.ListViewNewsAdapter;
import shn.study.jandan.bean.News;

import java.util.ArrayList;
import java.util.List;


public class AppStart extends Activity {

    private ListView lvNews;
    private ListViewNewsAdapter lvNewsAdapter;
    private AppContext ac;


    public void updateNews(List<News> newsList){
        for(int i=0;i<100;i++) {
            News news = new News();
            news.setTitle("这是第{0}篇文章的标题".replace("{0}",String.valueOf(i + 1)));
            news.setBody("今天是中秋节");
            newsList.add(news);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ac = (AppContext)getApplication();


        List<News> newsList = new ArrayList<News>();
        updateNews(newsList);

        lvNews = (ListView)findViewById(R.id.lv_news);
        lvNewsAdapter = new ListViewNewsAdapter(ac,0,newsList,R.layout.news_listitem);
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
                Toast.makeText(ac,news.getTitle(),Toast.LENGTH_SHORT)
                        .show();
            }
        });

    }
}
