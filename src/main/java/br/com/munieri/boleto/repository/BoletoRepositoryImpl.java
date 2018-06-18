package br.com.munieri.boleto.repository;

import br.com.munieri.boleto.model.Boleto;
import br.com.munieri.boleto.model.Status;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class BoletoRepositoryImpl implements BoletoCustomRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public void updateStatus(Status status, String id) {

        Query query = new Query(Criteria.where("id").is(id));
        query.fields().include("id");

        Update update = new Update();
        update.set("status", status);

        UpdateResult result = mongoTemplate.updateFirst(query, update, Boleto.class);
    }
}
