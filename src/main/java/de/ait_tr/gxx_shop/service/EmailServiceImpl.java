package de.ait_tr.gxx_shop.service;
/*
@date 07.08.2024
@author Sergey Bugaienko
*/

import de.ait_tr.gxx_shop.domain.entity.User;
import de.ait_tr.gxx_shop.service.interfaces.ConfirmationCodeService;
import de.ait_tr.gxx_shop.service.interfaces.EmailService;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    // Используется для создания и отправки e-mail сообщений
    private final JavaMailSender mailSender;

    //Объект конфигурации FreeMarker, который настраивается для работы с шаблонами
    private final Configuration mailConfig;

    private final ConfirmationCodeService confirmationService;

    private final static String HOST = "http://localhost:8080/api";

    public EmailServiceImpl(JavaMailSender mailSender, Configuration mailConfig, ConfirmationCodeService confirmationService) {
        this.mailSender = mailSender;
        this.mailConfig = mailConfig;
        this.confirmationService = confirmationService;

        // Настройка кодировки и расположения шаблонов
        this.mailConfig.setDefaultEncoding("UTF-8");
        // Путь от корня папки ресурсов
        this.mailConfig.setTemplateLoader(new ClassTemplateLoader(this.getClass(), "/mail"));
    }


    @Override
    public void sendConfirmationEmail(User user) {

        try {
            // Создание MIME-сообщения
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // Генерация текста письма
            String mailText = generateEmailText(user);

            // Получаем адрес отправителя из переменной среды
            String fromAddress = System.getenv("MAIL_USERNAME");

            // Указываем отправителя
            helper.setFrom(fromAddress);

            // Указание получателя
            helper.setTo(user.getEmail());

            //Указание темы письма
            helper.setSubject("Registration Confirmation");

            // Добавление текста письма
            helper.setText(mailText, true);

            // Для отправки письма мы используем метод send объекта JavaMailSender
            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException();
        }
    }

    // Метод для генерации текста письма на основе шаблона
    private String generateEmailText(User user) {
        try {
            // Загрузка шаблона
            Template template = mailConfig.getTemplate("confirm_reg_mail.ftlh");

            // Генерация кода подтверждения для пользователя
            String code = confirmationService.generateConfirmCode(user);

            // http://localhost:8080/confirm?code=значение_кода
            String confirmationLink = HOST + "/register?code=" + code;

            // Подготовка данных для подстановки в шаблон
            Map<String, Object> model = new HashMap<>();
            model.put("name", user.getUsername());
            model.put("confirmationLink", confirmationLink);

           // Возвращаем сгенерированный текст письма
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);


        } catch (IOException | TemplateException e) {
            throw new RuntimeException();
        }
    }
}
