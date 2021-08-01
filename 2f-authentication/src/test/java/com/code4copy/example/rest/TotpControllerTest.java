package com.code4copy.example.rest;

import com.code4copy.example.rest.resource.TotpResource;
import com.code4copy.example.utils.TestUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
       String emailId = "ssp@sss.com";
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

    private static TotpResource getTotpResource(String email){
        TotpResource res = new TotpResource();
        res.setEmailId(email);
        return res;
    }
}
