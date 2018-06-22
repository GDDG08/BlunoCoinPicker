package com.zzh.bamboobook.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.*;
import com.stone.card.data.db.*;
import com.stone.card.library.CardAdapter;
import com.stone.card.library.CardSlidePanel;
import com.stone.card.library.R;
import com.zzh.bamboobook.ZZHApplication;
import com.zzh.bamboobook.data.db.User;
import com.zzh.bamboobook.tool.CardDataItem;

import org.xutils.DbManager;
import org.xutils.common.util.KeyValue;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReviewActivity extends FragmentActivity {

    private CardSlidePanel.CardSwitchListener cardSwitchListener;

    private String imagePaths[] = {"file:///android_asset/wall01.jpg",
            "file:///android_asset/wall02.jpg", "file:///android_asset/wall03.jpg",
            "file:///android_asset/wall04.jpg", "file:///android_asset/wall05.jpg",
            "file:///android_asset/wall06.jpg", "file:///android_asset/wall07.jpg",
            "file:///android_asset/wall08.jpg", "file:///android_asset/wall09.jpg",
            "file:///android_asset/wall10.jpg", "file:///android_asset/wall11.jpg",
            "file:///android_asset/wall12.jpg"}; // 12个图片资源

    private String names[] = {"郭富城", "刘德华", "张学友", "李连杰", "成龙", "谢霆锋", "李易峰",
            "霍建华", "胡歌", "曾志伟", "吴孟达", "梁朝伟"}; // 12个人名

    private List<CardDataItem> dataList = new ArrayList<>();

	private TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_review);
        initView();
    }

    private void initView() {
        final CardSlidePanel slidePanel = (CardSlidePanel) findViewById(R.id.image_slide_panel);
		text = (TextView) findViewById(R.id.onshow);

        // 1. 左右滑动监听
        cardSwitchListener = new CardSlidePanel.CardSwitchListener() {

            @Override
            public void onShow(int index) {
                Log.d("Card", "正在显示-" + dataList.get(index).userName);
            }

            @Override
            public void onCardVanish(int index, int type) {
                Log.d("Card", "正在消失-" + dataList.get(index).userName + " 消失type=" + type);
				
					text.setText("类型："+type);
				
            }
        };
        slidePanel.setCardSwitchListener(cardSwitchListener);


        // 2. 绑定Adapter
        slidePanel.setAdapter(new CardAdapter() {
            @Override
            public int getLayoutId() {
                return R.layout.card_item;
            }

            @Override
            public int getCount() {
                return dataList.size();
            }

            @Override
            public void bindView(View view, int index) {
                Object tag = view.getTag();
                ViewHolder viewHolder;
                if (null != tag) {
                    viewHolder = (ViewHolder) tag;
                } else {
                    viewHolder = new ViewHolder(view);
                    view.setTag(viewHolder);
                }

                viewHolder.bindData(dataList.get(index));
            }

            @Override
            public Object getItem(int index) {
                return dataList.get(index);
            }

            @Override
            public Rect obtainDraggableArea(View view) {
                // 可滑动区域定制，该函数只会调用一次
                View contentView = view.findViewById(R.id.card_item_content);
                View topLayout = view.findViewById(R.id.card_top_layout);
                View bottomLayout = view.findViewById(R.id.card_bottom_layout);
                int left = view.getLeft() + contentView.getPaddingLeft() + topLayout.getPaddingLeft();
                int right = view.getRight() - contentView.getPaddingRight() - topLayout.getPaddingRight();
                int top = view.getTop() + contentView.getPaddingTop() + topLayout.getPaddingTop();
                int bottom = view.getBottom() - contentView.getPaddingBottom() - bottomLayout.getPaddingBottom();
                return new Rect(left, top, right, bottom);
            }
        });


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                prepareDataList();
                slidePanel.getAdapter().notifyDataSetChanged();
            }
        }, 500);

        // 3. notifyDataSetChanged调用
        findViewById(R.id.notify_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendDataList();
                slidePanel.getAdapter().notifyDataSetChanged();
            }
        });
    }

    private void prepareDataList() {
        for (int i = 0; i < 6; i++) {
            CardDataItem dataItem = new CardDataItem();
            dataItem.userName = names[i];
            dataItem.imagePath = imagePaths[i];
            dataItem.likeNum = (int) (Math.random() * 10);
            dataItem.imageNum = (int) (Math.random() * 6);
            dataList.add(dataItem);
        }
    }

    private void appendDataList() {
        for (int i = 0; i < 6; i++) {
            CardDataItem dataItem = new CardDataItem();
            dataItem.userName = "From Append";
            dataItem.imagePath = imagePaths[8];
            dataItem.likeNum = (int) (Math.random() * 10);
            dataItem.imageNum = (int) (Math.random() * 6);
            dataList.add(dataItem);
        }
    }

    class ViewHolder {

        ImageView imageView;
        View maskView;
        TextView userNameTv;
        TextView imageNumTv;
        TextView likeNumTv;

        public ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.card_image_view);
            maskView = view.findViewById(R.id.maskView);
            userNameTv = (TextView) view.findViewById(R.id.card_user_name);
            imageNumTv = (TextView) view.findViewById(R.id.card_pic_num);
            likeNumTv = (TextView) view.findViewById(R.id.card_like);
        }

        public void bindData(CardDataItem itemData) {
            Glide.with(this).load(itemData.imagePath).into(imageView);
            userNameTv.setText(itemData.userName);
            imageNumTv.setText(itemData.imageNum + "");
            likeNumTv.setText(itemData.likeNum + "");
        }
    }
	
	
	/**
     * 实现数据库的存储
     */
    public void save(View view) {
        if (checkPermission()) {
            //获取数据库管理器
            DbManager manager = ZZHApplication.getInstance().getDbManager();
            //数据
            User user = new User();
            // user.setId(1);//自增长的id设置了也是没有用的
            user.setALLID("test");
            user.setSID(666);
            user.setAddTime(new Date().getTime());
            user.setTITLE("测试标题");
            user.setText("测试文本");
            //user.setPicnum();
            //user.setKeypoint();
            //user.setStar();
            //user.setReviewTime();
            user.setReviewPre(100);
            try {
                //实现数据的存储,配合User类中的注释才能进行对应的存储
                //表名和列名都是在User中注释决定的。
                manager.save(user);//保存
                // manager.saveOrUpdate(user);//保存或更新，这如果数据不存在是不会保存的，存在的话会跟新
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除数据
     * //删除整个表的所有数据
     * //manager.delete(User.class);
     * //删除指定id的记录
     * //manager.deleteById(User.class,1);
     * //删除某一类数据  where name="张三"
     * manager.delete(User.class, WhereBuilder.b("username", "=", "王五").and("age", "<", "100"));
     */
    public void delete(View view) {
        if (checkPermission()) {
            DbManager manager = ZZHApplication.getInstance().getDbManager();
            try {
                manager.delete(User.class, WhereBuilder.b("AddTime", "<", "1528910496690"));
            } catch (DbException e) {
                e.printStackTrace();

            }
        }
    }

    /**
     * 修改数据
     */
    public void update(View view) {
        if (checkPermission()) {
            DbManager manager = ZZHApplication.getInstance().getDbManager();
            //要修改的数据，以键值对的显示传入,
            KeyValue keyValue = new KeyValue("ReviewTime", "6");
            try {
                //过滤年龄小于20的数据就修改,这里可以设置多个keyValue值
                manager.update(User.class, WhereBuilder.b("AddTime", "<=", "1528910496691"), keyValue);
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 查找数据
     */
    public void query(View view) {
        if(checkPermission()) {
            //查询所有
            //获取管理器
            DbManager manager = ZZHApplication.getInstance().getDbManager();
            try {
                List<User> all = manager.findAll(User.class);
                //manager.findById()找单个的对象
                for (int i = 0; i < all.size(); i++) {
                    Log.e("TAG", all.get(i).toString());//打印显示
                    Toast.makeText(this, all.get(i).toString(), Toast.LENGTH_LONG).show();
                }

            } catch (DbException e) {
                e.printStackTrace();
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean checkPermission(){
       /* if(Build.VERSION.SDK_INT>=23){
            //判断是否有这个权限
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                //第一请求权限被取消显示的判断，一般可以不写
                if (ActivityCompat.shouldShowRequestPermissionRationale(SdkActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    //Log.i("readTosdCard","我们需要这个权限给你提供存储服务");
                    showAlert();
                }else {
                    //2、申请权限: 参数二：权限的数组；参数三：请求码
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_WRITE);
                }
            }else {
                return true;
            }
        } else{
            return  true;
        }
        return false;*/
       return true;
    }


    private void showAlert(){
        Dialog alertDialog = new AlertDialog.Builder(this).
                setTitle("权限说明").
                setMessage("我们需要这个权限给你提供存储服务").
                setIcon(R.mipmap.ic_launcher).
                setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        //2、申请权限: 参数二：权限的数组；参数三：请求码
                        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                    }
                }).
                setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                }).
                create();
        alertDialog.show();
    }

}
