package com.company.service;

import com.company.config.CustomUserDetails;
import com.company.dto.profile.AuthDTO;
import com.company.dto.profile.ProfileDTO;
import com.company.dto.profile.RegistrationDTO;
import com.company.dto.profile.ResponseInfoDTO;
import com.company.entity.AttachEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import com.company.enums.PositionStatus;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.ProfileRepository;
import com.company.util.JwtUtil;
import com.company.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private EmailService emailService;

    public String registration(RegistrationDTO dto) {
        boolean existEmail = profileService.isExistEmail(dto.getEmail());
        if (existEmail) {
            throw new BadRequestException("This email busy");
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.getMd5(dto.getPassword()));
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(PositionStatus.BLOCK);

        if (dto.getPhotoId() == null) {
            entity.setPhotoId(null);
        } else {
            AttachEntity attach = new AttachEntity(dto.getPhotoId());
            entity.setPhotoId(attach.getId());
        }

        profileRepository.save(entity);

        emailService.sendRegistrationEmail(dto.getEmail(), entity.getId());
        return "Message was send";
    }


    public ProfileDTO login(AuthDTO authDTO) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authDTO.getEmail(), authDTO.getPassword()));
        CustomUserDetails user = (CustomUserDetails) authenticate.getPrincipal();
        ProfileEntity profile = user.getProfile();

        ProfileDTO dto = new ProfileDTO();
        dto.setName(profile.getName());
        dto.setJwt(JwtUtil.encode(profile.getId()));

        return dto;
    }


//    public String emailVerification(Integer id) {
//        Optional<ProfileEntity> optional = profileRepository.findById(id);
//        if (optional.isEmpty()) {
//            return "<h1>User Not Found</h1>";
//        }
//
//        ProfileEntity profile = optional.get();
//        profile.setStatus(Status.ACTIVE);
//        profileRepository.save(profile);
//        return "<h1 style='align-text:center'>Success. Tabriklaymiz.</h1>";
//    }
    public ResponseInfoDTO emailVerification(Integer id) {

        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (optional.isEmpty()) {
            return new ResponseInfoDTO(-1, "<h1>User Not Found</h1>");
        }

        ProfileEntity profile = optional.get();
        if (!emailService.verificationTime(profile.getEmail())) {
            return new ResponseInfoDTO(-1, "<h1>Time is out</h1>");
        }
        profile.setStatus(PositionStatus.ACTIVE);
        profileRepository.save(profile);
        return new ResponseInfoDTO(1, "<h1 style='align-text:center'>Success. Tabriklaymiz.</h1>");
    }

    public ResponseInfoDTO resendEmail(Integer id) {

        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("User not fount");
        }
        ProfileEntity profile = optional.get();

        Long count = emailService.countVerificationSending(profile.getEmail());
        if (count >= 4) {
            return new ResponseInfoDTO(-1, "limit");
        }

        emailService.sendRegistrationEmail(profile.getEmail(), profile.getId());
        return new ResponseInfoDTO(1, "success");
    }

}

