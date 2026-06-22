public class Aficionado {
    // Ponemos los atributos en private para cumplir con el encapsulamiento que exige la ESPOL
    private String codigoUnico;
    private String cedula;
    private String nombres;
    private String apellidos;
    private String usuario;
    private String contrasena;
    private String correo;
    private String celular;
    private String paisFavorito;

    // Constructor completo para setear todos los datos cuando leamos el archivo txt
    public Aficionado(String codigoUnico, String cedula, String nombres, String apellidos, 
                      String usuario, String contrasena, String correo, String celular, String paisFavorito) {
        this.codigoUnico = codigoUnico;
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.correo = correo;
        this.celular = celular;
        this.paisFavorito = paisFavorito;
    }

    // --- GETTERS Y SETTERS ---
    // Los necesitamos obligatoriamente para poder leer y modificar las variables desde el Main
    
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

    public String getNombres() { 
        return nombres; 
    }
    public void setNombres(String nombres) { 
        this.nombres = nombres; 
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