package com.code4copy.example.core.service.api;

import com.code4copy.example.core.dao.domain.TotpDO;
import com.code4copy.example.rest.resource.TotpResource;
import com.google.zxing.WriterException;

public interface TotpService {
    TotpResource addNew(TotpResource totpResource);

    TotpDO getByEmailId(String emailId);

    TotpResource update(TotpResource totpResource);

    TotpResource getById(String emailId);

    String getQrById(String emailId, int size) throws WriterException;
}
