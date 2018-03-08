package br.com.caelum.estoque.ws;

import javax.xml.ws.WebFault;

@WebFault(name="AutorizacaoFault")
public class AutorizacaoExeception extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AutorizacaoExeception(String message){
		super(message);
	}
}