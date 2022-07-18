package com.company.service;

import com.company.entity.EmailHistoryEntity;
import com.company.repository.EmailHistoryRepository;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EmailService {
    @Autowired
    private EmailHistoryRepository emailHistoryRepository;
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromAccount;

    @Value("${server.url}")
    private String serverUrl;


    public void sendRegistrationEmail(String toAccount, Integer id) {

        String url = String.format("<a href='%sauth/email/verification/%s'>Verification Link</a>", serverUrl, JwtUtil.encode(id));

        StringBuilder builder = new StringBuilder();
        builder.append("<h1 style='align-text:center'>Salom Jigar alo </h1>");
        builder.append("<b>Mazgi </b>");
        builder.append(JwtUtil.encode(id));
        builder.append("<p>");
        builder.append(url);
        builder.append("</p>");
        System.out.println(JwtUtil.encode(id));
        sendEmail(toAccount, "Registration", builder.toString());
    }

    private void sendEmail(String toAccount, String subject, String text) {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            msg.setFrom(fromAccount);
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(toAccount);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(msg);

            EmailHistoryEntity entity  =new EmailHistoryEntity();
            entity.setEmail(toAccount);
            emailHistoryRepository.save(entity);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendSimpleEmail(String toAccount, String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(toAccount);
        msg.setSubject(subject);
        msg.setText(text);
        msg.setFrom(fromAccount);
        javaMailSender.send(msg);
    }

    public Long getEmailCount(String email) {
        return emailHistoryRepository.countAllByEmail(email);
    }

    public PageImpl<EmailHistoryEntity> pagination(Integer page, Integer size) {

        Sort sort= Sort.by(Sort.Direction.ASC, "id");

        Pageable pageable = PageRequest.of(page,size,sort);
        Page<EmailHistoryEntity> all = emailHistoryRepository.findAll(pageable);
        List<EmailHistoryEntity> list = all.getContent();

        return  new PageImpl(list, pageable, all.getTotalElements());
    }

    public boolean verificationTime(String email) {

        Optional<EmailHistoryEntity> optional = emailHistoryRepository
                .findTopByEmailOrderByCreatedDateDesc(email);

        if (optional.isEmpty()) {
            return false;
        }

        EmailHistoryEntity history = optional.get();

        return history.getCreatedDate().plusMinutes(1).isAfter(LocalDateTime.now());
    }


    public Long countVerificationSending(String email) {
        return emailHistoryRepository.countResend(email);
    }

}
