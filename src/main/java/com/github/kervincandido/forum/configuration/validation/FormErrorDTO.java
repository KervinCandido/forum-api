package com.github.kervincandido.forum.configuration.validation;

public class FormErrorDTO {
	
	private String field;
	private String messagem;

	public FormErrorDTO(String field, String messagem) {
		this.messagem = messagem;
		this.field = field;
	}
	
	public FormErrorDTO() {
		
	}
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getMessagem() {
		return messagem;
	}
	public void setMessagem(String messagem) {
		this.messagem = messagem;
	}
}
