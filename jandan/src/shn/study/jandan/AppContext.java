package shn.study.jandan;

import android.app.Application;
import android.content.res.Configuration;
import android.widget.Toast;

/**
 * Created by shn7798 on 14-9-7.
 */
public class AppContext extends Application {
    private Toast toast;
    @Override
    public void onCreate() {
        super.onCreate();
        toast = new Toast(this);
    }

    /**
     * This method is for use in emulated process environments.  It will
     * never be called on a production Android device, where processes are
     * removed by simply killing them; no user code (including this callback)
     * is executed when doing so.
     */

    public Toast getToast(){
        return toast;
    }
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
