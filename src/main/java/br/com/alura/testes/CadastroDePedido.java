package br.com.alura.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ClienteDao;
import br.com.alura.loja.dao.PedidoDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.util.JPAUtil;
import br.com.alura.loja.vo.RelatorioDeVendasVO;
import br.com.alura.modelo.Categoria;
import br.com.alura.modelo.Cliente;
import br.com.alura.modelo.ItemPedido;
import br.com.alura.modelo.Pedido;
import br.com.alura.modelo.Produto;

public class CadastroDePedido {

	public static void main(String[] args) {
		popularBancoDeDados();

		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		ClienteDao clienteDao = new ClienteDao(em);

		Produto produto = produtoDao.buscarPorId(1l);
		Produto produto2 = produtoDao.buscarPorId(2l);
		Produto produto3 = produtoDao.buscarPorId(3l);
		Cliente cliente = clienteDao.buscarPorId(1l);

		em.getTransaction().begin();

		Pedido pedido = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(10, produto, pedido));
		pedido.adicionarItem(new ItemPedido(40, produto2, pedido));
		
		Pedido pedido2 = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(2, produto3, pedido));

		PedidoDao pedidoDao = new PedidoDao(em);
		pedidoDao.cadastrar(pedido);
		pedidoDao.cadastrar(pedido2);

		em.getTransaction().commit();
		
		BigDecimal totalVendido = pedidoDao.valorTotalVendido();
		System.out.println("Valor Total: " + totalVendido);
		
		List<RelatorioDeVendasVO> relatorioDeVendas = pedidoDao.relatorioDeVendas();
		relatorioDeVendas.forEach(System.out::println);
		Cliente buscarPorId = clienteDao.buscarPorId(1l);
		System.out.println(buscarPorId.getNome());
		
		
		
		em.close();

	}

	private static void popularBancoDeDados() {
		Categoria celulares = new Categoria("celulares", "xpto");
		Categoria videogames = new Categoria("videogames", "xpto");
		Categoria informatica = new Categoria("informatica", "xpto");
		
		Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), celulares);
		Produto videogame = new Produto("PS5", "PlayStation 5", new BigDecimal("5000"), videogames);
		Produto macbook = new Produto("Macbook", "Macbook pro retina", new BigDecimal("10000"), informatica);
		
		Cliente cliente = new Cliente("Thais", "45041250804");

		EntityManager em = JPAUtil.getEntityManager();
		CategoriaDao categoriaDao = new CategoriaDao(em);
		ProdutoDao produtoDao = new ProdutoDao(em);
		ClienteDao clienteDao = new ClienteDao(em);

		em.getTransaction().begin();

		categoriaDao.cadastrar(celulares);
		categoriaDao.cadastrar(videogames);
		categoriaDao.cadastrar(informatica);
		
		produtoDao.cadastrar(celular);
		produtoDao.cadastrar(videogame);
		produtoDao.cadastrar(macbook);
		
		clienteDao.cadastrar(cliente);

		em.getTransaction().commit();
		em.close();
	}

}
