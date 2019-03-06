package com.mishra.sadikshya.jobfinder.Activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.mishra.sadikshya.jobfinder.Adapter.ViewPagerAdapterTable;
import com.mishra.sadikshya.jobfinder.Fragment.GitHub;
import com.mishra.sadikshya.jobfinder.Fragment.Gov;
import com.mishra.sadikshya.jobfinder.R;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapterTable viewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPagerAdapter = new ViewPagerAdapterTable(getSupportFragmentManager());
        viewPager = findViewById(R.id.containerTab);
        setupViewPager(viewPager);
        TabLayout tabLayout =findViewById(R.id.tableTab);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapterTable adapter = new ViewPagerAdapterTable(getSupportFragmentManager());
        adapter.addFragment(new GitHub(),"Git Jobs");
        adapter.addFragment(new Gov(),"Government Jobs");



        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
