package jp.nakayama.firechat;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends Activity {
    public static final String TAG = "firebase";
    private static final String FIREBASE_URL = "https://firechat-2cd34.firebaseio.com/";
    private static final String SENDER = "user1";
    private String mUsername;
    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private ArrayList<Message> mMessages;
    private MessageView mMessageView;
    private Bitmap mMyIcon;
    private Bitmap mFriendIcon;
    private EditText mInputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setupUsername();

        mFirebaseRef = new Firebase(FIREBASE_URL).child("chat");

        mInputText = (EditText)findViewById(R.id.messageInput);
        mInputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    sendMessage();
                }
                return true;
            }
        });

        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mInputText.getText().toString().equals("")) {
                    sendMessage();
                }
            }
        });

        mMessageView = (MessageView)findViewById(R.id.messageView);
        mMessages = new ArrayList<>();

        mMyIcon = BitmapFactory.decodeResource(getResources(),R.drawable.face_2);
        mFriendIcon = BitmapFactory.decodeResource(getResources(),R.drawable.face_1);

        setFirebaseEvents();


    }

    public void setFirebaseEvents() {
        mFirebaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String s) {
//                String user = (String) snapshot.child("sender").getValue();
//                String messageText = (String) snapshot.child("body").getValue();
//                Message message = new Message();
//                message.setUserName(user);
//                message.setMessageText(messageText);
//                message.setRightMessage(true);
//                message.setUserIcon(mMyIcon);
//                mMessages.add(message);
//                mMessageView.setMessage(message, true);

                String user = (String) snapshot.child("sender").getValue();
                String messageText = (String) snapshot.child("body").getValue();
                Message message = new Message();
                message.setUserName(user);
                message.setMessageText(messageText);
                if (user.equals(SENDER)) {
                    message.setRightMessage(true);
                    message.setUserIcon(mMyIcon);
                } else {
                    message.setRightMessage(false);
                    message.setUserIcon(mFriendIcon);
                }
                mMessages.add(message);
                mMessageView.init();
                mMessageView.addMessage(message);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }



    @Override
    public void onStart() {
        super.onStart();

        mFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                }
                mMessageView.init(mMessages);
                //removeListener(this);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void removeListener(ValueEventListener listener) {
        mFirebaseRef.removeEventListener(listener);
        //setFirebaseEvents();
    }



    private void sendMessage() {
        String number = String.valueOf(mMessages.size());
        String body = mInputText.getText().toString();
        Chat chat = new Chat(SENDER, body);

        mFirebaseRef.push().setValue(chat);
        mInputText.setText("");
    }



}


