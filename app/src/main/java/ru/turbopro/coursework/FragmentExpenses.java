package ru.turbopro.coursework;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Calendar;

public class FragmentExpenses extends Fragment {

    final static String SENDEXORIN = "expenses_or_income";

    View view;
    Spinner cat;
    EditText edName;
    EditText edSum;
    TextView currentDate;
    Button btnSave;
    Button btnCansel;
    Calendar date = Calendar.getInstance();
    DbHelper dbHelper;
    ImageView imagecalc;
    double res;
    String str;

    public FragmentExpenses() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.expenses_fragment, container, false);

        double defaulvalue=2;
        dbHelper = new DbHelper(getActivity());

        currentDate = (TextView)view.findViewById(R.id.tvDate);
        edSum = (EditText)view.findViewById(R.id.editSum);
        edName = (EditText)view.findViewById(R.id.editName);
        cat = (Spinner)view.findViewById(R.id.spinner);
        btnSave = (Button)view.findViewById(R.id.button2);
        btnCansel = (Button)view.findViewById(R.id.button3);
        imagecalc = (ImageView) view.findViewById(R.id.imgViewCalcExpenses);


        imagecalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Calc.class);
                intent.putExtra(SENDEXORIN,"expenses");
                startActivity(intent);
                getActivity().finish();
            }
        });

//        String str = String.valueOf(res);
//        if (!str.equals("0.0")) edSum.setText(str);
//        System.out.println("++++++++++++++++++++++++"+str+"++++++++++++++++++++++++");

        currentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), d,
                        date.get(Calendar.YEAR),
                        date.get(Calendar.MONTH),
                        date.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });

       setInitialDateTime();
       setupSpinner();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edSum.length()!=0 && edName.length()!=0) {
                    System.out.println("-------------------FragmentExpenses---------");
                    System.out.println("cat: " + cat.getSelectedItemId());
                    String rubpr = " руб.";
                    String minus = "-";
                    dbHelper.insert(edName.getText().toString(), cat.getSelectedItemId(), minus + edSum.getText().toString() + rubpr,
                            currentDate.getText().toString(), "expenses");
                    System.out.println("-------------------FragmentExpenses---------");
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().finish();
                }
                else if(edSum.length()!=0 && edName.length()==0){
                    System.out.println("-------------------FragmentExpenses---------");
                    System.out.println("cat: " + cat.getSelectedItemId());
                    String rubpr = " руб.";
                    String minus = "-";
                    dbHelper.insert("Без названия", cat.getSelectedItemId(), minus + edSum.getText().toString() + rubpr,
                            currentDate.getText().toString(), "expenses");
                    System.out.println("-------------------FragmentExpenses---------");
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().finish();
                }
                else
                    Toast.makeText(getActivity(),"Введите сумму",Toast.LENGTH_SHORT).show();
                edSum.requestFocus();
            }
        });

        btnCansel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        setTxt();
        return view;
    }

    public void setInitialDateTime(){
        currentDate.setText("  "+DateUtils.formatDateTime(getActivity(),
                date.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            date.set(Calendar.YEAR, year);
            date.set(Calendar.MONTH, monthOfYear);
            date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

    private void setupSpinner() {

        ArrayAdapter catSpinnerAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.array_cat_options_expenses, android.R.layout.simple_spinner_item);

        catSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        cat.setAdapter(catSpinnerAdapter);
        cat.setSelection(0);

        cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setTextExp(String str) {
        this.str = str;
        System.out.println("Expenses++++++++++++++++++++---------"+str+"-----------++++++++++++++++++++++++=");
    }

    private void setTxt(){
        if(str!=null && !str.equals("0")) edSum.setText(str);
    }

}