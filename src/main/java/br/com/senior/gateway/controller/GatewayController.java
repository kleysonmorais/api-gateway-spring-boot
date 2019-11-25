package br.com.senior.gateway.controller;

import br.com.senior.gateway.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/gateway")
public class GatewayController {

  @Autowired
  GatewayService gatewayService;

  @RequestMapping(
      value = "/request-generic",
      method = {RequestMethod.GET, RequestMethod.POST})
  public ResponseEntity requestGeneric(
      @RequestParam(required = false) Map<String, String> params,
      @RequestBody(required = false) String body,
      HttpServletRequest request)
      throws Exception {
    String url = params.get("url");
    if (url != null) params.remove("url");
    else throw new Exception("O parâmetro 'url' é obrigatório e não está presente");
    return ResponseEntity.ok(
        gatewayService.execute(
            url.concat(gatewayService.buildParams(params)),
            gatewayService.getToken(request),
            request.getMethod(),
            body));
  }
}
