package controlador;
//imports

import java.awt.Font;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import model.Model;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import principal.Conexion;

import principal.Pokemon;
import principal.Usuario;

public class FinestraController implements Initializable {
//inyeccion 

    Model model;
    Usuario master;// para saber el usuario conectado
    String pokemon_seleccionado;//saber el seleccionado por su nombre
    int contador_de_tipos = 0;//para sumar maximo dos tipos
    String nombre_no_mostrado;
    String desc_no_mostrado;

    // controles de la interfaz
    @FXML
    private Text event_crear_usu;

    @FXML
    private Text pokcreate;
      @FXML
    private Text noti_pok_creado;
    

    @FXML
    private TextField buscarpkmn;

    @FXML
    private TextField contrasena_nueva;

    @FXML
    private TextArea descripcion;

    @FXML
    private TextArea descripcion_pokedex;

    @FXML
    private ImageView imagen;

    @FXML
    private Text norma_txt;

    @FXML
    private CheckBox quitanom;

    @FXML
    private CheckBox quitadesc;

    @FXML
    private TextField nombre_de_pkmn_nuevo;

    @FXML
    private TextField num_norma;

    @FXML
    private ListView<String> pokedex_lista;

    @FXML
    private ListView<String> eventos;

    @FXML
    private Text pokemonname;

    @FXML
    private TextField ruta_pkmn_img;

    @FXML
    private TextField sucontra;

    @FXML
    private TextField sunombre;

    @FXML
    private Text tipos_pok;

    @FXML
    private Text contracamb;

    @FXML
    private ToggleGroup tipos_pokemon;

    @FXML
    private TextField usuario_nuevo;

    @FXML
    private TextField contrasenanueva;

    @FXML
    private Text usuario_conectado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = new Model();//al inicializar crea el modelo
     

        master = model.sacar_admin();

        sunombre.setText("");
        sucontra.setText("");//set parametros a vacio
        if (master == null) {// si no hay nadie conectado
            usuario_conectado.setText("No conectado");// nos dira que no estamos conectados
        } else {
            usuario_conectado.setText(master.getNom());//nos dice su nombre
        }
        descripcion_pokedex.setWrapText(true);//el texto se ajusta a la ventana
        descripcion_pokedex.setEditable(false);//no se edita

        pokedex_lista.setItems(model.getnombres_pkmn());//carga los nombres en la lista

