package com.company.hibernateClass;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PIEZASGESTIONES", schema = "sql7378678")
public class PiezasgestionesEntity {
    private int codigo;
    private int codpieza;
    private int codgestion;

    @Id
    @Column(name = "CODIGO", nullable = false)
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    @Basic
    @Column(name = "CODPIEZA", nullable = false)
    public int getCodpieza() {
        return codpieza;
    }

    public void setCodpieza(int codpieza) {
        this.codpieza = codpieza;
    }

    @Basic
    @Column(name = "CODGESTION", nullable = false)
    public int getCodgestion() {
        return codgestion;
    }

    public void setCodgestion(int codgestion) {
        this.codgestion = codgestion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PiezasgestionesEntity that = (PiezasgestionesEntity) o;
        return codigo == that.codigo &&
                codpieza == that.codpieza &&
                codgestion == that.codgestion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, codpieza, codgestion);
    }
}
