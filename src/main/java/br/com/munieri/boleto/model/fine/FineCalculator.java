package br.com.munieri.boleto.model.fine;

import br.com.munieri.boleto.model.Boleto;
import br.com.munieri.boleto.model.Status;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
public class FineCalculator {

    public BigDecimal calculate(Boleto boleto) {

        BigDecimal fine = null;

        if (Status.PENDING.equals(boleto.getStatus())) {
            LocalDate dataAtual = LocalDate.now();
            LocalDate dataVencimento = boleto.getDataVencimento();
            long diferencaDias = DAYS.between(dataVencimento, dataAtual);

            if (diferencaDias > 0 && diferencaDias <= 5) {
                fine = (boleto.getValor().multiply(new BigDecimal(0.05)));
            }

            if (diferencaDias > 0 && diferencaDias >= 10) {
                fine = (boleto.getValor().multiply(new BigDecimal(0.10)));
            }
        }
        return fine;
    }
}
