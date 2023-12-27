package br.com.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredObjectIsNullFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L; 

	public RequiredObjectIsNullFoundException(String ex){
		super(ex);
	}
	
	public RequiredObjectIsNullFoundException(){
		super("Não e permitido persistir um objeto nulo!");
	}
	
}
