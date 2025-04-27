package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author ester
 */
public class DataBaseManager {

    private static String datosConexion = "jdbc:mysql://localhost:3306/";
    private static String baseDatos = "actJDBC";
    private static String usuario = "root";
    private static String password = "";
    private Connection con;

    public DataBaseManager() {
        try {
            con = DriverManager.getConnection(datosConexion, usuario, password);
            try {
                crearBBDD();

                crearTabla();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Creacion de de la BBDD
    private void crearBBDD() throws SQLException {
        String query = "create database if not exists " + baseDatos + ";";

        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(query);
            con = DriverManager.getConnection(datosConexion + baseDatos, usuario, password);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stmt.close();
        }
    }

    // Creacion de la tabla
    // Creación de la tabla (corregida)
    private void crearTabla() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS empleados ("
                + "id INT PRIMARY KEY AUTO_INCREMENT, "
                + "nombre VARCHAR(50), "
                + "edad INT, "
                + "departamento VARCHAR(50), "
                + "salario DOUBLE);";

        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
// MÉTODO PARA INGRESAR EMPLEADO

    public void ingresarEmpleado() {
        try {
            String nombre = JOptionPane.showInputDialog("Introduce el nombre del empleado:");
            int edad = Integer.parseInt(JOptionPane.showInputDialog("Introduce la edad del empleado:"));
            String departamento = JOptionPane.showInputDialog("Introduce el departamento del empleado:");
            double salario = Double.parseDouble(JOptionPane.showInputDialog("Introduce el salario del empleado:"));

            String query = "INSERT INTO empleados (nombre, edad, departamento, salario) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, nombre);
            ps.setInt(2, edad);
            ps.setString(3, departamento);
            ps.setDouble(4, salario);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Empleado ingresado correctamente.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al ingresar empleado.");
        }
    }

    // MÉTODO PARA ACTUALIZAR EMPLEADO
    public void actualizarEmpleado() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Introduce el ID del empleado a actualizar:"));
            String nombre = JOptionPane.showInputDialog("Nuevo nombre:");
            int edad = Integer.parseInt(JOptionPane.showInputDialog("Nueva edad:"));
            String departamento = JOptionPane.showInputDialog("Nuevo departamento:");
            double salario = Double.parseDouble(JOptionPane.showInputDialog("Nuevo salario:"));

            String query = "UPDATE empleados SET nombre=?, edad=?, departamento=?, salario=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, nombre);
            ps.setInt(2, edad);
            ps.setString(3, departamento);
            ps.setDouble(4, salario);
            ps.setInt(5, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Empleado actualizado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró un empleado con ese ID.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar empleado.");
        }
    }

    // MÉTODO PARA BORRAR EMPLEADO
    public void borrarEmpleado() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Introduce el ID del empleado a borrar:"));

            String query = "DELETE FROM empleados WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Empleado borrado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró un empleado con ese ID.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al borrar empleado");
        }
    }

    // MÉTODO PARA LISTAR EMPLEADOS
    public void listarEmpleados() {
        try {
            String query = "SELECT * FROM empleados";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("id"))
                        .append(", Nombre: ").append(rs.getString("nombre"))
                        .append(", Edad: ").append(rs.getInt("edad"))
                        .append(", Departamento: ").append(rs.getString("departamento"))
                        .append(", Salario: ").append(rs.getDouble("salario"))
                        .append("\n");
            }

            JOptionPane.showMessageDialog(null, sb.length() > 0 ? sb.toString() : "No hay empleados en la base de datos.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al listar empleados");
        }
    }
}
