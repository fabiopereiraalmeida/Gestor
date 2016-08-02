package br.com.grupocaravela.tableModel;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import br.com.grupocaravela.entidade.Pessoa;

public class TableModelPessoa extends AbstractTableModel{

		private static final long serialVersionUID = 1L;

		private ArrayList<Pessoa> listaPessoa;
	    //Titulo das colunas
	    private String[] colunas = {"Id", "Nome", "Fantasia", "Telefone", "Fornecedor"};
	    
	    //Construtor
	    public TableModelPessoa(){
	        this.listaPessoa = new ArrayList<>();
	    }
	    
	    //############### Inicio dos Metodos do TableModel ###################
	    public void addPessoa(Pessoa c){
	        this.listaPessoa.add(c);
	        fireTableDataChanged();
	    }
	    
	    public void removePessoa(int rowIndex){
	        this.listaPessoa.remove(rowIndex);
	        fireTableDataChanged();
	    }
	    
	    public Pessoa getPessoa(int rowIndex){
	        return this.listaPessoa.get(rowIndex); 
	    }
	    
	    //############### Fim dos Metodos do TableModel ###################

	    @Override
	    public int getRowCount() {
	        return this.listaPessoa.size();
	        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	    }

	    @Override
	    public int getColumnCount() {
	        return colunas.length;
	        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	    }

	    @Override
	    public Object getValueAt(int rowIndex, int columnIndex) {
	        switch(columnIndex){
	            case 0:
	                return this.listaPessoa.get(rowIndex).getId();
	            
	            case 1:
	                return this.listaPessoa.get(rowIndex).getNome();
	                
	            case 2:
	                return this.listaPessoa.get(rowIndex).getFantasia();
	                
	            case 3:
	                return this.listaPessoa.get(rowIndex).getTelefone();
	                
	            case 4:
	            	try {
	            		if (this.listaPessoa.get(rowIndex).getFornecedor()) {
							return "Sim";
						}else{
							return "Não";
						}
					} catch (Exception e) {
						return "ERRO!";
					}
	            	
	            	
	            default:
	                return this.listaPessoa.get(rowIndex); //Desta forma é retornado o objeto inteiro
	        }
	        
	    }
	    
	    @Override
	    public String getColumnName(int columnIndex){
	        return this.colunas[columnIndex];
	    }
	}
