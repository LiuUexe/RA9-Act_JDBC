package Controller;

import Model.DataBaseManager;
import frames.MainMenu;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class EmployeeController {

    public static DataBaseManager bbdd = new DataBaseManager();

    public static void main(String[] args) {

        MainMenu menu = new MainMenu();
        menu.setVisible(true);

    }

}
