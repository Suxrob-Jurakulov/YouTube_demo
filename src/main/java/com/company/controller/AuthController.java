package com.company.controller;

import com.company.dto.AuthDTO;
import com.company.dto.ProfileDTO;
import com.company.dto.RegistrationDTO;
import com.company.dto.ResponseInfoDTO;
import com.company.service.AuthService;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Api(tags = "Authorization and Registration")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody RegistrationDTO dto){
        String response = authService.registration(dto);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ProfileDTO> login(@RequestBody AuthDTO dto) {
        ProfileDTO profileDto = authService.login(dto);
        return ResponseEntity.ok(profileDto);
    }

    @ApiOperation(value = "Verification email", notes = "Method for verification email", nickname = "Some nick name")
    @GetMapping("/email/verification/{jwt}")
    public ResponseEntity<?> verification(@PathVariable("jwt") String jwt) {
        ResponseInfoDTO responseInfoDTO = authService.emailVerification(JwtUtil.decode(jwt));
        return ResponseEntity.ok(responseInfoDTO);
    }

    @ApiOperation(value = "Resend email", notes = "Method for resend email")
    @GetMapping("/resend/email/{jwt}")
    public ResponseEntity<?> resendEmail(@PathVariable("jwt") String jwt) {
        ResponseInfoDTO response = authService.resendEmail(JwtUtil.decode(jwt));
        log.info("Request for resend  email ");
        return ResponseEntity.ok(response);
    }
}

