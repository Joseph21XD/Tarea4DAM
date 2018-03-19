package enigma.mymovies;

import android.graphics.Bitmap;

/**
 * Created by ramir on 3/18/2018.
 */

public class Movie {
    private String nombre;
    private String descripcion;
    private Bitmap imagen;
    private double imdbRate;
    private int metascore;

    public Movie(String name, Bitmap image, double rate, int score, String desc){
        nombre=name;
        imagen=image;
        imdbRate= rate;
        metascore=score;
        descripcion= desc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getImdbRate() {
        return imdbRate;
    }

    public void setImdbRate(double imdbRate) {
        this.imdbRate = imdbRate;
    }

    public Bitmap getImagen() {

        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public int getMetascore() {
        return metascore;
    }

    public void setMetascore(int metascore) {
        this.metascore = metascore;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
