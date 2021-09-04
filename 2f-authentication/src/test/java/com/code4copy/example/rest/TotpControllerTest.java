package com.code4copy.example.rest;

import com.code4copy.example.rest.resource.TotpResource;
import com.code4copy.example.utils.TestUtils;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TotpControllerTest extends AbstractIntegrationTest{

    @Test
    @Order(0)
    public void test_context(){
        assertNotNull(this.mockMvc);
    }

    @Test
    public void test_get_by_id_not_found() throws Exception {
       String emailId = "notfound@sss.com";
       this.mockMvc.perform(MockMvcRequestBuilders
                       .get("/").param("emailId", emailId)
                       .accept(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andExpect(status().isNotFound());
    }

    @Test
    public void test_get_by_id_invalid_email() throws Exception {
        String emailId = "";
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/").param("emailId", emailId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_add_new_totp_config_created() throws Exception {
        TotpResource res =  getTotpResource("ssp@sss.com");
        this.mockMvc.perform(MockMvcRequestBuilders.post("/").content(TestUtils.convertObjectToJsonString(res))
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").isNotEmpty());

        res =  getTotpResource("1ssp@sss.com");
        res.setCompanyName("Comp11");
        this.mockMvc.perform(MockMvcRequestBuilders.post("/").content(TestUtils.convertObjectToJsonString(res))
                        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("Comp11"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").isNotEmpty());
    }

    @Test
    public void test_add_new_totp_config_duplicate() throws Exception {
        TotpResource res =  getTotpResource("ssp1@sss.com");
        this.mockMvc.perform(MockMvcRequestBuilders.post("/").content(TestUtils.convertObjectToJsonString(res))
                        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").isNotEmpty());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/").content(TestUtils.convertObjectToJsonString(res))
                        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void test_update_totp_config_ok() throws Exception {
        TotpResource res =  getTotpResource("ssp2@sss.com");
        res.setCompanyName("Comp1");
        this.mockMvc.perform(MockMvcRequestBuilders.post("/").content(TestUtils.convertObjectToJsonString(res))
                        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("Comp1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").isNotEmpty());
        res.setCompanyName("newComp");
        this.mockMvc.perform(MockMvcRequestBuilders.put("/").content(TestUtils.convertObjectToJsonString(res))
                        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("newComp"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").isNotEmpty());
    }

    public void test_update_totp_config_not_found() throws Exception {
        TotpResource res =  getTotpResource("nofoundssp2@sss.com");
        res.setCompanyName("Comp1");

        this.mockMvc.perform(MockMvcRequestBuilders.put("/").content(TestUtils.convertObjectToJsonString(res))
                        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void test_get_by_id_ok() throws Exception {
        TotpResource res =  getTotpResource("ssp3@sss.com");
        res.setCompanyName("Comp3");
        this.mockMvc.perform(MockMvcRequestBuilders.post("/").content(TestUtils.convertObjectToJsonString(res))
                        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("Comp3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").isNotEmpty());

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/").param("emailId", "ssp3@sss.com")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("Comp3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").isNotEmpty());
    }

    @Test
    public void test_get_qr_email_not_found() throws Exception {
        String emailId = "notfound@sss.com";
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/qrcode").param("emailId", emailId)
                        .param("size", String.valueOf( 200))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_get_qr_ok() throws Exception {
        TotpResource res =  getTotpResource("ssp4@sss.com");
        res.setCompanyName("Comp4");
        this.mockMvc.perform(MockMvcRequestBuilders.post("/").content(TestUtils.convertObjectToJsonString(res))
                        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("Comp4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").isNotEmpty());
        String result = this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/qrcode").param("emailId", "ssp4@sss.com")
                        .param("size", String.valueOf( 200))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
                assertEquals(result, "iVBORw0KGgoAAAANSUhEUgAAAMgAAADIAQAAAACFI5MzAAABtElEQVR42u2YUWrEMAxEBb6WQFc36FoCdWbSbraF/lmwHwmBJn6FSPJ4pNb6v8se8pCPIdts7ZX4EV2++DpCEvfGS5ZF4tLKBMHncwducBC8jpHtznzdPUbJTyDN35oiuFXN8uDj71qfI5RE3tcf7ZwjvCpUVF/6eo8QKaOizLBt0KPfsR0lFdupkVBhLfg0QRDDKlu1mko099UjpPhYgYzlEy4hDhCkR5+QGil8FniCQOhY49extnmEY4Rw33h2YXtUh+KZIMjQterIlLqfIleGVD0tdvubV50kJa2nbBz5ypMmSKOGsCFKELGEdDJBZBGlQ0XZ94XOk6vnsazIUT0jRwgkWPzu4s6hvPsVwWGCeiKEYmtSYW8nP0ogEZ0t5Go8xfaK4DQJmV5S++iHtyOdJXlJvlhR8/cIzhJuGtv6yy26ZwiSYwNUf9IomSNkOxe0b6l55VbiUaIggorHGWbS0SOEIxbaOTWy12WBI0QTpCY6N5nFXjNEEz6kgTMMQ7K3yeE4oU1A8bSK71Y7Q2jn6n6cH7pnSGsJicJoU6PXCLn+ooQWOXbLydcIef4X8JBPJl8i6FW7nV3pbAAAAABJRU5ErkJggg==");
    }

    private static TotpResource getTotpResource(String email){
        TotpResource res = new TotpResource();
        res.setEmailId(email);
        return res;
    }
}
