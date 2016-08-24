package jp.nakayama.firechat;

/**
 * Created by nakayama on 2016/08/24.
 */
public class Chat {
    private String mSender;
    private String mBody;

    Chat(String sender, String body) {
        this.mSender = sender;
        this.mBody = body;
    }

    public String getSender() {
        return mSender;
    }

    public void setSender(String sender) {
        mSender = sender;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(String body) {
        mBody = body;
    }
}

