package br.com.grupocaravela.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

import com.toedter.calendar.JDateChooser;

import br.com.grupocaravela.aguarde.EsperaJanela;
import br.com.grupocaravela.aguarde.EsperaLista;
import br.com.grupocaravela.configuracao.BackUp;
import br.com.grupocaravela.configuracao.EntityManagerProducer;
import br.com.grupocaravela.entidade.Credito;
import br.com.grupocaravela.entidade.Debito;
import br.com.grupocaravela.entidade.Entidade;
import br.com.grupocaravela.entidade.Pessoa;
import br.com.grupocaravela.mask.DecimalFormattedField;
import br.com.grupocaravela.relatorio.ChamaRelatorio;
import br.com.grupocaravela.render.DateRenderer;
import br.com.grupocaravela.render.MoedaRender;
import br.com.grupocaravela.tableModel.TableModelCredito;
import br.com.grupocaravela.tableModel.TableModelDebito;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class JanelaMenuPrincipal extends JFrame {

	private JPanel contentPane;
	
	private EntityManagerProducer entityManagerProducer = new EntityManagerProducer();
	private EntityManager manager;
	private EntityTransaction trx;
	private JTable tableCreditosPrincipal;
	private JTable tableDebitosPrincipal;
	
	private TableModelCredito tableModelCredito;
	private TableModelDebito tableModelDebito;
	
	private SimpleDateFormat formatDataSql = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat formatDataBra = new SimpleDateFormat("dd/MM/yyyy");

	private JTextField lblDebitos;

	private JTextField lblCreditos;

	private Double totalDebitos;

	private Double totalCreditos;

	private JTextField tfTotalLucros;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	private JComboBox cbEntidade;

	private JRadioButton rdbtnDebitos;

	private JRadioButton rdbtnCreditos;

	private JDateChooser dcDataInicial;

	private JDateChooser dcDataFinal;

	private JCheckBox chckbxFiltrarPorData;

	private JDateChooser dcInicial;

	private JDateChooser dcFinal;

	private JComboBox cbEntidades;

	private JDateChooser dcDataInicial2;

	private JDateChooser dcDataFinal2;

	private JComboBox cbEntidade2;

	private JCheckBox chckbxFiltrarPorData2;

	private JButton btnGerarRelatrio2;

	private JDateChooser dcDataFinal3;

	private JDateChooser dcDataInicial3;

	private JCheckBox chckbxFiltrarPorData3;

	private JComboBox cbPessoas;

	private JCheckBox chckbxFiltrarPai;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaMenuPrincipal frame = new JanelaMenuPrincipal();
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
	public JanelaMenuPrincipal() {
		
		carregarJanela();
		
		logo();
		
		iniciaConexao();
		
		carregarTableModel();
				
		tamanhoColunas();
		
		carregajcbEntidade();
		carregajcbPessoa();
		
		dcInicial.setDate(dataAtual());
		dcFinal.setDate(dataAtual());
		
		dcDataInicial.setDate(dataAtual());
		dcDataFinal.setDate(dataAtual());
		
		carregarCreditosHoje();
		carregarDebitossHoje();
		
		calcularLucros();
		
		verificarCbData();
		verificarCbData2();
		
		tfTotalLucros.setText("0");
		lblDebitos.setText("0");
		lblCreditos.setText("0");
		
	}
	
	private void carregarJanela(){
		setTitle("Gestor - Menu principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 632);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnArquivo = new JMenu("Arquivo");
		menuBar.add(mnArquivo);
		
		JMenuItem mntmCrditos = new JMenuItem("Créditos");
		mntmCrditos.setIcon(new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/money-Bag-icon_24.png")));
		mntmCrditos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// #############################################
				final Thread tr = new Thread(new Runnable() {
					@Override
					public void run() {
						//try {

							JanelaCredito jCredito = new JanelaCredito();
							jCredito.setVisible(true);
							jCredito.setLocationRelativeTo(null);

						//} catch (Exception ex) {

							//JOptionPane.showMessageDialog(null,"Erro na tentativa de conexão com o servidor!!! Verifique a conexão! " + ex);
						//}

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
				
			}
		});
		mnArquivo.add(mntmCrditos);
		
		JMenuItem mntmEntidade = new JMenuItem("Entidade");
		mntmEntidade.setIcon(new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/folder-Data-icon_24.png")));
		mntmEntidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// #############################################
				final Thread tr = new Thread(new Runnable() {
					@Override
					public void run() {
							JanelaEntidade jEntidade = new JanelaEntidade();
							jEntidade.setVisible(true);
							jEntidade.setLocationRelativeTo(null);

					}
				});

				new Thread(new Runnable() {
					@Override
					public void run() {
						tr.start();
						// .....
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
						}
					}
				}).start();
				
			}
		});
		
		JMenuItem mntmDbitos = new JMenuItem("Débitos");
		mntmDbitos.setIcon(new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/wallet-icon_24.png")));
		mntmDbitos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// #############################################
				final Thread tr = new Thread(new Runnable() {
					@Override
					public void run() {
						//try {

							JanelaDebito jDebito = new JanelaDebito();
							jDebito.setVisible(true);
							jDebito.setLocationRelativeTo(null);

						//} catch (Exception ex) {

							//JOptionPane.showMessageDialog(null,"Erro na tentativa de conexão com o servidor!!! Verifique a conexão! " + ex);
						//}

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
				
			}
		});
		mnArquivo.add(mntmDbitos);
		
		JMenuItem mntmDespesasFixas = new JMenuItem("Despesas fixas");
		mntmDespesasFixas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// #############################################
				final Thread tr = new Thread(new Runnable() {
					@Override
					public void run() {
							JanelaDespesaFixa jDespFixas = new JanelaDespesaFixa();
							jDespFixas.setVisible(true);
							jDespFixas.setLocationRelativeTo(null);

					}
				});

				new Thread(new Runnable() {
					@Override
					public void run() {
						tr.start();
						// .....
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
						}
					}
				}).start();
				
			}
		});
		mntmDespesasFixas.setIcon(new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/despesas_fixas_24.png")));
		mnArquivo.add(mntmDespesasFixas);
		mnArquivo.add(mntmEntidade);
		
		JMenuItem mntmPessoa = new JMenuItem("Pessoa");
		mntmPessoa.setIcon(new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/users-icon_24.png")));
		mntmPessoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// #############################################
				final Thread tr = new Thread(new Runnable() {
					@Override
					public void run() {
							JanelaPessoa jPessoa = new JanelaPessoa();
							jPessoa.setVisible(true);
							jPessoa.setLocationRelativeTo(null);

					}
				});

				new Thread(new Runnable() {
					@Override
					public void run() {
						tr.start();
						// .....
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
						}
					}
				}).start();
				
			}
		});
		mnArquivo.add(mntmPessoa);
		
		JMenuItem mntmUsuarios = new JMenuItem("Usuarios");
		mntmUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// #############################################
				final Thread tr = new Thread(new Runnable() {
					@Override
					public void run() {
							JanelaUsuarios jUsuarios = new JanelaUsuarios();
							jUsuarios.setVisible(true);
							jUsuarios.setLocationRelativeTo(null);

					}
				});

				new Thread(new Runnable() {
					@Override
					public void run() {
						tr.start();
						// .....
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
						}
					}
				}).start();
				
			}
		});
		mntmUsuarios.setIcon(new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/usuario_24.png")));
		mnArquivo.add(mntmUsuarios);
		
		JMenuItem mntmSair = new JMenuItem("Sair");
		mntmSair.setIcon(new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/button-exit-icon_24.png")));
		mnArquivo.add(mntmSair);
		
		JMenu mnEditar = new JMenu("Relatórios");
		menuBar.add(mnEditar);
		
		JMenu mnCrditos = new JMenu("Créditos");
		mnCrditos.setIcon(new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/my-Documents-icon_24.png")));
		mnEditar.add(mnCrditos);
		
		JMenuItem mntmTodosOsCrditos = new JMenuItem("Todos créditos");
		mntmTodosOsCrditos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					ChamaRelatorio chamaRelatorio = new ChamaRelatorio();

					chamaRelatorio.report("Creditos.jasper");
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage());
				}				
				
			}
		});
		mntmTodosOsCrditos.setIcon(new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/document-scroll-icon_24.png")));
		mnCrditos.add(mntmTodosOsCrditos);
		
		JMenu mnContasAPagar = new JMenu("Contas a pagar");
		mnContasAPagar.setIcon(new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/my-Documents-icon_24.png")));
		mnEditar.add(mnContasAPagar);
		
		JMenuItem mntmTodasAsContas = new JMenuItem("Todas contas a pagar");
		mntmTodasAsContas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					ChamaRelatorio chamaRelatorio = new ChamaRelatorio();

					chamaRelatorio.report("DespesasFixas.jasper");
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage());
				}
			}
		});
		mntmTodasAsContas.setIcon(new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/document-scroll-icon_24.png")));
		mnContasAPagar.add(mntmTodasAsContas);
		
		JMenu mnDbitos = new JMenu("Débitos");
		mnDbitos.setIcon(new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/my-Documents-icon_24.png")));
		mnEditar.add(mnDbitos);
		
		JMenuItem mntmTodosOsDbitos = new JMenuItem("Todos débitos");
		mntmTodosOsDbitos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ChamaRelatorio chamaRelatorio = new ChamaRelatorio();

					chamaRelatorio.report("Debitos.jasper");
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage());
				}
			}
		});
		mntmTodosOsDbitos.setIcon(new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/document-scroll-icon_24.png")));
		mnDbitos.add(mntmTodosOsDbitos);
		
		JMenu mnDespesasFixas = new JMenu("Despesas fixas");
		mnDespesasFixas.setIcon(new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/my-Documents-icon_24.png")));
		mnEditar.add(mnDespesasFixas);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Todas as despesas fixas");
		mntmNewMenuItem.setIcon(new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/document-scroll-icon_24.png")));
		mnDespesasFixas.add(mntmNewMenuItem);
		
		JMenu mnEntidade = new JMenu("Entidades");
		mnEntidade.setIcon(new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/my-Documents-icon_24.png")));
		mnEditar.add(mnEntidade);
		
		JMenuItem mntmTodasEntidades = new JMenuItem("Todas entidades");
		mntmTodasEntidades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ChamaRelatorio chamaRelatorio = new ChamaRelatorio();

					chamaRelatorio.report("Entidades.jasper");
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage());
				}
			}
		});
		mntmTodasEntidades.setIcon(new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/document-scroll-icon_24.png")));
		mnEntidade.add(mntmTodasEntidades);
		
		JMenu mnPessoas = new JMenu("Pessoas");
		mnPessoas.setIcon(new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/my-Documents-icon_24.png")));
		mnEditar.add(mnPessoas);
		
		JMenuItem mntmTodasPessoas = new JMenuItem("Todas pessoas");
		mntmTodasPessoas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ChamaRelatorio chamaRelatorio = new ChamaRelatorio();

					chamaRelatorio.report("Pessoas.jasper");
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage());
				}
			}
		});
		mntmTodasPessoas.setIcon(new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/document-scroll-icon_24.png")));
		mnPessoas.add(mntmTodasPessoas);
		
		JMenu mnFerramentas = new JMenu("Ferramentas");
		menuBar.add(mnFerramentas);
		
		JMenuItem mntmFazerBackup = new JMenuItem("Fazer BackUp");
		mntmFazerBackup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				BackUp backUp = new BackUp();

				backUp.salvarBackup();

			}
		});
		mntmFazerBackup.setIcon(new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/misc-Download-Database-icon_24.png")));
		mnFerramentas.add(mntmFazerBackup);
		
		JMenuItem mntmRestaurarBackup = new JMenuItem("Restaurar BackUp");
		mntmRestaurarBackup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				BackUp backUp = new BackUp();

				backUp.uploadBackup();
				
			}
		});
		mntmRestaurarBackup.setIcon(new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/misc-Upload-Database-icon_24.png")));
		mnFerramentas.add(mntmRestaurarBackup);
		
		JMenu mnAjuda = new JMenu("Ajuda");
		menuBar.add(mnAjuda);
		
		JMenuItem mntmSobre = new JMenuItem("Sobre");
		mntmSobre.setIcon(new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/sobre_24.png")));
		mnAjuda.add(mntmSobre);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 854, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
		);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 733, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 415, Short.MAX_VALUE)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		tabbedPane.addTab("Principal", new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/application-icon_24.png")), panel_2, null);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(null, "Cr\u00E9ditos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new TitledBorder(null, "D\u00E9bitos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new TitledBorder(null, "Filtro por data", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_panel_2.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel_6, GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, gl_panel_2.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel_5, GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, gl_panel_2.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel_8, GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_5, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_6, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		JLabel lblDe = new JLabel("De:");
		
		dcInicial = new JDateChooser();
		
		JLabel lblAt_1 = new JLabel("até");
		
		dcFinal = new JDateChooser();
		
		JButton btnAtualizar = new JButton("");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
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
						atualizarConexao();

						limparTabelaCreditoPrincipal();
						limparTabelaDebitoPrincipal();
						
						if (cbEntidades.getSelectedIndex() == 0) {
							carregarCreditosHoje();
							carregarDebitossHoje();
						}else{
							
							Entidade ent = (Entidade) cbEntidades.getSelectedItem();
							
							carregarCreditosHojeEntidade(ent);
							carregarDebitosHojeEntidade(ent);
						}
						
						calcularLucros();
						
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
						espera.setBackground(new Color(0, 0, 0, 0));
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
		});
		btnAtualizar.setIcon(new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/refresh-icon_24.png")));
		
		JLabel lblTotal_1 = new JLabel("Total:");
		lblTotal_1.setFont(new Font("Dialog", Font.BOLD, 14));
		
		tfTotalLucros = new DecimalFormattedField(DecimalFormattedField.REAL);
		tfTotalLucros.setEnabled(false);
		tfTotalLucros.setEditable(false);
		tfTotalLucros.setHorizontalAlignment(SwingConstants.RIGHT);
		tfTotalLucros.setFont(new Font("Dialog", Font.BOLD, 14));
		tfTotalLucros.setDisabledTextColor(Color.BLACK);
		
		cbEntidades = new JComboBox();
		GroupLayout gl_panel_8 = new GroupLayout(panel_8);
		gl_panel_8.setHorizontalGroup(
			gl_panel_8.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_8.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblDe)
					.addGap(12)
					.addComponent(dcInicial, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblAt_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(dcFinal, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(cbEntidades, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnAtualizar)
					.addPreferredGap(ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
					.addComponent(lblTotal_1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tfTotalLucros, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panel_8.setVerticalGroup(
			gl_panel_8.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_8.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_8.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_8.createSequentialGroup()
							.addGap(5)
							.addGroup(gl_panel_8.createParallelGroup(Alignment.BASELINE)
								.addComponent(tfTotalLucros)
								.addComponent(lblTotal_1)))
						.addGroup(gl_panel_8.createParallelGroup(Alignment.TRAILING)
							.addComponent(cbEntidades, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(dcFinal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblAt_1)
							.addGroup(gl_panel_8.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_8.createSequentialGroup()
									.addGap(4)
									.addComponent(lblDe))
								.addComponent(dcInicial, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addComponent(btnAtualizar, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(14, Short.MAX_VALUE))
		);
		panel_8.setLayout(gl_panel_8);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		tableDebitosPrincipal = new JTable();
		scrollPane_1.setViewportView(tableDebitosPrincipal);
		
		JLabel lblTotalDosDbitos = new JLabel("Total dos débitos:");
		
		lblDebitos = new DecimalFormattedField(DecimalFormattedField.REAL);
		lblDebitos.setEnabled(false);
		lblDebitos.setEditable(false);
		lblDebitos.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDebitos.setDisabledTextColor(Color.BLACK);
		
		GroupLayout gl_panel_6 = new GroupLayout(panel_6);
		gl_panel_6.setHorizontalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_6.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_6.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 804, Short.MAX_VALUE)
						.addGroup(gl_panel_6.createSequentialGroup()
							.addComponent(lblTotalDosDbitos, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblDebitos, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_panel_6.setVerticalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_6.createSequentialGroup()
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
					.addGap(8)
					.addGroup(gl_panel_6.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTotalDosDbitos)
						.addComponent(lblDebitos))
					.addContainerGap())
		);
		panel_6.setLayout(gl_panel_6);
		
		JScrollPane scrollPane = new JScrollPane();
		
		tableCreditosPrincipal = new JTable();
		scrollPane.setViewportView(tableCreditosPrincipal);
		
		lblCreditos = new DecimalFormattedField(DecimalFormattedField.REAL);
		lblCreditos.setEditable(false);
		lblCreditos.setEnabled(false);
		lblCreditos.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCreditos.setDisabledTextColor(Color.BLACK);
		
		JLabel lblTotalDosCrditos = new JLabel("Total dos créditos:");
		GroupLayout gl_panel_5 = new GroupLayout(panel_5);
		gl_panel_5.setHorizontalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_5.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 804, Short.MAX_VALUE)
						.addGroup(gl_panel_5.createSequentialGroup()
							.addComponent(lblTotalDosCrditos)
							.addGap(18)
							.addComponent(lblCreditos, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_panel_5.setVerticalGroup(
			gl_panel_5.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCreditos)
						.addComponent(lblTotalDosCrditos))
					.addGap(10))
		);
		panel_5.setLayout(gl_panel_5);
		panel_2.setLayout(gl_panel_2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		tabbedPane.addTab("Relatórios", new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/document-scroll-icon_24.png")), panel_3, null);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Relat\u00F3rio simples", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		
		JPanel panel_9 = new JPanel();
		panel_9.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Relat\u00F3rio comparativo Cr\u00E9ditos/Debitos por Entidade", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		
		JLabel label_2 = new JLabel("Entidade:");
		
		chckbxFiltrarPorData2 = new JCheckBox("Filtrar por data:");
		chckbxFiltrarPorData2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				verificarCbData2();
			}
		});
				
		dcDataInicial2 = new JDateChooser();
		dcDataInicial2.setEnabled(false);
		
		JLabel label_3 = new JLabel("até");
		
		dcDataFinal2 = new JDateChooser();
		dcDataFinal2.setEnabled(false);
		
		cbEntidade2 = new JComboBox();
		cbEntidade2.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				
				atualizarConexao();
				carregajcbEntidade();
				
			}
		});
		
		btnGerarRelatrio2 = new JButton("Gerar relatório");
		btnGerarRelatrio2.setIcon(new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/document-scroll-icon_64.png")));
		btnGerarRelatrio2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				atualizarConexao();
				
				Entidade ent = (Entidade) cbEntidade2.getSelectedItem();
				
				//JOptionPane.showMessageDialog(null, "Entidade: " + ent.getId());
				
				if (chckbxFiltrarPorData2.isSelected()) {				
										
					
						ChamaRelatorio chamaRelatorio = new ChamaRelatorio();
						
						if (chckbxFiltrarPai.isSelected()) {
							
							Query consulta = manager.createQuery("FROM Entidade WHERE filho = '1' AND entidade_id LIKE '" + ent.getId().toString() + "'");
							
							List<Entidade> listaEntidades = consulta.getResultList();

							if (listaEntidades.size() > 0) {
														
							try {
								
								Double c = calcularCreditosEntidadePai(ent, dcDataInicial2.getDate(), dcDataFinal2.getDate());
								Double d = calcularDebitosEntidadePai(ent, dcDataInicial2.getDate(), dcDataFinal2.getDate());
							
								Double saldo = (c-d);
							
								chamaRelatorio.reportEntidadeCreditoDebitoPai("EntidadeCreditoDebitoPai.jasper", ent, dcDataInicial2.getDate(), dcDataFinal2.getDate(), saldo, c, d);
							} catch (Exception e2) {
								JOptionPane.showMessageDialog(null, "Erro ao gerar o relatório com a função pai" + e2.getMessage());
								//JOptionPane.showMessageDialog(null, "Não foram encontrados filhos da entidade " + ent.getNome());
							}
							}else{
								JOptionPane.showMessageDialog(null, "Não foram encontrados filhos da entidade " + ent.getNome());
							}
						}else{
							try {
								Double c = calcularCreditosEntidade(ent, dcDataInicial2.getDate(), dcDataFinal2.getDate());
								Double d = calcularDebitosEntidade(ent, dcDataInicial2.getDate(), dcDataFinal2.getDate());
								
								Double saldo = (c-d);
								
								chamaRelatorio.reportEntidadeCreditoDebito("EntidadeCreditoDebito.jasper", ent, dcDataInicial2.getDate(), dcDataFinal2.getDate(), saldo, c, d);
							} catch (Exception e2) {
								JOptionPane.showMessageDialog(null, "Erro ao gerar o relatório" + e2.getMessage());
							}
							
						}
					
				}else{
					
					String sdi = "01/01/2000";
					String sdf = "01/01/2030";
					
					Date di = null;
					Date df = null;
					try {
						di = formatDataBra.parse(sdi);
						df = formatDataBra.parse(sdf);
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "Erro ao transforma as datas" + e2.getMessage());
					}																	
					
						ChamaRelatorio chamaRelatorio = new ChamaRelatorio();
						
						if (chckbxFiltrarPai.isSelected()) {
							
							Query consulta = manager.createQuery("FROM Entidade WHERE filho = '1' AND entidade_id LIKE '" + ent.getId().toString() + "'");
							
							List<Entidade> listaEntidades = consulta.getResultList();

							if (listaEntidades.size() > 0) {
							
							try {								
								Double c = calcularCreditosEntidadePai(ent, di, df);
								Double d = calcularDebitosEntidadePai(ent, di, df);
												
								Double saldo = (c-d);
							
								chamaRelatorio.reportEntidadeCreditoDebitoPai("EntidadeCreditoDebitoPai.jasper", ent, di, df, saldo, c, d);
							} catch (Exception e3) {
								JOptionPane.showMessageDialog(null, "Erro ao gerar o relatório utilizando a função pai: " + e3.getMessage());
								//JOptionPane.showMessageDialog(null, "Não foram encontrados filhos da entidade " + ent.getNome());
							}
							}else{
								JOptionPane.showMessageDialog(null, "Não foram encontrados filhos da entidade " + ent.getNome());
							}
						}else{
							try {
								Double c = calcularCreditosEntidade(ent, di, df);
								Double d = calcularDebitosEntidade(ent, di, df);
													
								Double saldo = (c-d);
								
								chamaRelatorio.reportEntidadeCreditoDebito("EntidadeCreditoDebito.jasper", ent, di, df, saldo, c, d);
							} catch (Exception e2) {
								JOptionPane.showMessageDialog(null, "Erro ao gerar o relatório: " + e2.getMessage());
							}
							
						}
					
				}				
			}
		});
		
		chckbxFiltrarPai = new JCheckBox("Filtrar pai");
		GroupLayout gl_panel_9 = new GroupLayout(panel_9);
		gl_panel_9.setHorizontalGroup(
			gl_panel_9.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_9.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_9.createParallelGroup(Alignment.LEADING)
						.addComponent(label_2)
						.addGroup(gl_panel_9.createSequentialGroup()
							.addGroup(gl_panel_9.createParallelGroup(Alignment.LEADING)
								.addComponent(chckbxFiltrarPorData2)
								.addGroup(gl_panel_9.createSequentialGroup()
									.addComponent(dcDataInicial2, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(label_3)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(dcDataFinal2, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
						.addComponent(cbEntidade2, GroupLayout.PREFERRED_SIZE, 348, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(chckbxFiltrarPai)
					.addPreferredGap(ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
					.addComponent(btnGerarRelatrio2)
					.addContainerGap())
		);
		gl_panel_9.setVerticalGroup(
			gl_panel_9.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_9.createSequentialGroup()
					.addGroup(gl_panel_9.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_9.createSequentialGroup()
							.addContainerGap()
							.addComponent(label_2)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_9.createParallelGroup(Alignment.BASELINE)
								.addComponent(cbEntidade2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(chckbxFiltrarPai))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(chckbxFiltrarPorData2)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_9.createParallelGroup(Alignment.LEADING)
								.addComponent(dcDataInicial2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel_9.createParallelGroup(Alignment.TRAILING)
									.addComponent(dcDataFinal2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(label_3))))
						.addComponent(btnGerarRelatrio2, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_9.setLayout(gl_panel_9);
		
		JPanel panel_10 = new JPanel();
		panel_10.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Relat\u00F3rio comparativo Cr\u00E9ditos/Debitos por Pessoa", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		
		JLabel lblPessoa = new JLabel("Pessoa:");
		
		chckbxFiltrarPorData3 = new JCheckBox("Filtrar por data:");
		chckbxFiltrarPorData3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				verificarCbData3();
			}
		});
				
		dcDataInicial3 = new JDateChooser();
		dcDataInicial3.setEnabled(false);
		
		JLabel label_5 = new JLabel("até");
		
		dcDataFinal3 = new JDateChooser();
		dcDataFinal3.setEnabled(false);
		
		cbPessoas = new JComboBox();
		cbPessoas.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				
				atualizarConexao();
				carregajcbEntidade();
				
			}
		});
		
		JButton button = new JButton("Gerar relatório");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				atualizarConexao();
				
				Pessoa pes = (Pessoa) cbPessoas.getSelectedItem();
				
				if (chckbxFiltrarPorData3.isSelected()) {
					
					Date di = dcDataInicial3.getDate();
					Date df = dcDataFinal3.getDate();
					
					Double c = calcularCreditosPessoa(pes, di, df);
					Double d = calcularDebitosPessoa(pes, di, df);
					
					//JOptionPane.showMessageDialog(null, "Créditos " + c.toString());
					//JOptionPane.showMessageDialog(null, "Débitos " + d.toString());
					
					Double saldo = (c-d);
					
					//JOptionPane.showMessageDialog(null, "Saldo " + saldo.toString());
					
					try {
						ChamaRelatorio chamaRelatorio = new ChamaRelatorio();

						chamaRelatorio.reportPessoaCreditoDebito("PessoaCreditoDebito.jasper", pes, di, df, saldo, c, d);
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, e2.getMessage());
					}
					
				}else{
				
					String sdi = "01/01/2000";
					String sdf = "01/01/2030";
					
					Date di = null;
					Date df = null;
					try {
						di = formatDataBra.parse(sdi);
						df = formatDataBra.parse(sdf);
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "Erro ao transforma as datas" + e2.getMessage());
					}
					
					Double c = calcularCreditosPessoa(pes, di, df);
					Double d = calcularDebitosPessoa(pes, di, df);
					
					//JOptionPane.showMessageDialog(null, "Créditos " + c.toString());
					//JOptionPane.showMessageDialog(null, "Débitos " + d.toString());
					
					Double saldo = (c-d);
					
					//JOptionPane.showMessageDialog(null, "Saldo " + saldo.toString());
					
					try {
						ChamaRelatorio chamaRelatorio = new ChamaRelatorio();

						chamaRelatorio.reportPessoaCreditoDebito("PessoaCreditoDebito.jasper", pes, di, df, saldo, c, d);
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, e2.getMessage());
					}					
				}				
				
				
			}
		});
		button.setIcon(new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/document-scroll-icon_64.png")));
		GroupLayout gl_panel_10 = new GroupLayout(panel_10);
		gl_panel_10.setHorizontalGroup(
			gl_panel_10.createParallelGroup(Alignment.LEADING)
				.addGap(0, 841, Short.MAX_VALUE)
				.addGroup(gl_panel_10.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_10.createParallelGroup(Alignment.LEADING)
						.addComponent(lblPessoa)
						.addGroup(gl_panel_10.createSequentialGroup()
							.addGroup(gl_panel_10.createParallelGroup(Alignment.LEADING)
								.addComponent(chckbxFiltrarPorData3)
								.addGroup(gl_panel_10.createSequentialGroup()
									.addComponent(dcDataInicial3, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(label_5)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(dcDataFinal3, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
						.addComponent(cbPessoas, GroupLayout.PREFERRED_SIZE, 348, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 319, Short.MAX_VALUE)
					.addComponent(button)
					.addContainerGap())
		);
		gl_panel_10.setVerticalGroup(
			gl_panel_10.createParallelGroup(Alignment.LEADING)
				.addGap(0, 142, Short.MAX_VALUE)
				.addGroup(gl_panel_10.createSequentialGroup()
					.addGroup(gl_panel_10.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_10.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblPessoa)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cbPessoas, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(chckbxFiltrarPorData3)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_10.createParallelGroup(Alignment.LEADING)
								.addComponent(dcDataInicial3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel_10.createParallelGroup(Alignment.TRAILING)
									.addComponent(dcDataFinal3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(label_5))))
						.addComponent(button, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_10.setLayout(gl_panel_10);
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_9, GroupLayout.PREFERRED_SIZE, 841, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panel_10, GroupLayout.PREFERRED_SIZE, 841, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_7, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_9, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_10, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(44, Short.MAX_VALUE))
		);
		
		cbEntidade = new JComboBox();
		cbEntidade.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				
				atualizarConexao();
				carregajcbEntidade();
			}
		});
				
		JLabel lblEntidade = new JLabel("Entidade:");
		
		chckbxFiltrarPorData = new JCheckBox("Filtrar por data:");
		chckbxFiltrarPorData.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				verificarCbData();
			}
		});
				
		dcDataInicial = new JDateChooser();
		dcDataInicial.setEnabled(false);
		
		JLabel lblAt = new JLabel("até");
		
		dcDataFinal = new JDateChooser();
		dcDataFinal.setEnabled(false);
		
		rdbtnCreditos = new JRadioButton("Créditos");
		rdbtnCreditos.setSelected(true);
		buttonGroup.add(rdbtnCreditos);
		
		rdbtnDebitos = new JRadioButton("Débitos");
		buttonGroup.add(rdbtnDebitos);
		
		JButton btnGerarRelatrio = new JButton("Gerar relatório");
		btnGerarRelatrio.setIcon(new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/document-scroll-icon_64.png")));
		btnGerarRelatrio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				atualizarConexao();
				
				Date dti = null;
				Date dtf = null;
				
				if (chckbxFiltrarPorData.isSelected()) {					
					dti = dcDataInicial.getDate();
					dtf = dcDataFinal.getDate();
					
					gerarRelatorio((Entidade) cbEntidade.getSelectedItem(), dti, dtf);
				}else{
					gerarRelatorio((Entidade) cbEntidade.getSelectedItem(), null, null);
				}
				
				
				
			}
		});
		GroupLayout gl_panel_7 = new GroupLayout(panel_7);
		gl_panel_7.setHorizontalGroup(
			gl_panel_7.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_7.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_7.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel_7.createSequentialGroup()
							.addComponent(rdbtnCreditos)
							.addGap(18)
							.addComponent(rdbtnDebitos))
						.addComponent(lblEntidade)
						.addGroup(gl_panel_7.createSequentialGroup()
							.addGroup(gl_panel_7.createParallelGroup(Alignment.LEADING)
								.addComponent(chckbxFiltrarPorData)
								.addGroup(gl_panel_7.createSequentialGroup()
									.addComponent(dcDataInicial, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblAt)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(dcDataFinal, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
						.addComponent(cbEntidade, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED, 291, Short.MAX_VALUE)
					.addComponent(btnGerarRelatrio)
					.addContainerGap())
		);
		gl_panel_7.setVerticalGroup(
			gl_panel_7.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_7.createSequentialGroup()
					.addGroup(gl_panel_7.createParallelGroup(Alignment.LEADING)
						.addComponent(btnGerarRelatrio, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_7.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel_7.createParallelGroup(Alignment.BASELINE)
								.addComponent(rdbtnCreditos)
								.addComponent(rdbtnDebitos))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblEntidade)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cbEntidade, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(chckbxFiltrarPorData)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_7.createParallelGroup(Alignment.LEADING)
								.addComponent(dcDataInicial, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel_7.createParallelGroup(Alignment.TRAILING)
									.addComponent(dcDataFinal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblAt)))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_7.setLayout(gl_panel_7);
		panel_3.setLayout(gl_panel_3);
		
		JButton btEntidade = new JButton("");
		btEntidade.setToolTipText("Entidades");
		btEntidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// #############################################
				final Thread tr = new Thread(new Runnable() {
					@Override
					public void run() {
							JanelaEntidade jEntidade = new JanelaEntidade();
							jEntidade.setVisible(true);
							jEntidade.setLocationRelativeTo(null);

					}
				});

				new Thread(new Runnable() {
					@Override
					public void run() {
						tr.start();
						// .....
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
						}
					}
				}).start();
				
			}
		});
		btEntidade.setIcon(new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/folder-Data-icon_64.png")));
		//btEntidade.setBackground(new Color(0,0,0,0));
		//btEntidade.setBorder(null);
		
		JButton btCredito = new JButton("");
		btCredito.setToolTipText("Créditos");
		btCredito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// #############################################
				final Thread tr = new Thread(new Runnable() {
					@Override
					public void run() {
						//try {

							JanelaCredito jCredito = new JanelaCredito();
							jCredito.setVisible(true);
							jCredito.setLocationRelativeTo(null);

						//} catch (Exception ex) {

							//JOptionPane.showMessageDialog(null,"Erro na tentativa de conexão com o servidor!!! Verifique a conexão! " + ex);
						//}

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
				
			}
		});
		btCredito.setIcon(new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/money-Bag-icon_64.png")));
		//btCredito.setBackground(new Color(0,0,0,0));
		//btCredito.setBorder(null);
		
		JButton btDebito = new JButton("");
		btDebito.setToolTipText("Débitos");
		btDebito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// #############################################
				final Thread tr = new Thread(new Runnable() {
					@Override
					public void run() {
						//try {

							JanelaDebito jDebito = new JanelaDebito();
							jDebito.setVisible(true);
							jDebito.setLocationRelativeTo(null);

						//} catch (Exception ex) {

							//JOptionPane.showMessageDialog(null,"Erro na tentativa de conexão com o servidor!!! Verifique a conexão! " + ex);
						//}

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
				
			}
		});
		btDebito.setIcon(new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/wallet-icon_64.png")));
		//btDebito.setBackground(new Color(0,0,0,0));
		//btDebito.setBorder(null);
		
		JButton btPessoa = new JButton("");
		btPessoa.setToolTipText("Pessoas");
		btPessoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// #############################################
				final Thread tr = new Thread(new Runnable() {
					@Override
					public void run() {
							JanelaPessoa jPessoa = new JanelaPessoa();
							jPessoa.setVisible(true);
							jPessoa.setLocationRelativeTo(null);

					}
				});

				new Thread(new Runnable() {
					@Override
					public void run() {
						tr.start();
						// .....
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
						}
					}
				}).start();
				
			}
		});
		btPessoa.setIcon(new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/users-icon_64.png")));
		//btPessoa.setBackground(new Color(0,0,0,0));
		//btPessoa.setBorder(null);
		
		JButton btDespesasFixas = new JButton("");
		btDespesasFixas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// #############################################
				final Thread tr = new Thread(new Runnable() {
					@Override
					public void run() {
							JanelaDespesaFixa jDespesasFixas = new JanelaDespesaFixa();
							jDespesasFixas.setVisible(true);
							jDespesasFixas.setLocationRelativeTo(null);

					}
				});

				new Thread(new Runnable() {
					@Override
					public void run() {
						tr.start();
						// .....
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
						}
					}
				}).start();
				
			}
		});
		btDespesasFixas.setToolTipText("Sair");
		btDespesasFixas.setIcon(new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/despesas_fixas_64.png")));
		//btDespesasFixas.setBackground(new Color(0,0,0,0));
		//btDespesasFixas.setBorder(null);
		
		JButton btSair = new JButton("");
		btSair.setIcon(new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/icones/button-exit-icon_64.png")));
		btSair.setToolTipText("Sair");
		//btSair.setBackground(new Color(0,0,0,0));
		//btSair.setBorder(null);
		
		JLabel label_1 = new JLabel("");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setIcon(new ImageIcon(JanelaMenuPrincipal.class.getResource("/br/com/grupocaravela/imagens/logo_completo_64.png")));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(label_1, GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(btCredito, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
							.addComponent(btEntidade, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 70, Short.MAX_VALUE))
						.addComponent(btDebito, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
						.addComponent(btPessoa, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
						.addComponent(btDespesasFixas, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
						.addComponent(btSair, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(btEntidade, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btCredito, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btDebito, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btPessoa, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btDespesasFixas, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btSair, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
					.addComponent(label_1)
					.addContainerGap())
		);
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
	}
	
	private void iniciaConexao() {
		manager = entityManagerProducer.createEntityManager();
		trx = manager.getTransaction();
	}
	
	private void atualizarConexao(){
		
		manager.close();
		
		manager = entityManagerProducer.createEntityManager();
		trx = manager.getTransaction();
		
	}
	
	private void carregarTableModel() {
		this.tableModelCredito = new TableModelCredito();
		this.tableCreditosPrincipal.setModel(tableModelCredito);
		
		this.tableModelDebito = new TableModelDebito();
		this.tableDebitosPrincipal.setModel(tableModelDebito);
		
		//Render numeros moeda
		NumberFormat numeroMoeda = NumberFormat.getNumberInstance();
		numeroMoeda.setMinimumFractionDigits(2);

		DefaultTableCellRenderer cellRendererCustomMoeda = new MoedaRender(numeroMoeda);
		tableCreditosPrincipal.getColumnModel().getColumn(2).setCellRenderer(cellRendererCustomMoeda);
		tableDebitosPrincipal.getColumnModel().getColumn(2).setCellRenderer(cellRendererCustomMoeda);
		
		// Render das datas
		tableCreditosPrincipal.getColumnModel().getColumn(3).setCellRenderer(new DateRenderer());
		tableDebitosPrincipal.getColumnModel().getColumn(3).setCellRenderer(new DateRenderer());
		
		//Ordenação
		tableCreditosPrincipal.setAutoCreateRowSorter(true);
		tableDebitosPrincipal.setAutoCreateRowSorter(true);	
		
	}
	
	private java.util.Date dataAtual() {

		java.util.Date hoje = new java.util.Date();
		return hoje;

	}
	
	private void carregarCreditosHoje(){
		
		totalCreditos = 0.0;
		
		String dataInicial = formatDataSql.format(dcInicial.getDate());
		String dataFinal = formatDataSql.format(dcFinal.getDate());
		
		try {

			//Query consulta = manager.createQuery("FROM Credito WHERE data_credito LIKE '" + formatDataSql.format(dataAtual()) + "'");
			Query consulta = manager.createQuery("FROM Credito WHERE data_credito BETWEEN '" + dataInicial + "' AND '" + dataFinal + "'");
			
			//BETWEEN $P{DATA_INICIAL_SQL} AND $P{DATA_FINAL_SQL}
			
			List<Credito> listaCreditos = consulta.getResultList();

			for (int i = 0; i < listaCreditos.size(); i++) {
				Credito c = listaCreditos.get(i);
				tableModelCredito.addCredito(c);
				
				totalCreditos = totalCreditos + c.getValor();
			}
			
			lblCreditos.setText(totalCreditos.toString());
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao carregar a tabela de creditos: " + e);
		}		
	}
	
	private void carregarDebitossHoje(){
		
		totalDebitos = 0.0;
		
		String dataInicial = formatDataSql.format(dcInicial.getDate());
		String dataFinal = formatDataSql.format(dcFinal.getDate());
		
		try {

			//Query consulta = manager.createQuery("FROM Debito WHERE data_debito LIKE '" + formatDataSql.format(dataAtual()) + "'");
			Query consulta = manager.createQuery("FROM Debito WHERE data_debito BETWEEN '" + dataInicial + "' AND '" + dataFinal + "'");
			
			List<Debito> listaDebitos = consulta.getResultList();

			for (int i = 0; i < listaDebitos.size(); i++) {
				Debito c = listaDebitos.get(i);
				tableModelDebito.addDebito(c);
				totalDebitos = totalDebitos + c.getValor();
			}
			
			lblDebitos.setText(totalDebitos.toString());
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao carregar a tabela de debitos: " + e);
		}		
	}
	
private void carregarCreditosHojeEntidade(Entidade ent){
		
		totalCreditos = 0.0;
		
		String dataInicial = formatDataSql.format(dcInicial.getDate());
		String dataFinal = formatDataSql.format(dcFinal.getDate());
		
		try {

			//Query consulta = manager.createQuery("FROM Credito WHERE data_credito LIKE '" + formatDataSql.format(dataAtual()) + "'");
			Query consulta = manager.createQuery("FROM Credito WHERE entidade_id LIKE '" + ent.getId().toString() + "' AND data_credito BETWEEN '" + dataInicial + "' AND '" + dataFinal + "'");
			
			//BETWEEN $P{DATA_INICIAL_SQL} AND $P{DATA_FINAL_SQL}
			
			List<Credito> listaCreditos = consulta.getResultList();

			for (int i = 0; i < listaCreditos.size(); i++) {
				Credito c = listaCreditos.get(i);
				tableModelCredito.addCredito(c);
				
				totalCreditos = totalCreditos + c.getValor();
			}
			
			lblCreditos.setText(totalCreditos.toString());
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao carregar a tabela de creditos: " + e);
		}		
	}
	
	private void carregarDebitosHojeEntidade(Entidade ent){
		
		totalDebitos = 0.0;
		
		String dataInicial = formatDataSql.format(dcInicial.getDate());
		String dataFinal = formatDataSql.format(dcFinal.getDate());
		
		try {

			//Query consulta = manager.createQuery("FROM Debito WHERE data_debito LIKE '" + formatDataSql.format(dataAtual()) + "'");
			Query consulta = manager.createQuery("FROM Debito WHERE entidade_id LIKE '" + ent.getId().toString() + "' AND data_debito BETWEEN '" + dataInicial + "' AND '" + dataFinal + "'");
			
			List<Debito> listaDebitos = consulta.getResultList();

			for (int i = 0; i < listaDebitos.size(); i++) {
				Debito c = listaDebitos.get(i);
				tableModelDebito.addDebito(c);
				totalDebitos = totalDebitos + c.getValor();
			}
			
			lblDebitos.setText(totalDebitos.toString());
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao carregar a tabela de debitos: " + e);
		}		
	}
	
private Double calcularCreditosEntidade(Entidade ent, Date dtInicial, Date dtFinal){
		
		Double retorno = 0.0;
		
		String dataInicial = formatDataSql.format(dtInicial);
		String dataFinal = formatDataSql.format(dtFinal);
		
		try {

			Query consulta;			
			
			consulta = manager.createQuery("FROM Credito WHERE entidade_id LIKE '" + ent.getId().toString() + "' AND data_credito BETWEEN '" + dataInicial + "' AND '" + dataFinal + "'");
						
			List<Credito> listaCreditos = consulta.getResultList();

			for (int i = 0; i < listaCreditos.size(); i++) {
				Credito c = listaCreditos.get(i);
				tableModelCredito.addCredito(c);
				
				retorno = retorno + c.getValor();
			}
			
			return retorno;
			//lblCreditos.setText("R$ " + totalCreditos);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao carregar a tabela de creditos: " + e);
			return 0.0;
		}		
	}
	
	private Double calcularDebitosEntidade(Entidade ent, Date dtInicial, Date dtFinal){
		
		Double retorno = 0.0;
		
		String dataInicial = formatDataSql.format(dtInicial);
		String dataFinal = formatDataSql.format(dtFinal);
		
		try {
			
			Query consulta;
			
				consulta = manager.createQuery("FROM Debito WHERE entidade_id LIKE '" + ent.getId().toString() + "' AND data_debito BETWEEN '" + dataInicial + "' AND '" + dataFinal + "'");
				
				List<Debito> listaDebitos = consulta.getResultList();

				for (int w = 0; w < listaDebitos.size(); w++) {
					Debito c = listaDebitos.get(w);
					tableModelDebito.addDebito(c);
					retorno = retorno + c.getValor();
				}
			
			
			return retorno;
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao carregar a tabela de debitos: " + e);
			return 0.0;
		}		
	}
	
private Double calcularCreditosEntidadePai(Entidade ent, Date dtInicial, Date dtFinal){
		
		Double retorno = 0.0;
		
		String dataInicial = formatDataSql.format(dtInicial);
		String dataFinal = formatDataSql.format(dtFinal);
		
		try {
			
			Query consulta = manager.createQuery("FROM Entidade WHERE filho = '1' AND entidade_id LIKE '" + ent.getId().toString() + "'");
			
			List<Entidade> listaEntidades = consulta.getResultList();

			//JOptionPane.showMessageDialog(null, "tamanho da lista" + listaEntidades.size());
			
			for (int w = 0; w < listaEntidades.size(); w++) {
				
				Query consulta2 = manager.createQuery("FROM Credito WHERE entidade_id LIKE '" + listaEntidades.get(w).getId().toString() + "' OR entidade_id LIKE '" + ent.getId().toString() + "' AND data_credito BETWEEN '" + dataInicial + "' AND '" + dataFinal + "'");
				
				List<Credito> listaCreditos = consulta2.getResultList();

				//JOptionPane.showMessageDialog(null, "tamanho da lista" + listaCreditos.size());
				
				for (int i = 0; i < listaCreditos.size(); i++) {
					Credito c = listaCreditos.get(i);
					tableModelCredito.addCredito(c);
					
					retorno = retorno + c.getValor();
				}
				
			}			
			return retorno;
			//lblCreditos.setText("R$ " + totalCreditos);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao carregar a tabela de creditos: " + e);
			return 0.0;
		}		
	}
	
private Double calcularDebitosEntidadePai(Entidade ent, Date dtInicial, Date dtFinal){
		
		Double retorno = 0.0;
		
		String dataInicial = formatDataSql.format(dtInicial);
		String dataFinal = formatDataSql.format(dtFinal);
		
		try {
			
			Query consulta = manager.createQuery("FROM Entidade WHERE filho = '1' AND entidade_id LIKE '" + ent.getId().toString() + "'");
			
			List<Entidade> listaEntidades = consulta.getResultList();

			for (int w = 0; w < listaEntidades.size(); w++) {
				Entidade e = listaEntidades.get(w);
				
				Query consulta2 = manager.createQuery("FROM Debito WHERE entidade_id LIKE '" + e.getId().toString() + "' OR entidade_id LIKE '" + ent.getId().toString() + "' AND data_debito BETWEEN '" + dataInicial + "' AND '" + dataFinal + "'");
				
				List<Debito> listaDebitos = consulta2.getResultList();

				for (int i = 0; i < listaDebitos.size(); i++) {
					Debito d = listaDebitos.get(i);
					retorno = retorno + d.getValor();
				}	
			}			
			return retorno;
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao carregar a tabela de debitos: " + e);
			return 0.0;
		}		
	}
	
private Double calcularCreditosPessoa(Pessoa pes, Date dtInicial, Date dtFinal){
		
		Double retorno = 0.0;
		
		String dataInicial = formatDataSql.format(dtInicial);
		String dataFinal = formatDataSql.format(dtFinal);
		
		try {

			Query consulta = manager.createQuery("FROM Credito WHERE pessoa_id LIKE '" + pes.getId().toString() + "' AND data_credito BETWEEN '" + dataInicial + "' AND '" + dataFinal + "'");
			
			
			List<Credito> listaCreditos = consulta.getResultList();

			for (int i = 0; i < listaCreditos.size(); i++) {
				Credito c = listaCreditos.get(i);
				tableModelCredito.addCredito(c);
				
				retorno = retorno + c.getValor();
			}
			
			return retorno;
			//lblCreditos.setText("R$ " + totalCreditos);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao carregar a tabela de creditos: " + e);
			return 0.0;
		}		
	}
	
	private Double calcularDebitosPessoa(Pessoa pes, Date dtInicial, Date dtFinal){
		
		Double retorno = 0.0;
		
		String dataInicial = formatDataSql.format(dtInicial);
		String dataFinal = formatDataSql.format(dtFinal);
		
		try {
			
			Query consulta = manager.createQuery("FROM Debito WHERE pessoa_id LIKE '" + pes.getId().toString() + "' AND data_debito BETWEEN '" + dataInicial + "' AND '" + dataFinal + "'");
			
			List<Debito> listaDebitos = consulta.getResultList();

			for (int i = 0; i < listaDebitos.size(); i++) {
				Debito c = listaDebitos.get(i);
				tableModelDebito.addDebito(c);
				retorno = retorno + c.getValor();
			}
			
			return retorno;
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao carregar a tabela de debitos: " + e);
			return 0.0;
		}		
	}
	
	private void limparTabelaCreditoPrincipal() {
		while (tableCreditosPrincipal.getModel().getRowCount() > 0) {
			tableModelCredito.removeCredito(0);
		}
	}
	
	private void limparTabelaDebitoPrincipal() {
		while (tableDebitosPrincipal.getModel().getRowCount() > 0) {
			tableModelDebito.removeDebito(0);
		}
	}
	
	private void calcularLucros(){
		
		Double total = totalCreditos - totalDebitos;
		
		try {
			
			if (total < 0) {
				//tfTotalLucros.setForeground(Color.RED);
				tfTotalLucros.setDisabledTextColor(Color.RED);
			}else{
				//tfTotalLucros.setForeground(Color.BLUE);
				tfTotalLucros.setDisabledTextColor(Color.BLUE);
			}
			
			tfTotalLucros.setText(total.toString());
		} catch (Exception e) {
			tfTotalLucros.setText(total.toString());
		}
	}
	
	private void carregajcbEntidade() {

		cbEntidade.removeAllItems();
		cbEntidades.removeAllItems();
		cbEntidade2.removeAllItems();
		
		cbEntidades.addItem(new String("Todas entidades"));

		try {

			Query consulta = manager.createQuery("FROM Entidade ORDER BY nome ASC");
			List<Entidade> listaEntidades = consulta.getResultList();

			for (int i = 0; i < listaEntidades.size(); i++) {

				Entidade e = listaEntidades.get(i);
				cbEntidade.addItem(e);
				cbEntidades.addItem(e);
				cbEntidade2.addItem(e);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro no carregamento das entidades! " + e);
		}
	}
	
	private void carregajcbPessoa() {

		cbPessoas.removeAllItems();
		
		//cbPessoas.addItem(new String("Todas pessoas"));

		try {

			Query consulta = manager.createQuery("FROM Pessoa ORDER BY nome ASC");
			List<Pessoa> listaPessoas = consulta.getResultList();

			for (int i = 0; i < listaPessoas.size(); i++) {

				Pessoa e = listaPessoas.get(i);
				cbPessoas.addItem(e);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro no carregamento das pessoas! " + e);
		}
	}
	
	private void tamanhoColunas() {
		// tableProdutos.setAutoResizeMode(tableProdutos.AUTO_RESIZE_OFF);
		tableCreditosPrincipal.getColumnModel().getColumn(0).setWidth(40);
		tableCreditosPrincipal.getColumnModel().getColumn(0).setMaxWidth(60);
		tableCreditosPrincipal.getColumnModel().getColumn(2).setMinWidth(90);
		tableCreditosPrincipal.getColumnModel().getColumn(2).setMaxWidth(100);
		tableCreditosPrincipal.getColumnModel().getColumn(3).setMinWidth(90);
		tableCreditosPrincipal.getColumnModel().getColumn(3).setMaxWidth(100);
		
		tableDebitosPrincipal.getColumnModel().getColumn(0).setWidth(40);
		tableDebitosPrincipal.getColumnModel().getColumn(0).setMaxWidth(60);
		tableDebitosPrincipal.getColumnModel().getColumn(2).setMinWidth(90);
		tableDebitosPrincipal.getColumnModel().getColumn(2).setMaxWidth(100);
		tableDebitosPrincipal.getColumnModel().getColumn(3).setMinWidth(90);
		tableDebitosPrincipal.getColumnModel().getColumn(3).setMaxWidth(100);

	}
	
	private void gerarRelatorio(Entidade entidade, Date dtInicial, Date dtFinal){
		
		ChamaRelatorio chamaRelatorio = new ChamaRelatorio();
		
		if (rdbtnCreditos.isSelected()) {
			
			if (dtInicial == null || dtFinal == null) {
				try {					
					chamaRelatorio.reportEntidade("CreditosPorEntidade.jasper", entidade);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage());
				}	
			}else{
				try {					
					chamaRelatorio.reportEntidadeData("CreditosPorEntidadeData.jasper", entidade, dtInicial, dtFinal);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage());
				}	
			}
		}
		
		if (rdbtnDebitos.isSelected()) {
			
			if (dtInicial == null || dtFinal == null) {
				try {					
					chamaRelatorio.reportEntidade("DebitosPorEntidade.jasper", entidade);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage());
				}	
			}else{
				try {					
					chamaRelatorio.reportEntidadeData("DebitosPorEntidadeData.jasper", entidade, dtInicial, dtFinal);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage());
				}	
			}			
		}		
	}
	
	private void logo() {
        //Mudando o ícone da barra de títulos...  
        URL url = this.getClass().getResource("/br/com/grupocaravela/imagens/logo_24.png");
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(url));
    }
	
	private void verificarCbData(){
		
		if (chckbxFiltrarPorData.isSelected()) {
			dcDataInicial.setEnabled(true);
			dcDataFinal.setEnabled(true);
			
			dcDataInicial.setDate(dataAtual());
			dcDataFinal.setDate(dataAtual());
			
		}else{
			dcDataInicial.setEnabled(false);
			dcDataFinal.setEnabled(false);
			dcDataInicial.setDate(null);
			dcDataFinal.setDate(null);
		}		
	}
	
private void verificarCbData2(){
		
		if (chckbxFiltrarPorData2.isSelected()) {
			dcDataInicial2.setEnabled(true);
			dcDataFinal2.setEnabled(true);
			dcDataInicial2.setDate(dataAtual());
			dcDataFinal2.setDate(dataAtual());
		}else{
			dcDataInicial2.setEnabled(false);
			dcDataFinal2.setEnabled(false);
			dcDataInicial2.setDate(null);
			dcDataFinal2.setDate(null);
		}		
	}

private void verificarCbData3(){
	
	if (chckbxFiltrarPorData3.isSelected()) {
		dcDataInicial3.setEnabled(true);
		dcDataFinal3.setEnabled(true);
		dcDataInicial3.setDate(dataAtual());
		dcDataFinal3.setDate(dataAtual());
	}else{
		dcDataInicial3.setEnabled(false);
		dcDataFinal3.setEnabled(false);
		dcDataInicial3.setDate(null);
		dcDataFinal3.setDate(null);
	}		
}

public static double Arredonda(double Valor) {
    Valor = Valor * 100;
    Valor = Math.round(Valor);
    Valor = Valor / 100;
    return Valor;
}
}
