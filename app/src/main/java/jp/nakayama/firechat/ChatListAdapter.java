package jp.nakayama.firechat;

import android.app.Activity;
import android.view.View;

import com.firebase.client.Query;

/**
 * Created by nakayama on 2016/08/24.
 */
public class ChatListAdapter extends FirebaseListAdapter<Message> {


    private  String mUsername;
    public ChatListAdapter(Query ref, Activity activity, int layout, String username) {
        super(ref, Message.class, layout, activity);
        this.mUsername = username;
    }

    @Override
    protected void populateView(View view, Message message) {
        String user = message.getUserName();


    }
}
