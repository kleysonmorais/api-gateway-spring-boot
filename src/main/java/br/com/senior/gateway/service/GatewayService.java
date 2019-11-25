package br.com.senior.gateway.service;

import br.com.senior.gateway.exception.RequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

@Service
public class GatewayService {

  @Autowired private RestTemplate restTemplate;

  public Object execute(String url, String token, String method, String body) {
    HttpHeaders headers = getHeaders(token);
    HttpEntity<String> entity = new HttpEntity<>(body, headers);
    ResponseEntity<Object> response =
        restTemplate.exchange(url, HttpMethod.resolve(method), entity, Object.class);
    return response.getBody();
  }

  public String getToken(HttpServletRequest request) {
    Optional<String> header = Optional.ofNullable(request.getHeader("Authorization"));

    if (header.isPresent() && header.get().startsWith("Bearer ")) return header.get().substring(7);
    else throw new RequestException("É obrigatório informar o token para autenticação");
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
