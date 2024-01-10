package kiosk;


import java.util.ArrayList;
import java.util.HashMap;

public class Menu {
    //필드
    private String name;
    private String explan;

    // 생성자
    public Menu(String name, String explan){
        this.name = name;
        this.explan = explan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExplan(String explan) {
        this.explan = explan;
    }

    public String getExplan(){
        return  explan;
    }

    void ShowMenu(){
        System.out.println(String.format("%-15s", name)+" | "+explan);
    }

}
