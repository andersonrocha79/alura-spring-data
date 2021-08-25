package br.com.alura.spring.data.orm;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="tbCargo")
public class Cargo
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String  descricao;
    private Double  salarioPadrao;

    @OneToMany(mappedBy = "cargo")
    private List<Funcionario> funcionarios;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getSalarioPadrao() {
        return salarioPadrao;
    }

    public void setSalarioPadrao(Double salarioPadrao) {
        this.salarioPadrao = salarioPadrao;
    }

    @Override
    public String toString() {
        return "Cargo{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", salarioPadrao=" + salarioPadrao +
                '}';
    }
}

