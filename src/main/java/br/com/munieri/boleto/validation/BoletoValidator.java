package br.com.munieri.boleto.validation;

import br.com.munieri.boleto.exception.AmountException;
import br.com.munieri.boleto.exception.BankSlipsException;
import br.com.munieri.boleto.exception.CustomerException;
import br.com.munieri.boleto.exception.DueDateException;
import br.com.munieri.boleto.model.Boleto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class BoletoValidator {

    public void validate(Boleto boleto) {

        if (boleto == null) {
            throw new BankSlipsException();
        }
        this.checkDueDate(boleto);
        this.checkCustomer(boleto);
        this.checkAmount(boleto);
    }

    private void checkAmount(Boleto boleto) {
        if (boleto.getValor() == null || boleto.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new AmountException();
        }
    }

    private void checkCustomer(Boleto boleto) {
        if (boleto.getCliente() == null || boleto.getCliente().isEmpty()) {
            throw new CustomerException();
        }
    }

    private void checkDueDate(Boleto boleto) {
        if (boleto.getDataVencimento().isBefore(LocalDate.now().minusDays(1))) {
            throw new DueDateException();
        }
    }
}
