package br.com.grupocaravela.tableModel;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import br.com.grupocaravela.entidade.Entidade;

public class TableModelEntidade extends AbstractTableModel{

		private static final long serialVersionUID = 1L;

		private ArrayList<Entidade> listaEntidade;
	    //Titulo das colunas
	    private String[] colunas = {"Id", "Nome", "Data criação", "Observação"};
	    
	    //Construtor
	    public TableModelEntidade(){
	        this.listaEntidade = new ArrayList<>();
	    }
	    
	    //############### Inicio dos Metodos do TableModel ###################
	    public void addEntidade(Entidade c){
	        this.listaEntidade.add(c);
	        fireTableDataChanged();
	    }
	    
	    public void removeEntidade(int rowIndex){
	        this.listaEntidade.remove(rowIndex);
	        fireTableDataChanged();
	    }
	    
	    public Entidade getEntidade(int rowIndex){
	        return this.listaEntidade.get(rowIndex); 
	    }
	    
	    //############### Fim dos Metodos do TableModel ###################

	    @Override
	    public int getRowCount() {
	        return this.listaEntidade.size();
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
	                return this.listaEntidade.get(rowIndex).getId();
	            
	            case 1:
	                return this.listaEntidade.get(rowIndex).getNome();
	                
	            case 2:
	                return this.listaEntidade.get(rowIndex).getDataCriacao();
	                
	            case 3:
	                return this.listaEntidade.get(rowIndex).getObs();
	                
	            default:
	                return this.listaEntidade.get(rowIndex); //Desta forma é retornado o objeto inteiro
	        }
	        
	    }
	    
	    @Override
	    public String getColumnName(int columnIndex){
	        return this.colunas[columnIndex];
	    }
	}
