/**
 * Representa una transaccion de boletos individuales o paquetes (Kits).
 * Utiliza una variable estatica para la generacion secuencial autonumerica de IDs.
 * * @author Samantha Sulay Luna Malta
 * @author 
 * @version 1.0
 */
public class Compra {
    // Variable de clase estatica para autoincrementar codigos en ejecucion
    private static int contadorCompras = 0;

    private String codigoCompra;
    private String tipoAdquisicion;
    private String codigoReferencia;
    private String fechaCompra;
    private int cantidad;
    private double valorTotal;
    private String codigoComprador;

    /**
     * Constructor para compras hechas en vivo durante la sesion.
     * Autogenera el codigo CXXX utilizando la variable estatica.
     * * @param tipoAdquisicion Tipo de compra (ENTRADA o KIT).
     * @param codigoReferencia Codigo de partido o kit asociado.
     * @param fechaCompra Fecha actual del sistema.
     * @param cantidad Cantidad de boletos o paquetes adquiridos.
     * @param valorTotal Monto total cobrado.
     * @param codigoComprador Codigo del aficionado comprador.
     */
    public Compra(String tipoAdquisicion, String codigoReferencia, String fechaCompra, int cantidad, double valorTotal, String codigoComprador) {
        contadorCompras++;
        this.codigoCompra = "C" + String.format("%03d", contadorCompras);
        this.tipoAdquisicion = tipoAdquisicion;
        this.codigoReferencia = codigoReferencia;
        this.fechaCompra = fechaCompra;
        this.cantidad = cantidad;
        this.valorTotal = valorTotal;
        this.codigoComprador = codigoComprador;
    }

    /**
     * Constructor para compras cargadas historicamente desde el archivo de texto compras.txt.
     * No altera destructivamente el contador secuencial global.
     * * @param codigoCompra Codigo leido del archivo.
     * @param tipoAdquisicion Tipo de compra.
     * @param codigoReferencia Referencia asociada.
     * @param fechaCompra Fecha registrada.
     * @param cantidad Cantidad de unidades.
     * @param valorTotal Valor total pagado.
     * @param codigoComprador Codigo del comprador.
     */
    public Compra(String codigoCompra, String tipoAdquisicion, String codigoReferencia, String fechaCompra, int cantidad, double valorTotal, String codigoComprador) {
        this.codigoCompra = codigoCompra;
        this.tipoAdquisicion = tipoAdquisicion;
        this.codigoReferencia = codigoReferencia;
        this.fechaCompra = fechaCompra;
        this.cantidad = cantidad;
        this.valorTotal = valorTotal;
        this.codigoComprador = codigoComprador;

        // Sincroniza el contador estatico de forma dinamica al leer el archivo
        try {
            int numExtraido = Integer.parseInt(codigoCompra.replaceAll("[^0-9]", ""));
            if (numExtraido > contadorCompras) {
                contadorCompras = numExtraido;
            }
        } catch (Exception e) {
            // Se ignora el fallo si el ID no es estrictamente numerico
        }
    }

    public String getCodigoCompra() { return codigoCompra; }
    public String getTipoAdquisicion() { return tipoAdquisicion; }
    public String getCodigoReferencia() { return codigoReferencia; }
    public String getFechaCompra() { return fechaCompra; }
    public int getCantidad() { return cantidad; }
    public double getValorTotal() { return valorTotal; }
    public String getCodigoAficionado() { return codigoComprador; }

    /**
     * Devuelve la informacion de la compra con formato para persistencia.
     * * @return Cadena formateada para escritura directa en compras.txt.
     */
    @Override
    public String toString() {
        return codigoCompra + "|" + tipoAdquisicion + "|" + codigoReferencia + "|" + fechaCompra + "|" + cantidad + "|" + valorTotal + "|" + codigoComprador;
    }
}
