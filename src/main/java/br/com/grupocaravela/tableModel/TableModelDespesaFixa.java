package br.com.grupocaravela.tableModel;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import br.com.grupocaravela.entidade.DespesaFixa;

public class TableModelDespesaFixa extends AbstractTableModel{

		private static final long serialVersionUID = 1L;

		private ArrayList<DespesaFixa> listaDespesaFixa;
	    //Titulo das colunas
	    private String[] colunas = {"Id", "Nome", "Valor", "Data criação"};
	    
	    //Construtor
	    public TableModelDespesaFixa(){
	        this.listaDespesaFixa = new ArrayList<>();
	    }
	    
	    //############### Inicio dos Metodos do TableModel ###################
	    public void addDespesaFixa(DespesaFixa c){
	        this.listaDespesaFixa.add(c);
	        fireTableDataChanged();
	    }
	    
	    public void removeDespesaFixa(int rowIndex){
	        this.listaDespesaFixa.remove(rowIndex);
	        fireTableDataChanged();
	    }
	    
	    public DespesaFixa getDespesaFixa(int rowIndex){
	        return this.listaDespesaFixa.get(rowIndex); 
	    }
	    
	    //############### Fim dos Metodos do TableModel ###################

	    @Override
	    public int getRowCount() {
	        return this.listaDespesaFixa.size();
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
	                return this.listaDespesaFixa.get(rowIndex).getId();
	            
	            case 1:
	                return this.listaDespesaFixa.get(rowIndex).getNome();
	                
	            case 2:
	                return this.listaDespesaFixa.get(rowIndex).getValor();
	                
	            case 3:
	                return this.listaDespesaFixa.get(rowIndex).getDataCriacao();
	                
	            default:
	                return this.listaDespesaFixa.get(rowIndex); //Desta forma é retornado o objeto inteiro
	        }
	        
	    }
	    
	    @Override
	    public String getColumnName(int columnIndex){
	        return this.colunas[columnIndex];
	    }
	}
