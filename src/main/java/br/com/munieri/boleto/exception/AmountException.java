package br.com.munieri.boleto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Invalid bankslip provided.The possible reasons are: " +
        "A field of the provided bankslip was null or with invalid values")
public class AmountException extends RuntimeException {
}
