package com.company.crm.service;

import com.company.crm.domain.model.Account;
import com.company.crm.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Account> findAll() {
        return repository.findAll();
    }

    @Override
    public Account save(Account account) {
        return repository.save(account);
    }

    @Override
    public Account findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
