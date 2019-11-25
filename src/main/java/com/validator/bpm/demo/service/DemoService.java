package com.validator.bpm.demo.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class DemoService {

  public Object execute(String url, String token, String method, String body) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = getHeaders(token);
    HttpEntity<String> entity = new HttpEntity<>(body, headers);
    ResponseEntity<Object> response =
        restTemplate.exchange(url, HttpMethod.resolve(method), entity, Object.class);
    return response.getBody();
  }

  public String getToken(HttpServletRequest request) throws Exception {
    String header = request.getHeader("Authorization");
    if (header != null && header.startsWith("Bearer ")) return header.substring(7);
    else throw new Exception("É obrigatório informar o token para autenticação");
  }

  public String buildParams(Map<String, String> params) {
    StringBuilder paramsUrl = new StringBuilder();
    for (Map.Entry<String, String> param : params.entrySet()) {
      if (paramsUrl.length() > 0) paramsUrl.append("&");
      paramsUrl.append(param.getKey() + "=" + param.getValue());
    }
    return paramsUrl.length() > 0 ? "?" + paramsUrl.toString() : "";
  }

  HttpHeaders getHeaders(String token) {
    return new HttpHeaders() {
      {
        set("Authorization", "Bearer " + token);
        setContentType(MediaType.APPLICATION_JSON);
      }
    };
  }
}
