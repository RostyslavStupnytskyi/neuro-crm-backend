package com.company.crm.domain.model;

import com.company.crm.config.JsonConverter;
import jakarta.persistence.*;

@Entity
@Table(name = "leads")
public class Lead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String source;

    @Enumerated(EnumType.STRING)
    private LeadStatus status = LeadStatus.NEW;

    @Column(columnDefinition = "jsonb")
    @Convert(converter = JsonConverter.class)
    private Object contactData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public LeadStatus getStatus() {
        return status;
    }

    public void setStatus(LeadStatus status) {
        this.status = status;
    }

    public Object getContactData() {
        return contactData;
    }

    public void setContactData(Object contactData) {
        this.contactData = contactData;
    }
}
