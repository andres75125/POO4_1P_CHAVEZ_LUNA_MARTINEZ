import java.util.Date;

public class Partido{
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

    public Partido(String codigoPartido, String seleccionLocal, String seleccionVisitante, Date fecha, 
        String estadio, String ciudad, int capacidad, int entradasDisponiblesGeneral, int entradasDisponiblesPreferencial,
        int entradasDisponiblesVIP, String fase ){
            this.codigoPartido=codigoPartido;
            this.seleccionLocal=seleccionLocal;
            this.seleccionVisitante=seleccionVisitante;
            this.fecha=fecha;
            this.estadio=estadio;
            this.ciudad=ciudad;
            this.capacidad=capacidad;
            this.entradasDisponiblesGeneral=entradasDisponiblesGeneral;
            this.entradasDisponiblesPreferencial=entradasDisponiblesPreferencial;
            this.entradasDisponiblesVIP=entradasDisponiblesVIP;
            this.fase=fase;
    }

    //Metodos getters
    public String getCodigoPartido(){
        return codigoPartido;
    }
    public String getSeleccionLocal(){
        return seleccionLocal;
    }
    public String getSeleccionVisitante(){
        return seleccionVisitante;
    }
    public Date getFecha(){
        return fecha;
    }
    public String getEstadio(){
        return estadio;
    }
    public String getCiudad(){
        return ciudad;
    }
    public int getCapacidad(){
        return capacidad;
    }
    public int getEntradasDisponiblesGeneral(){
        return entradasDisponiblesGeneral;
    }
    public int getEntradasDiponiblesPreferencial(){
        return entradasDisponiblesPreferencial;
    }
    public int getEntradasDisponiblesVIP(){
        return entradasDisponiblesVIP;
    }
    public String getFase(){
        return fase;
    }

    //Metodos Setters
    public void setCodigoPartido(String codigoPartido){
        this.codigoPartido=codigoPartido;
    }
    public void setSelecccionLocal(String seleccionLocal){
        this.seleccionLocal=seleccionLocal;
    }
    public void setSeleccionVisitante(String seccionVisitante){
        this.seleccionVisitante=seccionVisitante;
    }
    public void setFecha(Date fecha){
        this.fecha=fecha;
    }
    public void setEstadio(String estadio){
        this.estadio=estadio;
    }
    public void setCiudad(String ciudad){
        this.ciudad=ciudad;
    }
    public void setCapacidad(int capacidad){
        this.capacidad=capacidad;
    }
    public void setEntradasDisponiblesGeneral(int entradasDisponiblesGeneral){
        this.entradasDisponiblesGeneral=entradasDisponiblesGeneral;
    }
    public void setEntrasdaDisponiblesPreferencial(int entradasDisponiblesPreferencial){
        this.entradasDisponiblesPreferencial=entradasDisponiblesPreferencial;
    }
    public void setEntradasDisponibleVIP(int entradasDisponiblesVIP){
        this.entradasDisponiblesVIP=entradasDisponiblesVIP;
    }
    public void setFase(String fase){
        this.fase=fase;
    }
}