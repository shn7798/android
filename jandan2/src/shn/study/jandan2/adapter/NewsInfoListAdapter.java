package shn.study.jandan2.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import shn.study.jandan2.R;
import shn.study.jandan2.beans.NewsInfo;
import shn.study.jandan2.utils.AsyncImageLoader;
import shn.study.jandan2.utils.HttpHelper;

import java.util.List;

/**
 * Created by shn7798 on 15-11-1.
 */
public class NewsInfoListAdapter extends BaseAdapter {
    private Context context;
    private List<NewsInfo> newsInfoList;
    public AsyncImageLoader asyncImageLoader;

    public NewsInfoListAdapter(Context context, List<NewsInfo> newsInfoList) {
        this.context = context;
        this.newsInfoList = newsInfoList;
        this.asyncImageLoader = new AsyncImageLoader();
    }

    public void setNewsInfoList(List<NewsInfo> newsInfoList) {
        this.newsInfoList = newsInfoList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();

            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.layout_news_info_item, null);

            viewHolder.icon = (ImageView) convertView.findViewById(R.id.icon);
            viewHolder.title = (TextView) convertView.findViewById(R.id.news_title);
            viewHolder.info = (TextView) convertView.findViewById(R.id.news_info);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }


        NewsInfo item = (NewsInfo)getItem(position);
        if(item != null){
            viewHolder.title.setText(item.getTitle());
            viewHolder.info.setText(item.getIntros());
            // 先清空image
            viewHolder.icon.setImageBitmap(null);
            final ImageView ivIcon = viewHolder.icon;

            Drawable cachedImage = asyncImageLoader.loadDrawable(item.getIconLink(), new AsyncImageLoader.ImageCallback() {
                @Override
                public void imageLoaded(Drawable imageDrawable, String imageUrl) {
                    ivIcon.setImageDrawable(imageDrawable);
                    notifyDataSetChanged();
                }
            });

            if(cachedImage != null){
                viewHolder.icon.setImageDrawable(cachedImage);
            }
        }


        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        NewsInfo item = null;
        if(newsInfoList != null){
            item = newsInfoList.get(position);
        }
        return item;
    }

    @Override
    public int getCount() {
        if(newsInfoList != null){
            return newsInfoList.size();
        }
        else{
            return 0;
        }
    }

    private static class ViewHolder{
        ImageView icon;
        TextView title;
        TextView info;
    }
}
