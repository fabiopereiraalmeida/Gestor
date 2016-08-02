package br.com.grupocaravela.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import br.com.grupocaravela.aguarde.EsperaLista;
import br.com.grupocaravela.configuracao.EntityManagerProducer;
import br.com.grupocaravela.entidade.Entidade;
import br.com.grupocaravela.entidade.Pessoa;
import br.com.grupocaravela.render.DateRenderer;
import br.com.grupocaravela.render.MoedaRender;
import br.com.grupocaravela.tableModel.TableModelEntidade;

import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.text.NumberFormat;
import javax.swing.JCheckBox;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class JanelaEntidade extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField tfId;
	private JTextField tfNome;
	
	private TableModelEntidade tableModelEntidade;
	
	private EntityManagerProducer entityManagerProducer = new EntityManagerProducer();
	private EntityManager manager;
	private EntityTransaction trx;
	private JTextField tfLocalizar;
	private JDateChooser dcData;
	private JTextPane tpObs;
	private JTabbedPane tabbedPane;

	private Entidade entidade;
	private JCheckBox chckbxFilho;
	private JComboBox cbEntidade;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaEntidade frame = new JanelaEntidade();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public JanelaEntidade() {			
		carregarJanela();
		logo();
		
		carregarTableModel();
		
		tamanhoColunas();
		
		iniciaConexao();	
		
		carregajcbEntidade();
	}
	
	/**
	 * Creando a janela
	 */
	public void carregarJanela(){
		setTitle("Entidades");
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
		tabbedPane.addTab("Lista de entidades", null, panel_1, null);
		
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
		button.setIcon(new ImageIcon(JanelaEntidade.class.getResource("/br/com/grupocaravela/icones/lupa_24.png")));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 759, Short.MAX_VALUE)
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
				
				entidade = new Entidade();
				
				limparCampos();
				dcData.setDate(dataAtual());
				tabbedPane.setSelectedIndex(1);
			}
		});
		btnNovo.setIcon(new ImageIcon(JanelaEntidade.class.getResource("/br/com/grupocaravela/icones/novo_24.png")));
		
		JButton btnDetalhes = new JButton("Detalhes");
		btnDetalhes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				botaoDetalhes();	
				
			}
		});
		btnDetalhes.setIcon(new ImageIcon(JanelaEntidade.class.getResource("/br/com/grupocaravela/icones/alterar_24.png")));
		
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
			        
			        Entidade c = tableModelEntidade.getEntidade(linhaReal);
			        
			        excluirEntidade(c);
			        
					limparCampos();
				}	
				
			}
		});
		btnExcluir.setIcon(new ImageIcon(JanelaEntidade.class.getResource("/br/com/grupocaravela/icones/alerta_vermelho_24.png")));
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
		tabbedPane.addTab("Detalhes da entidade", null, panel_2, null);
		
		JLabel lblId = new JLabel("Id:");
		
		tfId = new JTextField();
		tfId.setEnabled(false);
		tfId.setEditable(false);
		tfId.setColumns(10);
		
		tfNome = new JTextField();
		tfNome.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome:");
		
		JLabel lblData = new JLabel("Data cadastro:");
		
		dcData = new JDateChooser();
		
		JLabel lblObs = new JLabel("Obs:");
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		chckbxFilho = new JCheckBox("Filho");
		chckbxFilho.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if (chckbxFilho.isSelected()) {
					cbEntidade.setEnabled(true);
				}else{
					cbEntidade.setEnabled(false);
				}				
			}
		});
		
		cbEntidade = new JComboBox();
		cbEntidade.setEnabled(false);
		
		JLabel lblEntidadePai = new JLabel("Entidade pai:");
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 759, Short.MAX_VALUE)
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
						.addComponent(lblObs)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_2.createSequentialGroup()
									.addComponent(dcData, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(chckbxFilho))
								.addComponent(lblData))
							.addGap(18)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(lblEntidadePai)
								.addComponent(cbEntidade, GroupLayout.PREFERRED_SIZE, 396, GroupLayout.PREFERRED_SIZE))))
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
						.addComponent(lblData)
						.addComponent(lblEntidadePai))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(dcData, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblObs))
						.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
							.addComponent(chckbxFilho)
							.addComponent(cbEntidade, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel_6, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		tpObs = new JTextPane();
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addComponent(tpObs, GroupLayout.DEFAULT_SIZE, 757, Short.MAX_VALUE)
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addComponent(tpObs, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
		);
		panel_4.setLayout(gl_panel_4);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Object[] options = { "Sim", "Não" };
				int i = JOptionPane.showOptionDialog(null,
						"Gostaria de salvar a entidade com nome " + tfNome.getText() + "?", "Salvar",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

				if (i == JOptionPane.YES_OPTION) {

					salvarEntidade(entidade);
					
					tabbedPane.setSelectedIndex(0);
					limparCampos();
				}
				
			}
		});
		btnSalvar.setIcon(new ImageIcon(JanelaEntidade.class.getResource("/br/com/grupocaravela/icones/alerta_verde_24.png")));
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				tabbedPane.setSelectedIndex(0);
				limparCampos();
				entidade = null;
				
			}
		});
		btnCancelar.setIcon(new ImageIcon(JanelaEntidade.class.getResource("/br/com/grupocaravela/icones/alerta_amarelo_24.png")));
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				tabbedPane.setSelectedIndex(0);
				limparCampos();				
				
			}
		});
		btnVoltar.setIcon(new ImageIcon(JanelaEntidade.class.getResource("/br/com/grupocaravela/icones/voltar_24.png")));
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
		this.tableModelEntidade = new TableModelEntidade();
		this.table.setModel(tableModelEntidade);
		
		//Render numeros moeda
		NumberFormat numeroMoeda = NumberFormat.getNumberInstance();
		numeroMoeda.setMinimumFractionDigits(2);

		//DefaultTableCellRenderer cellRendererCustomMoeda = new MoedaRender(numeroMoeda);
		//table.getColumnModel().getColumn(2).setCellRenderer(cellRendererCustomMoeda);
		//table.getColumnModel().getColumn(2).setCellRenderer(cellRendererCustomMoeda);
						
		// Render das datas
		table.getColumnModel().getColumn(2).setCellRenderer(new DateRenderer());
		table.getColumnModel().getColumn(2).setCellRenderer(new DateRenderer());
		
		//Ordenação
		table.setAutoCreateRowSorter(true);
	}
	
	private void limparTabela() {
		while (table.getModel().getRowCount() > 0) {
			tableModelEntidade.removeEntidade(0);
		}
	}
	
	private void iniciaConexao() {
		manager = entityManagerProducer.createEntityManager();
		trx = manager.getTransaction();		
		
	}
	
	private void buscarNome(String nome){		
		try {

			Query consulta = manager
					.createQuery("FROM Entidade WHERE nome LIKE '%" + nome + "%'");
			List<Entidade> listaEntidades = consulta.getResultList();

			for (int i = 0; i < listaEntidades.size(); i++) {
				Entidade c = listaEntidades.get(i);
				tableModelEntidade.addEntidade(c);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao carregar a tabela de entidades: " + e);
		}		
	}
	
	private void limparCampos(){
		
		tfId.setText("");
		tfNome.setText("");		
		dcData.setDate(null);
		tpObs.setText("");
		chckbxFilho.setSelected(false);
		cbEntidade.setEnabled(false);
		cbEntidade.setSelectedIndex(-1);
	}
	
	private void carregarCampos(Entidade c){
		
		tfId.setText(c.getId().toString());
		tfNome.setText(c.getNome());
		dcData.setDate(c.getDataCriacao());
		tpObs.setText(c.getObs());
		
		try {
			if (c.getFilho() == true) {
				chckbxFilho.setSelected(true);
				cbEntidade.setEnabled(true);
				cbEntidade.setSelectedItem(c.getEntidade());
			}
		} catch (Exception e) {
			chckbxFilho.setSelected(false);
			cbEntidade.setEnabled(false);
			cbEntidade.setSelectedIndex(-1);
		}
			
	}
	
	private void excluirEntidade(Entidade c){
		
		//int linha = table.getSelectedRow();
        //int linhaReal = table.convertRowIndexToModel(linha);		
        //Entidade c = tableModelEntidade.getEntidade(linhaReal);
        
        try {
			trx.begin();
			manager.remove(c);
			trx.commit();

			JOptionPane.showMessageDialog(null, "O entidade foi excluida com sucesso!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERRO! " + e);
		}		
	}
	
	private void salvarEntidade(Entidade c){
		try {

			c.setDataCriacao(dcData.getDate());
			c.setNome(tfNome.getText());
			c.setObs(tpObs.getText());
			
			if (chckbxFilho.isSelected()) {
				c.setFilho(true);
				c.setEntidade((Entidade) cbEntidade.getSelectedItem());
			}else{
				c.setFilho(false);
				c.setEntidade(null);
			}
			
			trx.begin();
			manager.persist(c);
			trx.commit();

			JOptionPane.showMessageDialog(null, "A entidade foi salvo com sucesso!");						
			
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
					Logger.getLogger(JanelaEntidade.class.getName()).log(Level.SEVERE, null, ex);
				}
				
				limparCampos();
				
				int linha = table.getSelectedRow();
		        int linhaReal = table.convertRowIndexToModel(linha);		
		        entidade = tableModelEntidade.getEntidade(linhaReal);
				
				carregarCampos(entidade);
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
	
	private java.util.Date dataAtual() {

		java.util.Date hoje = new java.util.Date();
		return hoje;
	}
	
	private void tamanhoColunas() {
		// tableProdutos.setAutoResizeMode(tableProdutos.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setWidth(40);
		table.getColumnModel().getColumn(0).setMaxWidth(60);
		table.getColumnModel().getColumn(2).setMinWidth(90);
		table.getColumnModel().getColumn(2).setMaxWidth(100);		
	}
	
	private void logo() {
        //Mudando o ícone da barra de títulos...  
        URL url = this.getClass().getResource("/br/com/grupocaravela/imagens/logo_24.png");
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(url));
    }
	
	private void carregajcbEntidade() {

		cbEntidade.removeAllItems();
				
		try {

			Query consulta = manager.createQuery("FROM Entidade ORDER BY nome ASC");
			List<Entidade> listaEntidades = consulta.getResultList();

			for (int i = 0; i < listaEntidades.size(); i++) {

				Entidade e = listaEntidades.get(i);
				cbEntidade.addItem(e);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro no carregamento das entidades no CB_ENTIDADE_PAI! " + e);
		}
	}
	
	private void botaoBuscar(){
		// #############################################
		final Thread tr = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(0);
				} catch (InterruptedException ex) {
					Logger.getLogger(JanelaEntidade.class.getName()).log(Level.SEVERE, null, ex);
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
