package de.ait_tr.gxx_shop.service.interfaces;

import de.ait_tr.gxx_shop.domain.dto.CustomerDto;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerService {

    // Сохранить покупателя в базе данных (при сохранении покупатель автоматически считается активным)
    CustomerDto saveCustomer(CustomerDto customerDto);

    // Вернуть всех покупателей из базы данных (активных)
    List<CustomerDto> getAllActiveCustomers();

    // Вернуть одного покупателя из базы данных по его идентификатору (если он активен)
    CustomerDto getCustomerById(Long id);

    // Изменить одного покупателя в базе данных по его идентификатору
    CustomerDto updateCustomer(Long id, CustomerDto customerDto);

    // Удалить покупателя из базы данных по его идентификатору
    void deleteCustomerById(Long id);

    // Удалить покупателя из базы данных по его имени
    void deleteCustomerByName(String name);

    // Восстановить удалённого покупателя в базе данных по его идентификатору
    CustomerDto restoreCustomerById(Long id);

    // Вернуть общее количество покупателей в базе данных (активных)
    long getActiveCustomerCount();

    // Вернуть стоимость корзины покупателя по его идентификатору (если он активен)
    BigDecimal getCartTotalPrice(Long customerId);

    // Вернуть среднюю стоимость продукта в корзине покупателя по его идентификатору (если он активен)
    BigDecimal getCartAveragePrice(Long customerId);

    // Добавить товар в корзину покупателя по их идентификаторам (если оба активны)
    void addProductToCustomerCart(Long customerId, Long productId);

    // Удалить товар из корзины покупателя по их идентификаторам
    void removeProductFromCustomerCart(Long customerId, Long productId);

    // Полностью очистить корзину покупателя по его идентификатору (если он активен)
    void clearCustomerCart(Long customerId);
}