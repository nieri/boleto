package br.com.munieri.boleto.controller;

import br.com.munieri.boleto.exception.BankSlipsNotFoundException;
import br.com.munieri.boleto.model.Boleto;
import br.com.munieri.boleto.model.Status;
import br.com.munieri.boleto.model.fine.FineCalculator;
import br.com.munieri.boleto.repository.BoletoRepository;
import br.com.munieri.boleto.validation.BoletoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collection;

@RestController
@RequestMapping(value = "/bankslips")
public class BoletoController {

    @Autowired
    BoletoRepository repository;

    @Autowired
    BoletoValidator validator;

    @Autowired
    FineCalculator calculator;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED, reason = "Bankslip created")
    public Boleto post(@RequestBody Boleto boleto) {

        validator.validate(boleto);
        Boleto boletoSalvo = repository.insert(boleto);

        return boletoSalvo;
    }


    @GetMapping("/{id}")
    public Boleto get(@PathVariable String id) {
        Boleto boleto = this.repository.findById(id).orElseThrow(() -> new BankSlipsNotFoundException());

        BigDecimal fine = calculator.calculate(boleto);
        boleto.setFine(fine);

        return boleto;
    }

    @GetMapping()
    public Collection<Boleto> get() {
        return this.repository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@RequestBody StatusDTO dto, @PathVariable("id") String id) {
        this.validateBankSlips(id);

        if (Status.PAID.equals(dto.getStatus())) {
            repository.updateStatus(dto.getStatus(), id);
            return ResponseEntity.ok(" Bankslip paid");
        }

        if (Status.CANCELED.equals(dto.getStatus())) {
            repository.updateStatus(dto.getStatus(), id);
            return ResponseEntity.ok(" Bankslip canceled");
        }

        throw new UnsupportedOperationException();
    }

    private void validateBankSlips(String id) {
        this.repository.findById(id).orElseThrow(() -> new BankSlipsNotFoundException());
    }

//    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, reason="Internal Server Error")
//    @ExceptionHandler({Exception.class})
//    public void errorHandler(Exception ex) {
//        //logger.error(ex);
//    }
}
