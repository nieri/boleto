package br.com.munieri.boleto.repository;

import br.com.munieri.boleto.model.Boleto;
import br.com.munieri.boleto.model.Status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoletoRepositoryTest {

    @Autowired
    BoletoRepository repository;

    @Before
    public void setUp() {
        repository.deleteAll();
    }


    @Test
    public void should_set_id_on_save() {
        Boleto boleto = repository.save(new Boleto(LocalDate.now(), BigDecimal.TEN, "Joao da Silva", Status.PENDING));
        assertThat(boleto.getId()).isNotNull();
    }
}
