package br.com.grupocaravela.tableModel;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import br.com.grupocaravela.entidade.Debito;

public class TableModelDebito extends AbstractTableModel{

		private static final long serialVersionUID = 1L;

		private ArrayList<Debito> listaDebito;
	    //Titulo das colunas
	    private String[] colunas = {"Id", "Nome", "Valor", "Data debito", "Entidade", "Pessoa"};
	    
	    //Construtor
	    public TableModelDebito(){
	        this.listaDebito = new ArrayList<>();
	    }
	    
	    //############### Inicio dos Metodos do TableModel ###################
	    public void addDebito(Debito c){
	        this.listaDebito.add(c);
	        fireTableDataChanged();
	    }
	    
	    public void removeDebito(int rowIndex){
	        this.listaDebito.remove(rowIndex);
	        fireTableDataChanged();
	    }
	    
	    public Debito getDebito(int rowIndex){
	        return this.listaDebito.get(rowIndex); 
	    }
	    
	    //############### Fim dos Metodos do TableModel ###################

	    @Override
	    public int getRowCount() {
	        return this.listaDebito.size();
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
	            		return this.listaDebito.get(rowIndex).getId();
					} catch (Exception e) {
						return "";
					}	                
	            
	            case 1:
	            	try {
	            		return this.listaDebito.get(rowIndex).getNome();
					} catch (Exception e) {
						return "";
					}	                
	                
	            case 2:
	            	try {
	            		return this.listaDebito.get(rowIndex).getValor();
					} catch (Exception e) {
						return "";
					}	                
	                
	            case 3:
	            	try {
	            		return this.listaDebito.get(rowIndex).getDataDebito();
					} catch (Exception e) {
						return "";
					}	                
	                
	            case 4:
	            	try {
	            		return this.listaDebito.get(rowIndex).getEntidade().getNome();
					} catch (Exception e) {
						return "";
					}	                
	                
	            case 5:
	            	try {
	            		return this.listaDebito.get(rowIndex).getPessoa().getNome();
					} catch (Exception e) {
						return "";
					}	                
	                
	            default:
	                return this.listaDebito.get(rowIndex); //Desta forma é retornado o objeto inteiro
	        }
	        
	    }
	    
	    @Override
	    public String getColumnName(int columnIndex){
	        return this.colunas[columnIndex];
	    }
	}
