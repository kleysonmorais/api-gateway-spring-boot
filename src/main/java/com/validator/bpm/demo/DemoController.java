package com.validator.bpm.demo;

import com.validator.bpm.demo.dto.DeficientDto;
import com.validator.bpm.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DemoController {

    @Autowired
    DemoService demoService;

    @GetMapping("/hello-world")
    public ResponseEntity<ArrayList<Demo>> demo(@RequestHeader HttpHeaders headers, HttpServletRequest request) {
        InetSocketAddress host = headers.getHost();
        String header = request.getHeader("Authorization");
        String authToken = "sem_token";
        if (header != null && header.startsWith("Bearer ")) authToken = header.substring(7);
        String url = "http://" + host.getHostName() + ":" + host.getPort() + "/api/hello-world";
        ArrayList<Demo> arrayList = new ArrayList<>();
        arrayList.add(new Demo(url, authToken));
        return ResponseEntity.ok(arrayList);
    }

    @GetMapping("/deficient-all-authorization")
    public ResponseEntity deficientAllAuthorization(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token;
        if (header != null && header.startsWith("Bearer ")) token = header.substring(7);
        else {
            System.out.println("Sem token");
            return ResponseEntity.notFound().build();
        }
        List<DeficientDto> deficientDtoList = demoService.getDeficientAllAuthorization(token);
        if(deficientDtoList != null) return ResponseEntity.ok(deficientDtoList);
        else return ResponseEntity.notFound().build();
    }

    @GetMapping("/deficients-all")
    public ResponseEntity<List<DeficientDto>> deficientAll(HttpServletRequest request) {
        return ResponseEntity.ok(demoService.getDeficientAll());
    }

    @PostMapping("/add-outher-job")
    public ResponseEntity addOutherJob(@RequestBody Map<String, Object> jobs, HttpServletRequest request) throws Exception{
        System.out.println(jobs);
        String header = request.getHeader("Authorization");
        String token;
        if (header != null && header.startsWith("Bearer ")) token = header.substring(7);
        else {
            System.out.println("Sem token");
            return ResponseEntity.notFound().build();
        }
        demoService.addOutherJob(jobs, token);
        return ResponseEntity.ok().build();
    }

}
