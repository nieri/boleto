package br.com.munieri.boleto.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@Document(collection = "boletos")
@JsonIgnoreProperties(ignoreUnknown=true)
public class Boleto {

    @Id
    @JsonProperty("id")
    private String id;
    @JsonProperty("due_date")
    private LocalDate dataVencimento;
    @JsonProperty("total_in_cents")
    private BigDecimal valor;
    @JsonProperty("customer")
    private String cliente;
    @JsonProperty("status")
    private Status status;

    public BigDecimal getFine() {
        return fine;
    }

    public void setFine(BigDecimal fine) {
        this.fine = fine;
    }

    @JsonProperty("fine")
    private BigDecimal fine;

    public Boleto() {
    }

    public Boleto(LocalDate dataVencimento, BigDecimal valor, String cliente, Status status) {
        this.dataVencimento = dataVencimento;
        this.valor = valor;
        this.cliente = cliente;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
