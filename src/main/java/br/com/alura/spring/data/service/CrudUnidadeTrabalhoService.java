package br.com.alura.spring.data.service;

import br.com.alura.spring.data.orm.UnidadeTrabalho;
import br.com.alura.spring.data.repository.UnidadeTrabalhoRepository;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class CrudUnidadeTrabalhoService
{

    private Boolean system = true;

    private final UnidadeTrabalhoRepository repository;

    public CrudUnidadeTrabalhoService(UnidadeTrabalhoRepository repository)
    {
        this.repository = repository;
    }

    public void inicial(Scanner scanner)
    {

        while (system)
        {

            System.out.println("------------------------------------------------");
            System.out.println("Qual ação de Unidade de Trabalho você deseja executar:");
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

        System.out.println("descrição da Unidade de Trabalho:");
        String descricao = scanner.next();

        // se já existir, não permite duplicidade
        if (repository.count() > 0)
        {
            repository.findAll().forEach( c ->
            {
                if (c.getDescricao().toUpperCase().equals(descricao))
                {
                    System.out.println("Esta Unidade de Trabalho já está cadastrada. Duplicação não autorizada.");
                    return;
                }
            });
        }

        System.out.println("endereço da Unidade de Trabalho:");
        String endereco = scanner.next();

        UnidadeTrabalho unidadeTrabalho = new UnidadeTrabalho();
        unidadeTrabalho.setId(0L);
        unidadeTrabalho.setDescricao(descricao);
        unidadeTrabalho.setEndereco(endereco);
        repository.save(unidadeTrabalho);

    }

    private void atualizar(Scanner scanner)
    {

        System.out.println(" *** ALTERAÇÃO ***************************************************");

        System.out.println("ID da Unidade de Trabalho:");
        Long id = scanner.nextLong();

        if (!repository.existsById(id))
        {
            System.out.println("Não existe Unidade de Trabalho com ID informado.");
            return;
        }

        System.out.println("Descrição da Unidade Trabalho:");
        String descricao = scanner.next();

        System.out.println("Endereço da Unidade Trabalho:");
        String endereco = scanner.next();

        UnidadeTrabalho UnidadeTrabalho = new UnidadeTrabalho();
        UnidadeTrabalho.setId(id);
        UnidadeTrabalho.setDescricao(descricao);
        UnidadeTrabalho.setEndereco(endereco);
        repository.save(UnidadeTrabalho);

    }

    private void listar(Scanner scanner)
    {

        System.out.println(" *** RELATÓRIO ***************************************************");

        if (repository.count() > 0)
        {

            System.out.println("------------------------------------------------");
            System.out.println("Lista de Unidades de Trabalho Cadastradas");
            System.out.println("------------------------------------------------");

            Iterable<UnidadeTrabalho> UnidadeTrabalhos = repository.findAll();

            UnidadeTrabalhos.forEach(UnidadeTrabalho ->
            {
                System.out.println(UnidadeTrabalho); // utiliza ToString
            });

            System.out.println(repository.count() + " Unidades de Trabalho cadastradas.");

        }
        else
        {
            System.out.println("Não existem Unidades de Trabalho cadastradas.");
        }

    }

    private void excluir(Scanner scanner)
    {

        System.out.println(" *** EXCLUSÃO ****************************************************");

        System.out.println("ID da Unidade de Trabalho a ser excluída:");
        Long id = scanner.nextLong();

        if (!repository.existsById(id))
        {
            System.out.println("Não existe Unidade de Trabalho com ID informado.");
            return;
        }

        repository.deleteById(id);

        System.out.println("Unidade de Trabalho excluída com sucesso.");

    }

}
