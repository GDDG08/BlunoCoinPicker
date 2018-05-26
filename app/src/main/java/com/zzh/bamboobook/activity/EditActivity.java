package com.zzh.bamboobook.activity;

/**
 * Created on 2018/5/26-09-16.
 */
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zzh.bamboobook.MainActivity;
import com.zzh.bamboobook.R;
import com.zzh.bamboobook.ZZHApplication;
import com.zzh.bamboobook.data.db.User;
import com.zzh.bamboobook.tool.Msg;

import org.xutils.DbManager;
import org.xutils.common.util.KeyValue;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import devlight.io.library.ntb.NavigationTabBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by GIGAMOLE on 28.03.2016.
 */
public class EditActivity extends Activity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_top_ntb);
        initUI();
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.editTexttestfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    private void initUI() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public boolean isViewFromObject(final View view, final Object object) {
                return view.equals(object);
            }

            @Override
            public void destroyItem(final View container, final int position, final Object object) {
                ((ViewPager) container).removeView((View) object);
            }

            @Override
            public Object instantiateItem(final ViewGroup container, final int position) {
                final View view = LayoutInflater.from(
                        getBaseContext()).inflate(R.layout.item_vp_list, null, false);

                final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(
                                getBaseContext(), LinearLayoutManager.VERTICAL, false
                        )
                );
                recyclerView.setAdapter(new RecycleAdapter());

                container.addView(view);
                return view;
            }
        });

        final String[] colors = getResources().getStringArray(R.array.default_preview);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_first),
                        Color.parseColor(colors[0]))
                        .title("Heart")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_second),
                        Color.parseColor(colors[1]))
                        .title("Cup")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_third),
                        Color.parseColor(colors[2]))
                        .title("Diploma")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_fourth),
                        Color.parseColor(colors[3]))
                        .title("Flag")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_fifth),
                        Color.parseColor(colors[4]))
                        .title("Medal")
                        .build()
        );
        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 2);

        navigationTabBar.post(new Runnable() {
            @Override
            public void run() {
                final View viewPager = findViewById(R.id.vp_horizontal_ntb);
                ((ViewGroup.MarginLayoutParams) viewPager.getLayoutParams()).topMargin =
                        (int) -navigationTabBar.getBadgeMargin();
                viewPager.requestLayout();
            }
        });

        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(final NavigationTabBar.Model model, final int index) {

            }

            @Override
            public void onEndTabSelected(final NavigationTabBar.Model model, final int index) {
                model.hideBadge();
            }
        });

        findViewById(R.id.mask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                    navigationTabBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            final String title = String.valueOf(new Random().nextInt(15));
                            if (!model.isBadgeShowed()) {
                                model.setBadgeTitle(title);
                                model.showBadge();
                            } else model.updateBadgeTitle(title);
                        }
                    }, i * 100);
                }
            }
        });
    }

    public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            final View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_list, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.txt.setText(String.format("Navigation Item #%d", position));
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView txt;

            public ViewHolder(final View itemView) {
                super(itemView);
                txt = (TextView) itemView.findViewById(R.id.txt_vp_item_list);
            }
        }
    }
    public void ontestbuttonclick(View v){
        Toast.makeText(EditActivity.this,"66666",Toast.LENGTH_LONG).show();
        //finish();
        Intent intent = new Intent();
        intent.setAction("android.media.action.IMAGE_CAPTURE");
        intent.addCategory("android.intent.category.DEFAULT");
        //保存照片到指定的路径
        File file = new File("/sdcard/image.jpg");
        Uri uri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);           startActivity(intent);
    }

    public void save(View view) {
        //获取数据库管理器
        DbManager manager = ZZHApplication.getInstance().getDbManager();
        //数据
        User user = new User();
        // user.setId(1);//自增长的id设置了也是没有用的
        user.setSex("女");
        user.setPassword("123456");
        user.setUsername("李文");
        user.setAge(18);
        try {
            //实现数据的存储,配合User类中的注释才能进行对应的存储
            //表名和列名都是在User中注释决定的。
            manager.save(user);//保存
            // manager.saveOrUpdate(user);//保存或更新，这如果数据不存在是不会保存的，存在的话会跟新
        } catch (DbException e) {
            e.printStackTrace();
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
        DbManager manager = ZZHApplication.getInstance().getDbManager();
        try {
            manager.delete(User.class, WhereBuilder.b("username", "=", "李文"));
        } catch (DbException e) {
            e.printStackTrace();

        }
    }

    /**
     * 修改数据
     */
    public void update(View view) {
        DbManager manager = ZZHApplication.getInstance().getDbManager();
        //要修改的数据，以键值对的显示传入,
        KeyValue keyValue = new KeyValue("username", "李世民");
        try {
            //过滤年龄小于20的数据就修改,这里可以设置多个keyValue值
            manager.update(User.class, WhereBuilder.b("age", "<", "20"), keyValue);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查找数据
     */
    public void query(View view) {
        //查询所有
        //获取管理器
        DbManager manager = ZZHApplication.getInstance().getDbManager();
        try {
            List<User> all = manager.findAll(User.class);
            //manager.findById()找单个的对象
            for (int i = 0; i < all.size(); i++) {
                Log.e("TAG", all.get(i).toString());//打印显示
            }

        } catch (DbException e) {
            e.printStackTrace();
        }
    }

}
