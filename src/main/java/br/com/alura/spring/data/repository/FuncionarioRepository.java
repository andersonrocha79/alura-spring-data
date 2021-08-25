package br.com.alura.spring.data.repository;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;
import br.com.alura.spring.data.orm.UnidadeTrabalho;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FuncionarioRepository extends PagingAndSortingRepository<Funcionario, Long>, JpaSpecificationExecutor<Funcionario>
{

    // pesquisa funcionário
    // nome = X
    List<Funcionario> findByNome(String nome);

    // novo método com paginação
    List<Funcionario> findByNome(String nome, Pageable pageable);

    // pesquisa funcionário
    // nome             = ?1
    // salário          > ?2
    // data contratação = ?3
    // Derived Query Methods
    List<Funcionario> findByNomeAndSalarioGreaterThanAndDataContratacao(String nome, Double salario, LocalDate data);

    // jpql (faz pesquisa 'sql' utilizando os campos da classe)
    @Query("SELECT f FROM Funcionario f" +
           " WHERE f.nome = :nome"       +
           " AND f.salario >= :salario"  +
           " AND f.dataContratacao = :data")
    List<Funcionario> findNomeDataContratacaoSalarioMaior(String nome, Double salario, LocalDate data);

    // buscando pela descrição do cargo do funcionário (campo cargo > campo descricao)
    List<Funcionario> findByCargoDescricao(String descricao);

    // utilizando a mesma pesquisa com jpql
    @Query("SELECT f FROM Funcionario f JOIN f.cargo c WHERE c.descricao = :descricao")
    List<Funcionario> findByCargoPelaDescricao(String descricao);

    // pesquisando pela descrição da unidade de trabalho (para tabelas com nomes compostos, temos que incluir o _ )
    List<Funcionario> findByUnidadeTrabalhos_Descricao(String descricao);

    // mesma pesquisa com jpql
    @Query("SELECT f FROM Funcionario f JOIN f.unidadeTrabalhos u WHERE u.descricao = :descricao")
    List<Funcionario> findByUnidadeTrabalhos_Descricao_jpql(String descricao);

    // consulta utilizando a query nativa do mariaDB
    @Query( value = "SELECT * FROM tb_funcionario f" +
                    " WHERE f.data_contratacao >= :data",
            nativeQuery = true)
    List<Funcionario> findDataContratacaoMaiorQue(LocalDate data);

    @Query(value = "SELECT f.id, f.nome, f.salario FROM tb_funcionario f",
           nativeQuery = true)
    List<FuncionarioProjecao> findFuncionarioSalario();

}
