package kiosk;

public class Food extends Menu {

    private double price;
    private String type;

    private int number;

    private String option1_name;
    private Double option1_price;

    private String option2_name;
    private Double option2_price;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public int getNumber() {
        return number;
    }

    public String getOption1_name() {
        return option1_name;
    }

    public void setOption1_name(String option1_name) {
        this.option1_name = option1_name;
    }

    public void setOption2_name(String option2_name) {
        this.option2_name = option2_name;
    }

    public void setOption1_price(Double option1_price) {
        this.option1_price = option1_price;
    }

    public void setOption2_price(Double option2_price) {
        this.option2_price = option2_price;
    }

    public String getOption2_name() {
        return option2_name;
    }

    public Double getOption1_price() {
        return option1_price;
    }

    public Double getOption2_price() {
        return option2_price;
    }

    public Food(String type, String name, double price, String explan) {
        super(name, explan);
        this.price = price;
        this.type = type;
        this.number = 1;
        this.option1_name = null;
        this.option2_name = null;
    }


    @Override
    public void ShowMenu()
    {
        System.out.println(String.format("%-15s", getName())+" | W "+price +" | "+getExplan());
    }

    public String getMenu( ){
        //int set_plus = number+plus;
        return String.format("%-15s", getName())+" | W "+price +" | "+getExplan();

    }

    public String getMenu(int  number){
        //int set_plus = number+plus;
        return String.format("%-15s", getName())+" | W "+price +" | "+number+" 개| "+getExplan();

    }

    public static  Food copyFood(Food copy){
        Food copy_food = new Food(copy.type,copy.getName(), copy.price, copy.getExplan());
        return  copy_food;
    }
}
