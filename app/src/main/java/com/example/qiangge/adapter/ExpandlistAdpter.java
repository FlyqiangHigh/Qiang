package com.example.qiangge.adapter;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qiangge.qiangge.R;

import org.w3c.dom.Text;

import java.util.zip.Inflater;

/**
 * Created by qiangge on 2016/4/30.
 */
public class ExpandlistAdpter extends BaseExpandableListAdapter {
    private String[] group_title = new String[]{"我的好友","同伴"};
    private String[][] child_text_array = new String[][]{
            {"小强","小明","小伙"},
            {"小红","小鹏"}
    };
    private Context mContext;
    public ExpandlistAdpter(Context context){
        this.mContext = context;
    }
    /**
     * 获取一级标签总数
     */
    @Override
    public int getGroupCount() {
        return group_title.length;
    }
    /**
     * 获取一级标签下二级标签的总数
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return child_text_array[groupPosition].length;
    }
    /**
     * 获取一级标签内容
     */
    @Override
    public Object getGroup(int groupPosition) {
        return group_title[groupPosition];
    }
    /**
     * 获取一级标签下二级标签的内容
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child_text_array[groupPosition][childPosition];
    }
    /**
     * 获取一级标签的ID
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    /**
     * 获取二级标签的ID
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    /**
     * 指定位置相应的组视图
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.expandlistparent,null);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.rightindicate);
        TextView textView = (TextView) convertView.findViewById(R.id.expand_text);
        textView.setText(group_title[groupPosition]);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.expandlistchild,null);
        TextView textView = (TextView) convertView.findViewById(R.id.expandchild_tv);
        textView.setText(child_text_array[groupPosition][childPosition]);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
