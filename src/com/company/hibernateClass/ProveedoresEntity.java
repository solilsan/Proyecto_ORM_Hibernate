package com.company.hibernateClass;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PROVEEDORES", schema = "sql7378678")
public class ProveedoresEntity {
    private int codigo;
    private String nombre;
    private String apellidos;
    private String direccion;

    @Id
    @Column(name = "CODIGO", nullable = false)
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    @Basic
    @Column(name = "NOMBRE", nullable = false, length = 25)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Basic
    @Column(name = "APELLIDOS", nullable = false, length = 50)
    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    @Basic
    @Column(name = "DIRECCION", nullable = false, length = 50)
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProveedoresEntity that = (ProveedoresEntity) o;
        return codigo == that.codigo &&
                Objects.equals(nombre, that.nombre) &&
                Objects.equals(apellidos, that.apellidos) &&
                Objects.equals(direccion, that.direccion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, nombre, apellidos, direccion);
    }
}
