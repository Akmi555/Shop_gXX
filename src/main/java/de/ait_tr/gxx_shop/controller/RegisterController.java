package de.ait_tr.gxx_shop.controller;

import de.ait_tr.gxx_shop.domain.entity.ConfirmationCode;
import de.ait_tr.gxx_shop.domain.entity.User;
import de.ait_tr.gxx_shop.exception_handling.Response;
import de.ait_tr.gxx_shop.service.interfaces.ConfirmationCodeService;
import de.ait_tr.gxx_shop.service.interfaces.UserService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;
    private final ConfirmationCodeService confirmationCodeService;

    public RegisterController(UserService userService, ConfirmationCodeService confirmationCodeService) {
        this.userService = userService;
        this.confirmationCodeService = confirmationCodeService;
    }

    @PostMapping
    public Response register (@RequestBody User user) {
        userService.register(user);
        return new Response("Registration Complete. Please check your e-mail.");
    }

    @GetMapping
    public Response confirmEmail(@RequestParam String code){
        ConfirmationCode confirmation = confirmationCodeService.findByCode(code);
        if (confirmation.getExpired().isAfter(LocalDateTime.now())) {

            User user = confirmation.getUser();
            user.setActive(true);
            userService.update(user);
            return new Response(user.getEmail() + " confirmed email.");
        }

        throw new RuntimeException("Wrong e-mail address.");
    }



}
