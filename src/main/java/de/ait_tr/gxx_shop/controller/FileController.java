package de.ait_tr.gxx_shop.controller;

import de.ait_tr.gxx_shop.exception_handling.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileController {

    @PostMapping
    public Response upload(@RequestParam(value = "file") MultipartFile file,
                           @RequestParam(value = "productTitle")  String productTitle) {

        // Временно возвращаем null
        return null;
    }
}
