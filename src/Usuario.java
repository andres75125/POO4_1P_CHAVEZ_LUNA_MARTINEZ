import java.util.ArrayList;
public abstract class Usuario {
    private String codigoUnico;
    private String cedula;
    private String nombre;
    private String apellidos;
    private String usuario;
    private String contrasena;
    private String correo;
    private Rol rol;

    //Agregar el constructor de la clase
    public Usuario(String codigoUnico, String cedula, String nombre, String apellidos, String usuario, String contrasena, String correo, Rol rol){
        this.codigoUnico=codigoUnico;
        this.cedula=cedula;
        this.nombre=nombre;
        this.apellidos=apellidos;
        this.usuario=usuario;
        this.contrasena = contrasena;
        this.correo = correo;
        this.rol = rol;
    }
    public abstract void consultarEntradas(ArrayList<Compra>compras);
    
    //Aplicar getters y setters por cada atributo
    public String getCodigoUnico() {
        return codigoUnico;
    }
    public void setCodigoUnico(String codigoUnico) {
        this.codigoUnico = codigoUnico;
    }
    public String getCedula() {
        return cedula;
    }
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getContrasena() {
        return contrasena;
    }
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public Rol getRol() {
        return rol;
    }
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "codigoUnico=" + codigoUnico + ", cedula=" + cedula + ", nombre=" + nombre + ", apellidos=" + apellidos + ", usuario=" + usuario + ", correo=" + correo + ", rol=" + rol;
    }

}

