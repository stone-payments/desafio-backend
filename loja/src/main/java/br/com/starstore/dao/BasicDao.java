package br.com.starstore.dao;

import java.util.List;

public interface BasicDao<T> {
	
	void adicionar(T t) throws Exception;
	List<T> buscar(T t) throws Exception;
}
