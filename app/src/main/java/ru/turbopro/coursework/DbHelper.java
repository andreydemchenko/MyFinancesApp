package ru.turbopro.coursework;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;


public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Friend.db";
    private static final int DB_VERSION = 1;
    static final String TABLE_NAME = "ExpensesAndIncome";
    static final String ID = "_id";
    static final String NAME = "name";
    static final String CAT_NAME = "cat_name";
    static final String PRICE = "price";
    static final String DATE = "date";
    static final String EXORIN = "expenses_or_income";

    String exp = "expenses";
    String inc = "income";
    int count = 0;

    private SQLiteDatabase mdb;

    String categ;
    View view;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.mdb = getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NAME + " TEXT," + CAT_NAME + " TEXT, " + PRICE + " TEXT," + DATE +
                " TEXT," + EXORIN + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void delete(long NID) {
        String delete = "DELETE  FROM " + TABLE_NAME + " WHERE " + ID + " = " + NID;
        System.out.println("String DELETE: " + delete);
        if(count!=0)count--;
        this.mdb.execSQL(delete);

    }

    public void insert(String Name, long Cat, String Price, String Date, String ExOrIn) {
        count++;
        Friend friend = new Friend(count);

        if (ExOrIn.equals(exp)) {
            if (Cat == 0) categ = "Не выбрано";
            else if (Cat == 1) categ = "Питание";
            else if (Cat == 2) categ = "Одежда";
            else if (Cat == 3) categ = "Аксессуары";
            else if (Cat == 4) categ = "Электроника";
            else if (Cat == 5) categ = "Развлечения";
            else if (Cat == 6) categ = "Красота и здоровье";
            else if (Cat == 7) categ = "Дом";
            else if (Cat == 8) categ = "Транспорт";
        } else {
            if (Cat == 0) categ = "Не выбрано";
            else if (Cat == 1) categ = "Зарплата";
            else if (Cat == 2) categ = "Стипендия";
            else if (Cat == 3) categ = "Премия";
            else if (Cat == 4) categ = "Подработка";
        }

        ContentValues value = new ContentValues();
        value.put(NAME, Name);
        value.put(CAT_NAME, categ);
        value.put(PRICE, Price);
        value.put(DATE, Date);
        value.put(EXORIN, ExOrIn);

        mdb.insert(TABLE_NAME, null, value);
    }

    public Cursor readAll() {
        System.out.println("--------readAll------");

        Cursor cursor = null;
        try {
            cursor = mdb.query(TABLE_NAME, new String[]{
                            ID, NAME, CAT_NAME, PRICE, DATE, EXORIN},
                    null, //нет столбцов для where
                    null, // нет значений для условия where
                    null, //нет групп строк
                    null, //не фильтровать с помощью групп строк
                    null // не сортировать
            );
            while (cursor.moveToNext()) {
                int id = (int) cursor.getInt(cursor.getColumnIndex(this.ID));
                String name = cursor.getString(cursor.getColumnIndex(NAME));
                String cat =  cursor.getString(cursor.getColumnIndex(CAT_NAME));
                int price = (int) cursor.getInt(cursor.getColumnIndex(PRICE));
                String date = cursor.getString(cursor.getColumnIndex(DATE));
                String exorin = cursor.getString(cursor.getColumnIndex(EXORIN));
                Log.d("mLog", "Запись(id): " + id + "    name: " + name + " " +
                        "   category: " + cat+"   price: " + price + "   date: " + date+"  expenses or income: "+exorin);
            }
        } catch (SQLiteException e) { }

        System.out.println("--------readAll------");
        return cursor;
    }

    public Friend select(long NID) {
        System.out.println("----------select------------");

        Cursor cursor = this.mdb.query(TABLE_NAME,
                null,
                this.ID + "=" + NID,
                null, null, null, null);

        cursor.moveToFirst();
        int id = (int) cursor.getInt(cursor.getColumnIndex(this.ID));
        String name = cursor.getString(cursor.getColumnIndex(NAME));
        String cat = cursor.getString(cursor.getColumnIndex(CAT_NAME));
        int price = (int) cursor.getInt(cursor.getColumnIndex(PRICE));
        String date = cursor.getString(cursor.getColumnIndex(DATE));
        String exorin = cursor.getString(cursor.getColumnIndex(EXORIN));
        System.out.println("Запись(id): " + id + "    name: " + name + " " +
                "   category: " + cat + "   price: " + price + "   date: " + date+"  expenses or income: "+exorin);
        System.out.println("----------select------------");

        return new Friend(id, name, cat, price, date, exorin);
    }


    public void update(String strname, Integer intprice, String strdate, long idcat, long NID, String ExOrIn) {
        if (ExOrIn != null && ExOrIn.equals(exp)){
            if(idcat==0)categ="Питание";
            else if(idcat==1)categ="Одежда";
            else if(idcat==2)categ="Аксессуары";
            else if(idcat==3)categ="Электроника";
            else if (idcat==4) categ = "Развлечения";
            else if (idcat==5) categ = "Красота и здоровье";
            else if (idcat==6) categ = "Дом";
            else if (idcat==7) categ = "Транспорт";
            else if (idcat==8) categ = "Не выбрано";
        }
        else {
            if(idcat==0)categ="Зарплата";
            else if(idcat==1)categ="Стипендия";
            else if(idcat==2)categ="Премия";
            else if(idcat==3)categ="Подработка";
            else if(idcat==4)categ="Не выбрано";
        }

        System.out.println("----------updateDbHelper------------");
        String rubpr = " руб.";
        ContentValues value = new ContentValues();
        value.put(NAME, strname);
        value.put(PRICE, intprice+rubpr);
        value.put(DATE, strdate);
        value.put(CAT_NAME, categ);
        this.mdb.update(TABLE_NAME, value,
                this.ID + "=" + NID,
                null);
        System.out.println("    name: " + strname + " " +
                "   cat: " + idcat + "   price: " + intprice + "   date: " + strdate+" exorin: "+ExOrIn);
        System.out.println("----------updateDbHelper------------");
    }

    public long getSumm() {
        long res = 0;
        Cursor cursor = this.mdb.query(TABLE_NAME, new String[]{"SUM(" + PRICE + ")"}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            res = cursor.getLong(0);
        }
        return res;
    }

    public long getSummExpenses() {

        long res = 0;

        Cursor cursor = getReadableDatabase().
                rawQuery("select sum(" + PRICE + ") from " + TABLE_NAME + " where " + EXORIN + " = ?", new String[]{"expenses"});
        if (cursor.moveToFirst()) {
            res = cursor.getLong(0);
        }

        return res;
    }

    public long getSummIncome() {

        long res = 0;

        Cursor cursor = getReadableDatabase().
                rawQuery("select sum(" + PRICE + ") from " + TABLE_NAME + " where " + EXORIN + " = ?", new String[]{"income"});
        if (cursor.moveToFirst()) {
            res = cursor.getLong(0);
        }

        return res;
    }

    public long getSummExpFood(){

        long res = 0;

        Cursor cursor = getReadableDatabase().
                rawQuery("select sum(" + PRICE + ") from " + TABLE_NAME + " where " + CAT_NAME + " = ?", new String[]{"Питание"});
        if (cursor.moveToFirst()) {
            res = cursor.getLong(0);
        }

        return res;
    }

    public long getSummExpClothes(){

        long res = 0;

        Cursor cursor = getReadableDatabase().
                rawQuery("select sum(" + PRICE + ") from " + TABLE_NAME + " where " + CAT_NAME + " = ?", new String[]{"Одежда"});
        if (cursor.moveToFirst()) {
            res = cursor.getLong(0);
        }

        return res;
    }

    public long getSummExpAcces(){

        long res = 0;

        Cursor cursor = getReadableDatabase().
                rawQuery("select sum(" + PRICE + ") from " + TABLE_NAME + " where " + CAT_NAME + " = ?", new String[]{"Аксессуары"});
        if (cursor.moveToFirst()) {
            res = cursor.getLong(0);
        }

        return res;
    }

    public long getSummExpElectr(){

        long res = 0;

        Cursor cursor = getReadableDatabase().
                rawQuery("select sum(" + PRICE + ") from " + TABLE_NAME + " where " + CAT_NAME + " = ?", new String[]{"Электроника"});
        if (cursor.moveToFirst()) {
            res = cursor.getLong(0);
        }

        return res;
    }

    public long getSummEntainment(){

        long res = 0;

        Cursor cursor = getReadableDatabase().
                rawQuery("select sum(" + PRICE + ") from " + TABLE_NAME + " where " + CAT_NAME + " = ?", new String[]{"Развлечения"});
        if (cursor.moveToFirst()) {
            res = cursor.getLong(0);
        }

        return res;
    }

    public long getSummExpBeAndHeal(){

        long res = 0;


        Cursor cursor = getReadableDatabase().
                rawQuery("select sum(" + PRICE + ") from " + TABLE_NAME + " where " + CAT_NAME + " = ?", new String[]{"Красота и здоровье"});
        if (cursor.moveToFirst()) {
            res = cursor.getLong(0);
        }

        return res;
    }

    public long getSummExpHome(){

        long res = 0;

        Cursor cursor = getReadableDatabase().
                rawQuery("select sum(" + PRICE + ") from " + TABLE_NAME + " where " + CAT_NAME + " = ?", new String[]{"Дом"});
        if (cursor.moveToFirst()) {
            res = cursor.getLong(0);
        }

        return res;
    }

    public long getSummExpTransport(){

        long res = 0;

        Cursor cursor = getReadableDatabase().
                rawQuery("select sum(" + PRICE + ") from " + TABLE_NAME + " where " + CAT_NAME + " = ?", new String[]{"Транспорт"});
        if (cursor.moveToFirst()) {
            res = cursor.getLong(0);
        }

        return res;
    }

    public long getSummExpNotselect(){

        long res = 0;

        Cursor cursor = getReadableDatabase().rawQuery("select sum(" + PRICE + ") from " +
                TABLE_NAME + " where " + CAT_NAME + " = ? and " + EXORIN + " = ? ", new String[]{"Не выбрано", "expenses"});
        if (cursor.moveToFirst()) {
            res = cursor.getLong(0);
        }

        return res;
    }


    public long getSummIncSalary(){

        long res = 0;

        Cursor cursor = getReadableDatabase().
                rawQuery("select sum(" + PRICE + ") from " + TABLE_NAME + " where " + CAT_NAME + " = ?", new String[]{"Зарплата"});
        if (cursor.moveToFirst()) {
            res = cursor.getLong(0);
        }

        return res;
    }

    public long getSummIncSchoolarship(){

        long res = 0;

        Cursor cursor = getReadableDatabase().
                rawQuery("select sum(" + PRICE + ") from " + TABLE_NAME + " where " + CAT_NAME + " = ?", new String[]{"Стипендия"});
        if (cursor.moveToFirst()) {
            res = cursor.getLong(0);
        }

        return res;
    }

    public long getSummIncPrize(){

        long res = 0;

        Cursor cursor = getReadableDatabase().
                rawQuery("select sum(" + PRICE + ") from " + TABLE_NAME + " where " + CAT_NAME + " = ?", new String[]{"Премия"});
        if (cursor.moveToFirst()) {
            res = cursor.getLong(0);
        }

        return res;
    }

    public long getSummIncUnderworking(){

        long res = 0;

        Cursor cursor = getReadableDatabase().
                rawQuery("select sum(" + PRICE + ") from " + TABLE_NAME + " where " + CAT_NAME + " = ?", new String[]{"Подработка"});
        if (cursor.moveToFirst()) {
            res = cursor.getLong(0);
        }

        return res;
    }

    public long getSummIncNotselect(){

        long res = 0;

        Cursor cursor = getReadableDatabase().rawQuery("select sum(" + PRICE + ") from " +
                TABLE_NAME + " where " + CAT_NAME + " = ? and " + EXORIN + " = ? ", new String[]{"Не выбрано", "income"});
        if (cursor.moveToFirst()) {
            res = cursor.getLong(0);
        }

        return res;
    }
}
