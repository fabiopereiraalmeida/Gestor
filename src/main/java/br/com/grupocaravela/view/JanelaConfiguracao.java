package br.com.grupocaravela.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import br.com.grupocaravela.configuracao.Empresa;

public class JanelaConfiguracao extends JFrame {

	private JPanel contentPane;
	private JTextField tfIp;
	private JPanel panel_1;
	private JButton btnConfirmar;
	private JButton btnCancelar;
	private JTextField tfPortaHttp;
	private JLabel lblPorta;
	private JTextField tfPortaMysql;
	private JTextField tfNome;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaConfiguracao frame = new JanelaConfiguracao();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JanelaConfiguracao() {
		carregarJanela();

		try {
			lerArquivoXml();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo info.xml: " + e.getMessage());
		}
		
	}

	private void carregarJanela() {
		setTitle("Configurações");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 690, 481);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		panel_1 = new JPanel();

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE)
				.addComponent(tabbedPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 646, Short.MAX_VALUE));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE).addGap(18)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)));

		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("Configuração do servidor", null, panel_4, null);

		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(null, "Configura\u00E7\u00E3o do servidor", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Informa\u00E7\u00F5es", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_5, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)
						.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_5, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(139, Short.MAX_VALUE))
		);
		
		JLabel lblNome = new JLabel("Nome:");
		
		tfNome = new JTextField();
		tfNome.setToolTipText("Nome da empresa/pessoa que adquiriu a licença do sistema");
		tfNome.setColumns(10);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(tfNome, GroupLayout.PREFERRED_SIZE, 464, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNome))
					.addContainerGap(163, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNome)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tfNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);

		JLabel lblIp = new JLabel("IP:");

		tfIp = new JTextField();
		tfIp.setToolTipText("IP do servidor MYSQL");
		tfIp.setColumns(10);

		lblPorta = new JLabel("Porta HTTP:");

		tfPortaHttp = new JTextField();
		tfPortaHttp.setToolTipText("Porta padrão: 80");
		tfPortaHttp.setColumns(10);

		tfPortaMysql = new JTextField();
		tfPortaMysql.setToolTipText("Porta padrão: 3306");
		tfPortaMysql.setColumns(10);

		JLabel lblPortaMysql = new JLabel("Porta MySQL:");
		GroupLayout gl_panel_5 = new GroupLayout(panel_5);
		gl_panel_5.setHorizontalGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING).addComponent(lblIp)
								.addComponent(tfIp, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
						.addComponent(tfPortaHttp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPorta))
				.addGap(18)
				.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING).addComponent(lblPortaMysql).addComponent(
						tfPortaMysql, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(291, Short.MAX_VALUE)));
		gl_panel_5.setVerticalGroup(gl_panel_5.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING,
				gl_panel_5.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_5.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel_5.createSequentialGroup()
										.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblPorta).addComponent(lblPortaMysql))
								.addGap(6)
								.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
										.addComponent(tfPortaHttp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(tfPortaMysql, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel_5.createSequentialGroup().addComponent(lblIp).addGap(6).addComponent(tfIp,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(45, Short.MAX_VALUE)));
		panel_5.setLayout(gl_panel_5);
		panel_4.setLayout(gl_panel_4);

		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				criarArquivoXml();

				// escreverArquivoNomeEmpresa(tfFantasia.getText());
				// escreverArquivoIp(tfIp.getText());
				// escreverArquivoPorta(tfPortaHttp.getText());

				// Empresa.setIpServidor(lerArquivoIp());

				JOptionPane.showMessageDialog(null,
						"O sistema será fechado para que as configurações sejam aplicadas!");

				System.exit(0);

			}
		});
		btnConfirmar.setIcon(
				new ImageIcon(JanelaConfiguracao.class.getResource("/br/com/grupocaravela/icones/alerta_verde_24.png")));

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setIcon(new ImageIcon(
				JanelaConfiguracao.class.getResource("/br/com/grupocaravela/icones/alerta_amarelo_24.png")));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap(427, Short.MAX_VALUE)
					.addComponent(btnCancelar)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnConfirmar)
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap(16, Short.MAX_VALUE)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnConfirmar, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnCancelar, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		panel_1.setLayout(gl_panel_1);
		contentPane.setLayout(gl_contentPane);
	}
	

	private void criarArquivoXml() {

		Document docConfig = new Document();

		Element configuracao = new Element("configuracao");

		Element empresa = new Element("empresa");

		Element nome = new Element("nome");
		nome.setText(tfNome.getText());
		Empresa.setNomeEpresa(nome.getText());


		Element ipServidor = new Element("ipServidor");
		ipServidor.setText(tfIp.getText());
		Empresa.setIpServidor(tfIp.getText());

		Element portaHttp = new Element("portaHttp");
		portaHttp.setText(tfPortaHttp.getText());
		Empresa.setPortaHttpServidor(tfPortaHttp.getText());

		Element portaMysql = new Element("portaMysql");
		portaMysql.setText(tfPortaMysql.getText());
		Empresa.setPortaMysqlServidor(tfPortaMysql.getText());

		empresa.addContent(nome);
		
		empresa.addContent(ipServidor);
		empresa.addContent(portaHttp);
		empresa.addContent(portaMysql);

		configuracao.addContent(empresa);

		docConfig.setRootElement(configuracao);

		XMLOutputter xout = new XMLOutputter();

		try {

			// Criando o arquivo de saida

			FileWriter arquivo = null;

			if ("Linux".equals(System.getProperty("os.name"))) { // Verifica se
																	// o sistema
																	// é linux
				arquivo = new FileWriter(new File("/opt/GrupoCaravela/gestor/conf.xml"));
			} else {
				arquivo = new FileWriter(new File("c:\\GrupoCaravela\\gestor\\conf.xml"));
			}

			xout.output(docConfig, arquivo);

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	private void lerArquivoXml() {

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

			tfNome.setText(empresa.getChildText("nome"));
			tfIp.setText(empresa.getChildText("ipServidor"));
			tfPortaHttp.setText(empresa.getChildText("portaHttp"));
			tfPortaMysql.setText(empresa.getChildText("portaMysql"));
		}
	}	
}
