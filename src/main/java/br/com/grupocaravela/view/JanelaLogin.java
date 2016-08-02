package br.com.grupocaravela.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import br.com.grupocaravela.aguarde.EsperaJanela;
import br.com.grupocaravela.configuracao.Empresa;
import br.com.grupocaravela.configuracao.EntityManagerProducer;
import br.com.grupocaravela.entidade.Usuario;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class JanelaLogin extends JFrame {

	private JPanel contentPane;
	private JTextField tfLogin;
	
	private EntityManagerProducer entityManagerProducer = new EntityManagerProducer();
	private EntityManager manager;
	private EntityTransaction trx;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaLogin frame = new JanelaLogin();
					frame.setSize(454, 388);
					frame.setResizable(false);
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
	public JanelaLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 454, 388);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 418, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
		);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(JanelaLogin.class.getResource("/br/com/grupocaravela/imagens/logo-gestor_400x140.png")));
		
		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setBackground(Color.WHITE);
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setHorizontalAlignment(SwingConstants.CENTER);
		
		tfLogin = new JTextField();
		tfLogin.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfLogin.selectAll();
			}
		});
		tfLogin.setHorizontalAlignment(SwingConstants.CENTER);
		tfLogin.setColumns(10);
		
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logar();
			}			
				
		});
		btnEntrar.setIcon(new ImageIcon(JanelaLogin.class.getResource("/br/com/grupocaravela/icones/alerta_verde_24.png")));
		
		JLabel lblGrupoCaravele = new JLabel("Grupo Caravele - www.grupocaravela.com.br");
		lblGrupoCaravele.setHorizontalAlignment(SwingConstants.CENTER);
		lblGrupoCaravele.setFont(new Font("Dialog", Font.BOLD, 8));
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// #############################################
				final Thread tr = new Thread(new Runnable() {
					@Override
					public void run() {
						try {

							JanelaConfiguracao jConf = new JanelaConfiguracao();
							jConf.setVisible(true);
							jConf.setLocationRelativeTo(null);
							
							dispose();

						} catch (Exception ex) {

							JOptionPane.showMessageDialog(null,
									"Erro na tentativa de conexão com o servidor!!! Verifique a conexão! " + ex);
						}

					}
				});

				new Thread(new Runnable() {
					@Override
					public void run() {
						tr.start();
						// .....
						// BootSplash bootSplash = new BootSplash();
						EsperaJanela bootSplash = new EsperaJanela();
						bootSplash.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
						// bootSplash.setUndecorated(true);
						// bootSplash.setBackground(new Color(0, 0, 0, 0));
						bootSplash.setVisible(true);
						bootSplash.setLocationRelativeTo(null);
						try {
							tr.join();
							bootSplash.dispose();

						} catch (InterruptedException ex) {
							// Logger.getLogger(MenuView.class.getName()).log(Level.SEVERE,
							// null, ex);
						}
					}
				}).start();
				
			}
		});
		button.setIcon(new ImageIcon(JanelaLogin.class.getResource("/br/com/grupocaravela/icones/configure_24.png")));
		button.setBackground(new Color(0, 0, 0, 0));
		button.setBorder(null);
		
		passwordField = new JPasswordField();
		passwordField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				passwordField.selectAll();
			}
		});
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					logar();
				}
			}
		});
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblLogin, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
						.addComponent(lblNewLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(161)
					.addComponent(tfLogin, 102, 102, 102)
					.addGap(161))
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblSenha, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(122)
					.addComponent(btnEntrar, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(button, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
					.addGap(64))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(69)
					.addComponent(lblGrupoCaravele, GroupLayout.PREFERRED_SIZE, 286, Short.MAX_VALUE)
					.addGap(69))
				.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
					.addGap(161)
					.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(161, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblLogin)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tfLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblSenha)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnEntrar)
						.addComponent(button, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
					.addComponent(lblGrupoCaravele))
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
		
		logo();
		
		try {
			carregarObjetoEmpresa();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao carregar o arquivo info.xml: " + e.getMessage());
		}
		
		try {
			iniciaConexao();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao iniciar a conexão: " + e.getMessage());
		}
		
		
		//JOptionPane.showMessageDialog(null, System.getProperty("os.arch"));
		//JOptionPane.showMessageDialog(null, System.getProperty("sun.arch.data.model")); // 32/64
	}
	
	private void logo() {
        //Mudando o ícone da barra de títulos...  
        URL url = this.getClass().getResource("/br/com/grupocaravela/imagens/logo_24.png");
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(url));
    }
	
