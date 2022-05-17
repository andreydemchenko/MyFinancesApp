package ru.turbopro.coursework;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


public class Fragment_main extends AppCompatActivity {

    static final String RES = "result";

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String exorin;
    private int res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_main);

        setTitle("Добавить...");

        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);

        FragmentIncome fragmentIncome = new FragmentIncome();
        FragmentExpenses fragmentExpenses = new FragmentExpenses();

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(fragmentExpenses, "Расход");
        adapter.AddFragment(fragmentIncome, "Доход");


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            exorin = extras.getString(Calc.SENDEXORIN);
            res = extras.getInt(Calc.SENDRESULT);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();


        System.out.println("---------------------------------"+exorin+"-----------------------------"+res+"----------------------");

        if (exorin != null && exorin.equals("income")) {
            System.out.println("exorin.equals(\"income\")");
            fragmentIncome.setTextInc(String.valueOf(res));
        } else
            if (exorin != null && exorin.equals("expenses")) {
            System.out.println("exorin.equals(\"expenses\")");
            fragmentExpenses.setTextExp(String.valueOf(res));
        }


        System.out.println("-----------Fragment_main-----------");
    }
}
