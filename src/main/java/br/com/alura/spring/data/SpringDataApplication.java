package br.com.alura.spring.data;

import br.com.alura.spring.data.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*

	https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation

	Derived Queries		queries criadas através de comandos Java
	JPQL 				queries criadas através de uma estrutura SQL, porém com os nomes das entidades Java
	Native Query		queries padrões SQL que conseguimos executar no nosso Client SQL

*/

import java.util.Scanner;

@SpringBootApplication
public class SpringDataApplication implements CommandLineRunner
{

	private Boolean system = true;

	private final CrudCargoService 				cargoService;
	private final CrudUnidadeTrabalhoService 	unidadeService;
	private final CrudFuncionarioService		funcionarioService;

	private final RelatoriosService 			relatoriosService;
	private final RelatorioFuncionarioDinamico	relatorioFuncionarioDinamicoService;

	public SpringDataApplication(CrudCargoService cargoService,
								 CrudUnidadeTrabalhoService unidadeService,
								 CrudFuncionarioService funcionarioService,
								 RelatoriosService relatoriosService,
								 RelatorioFuncionarioDinamico	relatorioFuncionarioDinamicoService)
	{
		this.cargoService 		= cargoService;
		this.unidadeService 	= unidadeService;
		this.funcionarioService = funcionarioService;
		this.relatoriosService  = relatoriosService;
		this.relatorioFuncionarioDinamicoService = relatorioFuncionarioDinamicoService;
	}

	public static void main(String[] args)
	{
		SpringApplication.run(SpringDataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception
	{

		System.out.println("*** método run");

		Scanner scanner = new Scanner(System.in);

		while (system)
		{

			System.out.println("------------------------------------------------");
			System.out.println("Qual ação você deseja executar:");
			System.out.println("------------------------------------------------");
			System.out.println("0 - SAIR");
			System.out.println("1 - CARGOS");
			System.out.println("2 - UNIDADES DE TRABALHO");
			System.out.println("3 - FUNCIONÁRIOS");
			System.out.println("4 - PESQUISAS E RELATÓRIOS");
			System.out.println("5 - FUNCIONÁRIOS (DINÂMICO)");
			System.out.println("------------------------------------------------");

			int action = scanner.nextInt();

			switch (action)
			{
				case 1:
					cargoService.inicial(scanner);
					break;
				case 2:
					unidadeService.inicial(scanner);
					break;
				case 3:
					funcionarioService.inicial(scanner);
					break;
				case 4:
					relatoriosService.inicial(scanner);
					break;
				case 5:
					relatorioFuncionarioDinamicoService.inicial(scanner);
					break;
				default:
					system = false;
					break;

			}

		}

		System.out.println("*** FINALIZANDO ...");

		System.exit(0);

	}

}
