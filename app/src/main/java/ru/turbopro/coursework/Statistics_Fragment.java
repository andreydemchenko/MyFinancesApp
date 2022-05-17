package ru.turbopro.coursework;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Statistics_Fragment extends Fragment {

    public Statistics_Fragment() {
    }

    View view;
    private List<Card_Statistics> card_exp;
    private List<Card_Statistics> card_inc;
    private RecyclerView rvExp;
    private RecyclerView rvInc;
    private RVAdapter mAdapterExp;
    private RVAdapterInc mAdapterInc;
    DbHelper dbHelper;
    static String[] recordPriceExp;
    static String[] recordCatExp;
    static String[] recordPriceInc;
    static String[] recordCatInc;
    TextView tvCardNameExp;
    TextView tvInc;
    TextView tvExp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_statistics, container, false);
        getActivity().setTitle("Список");

        dbHelper = new DbHelper(getActivity());

        rvExp = (RecyclerView)view.findViewById(R.id.recyclerViewExpenses);
        rvInc = (RecyclerView)view.findViewById(R.id.recyclerViewIncome);
        tvCardNameExp = (TextView)view.findViewById(R.id.namecard);
        tvInc = (TextView)view.findViewById(R.id.tvIncList);
        tvExp = (TextView)view.findViewById(R.id.tvExpList);

        tvInc.setText(String.valueOf(dbHelper.getSummIncome()));
        tvExp.setText(String.valueOf(dbHelper.getSummExpenses()));

        LinearLayoutManager llmExp = new LinearLayoutManager(getActivity());
        rvExp.setLayoutManager(llmExp);

        LinearLayoutManager llmInc = new LinearLayoutManager(getActivity());
        rvInc.setLayoutManager(llmInc);

        RecyclerView.ItemAnimator itemAnimatorExp = new DefaultItemAnimator();
        rvExp.setItemAnimator(itemAnimatorExp);

        RecyclerView.ItemAnimator itemAnimatorInc = new DefaultItemAnimator();
        rvInc.setItemAnimator(itemAnimatorInc);

        runAnimation(rvInc, 1);
        runAnimation(rvExp, 2);

        initializeDataExp();
        initializeDataInc();

        rvExp.setHasFixedSize(true);
        rvInc.setHasFixedSize(true);

        mAdapterExp = new RVAdapter(card_exp);
        rvExp.setAdapter(mAdapterExp);

        mAdapterExp.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
               changeItemExp(position);
            }
        });

        mAdapterInc = new RVAdapterInc(card_inc);
        rvInc.setAdapter(mAdapterInc);

        mAdapterInc.setOnItemClickListener(new RVAdapterInc.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                changeItemInc(position);
            }
        });

        return view;
    }

    private void runAnimation(RecyclerView recyclerView, int type){
        Context context = recyclerView.getContext();
        LayoutAnimationController controller = null;

        if (type == 1)
            controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_slide_cardview_inc);
        else if (type == 2)
            controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_slide_cardview_exp);

        mAdapterInc = new RVAdapterInc(card_inc);
        rvInc.setAdapter(mAdapterInc);

        mAdapterExp = new RVAdapter(card_exp);
        rvExp.setAdapter(mAdapterExp);


        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public void changeItemExp(int position){
        System.out.println("------------------------------------"+position+"--------------------------------");
        card_exp.get(position);
        mAdapterExp.notifyItemChanged(position);
        if (position==0){
            //Intent intent = new Intent(getActivity(), CommonChart.class);
            //startActivity(intent);
        }
    }

    public void changeItemInc(int position){
        System.out.println("------------------------------------"+position+"--------------------------------");
        card_inc.get(position);
        mAdapterInc.notifyItemChanged(position);
        if (position==0){
            //Intent intent = new Intent(getActivity(), CommonChart.class);
            //startActivity(intent);
        }
    }

    private void initializeDataExp() {
        card_exp = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                dbHelper.ID,
                dbHelper.PRICE,
                dbHelper.CAT_NAME};

        String selection = dbHelper.EXORIN+ "=?";
        String[] selectionArgs = {"expenses"};


        Cursor cursor = db.query(
                dbHelper.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);


        recordPriceExp = new String[cursor.getCount()];
        recordCatExp = new String[cursor.getCount()];
        try {
            System.out.println("Таблица содержит " + cursor.getCount() + " операций доходов.\n\n");


            int idColumnIndex = cursor.getColumnIndex(dbHelper.ID);
            int priceColumnIndex = cursor.getColumnIndex(dbHelper.PRICE);
            int catColumnIndex = cursor.getColumnIndex(dbHelper.CAT_NAME);


            while (cursor.moveToNext()) {

                int currentID = cursor.getInt(idColumnIndex);
                String currentPrice = cursor.getString(priceColumnIndex);
                String currentCat = cursor.getString(catColumnIndex);

                System.out.println(("\n" + currentID + " - " +
                        currentPrice + " - " +
                        currentCat + " - "));
            }

            cursor.moveToPosition(priceColumnIndex-1);
            for (int i = 0; i < cursor.getCount(); i++) {
                recordPriceExp[i] = cursor.getString(priceColumnIndex);
                recordCatExp[i] = cursor.getString(catColumnIndex);
//                 else tvCardNameExp.setPadding(35,0,0,0);
                card_exp.add(new Card_Statistics(recordCatExp[i],recordPriceExp[i]));
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
            db.close();
        }
    }

    private void initializeDataInc() {
        card_inc = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                dbHelper.ID,
                dbHelper.PRICE,
                dbHelper.CAT_NAME};

        String selection = dbHelper.EXORIN+ "=?";
        String[] selectionArgs = {"income"};


        Cursor cursor = db.query(
                dbHelper.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);


        recordPriceInc = new String[cursor.getCount()];
        recordCatInc = new String[cursor.getCount()];
        try {
            System.out.println("Таблица содержит " + cursor.getCount() + " операций доходов.\n\n");


            int idColumnIndex = cursor.getColumnIndex(dbHelper.ID);
            int priceColumnIndex = cursor.getColumnIndex(dbHelper.PRICE);
            int catColumnIndex = cursor.getColumnIndex(dbHelper.CAT_NAME);


            while (cursor.moveToNext()) {

                int currentID = cursor.getInt(idColumnIndex);
                String currentPrice = cursor.getString(priceColumnIndex);
                String currentCat = cursor.getString(catColumnIndex);

                System.out.println(("\n" + currentID + " - " +
                        currentPrice + " - " +
                        currentCat + " - "));
            }

            cursor.moveToPosition(priceColumnIndex-1);
            for (int i = 0; i < cursor.getCount(); i++) {
                recordPriceInc[i] = cursor.getString(priceColumnIndex);
                recordCatInc[i] = cursor.getString(catColumnIndex);
                card_inc.add(new Card_Statistics(recordCatInc[i],recordPriceInc[i]));
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
            db.close();
        }
    }
}
