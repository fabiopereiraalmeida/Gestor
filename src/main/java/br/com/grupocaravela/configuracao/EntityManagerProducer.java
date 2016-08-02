package br.com.grupocaravela.configuracao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class EntityManagerProducer {

	private static EntityManagerFactory factory;

	public EntityManagerProducer() {

		try {
			
			Map<String, String> properties = new HashMap<String, String>();
			//properties.put("javax.persistence.jdbc.url", "jdbc:mysql://" + lerArquivoIp() +"/gestor");
			properties.put("javax.persistence.jdbc.url", "jdbc:mysql://" + lerArquivoXml() + "/gestor?zeroDateTimeBehavior=convertToNull&autoReconnect=true");
			properties.put("javax.persistence.jdbc.user", "root");
			properties.put("javax.persistence.jdbc.password", "peperoni");
			
			factory = Persistence.createEntityManagerFactory("gestorPU", properties);
		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "ATENÇÃO! Servidor não encontrado!!!" + e);

		}
	}

	public static EntityManager createEntityManager() {
		return factory.createEntityManager();
	}

	public void closeEntityManager(EntityManager manager) {
		manager.close();
	}
	
	/*
	private String lerArquivoIp() {

		FileReader fileReader;
		String sistema = System.getProperty("os.name");
		String ip = null;

		try {
			if ("Linux".equals(sistema)) {
				fileReader = new FileReader("/opt/GrupoCaravela/gestor/software/conf.txt");
			} else {
				fileReader = new FileReader("C:\\GrupoCaravela\\gestor\\software\\conf.txt");
			}

			BufferedReader reader = new BufferedReader(fileReader);
			String data = null;
			while ((data = reader.readLine()) != null) {
				ip = String.valueOf(data);
			}
			fileReader.close();
			reader.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERRO! Erro ao ler o arquivo de versão do sistema!" + e);
		}

		return ip;
	}
*/
	
	private String lerArquivoXml() {

		String retorno = "localhost";
		
		File arquivo = null;

		if ("Linux".equals(System.getProperty("os.name"))) { // Verifica se o
																// sistema é
																// linux

			try {
				arquivo = new File("/opt/GrupoCaravela/gestor/conf.xml");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Arquivo de configuração não encontrado");
			}

		} else {
			try {
				arquivo = new File("c:\\GrupoCaravela\\gestor\\conf.xml");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Arquivo de configuração não encontrado");
			}

		}

		SAXBuilder builder = new SAXBuilder();

		Document doc = null;
		try {
			doc = builder.build(arquivo);
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Element configuracao = (Element) doc.getRootElement();

		List conf = configuracao.getChildren();

		Iterator i = conf.iterator();

		while (i.hasNext()) {
			Element empresa = (Element) i.next();

			retorno = empresa.getChildText("ipServidor");
			/*
			tfNome.setText(empresa.getChildText("nome"));
			tfIp.setText(empresa.getChildText("ipServidor"));
			tfPortaHttp.setText(empresa.getChildText("portaHttp"));
			tfPortaMysql.setText(empresa.getChildText("portaMysql"));
			*/
		}
		return retorno;
	}
}
