/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal;

/**
 *
 * @author Rubén
 */
public class Pokemon {
    //datos del poke
    private String nom;
    private String descripcion;
    private String tipos;
    private String ruta_imagen;
//constructor
    public Pokemon(String nom, String descripcion, String tipos, String ruta_imagen) {
        this.nom = nom;
        this.descripcion = descripcion;
        this.tipos = tipos;
        this.ruta_imagen = ruta_imagen;
    }
//getters
    public String getNom() {
        return nom;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTipos() {
        return tipos;
    }

    public String getRuta_imagen() {
        return ruta_imagen;
    }
    
}
