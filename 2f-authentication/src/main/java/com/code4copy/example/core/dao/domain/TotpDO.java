package com.code4copy.example.core.dao.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "totp_info")
@Entity
public class TotpDO {
    @Id
    @Column(name = "email_id", nullable = false)
    private String emailId;

    @Column(name = "secret_key", nullable = false, length = 36)
    private String secretKey;

    @Column(name = "company_name", length = 128)
    private String companyName;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}