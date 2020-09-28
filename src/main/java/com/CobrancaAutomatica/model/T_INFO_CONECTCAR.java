package com.CobrancaAutomatica.model;

import com.CobrancaAutomatica.model.T_INFO_CONECTCAR;
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
public class T_INFO_CONECTCAR implements Serializable {
  private static final long serialVersionUID = 1L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long ID_GERAL;
  
  @NotEmpty(message = "Cliente deve ser preenchido!")
  private String NM_EMPRESA;
  
  @NotEmpty(message = "Unidade deve ser preenchido!")
  private String UNIDADE;
  
  private String MOBILE_DESKTOP;
  
  @NotEmpty(message = "Cdeve ser preenchido!")
  private String CD_CONECTCAR;
  
  @NotEmpty(message = "Usudeve ser preenchido!")
  private String DS_USUARIO_CONECTCAR;
  
  @NotEmpty(message = "Senha deve ser preenchido!")
  private String SENHA_CONECTCAR;
  
  private String STATUS_INSTALACAO;
  
  @NotNull(message = "Data obrigatória!")
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  @Temporal(TemporalType.DATE)
  
  private Date DT_INSTALACAO;
  
  private String CD_UNIDADE_ERP;
  
  private String SIGLA_UNIDADE_ERP;
  
  private String NM_SUPER_UNIDADE;
  
  private String CONTATO_SUPERVISOR;
  
  private String OBSERVACOES;
  
  public Long getID_GERAL() {
    return this.ID_GERAL;
  }
  
  public void setID_GERAL(Long iD_GERAL) {
    this.ID_GERAL = iD_GERAL;
  }
  
  public String getUNIDADE() {
    return this.UNIDADE;
  }
  
  public void setUNIDADE(String uNIDADE) {
    this.UNIDADE = uNIDADE;
  }
  
  public String getCD_UNIDADE_ERP() {
    return this.CD_UNIDADE_ERP;
  }
  
  public void setCD_UNIDADE_ERP(String cD_UNIDADE_ERP) {
    this.CD_UNIDADE_ERP = cD_UNIDADE_ERP;
  }
  
  public String getSIGLA_UNIDADE_ERP() {
    return this.SIGLA_UNIDADE_ERP;
  }
  
  public void setSIGLA_UNIDADE_ERP(String sIGLA_UNIDADE_ERP) {
    this.SIGLA_UNIDADE_ERP = sIGLA_UNIDADE_ERP;
  }
  
  public String getMOBILE_DESKTOP() {
    return this.MOBILE_DESKTOP;
  }
  
  public void setMOBILE_DESKTOP(String mOBILE_DESKTOP) {
    this.MOBILE_DESKTOP = mOBILE_DESKTOP;
  }
  
  public String getNM_EMPRESA() {
    return this.NM_EMPRESA;
  }
  
  public void setNM_EMPRESA(String nM_EMPRESA) {
    this.NM_EMPRESA = nM_EMPRESA;
  }
  
  public String getCD_CONECTCAR() {
    return this.CD_CONECTCAR;
  }
  
  public void setCD_CONECTCAR(String cD_CONECTCAR) {
    this.CD_CONECTCAR = cD_CONECTCAR;
  }
  
  public String getDS_USUARIO_CONECTCAR() {
    return this.DS_USUARIO_CONECTCAR;
  }
  
  public void setDS_USUARIO_CONECTCAR(String dS_USUARIO_CONECTCAR) {
    this.DS_USUARIO_CONECTCAR = dS_USUARIO_CONECTCAR;
  }
  
  public String getSENHA_CONECTCAR() {
    return this.SENHA_CONECTCAR;
  }
  
  public void setSENHA_CONECTCAR(String sENHA_CONECTCAR) {
    this.SENHA_CONECTCAR = sENHA_CONECTCAR;
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
  
  public String getNM_SUPER_UNIDADE() {
    return this.NM_SUPER_UNIDADE;
  }
  
  public void setNM_SUPER_UNIDADE(String nM_SUPER_UNIDADE) {
    this.NM_SUPER_UNIDADE = nM_SUPER_UNIDADE;
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
