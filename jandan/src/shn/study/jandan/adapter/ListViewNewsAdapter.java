package shn.study.jandan.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import shn.study.jandan.R;
import shn.study.jandan.api.NewsAPI;
import shn.study.jandan.bean.News;
import shn.study.jandan.util.ImageHelper;

import java.util.Date;
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
            convertView.setTag(item);

        } else {
            item = (ListItemView)convertView.getTag();
        }

        News news = this.newsData.get(position);
        item.title.setText(news.getTitle());
        item.title.setTag(news);

        item.body.setText(news.getBody());


        if(news.getPicObj() == null){
            final ListItemView itemRO = item;
            final News newsRO = news;
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what) {
                        case NewsAPI.API_SUCEESS:
                            newsRO.setPicObj((Bitmap)msg.obj);
                            itemRO.pic.setImageBitmap((Bitmap) msg.obj);
                            break;
                    }
                }
            };

            final String imageURL = news.getPicURL();
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    Message msg = new Message();
                    msg.what = NewsAPI.API_SUCEESS;
                    msg.obj = ImageHelper.getImageFromURL(imageURL);
                    handler.sendMessage(msg);
                }
            }.start();
        }

        item.pic.setImageBitmap(news.getPicObj());
        return convertView;
    }
}
