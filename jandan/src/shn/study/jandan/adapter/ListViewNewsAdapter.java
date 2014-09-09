package shn.study.jandan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import shn.study.jandan.R;
import shn.study.jandan.bean.News;

import java.util.List;

/**
 * Created by shn7798 on 14-9-8.
 */
public class ListViewNewsAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater listContainer;
    private int resource;
    private int newsType;
    private List<News> newsData;

    static class ListItemView {
        public TextView title;
        public TextView body;
        public ImageView pic;
        public Button getBody;
        public Button getTitle;
    }

    public ListViewNewsAdapter(Context context, int newsType, List<News> newsData, int resource) {
        this.context = context;
        this.listContainer = LayoutInflater.from(context);
        this.resource = resource;
        this.newsType = newsType;
        this.newsData = newsData;
    }

    @Override
    public int getCount() {
        return newsData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListItemView item = null;

        if(convertView == null){
            convertView = this.listContainer.inflate(this.resource, null);

            item = new ListItemView();
            item.title = (TextView)convertView.findViewById(R.id.news_listitem_title);
            item.body = (TextView)convertView.findViewById(R.id.news_listitem_body);
            item.pic = (ImageView)convertView.findViewById(R.id.news_listitem_pic);
            item.getBody = (Button) convertView.findViewById(R.id.news_listitem_getbody);
            item.getTitle = (Button) convertView.findViewById(R.id.news_listitem_gettitle);

            convertView.setTag(item);

        } else {
            item = (ListItemView)convertView.getTag();
        }

        News news = this.newsData.get(position);
        item.title.setText(news.getTitle());
        item.title.setTag(news);

        item.body.setText(news.getBody());
        //item.pic.setImageURI(news);

        item.getBody.setTag(item);
        item.getBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListItemView item = (ListItemView)v.getTag();
                Toast.makeText(context,
                    item.body.getText(),
                    Toast.LENGTH_SHORT)
                .show();

            }
        });

        item.getTitle.setTag(item);
        item.getTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListItemView item = (ListItemView)v.getTag();
                Toast.makeText(context,
                        item.title.getText(),
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        return convertView;
    }
}
