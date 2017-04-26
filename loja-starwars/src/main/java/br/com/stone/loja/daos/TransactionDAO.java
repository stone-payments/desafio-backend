package br.com.stone.loja.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.com.stone.loja.models.Transaction;

@Repository
public class TransactionDAO {

	@PersistenceContext
	private EntityManager manager;

	public void save(Transaction transaction) {
		manager.persist(transaction);
	}

	public List<Transaction> all() {
		return manager.createQuery("select t from Transaction t", Transaction.class).getResultList();
	}

	public List<Transaction> findByClientId(String id) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Transaction> criteria = builder.createQuery(Transaction.class);
		Root<Transaction> root = criteria.from(Transaction.class);
		Predicate like = builder.like(root.<String>get("clientId"), id);
		return manager.createQuery(criteria.select(root).where(like)).getResultList();
	}

}
