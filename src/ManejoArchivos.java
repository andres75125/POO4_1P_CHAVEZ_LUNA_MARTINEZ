import java.io.*;
import java.util.ArrayList;

/**
 * Clase utilitaria provista para la lectura y escritura de ficheros planos (.txt).
 * * @author Jorge Andres Martinez Gutierrez
 * @author
 * @version 1.0
 */
public class ManejoArchivos {

    /**
     * Lee un archivo de texto plano y retorna su contenido linea por linea en un ArrayList.
     * * @param nombreArchivo Ruta o nombre del archivo en disco.
     * @return ArrayList de String conteniendo las lineas encontradas.
     */
    public static ArrayList<String> LeeFichero(String nombreArchivo) {
        ArrayList<String> lineas = new ArrayList<>();
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            return lineas;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
        } catch (IOException e) {
            System.out.println("Fallo critico al procesar el archivo: " + nombreArchivo);
        }
        return lineas;
    }

    /**
     * Escribe una nueva linea de texto al final del archivo especificado sin sobreescribir el contenido previo.
     * * @param nombreArchivo Nombre del archivo en disco.
     * @param linea Contenido de texto que se va a guardar.
     */
    public static void EscribirArchivo(String nombreArchivo, String linea) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo, true))) {
            bw.write(linea);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al intentar escribir en: " + nombreArchivo);
        }
    }
}