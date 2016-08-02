package br.com.grupocaravela.configuracao;

public class Empresa {

	private static String nomeEpresa = "Empresa";
	private static String ipServidor = "localhost";
	private static String portaHttpServidor = "80";
	private static String portaMysqlServidor = "3306";
	public static String getNomeEpresa() {
		return nomeEpresa;
	}
	public static void setNomeEpresa(String nomeEpresa) {
		Empresa.nomeEpresa = nomeEpresa;
	}
	public static String getIpServidor() {
		return ipServidor;
	}
	public static void setIpServidor(String ipServidor) {
		Empresa.ipServidor = ipServidor;
	}
	public static String getPortaHttpServidor() {
		return portaHttpServidor;
	}
	public static void setPortaHttpServidor(String portaHttpServidor) {
		Empresa.portaHttpServidor = portaHttpServidor;
	}
	public static String getPortaMysqlServidor() {
		return portaMysqlServidor;
	}
	public static void setPortaMysqlServidor(String portaMysqlServidor) {
		Empresa.portaMysqlServidor = portaMysqlServidor;
	}
	
	
		
}
