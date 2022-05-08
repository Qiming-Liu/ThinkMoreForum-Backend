package com.thinkmore.forum.service;

import java.util.Optional;
import java.util.UUID;

import com.thinkmore.forum.configuration.StaticConfig;
import com.thinkmore.forum.entity.JwtUser;
import com.thinkmore.forum.entity.Users;
import com.thinkmore.forum.entity.rabbitmq.ResetPasswordEmailMessage;
import com.thinkmore.forum.entity.rabbitmq.VerificationEmailMessage;
import com.thinkmore.forum.exception.UserNotFoundException;
import com.thinkmore.forum.repository.UsersRepository;
import com.thinkmore.forum.util.Util;

import io.prometheus.client.Gauge;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageService {

    @Value("${domain.name}")
    public String domainName;

    @Autowired
    RabbitTemplate rabbitTemplate;

    private static final Gauge emailQueue = Gauge.build()
                                                 .name("email")
                                                 .help("size of email queue.")
                                                 .register();

    private final UsersRepository usersRepository;
    private final JwtRouterService jwtRouterService;

    @Transactional
    public boolean sendVerificationEmail(UUID usersId, String newEmail) {
        VerificationEmailMessage message = new VerificationEmailMessage(usersId, newEmail);
        emailQueue.inc();
        rabbitTemplate.convertAndSend("VerificationEmail", message);
        return true;
    }

    @Transactional
    @RabbitListener(queues = "VerificationEmail")
    public void handleVerificationEmail(VerificationEmailMessage message) throws Exception {
        Users user = usersRepository.findById(message.getUsersId())
                                    .orElseThrow(() -> new UserNotFoundException("Invalid UserID"));
        emailQueue.dec();

        Util.createMail(
                StaticConfig.fromEmail,
                user.getEmail(),
                "Change Email Request",
                "Your account " + user.getUsername() + " is changing email to " + message.getNew_email());

        Util.createMail(
                StaticConfig.fromEmail,
                message.getNew_email(),
                "Verify Email",
                StaticConfig.VerifyEmailContext + domainName + StaticConfig.VerifyEmailUrl + message.getNew_email());
    }

    @Transactional
    public boolean sendResetPasswordEmail(String email) {
        ResetPasswordEmailMessage message = new ResetPasswordEmailMessage(email);
        rabbitTemplate.convertAndSend("ResetPasswordEmail", message);
        return true;
    }

    @Transactional
    @RabbitListener(queues = "ResetPasswordEmail")
    public void handleResetPasswordEmail(ResetPasswordEmailMessage message) throws Exception {
        Optional<Users> user = usersRepository.findByEmail(message.getEmail());
        emailQueue.dec();

        if (user.isPresent()) {
            String fakeJwt = StaticConfig.JwtPrefix + jwtRouterService.getFakeJwt(Util.generateJwt(new JwtUser(user.get())));
            String encode = Util.UrlEncoder(fakeJwt);
            Util.createMail(
                    StaticConfig.fromEmail,
                    message.getEmail(),
                    "Reset password",
                    StaticConfig.ResetPasswordContext +
                            domainName + StaticConfig.ResetPasswordUrl + encode);
        }
    }
}