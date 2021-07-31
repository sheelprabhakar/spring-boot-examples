package com.code4copy.example.rest;

import com.code4copy.example.core.service.api.TotpService;
import com.code4copy.example.rest.resource.TotpResource;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController(value = "/")
public class TotpController {

    private final TotpService totpService;

    @Autowired
    public  TotpController(final TotpService totpService){
        this.totpService = totpService;
    }

    @PostMapping( consumes = "application/json", produces = "application/json")
    public ResponseEntity<TotpResource> addNew(@Valid @RequestBody TotpResource totpResource) {
        totpResource = this.totpService.addNew(totpResource);

        return ResponseEntity.created(URI.create("/" + totpResource.getEmailId())).body(totpResource);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<TotpResource> update(@Valid @RequestBody TotpResource totpResource) {
        totpResource = this.totpService.update(totpResource);

        return ResponseEntity.ok().body(totpResource);
    }

    @GetMapping(value = "{emailId}", produces = "application/json")
    public ResponseEntity<TotpResource> getById(@Valid @PathVariable("emailId") String emailId) {
        TotpResource totpResource = this.totpService.getById(emailId);

        return ResponseEntity.ok().body(totpResource);
    }

    @GetMapping(value = "{emailId}/qrcode", produces = "application/json")
    public ResponseEntity<String> getQrById(@Valid @PathVariable("emailId") String emailId,
                                            @Valid @RequestParam("size") Integer size) throws WriterException {
        String qrCode = this.totpService.getQrById(emailId, size);
        return ResponseEntity.ok().body(qrCode);
    }
}
