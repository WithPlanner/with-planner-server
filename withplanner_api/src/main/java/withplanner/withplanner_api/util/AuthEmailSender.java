package withplanner.withplanner_api.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AuthEmailSender {
    private final String FROM = "withplanner@test.com";
    private final String TITLE = "[위드플래너]안녕하세요 이메일 인증 메세지입니다.";
    private final String DOMAIN = "http://localhost:8080";

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendMail(String mailTo, String authToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailTo);
        message.setFrom(FROM);
        message.setSubject(TITLE);
        message.setText(makeText(mailTo,authToken));
        mailSender.send(message);
    }

    public String makeText(String email, String authToken) {
        return  "안녕하세요 위드플래너에 가입해 주셔서 감사합니다.\n" +
                "아래 링크를 클릭하시면 이메일 인증이 완료됩니다.\n " +
                DOMAIN+"/sign_up/check_valid_email?email="+email+"&authToken="+authToken+"\n\n" +
                "감사합니다.";
    }
}
