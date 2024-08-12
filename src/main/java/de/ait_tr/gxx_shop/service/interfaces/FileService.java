package de.ait_tr.gxx_shop.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String upload(MultipartFile file, String productTitle);
}
