import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Hilo conductor principal de la ejecucion. Administra la consola, sanea el buffer
 * del Scanner y maneja el Downcasting polimorfico y confirmacion de seguridad.
 * * @author Jorge Andres Martinez Gutierrez
 */
public class Main {
    
    /**
     * Ejecuta el inicio de sesion y desvia el flujo a los menus del rol autenticado.
     * * @param args Argumentos de terminal (No utilizados).
     */
    public static void main(String[] args) {
        Scanner lector = new Scanner(System.in);
        Sistema sistema = new Sistema();

        System.out.println("===== INICIO DE SESIÓN =====");
        System.out.print("Usuario: ");
        String cuentaInput = lector.nextLine().trim();
        System.out.print("Contraseña: ");
        String claveInput = lector.nextLine().trim();

        // Autenticacion Polimorfica
        Usuario usuarioLogueado = sistema.autenticarUsuario(cuentaInput, claveInput);

        if (usuarioLogueado == null) {
            System.out.println("\nError de acceso. Credenciales incorrectas.");
            return;
        }

        System.out.println("\nUsuario autenticado correctamente.");

        // =========================================================================
        // CONFIRMACIÓN OBLIGATORIA DE IDENTIDAD
        // =========================================================================
        if (usuarioLogueado instanceof Aficionado) {
            Aficionado af = (Aficionado) usuarioLogueado; // Downcasting explicito
            System.out.println("Rol detectado: AFICIONADO");
            System.out.println("Bienvenida, " + af.getNombre() + " " + af.getApellidos());
            System.out.println("Celular registrado: " + af.getCelular());
            System.out.print("¿Este número de celular es correcto? (S/N): ");
            String confirmacion = lector.nextLine().trim().toUpperCase();
            
            if (!confirmacion.equals("S")) {
                System.out.println("Verificación fallida. Por motivos de seguridad se cerrará la sesión.");
                System.out.println("\nSaliendo del sistema...");
                return;
            }
            System.out.println("Identidad confirmada.");
            menuAficionado(af, sistema, lector);

        } else if (usuarioLogueado instanceof Organizador) {
            Organizador org = (Organizador) usuarioLogueado; // Downcasting explicito
            System.out.println("Rol detectado: ORGANIZADOR");
            System.out.println("Bienvenido, " + org.getNombre() + " " + org.getApellidos());
            System.out.println("Empresa asignada: " + org.getEmpresa());
            System.out.print("¿Esta empresa es correcta? (S/N): ");
            String confirmacion = lector.nextLine().trim().toUpperCase();
            
            if (!confirmacion.equals("S")) {
                System.out.println("Verificación fallida. Por motivos de seguridad se cerrará la sesión.");
                System.out.println("\nSaliendo del sistema...");
                return;
            }
            System.out.println("Identidad confirmada.");
            menuOrganizador(org, sistema, lector);
        }
    }

    /**
     * Menu interactivo y sintonizado para usuarios de tipo Aficionado.
     */
    private static void menuAficionado(Aficionado aficionado, Sistema sistema, Scanner lec) {
        int opcion = 0;
        do {
            System.out.println("\nMenú de Aficionado:");
            System.out.println("1. Consultar partidos");
            System.out.println("2. Comprar entrada");
            System.out.println("3. Comprar kit de entradas");
            System.out.println("4. Consultar entradas");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            
            if (lec.hasNextInt()) {
                opcion = lec.nextInt();
                lec.nextLine(); // Saneamiento para vaciar el salto de linea (\n) del buffer
            } else {
                System.out.println("Error. Debe ingresar una opción numérica.");
                lec.nextLine(); // Saneamiento manual para desatascar caracteres basura
                continue;
            }

            switch (opcion) {
                case 1:
                    consultarPartidos(sistema);
                    break;
                case 2:
                    comprarEntrada(aficionado, sistema, lec);
                    break;
                case 3:
                    comprarKit(aficionado, sistema, lec);
                    break;
                case 4:
                    // Enlace dinamico: ejecuta la sobrescritura especifica de Aficionado
                    aficionado.consultarEntradas(sistema.getCompras());
                    break;
                case 5:
                    System.out.println("\nSaliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción incorrecta.");
            }
        } while (opcion != 5);
    }

