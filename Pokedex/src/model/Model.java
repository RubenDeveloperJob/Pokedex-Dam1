/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
//imports

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import principal.Pokemon;
import principal.Usuario;

import java.sql.*;
import principal.Conexion;

/**
 *
 * @author Rubén
 */
public class Model {

    ArrayList<Pokemon> pokedex = new ArrayList<Pokemon>();//lista con todos los pokemons creados

    private ObservableList<String> obs_list_nom_pkmn = FXCollections.observableArrayList();//ObservableList para los nombres de los pokemons
    private ObservableList<Pokemon> obs_ver_pokedex = FXCollections.observableArrayList();//ObservableList para ver la pokedex
    private ObservableList<String> obs_eventos = FXCollections.observableArrayList();//ObservableList para ver los eventos
    ArrayList<String> nombres_pokes = new ArrayList<String>();//lista de los nombres de los pokemons
    LinkedList<String> eventos = new LinkedList();//LinkedList de los eventos
    HashMap<Integer, String> normas = new HashMap<Integer, String>();//Hashmap de las normas
    Connection connection;

//Constructor modelo
    public Model() {

    }

    public void crear_usuario(String nom, String contra) throws SQLException {
        Connection connection = new Conexion().connecta();//conexion
        String sql = "INSERT INTO usuarios VALUES (" + "'" + nom + "'" + "," + "'" + contra + "'" + ",'no')";//sentencia sql
        PreparedStatement sentencia = connection.prepareStatement(sql);
        try {

            sentencia.executeUpdate();//hace el insert de ususario

        } catch (SQLException throwables) {
            System.out.println("exep: " + throwables);
            throwables.printStackTrace();
        }
        connection.close();
    }

