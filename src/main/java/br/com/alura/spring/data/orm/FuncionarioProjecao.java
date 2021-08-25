package br.com.alura.spring.data.orm;

// Interface based Projection.
// Como alternativa, podemos também usar uma classe com o mesmo propósito:
// o sufixo Dto é muito comum para esse tipo de classe auxiliar, e significa Data Transfer Object.
public interface FuncionarioProjecao
{

    Integer getId();
    String getNome();
    Double getSalario();

}
