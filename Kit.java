import java.util.ArrayList;

/**
 * Representa un kit o paquete de boletos agrupados que se vende a los aficionados.
 * * @author Samantha Sulay Luna Malta
 * @author 
 * @version 1.0
 */
public class Kit {
    private String codigo;
    private String nombre;
    private String descripcion;
    private ArrayList<Partido> partidosIncluidos;
    private double precio;
    private int disponibles;

    /**
     * Constructor para instanciar un Kit.
     * * @param codigo Identificador unico del kit.
     * @param nombre Nombre descriptivo del paquete.
     * @param descripcion Detalle del kit.
     * @param partidosIncluidos Lista de partidos que componen el paquete.
     * @param precio Tarifa total del kit.
     * @param disponibles Stock disponible del paquete.
     */
    public Kit(String codigo, String nombre, String descripcion, ArrayList<Partido> partidosIncluidos, double precio, int disponibles) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.partidosIncluidos = partidosIncluidos;
        this.precio = precio;
        this.disponibles = disponibles;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public ArrayList<Partido> getPartidosIncluidos() { return partidosIncluidos; }
    public double getPrecio() { return precio; }
    public int getDisponibles() { return disponibles; }
    
    public void setDisponibles(int disponibles) { this.disponibles = disponibles; }
}
