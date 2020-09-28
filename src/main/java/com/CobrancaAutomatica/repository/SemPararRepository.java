package com.CobrancaAutomatica.repository;

import com.CobrancaAutomatica.model.T_INFO_SEMPARAR;
import com.CobrancaAutomatica.repository.SemPararRepository;
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
public interface SemPararRepository extends JpaRepository<T_INFO_SEMPARAR, Long> {
  @Query("select s from T_INFO_SEMPARAR s where s.UNIDADE like %?1% and s.STATUS_INSTALACAO = 'ATIVO'")
  List<T_INFO_SEMPARAR> findPessoaByUnidade(String paramString);
  
  @Query("select s from T_INFO_SEMPARAR s where s.EMPRESA like %?1% and s.STATUS_INSTALACAO = 'ATIVO'")
  List<T_INFO_SEMPARAR> findPessoaByCliente(String paramString);
  
  @Query("select s from T_INFO_SEMPARAR s where s.UNIDADE like %?1% and s.EMPRESA = ?2 and s.STATUS_INSTALACAO = 'ATIVO'")
  List<T_INFO_SEMPARAR> findPessoaByUnidadeCliente(String paramString1, String paramString2);
  
  @Query("select EMPRESA from T_INFO_SEMPARAR s GROUP BY EMPRESA")
  String[] findPessoaByClienteTodos();
  
  @Query("select s from T_INFO_SEMPARAR s where s.DT_INSTALACAO BETWEEN ?1 and ?2 and s.EMPRESA LIKE %?3% and s.STATUS_INSTALACAO = 'ATIVO'")
  List<T_INFO_SEMPARAR> findPessoaByData(Date paramDate1, Date paramDate2, String paramString);
  
  @Query("select s from T_INFO_SEMPARAR s where s.DT_INSTALACAO BETWEEN ?1 and ?2 and s.EMPRESA LIKE %?3%")
  Page<T_INFO_SEMPARAR> findPessoaByDataPage(Date paramDate1, Date paramDate2, String paramString, Pageable paramPageable);
  
  @Query("select s from T_INFO_SEMPARAR s where s.DT_INSTALACAO BETWEEN ?1 and ?2 and s.STATUS_INSTALACAO = 'ATIVO'")
  List<T_INFO_SEMPARAR> findPessoaByTodosClienteComData(Date paramDate1, Date paramDate2);
  
  @Query("select s from T_INFO_SEMPARAR s where s.DT_INSTALACAO BETWEEN ?1 and ?2")
  Page<T_INFO_SEMPARAR> findPessoaByTodosCliente(Date paramDate1, Date paramDate2, Pageable paramPageable);
  
  default Page<T_INFO_SEMPARAR> findPessoaByUnidadePage(String nomePesquisaSemparar, Pageable pageable) {
    T_INFO_SEMPARAR sempararModel = new T_INFO_SEMPARAR();
    sempararModel.setUNIDADE(nomePesquisaSemparar);
    ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny().withMatcher(
        "UNIDADE", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
    Example<T_INFO_SEMPARAR> example = Example.of(sempararModel, exampleMatcher);
    Page<T_INFO_SEMPARAR> semparar = findAll(example, pageable);
    return semparar;
  }
  
  default Page<T_INFO_SEMPARAR> findPessoaByUnidadeClientePage(String UNIDADE, String EMPRESA, Pageable pageable) {
    T_INFO_SEMPARAR sempararModel = new T_INFO_SEMPARAR();
    sempararModel.setUNIDADE(UNIDADE);
    sempararModel.setEMPRESA(EMPRESA);
    ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny()
      .withMatcher("UNIDADE", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
      .withMatcher("EMPRESA", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
    Example<T_INFO_SEMPARAR> example = Example.of(sempararModel, exampleMatcher);
    Page<T_INFO_SEMPARAR> semparar = findAll(example, pageable);
    return semparar;
  }
  
  default Page<T_INFO_SEMPARAR> findPessoaByClientePage(String EMPRESA, Pageable pageable) {
    T_INFO_SEMPARAR sempararModel = new T_INFO_SEMPARAR();
    sempararModel.setEMPRESA(EMPRESA);
    ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny().withMatcher(
        "EMPRESA", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
    Example<T_INFO_SEMPARAR> example = Example.of(sempararModel, exampleMatcher);
    Page<T_INFO_SEMPARAR> semparar = findAll(example, pageable);
    return semparar;
  }
}
