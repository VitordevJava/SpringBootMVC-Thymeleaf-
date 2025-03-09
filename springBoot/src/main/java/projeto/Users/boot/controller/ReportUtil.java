package projeto.Users.boot.controller;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.servlet.ServletContext;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component
public class ReportUtil implements Serializable{

	/*Retorna nosso PDF Em Byte para download no navegador*/
	public byte[] gerarRelatorio (List listDados, String relatorio, ServletContext servletContext) throws Exception{
		
		/*Cria a lista de dados para o relatorio com a nossa lista de objetos para imprimir*/
		JRBeanCollectionDataSource JrBCDS = new JRBeanCollectionDataSource(listDados);
		
		/*Carregar o caminho do noosso arquivo Jasper compilado*/
		
		String caminhoJasper = servletContext.getRealPath("relatorios") + File.separator + relatorio + ".jasper";
		
		/*Carrega o arquivo Jasper passando os dados*/
		
		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, new HashMap(), JrBCDS);
		
		return JasperExportManager.exportReportToPdf(impressoraJasper);
	}
}
