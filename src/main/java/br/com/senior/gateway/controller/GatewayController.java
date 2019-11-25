package br.com.senior.gateway.controller;

import br.com.senior.gateway.exception.RequestException;
import br.com.senior.gateway.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/gateway")
public class GatewayController {

  @Autowired GatewayService gatewayService;

  @RequestMapping(
      value = "/request-generic",
      method = {
        RequestMethod.GET,
        RequestMethod.POST,
        RequestMethod.DELETE,
        RequestMethod.PUT,
        RequestMethod.PATCH
      })
  public ResponseEntity requestGeneric(
      @RequestParam(required = false) Map<String, String> params,
      @RequestBody(required = false) String body,
      HttpServletRequest request) {

    Optional<String> url = Optional.ofNullable(params.get("url"));
    url.orElseThrow(
        () -> new RequestException("O parâmetro 'url' é obrigatório e não está presente"));

    return ResponseEntity.ok(
        gatewayService.execute(
            url.get().concat(gatewayService.buildParams(params)),
            gatewayService.getToken(request),
            request.getMethod(),
            body));
  }
}
