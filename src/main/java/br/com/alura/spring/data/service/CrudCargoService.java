package br.com.alura.spring.data.service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.repository.CargoRepository;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class CrudCargoService
{

    private Boolean system = true;

    private final CargoRepository repository;

    public CrudCargoService(CargoRepository repository)
    {
        this.repository = repository;
    }

    public void inicial(Scanner scanner)
    {

        while (system)
        {

            System.out.println("------------------------------------------------");
            System.out.println("Qual ação de CARGO você deseja executar:");
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

        System.out.println("descrição do cargo:");
        String descricao = scanner.next();

        // se já existir, não permite duplicidade
        if (repository.count() > 0)
        {
            repository.findAll().forEach( c ->
            {
                if (c.getDescricao().toUpperCase().equals(descricao))
                {
                    System.out.println("Este cargo já está cadastrado. Duplicação não autorizada.");
                    return;
                }
            });
        }

        System.out.println("Salário padrão:");
        Double salario = scanner.nextDouble();

        Cargo cargo = new Cargo();
        cargo.setId(0);
        cargo.setDescricao(descricao);
        cargo.setSalarioPadrao(salario);
        repository.save(cargo);

    }

    private void atualizar(Scanner scanner)
    {

        System.out.println(" *** ALTERAÇÃO ***************************************************");

        System.out.println("ID do cargo:");
        Integer id = scanner.nextInt();

        if (!repository.existsById(id))
        {
            System.out.println("Não existe cargo com ID informado.");
            return;
        }

        System.out.println("Descrição do cargo:");
        String descricao = scanner.next();

        System.out.println("Salário padrão:");
        Double salario = scanner.nextDouble();

        Cargo cargo = new Cargo();
        cargo.setId(id);
        cargo.setDescricao(descricao);
        cargo.setSalarioPadrao(salario);
        repository.save(cargo);

    }

    private void listar(Scanner scanner)
    {

        System.out.println(" *** RELATÓRIO ***************************************************");

        if (repository.count() > 0)
        {

            System.out.println("------------------------------------------------");
            System.out.println("Lista de Cargos Cadastrados");
            System.out.println("------------------------------------------------");

            Iterable<Cargo> cargos = repository.findAll();

            cargos.forEach(cargo ->
            {
                System.out.println(cargo); // utiliza ToString
            });

            System.out.println(repository.count() + " cargos cadastrados.");

        }
        else
        {
            System.out.println("Não existem cargos cadastrados.");
        }

    }

    private void excluir(Scanner scanner)
    {

        System.out.println(" *** EXCLUSÃO ****************************************************");

        System.out.println("ID do cargo a ser excluído:");
        Integer id = scanner.nextInt();

        if (!repository.existsById(id))
        {
            System.out.println("Não existe cargo com ID informado.");
            return;
        }

        repository.deleteById(id);

        System.out.println("Cargo excluído com sucesso.");

    }

}
