package de.ait_tr.gxx_shop.logging;

import de.ait_tr.gxx_shop.domain.dto.ProductDto;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergey Bugaenko
 * {@code @date} 25.08.2024
 */

@Aspect
@Component
public class AspectLogging {

    private final Logger logger = LoggerFactory.getLogger(AspectLogging.class);

    @Pointcut("execution(* de.ait_tr.gxx_shop.service.ProductServiceImpl.save(..))")
    public void saveProduct(){
        // Метод без тела, используется только для задания PointCut
    }

    @Before("saveProduct()")
    public void beforeSavingProduct(JoinPoint joinPoint) {
        // Извлекаем параметры метода
        Object[] params = joinPoint.getArgs();
        // Логируем информацию о вызове метода и параметре
        logger.info("Method `save` of the class ProductServiceImpl was called with param {}", params[0]);
    }

    @After("saveProduct()")
    public void afterSavingProduct() {
        logger.info("Method save of the class ProductServiceImpl finished its work.");
    }

//    @Before("saveProduct()")
//    public void beforeSavingProduct() {
//        logger.info("Method `save` in class ProductServiceImpl was called");
//    }

    @Pointcut("execution(* de.ait_tr.gxx_shop.service.ProductServiceImpl.getById(..))")
    public void getProductById() {

    }


    @AfterReturning( pointcut = "getProductById()", returning = "result")
    public void afterReturningFromGetProductById(Object result) {
        logger.info("Method `getById` successfully returned result: {}", result);
    }

    @AfterThrowing(pointcut = "execution(* de.ait_tr.gxx_shop.service.ProductServiceImpl.getById(..))", throwing = "ex")
    public void afterThrowingExceptionFromGetProductById(JoinPoint joinPoint, Exception ex) {
        Object[] params = joinPoint.getArgs();
        logger.error("Method `getById` with param {} threw an exception: {} ", params[0], ex.getMessage());
   }

   @Pointcut("execution(* de.ait_tr.gxx_shop.service.ProductServiceImpl.getAllActiveProducts(..))")
   public void getAllActiveProducts() {
        //Pointcut
   }

   @Around("getAllActiveProducts()")
   public Object aroundGetAllActiveProducts(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;

        try {
            // Логируем до выполнения основного метода
            logger.info("Method `getAllActiveProducts` of the class ProductServiceImpl called.");

            // // Выполняем основной метод и получаем результат
            result = joinPoint.proceed();

            // Логируем после успешного выполнения основного метода
            logger.info("Method `getAllActiveProducts` successfully returned result: {}", result);

            // Изменяем результат, добавляя дополнительную логику
            result = ((List<ProductDto>) result).stream()
                    .filter(product -> product.getPrice().doubleValue() > 1)
                    .toList();

        } catch (Throwable ex) {
            // Логируем, если произошло исключение
            logger.error("Method `getAllActiveProducts` threw an exception: {}", ex.getMessage());
        }

       // Возвращаем (модифицированный) результат
        return result == null ? new ArrayList<>() : result;

   }


}
