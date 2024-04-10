package edu.primesservice.controller;

import edu.primesservice.rabbitmq.MQSender;
import edu.primesservice.service.IPrimesService;
import edu.primesservice.rabbitmq.MQSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/primes")
public class PrimesController {
    IPrimesService primesService;

    private final MQSender mqSender;

    public PrimesController(IPrimesService primesService, MQSender mqSender) {

        this.primesService = primesService;
        this.mqSender = mqSender;
    }

    @GetMapping("/{n}")
    public boolean isPrime(@PathVariable int n){
        boolean result = primesService.isPrime(n);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((Jwt) principal).getSubject();
        System.out.println(username);
        mqSender.sendMessage(username, n, result);
        return result;
    }

}
