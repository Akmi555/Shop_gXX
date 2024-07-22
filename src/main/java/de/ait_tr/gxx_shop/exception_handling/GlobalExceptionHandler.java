package de.ait_tr.gxx_shop.exception_handling;
/*
@date 22.07.2024
@author Sergey Bugaienko
*/

import de.ait_tr.gxx_shop.exception_handling.exceptions.ThirdTestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ThirdTestException.class)
    public ResponseEntity<Response> handleThirdException(ThirdTestException e){
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

/*
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleValidationErrorsException(MethodArgumentNotValidException ex){

        // Создаем StringBuilder для накопления сообщений об ошибках
        StringBuilder errorMessage = new StringBuilder();

        // Перебираем все ошибки валидации
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            // Добавляем сообщение об ошибке для текущего поля
            errorMessage.append(error.getDefaultMessage()).append("; ");
        }

        // Создаем объект Response с накопленными сообщениями об ошибках
        Response response = new Response(errorMessage.toString());

        // Возвращаем ResponseEntity с объектом Response и статусом BAD_REQUEST
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


 */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationResponse> handleValidationErrorsException2(MethodArgumentNotValidException ex){
        System.out.println("ВАЛИДАЦИЯ Exception!!!");

        // Создаем StringBuilder для накопления сообщений об ошибках
        List<String> errors = new ArrayList<>();

        // Перебираем все ошибки валидации
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            // Добавляем сообщение об ошибке для текущего поля
            errors.add(error.getDefaultMessage());

        }

        // Создаем объект Response с накопленными сообщениями об ошибках
        ValidationResponse response = new ValidationResponse(errors);

        // Возвращаем ResponseEntity с объектом Response и статусом BAD_REQUEST
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
