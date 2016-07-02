package com.inc.specialtyshoppingdemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.inc.specialtyshoppingdemo.imgload.ImageLoader;

public class DetailActivity extends Activity {

    private GoodsBean mGoods = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mGoods = (GoodsBean) getIntent().getExtras().getSerializable("goodsData");
        if(mGoods != null){
            TextView title = (TextView) findViewById(R.id.title);
            title.setText(mGoods.gname);
            TextView introduce = (TextView)findViewById(R.id.introduce);
            introduce.setText(mGoods.introduce);
            TextView shopName = (TextView)findViewById(R.id.shopName);
            shopName.setText("商店编号："+mGoods.sid);
            ImageView introduceImg = (ImageView)findViewById(R.id.introduce_img);
            System.out.println("-->>max width="+introduce.getMaxWidth());
            Bitmap bm = ImageLoader.adjustBitmap(getResources(),R.drawable.food_introduce2,introduceImg);
            introduceImg.setImageBitmap(bm);

            ImageView headImg = (ImageView)findViewById(R.id.head_img);
            bm = ImageLoader.adjustBitmap(getResources(),R.drawable.food_introduce2,headImg);
            headImg.setImageBitmap(bm);

        }


        TextView comment = (TextView) findViewById(R.id.comment);
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailActivity.this, CommentActivity.class));
            }
        });

        ImageButton back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageButton trolley = (ImageButton) findViewById(R.id.trolley);
        trolley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog ad = new AlertDialog.Builder(DetailActivity.this).create();
                View view = LayoutInflater.from(DetailActivity.this).inflate(R.layout.dialog_trolley,null);
                ad.setView(view);
                ad.show();
            }
        });


        TextView add = (TextView) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailActivity.this,"点击添加，购物车图标发生变化",Toast.LENGTH_SHORT).show();
            }
        });


        TextView purchase = (TextView) findViewById(R.id.purchase);
        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog ad = new AlertDialog.Builder(DetailActivity.this).create();
                View view = LayoutInflater.from(DetailActivity.this).inflate(R.layout.dialog_purchase,null);
                ad.setView(view);
                ad.show();
            }
        });



    }


}
