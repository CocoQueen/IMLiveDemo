package com.coco.imlivedemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.coco.imlivedemo.R;

import java.util.List;

/**
 * Created by ydx on 18-6-1.
 */

public class WatchAdapter extends BaseAdapter {

    private List<String> list;
    private Context context;

    public WatchAdapter(List<String> list, Context context) {
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
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_watch, null);
            holder.mTv_sendMsg = convertView.findViewById(R.id.mTv_send);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTv_sendMsg.setText(list.get(position));

        return convertView;
    }

    class ViewHolder {
        TextView mTv_sendMsg;
    }

}
