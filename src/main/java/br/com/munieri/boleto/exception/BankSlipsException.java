package br.com.munieri.boleto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bankslip not provided in the request body")
public class BankSlipsException extends RuntimeException {
}
