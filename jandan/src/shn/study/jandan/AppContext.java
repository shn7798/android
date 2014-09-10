package shn.study.jandan;

import android.app.Application;
import android.content.res.Configuration;
import android.widget.Toast;

/**
 * Created by shn7798 on 14-9-7.
 */
public class AppContext extends Application {
    public final static int MSG_NEWS_TEXT_LOAD_START = 0x01;
    public final static int MSG_NEWS_TEXT_LOAD_DONE = 0x02;
    public final static int MSG_NEWS_PIC_LOAD_START = 0x03;
    public final static int MSG_NEWS_PIC_LOAD_DONE = 0x04;

    public final static int MSG_NEWS_ITEM_CLICK = 0x05;
    public final static int MSG_NEWS_DETAIL_LOAD_START = 0x06;
    public final static int MSG_NEWS_DETAIL_LOAD_DONE = 0x07;

    /**
     * This method is for use in emulated process environments.  It will
     * never be called on a production Android device, where processes are
     * removed by simply killing them; no user code (including this callback)
     * is executed when doing so.
     */


    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}
