package shn.study.jandan.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import shn.study.jandan.AppContext;
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
    private int newsType;
    private List<News> newsData;
    private int layoutID;
    private Handler handler;

    private class ListItemView {
        public TextView title;
        public TextView body;
        public ImageView pic;
        public Button getBody;
        public Button getTitle;
    }

    public ListViewNewsAdapter(Context context, int layoutID,int newsType, List<News> newsData, Handler handler) {
        this.context = context;
        this.newsType = newsType;
        this.newsData = newsData;
        this.handler = handler;
        this.layoutID = layoutID;
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
            convertView = LayoutInflater.from(this.context).inflate(this.layoutID,null);

            item = new ListItemView();
            item.title = (TextView)convertView.findViewById(R.id.news_listitem_title);
            item.body = (TextView)convertView.findViewById(R.id.news_listitem_body);
            item.pic = (ImageView)convertView.findViewById(R.id.news_listitem_pic);
            convertView.setTag(item);

        } else {
            item = (ListItemView)convertView.getTag();
        }

        final News news = this.newsData.get(position);

        // 解除OnClickListener, 释放News的引用
        convertView.setOnClickListener(null);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = AppContext.MSG_NEWS_ITEM_CLICK;
                msg.obj = news;
                handler.sendMessage(msg);
            }
        });

        item.title.setText(news.getTitle());
        item.title.setTag(news);
        item.body.setText(news.getBody());
        // 先清空image
        item.pic.setImageBitmap(null);
        if(news.getPicObj() == null) {
            Message msg = new Message();
            msg.what = AppContext.MSG_NEWS_PIC_LOAD_START;
            handler.sendMessage(msg);

            ImageHelper.threadGetImageFromURL(news, news.getPicURL(), handler, AppContext.MSG_NEWS_PIC_LOAD_DONE);
        } else {
            item.pic.setImageBitmap(news.getPicObj());
        }
        return convertView;
    }
}
