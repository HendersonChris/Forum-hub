package br.com.forumhub.domain;

public class ValidacaoException extends RuntimeException {

    public ValidacaoException(String mensagemErro) {
        super(mensagemErro);
    }

}
