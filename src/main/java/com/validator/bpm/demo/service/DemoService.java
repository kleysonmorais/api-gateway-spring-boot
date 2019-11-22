package com.validator.bpm.demo.service;

import com.validator.bpm.demo.dto.DeficientDto;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class DemoService {

    public List<DeficientDto> getDeficientAll(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://191.1.2.187:9898/providers/deficient-names";
        ResponseEntity<DeficientDto[]> response = restTemplate.getForEntity(url, DeficientDto[].class);
        DeficientDto[] deficientDtos = response.getBody();
        assert deficientDtos != null;
        return Arrays.asList(deficientDtos);
    }

    public List<DeficientDto> getDeficientAllAuthorization(String token){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://191.1.2.187:9898/api/v1/providers/deficient-names";
//        String url = "http://localhost:8443/api/v1/providers/deficient-names";
        try {
            HttpHeaders headers = getHeaders(token);
            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
            ResponseEntity<DeficientDto[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, DeficientDto[].class);
            System.out.println("Result - status ("+ response.getStatusCode() + ") has body: " + response.hasBody());
            DeficientDto[] deficientDtos = response.getBody();
            return Arrays.asList(deficientDtos);
        }
        catch (Exception eek) {
            System.out.println("** Exception: "+ eek.getMessage());
            return null;
        }
    }

    public void addOutherJob(Map<String, Object> jobs, String token) throws JSONException {
        String url = "http://191.1.2.187:9898/api/v1/providers/add-outher-job";
//        String url = "http://localhost:8443/api/v1/providers/add-outher-job";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = getHeaders(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject outherJobJsonObject = new JSONObject();
        outherJobJsonObject.put("usu_numemp", jobs.get("usu_numemp"));
        outherJobJsonObject.put("usu_tipcol", jobs.get("usu_tipcol"));
        outherJobJsonObject.put("usu_numcad", jobs.get("usu_numcad"));
        outherJobJsonObject.put("usu_numseq", jobs.get("usu_numseq"));
        outherJobJsonObject.put("usu_nmcnpj", jobs.get("usu_nmcnpj"));
        System.out.println(outherJobJsonObject.toString());
        HttpEntity<String> request = new HttpEntity<>(outherJobJsonObject.toString(), headers);
        ResponseEntity<String> response = restTemplate
                .exchange(url, HttpMethod.POST, request, String.class);
        System.out.println(response.getStatusCode());
    }

    HttpHeaders getHeaders(String token){
        return new HttpHeaders() {{set( "Authorization", "Bearer " + token );}};
    }

}
