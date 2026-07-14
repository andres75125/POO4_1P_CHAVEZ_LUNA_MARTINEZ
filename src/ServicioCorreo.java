import java.awt.Desktop;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Servicio encargado de gestionar el envio asincrono de correos electronicos reales.
 * Utiliza la API nativa Desktop mediante el protocolo mailto.
 * * @author Jorge Andres Martinez Gutierrez
 */
public class ServicioCorreo {

    /**
     * Despliega la aplicacion nativa de correos del sistema operativo del usuario con los campos prellenados.
     * * @param destinatario Direccion de correo electronico destino.
     * @param asunto Tema del correo electronico.
     * @param cuerpo Contenido textual estructurado del correo.
     */
    public static void enviarCorreoReal(String destinatario, String asunto, String cuerpo) {
        try {
            // Saneamiento de caracteres especiales mediante codificacion URL
            String asuntoProcesado = URLEncoder.encode(asunto, StandardCharsets.UTF_8.toString()).replace("+", "%20");
            String cuerpoProcesado = URLEncoder.encode(cuerpo, StandardCharsets.UTF_8.toString()).replace("+", "%20");

            String rutaMailto = "mailto:" + destinatario + "?subject=" + asuntoProcesado + "&body=" + cuerpoProcesado;

            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.MAIL)) {
                Desktop.getDesktop().mail(new URI(rutaMailto));
                System.out.println("[CORREO] Desplegando gestor de correo predeterminado...");
            } else {
                System.out.println("\n===== NOTIFICACION DE ENTRADAS (Simulada en Consola) =====");
                System.out.println("Destinatario: " + destinatario);
                System.out.println("Asunto: " + asunto);
                System.out.println("Cuerpo:\n" + cuerpo);
                System.out.println("==========================================================");
            }
        } catch (Exception e) {
            System.out.println("Error en la capa de comunicacion por correo nativo: " + e.getMessage());
        }
    }
}
