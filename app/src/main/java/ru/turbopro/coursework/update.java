package ru.turbopro.coursework;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Calendar;

public class update extends Activity {


    EditText edSum;
    EditText edName;
    Spinner cat;
    DbHelper dbHelper;

    TextView currentDate;
    Calendar date = Calendar.getInstance();

    static String exorin;
    String strdate;
    String strname;
    int intprice;
    String exp = "expenses";
    String inc = "income";
    String price;
    String cat_name;
    long id;
    int id_cat;

    private AdView mAdView;

    AlertDialog.Builder ad;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        setTitle("Редактирование");

        mAdView = findViewById(R.id.adViewUpdate);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("09F34835DC3F0DED2139AFB85B4877E1")
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mAdView.loadAd(adRequest);

        edSum = (EditText) findViewById(R.id.edSumUpdate);
        edName = (EditText) findViewById(R.id.edNameUpdate);
        currentDate = (TextView) findViewById(R.id.tvDateSetUpdate);
        cat = (Spinner) findViewById(R.id.spinnerUpdate);

        dbHelper = new DbHelper(this);
        Friend fr = new Friend();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String str = extras.getString(Home_Fragment.SEND);
            strname = extras.getString(Home_Fragment.SENDNAME);
            intprice = extras.getInt(Home_Fragment.SENDPRICE);
            strdate = extras.getString(Home_Fragment.SENDDATE);
            cat_name = extras.getString(Home_Fragment.SENDCAT);
            id = extras.getLong(Home_Fragment.SENDID);
            exorin = extras.getString(Home_Fragment.SENDEXORIN);
        }
        System.out.println("------Update------\nЗапись(id): " + id + "    name: " + strname + " " +
                "   category: " + cat_name + "   price: " + intprice + "   date: " + strdate+"  expenses or income: "+exorin+"\n----Update----");

        if(exorin.equals(exp)) setupSpinnerUpdateExpenses();
        else if (exorin.equals(inc)) setupSpinnerUpdateIncome();

        price = String.valueOf(intprice);
        edName.setText(strname);
        if(exorin.equals(exp)) edSum.setText(price.substring(1));
        else if (exorin.equals(inc)) edSum.setText(price);
        currentDate.setText(strdate);




        String title = "Удалить операцию?";
        String button1String = "Удалить";
        String button2String = "Отмена";

        context = update.this;
        ad = new AlertDialog.Builder(context);
        ad.setTitle(title);  // заголовок
        //ad.setMessage(fr.getName()); // сообщение
        ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                System.out.println("Запись для удаления: " + id);
                dbHelper.delete(id);
                dbHelper.readAll();
                Intent intent = new Intent(update.this, MainActivity.class);
                intent.putExtra("iddel", id);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) { }
        });
        ad.setCancelable(true);
        ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(context, "Вы ничего не выбрали",
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    private void setupSpinnerUpdateExpenses() {

        ArrayAdapter catSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_cat_options_expenses, android.R.layout.simple_spinner_item);

        catSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        cat.setAdapter(catSpinnerAdapter);

            if (cat_name.equals("Не выбрано")) id_cat = 0;
            else if (cat_name.equals("Питание")) id_cat = 1;
            else if (cat_name.equals("Одежда")) id_cat = 2;
            else if (cat_name.equals("Аксессуары")) id_cat = 3;
            else if (cat_name.equals("Электроника")) id_cat = 4;
            else if (cat_name.equals("Развлечения")) id_cat = 5;
            else if (cat_name.equals("Красота и здоровье")) id_cat = 6;
            else if (cat_name.equals("Дом")) id_cat = 7;
            else if (cat_name.equals("Транспорт")) id_cat = 8;

            cat.setSelection(id_cat);


        cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupSpinnerUpdateIncome() {

        ArrayAdapter catSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_cat_options_income, android.R.layout.simple_spinner_item);

        catSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        cat.setAdapter(catSpinnerAdapter);

        if (cat_name.equals("Не выбрано")) id_cat = 0;
        else if (cat_name.equals("Зарплата")) id_cat = 1;
        else if (cat_name.equals("Стипендия")) id_cat = 2;
        else if (cat_name.equals("Премия")) id_cat = 3;
        else if (cat_name.equals("Подработка")) id_cat = 4;

        cat.setSelection(id_cat);

        cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void setInitialDateTime(){
        currentDate.setText(DateUtils.formatDateTime(this,
                date.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    public void onClickDate(View v) {
        new DatePickerDialog(update.this, d,
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH))
                .show();
    }


    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            date.set(Calendar.YEAR, year);
            date.set(Calendar.MONTH, monthOfYear);
            date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();

        }
    };

    public void onClickUpdate(View v) {

        switch (v.getId()) {
            case R.id.btnUpdateUpdate:

                Integer textPrice = Integer.parseInt(edSum.getText().toString());
                if (textPrice > 0 && edSum.getText().length() != 0) {
                    String textName = edName.getText().toString();
                    String strpriceexp = "-"+edSum.getText().toString();
                    if (exorin.equals(exp))textPrice = Integer.parseInt(strpriceexp);
                    String textDate = currentDate.getText().toString();
                    long idcat = cat.getSelectedItemId();
                    Intent intent = new Intent(update.this, MainActivity.class);
                    intent.putExtra("name2", textName);
                    intent.putExtra("price2", textPrice);
                    intent.putExtra("date2", textDate);
                    intent.putExtra("idcat2", idcat);
                    intent.putExtra("id2", id);
                    intent.putExtra("exorin2", exorin);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(context, "Введите сумму!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnDeleteUpdate:
                ad.show();
                break;
            case R.id.btnCancelUpdate:
                finish();
                break;
        }
    }
}
