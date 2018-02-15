package br.com.joaoverissimo.curso.pontointeligente.utils;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils implements Serializable {

	private static final Logger log = LoggerFactory.getLogger(PasswordUtils.class);
	private static final long serialVersionUID = 1L;

	public PasswordUtils() {
	}
	
	/**
	 * Gera um hash utilizando o BCrypt.
	 */
	public static String gerarBCrypt(String senha) {
		if (senha == null) {
			return senha;
		}

		log.info("Gerando hash com o BCrypt.");
		BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();
		return bCryptEncoder.encode(senha);
	}
	
}
