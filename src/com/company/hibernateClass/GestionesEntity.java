package com.company.hibernateClass;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "GESTIONES", schema = "proyecthiber")
public class GestionesEntity {
    private int codigo;
    private String codproveedor;
    private String codproyecto;

    @Id
    @Column(name = "CODIGO", nullable = false)
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    @Basic
    @Column(name = "CODPROVEEDOR", nullable = false, length = 6)
    public String getCodproveedor() {
        return codproveedor;
    }

    public void setCodproveedor(String codproveedor) {
        this.codproveedor = codproveedor;
    }

    @Basic
    @Column(name = "CODPROYECTO", nullable = false, length = 6)
    public String getCodproyecto() {
        return codproyecto;
    }

    public void setCodproyecto(String codproyecto) {
        this.codproyecto = codproyecto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GestionesEntity that = (GestionesEntity) o;
        return codigo == that.codigo &&
                Objects.equals(codproveedor, that.codproveedor) &&
                Objects.equals(codproyecto, that.codproyecto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, codproveedor, codproyecto);
    }
}
