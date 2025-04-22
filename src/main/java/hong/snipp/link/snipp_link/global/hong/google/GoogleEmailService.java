package hong.snipp.link.snipp_link.global.hong.google;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * packageName    : hong.snipp.link.snipp_link.global.hong.google
 * fileName       : GoogleEmailService
 * author         : work
 * date           : 2025-04-22
 * description    : 구글 이메일 발송
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-22        work       최초 생성
 */
@Service
@RequiredArgsConstructor
public class GoogleEmailService {

    private final JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String content) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            javaMailSender.send(message);
        } catch(MessagingException e) {
            e.printStackTrace();
        }
    }
}
