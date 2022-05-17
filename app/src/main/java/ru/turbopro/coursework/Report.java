package ru.turbopro.coursework;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;

public class Report extends Fragment {

    DbHelper dbHelper;
    Friend friend;
    Home_Fragment home_fragment;
    MainActivity mainActivity;
    private TextView tvSumExp;
    private TextView tvSumInc;
    private TextView tvZ;
    private TextView tvSumExp2;
    private TextView tvSumInc2;
    private TextView tvCountExp;
    private TextView tvCountInc;
    private View view;
    private TextView tvCountAll;
    private TextView tvPrintExp;
    private TextView tvPrintInc;
    static int count;
    static Integer[] recordPriceExp;
    static String[] recordCatExp;
    static Integer[] recordPriceInc;
    static String[] recordCatInc;

    public Report() {
    }


    private static String TAG = "Report";

    private int  pexp;
    private int pinc;
    private int exp;
    private int inc;
    private int sumAllExp;
    private int sumAllInc;

    private int sfood;
    private int sclothes;
    private int sacess;
    private int selectr;
    private int sentainment;
    private int sbeandheal;
    private int shouse;
    private int stransport;
    private int snotselect;

    private int pfood;
    private int pclothes;
    private int pacess;
    private int pelectr;
    private int pentainment;
    private int pbeandheal;
    private int phouse;
    private int ptransport;
    private int pnotselect;

    private int ssalary;
    private int sschoolarship;
    private int sprize;
    private int sunderworking;

    private int psalary;
    private int pschoolarship;
    private int pprize;
    private int punderworking;

    PieChart pieChart;
    PieChart pieChartExp;
    PieChart pieChartInc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DbHelper(getActivity());
        friend = new Friend();
        home_fragment = new Home_Fragment();
        mainActivity = new MainActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_report, container, false);
        getActivity().setTitle("Отчеты");

        pieChart = (PieChart) view.findViewById(R.id.PieChart);
        pieChartExp = (PieChart) view.findViewById(R.id.PieChartExpenses);
        pieChartInc = (PieChart) view.findViewById(R.id.PieChartIncome);
        tvCountAll = (TextView) view.findViewById(R.id.tvCountAll);
        tvPrintExp = (TextView) view.findViewById(R.id.tvPrintExp);
        tvPrintInc = (TextView) view.findViewById(R.id.tvPrintInc);
        tvCountExp = (TextView) view.findViewById(R.id.tvCountExp);
        tvCountInc = (TextView) view.findViewById(R.id.tvCountInc);


        Description description = null;
        pieChart.setDescription(description);
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(50f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Соотношение");
        pieChart.setCenterTextSize(12);
        pieChart.animateY(1000, Easing.EasingOption.EaseOutCubic);
        pieChart.setDrawEntryLabels(false);

        addDataSet();



        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d(TAG, "onValueSelected: Value select from chart");
                Log.d(TAG, "onValueSelected: " + e.toString());
                Log.d(TAG, "onValueSelected: " + h.toString());
            }

            @Override
            public void onNothingSelected() {

            }
        });


        pieChartExp.setRotationEnabled(true);
        pieChartExp.setHoleRadius(50f);
        pieChartExp.setDescription(description);
        pieChartExp.setTransparentCircleAlpha(0);
