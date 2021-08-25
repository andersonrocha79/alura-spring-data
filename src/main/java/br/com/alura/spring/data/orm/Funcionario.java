package br.com.alura.spring.data.orm;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tbFuncionario")
public class Funcionario
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    id;

    private String  nome;

    private String  cpf;

    private Double  salario;

    private LocalDate dataContratacao;

    // funcionário tem 1 cargo // nome do campo que será criado na base de dados: cargo_id
    @ManyToOne
    @JoinColumn(name = "cargo_id", nullable = false)
    private Cargo cargo;

    // funcionário pode trabalhar em mais de uma unidade de trabalho
    // nova tabela para relacionamento muitos para muitos
    @Fetch(FetchMode.SELECT)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="tb_funcionario_unidades_trabalho", // nome da tabela que será criada para manter o join 'many to many'
            joinColumns = {@JoinColumn(name="fk_funcionario")},
            inverseJoinColumns = {@JoinColumn(name="fk_unidade")})
    private List<UnidadeTrabalho> unidadeTrabalhos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public LocalDate getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(LocalDate dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public List<UnidadeTrabalho> getUnidadeTrabalhos() {
        return unidadeTrabalhos;
    }

    public void setUnidadeTrabalhos(List<UnidadeTrabalho> unidadeTrabalhos) {
        this.unidadeTrabalhos = unidadeTrabalhos;
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", salario=" + salario +
                ", dataContratacao=" + dataContratacao +
                ", cargo=" + cargo +
                ", unidadeTrabalhos=" + unidadeTrabalhos +
                '}';
    }
}
