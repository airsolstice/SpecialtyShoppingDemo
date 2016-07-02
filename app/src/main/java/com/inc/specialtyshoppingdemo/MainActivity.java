package com.inc.specialtyshoppingdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements ListView.OnItemClickListener{


    private ListView mListView = null;

    private List<GoodsBean> mDatas = new ArrayList<>();

    private TextView mInputText = null;

    private ProgressBar mBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.lv);
        mBar = (ProgressBar)findViewById(R.id.pb);
        GoodsLoaderAsync loader = new GoodsLoaderAsync(this,mBar,mListView);
        loader.execute();
        mListView.setOnItemClickListener(this);

        mInputText = (TextView) findViewById(R.id.input);
        mInputText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupWindow pw = new PopupWindow(MainActivity.this);
                pw.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                pw.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.pop_search,null);
                ImageButton close = (ImageButton) view.findViewById(R.id.close);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pw.dismiss();
                    }
                });
                pw.setContentView(view);
                ColorDrawable cd = new ColorDrawable(0x0bffffff);
                pw.setBackgroundDrawable(cd);
                pw.showAtLocation(MainActivity.this.findViewById(R.id.containers), Gravity.CENTER,0,0);


            }
        });


    }

    /***
     * 异步数据加载
     */
    private class GoodsLoaderAsync extends AsyncTask<Void,Void,List<GoodsBean>>{

        private Context mContext = null;

        private ProgressBar mBar = null;

        private ListView mView = null;

        private String url = "http://www.51invent.cn:8080/Speciality/rest/GoodsService/getGoodsList";

        public GoodsLoaderAsync(Context context, ProgressBar pb, ListView lv) {
            this.mContext = context;
            this.mBar = pb;
            this.mView = lv;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mView.setVisibility(View.GONE);
        }

        @Override
        protected List<GoodsBean> doInBackground(Void... params) {
            InputStream is = null;
            BufferedReader br = null;
            List<GoodsBean> datas = new ArrayList<>();
            try {
                URL httpUrl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(3000);
                is = conn.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));
                StringBuffer sb = new StringBuffer();
                String str ;

                while((str = br.readLine()) != null){
                    sb.append(str);
                }

                JSONArray arr = new JSONArray(sb.toString().trim());
                for (int i = 0; i < arr.length(); i++) {
                    GoodsBean gb = new GoodsBean();
                    try {
                        JSONObject temp = (JSONObject) arr.get(i);
                        gb.gid = temp.getInt("goodsId");
                        gb.gname = temp.getString("goodsName");
                        gb.introduce = temp.getString("goodsIntroduce");
                        gb.url = temp.getString("goodsImage");
                        gb.sid = temp.getInt("shopId");
                        gb.type = temp.getString("goodsType");
                        gb.origin = temp.getString("location");
                        gb.price = temp.getDouble("price");
                        gb.volume = temp.getInt("number");
                        datas.add(gb);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return datas;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            return null;
        }


        @Override
        protected void onPostExecute(List<GoodsBean> datas) {
            super.onPostExecute(datas);

            if(datas == null){
                return;
            }
            mBar.setVisibility(View.GONE);
            mView.setVisibility(View.VISIBLE);
            mListView.setAdapter(new GoodsListAdapter(mContext, datas));
            mDatas = datas;
        }
    }

    /**
     * 启动详情页，须传递一个商品编号的唯一id过去才可
     * @param parent
     * @param view
     * @param position
     * @param id
     */

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(MainActivity.this,DetailActivity.class);

        if(mDatas != null){
            Bundle b = new Bundle();
            b.putSerializable("goodsData", mDatas.get(position));
            intent.putExtras(b);
        }
        startActivity(intent);

    }


}
