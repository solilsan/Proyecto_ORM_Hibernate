package com.company.hibernateClass;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "GESTIONESPIEZAS", schema = "proyecthiber")
public class GestionespiezasEntity {
    private int codigo;
    private String codpieza;
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
    @Column(name = "CODPIEZA", nullable = false, length = 6)
    public String getCodpieza() {
        return codpieza;
    }

    public void setCodpieza(String codpieza) {
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
        GestionespiezasEntity that = (GestionespiezasEntity) o;
        return codigo == that.codigo &&
                codgestion == that.codgestion &&
                Objects.equals(codpieza, that.codpieza);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, codpieza, codgestion);
    }
}
