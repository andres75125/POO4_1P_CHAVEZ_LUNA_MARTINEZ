import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Controlador de la logica central del negocio del Mundial de la FIFA.
 * Administra las colecciones en memoria de datos, la autenticacion, las compras y notificaciones.
 * * @author Jorge Andres Martinez Gutierrez
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

                        Usuario nuevoAficionado = new Aficionado(codigo, cedula, nombre, apellido, usuario, contrasena, correo, Rol.AFICIONADO, celular, paisFavorito);
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

                        Usuario nuevoOrganizador = new Organizador(codigo, cedula, nombre, apellido, usuario, contrasena, correo, Rol.ORGANIZADOR, empresa, cargo);
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

    /**
     * Busca y valida las credenciales ingresadas por consola contra la coleccion global.
     * * @param cuenta Nombre de usuario ingresado.
     * @param clave Contrasena ingresada.
     * @return El objeto de tipo Usuario (Aficionado u Organizador) si es exitoso, null de lo contrario.
     */
    public Usuario autenticarUsuario(String cuenta, String clave) {
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario u = usuarios.get(i);
            if (u.getUsuario().equals(cuenta) && u.getContrasena().equals(clave)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Registra la transaccion en la coleccion de compras y en el fichero compras.txt.
     * * @param c Instancia del objeto Compra generado.
     */
    public void registrarCompra(Compra c) {
        compras.add(c);
        ManejoArchivos.EscribirArchivo("compras.txt", c.toString());
    }


    /**
     * Sobrecarga 1: Procesa la compra de una Entrada Individual.
     * Actualiza stocks en memoria, persiste la transaccion y genera el correo.
     * * @param aficionado Aficionado que compra.
     * @param partido Partido elegido.
     * @param zona Localidad seleccionada.
     * @param cantidad Cantidad de entradas.
     * @param numTarjeta Metodo de pago.
     * @return Objeto Compra registrado.
     */
    public Compra comprar(Aficionado aficionado, Partido partido, String zona, int cantidad, String numTarjeta) {
        double precioBase = 0.0;
        int stock = 0;
        
        if (zona.equalsIgnoreCase("GENERAL")) {
            stock = partido.getEntradasDisponiblesGeneral();
            precioBase = partido.getPrecioGeneral();
            partido.setEntradasDisponiblesGeneral(stock - cantidad);
        } else if (zona.equalsIgnoreCase("PREFERENCIAL")) {
            stock = partido.getEntradasDiponiblesPreferencial();
            precioBase = partido.getPrecioPreferencial();
            partido.setEntrasdaDisponiblesPreferencial(stock - cantidad);
        } else if (zona.equalsIgnoreCase("VIP")) {
            stock = partido.getEntradasDisponiblesVIP();
            precioBase = partido.getPrecioVIP();
            partido.setEntradasDisponibleVIP(stock - cantidad);
        }
        
        double total = precioBase * cantidad;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaHoy = sdf.format(new Date());
        
        Compra nuevaCompra = new Compra("ENTRADA", partido.getCodigoPartido(), fechaHoy, cantidad, total, aficionado.getCodigoUnico());
        registrarCompra(nuevaCompra);
        
        // Notificacion correspondiente a Entrada Individual (3.1)
        notificar(aficionado, nuevaCompra);
        
        return nuevaCompra;
    }

    /**
     * Sobrecarga 2: Procesa la compra de un Kit de Entradas.
     * Actualiza el stock del kit, persiste la transaccion y dispara la notificacion.
     * * @param aficionado Aficionado que compra.
     * @param kit Kit elegido.
     * @param cantidad Cantidad de kits comprados.
     * @param numTarjeta Tarjeta de credito ingresada.
     * @return Objeto Compra registrado.
     */
    public Compra comprar(Aficionado aficionado, Kit kit, int cantidad, String numTarjeta) {
        int stock = kit.getDisponibles();
        kit.setDisponibles(stock - cantidad);
        
        double total = kit.getPrecio() * cantidad;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaHoy = sdf.format(new Date());
        
        Compra nuevaCompra = new Compra("KIT", kit.getCodigo(), fechaHoy, cantidad, total, aficionado.getCodigoUnico());
        registrarCompra(nuevaCompra);
        
        // Notificacion correspondiente a Compra de Kit (3.2)
        notificar(aficionado, nuevaCompra, kit);
        
        return nuevaCompra;
    }

    // =========================================================================
    // SOBRECARGA DEL METODO NOTIFICAR (PAGINAS 7 Y 8 DEL PDF)
    // =========================================================================

    /**
     * Variante 3.1: Envia confirmacion de Compra de Entrada Individual.
     * * @param aficionado Aficionado receptor.
     * @param compraRealizada Datos de la transaccion.
     */
    public void notificar(Aficionado aficionado, Compra compraRealizada) {
        Partido partido = null;
        for (int i = 0; i < partidos.size(); i++) {
            if (partidos.get(i).getCodigoPartido().equalsIgnoreCase(compraRealizada.getCodigoReferencia())) {
                partido = partidos.get(i);
                break;
            }
        }
        
        String partidoDetalle = (partido != null) 
            ? partido.getCodigoPartido() + ", " + partido.getSeleccionLocal() + " vs " + partido.getSeleccionVisitante() + " (Fecha: " + new SimpleDateFormat("yyyy-MM-dd").format(partido.getFecha()) + ")"
            : compraRealizada.getCodigoReferencia();

        String asunto = "Confirmacion de Compra - Entrada Mundial 2026";
        String cuerpo = "Estimado/a " + aficionado.getNombre() + " " + aficionado.getApellidos() + ",\n\n" +
                        "Su compra ha sido registrada con exito en nuestro portal.\n\n" +
                        "Resumen del pedido:\n" +
                        "- Codigo de compra: " + compraRealizada.getCodigoCompra() + "\n" +
                        "- Partido incluido: " + partidoDetalle + "\n" +
                        "- Cantidad: " + compraRealizada.getCantidad() + "\n" +
                        "- Valor a pagar: $" + compraRealizada.getValorTotal() + "\n\n" +
                        "¡Gracias por confiar en nosotros!";
        
        ServicioCorreo.enviarCorreoReal(aficionado.getCorreo(), asunto, cuerpo);
    }

    /**
     * Variante 3.2: Envia confirmacion de Compra de un Kit de Entradas.
     * * @param aficionado Aficionado receptor.
     * @param compraRealizada Transaccion registrada.
     * @param kit Paquete o Kit adquirido.
     */
    public void notificar(Aficionado aficionado, Compra compraRealizada, Kit kit) {
        String listaPartidos = "";
        for (int i = 0; i < kit.getPartidosIncluidos().size(); i++) {
            Partido p = kit.getPartidosIncluidos().get(i);
            listaPartidos += "  * " + p.getCodigoPartido() + ", " + p.getSeleccionLocal() + " vs " + p.getSeleccionVisitante() + " (Fecha: " + new SimpleDateFormat("yyyy-MM-dd").format(p.getFecha()) + ")\n";
        }

        String asunto = "Confirmacion de Compra - Kit Mundial 2026";
        String cuerpo = "Estimado/a " + aficionado.getNombre() + " " + aficionado.getApellidos() + ",\n\n" +
                        "Su compra del kit ha sido registrada con exito en nuestro portal.\n\n" +
                        "Resumen del pedido:\n" +
                        "- Codigo de compra: " + compraRealizada.getCodigoCompra() + "\n" +
                        "- Kit adquirido: " + kit.getNombre() + "\n" +
                        "- Partidos incluidos:\n" + listaPartidos + "\n" +
                        "- Cantidad de kits: " + compraRealizada.getCantidad() + "\n" +
                        "- Valor a pagar: $" + compraRealizada.getValorTotal() + "\n\n" +
                        "¡Disfrute del torneo!";
        
        ServicioCorreo.enviarCorreoReal(aficionado.getCorreo(), asunto, cuerpo);
    }

    /**
     * Variante 3.3: Remite el balance de auditoria al Organizador.
     * * @param organizador Administrador que audita.
     * @param totalCompras Transacciones totales.
     * @param totalEntradas Boletos individuales vendidos.
     * @param totalKits Paquetes vendidos.
     * @param montoTotal Dinero total recaudado.
     */
    public void notificar(Organizador organizador, int totalCompras, int totalEntradas, int totalKits, double montoTotal) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaHoy = sdf.format(new Date());

        String asunto = "Reporte de compras registradas";
        String cuerpo = "Estimado/a " + organizador.getNombre() + " " + organizador.getApellidos() + ",\n\n" +
                        "Se ha generado el reporte de compras del sistema.\n" +
                        "Fecha de generacion del reporte: " + fechaHoy + "\n" +
                        "Total de compras registradas: " + totalCompras + "\n" +
                        "Total de compras de entradas individuales: " + totalEntradas + "\n" +
                        "Total de compras de kits: " + totalKits + "\n" +
                        "Monto total recaudado: $" + montoTotal + "\n\n" +
                        "Este reporte corresponde a las compras registradas en el archivo compras.txt.";
        
        ServicioCorreo.enviarCorreoReal(organizador.getCorreo(), asunto, cuerpo);
    }

    /**
     * Genera en consola el reporte financiero y dispara la notificacion de auditoria.
     * * @param organizador Organizador que solicita el reporte.
     */
    public void generarReporteDeVentas(Organizador organizador) {
        int totalCompras = compras.size();
        int totalEntradas = 0;
        int totalKits = 0;
        double montoTotal = 0.0;

        for (int i = 0; i < compras.size(); i++) {
            Compra c = compras.get(i);
            montoTotal += c.getValorTotal();
            if (c.getTipoAdquisicion().equalsIgnoreCase("ENTRADA")) {
                totalEntradas++;
            } else if (c.getTipoAdquisicion().equalsIgnoreCase("KIT")) {
                totalKits++;
            }
        }

        System.out.println("\n===== GENERAR REPORTE DE VENTAS =====");
        System.out.println("Resumen de ventas registradas:");
        System.out.println("Total de compras: " + totalCompras);
        System.out.println("Compras por tipo:");
        System.out.println("  Entradas: " + totalEntradas);
        System.out.println("  Kits: " + totalKits);
        System.out.println("Monto total recaudado: $" + montoTotal);

        // Dispara la sobrecarga del reporte de notificar (3.3)
        notificar(organizador, totalCompras, totalEntradas, totalKits, montoTotal);
    }
}
