import java.util.ArrayList;

/**
 * Clase abstracta que representa la plantilla general de un usuario en el sistema.
 * Aplica el principio de Abstraccion al no permitir instanciaciones directas.
 * * @author Jorge Andres Martinez Gutierrez
 * @author
 * @version 1.0
 */
public abstract class Usuario {
    protected String codigoUnico;
    protected String cedula;
    protected String nombre;
    protected String apellidos;
    protected String usuario;
    protected String contrasena;
    protected String correo;
    protected Rol rol;

    /**
     * Constructor para inicializar los datos de un usuario base.
     * * @param codigoUnico Codigo unico identificador del usuario.
     * @param cedula Numero de identificacion (cedula).
     * @param nombre Nombres del usuario.
     * @param apellidos Apellidos del usuario.
     * @param usuario Nombre de usuario para el inicio de sesion.
     * @param contrasena Clave de acceso.
     * @param correo Direccion de correo electronico.
     * @param rol Rol asignado dentro del sistema (Enum Rol).
     */
    public Usuario(String codigoUnico, String cedula, String nombre, String apellidos, 
                   String usuario, String contrasena, String correo, Rol rol) {
        this.codigoUnico = codigoUnico;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.correo = correo;
        this.rol = rol;
    }

    /**
     * Metodo abstracto polimorfico para la consulta de boletos o reporte de transacciones.
     * Debe ser implementado obligatoriamente por las clases hijas.
     * * @param compras Lista global de compras registradas en el sistema.
     */
    public abstract void consultarEntradas(ArrayList<Compra> compras);

    // Métodos Getters para cumplir con el Encapsulamiento
    public String getCodigoUnico() { return codigoUnico; }
    public String getCedula() { return cedula; }
    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public String getUsuario() { return usuario; }
    public String getContrasena() { return contrasena; }
    public String getCorreo() { return correo; }
    public Rol getRol() { return rol; }
}