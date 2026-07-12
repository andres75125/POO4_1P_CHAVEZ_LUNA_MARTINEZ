import java.awt.Desktop;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ServicioCorreo {

    public static void enviarCorreoReal(String destinatario, String asunto, String cuerpo) {
        try {
            // Convertimos los espacios y caracteres especiales a formato URL seguro
            String asuntoProcesado = URLEncoder.encode(asunto, StandardCharsets.UTF_8.toString()).replace("+", "%20");
            String cuerpoProcesado = URLEncoder.encode(cuerpo, StandardCharsets.UTF_8.toString()).replace("+", "%20");

            // Construimos el enlace con el protocolo mailto
            String rutaMailto = "mailto:" + destinatario + "?subject=" + asuntoProcesado + "&body=" + cuerpoProcesado;

            // Verificamos si el sistema operativo soporta la accion de abrir el correo
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.MAIL)) {
                Desktop.getDesktop().mail(new URI(rutaMailto));
                System.out.println("📬 Abriendo el gestor de correo del sistema operativo para: " + destinatario);
            } else {
                System.out.println("❌ El entorno actual no soporta el envío automatizado por Desktop.");
            }
        } catch (Exception e) {
            System.out.println("❌ No se pudo desplegar la interfaz de correo: " + e.getMessage());
        }
    }
}
