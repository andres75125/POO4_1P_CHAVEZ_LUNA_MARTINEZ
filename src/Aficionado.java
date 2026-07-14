import java.util.ArrayList;

/**
 * Representa a un usuario de tipo Aficionado que puede comprar entradas.
 * Hereda de la clase base Usuario.
 * * @author Jorge Andres Martinez Gutierrez
 */
public class Aficionado extends Usuario {
    private String celular;
    private String paisFavorito;

    /**
     * Constructor para inicializar un Aficionado con sus atributos especificos.
     * * @param codigoUnico Codigo unico de aficionado.
     * @param cedula Numero de cedula.
     * @param nombre Nombres del aficionado.
     * @param apellidos Apellidos del aficionado.
     * @param usuario Nombre de usuario.
     * @param contrasena Clave de acceso.
     * @param correo Correo electronico.
     * @param rol Rol asignado (AFICIONADO).
     * @param celular Numero de contacto telefonico.
     * @param paisFavorito Seleccion nacional de preferencia.
     */
    public Aficionado(String codigoUnico, String cedula, String nombre, String apellidos, 
                      String usuario, String contrasena, String correo, Rol rol, 
                      String celular, String paisFavorito) {
        super(codigoUnico, cedula, nombre, apellidos, usuario, contrasena, correo, rol);
        this.celular = celular;
        this.paisFavorito = paisFavorito;
    }

    /**
     * Implementacion del metodo abstracto para consultar unicamente las compras propias.
     * Aplica el principio de polimorfismo y sobrescritura.
     * * @param compras Lista global de compras del sistema.
     */
    @Override
    public void consultarEntradas(ArrayList<Compra> compras) {
        System.out.println("\n========================================");
        System.out.println("         MIS ENTRADAS ADQUIRIDAS        ");
        System.out.println("========================================");
        
        boolean tieneCompras = false;
        for (int i = 0; i < compras.size(); i++) {
            Compra c = compras.get(i);
            if (c.getCodigoAficionado().equalsIgnoreCase(this.codigoUnico)) {
                System.out.println("Codigo Compra: " + c.getCodigoCompra());
                System.out.println("  Tipo: " + c.getTipoAdquisicion());
                System.out.println("  Referencia ID: " + c.getCodigoReferencia());
                System.out.println("  Fecha de Compra: " + c.getFechaCompra());
                System.out.println("  Cantidad Adquirida: " + c.getCantidad());
                System.out.println("  Valor Pagado: $" + c.getValorTotal());
                System.out.println("----------------------------------------");
                tieneCompras = true;
            }
        }
        
        if (!tieneCompras) {
            System.out.println("Usted no registra transacciones en nuestro sistema.");
        }
    }

    public String getCelular() { return celular; }
    public String getPaisFavorito() { return paisFavorito; }
}
