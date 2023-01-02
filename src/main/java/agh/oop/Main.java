package agh.oop;

import agh.gui.App;
import javafx.application.Application;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello Satora!");
        System.out.println("Hello Ozog!");

        try{
            Application.launch(App.class, args);
        }catch(Exception ex) {
            System.out.println(ex.toString());
        }
        System.out.println("System zakończyl dzialanie");
    }
}