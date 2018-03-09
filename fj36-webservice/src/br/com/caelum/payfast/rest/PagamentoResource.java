package br.com.caelum.payfast.rest;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.caelum.payfast.modelo.Pagamento;
import br.com.caelum.payfast.modelo.Transacao;

@Path("/pagamentos")
@Singleton
public class PagamentoResource {

	private Map<Integer,Pagamento> repositorio = new HashMap<>();
	private Integer idPagamento = 1;
	
	public PagamentoResource(){
		Pagamento pagamento = new Pagamento();
		pagamento.setId(idPagamento++);
		pagamento.setValor(BigDecimal.TEN);
		pagamento.comStatusCriado();
		repositorio.put(pagamento.getId(), pagamento);
	}
	
	@GET
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Pagamento buscaPagamento(@PathParam("id") Integer id){
		return repositorio.get(id);
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public Response criarPagamento(Transacao transacao) throws URISyntaxException{
		Pagamento pagamento = new Pagamento();
		pagamento.setId(idPagamento++);
		pagamento.setValor(transacao.getValor());
		pagamento.comStatusCriado();
		
		repositorio.put(pagamento.getId(), pagamento);
		
		System.out.println("Pagamento criado " + pagamento);
		
		return Response.created(new URI("/pagamentos/" + pagamento.getId())).entity(pagamento)
				.type(MediaType.APPLICATION_JSON).build();
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Pagamento confirmarPagamento(@PathParam("id") Integer pagamentoId){
		Pagamento pagamento = repositorio.get(pagamentoId);
		pagamento.comStatusConfirmado();
		//System.out.println(pgto);
		System.out.println("Pagamento confirmado: " + pagamento);
		
		return pagamento;
	}
}
