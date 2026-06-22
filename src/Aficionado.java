import java.util.ArrayList;

// Usamos extends porque en el diagrama Aficionado hereda de la clase abstracta Usuario
public class Aficionado extends Usuario {
    
    // Atributos específicos que aparecen en el recuadro de Aficionado en el UML
    private String celular;
    private String paisFavorito;

    // El constructor recibe sus parámetros propios y los que necesita heredar del Padre
    public Aficionado(String codigoUnico, String cedula, String nombre, String apellidos, 
                      String usuario, String contrasena, String correo, Rol rol, 
                      String celular, String paisFavorito) {
        
        // Con super() pasamos los datos obligatorios al constructor de la clase abstracta Usuario
        super(codigoUnico, cedula, nombre, apellidos, usuario, contrasena, correo, rol);
        this.celular = celular;
        this.paisFavorito = paisFavorito;
    }

    // Método que aparece en el diagrama para consultar las compras del aficionado
    @Override
    public void consultarEntradas(ArrayList<Compra> compras) {
        System.out.println("--- Consultando entradas del aficionado ---");
        // Aquí se recorrerá la lista de compras buscando las que coincidan con el codigoUnico
    }

    // Sobrescritura del método toString para mostrar la información limpia
    @Override
    public String toString() {
        return super.toString() + " | Celular: " + celular + " | País Favorito: " + paisFavorito;
    }

    // --- GETTERS Y SETTERS ---
    // El diagrama pide explicitamente +getters() y +setters() para acceder a sus atributos encapsulados
    
    public String getCelular() { 
        return celular; 
    }
    public void setCelular(String celular) { 
        this.celular = celular; 
    }

    public String getPaisFavorito() { 
        return paisFavorito; 
    }
    public void setPaisFavorito(String paisFavorito) { 
        this.paisFavorito = paisFavorito; 
    }
}