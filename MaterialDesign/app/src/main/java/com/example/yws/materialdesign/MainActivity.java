package com.example.yws.materialdesign;

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

    private DrawerLayout drawerLayout;
    private SwipeRefreshLayout swipeRefreshLayout;




    private Fruit[] fruits = {
            new Fruit("apple",R.drawable.apple),new Fruit("banana",R.drawable.banana),
            new Fruit("orange",R.drawable.orange),new Fruit("watermelon",R.drawable.watermelon),
            new Fruit("pear",R.drawable.pear),new Fruit("grape",R.drawable.grape),
            new Fruit("pneapple",R.drawable.pineapple),new Fruit("straeberry",R.drawable.strawberry),
            new Fruit("cherry",R.drawable.cherry),new Fruit("mango",R.drawable.mango)
    };

    private List<Fruit> fruitList = new ArrayList<>();
    private FruitAdapter fruitAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


           /*
    * 下拉刷新
    * */
           swipeRefreshLayout = findViewById(R.id.swipe_refresh);
           swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
           swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
               @Override
               public void onRefresh() {
                    refreshFruits();

               }
           });





        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //drawerLayout布局
        drawerLayout = findViewById(R.id.drawer_layout);

        //引入NavigationView布局
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        navigationView.setCheckedItem(R.id.nav_call);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();
                return true;
            }
        });

        //悬浮按钮点击
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //引入Scackbar
                Snackbar.make(view,"delete?",Snackbar.LENGTH_SHORT).setAction("Yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this,"delete data",Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });

        //cardview
        initFruits();
        RecyclerView recyclerView = findViewById(R.id.recycler_View);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        fruitAdapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(fruitAdapter);
    }

    private void initFruits() {
        fruitList.clear();
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    /*
    * 测试按钮效果
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.backup:
                Toast.makeText(this, "You clicked Backup", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "You clicked Delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this, "You clicked Settings", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

     /*
     * 下拉刷新
     * */
     private void refreshFruits(){
         new Thread(new Runnable() {
             @Override
             public void run() {
                 try {
                     Thread.sleep(2000);
                 }catch (InterruptedException e){
                     e.printStackTrace();
                 }
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         initFruits();
                         fruitAdapter.notifyDataSetChanged();
                         swipeRefreshLayout.setRefreshing(false);
                     }

                 });
             }
         }).start();
     }

}
