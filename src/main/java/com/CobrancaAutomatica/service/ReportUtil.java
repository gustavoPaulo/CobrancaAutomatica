package com.CobrancaAutomatica.service;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@SuppressWarnings("serial")
@Component
public class ReportUtil implements Serializable {

	// Retorna o PDF em byte para download
	public byte[] geraRelatorio(@SuppressWarnings("rawtypes") List listaDados, String relatorio,
			ServletContext servletContext) throws Exception {

		// Cria lista de dados para o relatorio com a lista de objetos
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listaDados);

		// Carrega o caminho do arquivo jasper compilado
		String caminhoJasper = servletContext.getRealPath("relatorios") + File.separator + relatorio + ".jasper";

		// Carrega o arquivo Jasper passando os dados
		@SuppressWarnings("unchecked")
		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, new HashedMap(), jrbcds);

		// Exporta para byte[] para o download do PDF
		return JasperExportManager.exportReportToPdf(impressoraJasper);
	}

}