    public ObservableList<String> getnombres_pkmn() {
//devuelve una lista con los nombres de los pokemons para que salgan en el list view todos los nombres
        String datai = null;
        ObservableList<String> llistaPoks = FXCollections.observableArrayList();
        String sql = "select nom from Pokemon";
        //String sql="select nom from usuaris";
        Connection connection = new Conexion().connecta();
        System.out.println("conexion" + connection);
        try {

            Statement ordre = connection.createStatement();

            ResultSet resultSet = ordre.executeQuery(sql);

            while (resultSet.next()) {//mientras tenga que leer va añadiendo los nombres a la lista
                System.out.println(resultSet.getString(1));
                llistaPoks.add(
                        resultSet.getString(1)
                );
            }

            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return llistaPoks;

    }

    public String get_descripcion(String nombre_poke) {
//devuelve la descripcion del pojemon y en caso de que el pokemon no tenga descripcion por no haber sido introducida dira que no tiene descripcion
        String descripcion = "No hay descripción para este Pokémon";
        String datai = null;

        String sql = "select nom,descripcion from Pokemon";
        //String sql="select nom from usuaris";
        Connection connection = new Conexion().connecta();
        System.out.println("conexion" + connection);
        try {

            Statement ordre = connection.createStatement();

            ResultSet resultSet = ordre.executeQuery(sql);

            while (resultSet.next()) {
                if (resultSet.getString(1).equals(nombre_poke)) {//mira el nombre y si coincide pasa la descripcion
                    descripcion = resultSet.getString(2);
                }

            }

            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return descripcion;
    }

    public String get_tipos(String nombre_poke) {

        String tipos = "";
        String datai = null;

        String sql = "select nom,tipos from Pokemon";

        Connection connection = new Conexion().connecta();

        try {

            Statement ordre = connection.createStatement();

            ResultSet resultSet = ordre.executeQuery(sql);
            //busca por el nombre, si lo encuentra le pasa sus tipos
            while (resultSet.next()) {
                if (resultSet.getString(1).equals(nombre_poke)) {
                    tipos = resultSet.getString(2);
                }

            }

            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return tipos;
    }

    public String get_ruta_imagen(String nombre_poke) {

        String ruta = "";
        String datai = null;

        String sql = "select nom,ruta_imagen from Pokemon";
        Connection connection = new Conexion().connecta();
        System.out.println("conexion" + connection);
        try {

            Statement ordre = connection.createStatement();

            ResultSet resultSet = ordre.executeQuery(sql);
            //busca por el nombre, si lo encuentra le pasa su ruta de imagen
            while (resultSet.next()) {
                if (resultSet.getString(1).equals(nombre_poke)) {
                    ruta = resultSet.getString(2);
                }

            }

            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return ruta;
    }

    public void anadir_a_la_pokedex(String nom, String desc, String tipos, String ruta) throws SQLException {
        Connection connection = new Conexion().connecta();
        //CREA LA SENTENCIA SQL ADAPTADA A LOS VALORES DEL POKEMON QUE QUUEREMOS CREAR
        String sql = "INSERT INTO Pokemon VALUES (" + "'" + nom + "'" + "," + "'" + desc + "'" + "," + "'" + tipos + "'" + "," + "'" + ruta + "'" + ")";
        PreparedStatement sentencia = connection.prepareStatement(sql);
        try {

            sentencia.executeUpdate();//hace el INSERT

        } catch (SQLException throwables) {
            System.out.println("exep: " + throwables);
            throwables.printStackTrace();
        }
        connection.close();

    }

    public Usuario sacar_admin() {
        Usuario master = null;
        String sql = "select nom,contra,permisos from usuarios";
        Connection connection = new Conexion().connecta();
        try {

            Statement ordre = connection.createStatement();

            ResultSet resultSet = ordre.executeQuery(sql);
            //busca el admin por su nombre si lo encuentra crea un nuevo usuario con sus datos y lo devuelve 
            while (resultSet.next()) {
                if (resultSet.getString(1).equals("Admin")) {

                    master = new Usuario(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
                }

            }

            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return master;
    }

    public Usuario buscar_usuario_y_conectarse(String nomusuario, String contra) {
        Usuario master = null;
        String sql = "select nom,contra,permisos from usuarios";
        Connection connection = new Conexion().connecta();
        try {

            Statement ordre = connection.createStatement();

            ResultSet resultSet = ordre.executeQuery(sql);
            //busca el usuario con sus credenciales, si coincides las dos crea un usuario y lo devuelve para que se conecte
            while (resultSet.next()) {
                if (resultSet.getString(1).equals(nomusuario) && resultSet.getString(2).equals(contra)) {

                    master = new Usuario(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));

                }

            }

            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return master;
    }

    public boolean cambiar_contra(String nomusuario, String contra, String nueva_contra) {

        boolean cambiado = false;//indica si se ha cambiado la contraseña
        boolean credenciales = false;//indica si las credenciales son correctas
        String sql = "select nom,contra,permisos from usuarios";
        Connection connection = new Conexion().connecta();
        try {

            Statement ordre = connection.createStatement();
            ResultSet resultSet = ordre.executeQuery(sql);
            //si las credenciales son correctas marcara que lo son
            while (resultSet.next()) {
                if (resultSet.getString(1).equals(nomusuario) && resultSet.getString(2).equals(contra)) {

                    credenciales = true;

                } else {

                }

            }
// si las credenciales son correctas 
            if (credenciales == true) {
                sql = "UPDATE usuarios SET contra=" + "'" + nueva_contra + "'" + " WHERE nom=" + "'" + nomusuario + "'";//cambia la sentencia
                PreparedStatement ordreupdate = connection.prepareStatement(sql);
                ordreupdate.executeUpdate();//hace el uodate
            }

            //
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return cambiado;

    }

    public void borrarusu(String nomusu) throws SQLException {
        //crea la sentencia sql con el nombre del usuario
        String sql = "DELETE FROM usuarios WHERE nom=" + "'" + nomusu + "'";
        Connection connection = new Conexion().connecta();
        try {

            Statement ordre = connection.createStatement();

            PreparedStatement ordreupdate = connection.prepareStatement(sql);

            ordreupdate.executeUpdate();//hace el delete

       
            connection.close();
        } catch (SQLException throwables) {
            System.out.println("ex: " + throwables);
            throwables.printStackTrace();
        }
    }

}
