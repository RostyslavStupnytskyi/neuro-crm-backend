package com.company.crm.graphql;

import com.company.crm.domain.model.Account;
import com.company.crm.service.AccountService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AccountResolver {

    private final AccountService service;

    public AccountResolver(AccountService service) {
        this.service = service;
    }

    @QueryMapping
    public List<Account> accounts() {
        return service.findAll();
    }

    @MutationMapping
    public Account createAccount(@Argument("input") Account input) {
        return service.save(input);
    }
}
