package glx.com.StarWarsStore.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import glx.com.StarWarsStore.controllers.StarStoreController;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SearchNotFoundException extends RuntimeException {
	private Logger log = LoggerFactory.getLogger(StarStoreController.class);

	public String message;

	public SearchNotFoundException(Long id, String pesquisa) {
		this.message = " A PESQUISA DE " + pesquisa + " COM O ID :" + id + " NAO ENTREGOU RESULTADO";
		log.error(this.message);

	}

	public SearchNotFoundException(String pesquisa) {
		this.message = " A PESQUISA DE " + pesquisa + " NAO ENTREGOU RESULTADO";
		log.error(this.message);
	}

	public SearchNotFoundException(String id, String pesquisa) {
		this.message = " A PESQUISA DE " + pesquisa + " COM O ID :" + id + " NAO ENTREGOU RESULTADO";
		log.error(this.message);
	}

	@Override
	public String getMessage() {
		return message;
	}

}
