package br.com.munieri.boleto.repository;

import br.com.munieri.boleto.model.Boleto;
import br.com.munieri.boleto.model.Status;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoletoRepository extends MongoRepository<Boleto, String>, BoletoCustomRepository{
}
