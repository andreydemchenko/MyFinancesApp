package ru.turbopro.coursework;

public class Friend {
    private long id;
    private String cat;
    private String name;
    private int price;
    private String data;
    private String exorin;
    private int count;

    public  Friend () {}


    public Friend (String Name, int Price, String Cat, String Date) {
        this.name = Name;
        this.price = Price;
        this.cat = Cat;
        this.data = Date;
    }
    public Friend (long ID, String Name, String Cat, int Price, String Data, String ExOrIn) {
        this.id = ID;
        this.name = Name;
        cat=Cat;
        price=Price;
        data=Data;
        exorin=ExOrIn;
    }

    public Friend(int Count){
        count = Count;
        System.out.println("Friend--------------------"+count+"------------------------Friend");
    }


    public long getID () {return id;}
    public String getName (){return  name;}
    public String getCat (){return cat;}
    public int getPrice(){return price;}
    public String getDate(){return data;}
    public String getExorin() { return exorin;}
}
