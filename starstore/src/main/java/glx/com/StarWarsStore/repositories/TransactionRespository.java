package glx.com.StarWarsStore.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import glx.com.StarWarsStore.entities.Transaction;

public interface TransactionRespository extends JpaRepository<Transaction, Long> {

	@SuppressWarnings("unchecked")
	public Transaction save(Transaction transaction);

	public Collection<Transaction> findByClientId(String clientId);
}
