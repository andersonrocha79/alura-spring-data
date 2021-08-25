package br.com.alura.spring.data.orm;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tbUnidadeTrabalho")
public class UnidadeTrabalho
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    id;

    private String  descricao;

    private String  endereco;

    // unidadeTrabalhos > nome do campo que representa o relacionamento na classe 'Funcinonario'
    @ManyToMany(mappedBy = "unidadeTrabalhos", fetch = FetchType.EAGER)
    private List<Funcionario> funcionarios;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "UnidadeTrabalho{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", endereco='" + endereco + '\'' +
                '}';
    }
}