        //escucha constantemente el que hemos seleccionado para mostrar todos sus datos en funcion del nombre
        pokedex_lista.getSelectionModel().selectedItemProperty().addListener(
                (var, oldValue, newValue) -> {
                    try {
                        pokemon_seleccionado = pokedex_lista.getSelectionModel().getSelectedItem();

                        descripcion_pokedex.setText(model.get_descripcion(pokemon_seleccionado));
                        descripcion_pokedex.setText(descripcion_pokedex.getText() + "\nTipos: " + model.get_tipos(pokemon_seleccionado));
                        pokemonname.setText(pokemon_seleccionado);
                        Image myImage = new Image(("img/" + model.get_ruta_imagen(pokemon_seleccionado)));
                        System.out.println(myImage);
                        imagen.setImage(myImage);
                    } catch (Exception e) {

                        //control de excepciones pq sino peta al iniciar el programa ya que no tienes seleccionado ninguno
                    }

                }
        );
    }

  

   

    public void sortir() {
        //vuelve el usuario null para indicar que no esta conectado y escribe introduzca etc
        master = null;
        sunombre.setText("Introduzca usuario");
        sucontra.setText("introduzca contraseña");
        if (master == null) {
            usuario_conectado.setText("No conectado");
        } else {
            usuario_conectado.setText(master.getNom());
        }

    }

    public void entrar() {
        if (model.buscar_usuario_y_conectarse(sunombre.getText(), sucontra.getText()) == null) {
//si el usuario no es null por lo cual existe se conecta sino dice que credenciales incorrectas
            usuario_conectado.setText("No conectado");
            sunombre.setText("credenciales erroneas");
            sucontra.setText("credenciales erroneas");
        } else {

            master = model.buscar_usuario_y_conectarse(sunombre.getText(), sucontra.getText());// master pasa a ser el usuario(objeto)que pasa el emtodo de vuelta con los datos de la BDD
            System.out.println(sunombre.getText()+" "+sucontra.getText());
            usuario_conectado.setText(master.getNom() + "\n Permisos " + master.getPermisos());

        }
        
    }

    public void buscar_poke() {

        String poke_que_es_buscado = buscarpkmn.getText();//nombre del pokemon

        descripcion_pokedex.setText(model.get_descripcion(poke_que_es_buscado));//llama al metodo del modelo para buscar su descripcion
        descripcion_pokedex.setText(descripcion_pokedex.getText() + "\nTipos: " + model.get_tipos(poke_que_es_buscado));//llama al metodo del modelo para buscar sus tipos
        pokemonname.setText(poke_que_es_buscado);//cambia el nombre
        Image myImage = new Image(("img/" + model.get_ruta_imagen(poke_que_es_buscado)));//crea lña ruta de la imagen
        //set imagen
        imagen.setImage(myImage);
    }

    public void anadir_usuario() throws SQLException {
        model.crear_usuario(usuario_nuevo.getText(), contrasena_nueva.getText());// llama al metodo del modelo para crear usuarios, lo crea y nos lo confirma
        event_crear_usu.setText("Usuario creado");
        event_crear_usu.setFill(Color.GREEN);

    }

    public void crear_pokemon() throws SQLException {
        if (master.getPermisos().equals("si")) {
            model.anadir_a_la_pokedex(nombre_de_pkmn_nuevo.getText(), descripcion.getText(), tipos_pok.getText(), ruta_pkmn_img.getText());
            pokedex_lista.setItems(model.getnombres_pkmn());
            noti_pok_creado.setText("Pokemon creado");//dice que se ha creado
            noti_pok_creado.setFill(Color.GREEN);
            //si el usuario es maestro llama al metodo crear pokemon y crea un pokemon, si no dice que no tines permisos
            //al crear un nuevo pokemon actualiza los items de la tabla
        } else {

            pokcreate.setText("No tienes permisos");
            pokcreate.setFill(Color.RED);

        }

    }

    public void anadir_tipo() {
        //si los tipos son 2 o menos añade un tipo 
        RadioButton btn = (RadioButton) tipos_pokemon.getSelectedToggle();// cast de objeto
        String tipo = btn.getText();
        if (contador_de_tipos < 2) {
            tipos_pok.setText(tipos_pok.getText() + " " + tipo);
            contador_de_tipos++;
        }

    }

  

  

    public void cambiar_contra() {
        boolean cambio = false;
        //mira si el usuario esta conectado si no lo esta dira que no estas conectado
        if (master != null) {
            cambio = model.cambiar_contra(master.getNom(), master.getContra(), contrasenanueva.getText());//llama al metodos de cambiar contraseña
            if (cambio != true) {
                contracamb.setText(" Contraseña cambiada");//si el cambio es correcto lo indica
                contracamb.setFill(Color.GREEN);
            }

        } else {
              contracamb.setText(" no estas conectado");
                contracamb.setFill(Color.RED);

        }

    }
    public void borrar_mi_usuario() throws SQLException{
    
        if (master.getPermisos().equals("no")) {// el admin no se puede borrar
            
            model.borrarusu(master.getNom());//llama al metodo borrar usuario
            master=null;//pone el master en null
              contracamb.setText(" Usuario borrado");// lo notifica
                contracamb.setFill(Color.GREEN);
                 usuario_conectado.setText("No conectado");
            sunombre.setText("Usuario");// pone el set text para que se pueda ingresar credenciales
            sucontra.setText("Contraseña");
        }else{
          contracamb.setText(" Admin no puede borrarse");//indica que no se puede borrar
                contracamb.setFill(Color.GREEN);
        }
    
    
    
    }
}
