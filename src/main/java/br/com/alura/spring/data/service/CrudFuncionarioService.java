package br.com.alura.spring.data.service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.UnidadeTrabalho;
import br.com.alura.spring.data.repository.CargoRepository;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.repository.UnidadeTrabalhoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class CrudFuncionarioService
{

    private Boolean system = true;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final FuncionarioRepository     repository;
    private final CargoRepository           repositoryCargo;
    private final UnidadeTrabalhoRepository repositoryUnidades;

    public CrudFuncionarioService(FuncionarioRepository repository,
                                  CargoRepository repositoryCargo,
                                  UnidadeTrabalhoRepository repositoryUnidades)
    {
        this.repository         = repository;
        this.repositoryCargo    = repositoryCargo;
        this.repositoryUnidades = repositoryUnidades;
    }

    public void inicial(Scanner scanner)
    {

        while (system)
        {

            System.out.println("------------------------------------------------");
            System.out.println("Qual ação de Funcionario você deseja executar:");
            System.out.println("------------------------------------------------");
            System.out.println("0 - SAIR");
            System.out.println("1 - INCLUSÃO");
            System.out.println("2 - ALTERAÇÃO");
            System.out.println("3 - LISTAR");
            System.out.println("4 - EXCLUIR");

            int action = scanner.nextInt();

            switch (action)
            {
                case 1:
                    salvar(scanner);
                    break;

                case 2:
                    atualizar(scanner);
                    break;

                case 3:
                    listar(scanner);
                    break;

                case 4:
                    excluir(scanner);
                    break;

                default:
                    system = false;
                    break;
            }

        }

    }

    private void salvar(Scanner scanner)
    {

        System.out.println(" *** INCLUSÃO ****************************************************");

        System.out.println("Nome:");
        String nome = scanner.next();

        System.out.println("CPF:");
        String cpf = scanner.next();

        // se já existir, não permite duplicidade
        if (repository != null && repository.count() > 0)
        {
            repository.findAll().forEach( c ->
            {
                if (c.getNome().toUpperCase().equals(nome) || c.getCpf().equals(cpf))
                {
                    System.out.println("Este Funcionario já está cadastrado. Duplicação não autorizada.");
                    return;
                }
            });
        }

        System.out.println("Salário:");
        Double salario = scanner.nextDouble();

        System.out.println("Data da contratação (formato: dd/mm/yyyy):");
        String data = scanner.next();
        LocalDate localDate = LocalDate.parse(data, formatter);

        Cargo cargo = solicitaCargo(scanner);

        List<UnidadeTrabalho> unidades = solicitaUnidadesTrabalho(scanner);

        Funcionario funcionario = new Funcionario();
        funcionario.setId(0L);
        funcionario.setNome(nome);
        funcionario.setCpf(cpf);
        funcionario.setSalario(salario);
        funcionario.setDataContratacao(localDate);
        funcionario.setCargo(cargo);
        funcionario.setUnidadeTrabalhos(unidades);

        repository.save(funcionario);

    }

    private Cargo solicitaCargo(Scanner scanner)
    {

        Boolean informou = false;
        Optional<Cargo> cargo = null;

        while (!informou)
        {

            System.out.println("Id do Cargo:");
            Integer idCargo = scanner.nextInt();

            cargo = repositoryCargo.findById(idCargo);

            if (cargo.isPresent())
            {
                informou = true;
            }
            else
            {
                System.out.println("Não existe cargo com o Id informado.");
            }

        }

        return cargo.get();

    }

    private List<UnidadeTrabalho> solicitaUnidadesTrabalho(Scanner scanner)
    {

        Boolean informou = false;
        List<UnidadeTrabalho> unidades = new ArrayList<>();

        while (!informou)
        {

            System.out.println("Id da Unidade de Trabalho:");
            Long idUnidade = scanner.nextLong();

            Optional<UnidadeTrabalho> ut = repositoryUnidades.findById(idUnidade);

            if (ut.isPresent())
            {
                unidades.add(ut.get());
                System.out.println("Tecle '1' para incluir mais unidades de trabalho:");
                String inclui = scanner.next();
                informou = (!inclui.equals("1"));
            }
            else
            {
                System.out.println("Não existe unidade de trabalho com o Id informado.");
            }

        }

        return unidades;

    }


    private void atualizar(Scanner scanner)
    {

        System.out.println(" *** ALTERAÇÃO ***************************************************");

        System.out.println("ID do Funcionario:");
        Long id = scanner.nextLong();

        if (!repository.existsById(id))
        {
            System.out.println("Não existe Funcionario com ID informado.");
            return;
        }

        System.out.println("Nome:");
        String nome = scanner.next();

        System.out.println("CPF:");
        String cpf = scanner.next();

        // se já existir, não permite duplicidade
        if (repository.count() > 0)
        {
            repository.findAll().forEach( c ->
            {
                if (c.getId() != id)
                {
                    if (c.getNome().toUpperCase().equals(nome) || c.getCpf().equals(cpf))
                    {
                        System.out.println("Este Funcionario já está cadastrado. Duplicação não autorizada.");
                        return;
                    }
                }
            });
        }

        System.out.println("Salário:");
        Double salario = scanner.nextDouble();

        System.out.println("Data da contratação (formato: dd/mm/yyyy):");
        String data = scanner.next();
        LocalDate localDate = LocalDate.parse(data, formatter);


        Cargo cargo = solicitaCargo(scanner);

        List<UnidadeTrabalho> unidades = solicitaUnidadesTrabalho(scanner);

        Funcionario funcionario = new Funcionario();
        funcionario.setId(id);
        funcionario.setNome(nome);
        funcionario.setCpf(cpf);
        funcionario.setSalario(salario);
        funcionario.setDataContratacao(localDate);
        funcionario.setCargo(cargo);
        funcionario.setUnidadeTrabalhos(unidades);

        repository.save(funcionario);

    }

    private void listar(Scanner scanner)
    {

        System.out.println("Qual página você deseja visualizar ?");
        Integer page = scanner.nextInt()-1;

        System.out.println("Quantos registros por página ?");
        Integer size = scanner.nextInt();

        // define os parâmetros de paginação e a ordenação
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "nome"));
        // Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "salario"));

        System.out.println(" *** RELATÓRIO ***************************************************");

        if (repository.count() > 0)
        {

            System.out.println("------------------------------------------------");
            System.out.println("Lista de Funcionários Cadastrados");
            System.out.println("------------------------------------------------");

            Page<Funcionario> funcionarios = repository.findAll(pageable);

            funcionarios.forEach(System.out::println);

            System.out.println("Funcionarios: "     + funcionarios.getTotalElements());
            System.out.println("Total de Paginas: " + funcionarios.getTotalPages());
            System.out.println("Pagina atual: "     + (funcionarios.getNumber()+1));

        }
        else
        {
            System.out.println("Não existem Funcionarios cadastrados.");
        }

    }

    private void excluir(Scanner scanner)
    {

        System.out.println(" *** EXCLUSÃO ****************************************************");

        System.out.println("ID do Funcionario a ser excluído:");
        Long id = scanner.nextLong();

        if (!repository.existsById(id))
        {
            System.out.println("Não existe Funcionario com ID informado.");
            return;
        }

        repository.deleteById(id);

        System.out.println("Funcionario excluído com sucesso.");

    }

}
