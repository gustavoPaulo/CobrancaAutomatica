package com.CobrancaAutomatica.repository;

import com.CobrancaAutomatica.model.T_INFO_CONECTCAR;
import com.CobrancaAutomatica.repository.ConectCarRepository;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ConectCarRepository extends JpaRepository<T_INFO_CONECTCAR, Long> {
  @Query("select c from T_INFO_CONECTCAR c where c.UNIDADE like %?1% and c.STATUS_INSTALACAO = 'ATIVO'")
  List<T_INFO_CONECTCAR> findPessoaByUnidade(String paramString);
  
  @Query("select c from T_INFO_CONECTCAR c where c.NM_EMPRESA like %?1% and c.STATUS_INSTALACAO = 'ATIVO'")
  List<T_INFO_CONECTCAR> findPessoaByCliente(String paramString);
  
  @Query("select c from T_INFO_CONECTCAR c where c.UNIDADE like %?1% and c.NM_EMPRESA = ?2 and c.STATUS_INSTALACAO = 'ATIVO'")
  List<T_INFO_CONECTCAR> findPessoaByUnidadeCliente(String paramString1, String paramString2);
  
  @Query("select NM_EMPRESA from T_INFO_CONECTCAR c GROUP BY NM_EMPRESA")
  String[] findPessoaByClienteTodos();
  
  @Query("select c from T_INFO_CONECTCAR c where c.DT_INSTALACAO BETWEEN ?1 and ?2 and c.NM_EMPRESA LIKE %?3% and c.STATUS_INSTALACAO = 'ATIVO'")
  List<T_INFO_CONECTCAR> findPessoaByData(Date paramDate1, Date paramDate2, String paramString);
  
  @Query("select c from T_INFO_CONECTCAR c where c.DT_INSTALACAO BETWEEN ?1 and ?2 and c.NM_EMPRESA LIKE %?3%")
  Page<T_INFO_CONECTCAR> findPessoaByDataPage(Date paramDate1, Date paramDate2, String paramString, Pageable paramPageable);
  
  @Query("select c from T_INFO_CONECTCAR c where c.DT_INSTALACAO BETWEEN ?1 and ?2 and c.STATUS_INSTALACAO = 'ATIVO'")
  List<T_INFO_CONECTCAR> findPessoaByTodosClienteComData(Date paramDate1, Date paramDate2);
  
  @Query("select c from T_INFO_CONECTCAR c where c.DT_INSTALACAO BETWEEN ?1 and ?2")
  Page<T_INFO_CONECTCAR> findPessoaByTodosCliente(Date paramDate1, Date paramDate2, Pageable paramPageable);
  
  default Page<T_INFO_CONECTCAR> findPessoaByUnidadePage(String nomePesquisaConectcar, Pageable pageable) {
    T_INFO_CONECTCAR conectcarModel = new T_INFO_CONECTCAR();
    conectcarModel.setUNIDADE(nomePesquisaConectcar);
    ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny().withMatcher(
        "UNIDADE", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
    Example<T_INFO_CONECTCAR> example = Example.of(conectcarModel, exampleMatcher);
    Page<T_INFO_CONECTCAR> conectcar = findAll(example, pageable);
    return conectcar;
  }
  
  default Page<T_INFO_CONECTCAR> findPessoaByUnidadeClientePage(String nomePesquisaConectcar, String NM_EMPRESA, Pageable pageable) {
    T_INFO_CONECTCAR conectcarModel = new T_INFO_CONECTCAR();
    conectcarModel.setUNIDADE(nomePesquisaConectcar);
    conectcarModel.setNM_EMPRESA(NM_EMPRESA);
    ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny()
      .withMatcher("UNIDADE", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
      .withMatcher("NM_EMPRESA", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
    Example<T_INFO_CONECTCAR> example = Example.of(conectcarModel, exampleMatcher);
    Page<T_INFO_CONECTCAR> conectcar = findAll(example, pageable);
    return conectcar;
  }
  
  default Page<T_INFO_CONECTCAR> findPessoaByClientePage(String NM_EMPRESA, Pageable pageable) {
    T_INFO_CONECTCAR conectcarModel = new T_INFO_CONECTCAR();
    conectcarModel.setNM_EMPRESA(NM_EMPRESA);
    ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny().withMatcher(
        "NM_EMPRESA", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
    Example<T_INFO_CONECTCAR> example = Example.of(conectcarModel, exampleMatcher);
    Page<T_INFO_CONECTCAR> conectcar = findAll(example, pageable);
    return conectcar;
  }
}
