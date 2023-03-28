package br.com.alura.testes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.util.JPAUtil;
import br.com.alura.modelo.Categoria;
import br.com.alura.modelo.CategoriaId;
import br.com.alura.modelo.Produto;

public class CadastroDeProduto {

	public static void main(String[] args) {
		
		cadastrarProduto(); 
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		
		Produto p = produtoDao.buscarPorId(1l);
		System.out.println(p.getPreco());
//		List<Produto> buscarTodos = produtoDao.buscarTodos();
//		
//		buscarTodos.forEach(p2 -> System.out.println(p2.getNome()));
		
//		List<Produto> buscarNome = produtoDao.buscarPorNome("Xiaomi Redmi");
//		
//		buscarNome.forEach(p2 -> System.out.println(p2.getNome()));

		List<Produto> buscarNomeCat = produtoDao.buscarPorNomeDaCategoria("celulares");
		
		buscarNomeCat.forEach(p2 -> System.out.println(p2.getNome()));
		
		BigDecimal buscarPreco = produtoDao.buscarPrecoDoProdutoComNome("Xiaomi Redmi");
		
		System.out.println("Preço do produto: "+buscarPreco);
		List<Produto> buscarPorParametrosComCriteria = produtoDao.buscarPorParametrosComCriteria(null, buscarPreco, LocalDate.now());
		buscarPorParametrosComCriteria.forEach(b -> {
			System.out.println(b);
		});
		
		Categoria find = em.find(Categoria.class, new CategoriaId("celulares", "xpto"));
		System.out.println(find.getNome());
		
		em.close();
		

	}

	private static void cadastrarProduto() {
		Categoria celulares = new Categoria("celulares", "xpto");
		Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), celulares);

		EntityManager em = JPAUtil.getEntityManager();
		CategoriaDao categoriaDao = new CategoriaDao(em);
		ProdutoDao produtoDao = new ProdutoDao(em);
		
		em.getTransaction().begin();
		
		categoriaDao.cadastrar(celulares);
		produtoDao.cadastrar(celular);
		
		em.getTransaction().commit();
		em.close();
	}

}