private void carregarObjetoEmpresa(){
		
		File arquivo = null;	

   	 	if ("Linux".equals(System.getProperty("os.name"))) {  //Verifica se o sistema é linux
   		 arquivo = new File("/opt/GrupoCaravela/gestor/conf.xml");
        } else {
       	 arquivo = new File("c:\\GrupoCaravela\\gestor\\conf.xml");            	 
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
		             
		while( i.hasNext() ){
		        Element empresa = (Element) i.next();
		        
		        Empresa.setNomeEpresa(empresa.getChildText("fantasia"));
		        Empresa.setIpServidor(empresa.getChildText("ipServidor"));
		        Empresa.setPortaHttpServidor(empresa.getChildText("portaHttp"));
		        Empresa.setPortaMysqlServidor(empresa.getChildText("portaMysql"));		        
		}		
	}

private void iniciaConexao() {
	manager = entityManagerProducer.createEntityManager();
	trx = manager.getTransaction();
}

private void verificaExistenciaUsuario() {

	try {
		Query consulta = manager.createQuery("FROM Usuario WHERE usuario LIKE 'admin' AND senha LIKE 'admin'");
		List<Usuario> listaUsuario = consulta.getResultList();

		if (listaUsuario.isEmpty()) {
			Usuario u = new Usuario();
						
			u.setAdministrador(true);
			u.setComum(false);
			u.setConsultor(false);
			u.setNome("Administrador");
			u.setSenha("admin");
			u.setUsuario("admin");

			trx.begin();
			manager.persist(u);
			trx.commit();

		}

		for (int i = 0; i < listaUsuario.size(); i++) {
			Usuario u = listaUsuario.get(i);

		}
	} catch (Exception e) {
		JOptionPane.showMessageDialog(null, "Erro ao verificar e criar usuario inicial: " + e);
	}
}

private Boolean verificaLogin(String usuario, String senha){
	Boolean retorno = false;

	try {
		Query consulta = manager
				.createQuery("FROM Usuario WHERE usuario LIKE '" + usuario + "' AND senha LIKE '" + senha + "'");
		List<Usuario> listaUsuarios = consulta.getResultList();

		Usuario u = listaUsuarios.get(0);

		retorno = true;
	} catch (Exception e) {
		JOptionPane.showMessageDialog(null, "Ususario/Senha incorreto!");
		retorno = false;
	}

	return retorno;
}

private void logar(){
	//iniciaConexao();
	
	verificaExistenciaUsuario();
		
	if (verificaLogin(tfLogin.getText(), passwordField.getText())) {
					
	// #############################################
	final Thread tr = new Thread(new Runnable() {
		@Override
		public void run() {
			try {

				JanelaMenuPrincipal loginView = new JanelaMenuPrincipal();
				loginView.setVisible(true);
				loginView.setLocationRelativeTo(null);
				
				manager.close();//fecha a conexao
				dispose();

			} catch (Exception ex) {

				JOptionPane.showMessageDialog(null,
						"Erro na tentativa de conexão com o servidor!!! Verifique a conexão! " + ex);
			}

		}
	});

	new Thread(new Runnable() {
		@Override
		public void run() {
			tr.start();
			// .....
			// BootSplash bootSplash = new BootSplash();
			EsperaJanela bootSplash = new EsperaJanela();
			bootSplash.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
			bootSplash.setUndecorated(true);
			bootSplash.setBackground(new Color(0, 0, 0, 0));
			bootSplash.setVisible(true);
			bootSplash.setLocationRelativeTo(null);
			try {
				tr.join();
				bootSplash.dispose();

			} catch (InterruptedException ex) {
				// Logger.getLogger(MenuView.class.getName()).log(Level.SEVERE,
				// null, ex);
			}
		}
	}).start();
	}else{
		JOptionPane.showMessageDialog(null, "Usuario/Senha incorreto(s)");
		tfLogin.requestFocus();
		tfLogin.selectAll();
	
}
}
}
