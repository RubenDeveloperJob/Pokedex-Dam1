/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author rugar
 */
public class Conexion {

   // IMPORTANTE//
    //SI NO ABRE LA APP ES PORQUE MI MAQUINA AL CAMBIAR DE RED WIFI CAMBIA LA IP DE CONEXION//
    //SOLUCION: MIRAR LA IP EN LA MV CON EL COMANDO       IP ADDRES     Y PONERLA EN EL STRING URL     //
   

    private final String URL = "jdbc:mysql://192.168.79.154:3306/pokedex";
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String USER = "pokedex";
    private final String PASSWD = "Qwerty1";

    public Connection connecta() {
        Connection connexio = null;
        try {
            //Carreguem el driver          
            Class.forName(DRIVER); 
            connexio = DriverManager.getConnection(URL, USER, PASSWD); 
           
        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println("ERRor"+throwables.getMessage());
        }    
        return connexio;
    }
}
