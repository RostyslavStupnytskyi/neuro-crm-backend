package com.company.crm.controller;

import com.company.crm.domain.model.Account;
import com.company.crm.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping
    public List<Account> list() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<Account> create(@Validated @RequestBody Account account) {
        Account saved = service.save(account);
        return ResponseEntity.created(URI.create("/api/v1/accounts/" + saved.getId())).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> get(@PathVariable Long id) {
        Account acc = service.findById(id);
        return acc != null ? ResponseEntity.ok(acc) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
