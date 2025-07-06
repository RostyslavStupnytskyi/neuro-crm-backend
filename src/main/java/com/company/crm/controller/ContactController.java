package com.company.crm.controller;

import com.company.crm.domain.model.Contact;
import com.company.crm.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/contacts")
public class ContactController {

    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    @GetMapping
    public List<Contact> list() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<Contact> create(@Validated @RequestBody Contact contact) {
        Contact saved = service.save(contact);
        return ResponseEntity.created(URI.create("/api/v1/contacts/" + saved.getId())).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> get(@PathVariable Long id) {
        Contact c = service.findById(id);
        return c != null ? ResponseEntity.ok(c) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> update(@PathVariable Long id, @Validated @RequestBody Contact contact) {
        Contact existing = service.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        
        contact.setId(id);
        Contact updated = service.save(contact);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
