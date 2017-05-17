package br.com.starstore.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.starstore.model.ProductModel;

public class ProductDaoTest {

	private static Map<Long, ProductModel> banco = new HashMap<Long, ProductModel>();

	static {
		banco.put(1l, new ProductModel("Blusa Han Shot First", 7990, "78993-000", "João da Silva",
				"https://cdn.awsli.com.br/600x450/21/21351/produto/3853007/f66e8c63ab.jpg", "26/11/2015"));

		banco.put(2l,
				new ProductModel("Sabre de luz", 150000, "13537-000", "Mario Mota",
						"http://www.obrigadopelospeixes.com/wp-content/uploads/2015/12/kalippe_lightsaber_by_jnetrocks-d4dyzpo1-1024x600.jpg",
						"20/11/2015"));
	}

	public void adiciona(ProductModel product) {
		banco.put((long) (banco.size()), product);
	}

	public List<ProductModel> buscar() {
		return new ArrayList<>(banco.values());
	}

	public ProductModel buscarPorId(long produtoId) {
		return banco.get(produtoId);
	}

	public ProductModel remove(long id) {
		return banco.remove(id);
	}
}
