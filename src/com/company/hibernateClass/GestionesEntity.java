package com.company.hibernateClass;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "GESTIONES", schema = "sql7378678")
public class GestionesEntity {
    private int codigo;
    private int codproveedor;
    private int codproyecto;

    @Id
    @Column(name = "CODIGO", nullable = false)
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    @Basic
    @Column(name = "CODPROVEEDOR", nullable = false)
    public int getCodproveedor() {
        return codproveedor;
    }

    public void setCodproveedor(int codproveedor) {
        this.codproveedor = codproveedor;
    }

    @Basic
    @Column(name = "CODPROYECTO", nullable = false)
    public int getCodproyecto() {
        return codproyecto;
    }

    public void setCodproyecto(int codproyecto) {
        this.codproyecto = codproyecto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GestionesEntity that = (GestionesEntity) o;
        return codigo == that.codigo &&
                codproveedor == that.codproveedor &&
                codproyecto == that.codproyecto;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, codproveedor, codproyecto);
    }
}
