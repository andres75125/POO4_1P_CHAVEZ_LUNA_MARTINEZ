import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Controlador de la logica central del negocio del Mundial de la FIFA.
 * Administra las colecciones en memoria de datos, la autenticacion, las compras y notificaciones.
 * * @author 
 * @author 
 * @version 1.0
 */
public class Sistema {
    private ArrayList<Usuario> usuarios;
    private ArrayList<Partido> partidos;
    private ArrayList<Compra> compras; 
    private ArrayList<Kit> kits;

    /**
     * Constructor del sistema central. Inicializa las listas y carga toda la informacion de archivos.
     */
    public Sistema(){
        usuarios = new ArrayList<>();
        partidos = new ArrayList<>();
        kits = new ArrayList<>();
        compras = new ArrayList<>();
        
        cargarPartidos(); 
        cargarUsuarios();
        cargarKits();
        cargarCompras();
    }
    
    // Getters encapsulados para acceso seguro a colecciones globales
    public ArrayList<Usuario> getUsuarios(){ return usuarios; }
    public ArrayList<Partido> getPartidos(){ return partidos; }
    public ArrayList<Compra> getCompras(){ return compras; }
    public ArrayList<Kit> getKits(){ return kits; }

    /**
     * Carga y procesa la lista de usuarios cruzando los datos para la instanciacion polimorfica.
     */
    public void cargarUsuarios(){
        ArrayList<String> infoUsuarios  = ManejoArchivos.LeeFichero("usuarios.txt");
        ArrayList<String> infoAficionados = ManejoArchivos.LeeFichero("aficionados.txt");
        ArrayList<String> infoOrganizadores = ManejoArchivos.LeeFichero("organizadores.txt");
        
        for(int i = 1; i < infoUsuarios.size(); i++){
            String linea = infoUsuarios.get(i);
            String[] datos = linea.split("\\|");

            String codigo = datos[0].trim();
            String cedula = datos[1].trim();
            String nombre = datos[2].trim();
            String apellido = datos[3].trim();
            String usuario = datos[4].trim();
            String contrasena = datos[5].trim();
            String correo = datos[6].trim();
            String rol = datos[7].trim();
            
            if(rol.equals("A")){
                for(int x = 1; x < infoAficionados.size(); x++){
                    String registroAficionado = infoAficionados.get(x);
                    String[] datosAficionados = registroAficionado.split("\\|");
                    
                    if(codigo.equals(datosAficionados[0].trim())){
                        String celular = datosAficionados[4].trim();
                        String paisFavorito = datosAficionados[5].trim();

                        Usuario nuevoAficionado = new Aficionado(codigo, cedula, nombre, apellido, usuario, contrasena, correo, Rol.A, celular, paisFavorito);
                        usuarios.add(nuevoAficionado);
                    }
                }    
            }
            else if(rol.equals("O")){
                for(int x = 1; x < infoOrganizadores.size(); x++){
                    String registroOrganizador = infoOrganizadores.get(x);
                    String[] datosOrganizador = registroOrganizador.split("\\|");

                    if(codigo.equals(datosOrganizador[0].trim())){
                        String empresa = datosOrganizador[4].trim();
                        String cargo = datosOrganizador[5].trim();

                        Usuario nuevoOrganizador = new Organizador(codigo, cedula, nombre, apellido, usuario, contrasena, correo, Rol.O, empresa, cargo);
                        usuarios.add(nuevoOrganizador);
                    }
                }
            }
        }
    }

    /**
     * Carga y procesa los partidos disponibles mapeando los precios dinamicos leidos desde partidos.txt.
     */
    public void cargarPartidos(){
        ArrayList<String> infoPartidos = ManejoArchivos.LeeFichero("partidos.txt");
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

        for(int i = 1; i < infoPartidos.size(); i++){
            String registro = infoPartidos.get(i);
            String[] datos = registro.split("\\|");

            String codigo = datos[0].trim();
            String local = datos[1].trim();
            String visitante = datos[2].trim();
            String fechaTexto = datos[3].trim();
            String estadio = datos[4].trim();
            String ciudad = datos[5].trim();
            int capacidad = Integer.parseInt(datos[6].trim());
            int general = Integer.parseInt(datos[7].trim());
            int preferencial = Integer.parseInt(datos[8].trim());
            int vip = Integer.parseInt(datos[9].trim());
            String fase = datos[10].trim();
            
            // Lectura de precios dinamicos desde el archivo plano
            double precioGeneral = Double.parseDouble(datos[11].trim());
            double precioPreferencial = Double.parseDouble(datos[12].trim());
            double precioVIP = Double.parseDouble(datos[13].trim());
            
            try{
                Date fecha = formatoFecha.parse(fechaTexto);
                Partido partido = new Partido(codigo, local, visitante, fecha, estadio, ciudad, capacidad, general, preferencial, vip, fase, precioGeneral, precioPreferencial, precioVIP);
                partidos.add(partido);
            } catch(ParseException e){
                System.out.println("Error al procesar la fecha de partidos.txt");
            }
        }
    }

    /**
     * Carga y procesa los paquetes o agrupaciones de entradas (Kits).
     */
    public void cargarKits(){
        ArrayList<String> infoKits = ManejoArchivos.LeeFichero("kits.txt");

        for(int i = 1; i < infoKits.size(); i++){
            String registro = infoKits.get(i);
            String[] datos = registro.split("\\|");

            String codigo = datos[0].trim();
            String nombre = datos[1].trim();
            String descripcion = datos[2].trim();
            String partidosKit = datos[3].trim();
            double precio = Double.parseDouble(datos[4].trim());
            int disponibles = Integer.parseInt(datos[5].trim());

            String[] codigosPartidos = partidosKit.split(",");
            ArrayList<Partido> partidosIncluidos = new ArrayList<>();
            for(int x = 0; x < codigosPartidos.length; x++){
                for(int j = 0; j < partidos.size(); j++){
                    if(codigosPartidos[x].trim().equals(partidos.get(j).getCodigoPartido())){
                        partidosIncluidos.add(partidos.get(j));
                    }
                }
            }
            Kit nuevoKit = new Kit(codigo, nombre, descripcion, partidosIncluidos, precio, disponibles);
            kits.add(nuevoKit);
        }
    }

    /**
     * Carga la informacion historica de transacciones previas en el archivo de compras.
     */
    public void cargarCompras() {
        ArrayList<String> infoCompras = ManejoArchivos.LeeFichero("compras.txt");
        for (int i = 0; i < infoCompras.size(); i++) {
            String linea = infoCompras.get(i).trim();
            if (linea.isEmpty()) continue;
            String[] datos = linea.split("\\|");
            String codigoCompra = datos[0].trim();
            String tipo = datos[1].trim();
            String codigoRef = datos[2].trim();
            String fecha = datos[3].trim();
            int cantidad = Integer.parseInt(datos[4].trim());
            double valor = Double.parseDouble(datos[5].trim());
            String codigoAf = datos[6].trim();

            Compra c = new Compra(codigoCompra, tipo, codigoRef, fecha, cantidad, valor, codigoAf);
            compras.add(c);
        }
    }

}
