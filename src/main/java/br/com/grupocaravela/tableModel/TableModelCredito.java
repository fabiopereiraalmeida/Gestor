package br.com.grupocaravela.tableModel;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import br.com.grupocaravela.entidade.Credito;

public class TableModelCredito extends AbstractTableModel{

		private static final long serialVersionUID = 1L;

		private ArrayList<Credito> listaCredito;
	    //Titulo das colunas
	    private String[] colunas = {"Id", "Nome", "Valor", "Data crédito", "Entidade", "Pessoa"};
	    
	    //Construtor
	    public TableModelCredito(){
	        this.listaCredito = new ArrayList<>();
	    }
	    
	    //############### Inicio dos Metodos do TableModel ###################
	    public void addCredito(Credito c){
	        this.listaCredito.add(c);
	        fireTableDataChanged();
	    }
	    
	    public void removeCredito(int rowIndex){
	        this.listaCredito.remove(rowIndex);
	        fireTableDataChanged();
	    }
	    
	    public Credito getCredito(int rowIndex){
	        return this.listaCredito.get(rowIndex); 
	    }
	    
	    //############### Fim dos Metodos do TableModel ###################

	    @Override
	    public int getRowCount() {
	        return this.listaCredito.size();
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
	            	try {
	            		return this.listaCredito.get(rowIndex).getId();
					} catch (Exception e) {
						return "";
					}                
	            
	            case 1:
	            	try {
	            		return this.listaCredito.get(rowIndex).getNome();
					} catch (Exception e) {
						return "";
					}	                
	                
	            case 2:
	            	try {
	            		return this.listaCredito.get(rowIndex).getValor();
					} catch (Exception e) {
						return "";
					}	                
	                
	            case 3:
	            	try {
	            		return this.listaCredito.get(rowIndex).getDataCredito();
					} catch (Exception e) {
						return "";
					}
	                	                
	            case 4:
	            	try {
	            		return this.listaCredito.get(rowIndex).getEntidade().getNome();
					} catch (Exception e) {
						return "";
					}	                
	                
	            case 5:
	            	try {
	            		return this.listaCredito.get(rowIndex).getPessoa().getNome();
					} catch (Exception e) {
						return "";
					}
	                	                
	            default:
	                return this.listaCredito.get(rowIndex); //Desta forma é retornado o objeto inteiro
	        }
	        
	    }
	    
	    @Override
	    public String getColumnName(int columnIndex){
	        return this.colunas[columnIndex];
	    }
	}
