package jp.nakayama.firechat;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by nakayama on 2016/08/08.
 */
public class MessageView extends ListView {

    private List<Message> mMessageList;
    private MessageAdapter mMessageAdapter;

    public MessageView(Context context, ArrayList<Message> messages) {
        super(context);
        init(messages);
    }


    public MessageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MessageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void init(ArrayList<Message> list) {
        mMessageList = new ArrayList<>();
        setChoiceMode(ListView.CHOICE_MODE_NONE);

        for(int i=0; i < list.size(); i++){
            setMessage(list.get(i),false);
        }

        mMessageAdapter = new MessageAdapter(getContext(), 0, mMessageList);
        setDividerHeight(0);
        setAdapter(mMessageAdapter);

    }

    public void init() {
        mMessageList = new ArrayList<>();
        mMessageAdapter = new MessageAdapter(getContext(), 0, mMessageList);
        setDividerHeight(0);
        setAdapter(mMessageAdapter);
    }


    public void setMessage(Message message, Boolean addAdapter){
        if(mMessageList.size() == 0){
            setDateSeparator(message);
        }else{
            Message prevMessage = mMessageList.get(mMessageList.size() - 1);

            int diff = message.getCompareDate().compareTo(prevMessage.getCompareDate());
            if(diff != 0){
                setDateSeparator(message);
            }
        }
        mMessageList.add(message);
        if (addAdapter) {
            mMessageAdapter.add(message);
        }
        this.setSelection(this.mMessageList.size());
    }

    public void addMessage(Message message) {

        mMessageList.add(message);
        mMessageAdapter.add(message);

        this.setSelection(this.mMessageList.size());
    }

    private void setDateSeparator(Message message) {
        Message dateSeparator = new Message();
        dateSeparator.setDateCell(true);
        dateSeparator.setDateSeparateText(message.getDateSeparateText());
        mMessageList.add(dateSeparator);
    }

    public void setMessageList(ArrayList<Message> list){
        mMessageList = new ArrayList<>(list);
    }

}