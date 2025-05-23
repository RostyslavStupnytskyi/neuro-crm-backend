package com.company.crm.service;

import com.company.crm.domain.model.Contact;
import com.company.crm.repository.ContactRepository;
import com.company.crm.service.common.CrudServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl extends CrudServiceImpl<Contact, Long> implements ContactService {
    public ContactServiceImpl(ContactRepository repository) {
        super(repository);
    }
}
