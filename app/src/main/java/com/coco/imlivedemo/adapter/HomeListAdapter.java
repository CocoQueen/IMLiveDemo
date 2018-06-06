package com.coco.imlivedemo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.coco.imlivedemo.R;
import com.tencent.TIMConversation;
import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;

import java.util.List;

/**
 * Created by ydx on 18-6-1.
 */

public class HomeListAdapter extends BaseAdapter {
    private static final String TAG = "HomeListAdapter";

    private List<String> list;
    private Context context;
    private String str;

    public HomeListAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        List<TIMConversation> conversionList = TIMManager.getInstance().getConversionList();
        TIMConversation conversation = conversionList.get(position);
        String identifer = conversation.getIdentifer();
        long cnt = TIMManager.getInstance().getConversationCount();
        //遍历会话列表
        for(long i = 0; i < cnt; ++i) {
            //根据索引获取会话
           conversation =
                    TIMManager.getInstance().getConversationByIndex(i);
            Log.d(TAG, "get conversation. type: " + conversation.getType());
        }

        List<TIMMessage> lastMsgs = conversation.getLastMsgs(cnt);
        TIMMessage message = lastMsgs.get(position);


        for(int i = 0; i < message.getElementCount(); ++i) {
            TIMElem elem = message.getElement(i);
            //获取当前元素的类型
            TIMElemType elemType = elem.getType();
            Log.d(TAG, "elem type: " + elemType.name());
            if (elemType == TIMElemType.Text) {
                //处理文本消息
                boolean b = message.convertToImportedMsg();
                int msg = conversation.importMsg(lastMsgs);
                str = String.valueOf(msg);

            } else if (elemType == TIMElemType.Image) {
                Log.e(TAG, "getView: "+message.toString() );
            }//...处理更多消息
        }


        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_home, null);
            holder.mTv_myGroup = convertView.findViewById(R.id.mTv_myGroup);
            holder.mTv_lastChat=convertView.findViewById(R.id.mTv_lastChat);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTv_myGroup.setText(list.get(position));

        holder.mTv_lastChat.setText(identifer+"=========="+str);

        return convertView;
    }

    class ViewHolder {
        TextView mTv_myGroup,mTv_lastChat;
    }

}
