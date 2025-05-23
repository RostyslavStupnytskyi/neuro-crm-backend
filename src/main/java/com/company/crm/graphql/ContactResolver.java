package com.company.crm.graphql;

import com.company.crm.domain.model.Contact;
import com.company.crm.service.ContactService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ContactResolver {

    private final ContactService service;

    public ContactResolver(ContactService service) {
        this.service = service;
    }

    @QueryMapping
    public List<Contact> contacts() {
        return service.findAll();
    }

    @MutationMapping
    public Contact createContact(@Argument("input") Contact input) {
        return service.save(input);
    }
}
