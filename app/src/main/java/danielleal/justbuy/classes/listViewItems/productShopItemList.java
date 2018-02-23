package danielleal.justbuy.classes.listViewItems;

/**
 * Created by Dani on 21/02/2018.
 */

public class productShopItemList {
    int idProducto;
    byte[] imagen;
    String nombre,descripcion,categoria;
    double precio;

    public productShopItemList(int idProducto, byte[] imagen, String nombre, String descripcion, String categoria, double precio) {
        this.idProducto = idProducto;
        this.imagen = imagen;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
