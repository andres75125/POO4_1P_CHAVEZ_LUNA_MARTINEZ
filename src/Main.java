/**
 * =============================================================================
 * Proyecto: Sistema de Venta y Gestión de Entradas para el Mundial 2026
    Clase principal que ejecuta la interfaz por consola del sistema,
 * controla el flujo de autenticación, la presentación dinámica
 * de menús por rol (Aficionado/Organizador) y la simulación
 * interactiva de adquisición de entradas con alertas reales por correo.
 * =============================================================================
 */

import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    
    public static void main(String[] args) {
        // Inicializamos el Scanner para capturar las entradas de teclado del usuario
        Scanner lector = new Scanner(System.in);
        
        // Instanciamos el controlador general del sistema para cargar la persistencia de archivos planos (.txt)
        Sistema sistema = new Sistema();

        System.out.println("=================================================");
        System.out.println("   BIENVENIDO AL SISTEMA DE RESERVAS - MUNDIAL   ");
        System.out.println("=================================================");
        
        // --- TAREA DEL SISTEMA: INICIAR SESIÓN (REQUERIMIENTO PUNTO 4) ---
        System.out.print("👉 Ingrese su cuenta de usuario: ");
        String cuentaInput = lector.nextLine();
        System.out.print("👉 Ingrese su contraseña de acceso: ");
        String claveInput = lector.nextLine();

        // Validamos las credenciales recorriendo la lista polimórfica de usuarios cargados
        Usuario usuarioLogueado = sistema.autenticarUsuario(cuentaInput, claveInput);

        // Control de flujo en caso de que el login falle
        if (usuarioLogueado == null) {
            System.out.println("\n❌ [ERROR] Credenciales incorrectas o usuario no registrado.");
            System.out.println("Finalizando la ejecución del programa de manera segura.");
            return;
        }

        // Mensaje personalizado de éxito usando el método heredado getNombre()
        System.out.println("\n✅ [ACCESO CONCEDIDO]");
        System.out.println("Bienvenido/a al portal: " + usuarioLogueado.getNombre() + " " + usuarioLogueado.getApellidos());

        // --- TAREA DEL SISTEMA: MOSTRAR MENÚ SEGÚN EL ROL (REQUERIMIENTO PUNTO 5) ---
        // Aplicamos el uso de Enums (Rol.AFICIONADO / Rol.ORGANIZADOR) y herencia para derivar al menú correcto
        if (usuarioLogueado.getRol() == Rol.AFICIONADO) {
            // Hacemos un casting seguro hacia la clase hija Aficionado para acceder a su comportamiento específico
            menuAficionado((Aficionado) usuarioLogueado, sistema, lector);
        } else if (usuarioLogueado.getRol() == Rol.ORGANIZADOR) {
            // Hacemos un casting seguro hacia la clase hija Organizador
            menuOrganizador((Organizador) usuarioLogueado, sistema, lector);
        }
    }

    /**
     * Módulo que administra las operaciones exclusivas del Aficionado.
     * Desarrollado siguiendo pautas de diseño modular para evitar bloques extensos de código.
     */
    private static void menuAficionado(Aficionado aficionado, Sistema sistema, Scanner lec) {
        int opcion = 0;
        
        // Bucle clásico de control para mantener el menú interactivo activo hasta que decida salir
        do {
            System.out.println("\n-------------------------------------------");
            System.out.println("         🎯 MENÚ DE AFICIONADO             ");
            System.out.println("-------------------------------------------");
            System.out.println("1. Consultar listado de partidos");
            System.out.println("2. Adquirir entradas individuales");
            System.out.println("3. Salir de la cuenta");
            System.out.print("👉 Seleccione una acción (1-3): ");
            
            // Validamos que la entrada sea de tipo numérico para evitar excepciones inesperadas de ejecución
            if (lec.hasNextInt()) {
                opcion = lec.nextInt();
                lec.nextLine(); // Limpieza obligatoria del buffer del Scanner para remover el carácter de salto de línea (\n)
            } else {
                System.out.println("\n❌ [ERROR] Por favor, ingrese un número válido de opción.");
                lec.nextLine(); // Saneamiento del buffer en caso de entrada de texto basura
                continue;
            }

            switch (opcion) {
                case 1:
                    // --- REQUERIMIENTO: CONSULTAR PARTIDOS ---
                    System.out.println("\n==========================================");
                    System.out.println("         PARTIDOS DISPONIBLES             ");
                    System.out.println("==========================================");
                    
                    // Recorremos la lista dinâmica de partidos obtenida desde el controlador
                    for (Partido part : sistema.getPartidos()) {
                        System.out.println("⚽ Código: " + part.getCodigoPartido());
                        System.out.println("   Encuentro: " + part.getSeleccionLocal() + " vs " + part.getSeleccionVisitante());
                        System.out.println("   Sede: " + part.getCiudad() + " | Estadio: " + part.getEstadio());
                        System.out.println("   Localidades disponibles:");
                        System.out.println("     - General: " + part.getEntradasDisponiblesGeneral());
                        System.out.println("     - Preferencial: " + part.getEntradasDiponiblesPreferencial());
                        System.out.println("     - VIP: " + part.getEntradasDisponiblesVIP());
                        System.out.println("------------------------------------------");
                    }
                    break;

                case 2:
                    // --- REQUERIMIENTO: COMPRAR ENTRADAS ---
                    System.out.print("\n📋 Ingrese el código identificador del partido (ej. M001): ");
                    String codigoPart = lec.nextLine().trim();
                    
                    // Buscamos el partido seleccionado dentro del ArrayList mediante iteración clásica
                    Partido partidoElegido = null;
                    for (Partido p : sistema.getPartidos()) {
                        if (p.getCodigoPartido().equalsIgnoreCase(codigoPart)) {
                            partidoElegido = p;
                            break;
                        }
                    }

                    // Validación en caso de ingresar un identificador erróneo
                    if (partidoElegido == null) {
                        System.out.println("\n❌ [ERROR] El código de partido ingresado no existe en nuestros registros.");
                        break;
                    }

                    System.out.print("📋 Seleccione la localidad (GENERAL / PREFERENCIAL / VIP): ");
                    String zona = lec.nextLine().trim().toUpperCase();
                    
                    System.out.print("📋 Ingrese la cantidad de boletos que desea adquirir: ");
                    int cantidadBoletos = 0;
                    
                    if (lec.hasNextInt()) {
                        cantidadBoletos = lec.nextInt();
                        lec.nextLine(); // Saneamiento del buffer posterior a la captura de entero
                    } else {
                        System.out.println("\n❌ [ERROR] La cantidad ingresada debe ser un número entero.");
                        lec.nextLine();
                        break;
                    }

                    // Evaluamos la disponibilidad actual de la zona deseada utilizando los Getters encapsulados
                    int asientosDisponibles = 0;
                    if (zona.equals("GENERAL")) {
                        asientosDisponibles = partidoElegido.getEntradasDisponiblesGeneral();
                    } else if (zona.equals("PREFERENCIAL")) {
                        asientosDisponibles = partidoElegido.getEntradasDiponiblesPreferencial();
                    } else if (zona.equals("VIP")) {
                        asientosDisponibles = partidoElegido.getEntradasDisponiblesVIP();
                    } else {
                        System.out.println("\n❌ [ERROR] Tipo de localidad no reconocida.");
                        break;
                    }

                    // Bloque de validación de existencias
                    if (cantidadBoletos > asientosDisponibles || cantidadBoletos <= 0) {
                        System.out.println("\n❌ [TRANSACCIÓN RECHAZADA] No contamos con suficientes cupos en esa localidad.");
                    } else {
                        // Determinamos el precio estipulado por el modelo para calcular la transacción
                        double precioAsiento = 50.0; 
                        
                        if (zona.equals("GENERAL")) {
                            // Modificamos el stock usando el setter encapsulado
                            partidoElegido.setEntradasDisponiblesGeneral(asientosDisponibles - cantidadBoletos);
                            precioAsiento = 45.00;
                        } else if (zona.equals("PREFERENCIAL")) {
                            partidoElegido.setEntrasdaDisponiblesPreferencial(asientosDisponibles - cantidadBoletos);
                            precioAsiento = 85.00;
                        } else if (zona.equals("VIP")) {
                            partidoElegido.setEntradasDisponibleVIP(asientosDisponibles - cantidadBoletos);
                            precioAsiento = 150.00;
                        }

                        double totalPagar = precioAsiento * cantidadBoletos;
                        System.out.println("\n💰 [TOTAL CALCULADO]: $" + totalPagar);
                        System.out.println("💳 Procesando cobro virtual...");
                        System.out.println("✅ Pago aprobado de forma exitosa.");

                        // Generamos un código único aleatorio de compra simulando el comportamiento del backend
                        String numeroCompra = "C" + (int)(Math.random() * 8999 + 1000);
                        
                        // Instanciamos el objeto de tipo Compra
                        Compra nuevaCompra = new Compra(
                            numeroCompra, "INDIVIDUAL", partidoElegido.getCodigoPartido(), 
                            "2026-07-12", cantidadBoletos, totalPagar, aficionado.getCodigoUnico()
                        );
                        
                        // Guardamos físicamente la compra en compras.txt usando la persistencia delegada
                        sistema.registrarCompra(nuevaCompra);

                        System.out.println("\n🎉 ¡Adquisición guardada con éxito! Registro ID: " + numeroCompra);
                        
                        // --- INVESTIGACIÓN DE NOTIFICACIÓN DE CORREO REAL (REQUERIMIENTO DEL DOC) ---
                        // Usamos el ServicioCorreo que emplea la API nativa de java.awt.Desktop para mailto
                        String asuntoEmail = "Confirmacion de Reserva - Entrada Mundial 2026";
                        String cuerpoEmail = "Estimado/a " + aficionado.getNombre() + ",\n\n" +
                                            "Confirmamos su compra con identificador: " + numeroCompra + ".\n" +
                                            "Detalle: " + cantidadBoletos + " boleto(s) para la zona " + zona + ".\n" +
                                            "Evento: " + partidoElegido.getSeleccionLocal() + " vs " + partidoElegido.getSeleccionVisitante() + ".\n\n" +
                                            "¡Muchas gracias por confiar en nuestro portal de reservas!";
                        
                        // Lanzamos el gestor predeterminado de correos de la máquina cliente
                        ServicioCorreo.enviarCorreoReal(aficionado.getCorreo(), asuntoEmail, cuerpoEmail);
                    }
                    break;

                case 3:
                    System.out.println("\nCerrando sesión del aficionado de manera segura...");
                    break;

                default:
                    System.out.println("\n❌ Opcion inválida. Ingrese un valor comprendido en el menú.");
            }
        } while (opcion != 3);
    }

    /**
     * Módulo que administra las operaciones exclusivas de los Organizadores del evento.
     */
    private static void menuOrganizador(Organizador organizador, Sistema sistema, Scanner lec) {
        int opcionOp = 0;
        do {
            System.out.println("\n-------------------------------------------");
            System.out.println("         💼 MENÚ DE ORGANIZADOR            ");
            System.out.println("-------------------------------------------");
            System.out.println("1. Monitorear inventario de estadios");
            System.out.println("2. Salir de la cuenta administrativa");
            System.out.print("👉 Seleccione una opción: ");
            
            if (lec.hasNextInt()) {
                opcionOp = lec.nextInt();
                lec.nextLine();
            } else {
                System.out.println("\n❌ [ERROR] Entrada numérica inválida.");
                lec.nextLine();
                continue;
            }

            if (opcionOp == 1) {
                System.out.println("\n==========================================");
                System.out.println("     BALANCE DE LOCALIDADES EN ESTADIOS   ");
                System.out.println("==========================================");
                
                // Recorremos los partidos para imprimir un log gerencial directo
                for (Partido part : sistema.getPartidos()) {
                    System.out.println("Encuentro [" + part.getCodigoPartido() + "] " + 
                                       part.getSeleccionLocal() + " vs " + part.getSeleccionVisitante() + 
                                       " -> Entradas Generales Libres: " + part.getEntradasDisponiblesGeneral());
                }
            } else if (opcionOp != 2) {
                System.out.println("\n❌ Opción incorrecta.");
            }
        } while (opcionOp != 2);
        
        System.out.println("\nCerrando sesión administrativa. Conexión finalizada.");
    }
}