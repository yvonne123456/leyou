package com.leyou.auth.web;

import com.leyou.auth.dto.AliOssSignatureDTO;
import com.leyou.auth.service.AliAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/ali")
public class AliAuthController {

    @Autowired
    private AliAuthService aliAuthService;

    @GetMapping("/oss/signature")
    public ResponseEntity<AliOssSignatureDTO> getSignature(){
        return ResponseEntity.ok(this.aliAuthService.getSignature());
    }
}
