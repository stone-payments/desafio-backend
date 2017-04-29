package glx.com.StarWarsStore.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import glx.com.StarWarsStore.controllers.StarStoreController;

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public class ValiationCustonException extends RuntimeException {
		
		private Logger log = LoggerFactory.getLogger(StarStoreController.class);
		public String message;

		public ValiationCustonException(String msg, String insert) {
			this.message = msg;
			log.error("Null fields: " + insert +" "+this.message);
		}
		

		@Override
		public String getMessage() {
			return message;
		}

	}