//        pieChartExp.setCenterText("Соотношение");
//        pieChartExp.setCenterTextSize(12);
        pieChartExp.animateY(1000, Easing.EasingOption.EaseOutCubic);
        pieChartExp.setDrawEntryLabels(false);

        addDataSetExp();

        pieChartInc.setRotationEnabled(true);
        pieChartInc.setHoleRadius(50f);
        pieChartInc.setDescription(description);
        pieChartInc.setTransparentCircleAlpha(0);
        pieChartInc.animateY(1000, Easing.EasingOption.EaseOutCubic);
        pieChartInc.setDrawEntryLabels(false);

        addDataSetInc();

        tvSumExp = (TextView) view.findViewById(R.id.tvSumExpensesReport);
        tvSumInc = (TextView) view.findViewById(R.id.tvSumIncomeReport);
        tvZ = (TextView) view.findViewById(R.id.tvZ);
        tvSumExp2 = (TextView)view.findViewById(R.id.tvSumExp);
        tvSumInc2 = (TextView)view.findViewById(R.id.tvSumInc);

        tvSumExp2.setText(Long.toString(dbHelper.getSummExpenses()) + " руб.");
        tvSumExp.setText(Long.toString(dbHelper.getSummExpenses()) + " руб.");
        tvSumInc.setText("+" + Long.toString(dbHelper.getSummIncome()) + " руб.");
        tvSumInc2.setText("+" + Long.toString(dbHelper.getSummIncome()) + " руб.");

        if (dbHelper.getSummIncome() + dbHelper.getSummExpenses() == 0)
            tvZ.setText(Long.toString(dbHelper.getSummIncome() + dbHelper.getSummExpenses()) + " руб.");
        else if (dbHelper.getSummIncome() + dbHelper.getSummExpenses() > 0)
            tvZ.setText("+" + Long.toString(dbHelper.getSummIncome() + dbHelper.getSummExpenses()) + " руб.");
        else
            tvZ.setText(Long.toString(dbHelper.getSummIncome() + dbHelper.getSummExpenses()) + " руб.");

        setTvCountAll();

        return view;
    }

    public void getCountAll(int count) {
        this.count = count;
        System.out.println("-----Report---------  count = " + count + "  ----------  this.count = " + this.count);
    }

    private void setTvCountAll() {
        System.out.println("-----Report---------  count = " + count);
        if (count == 0) tvCountAll.setText("Нет операций");
        else if (count != 0) tvCountAll.setText("Всего операций " + count);
    }

    private void addDataSet() {

        exp =  -(int)dbHelper.getSummExpenses();
        inc = (int)dbHelper.getSummIncome();
        if((exp+inc)!=0) {
            pexp = (exp * 100) / (exp + inc);
            pinc = (inc * 100) / (exp + inc);
        }
        System.out.println("---------------- pexp: "+pexp+"%   pink: "+pinc+"% ----------------------");

        String[] xData = {"- расходы","- доходы"};
        int[] yData = {pexp, pinc};

        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for (int i=0;i<yData.length;i++){
            yEntrys.add(new PieEntry(yData[i], xData[i]));
        }

        for (int i=1;i<xData.length;i++){
            xEntrys.add(xData[i]);
        }

        PieDataSet pieDataSet = new PieDataSet(yEntrys," ");
        pieDataSet.setSliceSpace(4);
        pieDataSet.setValueTextSize(12);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(getResources().getColor(R.color.green));

        pieDataSet.setColors(colors);

        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);

        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextColor(Color.WHITE);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    private void addDataSetExp(){

        expensesDatabaseInfoExpenses();

        for(int i = 0; i < recordPriceExp.length; i++){
            System.out.println(recordPriceExp[i]);
        }

        ArrayList<Integer> arrayList = new ArrayList<Integer>(Arrays.<Integer>asList(recordPriceExp));
        System.out.println(arrayList);

        for (Integer name : recordPriceExp) {

            String rec = name.toString().replaceAll("^-", "+");
            if (rec.charAt(0) == '+') {
                arrayList.add (Integer.parseInt(rec));
                arrayList.remove(0);
            }
            else System.out.println("Массив "+arrayList+" не преобразован");
        }
        System.out.println(arrayList);

        Integer[] intArr = new Integer[arrayList.size()];
        arrayList.toArray(intArr);
        for (Integer c : intArr)
            System.out.print(c+"  ");

        System.out.println();


        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        ArrayList<Integer> colors = new ArrayList<>();

        sfood = -(int)dbHelper.getSummExpFood();
        sclothes = -(int)dbHelper.getSummExpClothes();
        sacess = -(int)dbHelper.getSummExpAcces();
        selectr = -(int)dbHelper.getSummExpElectr();
        sentainment = -(int)dbHelper.getSummEntainment();
        sbeandheal = -(int)dbHelper.getSummExpBeAndHeal();
        shouse = -(int)dbHelper.getSummExpHome();
        stransport = -(int)dbHelper.getSummExpTransport();
        snotselect = -(int)dbHelper.getSummExpNotselect();

        System.out.println("___  sfood = "+sfood+"  sclothes = "+sclothes+"  sacess = "+sacess+"  selectr = "+selectr+"  sentainment = "+
                sentainment+"  sbeandheal = "+sbeandheal+"  shouse = "+shouse+"  stransport = "+stransport+"  snotselect = "+snotselect);

        sumAllExp = sfood + sclothes + sacess + selectr + sentainment + sbeandheal + shouse + stransport + snotselect;

        if(sumAllExp != 0){
            pfood = (sfood * 100) / sumAllExp;
            pclothes = (sclothes * 100) / sumAllExp;
            pacess = (sacess * 100) / sumAllExp;
            pelectr = (selectr * 100) / sumAllExp;
            pentainment = (sentainment * 100) / sumAllExp;
            pbeandheal = (sbeandheal * 100) / sumAllExp;
            phouse = (shouse * 100) / sumAllExp;
            ptransport = (stransport * 100) / sumAllExp;
            pnotselect = (snotselect * 100) / sumAllExp;
        }
        Integer[]intArray = {pfood, pclothes, pacess, pelectr, pentainment, pbeandheal, phouse, ptransport, pnotselect};
        String[] strArr = {"Питание","Одежда","Аксессуары","Электроника","Развлечения","Красота и здоровье","Дом","Транспорт","Без категории"};


       ArrayList<Integer> intArrays = new ArrayList<Integer>(Arrays.<Integer>asList(intArray));
        System.out.println(intArrays);


        for (int i = 0; i < intArrays.size(); ) {
            if(intArrays.get(i).equals(0)) {
                intArrays.remove(i);
                continue;
            }
            i++;
        }System.out.println(intArrays);

        Integer[] intArr2 = new Integer[intArrays.size()];
        intArrays.toArray(intArr2);
        for (Integer c : intArr2)
            System.out.print(c+"  ");

        System.out.println();
        for(int i = 0;i<recordCatExp.length;i++){
           System.out.println(recordCatExp[i]);
        }


        for (int i=0;i<intArr2.length;i++){

            colors.add(getResources().getColor(R.color.actionButton));
            colors.add(getResources().getColor(R.color.rowCur_bg));
            colors.add(getResources().getColor(R.color.blue));
            colors.add(getResources().getColor(R.color.bluecateg));
            colors.add(getResources().getColor(R.color.colorPrimary));
            colors.add(getResources().getColor(R.color.colorPrimaryFragment));
            colors.add(Color.RED);
            colors.add(Color.GRAY);
            colors.add(Color.CYAN);

            yEntrys.add(new PieEntry(intArr2[i], recordCatExp[i]));
        }


        PieDataSet pieDataSet = new PieDataSet(yEntrys," ");
        pieDataSet.setSliceSpace(4);
        pieDataSet.setValueTextSize(12);
        pieDataSet.setColors(colors);

        Legend legend = pieChartExp.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART_CENTER);


        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextColor(Color.WHITE);
        pieChartExp.setData(pieData);
        pieChartExp.invalidate();
    }


    private void expensesDatabaseInfoExpenses() {

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


        recordPriceExp = new Integer[cursor.getCount()];
        recordCatExp = new String[cursor.getCount()];
        try {
            System.out.println("Таблица содержит " + cursor.getCount() + " операций расходов.\n\n");

            tvCountExp.setText("Количество операций "+cursor.getCount());

            int idColumnIndex = cursor.getColumnIndex(dbHelper.ID);
            int priceColumnIndex = cursor.getColumnIndex(dbHelper.PRICE);
            int catColumnIndex = cursor.getColumnIndex(dbHelper.CAT_NAME);


            while (cursor.moveToNext()) {

                int currentID = cursor.getInt(idColumnIndex);
                Integer currentPrice = cursor.getInt(priceColumnIndex);
                String currentCat = cursor.getString(catColumnIndex);

                System.out.println(("\n" + currentID + " - " +
                        currentPrice + " - " +
                        currentCat + " - "));
            }

            cursor.moveToPosition(priceColumnIndex-1);
            for (int i = 0; i < cursor.getCount(); i++) {
                recordPriceExp[i] = cursor.getInt(priceColumnIndex);
                recordCatExp[i] = cursor.getString(catColumnIndex);
                System.out.println("---- "+recordPriceExp[i]+" ---- "+recordCatExp[i]+" ----");
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
            db.close();
        }
    }

    private void addDataSetInc(){

        incomeDatabaseInfoIncome();

        for(int i = 0; i < recordPriceInc.length; i++){
            System.out.println(recordPriceInc[i]);
        }


        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        ArrayList<Integer> colors = new ArrayList<>();

        ssalary = (int)dbHelper.getSummIncSalary();
        sschoolarship = (int)dbHelper.getSummIncSchoolarship();
        sprize = (int)dbHelper.getSummIncPrize();
        sunderworking = (int)dbHelper.getSummIncUnderworking();
        snotselect = (int)dbHelper.getSummIncNotselect();

        System.out.println("___  ssalary = "+ssalary+"  sshoolarship = "+sschoolarship+"  sprize = "+sprize+"  sunderworking = "+sunderworking+"  snotselect = "+snotselect);

        sumAllInc = ssalary+sschoolarship+sprize+sunderworking+snotselect;

        if(sumAllInc != 0){
            psalary = (ssalary * 100) / sumAllInc;
            pschoolarship = (sschoolarship * 100) / sumAllInc;
            pprize = (sprize * 100) / sumAllInc;
            punderworking = (sunderworking * 100) / sumAllInc;
            pnotselect = (snotselect * 100) / sumAllInc;
        }
        Integer[]intArray = {psalary, pschoolarship, pprize, punderworking, pnotselect};
        String[] strArr = {"Зарплата","Стипендия","Премия","Подработка","Без категории"};

        ArrayList<Integer> intArrays = new ArrayList<Integer>(Arrays.<Integer>asList(intArray));
        System.out.println(intArrays);

        for (int i = 0; i < intArrays.size(); ) {
            if(intArrays.get(i).equals(0)) {
                intArrays.remove(i);
                continue;
            }
            i++;
        }System.out.println(intArrays);

        Integer[] intArr2 = new Integer[intArrays.size()];
        intArrays.toArray(intArr2);
        for (Integer c : intArr2)
            System.out.print(c+"  ");

        System.out.println();
        for(int i = 0;i<recordCatInc.length;i++){
            System.out.println(recordCatInc[i]);
        }

        for (int i=0;i<intArr2.length;i++){
            colors.add(Color.RED);
            colors.add(Color.GRAY);
            colors.add(getResources().getColor(R.color.actionButton));
            colors.add(getResources().getColor(R.color.colorPrimary));
            colors.add(getResources().getColor(R.color.rowCur_bg));
            colors.add(getResources().getColor(R.color.bluecateg));
            colors.add(getResources().getColor(R.color.blue));
            colors.add(getResources().getColor(R.color.colorPrimaryFragment));
            yEntrys.add(new PieEntry(intArr2[i], recordCatInc[i]));
        }


        PieDataSet pieDataSet = new PieDataSet(yEntrys," ");
        pieDataSet.setSliceSpace(4);
        pieDataSet.setValueTextSize(12);
        pieDataSet.setColors(colors);

        Legend legend = pieChartInc.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART_CENTER);


        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextColor(Color.WHITE);
        pieChartInc.setData(pieData);
        pieChartInc.invalidate();
    }

    private void incomeDatabaseInfoIncome() {

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


        recordPriceInc = new Integer[cursor.getCount()];
        recordCatInc = new String[cursor.getCount()];
        try {
            System.out.println("Таблица содержит " + cursor.getCount() + " операций доходов.\n\n");

            tvCountInc.setText("Количество операций "+cursor.getCount());

            int idColumnIndex = cursor.getColumnIndex(dbHelper.ID);
            int priceColumnIndex = cursor.getColumnIndex(dbHelper.PRICE);
            int catColumnIndex = cursor.getColumnIndex(dbHelper.CAT_NAME);


            while (cursor.moveToNext()) {

                int currentID = cursor.getInt(idColumnIndex);
                Integer currentPrice = cursor.getInt(priceColumnIndex);
                String currentCat = cursor.getString(catColumnIndex);

                System.out.println(("\n" + currentID + " - " +
                        currentPrice + " - " +
                        currentCat + " - "));
            }

            cursor.moveToPosition(priceColumnIndex-1);
            for (int i = 0; i < cursor.getCount(); i++) {
                recordPriceInc[i] = cursor.getInt(priceColumnIndex);
                recordCatInc[i] = cursor.getString(catColumnIndex);
                System.out.println("---- "+recordPriceInc [i]+" ---- "+recordCatInc[i]+" ----");
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
            db.close();
        }
    }
}
