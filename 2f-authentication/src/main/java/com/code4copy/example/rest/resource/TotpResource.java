package com.code4copy.example.rest.resource;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class TotpResource {

    @NotBlank
    @Email
    private String emailId;
    private String companyName;
    private String code;

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
