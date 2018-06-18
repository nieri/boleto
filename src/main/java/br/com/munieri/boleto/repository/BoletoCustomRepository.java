package br.com.munieri.boleto.repository;

import br.com.munieri.boleto.model.Status;

public interface BoletoCustomRepository {

    void updateStatus(Status status, String id);
}
