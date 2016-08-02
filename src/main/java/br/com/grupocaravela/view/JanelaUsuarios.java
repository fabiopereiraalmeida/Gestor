package br.com.grupocaravela.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.text.NumberFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

import br.com.grupocaravela.aguarde.EsperaLista;
import br.com.grupocaravela.configuracao.EntityManagerProducer;
import br.com.grupocaravela.entidade.Usuario;
import br.com.grupocaravela.render.DateRenderer;
import br.com.grupocaravela.render.MoedaRender;
import br.com.grupocaravela.tableModel.TableModelUsuario;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class JanelaUsuarios extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField tfId;
	private JTextField tfNome;
	
	private TableModelUsuario tableModelUsuario;
	
	private EntityManagerProducer entityManagerProducer = new EntityManagerProducer();
	private EntityManager manager;
	private EntityTransaction trx;
	private JTextField tfLocalizar;
	private JTabbedPane tabbedPane;

	private Usuario usuario;
	private JTextField tfUsuario;
	private JPasswordField jpfSenha;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbAdministrador;
	private JRadioButton rdbNormal;
	private JRadioButton rdbSomenteConsulta;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaUsuarios frame = new JanelaUsuarios();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public JanelaUsuarios() {			
		carregarJanela();
		logo();
		
		carregarTableModel();
		
		tamanhoColunas();
		
		iniciaConexao();
		
	}
	
	/**
	 * Creando a janela
	 */
	public void carregarJanela(){
		setTitle("Usuários");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
		);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 788, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
		);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Lista de usuários", null, panel_1, null);
		
		JScrollPane scrollPane = new JScrollPane();
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					botaoDetalhes();
				}
			}
		});
		scrollPane.setViewportView(table);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JLabel label = new JLabel("Localizar:");
		
		tfLocalizar = new JTextField();
		tfLocalizar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					botaoBuscar();
				}
			}
		});
		tfLocalizar.setColumns(10);
		
		JButton button = new JButton("Filtrar");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				botaoBuscar();			
				
			}
		});
		button.setIcon(new ImageIcon(JanelaUsuarios.class.getResource("/br/com/grupocaravela/icones/lupa_24.png")));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 759, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 759, Short.MAX_VALUE)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfLocalizar, GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(button, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(label)
						.addComponent(tfLocalizar, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(button, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		JButton btnNovo = new JButton("Novo");
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				usuario = new Usuario();
				
				limparCampos();
				tabbedPane.setSelectedIndex(1);
			}
		});
		btnNovo.setIcon(new ImageIcon(JanelaUsuarios.class.getResource("/br/com/grupocaravela/icones/novo_24.png")));
		
		JButton btnDetalhes = new JButton("Detalhes");
		btnDetalhes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				botaoDetalhes();	
				
			}
		});
		btnDetalhes.setIcon(new ImageIcon(JanelaUsuarios.class.getResource("/br/com/grupocaravela/icones/alterar_24.png")));
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Object[] options = { "Sim", "Não" };
				int i = JOptionPane.showOptionDialog(null,
						"ATENÇÃO!!! Esta operação irá excluir o crédito selecionado! Gostaria de continuar?",
						"Excluir", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
						options[0]);

				if (i == JOptionPane.YES_OPTION) {
					
					int linha = table.getSelectedRow();
			        int linhaReal = table.convertRowIndexToModel(linha);		
			        
			        Usuario c = tableModelUsuario.getUsuario(linhaReal);
			        
			        excluirUsuario(c);
			        
					limparCampos();
				}	
				
			}
		});
		btnExcluir.setIcon(new ImageIcon(JanelaUsuarios.class.getResource("/br/com/grupocaravela/icones/alerta_vermelho_24.png")));
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_3.createSequentialGroup()
					.addContainerGap(451, Short.MAX_VALUE)
					.addComponent(btnExcluir)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnDetalhes)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNovo)
					.addContainerGap())
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_3.createSequentialGroup()
					.addContainerGap(50, Short.MAX_VALUE)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNovo)
						.addComponent(btnDetalhes)
						.addComponent(btnExcluir))
					.addContainerGap())
		);
		panel_3.setLayout(gl_panel_3);
		panel_1.setLayout(gl_panel_1);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Detalhes do usuário", null, panel_2, null);
		
		JLabel lblId = new JLabel("Id:");
		
		tfId = new JTextField();
		tfId.setEnabled(false);
		tfId.setEditable(false);
		tfId.setColumns(10);
		
		tfNome = new JTextField();
		tfNome.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome:");
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JLabel lblUsurio = new JLabel("Usuário:");
		
		tfUsuario = new JTextField();
		tfUsuario.setColumns(10);
		
		JLabel lblSenha = new JLabel("Senha:");
		
		jpfSenha = new JPasswordField();
		
		rdbSomenteConsulta = new JRadioButton("Somente consulta");
		rdbSomenteConsulta.setSelected(true);
		buttonGroup.add(rdbSomenteConsulta);
		
		rdbNormal = new JRadioButton("Normal");
		buttonGroup.add(rdbNormal);
		
		rdbAdministrador = new JRadioButton("Administrador");
		buttonGroup.add(rdbAdministrador);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(rdbSomenteConsulta)
							.addGap(18)
							.addComponent(rdbNormal)
							.addGap(18)
							.addComponent(rdbAdministrador))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(tfId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblId))
							.addGap(18)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_2.createSequentialGroup()
									.addComponent(lblNome)
									.addGap(529))
								.addComponent(tfNome, GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)))
						.addComponent(panel_6, GroupLayout.DEFAULT_SIZE, 759, Short.MAX_VALUE)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(tfUsuario, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblUsurio))
							.addGap(18)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(lblSenha)
								.addComponent(jpfSenha, GroupLayout.PREFERRED_SIZE, 222, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblId)
						.addComponent(lblNome))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tfNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUsurio)
						.addComponent(lblSenha))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfUsuario, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(jpfSenha, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(rdbSomenteConsulta)
						.addComponent(rdbNormal)
						.addComponent(rdbAdministrador))
					.addPreferredGap(ComponentPlacement.RELATED, 216, Short.MAX_VALUE)
					.addComponent(panel_6, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Object[] options = { "Sim", "Não" };
				int i = JOptionPane.showOptionDialog(null,
						"Gostaria de salvar o usuario com o nome " + tfNome.getText() + "?", "Salvar",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

				if (i == JOptionPane.YES_OPTION) {

					salvarUsuario(usuario);
					
					tabbedPane.setSelectedIndex(0);
					limparCampos();
				}
				
			}
		});
		btnSalvar.setIcon(new ImageIcon(JanelaUsuarios.class.getResource("/br/com/grupocaravela/icones/alerta_verde_24.png")));
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				tabbedPane.setSelectedIndex(0);
				limparCampos();
				usuario = null;
				
			}
		});
		btnCancelar.setIcon(new ImageIcon(JanelaUsuarios.class.getResource("/br/com/grupocaravela/icones/alerta_amarelo_24.png")));
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				tabbedPane.setSelectedIndex(0);
				limparCampos();				
				
			}
		});
		btnVoltar.setIcon(new ImageIcon(JanelaUsuarios.class.getResource("/br/com/grupocaravela/icones/voltar_24.png")));
		GroupLayout gl_panel_6 = new GroupLayout(panel_6);
		gl_panel_6.setHorizontalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_6.createSequentialGroup()
					.addContainerGap(444, Short.MAX_VALUE)
					.addComponent(btnVoltar)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnCancelar)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSalvar)
					.addContainerGap())
		);
		gl_panel_6.setVerticalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_6.createSequentialGroup()
					.addContainerGap(35, Short.MAX_VALUE)
					.addGroup(gl_panel_6.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSalvar)
						.addComponent(btnCancelar)
						.addComponent(btnVoltar))
					.addContainerGap())
		);
		panel_6.setLayout(gl_panel_6);
		panel_2.setLayout(gl_panel_2);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
	}
	
	private void carregarTableModel() {
		this.tableModelUsuario = new TableModelUsuario();
		this.table.setModel(tableModelUsuario);
		
		/*
		//Render numeros moeda
		NumberFormat numeroMoeda = NumberFormat.getNumberInstance();
		numeroMoeda.setMinimumFractionDigits(2);

		DefaultTableCellRenderer cellRendererCustomMoeda = new MoedaRender(numeroMoeda);
		table.getColumnModel().getColumn(2).setCellRenderer(cellRendererCustomMoeda);
		table.getColumnModel().getColumn(2).setCellRenderer(cellRendererCustomMoeda);
						
		// Render das datas
		table.getColumnModel().getColumn(3).setCellRenderer(new DateRenderer());
		table.getColumnModel().getColumn(3).setCellRenderer(new DateRenderer());
		*/
		
		//Ordenação
		table.setAutoCreateRowSorter(true);
		
	}
	
	private void limparTabela() {
		while (table.getModel().getRowCount() > 0) {
			tableModelUsuario.removeUsuario(0);
		}
	}
	
	private void iniciaConexao() {
		manager = entityManagerProducer.createEntityManager();
		trx = manager.getTransaction();
	}
	
	private void buscarNome(String nome){		
		try {
			Query consulta = manager
					.createQuery("FROM Usuario WHERE nome LIKE '%" + nome + "%' ORDER BY nome ASC");
			List<Usuario> listaUsuarios = consulta.getResultList();

			for (int i = 0; i < listaUsuarios.size(); i++) {
				Usuario u = listaUsuarios.get(i);
				tableModelUsuario.addUsuario(u);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao carregar a tabela de usuarios: " + e);
		}		
	}
	
	private void limparCampos(){
		
		tfId.setText("");
		tfNome.setText("");
		tfUsuario.setText("");
		jpfSenha.setText("");
		rdbSomenteConsulta.setSelected(true);
		rdbNormal.setSelected(false);
		rdbAdministrador.setSelected(false);
	}
	
	private void carregarCampos(Usuario u){
		
		tfId.setText(u.getId().toString());
		tfNome.setText(u.getNome());
		tfUsuario.setText(u.getUsuario());
		jpfSenha.setText(u.getSenha());
		
		if (u.getConsultor()) {
			rdbSomenteConsulta.setSelected(true);
		}
		
		if (u.getComum()) {
			rdbNormal.setSelected(true);
		}
		
		if (u.getAdministrador()) {
			rdbAdministrador.setSelected(true);
		}
				
	}
	
	private void excluirUsuario(Usuario u){		
		
        try {
			trx.begin();
			manager.remove(u);
			trx.commit();

			JOptionPane.showMessageDialog(null, "O usuario foi excluido com sucesso!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERRO! " + e);
		}		
	}
	
	private void salvarUsuario(Usuario u){
		try {
						
			u.setNome(tfNome.getText());
			u.setUsuario(tfUsuario.getText());
			u.setSenha(jpfSenha.getText());
			
			if (rdbSomenteConsulta.isSelected()) {
				u.setConsultor(true);
				u.setComum(false);
				u.setAdministrador(false);
			}
			
			if (rdbNormal.isSelected()) {
				u.setConsultor(false);
				u.setComum(true);
				u.setAdministrador(false);
			}
			
			if (rdbAdministrador.isSelected()) {
				u.setConsultor(false);
				u.setComum(false);
				u.setAdministrador(true);
			}
			
			trx.begin();
			manager.persist(u);
			trx.commit();

			JOptionPane.showMessageDialog(null, "O usuario " + tfNome.getText() + " foi salvo com sucesso!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERRO! " + e);
		}
	}
	
	private void botaoDetalhes(){
		// #############################################
		final Thread tr = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(0);
				} catch (InterruptedException ex) {
					Logger.getLogger(JanelaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
				}
				
				limparCampos();
				
				int linha = table.getSelectedRow();
		        int linhaReal = table.convertRowIndexToModel(linha);		
		        usuario = tableModelUsuario.getUsuario(linhaReal);
				
				carregarCampos(usuario);
				tabbedPane.setSelectedIndex(1);
				
			}
		});
		new Thread(new Runnable() {
			@Override
			public void run() {
				tr.start();
				// .....
				EsperaLista espera = new EsperaLista();
				espera.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
				espera.setUndecorated(true);
				espera.setVisible(true);
				espera.setLocationRelativeTo(null);
				try {
					tr.join();
					espera.dispose();

				} catch (InterruptedException ex) {
					// Logger.getLogger(MenuView.class.getName()).log(Level.SEVERE,
					// null, ex);
				}
			}
		}).start();

		// ###############################################		
	}
	
	private void tamanhoColunas() {
		// tableProdutos.setAutoResizeMode(tableProdutos.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setWidth(40);
		table.getColumnModel().getColumn(0).setMaxWidth(60);
		//table.getColumnModel().getColumn(2).setMinWidth(90);
		//table.getColumnModel().getColumn(2).setMaxWidth(100);
		//table.getColumnModel().getColumn(3).setMinWidth(90);
		//table.getColumnModel().getColumn(3).setMaxWidth(100);		
	}
	
	private void logo() {
        //Mudando o ícone da barra de títulos...  
        URL url = this.getClass().getResource("/br/com/grupocaravela/imagens/logo_24.png");
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(url));
    }
	
	private void botaoBuscar(){
		// #############################################
		final Thread tr = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(0);
				} catch (InterruptedException ex) {
					Logger.getLogger(JanelaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
				}
				// ######################METODO A SER
				// EXECUTADO##############################
				limparTabela();

				buscarNome(tfLocalizar.getText());
				
			}
		});
		new Thread(new Runnable() {
			@Override
			public void run() {
				tr.start();
				// .....
				EsperaLista espera = new EsperaLista();
				espera.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
				espera.setUndecorated(true);
				espera.setVisible(true);
				espera.setLocationRelativeTo(null);
				try {
					tr.join();
					espera.dispose();

				} catch (InterruptedException ex) {
					// Logger.getLogger(MenuView.class.getName()).log(Level.SEVERE,
					// null, ex);
				}
			}
		}).start();

		// ###############################################	
	}
}
