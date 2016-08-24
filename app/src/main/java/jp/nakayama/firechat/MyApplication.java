package jp.nakayama.firechat;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by nakayama on 2016/07/25.
 */
public class MyApplication extends Application{

    @Override
    public void onCreate(){
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
