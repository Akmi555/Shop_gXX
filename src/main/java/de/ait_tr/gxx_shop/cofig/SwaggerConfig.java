package de.ait_tr.gxx_shop.cofig;
/*
@date 14.07.2024
@author Sergey Bugaienko
*/

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Application Shop",
                description = "Application for various operations with Customers and Products",
                version = "1.0.0",
                contact = @Contact(
                        name = "Sergey B.",
                        email = "sergeyB@ait-tr.de",
                        url = "https://ait-tr.de"
                )
        )
)
public class SwaggerConfig { }
