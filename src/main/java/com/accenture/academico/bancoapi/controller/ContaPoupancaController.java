package com.accenture.academico.bancoapi.controller;

import com.accenture.academico.bancoapi.entity.ContaCorrente;
import com.accenture.academico.bancoapi.entity.ContaPoupanca;
import com.accenture.academico.bancoapi.exception.AgenciaNotFoundException;
import com.accenture.academico.bancoapi.exception.ClienteNotFoundException;
import com.accenture.academico.bancoapi.exception.ContaCorrenteNotFoundException;
import com.accenture.academico.bancoapi.exception.ContaPoupancaNotFoundException;
import com.accenture.academico.bancoapi.model.ContaCorrenteModel;
import com.accenture.academico.bancoapi.model.ContaPoupancaModel;
import com.accenture.academico.bancoapi.model.ErrorModel;
import com.accenture.academico.bancoapi.service.ContaCorrenteService;
import com.accenture.academico.bancoapi.service.ContaPoupancaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ContaPoupancaController {

    @Autowired
    ContaPoupancaService contaPoupancaService;

    @GetMapping("/contaspoupancas")
    public ResponseEntity<List<ContaPoupanca>> getAllContasPoupancas()
    {
        return new ResponseEntity<>(contaPoupancaService.getAllContasPoupancas(), HttpStatus.OK);
    }

    @GetMapping("/contaspoupancas/{id}")
    public ResponseEntity<ContaPoupanca> getContaPoupanca(@PathVariable("id") long id)
    {
        try{
            var contaPoupanca = contaPoupancaService.getContaPoupancaById(id);
            return new ResponseEntity<>(contaPoupanca, HttpStatus.OK);
        } catch (ContaPoupancaNotFoundException e){
            return new ResponseEntity(new ErrorModel(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/contaspoupancas/{id}")
    public ResponseEntity<Integer> deleteContaPoupanca(@PathVariable("id") long id)
    {
        try {
            var delete = contaPoupancaService.delete(id);
            return new ResponseEntity<>(delete, HttpStatus.OK);
        } catch (ContaPoupancaNotFoundException e){
            return new ResponseEntity(new ErrorModel(e.getMessage()), HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/contapoupanca")
    public ResponseEntity<ContaPoupanca> saveContaPoupanca(@RequestBody ContaPoupancaModel contaPoupancaModel)
    {
        try {
            var contaPoupanca = contaPoupancaService.saveOrUpdate(contaPoupancaModel);
            return new ResponseEntity<>(contaPoupanca, HttpStatus.CREATED);
        } catch (ClienteNotFoundException e){
            return new ResponseEntity(new ErrorModel(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (AgenciaNotFoundException e){
            return new ResponseEntity(new ErrorModel(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/saquecontapoupanca/{id}/{valor}")
    public ResponseEntity<Double> saqueContaPoupanca(@PathVariable("id") long id, @PathVariable("valor") double valor)
    {
        try {
            var saque = contaPoupancaService.saqueContaPoupanca(id, valor);
            return new ResponseEntity(saque, HttpStatus.OK);
        } catch (ContaPoupancaNotFoundException e){
            return new ResponseEntity(new ErrorModel(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/depositocontapoupanca/{id}/{valor}")
    public ResponseEntity<Double> depositoContaPoupanca(@PathVariable("id") long id, @PathVariable("valor") double valor)
    {
        try {
            var deposito = contaPoupancaService.depositoContaPoupanca(id, valor);
            return new ResponseEntity(deposito, HttpStatus.OK);
        } catch (ContaPoupancaNotFoundException e){
            return new ResponseEntity(new ErrorModel(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/transferenciaentrecontaspoupancasbanco/{idCPI}/{valor}/{idCPD}")
    public ResponseEntity<Double> transferenciaEntreContasPoupancasBanco(@PathVariable("idCPI") long idCPI, @PathVariable("valor") double valor, @PathVariable("idCPD") long idCPD)
    {
        try {
            var transferenciaEntreContasPoupancasBanco = contaPoupancaService.transferenciaEntreContasPoupancasBanco(idCPI, valor, idCPD);
            return new ResponseEntity(transferenciaEntreContasPoupancasBanco, HttpStatus.OK);
        } catch (ContaPoupancaNotFoundException e){
            return new ResponseEntity(new ErrorModel(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/transferenciaentrecontaspoupancasoutrobanco/{idCPI}/{valor}/{idCPExterno}")
    public ResponseEntity<Double> transferenciaEntreContasPoupancasOutroBanco(@PathVariable("idCPI") long idCPI, @PathVariable("valor") double valor, @PathVariable("idCPExterno") long idCPExterno)
    {
        try {
            var transferenciaEntreContasPoupancasOutroBanco = contaPoupancaService.transferenciaEntreContasPoupancasOutroBanco(idCPI, valor, idCPExterno);
            return new ResponseEntity(transferenciaEntreContasPoupancasOutroBanco, HttpStatus.OK);
        } catch (ContaPoupancaNotFoundException e){
            return new ResponseEntity(new ErrorModel(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

}