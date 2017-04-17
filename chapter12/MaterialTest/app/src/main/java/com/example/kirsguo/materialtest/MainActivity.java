package com.example.kirsguo.materialtest;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout ;

    private Fruit[] fruits ={
            new Fruit("Apple",R.drawable.apple),
            new Fruit("Banana",R.drawable.banana),
            new Fruit("Orange",R.drawable.orange),
            new Fruit("Watermelon",R.drawable.watermelon),
            new Fruit("Pear",R.drawable.pear),
            new Fruit("Grape",R.drawable.grape),
            new Fruit("Pineapple",R.drawable.pineapple),
            new Fruit("Strawberry",R.drawable.strawberry),
            new Fruit("Cherry",R.drawable.cherry),
            new Fruit("Mango",R.drawable.mango),
    };
    private List<Fruit> fruitList = new ArrayList<>();
    private FruitAdapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        初始化数据
        initFruits();
//        设置recycle内布局和adapter
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager =new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);

//        下拉刷新
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
//            导航按钮显示和设置图标
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);

//         NavigationView 滑动菜单
        navigationView.setCheckedItem(R.id.nav_call);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                drawerLayout.closeDrawers();
                return true;
            }
        });
//        FloatingActionButton 悬浮按钮
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,"Fab Clicked",Toast.LENGTH_SHORT).show();
//                交互式的提示 Snackbar
                Snackbar.make(v,"Data deleted",Snackbar.LENGTH_SHORT).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,"Data restored",Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });
    }
//  toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.backup:
                Toast.makeText(this,"You clicked Backup",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this,"You clicked delete",Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this,"You clicked settings",Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }
//    下拉刷新
    private void refreshFruits() {
        new  Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruits();
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void initFruits(){
        fruitList.clear();
        for (int i = 0; i < 50; i++){
            Random random = new Random();
            int index =random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }
}