    /**
     * Muestra la lista de partidos ordenados por fecha mediante la interfaz Comparable.
     */
    private static void consultarPartidos(Sistema sistema) {
        System.out.println("\nPartidos encontrados:");
        ArrayList<Partido> partidos = sistema.getPartidos();
        
        // ORDENAMIENTO CRONOLOGICO UTILIZANDO COMPARABLE
        java.util.Collections.sort(partidos);

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < partidos.size(); i++) {
            Partido p = partidos.get(i);
            System.out.println((i + 1) + ". Código: " + p.getCodigoPartido());
            System.out.println("   Partido: " + p.getSeleccionLocal() + " vs " + p.getSeleccionVisitante());
            System.out.println("   Fecha: " + formato.format(p.getFecha()));
            System.out.println("   Estadio: " + p.getEstadio());
            System.out.println("   Ciudad: " + p.getCiudad());
            System.out.println("   Fase: " + p.getFase());
            System.out.println("   Zonas disponibles:");
            System.out.println("     GENERAL      | Disponibles: " + p.getEntradasDisponiblesGeneral() + " | Precio: $" + p.getPrecioGeneral());
            System.out.println("     PREFERENCIAL | Disponibles: " + p.getEntradasDiponiblesPreferencial() + " | Precio: $" + p.getPrecioPreferencial());
            System.out.println("     VIP          | Disponibles: " + p.getEntradasDisponiblesVIP() + " | Precio: $" + p.getPrecioVIP());
            System.out.println("------------------------------------------------");
        }
    }

    /**
     * Solicita la seleccion de localidades y valida stocks para la compra individual.
     */
    private static void comprarEntrada(Aficionado aficionado, Sistema sistema, Scanner lec) {
        System.out.print("\nIngrese el código del partido: ");
        String codigoPart = lec.nextLine().trim();
        
        Partido partido = null;
        for (int i = 0; i < sistema.getPartidos().size(); i++) {
            Partido p = sistema.getPartidos().get(i);
            if (p.getCodigoPartido().equalsIgnoreCase(codigoPart)) {
                partido = p;
                break;
            }
        }
        
        if (partido == null) {
            System.out.println("Error. No se encontró ningún partido con ese código.");
            return;
        }
        
        System.out.print("Elegir la zona (GENERAL / PREFERENCIAL / VIP): ");
        String zona = lec.nextLine().trim().toUpperCase();
        
        int stockDisponible = 0;
        double precioUnitario = 0.0;
        
        if (zona.equals("GENERAL")) {
            stockDisponible = partido.getEntradasDisponiblesGeneral();
            precioUnitario = partido.getPrecioGeneral();
        } else if (zona.equals("PREFERENCIAL")) {
            stockDisponible = partido.getEntradasDiponiblesPreferencial();
            precioUnitario = partido.getPrecioPreferencial();
        } else if (zona.equals("VIP")) {
            stockDisponible = partido.getEntradasDisponiblesVIP();
            precioUnitario = partido.getPrecioVIP();
        } else {
            System.out.println("Error. Zona no válida.");
            return;
        }
        
        System.out.print("Ingresar cantidad de entradas: ");
        int cantidad = 0;
        if (lec.hasNextInt()) {
            cantidad = lec.nextInt();
            lec.nextLine(); // limpiar buffer
        } else {
            System.out.println("Error. Cantidad debe ser un número entero.");
            lec.nextLine(); // limpiar buffer
            return;
        }
        
        if (cantidad <= 0) {
            System.out.println("Error. La cantidad debe ser mayor que cero.");
            return;
        }
        
        if (cantidad > stockDisponible) {
            System.out.println("Error. No hay suficiente stock disponible. Stock actual: " + stockDisponible);
            return;
        }
        
        double total = precioUnitario * cantidad;
        System.out.println("Total a pagar: $" + total);
        
        System.out.print("Ingresar número de tarjeta: ");
        String tarjeta = lec.nextLine().trim();
        if (tarjeta.length() != 16 || !tarjeta.matches("[0-9]+") || tarjeta.equals("0000000000000000")) {
            System.out.println("Error. La tarjeta debe tener 16 dígitos numéricos y ser válida.");
            return; 
        }
        
        System.out.println("Simulando pago exitoso...");
        System.out.println("Procesando cobro...");
        System.out.println("Pago aprobado.");
        
        Compra c = sistema.comprar(aficionado, partido, zona, cantidad, tarjeta);
        System.out.println("Compra realizada exitosamente. Código de compra generado: " + c.getCodigoCompra());
    }

    /**
     * Muestra la oferta de paquetes de boletos y procesa la compra del Kit.
     */
    private static void comprarKit(Aficionado aficionado, Sistema sistema, Scanner lec) {
        System.out.println("\n===== KITS DISPONIBLES =====");
        ArrayList<Kit> kits = sistema.getKits();
        if (kits.isEmpty()) {
            System.out.println("No hay kits registrados en el sistema.");
            return;
        }
        
        for (int i = 0; i < kits.size(); i++) {
            Kit k = kits.get(i);
            System.out.println((i + 1) + ". " + k.getNombre() + " (Código: " + k.getCodigo() + ")");
            System.out.println("   Descripción: " + k.getDescripcion());
            System.out.println("   Precio: $" + k.getPrecio());
            System.out.println("   Disponibles: " + k.getDisponibles());
            System.out.println("   Partidos incluidos:");
            for (int j = 0; j < k.getPartidosIncluidos().size(); j++) {
                Partido p = k.getPartidosIncluidos().get(j);
                System.out.println("     - " + p.getSeleccionLocal() + " vs " + p.getSeleccionVisitante() + " (Estadio: " + p.getEstadio() + ", Ciudad: " + p.getCiudad() + ")");
            }
            System.out.println("------------------------------------------------");
        }
        
        System.out.print("Ingrese el código del kit que desea comprar: ");
        String codigoKit = lec.nextLine().trim();
        
        Kit kit = null;
        for (int i = 0; i < kits.size(); i++) {
            if (kits.get(i).getCodigo().equalsIgnoreCase(codigoKit)) {
                kit = kits.get(i);
                break;
            }
        }
        
        if (kit == null) {
            System.out.println("Error. No se encontró el kit seleccionado.");
            return;
        }
        
        if (kit.getDisponibles() <= 0) {
            System.out.println("Error. Este kit se encuentra agotado.");
            return;
        }
        
        System.out.print("Ingresar cantidad de kits: ");
        int cantidad = 0;
        if (lec.hasNextInt()) {
            cantidad = lec.nextInt();
            lec.nextLine(); // limpiar buffer
        } else {
            System.out.println("Error. Cantidad debe ser un número entero.");
            lec.nextLine(); // limpiar buffer
            return;
        }
        
        if (cantidad <= 0) {
            System.out.println("Error. La cantidad debe ser mayor que cero.");
            return;
        }
        
        if (cantidad > kit.getDisponibles()) {
            System.out.println("Error. No hay suficiente stock de este kit. Disponibles: " + kit.getDisponibles());
            return;
        }
        
        double total = kit.getPrecio() * cantidad;
        System.out.println("Monto total a pagar: $" + total);
        
        System.out.print("Ingresar número de tarjeta: ");
        String tarjeta = lec.nextLine().trim();
        
        System.out.println("Simulando pago exitoso...");
        System.out.println("Procesando cobro...");
        System.out.println("Pago aprobado.");
        
        Compra c = sistema.comprar(aficionado, kit, cantidad, tarjeta);
        System.out.println("Kit adquirido correctamente. Su código de compra es: " + c.getCodigoCompra());
    }

    /**
     * Menu interactivo y sintonizado para administradores de tipo Organizador.
     */
    private static void menuOrganizador(Organizador organizador, Sistema sistema, Scanner lec) {
        int opcion = 0;
        do {
            System.out.println("\nMenú de Organizador:");
            System.out.println("1. Consultar entradas");
            System.out.println("2. Generar reporte");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            
            if (lec.hasNextInt()) {
                opcion = lec.nextInt();
                lec.nextLine(); // limpiar buffer
            } else {
                System.out.println("Error. Digite una opción válida.");
                lec.nextLine(); // limpiar buffer
                continue;
            }

            switch (opcion) {
                case 1:
                    // Enlace dinamico: ejecuta la sobrescritura especifica de Organizador
                    organizador.consultarEntradas(sistema.getCompras());
                    break;
                case 2:
                    sistema.generarReporteDeVentas(organizador);
                    break;
                case 3:
                    System.out.println("\nSaliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción incorrecta.");
            }
        } while (opcion != 3);
    }
}
