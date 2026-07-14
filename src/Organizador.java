import java.util.ArrayList;

/**
 * Representa al personal organizador o administrador de la FIFA.
 * Hereda de la clase base Usuario.
 * * @author Javier Fernando Chavez Anchundia
 */
public class Organizador extends Usuario {
    private String empresa;
    private String cargo;

    /**
     * Constructor para inicializar un Organizador con sus atributos especificos.
     * * @param codigoUnico Codigo unico de organizador.
     * @param cedula Numero de cedula.
     * @param nombre Nombres del organizador.
     * @param apellidos Apellidos del organizador.
     * @param usuario Nombre de usuario.
     * @param contrasena Clave de acceso.
     * @param correo Correo electronico.
     * @param rol Rol asignado (ORGANIZADOR).
     * @param empresa Nombre de la empresa o comite al que pertenece.
     * @param cargo Puesto de trabajo del organizador.
     */
    public Organizador(String codigoUnico, String cedula, String nombre, String apellidos, 
                       String usuario, String contrasena, String correo, Rol rol, 
                       String empresa, String cargo) {
        super(codigoUnico, cedula, nombre, apellidos, usuario, contrasena, correo, rol);
        this.empresa = empresa;
        this.cargo = cargo;
    }

    /**
     * Implementacion del metodo abstracto para que el Organizador audite todas las compras.
     * Aplica el principio de polimorfismo y sobrescritura.
     * * @param compras Lista global de compras del sistema.
     */
    @Override
    public void consultarEntradas(ArrayList<Compra> compras) {
        System.out.println("\n===== CONSULTA DE COMPRAS =====");

        if (compras.isEmpty()) {
            System.out.println("No se registran compras dentro del sistema.");
            return;
        }

        for (int i = 0; i < compras.size(); i++) {
            Compra c = compras.get(i);
            System.out.println("Código Compra: " + c.getCodigoCompra() + " | Cliente ID: " + c.getCodigoAficionado());
            System.out.println("  Tipo: " + c.getTipoAdquisicion() + " | Referencia: " + c.getCodigoReferencia());
            System.out.println("  Fecha: " + c.getFechaCompra() + " | Boletos: " + c.getCantidad());
            System.out.println("  Total Recaudado: $" + c.getValorTotal());
            System.out.println("----------------------------------------");
        }
    }

    public String getEmpresa() { return empresa; }
    public String getCargo() { return cargo; }
}
