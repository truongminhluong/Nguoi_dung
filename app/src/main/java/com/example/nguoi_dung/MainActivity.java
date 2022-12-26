package com.example.nguoi_dung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.nguoi_dung.fragment.GioHangFragment;
import com.example.nguoi_dung.fragment.LaptopFragment;
import com.example.nguoi_dung.fragment.DangNhapFragment;
import com.example.nguoi_dung.molder.Laptop;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private LaptopFragment laptopFragment = new LaptopFragment();
    private GioHangFragment cardFragment = new GioHangFragment();
    private DangNhapFragment userFragment = new DangNhapFragment();

    public static BadgeDrawable badgeDrawable;
    public static int numInCart = 0;
    public static ArrayList<Laptop> spCart = new ArrayList<>();//Các sản phẩm thêm vào giở hàng
    public static ArrayList<Laptop> itemOrders = new ArrayList<>();//Các sản phẩm trong giỏ hàng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,laptopFragment).commit();

        badge();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_Laptop:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,laptopFragment).commit();
                        return true;
                    case R.id.action_Cart:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,cardFragment).commit();
                        return true;
                    case R.id.action_User:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,userFragment).commit();
                        return true;
                }
                return false;

            }
        });
    }
    //hiện badge trên phần cart
    private void badge(){
        badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.action_Cart);
        badgeDrawable.setVisible(true);
        badgeDrawable.setNumber(0);
    }
    //khi ấn thêm laptop vào cart thì badge tăng lên
    public static void updateBadge(){
        if(spCart.size() > 0){
            badgeDrawable.setVisible(true);
            badgeDrawable.setNumber(spCart.size());
        }else{
            badgeDrawable.setVisible(false);
        }
    }

    //tìm kiếm
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
//        if (searchItem != null) {
//            searchView = (SearchView) searchItem.getActionView();
//        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
        }
        return super.onCreateOptionsMenu(menu);
    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu, menu);
//
//        final View view = menu.findItem(R.id.action_Cart).getActionView();
//
//        txtViewCount = (TextView) view.findViewById(R.id.txtCount);
//        updateHotCount(count++);
//        txtViewCount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateHotCount(count++);
//            }
//        });
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //    TODO
//            }
//        });
//
//
//        return true;
//    }
//    public void updateHotCount(final int new_hot_number) {
//        count = new_hot_number;
//        if (count < 0) return;
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (count == 0)
//                    txtViewCount.setVisibility(View.GONE);
//                else {
//                    txtViewCount.setVisibility(View.VISIBLE);
//                    txtViewCount.setText(Integer.toString(count));
//                    // supportInvalidateOptionsMenu();
//                }
//            }
//        });
//    }

}