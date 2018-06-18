package br.com.munieri.boleto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Bankslip not found with the specified id")
public class BankSlipsNotFoundException extends RuntimeException {
}
