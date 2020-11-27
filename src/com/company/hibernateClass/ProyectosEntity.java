package com.company.hibernateClass;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PROYECTOS", schema = "sql7378678")
public class ProyectosEntity {
    private int codigo;
    private String nombre;
    private String ciudad;

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
    @Column(name = "CIUDAD", nullable = false, length = 25)
    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProyectosEntity that = (ProyectosEntity) o;
        return codigo == that.codigo &&
                Objects.equals(nombre, that.nombre) &&
                Objects.equals(ciudad, that.ciudad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, nombre, ciudad);
    }
}
