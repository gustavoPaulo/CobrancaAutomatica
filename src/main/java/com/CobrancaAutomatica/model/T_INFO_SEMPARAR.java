package com.CobrancaAutomatica.model;

import com.CobrancaAutomatica.model.T_INFO_SEMPARAR;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@SuppressWarnings("deprecation")
@Entity
public class T_INFO_SEMPARAR implements Serializable {
  private static final long serialVersionUID = 1L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long ID_GERAL;
  
  @NotEmpty(message = "Cliente deve ser preenchido!")
  private String EMPRESA;
  
  @NotEmpty(message = "Unidade deve ser preenchido!")
  private String UNIDADE;
  
  private String MOBILE_DESKTOP;
  
  @NotEmpty(message = "Cdeve ser preenchido!")
  private String CD_SEMPARAR;
  
  @NotEmpty(message = "Hash deve ser preenchido!")
  private String HASH_ORIGINAL;
  
  private String HASH_CRIPTOGRAFADO;
  
  private String STATUS_INSTALACAO;
  
  @NotNull(message = "Data obrigatória!")
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  @Temporal(TemporalType.DATE)
  private Date DT_INSTALACAO;
  
  private Long CD_UND_ERP;
  
  private String UNIDADE_ERP;
  
  private String SUPERVISOR_UNIDADE;
  
  private String CONTATO_SUPERVISOR;
  
  private String OBSERVACOES;
  
  public Long getID_GERAL() {
    return this.ID_GERAL;
  }
  
  public void setID_GERAL(Long iD_GERAL) {
    this.ID_GERAL = iD_GERAL;
  }
  
  public String getCD_SEMPARAR() {
    return this.CD_SEMPARAR;
  }
  
  public void setCD_SEMPARAR(String cD_SEMPARAR) {
    this.CD_SEMPARAR = cD_SEMPARAR;
  }
  
  public String getUNIDADE() {
    return this.UNIDADE;
  }
  
  public void setUNIDADE(String uNIDADE) {
    this.UNIDADE = uNIDADE;
  }
  
  public Long getCD_UND_ERP() {
    return this.CD_UND_ERP;
  }
  
  public void setCD_UND_ERP(Long cD_UND_ERP) {
    this.CD_UND_ERP = cD_UND_ERP;
  }
  
  public String getUNIDADE_ERP() {
    return this.UNIDADE_ERP;
  }
  
  public void setUNIDADE_ERP(String uNIDADE_ERP) {
    this.UNIDADE_ERP = uNIDADE_ERP;
  }
  
  public String getMOBILE_DESKTOP() {
    return this.MOBILE_DESKTOP;
  }
  
  public void setMOBILE_DESKTOP(String mOBILE_DESKTOP) {
    this.MOBILE_DESKTOP = mOBILE_DESKTOP;
  }
  
  public String getEMPRESA() {
    return this.EMPRESA;
  }
  
  public void setEMPRESA(String eMPRESA) {
    this.EMPRESA = eMPRESA;
  }
  
  public String getHASH_ORIGINAL() {
    return this.HASH_ORIGINAL;
  }
  
  public void setHASH_ORIGINAL(String hASH_ORIGINAL) {
    this.HASH_ORIGINAL = hASH_ORIGINAL;
  }
  
  public String getHASH_CRIPTOGRAFADO() {
    return this.HASH_CRIPTOGRAFADO;
  }
  
  public void setHASH_CRIPTOGRAFADO(String hASH_CRIPTOGRAFADO) {
    this.HASH_CRIPTOGRAFADO = hASH_CRIPTOGRAFADO;
  }
  
  public String getSTATUS_INSTALACAO() {
    return this.STATUS_INSTALACAO;
  }
  
  public void setSTATUS_INSTALACAO(String sTATUS_INSTALACAO) {
    this.STATUS_INSTALACAO = sTATUS_INSTALACAO;
  }
  
  public Date getDT_INSTALACAO() {
    return this.DT_INSTALACAO;
  }
  
  public void setDT_INSTALACAO(Date dT_INSTALACAO) {
    this.DT_INSTALACAO = dT_INSTALACAO;
  }
  
  public String getSUPERVISOR_UNIDADE() {
    return this.SUPERVISOR_UNIDADE;
  }
  
  public void setSUPERVISOR_UNIDADE(String sUPERVISOR_UNIDADE) {
    this.SUPERVISOR_UNIDADE = sUPERVISOR_UNIDADE;
  }
  
  public String getCONTATO_SUPERVISOR() {
    return this.CONTATO_SUPERVISOR;
  }
  
  public void setCONTATO_SUPERVISOR(String cONTATO_SUPERVISOR) {
    this.CONTATO_SUPERVISOR = cONTATO_SUPERVISOR;
  }
  
  public String getOBSERVACOES() {
    return this.OBSERVACOES;
  }
  
  public void setOBSERVACOES(String oBSERVACOES) {
    this.OBSERVACOES = oBSERVACOES;
  }
}
