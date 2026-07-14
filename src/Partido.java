import java.util.Date;

/**
 * Representa la informacion de un partido de futbol del Mundial.
 * Implementa la interfaz Comparable para permitir el ordenamiento cronologico.
 * * @author Jorge Andres Martinez Gutierrez
 * @author 
 * @version 1.0
 */
public class Partido implements Comparable<Partido> {
    private String codigoPartido;
    private String seleccionLocal;
    private String seleccionVisitante;
    private Date fecha;
    private String estadio;
    private String ciudad;
    private int capacidad;
    private int entradasDisponiblesGeneral;
    private int entradasDisponiblesPreferencial;
    private int entradasDisponiblesVIP;
    private String fase;
    private double precioGeneral;
    private double precioPreferencial;
    private double precioVIP;

    /**
     * Constructor completo para instanciar un Partido leyendo precios desde archivo.
     * * @param codigoPartido Identificador unico del partido.
     * @param seleccionLocal Pais local.
     * @param seleccionVisitante Pais visitante.
     * @param fecha Fecha y hora del partido (Objeto Date).
     * @param estadio Estadio donde se juega.
     * @param ciudad Ciudad sede.
     * @param capacidad Aforo maximo.
     * @param general Entradas disponibles en zona General.
     * @param preferencial Entradas disponibles en zona Preferencial.
     * @param vip Entradas disponibles en zona VIP.
     * @param fase Fase del torneo.
     * @param precioGeneral Precio de la zona General.
     * @param precioPreferencial Precio de la zona Preferencial.
     * @param precioVIP Precio de la zona VIP.
     */
    public Partido(String codigoPartido, String seleccionLocal, String seleccionVisitante, Date fecha, 
                   String estadio, String ciudad, int capacidad, int general, int preferencial, int vip, 
                   String fase, double precioGeneral, double precioPreferencial, double precioVIP) {
        this.codigoPartido = codigoPartido;
        this.seleccionLocal = seleccionLocal;
        this.seleccionVisitante = seleccionVisitante;
        this.fecha = fecha;
        this.estadio = estadio;
        this.ciudad = ciudad;
        this.capacidad = capacidad;
        this.entradasDisponiblesGeneral = general;
        this.entradasDisponiblesPreferencial = preferencial;
        this.entradasDisponiblesVIP = vip;
        this.fase = fase;
        this.precioGeneral = precioGeneral;
        this.precioPreferencial = precioPreferencial;
        this.precioVIP = precioVIP;
    }

    /**
     * Compara este partido con otro por medio de su fecha de manera cronologica.
     * Implementa el metodo compareTo de la interfaz Comparable.
     * * @param otro El otro partido a comparar.
     * @return Valor numerico indicador de la relacion de orden cronologico.
     */
    @Override
    public int compareTo(Partido otro) {
        return this.fecha.compareTo(otro.getFecha());
    }

    // Getters y Setters seguros para cumplir con el Encapsulamiento
    public String getCodigoPartido() { return codigoPartido; }
    public String getSeleccionLocal() { return seleccionLocal; }
    public String getSeleccionVisitante() { return seleccionVisitante; }
    public Date getFecha() { return fecha; }
    public String getEstadio() { return estadio; }
    public String getCiudad() { return ciudad; }
    public int getCapacidad() { return capacidad; }
    
    public int getEntradasDisponiblesGeneral() { return entradasDisponiblesGeneral; }
    public void setEntradasDisponiblesGeneral(int general) { this.entradasDisponiblesGeneral = general; }

    public int getEntradasDiponiblesPreferencial() { return entradasDisponiblesPreferencial; }
    public void setEntrasdaDisponiblesPreferencial(int preferencial) { this.entradasDisponiblesPreferencial = preferencial; }

    public int getEntradasDisponiblesVIP() { return entradasDisponiblesVIP; }
    public void setEntradasDisponibleVIP(int vip) { this.entradasDisponiblesVIP = vip; }

    public String getFase() { return fase; }
    public double getPrecioGeneral() { return precioGeneral; }
    public double getPrecioPreferencial() { return precioPreferencial; }
    public double getPrecioVIP() { return precioVIP; }
}