package com.code4copy.example.core.service.api.impl;

import com.code4copy.example.core.dao.api.ToptRepository;
import com.code4copy.example.core.dao.domain.TotpDO;
import com.code4copy.example.core.service.api.TotpService;
import com.code4copy.example.rest.resource.TotpResource;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base32;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;

@Service
public class TotpServiceImpl implements TotpService {
    private final  ToptRepository toptRepository;
    private final SecureRandom random = new SecureRandom();
    @Autowired
    public TotpServiceImpl(final  ToptRepository toptRepository){
        this.toptRepository = toptRepository;
    }

    @Override
    public TotpResource addNew(TotpResource totpResource) {
        if(existByEmailId(totpResource.getEmailId())){
            throw new EntityExistsException();
        }

        TotpDO totpDO = toptResourceToDO(totpResource);
        totpDO.setSecretKey(generateSecretKey());
        totpDO = this.toptRepository.save(totpDO);
        return toptDOToResource(totpDO);
    }

    @Override
    public Boolean existByEmailId(final String emailId) {
        return this.toptRepository.existsById(emailId);
    }

    @Override
    public TotpDO getByEmailId(final String emailId) {
        return this.toptRepository.getById(emailId);
    }

    @Override
    public TotpResource update(TotpResource totpResource) {
        final TotpDO totpDO = getByEmailId(totpResource.getEmailId());
        if(totpDO == null){
            throw new EntityNotFoundException();
        }

        totpDO.setSecretKey(generateSecretKey());
        totpDO.setCompanyName(totpResource.getCompanyName());
        TotpDO totpDOUpdate = this.toptRepository.save(totpDO);

        return toptDOToResource(totpDOUpdate);
    }

    @Override
    public TotpResource getById(String emailId) {
        final TotpDO totpDO = getByEmailId(emailId);
        if(totpDO == null){
            throw new EntityNotFoundException();
        }
        return toptDOToResource(totpDO);
    }

    @Override
    public String getQrById(String emailId, int size) throws WriterException {
        String data = null;
        final TotpDO totpDO = getByEmailId(emailId);
        if(totpDO == null){
            throw new EntityNotFoundException();
        }

        String barCodeUrl = getGoogleAuthenticatorBarCode(totpDO.getSecretKey(), totpDO.getEmailId(), totpDO.getCompanyName());

            BitMatrix matrix = new MultiFormatWriter().encode(barCodeUrl, BarcodeFormat.QR_CODE,
                   size, size);
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                MatrixToImageWriter.writeToStream(matrix, "png", out);
                byte[] encoded = Base64Utils.encode(out.toByteArray());
                data = new String(encoded, Charsets.toCharset("UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        return data;
    }

    private static TotpDO toptResourceToDO(final TotpResource totpResource){
        TotpDO totpDO = new TotpDO();
        totpDO.setCompanyName(totpResource.getCompanyName());
        totpDO.setEmailId(totpResource.getEmailId());
        totpDO.setSecretKey(totpResource.getCode());

        return totpDO;
    }

    private static TotpResource toptDOToResource(final TotpDO totpDo){
        TotpResource totpResource = new TotpResource();
        totpResource.setCompanyName(totpDo.getCompanyName());
        totpResource.setEmailId(totpDo.getEmailId());
        totpResource.setCode(totpDo.getSecretKey());

        return totpResource;
    }

    private  String generateSecretKey() {

        byte[] bytes = new byte[20];
        this.random.nextBytes(bytes);
        Base32 base32 = new Base32();
        return base32.encodeToString(bytes);
    }

    private static String getGoogleAuthenticatorBarCode(String secretKey, String account, String issuer) {
        try {
            return "otpauth://totp/"
                    + URLEncoder.encode(issuer + ":" + account, "UTF-8").replace("+", "%20")
                    + "?secret=" + URLEncoder.encode(secretKey, "UTF-8").replace("+", "%20")
                    + "&issuer=" + URLEncoder.encode(issuer, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }
}
