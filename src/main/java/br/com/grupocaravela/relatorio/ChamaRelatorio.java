package br.com.grupocaravela.relatorio;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import br.com.grupocaravela.entidade.Credito;
import br.com.grupocaravela.entidade.Entidade;
import br.com.grupocaravela.entidade.Pessoa;
import br.com.grupocaravela.util.ConectaBanco;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class ChamaRelatorio {

	String sistema = System.getProperty("os.name");
	
	private SimpleDateFormat formatBra = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat formatSql = new SimpleDateFormat("yyyy-MM-dd");
	
	private ImageIcon gto = new ImageIcon(getClass().getResource("/br/com/grupocaravela/imagens/logo_completo_512_branco.png"));

	//método
	    public void report(String endereco) throws JRException {

	        Connection conn = ConectaBanco.getConnection();

	        JasperReport jasper; 

	        Map map = new HashMap<>();
	        
	        map.put("LOGO", gto.getImage());
	        
	        //map.put("IP_SERVIDOR", Empresa.getIpServidor());

	        URL arquivo = getClass().getResource(endereco);
	        jasper = (JasperReport) JRLoader.loadObject(arquivo);

	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, map, conn);
	        
	        
	        try {
	        	JasperExportManager.exportReportToPdfFile(jasperPrint, "relatorio.pdf");
	        	java.awt.Desktop.getDesktop().open( new File( "relatorio.pdf" ) );
			} catch (Exception e) {
				JasperViewer jv = new JasperViewer(jasperPrint, false);
		        jv.setVisible(true);
			}
	        

	    }	
	    
	    public void reportEntidade(String endereco, Entidade entidade) throws JRException {

	        Connection conn = ConectaBanco.getConnection();

	        JasperReport jasper; 

	        Map map = new HashMap<>();
	        
	        map.put("ENTIDADE", entidade.getNome());
	        map.put("LOGO", gto.getImage());

	        URL arquivo = getClass().getResource(endereco);
	        jasper = (JasperReport) JRLoader.loadObject(arquivo);

	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, map, conn);

	        try {
	        	JasperExportManager.exportReportToPdfFile(jasperPrint, "relatorio.pdf");
	        	java.awt.Desktop.getDesktop().open( new File( "relatorio.pdf" ) );
			} catch (Exception e) {
				JasperViewer jv = new JasperViewer(jasperPrint, false);
		        jv.setVisible(true);
			}

	    }	
	    
	    public void reportEntidadeData(String endereco, Entidade entidade, Date dtInicial, Date dtFinal) throws JRException {

	        Connection conn = ConectaBanco.getConnection();

	        JasperReport jasper; 

	        Map map = new HashMap<>();
	        
	        map.put("ENTIDADE", entidade.getNome());
	        map.put("DATA_INICIAL_SQL", formatSql.format(dtInicial));
	        map.put("DATA_FINAL_SQL", formatSql.format(dtFinal));
	        map.put("DATA_INICIAL", formatBra.format(dtInicial));
	        map.put("DATA_FINAL", formatBra.format(dtFinal));
	        map.put("LOGO", gto.getImage());

	        URL arquivo = getClass().getResource(endereco);
	        jasper = (JasperReport) JRLoader.loadObject(arquivo);

	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, map, conn);

	        try {
	        	JasperExportManager.exportReportToPdfFile(jasperPrint, "relatorio.pdf");
	        	java.awt.Desktop.getDesktop().open( new File( "relatorio.pdf" ) );
			} catch (Exception e) {
				JasperViewer jv = new JasperViewer(jasperPrint, false);
		        jv.setVisible(true);
			}

	    }	
	    
	    public void reportEntidadeCreditoDebito(String endereco, Entidade entidade, Date dtInicial, Date dtFinal, Double saldo, Double credito, Double debito) throws JRException {

	        Connection conn = ConectaBanco.getConnection();

	        JasperReport jasper; 

	        Map map = new HashMap<>();
	        Map mapExport = new HashMap<>();
	        
	        //URL arq = getClass().getResource("/home/fabio/Eclipse Workspace/EE/Gestor/src/main/java/br/com/grupocaravela/relatorio/SubCredito.jrxml");
	        URL arq = this.getClass().getResource("SubCredito.jasper");
	        String arq2 = arq.getPath();
	        String arq3 = arq2.substring(0, arq2.lastIndexOf("/")+1);
	        
	        map.put("ENTIDADE", entidade.getNome());
	        map.put("DATA_INICIAL_SQL", formatSql.format(dtInicial));
	        map.put("DATA_FINAL_SQL", formatSql.format(dtFinal));
	        map.put("DATA_INICIAL", formatBra.format(dtInicial));
	        map.put("DATA_FINAL", formatBra.format(dtFinal));
	        map.put("SALDO", saldo);
	        map.put("CREDITO", credito);
	        map.put("DEBITO", debito);
	        
	        if ("Linux".equals(System.getProperty("os.name"))) {
	        	map.put("SUBREPORT_DIR", arq3);
	        }else{	  
	        	map.put("SUBREPORT_DIR", "C:\\GrupoCaravela\\gestor\\software\\relatorio\\");
	        }
	        
	        map.put("LOGO", gto.getImage());
	        
	        mapExport.put("ENTIDADE", entidade.getNome());
	        mapExport.put("DATA_INICIAL_SQL", formatSql.format(dtInicial));
	        mapExport.put("DATA_FINAL_SQL", formatSql.format(dtFinal));
	        
	        map.put("MAP", mapExport);

	        URL arquivo = getClass().getResource(endereco);
	        jasper = (JasperReport) JRLoader.loadObject(arquivo);

	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, map, conn);

	        try {
	        	JasperExportManager.exportReportToPdfFile(jasperPrint, "relatorio.pdf");
	        	java.awt.Desktop.getDesktop().open( new File( "relatorio.pdf" ) );
			} catch (Exception e) {
				JasperViewer jv = new JasperViewer(jasperPrint, false);
		        jv.setVisible(true);
			}

	    }	
	    
	    public void reportEntidadeCreditoDebitoPai(String endereco, Entidade entidade, Date dtInicial, Date dtFinal, Double saldo, Double credito, Double debito) throws JRException {

	        Connection conn = ConectaBanco.getConnection();

	        JasperReport jasper; 

	        Map map = new HashMap<>();
	        Map mapExport = new HashMap<>();
	        
	        //URL arq = getClass().getResource("/home/fabio/Eclipse Workspace/EE/Gestor/src/main/java/br/com/grupocaravela/relatorio/SubCredito.jrxml");
	        URL arq = this.getClass().getResource("SubCreditoPai.jasper");
	        String arq2 = arq.getPath();
	        String arq3 = arq2.substring(0, arq2.lastIndexOf("/")+1);
	        
	        map.put("ENTIDADE", entidade.getNome());
	        map.put("DATA_INICIAL_SQL", formatSql.format(dtInicial));
	        map.put("DATA_FINAL_SQL", formatSql.format(dtFinal));
	        map.put("DATA_INICIAL", formatBra.format(dtInicial));
	        map.put("DATA_FINAL", formatBra.format(dtFinal));
	        map.put("SALDO", saldo);
	        map.put("CREDITO", credito);
	        map.put("DEBITO", debito);
	        
	        if ("Linux".equals(System.getProperty("os.name"))) {
	        	map.put("SUBREPORT_DIR", arq3);
	        }else{	  
	        	map.put("SUBREPORT_DIR", "C:\\GrupoCaravela\\gestor\\software\\relatorio\\");
	        }
	        
	        map.put("LOGO", gto.getImage());
	        
	        mapExport.put("ENTIDADE", entidade.getNome());
	        //mapExport.put("ENTIDADE_PAI", entidade.getEntidade().getId());
	        mapExport.put("ENTIDADE_PAI", entidade.getId());
	        mapExport.put("DATA_INICIAL_SQL", formatSql.format(dtInicial));
	        mapExport.put("DATA_FINAL_SQL", formatSql.format(dtFinal));
	        
	        //JOptionPane.showMessageDialog(null, "ENTIDADE_PAI " + entidade.getEntidade().getId());
	        
	        map.put("MAP", mapExport);

	        URL arquivo = getClass().getResource(endereco);
	        jasper = (JasperReport) JRLoader.loadObject(arquivo);

	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, map, conn);

	        try {
	        	JasperExportManager.exportReportToPdfFile(jasperPrint, "relatorio.pdf");
	        	java.awt.Desktop.getDesktop().open( new File( "relatorio.pdf" ) );
			} catch (Exception e) {
				JasperViewer jv = new JasperViewer(jasperPrint, false);
		        jv.setVisible(true);
			}

	    }	
	    
	    public void reportPessoaCreditoDebito(String endereco, Pessoa pessoa, Date dtInicial, Date dtFinal, Double saldo, Double credito, Double debito) throws JRException {

	        Connection conn = ConectaBanco.getConnection();

	        JasperReport jasper; 

	        Map map = new HashMap<>();
	        Map mapExport = new HashMap<>();
	        
	        URL arq = this.getClass().getResource("SubCredito.jasper");
	        String arq2 = arq.getPath();
	        String arq3 = arq2.substring(0, arq2.lastIndexOf("/")+1);
	        	        	        
	        map.put("PESSOA", pessoa.getNome());
	        map.put("DATA_INICIAL_SQL", formatSql.format(dtInicial));
	        map.put("DATA_FINAL_SQL", formatSql.format(dtFinal));
	        map.put("DATA_INICIAL", formatBra.format(dtInicial));
	        map.put("DATA_FINAL", formatBra.format(dtFinal));
	        map.put("SALDO", saldo);
	        map.put("CREDITO", credito);
	        map.put("DEBITO", debito);
	        
	        if ("Linux".equals(System.getProperty("os.name"))) {
	        	map.put("SUBREPORT_DIR", arq3);
	        }else{	        	
	        	map.put("SUBREPORT_DIR", "C:\\GrupoCaravela\\gestor\\software\\relatorio\\");
	        }
	        
	        map.put("LOGO", gto.getImage());
	        
	        mapExport.put("PESSOA", pessoa.getNome());
	        mapExport.put("DATA_INICIAL_SQL", formatSql.format(dtInicial));
	        mapExport.put("DATA_FINAL_SQL", formatSql.format(dtFinal));
	        
	        map.put("MAP", mapExport);

	        URL arquivo = getClass().getResource(endereco);
	        jasper = (JasperReport) JRLoader.loadObject(arquivo);

	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, map, conn);

	        try {
	        	JasperExportManager.exportReportToPdfFile(jasperPrint, "relatorio.pdf");
	        	java.awt.Desktop.getDesktop().open( new File( "relatorio.pdf" ) );
			} catch (Exception e) {
				JasperViewer jv = new JasperViewer(jasperPrint, false);
		        jv.setVisible(true);
			}

	    }	
	    
}
