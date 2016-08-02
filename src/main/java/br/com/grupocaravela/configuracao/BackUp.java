package br.com.grupocaravela.configuracao;

import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class BackUp {

	private String[] dumps;

	public void salvarBackup() {
		JFileChooser jFileChooser = new JFileChooser();

		jFileChooser.setDialogTitle("Informe o diretório e nome do backup");

		// seta para selecionar apenas arquivos
		jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		// desabilita todos os tipos de arquivos
		jFileChooser.setAcceptAllFileFilterUsed(false);
		/*
		 * //filtra por extensao jFileChooser.setFileFilter(new FileFilter() {
		 * 
		 * @Override public String getDescription() { return "Extensão PDF"; }
		 * 
		 * @Override public boolean accept(File f) { return
		 * f.getName().toLowerCase().endsWith("pdf"); } });
		 */

		// mostra janela para salvar
		int acao = jFileChooser.showSaveDialog(null);

		// executa acao conforme opcao selecionada
		if (acao == JFileChooser.APPROVE_OPTION) {
			// escolheu arquivo
			System.out.println(jFileChooser.getSelectedFile().getAbsolutePath());
			
			salvar(jFileChooser.getSelectedFile().getAbsolutePath());
			//File f = new File(salvar(), jFileChooser.getSelectedFile().getAbsolutePath());
			
		} else if (acao == JFileChooser.CANCEL_OPTION) {
			// apertou botao cancelar
		} else if (acao == JFileChooser.ERROR_OPTION) {
			// outra opcao
		}
	}

	public void salvar(String nome) {

		dumps = null;
		
		if ("Linux".equals(System.getProperty("os.name"))) {
			dumps = new String[] {"/bin/sh", "-c", "mysqldump -h " + Empresa.getIpServidor() + " -u root -ppeperoni gestor > " + nome + ".sql"};
		} else {
			
			if ((System.getProperty("sun.arch.data.model")).equals("32")) {
				dumps = new String[] {"cmd.exe", "/c", "C:\\GrupoCaravela\\gestor\\backup\\x32\\mysqldump.exe -h " + Empresa.getIpServidor() + " -u root -ppeperoni gestor > " + nome + ".sql"};
			}
			
			if ((System.getProperty("sun.arch.data.model")).equals("64")) {
				dumps = new String[] {"cmd.exe", "/c", "C:\\GrupoCaravela\\gestor\\backup\\x64\\mysqldump.exe -h " + Empresa.getIpServidor() + " -u root -ppeperoni gestor > " + nome + ".sql"};
			}			
			
		}

		try {

			Runtime bkp = Runtime.getRuntime();
			bkp.exec(dumps);

			JOptionPane.showMessageDialog(null, "Backup realizado com sucesso!");
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "erro" + ex.getMessage());
		}
	}
	
	public void uploadBackup() {
		JFileChooser jFileChooser = new JFileChooser();

		jFileChooser.setDialogTitle("Informe o diretório e nome do backup");

		// seta para selecionar apenas arquivos
		jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		// desabilita todos os tipos de arquivos
		jFileChooser.setAcceptAllFileFilterUsed(false);
		/*
		 * //filtra por extensao jFileChooser.setFileFilter(new FileFilter() {
		 * 
		 * @Override public String getDescription() { return "Extensão PDF"; }
		 * 
		 * @Override public boolean accept(File f) { return
		 * f.getName().toLowerCase().endsWith("pdf"); } });
		 */

		// mostra janela para salvar
		int acao = jFileChooser.showSaveDialog(null);

		// executa acao conforme opcao selecionada
		if (acao == JFileChooser.APPROVE_OPTION) {
			// escolheu arquivo
			System.out.println(jFileChooser.getSelectedFile().getAbsolutePath());
			
			//File selectedFile = jFileChooser.getSelectedFile();
			
			upload(jFileChooser.getSelectedFile().getAbsolutePath());
			//File f = new File(salvar(), jFileChooser.getSelectedFile().getAbsolutePath());
			
		} else if (acao == JFileChooser.CANCEL_OPTION) {
			// apertou botao cancelar
		} else if (acao == JFileChooser.ERROR_OPTION) {
			// outra opcao
		}
	}
	
	private void upload(String nome){
		
		dumps = null;
		
		if ("Linux".equals(System.getProperty("os.name"))) {
			dumps = new String[] {"/bin/sh", "-c","mysql -h " + Empresa.getIpServidor() + " -u root -ppeperoni gestor < " + nome};
		} else {
			dumps = new String[] {"cmd.exe", "/c", "C:\\GrupoCaravela\\mysql.exe -h " + Empresa.getIpServidor() + " -u root -ppeperoni gestor < " + nome};
		}

		try {

			Runtime bkp = Runtime.getRuntime();
			bkp.exec(dumps);

			JOptionPane.showMessageDialog(null, "Upload realizado com sucesso!");
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "erro" + ex.getMessage());
		}		
	}

	private java.util.Date dataAtual() {

		java.util.Date hoje = new java.util.Date();
		return hoje;
	}
	
	
}
