package br.com.alura.spring.data.service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@Service
public class RelatoriosService
{

    private Boolean system = true;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final FuncionarioRepository funcionarioRepository;

    public RelatoriosService(FuncionarioRepository funcionarioRepository)
    {
        this.funcionarioRepository = funcionarioRepository;
    }

    public void inicial(Scanner scanner)
    {

        while (system)
        {

            System.out.println("------------------------------------------------");
            System.out.println("PESQUISAS E RELATÓRIOS:");
            System.out.println("------------------------------------------------");
            System.out.println("0 - SAIR");
            System.out.println("1 - BUSCA FUNCIONARIO POR NOME");
            System.out.println("2 - BUSCA FUNCIONARIO POR NOME, DATA DE CONTRATACAO E SALARIO MAIOR QUE 'X'");
            System.out.println("3 - BUSCA FUNCIONARIO COM DATA DE CONTRATACAO IGUAL OU SUPERIOR A 'X'");
            System.out.println("4 - BUSCA FUNCIONARIOS E SALÁRIOS ");

            int action = scanner.nextInt();

            switch (action)
            {
                case 1:
                    buscaFuncionarioPorNome(scanner);
                    break;

                case 2:
                    buscaFuncionarioNomeSalarioMaiorData(scanner);
                    break;

                case 3:
                    buscaFuncionarioDataContratacaoPosteriorA(scanner);
                    break;

                case 4:
                    buscaFuncionarioSalario();
                    break;

                default:
                    system = false;
                    break;
            }

        }

    }

    private void buscaFuncionarioPorNome(Scanner scanner)
    {

        System.out.println("Qual o nome do funcionário:");
        String nome = scanner.next();

        List<Funcionario> lista = funcionarioRepository.findByNome(nome);

        lista.forEach(System.out::println);

    }

    private void buscaFuncionarioNomeSalarioMaiorData(Scanner scanner)
    {

        System.out.println("Qual o nome do funcionário:");
        String nome = scanner.next();

        System.out.println("Qual a data da contratação (formato: dd/mm/yyyy):");
        String data = scanner.next();
        LocalDate dataContratacao = LocalDate.parse(data, formatter);

        System.out.println("Qual salário deseja pesquisar:");
        Double salario = scanner.nextDouble();

        List<Funcionario> lista = funcionarioRepository.findByNomeAndSalarioGreaterThanAndDataContratacao(nome, salario, dataContratacao);

        lista.forEach(System.out::println);

    }

    private void buscaFuncionarioDataContratacaoPosteriorA(Scanner scanner)
    {

        System.out.println("Qual a data da contratação (formato: dd/mm/yyyy):");
        String data = scanner.next();
        LocalDate dataContratacao = LocalDate.parse(data, formatter);

        List<Funcionario> lista = funcionarioRepository.findDataContratacaoMaiorQue(dataContratacao);

        lista.forEach(System.out::println);

    }

    private void buscaFuncionarioSalario()
    {

        List<FuncionarioProjecao> lista = funcionarioRepository.findFuncionarioSalario();

        lista.forEach(f ->
        {
            System.out.println("id: "         + f.getId() +
                               " - nome: "    + f.getNome() +
                               " - salario: " + f.getSalario());
        });

    }

}
