package com.company.service;

import com.company.config.CustomUserDetails;
import com.company.dto.AuthDTO;
import com.company.dto.ProfileDTO;
import com.company.dto.RegistrationDTO;
import com.company.entity.AttachEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import com.company.enums.Status;
import com.company.exp.BadRequestException;
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
        entity.setStatus(Status.BLOCK);

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

       /* Optional<ProfileEntity> optional = profileRepository.findByEmail(authDTO.getEmail());
        if (optional.isEmpty()) {
            throw new BadRequestException("User not found");
        }
        ProfileEntity profile = optional.get();
        if (!MD5Util.getMd5(authDTO.getPassword()).equals(profile.getPassword())) {
            throw new BadRequestException("User not found");
        }

        if (!profile.getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new BadRequestException("No ruxsat");
        }*/

        ProfileDTO dto = new ProfileDTO();
        dto.setName(profile.getName());
        dto.setJwt(JwtUtil.encode(profile.getId()));

        return dto;
    }
    public String emailVerification(Integer id) {
        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (optional.isEmpty()) {
            return "<h1>User Not Found</h1>";
        }

        ProfileEntity profile = optional.get();
        profile.setStatus(Status.ACTIVE);
        profileRepository.save(profile);
        return "<h1 style='align-text:center'>Success. Tabriklaymiz.</h1>";
    }
}

