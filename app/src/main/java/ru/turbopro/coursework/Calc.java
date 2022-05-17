package ru.turbopro.coursework;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Calc extends Activity implements View.OnClickListener {

    //final static String SENDRESULTEXP = "result";
    final static String SENDRESULT = "result";
    final static String SENDEXORIN = "exorin";

    private EditText edExpres;
    private TextView tvResult;
    private Button btn0;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btnplus;
    private Button btndivi;
    private Button btnmin;
    private ImageView btndelet;
    private Button btnclear;
    private Button btnmulti;
    private Button btnequl;
    private Button btndot;
    private boolean stateError;
    private boolean isNumber;
    private boolean lastDot;
    private double res;
    private String exp;
    private String inc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        setTitle("Калькулятор");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            exp = extras.getString(FragmentExpenses.SENDEXORIN);
            inc = extras.getString(FragmentIncome.SENDEXORIN);
        }

        edExpres=findViewById(R.id.edExpression);
        tvResult=findViewById(R.id.tvResult);
        btn0=findViewById(R.id.btnZero);
        btn1=findViewById(R.id.btnOne);
        btn2=findViewById(R.id.btnTwo);
        btn3=findViewById(R.id.btnThree);
        btn4=findViewById(R.id.btnFour);
        btn5=findViewById(R.id.btnFive);
        btn6=findViewById(R.id.btnSix);
        btn7=findViewById(R.id.btnSeven);
        btn8=findViewById(R.id.btnEight);
        btn9=findViewById(R.id.btnNine);
        btnplus=findViewById(R.id.btnPlus);
        btnmin=findViewById(R.id.btnMinus);
        btnmulti=findViewById(R.id.btnMul);
        btnequl=findViewById(R.id.btnEquals);
        btndivi=findViewById(R.id.btnDivide);
        btndelet=findViewById(R.id.btnBack);
        btndot=findViewById(R.id.btnDot);
        btnclear=findViewById(R.id.btnClear);


        setOnClick();
        edExpres.addTextChangedListener(textWatcher);
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            calcule(false);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void calcule(boolean isequlclick){

        String input = getinput();
        try {
            if(!isEmpty() && !endWidthOperator()){

                Expression expression = new ExpressionBuilder(input).build();
                double result=expression.evaluate();
                if (isequlclick) {
                    edExpres.setText(String.valueOf(result));
                    tvResult.setText("");
                    res = result;
                }else
                tvResult.setText(Double.toString(result));
            } else tvResult.setText("");

        }catch (Exception e){
            stateError=true;
            isNumber=false;
        }
    }

    private void setOnClick(){
        this.btn0.setOnClickListener(this);
        this.btn1.setOnClickListener(this);
        this.btn2.setOnClickListener(this);
        this.btn3.setOnClickListener(this);
        this.btn4.setOnClickListener(this);
        this.btn5.setOnClickListener(this);
        this.btn6.setOnClickListener(this);
        this.btn7.setOnClickListener(this);
        this.btn8.setOnClickListener(this);
        this.btn9.setOnClickListener(this);
        this.btnplus.setOnClickListener(this);
        this.btnmin.setOnClickListener(this);
        this.btnmulti.setOnClickListener(this);
        this.btnequl.setOnClickListener(this);
        this.btndivi.setOnClickListener(this);
        this.btndelet.setOnClickListener(this);
        this.btndot.setOnClickListener(this);
        this.btnclear.setOnClickListener(this);
    }

    public void onClick(View view) {
        int Id = view.getId();
        switch (Id) {
            case R.id.btnZero:
                append("0");
                isNumber = true;
                break;
            case R.id.btnOne:
                append("1");
                isNumber = true;
                break;
            case R.id.btnTwo:
                append("2");
                isNumber = true;
                break;
            case R.id.btnThree:
                append("3");
                isNumber = true;
                break;
            case R.id.btnFour:
                append("4");
                isNumber = true;
                break;
            case R.id.btnFive:
                append("5");
                isNumber = true;
                break;
            case R.id.btnSix:
                isNumber = true;
                append("6");
                break;
            case R.id.btnSeven:
                append("7");
                isNumber = true;
                break;
            case R.id.btnEight:
                append("8");
                isNumber = true;
                break;
            case R.id.btnNine:
                append("9");
                isNumber = true;
                break;
            case R.id.btnPlus:
                if (endWidthOperator())
                    replace("+");
                else
                    append("+");
                isNumber = true;
                lastDot = false;
                break;
            case R.id.btnMinus:
                if (endWidthOperator())
                    replace("-");
                else
                    append("-");
                isNumber = true;
                lastDot = false;
                break;
            case R.id.btnMul:
                if (endWidthOperator())
                    replace("*");
                else
                    append("*");
                isNumber = true;
                lastDot = false;
                break;
            case R.id.btnEquals:
                calcule(true);
                if (exp.equals("expenses")) {
                    FragmentExpenses fragmentExpenses = new FragmentExpenses();
                    Intent intent = new Intent(Calc.this, Fragment_main.class);
                    intent.putExtra(SENDRESULT, (int)res);
                    intent.putExtra(SENDEXORIN, exp);
                    startActivity(intent);
                    finish();
                } else if (inc.equals("income")){
                    Intent intent = new Intent(Calc.this, Fragment_main.class);
                    intent.putExtra(SENDRESULT, (int)res);
                    intent.putExtra(SENDEXORIN, inc);
                    startActivity(intent);
                    //FragmentIncome fragmentIncome = new FragmentIncome();
                    //Bundle bundle = new Bundle();
                    //bundle.putDouble(SENDRESULTINC, res);
                    //fragmentIncome.setArguments(bundle);
                    finish();
                }
                break;
            case R.id.btnDivide:
                if (endWidthOperator())
                    replace("/");
                else
                    append("/");
                lastDot = false;
                isNumber = true;
                break;
            case R.id.btnClear:
                clear();
                break;
            case R.id.btnDot:
                if (isNumber && !stateError && !lastDot) {
                    append(".");
                    isNumber = false;
                    lastDot = true;
                } else if (isEmpty()) {
                    append("0.");
                    isNumber = false;
                    lastDot = true;
                }
                break;
            case R.id.btnBack:
                delete();
                break;
            default:
                break;
        }
    }

    private void append(String str){
        this.edExpres.getText().append(str);
    }

    private void delete(){
        if(!isEmpty()){
            this.edExpres.getText().delete(getinput().length()-1,getinput().length());
        } else clear();
    }

    private String getinput(){
        return this.edExpres.getText().toString();
    }

    private boolean isEmpty(){
        return getinput().isEmpty();
    }

    private void clear(){
        lastDot=false;
        isNumber=false;
        stateError=false;
        edExpres.getText().clear();
        tvResult.setText("");
    }

    private void replace(String str){
        edExpres.getText().replace(getinput().length()-1,getinput().length(),str);
    }

    private boolean endWidthOperator(){
        return getinput().endsWith("+")||getinput().endsWith("-")||getinput().endsWith("/")||getinput().endsWith("*");
    }
}
