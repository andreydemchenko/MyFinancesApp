package ru.turbopro.coursework;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Home_Fragment extends Fragment {

    public Home_Fragment() {
    }

    View view;

    final static String SEND = "send";
    final static String SENDNAME = "name";
    final static String SENDPRICE = "price";
    final static String SENDDATE = "date";
    final static String SENDCAT = "cat";
    final static String SENDID = "id";
    final static String SENDEXORIN = "expenses_or_income";


    DbHelper dbHelper;
    SimpleCursorAdapter cursorListAdapter;
    long id_name;
    Cursor cursor;
    TextView tvwal;
    TextView tvSumItem;
    TextView tvRub;

    ListView listView;

    String strdate2;
    String strname2;
    Integer intprice2;
    String exorin;
    String categ="";
    long idcat2;
    long id2;
    long iddel;
    Friend fr;
    int count;

    AlertDialog.Builder ad;
    Context context;

    Button btnSave;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_home, container, false);
        getActivity().setTitle("Главная");

        tvwal = (TextView) view.findViewById(R.id.textWallet);
        TextView tvSum = (TextView) view.findViewById(R.id.textSumItem);
        btnSave = (Button) view.findViewById(R.id.btnSaveHome);
        tvSumItem = view.findViewById(R.id.textSumItem);
        tvRub = view.findViewById(R.id.textView5);


        fr = new Friend();

        listView = (ListView) view.findViewById(R.id.list);
        dbHelper = new DbHelper(getActivity());
        cursor = dbHelper.readAll();


        cursorListAdapter = new SimpleCursorAdapter(getActivity(),
                R.layout.list_item,
                cursor,
                new String[]{dbHelper.NAME, dbHelper.PRICE, dbHelper.DATE, dbHelper.CAT_NAME},
                new int[]{R.id.textNameItem, R.id.textSumItem, R.id.textDateItem, R.id.textCateg}, 0);
/*
        ArrayList<Friend> list = new ArrayList<>();
        list.clear();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                dbHelper.ID,
                dbHelper.NAME,
                dbHelper.PRICE,
                dbHelper.CAT_NAME,
                dbHelper.DATE};

             cursor = db.query(
                dbHelper.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        try {
            System.out.println("Таблица содержит " + cursor.getCount() + " операций.\n\n");


            int idColumnIndex = cursor.getColumnIndex(dbHelper.ID);
            int nameColumnIndex = cursor.getColumnIndex(dbHelper.NAME);
            int priceColumnIndex = cursor.getColumnIndex(dbHelper.PRICE);
            int catColumnIndex = cursor.getColumnIndex(dbHelper.CAT_NAME);
            int dateColumnIndex = cursor.getColumnIndex(dbHelper.DATE);


            while (cursor.moveToNext()) {

                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
                String currentCat = cursor.getString(catColumnIndex);
                String currentDate = cursor.getString(dateColumnIndex);

                System.out.println("\n" + currentID + " - " +
                        currentName + " - " +
                        currentPrice + " - " +
                        currentCat + " - " +
                        currentDate + " - ");
            }
            cursor.moveToPosition(nameColumnIndex-1);
            for (int i = 0; i < cursor.getCount(); i++) {
                list.add(new Friend(cursor.getString(nameColumnIndex),  cursor.getInt(priceColumnIndex),
                        cursor.getString(catColumnIndex), cursor.getString(dateColumnIndex)));
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
            db.close();
        }


*/
        listView.setAdapter(cursorListAdapter);
        count = listView.getAdapter().getCount();


        LayoutAnimationController controller = AnimationUtils
                .loadLayoutAnimation(getActivity(), R.anim.list_layout_controller);
        listView.setLayoutAnimation(controller);
        System.out.println("--------getListCount = " + count);

        Report report = new Report();
        report.getCountAll(count);


        context = getActivity();


        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            strname2 = extras.getString("name2");
            intprice2 = extras.getInt("price2");
            strdate2 = extras.getString("date2");
            idcat2 = extras.getLong("idcat2");
            id2 = extras.getLong("id2");
            iddel = extras.getLong("iddel");
            exorin = extras.getString("exorin2");
        }
        System.out.println("-----------------MainActivity------------------\nname2: " + strname2 + " price2: " + intprice2 + " id: " + id2 + "  expenses or income: " + exorin + "\n-----------------MainActivity------------------");
        dbHelper.update(strname2, intprice2, strdate2, idcat2, id2, exorin);
        cursor = dbHelper.readAll();
        cursorListAdapter.changeCursor(cursor);
        tvwal.setText(Long.toString(dbHelper.getSumm()));
        if((int)dbHelper.getSumm()>=0){
            tvwal.setTextColor(getResources().getColor(R.color.green));
            tvRub.setTextColor(getResources().getColor(R.color.green));
        }
        else if ((int)dbHelper.getSumm()<0){
            tvwal.setTextColor(getResources().getColor(R.color.red));
            tvRub.setTextColor(getResources().getColor(R.color.red));
        }


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Fragment_main.class);
                intent.putExtra(SEND, "это строка");
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                System.out.println("position= " + position + "   id: " + id + "  v= " + view);
                id_name = id;
                MainActivity mainActivity = new MainActivity();
                fr = dbHelper.select(id);
                Intent intent = new Intent(getActivity(), update.class);
                intent.putExtra(SENDNAME, fr.getName());
                intent.putExtra(SENDPRICE, fr.getPrice());
                intent.putExtra(SENDDATE, fr.getDate());
                intent.putExtra(SENDCAT, fr.getCat());
                intent.putExtra(SENDID, fr.getID());
                intent.putExtra(SENDEXORIN, fr.getExorin());
                mainActivity.finish();
                startActivity(intent);

            }
        });
        return view;
    }
}
