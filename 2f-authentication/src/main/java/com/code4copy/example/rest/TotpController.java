package com.code4copy.example.rest;

import com.code4copy.example.core.service.api.TotpService;
import com.code4copy.example.rest.resource.TotpResource;
import com.google.zxing.WriterException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.net.URI;

@Api(value="Register email for 2F authentication using TOTP")
@RestController(value = "/")
@Validated
public class TotpController {

    private final TotpService totpService;

    @Autowired
    public  TotpController(final TotpService pTotpService){
        this.totpService = pTotpService;
    }

    @ApiOperation(value = "Add new email and company for 2F authentication",response = TotpResource.class)
    @PostMapping( consumes = "application/json", produces = "application/json")
    public ResponseEntity<TotpResource> addNew(@Valid @RequestBody TotpResource totpResource) {
        totpResource = this.totpService.addNew(totpResource);

        return ResponseEntity.created(URI.create("/" + totpResource.getEmailId())).body(totpResource);
    }

    @ApiOperation(value = "Update secret and company for 2F authentication",response = TotpResource.class)
    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<TotpResource> update(@Valid @RequestBody TotpResource totpResource) {
        totpResource = this.totpService.update(totpResource);

        return ResponseEntity.ok().body(totpResource);
    }

    @ApiOperation(value = "Get registered secrete code against email and company",response = TotpResource.class)
    @GetMapping( produces = "application/json")
    public ResponseEntity<TotpResource> getById(@RequestParam("emailId") @Email @NotEmpty String emailId) {
        TotpResource totpResource = this.totpService.getById(emailId);

        return ResponseEntity.ok().body(totpResource);
    }

    @ApiOperation(value = "Get QR code image data in base64 format",response = TotpResource.class)
    @GetMapping(value = "/qrcode", produces = "application/json")
    public ResponseEntity<String> getQrById(@RequestParam("emailId")  @Email @NotEmpty String emailId,
                                            @RequestParam("size") @Min(200) Integer size) throws WriterException {
        String qrCode = this.totpService.getQrById(emailId, size);
        return ResponseEntity.ok().body(qrCode);
    }
}
