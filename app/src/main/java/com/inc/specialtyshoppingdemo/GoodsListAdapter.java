package com.inc.specialtyshoppingdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.inc.specialtyshoppingdemo.imgload.ImageLoader;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Holy-Spirit on 2016/5/30.
 */
@SuppressWarnings("ALL")
public class GoodsListAdapter extends BaseAdapter {

    private List<GoodsBean> mDatas = null;
    private ImageLoader mLoader = null;

    private Context mContext = null;

    public GoodsListAdapter(Context context, List<GoodsBean> datas) {
        mLoader = new ImageLoader(context);
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mDatas.get(position).gid;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vh;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item, null);
            vh = new ViewHolder();

            vh.title = (TextView) convertView.findViewById(R.id.item_title);
            vh.detail = (TextView) convertView.findViewById(R.id.item_detail);
            vh.price = (TextView) convertView.findViewById(R.id.item_price);
            vh.volume = (TextView) convertView.findViewById(R.id.item_volume);
            vh.origin = (TextView) convertView.findViewById(R.id.item_origin);
            vh.img = (ImageView) convertView.findViewById(R.id.item_img);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.title.setText(mDatas.get(position).gname);
        vh.detail.setText(mDatas.get(position).introduce);
        vh.price.setText("￥ " + mDatas.get(position).price);
        vh.volume.setText("销量 " + mDatas.get(position).volume);
        vh.origin.setText("产地 " + mDatas.get(position).origin);

        mLoader.display(mDatas.get(position).url,vh.img);

        return convertView;
    }


}


class ViewHolder {
    public TextView title;
    public TextView detail;
    public TextView price;
    public TextView volume;
    public TextView origin;
    public ImageView img;

}


class GoodsBean implements Serializable{
    public int gid;
    public String gname;
    public String introduce;
    public String url;
    public int sid;
    public String type;
    public String origin;
    public double price;
    public int volume;


}