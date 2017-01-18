package jose.tab.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alicia on 18/01/2017.
 */
public class Obra {

    /**
     * Nombre de la Obra
     */
    @SerializedName("nombre")
    private String nombre;

    /**
     * Nombre del Autor de la Obra
     */
    @SerializedName("autor")
    private String autor;

    /**
     * Fecha en la que se creo la obra, se denota como una fecha en años
     */
    @SerializedName("fecha_creacion")
    private String fecha_creacion;

    /**
     * Resumen sobre la obra de arte presentada
     */
    @SerializedName("resumen")
    private String resumen;

    /**
     * Tipo de obra de arte presentada, si pintura, escultura u otros
     */
    @SerializedName("tipo_obra")
    private String tipo_obra;

    /**
     * Tecnica artistica que uso el autor para crear la obra
     */
    @SerializedName("tecnica_obra")
    private String tecnica_obra;

    /**
     * Fecha de Ingreso de la obra en el museo que se muestra el año en que ocurrio tal ingreso
     */
    @SerializedName("fecha_ingreso")
    private String fecha_ingreso;

    /**
     * Nacionalidad de la obra expuesta
     */
    @SerializedName("nacionalidad")
    private String nacionalidad;

    /**
     * Dimensiones de la obra expuesta
     */
    @SerializedName("dimensiones")
    private String dimensiones;

    /**
     * Peso de la obra expuesta, irrelevante para las obras que no sean esculturas
     */
    @SerializedName("peso")
    private String peso;

    /**
     * Imagen representativa de la obra expuesta
     */
    @SerializedName("imagen")
    private String imagen;

    /**
     * Historia completa de la obra expuesta
     */
    @SerializedName("historia")
    private String historia;

    /**
     * Audio explicativo de la obra expuesta
     */
    @SerializedName("audio")
    private String audio;

    /**
     * Video explicativo de la obra expuesta
     */
    @SerializedName("video")
    private String video;

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(String fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getTecnica_obra() {
        return tecnica_obra;
    }

    public void setTecnica_obra(String tecnica_obra) {
        this.tecnica_obra = tecnica_obra;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getTipo_obra() {
        return tipo_obra;
    }

    public void setTipo_obra(String tipo_obra) {
        this.tipo_obra = tipo_obra;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
