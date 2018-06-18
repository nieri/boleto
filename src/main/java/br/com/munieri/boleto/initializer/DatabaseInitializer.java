package br.com.munieri.boleto.initializer;

import br.com.munieri.boleto.model.Boleto;
import br.com.munieri.boleto.model.Status;
import br.com.munieri.boleto.repository.BoletoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DatabaseInitializer {


    @Autowired
    BoletoRepository repository;

    @PostConstruct
    public void init() {

        repository.save(new Boleto(LocalDate.now(), new BigDecimal(100), "Jo Miranda", Status.PENDING));
        repository.save(new Boleto(LocalDate.now().minusDays(5), new BigDecimal(100), "Jo Miranda", Status.PENDING));
        repository.save(new Boleto(LocalDate.now().minusDays(10), new BigDecimal(100), "Jo Miranda", Status.PENDING));
    }
}
