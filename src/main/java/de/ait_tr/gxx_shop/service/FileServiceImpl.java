package de.ait_tr.gxx_shop.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import de.ait_tr.gxx_shop.service.interfaces.FileService;
import de.ait_tr.gxx_shop.service.interfaces.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

    private final AmazonS3 client;
    private final ProductService productService;

    public FileServiceImpl(AmazonS3 client, ProductService productService) {
        this.client = client;
        this.productService = productService;
    }


    @Override
    public String upload(MultipartFile file, String productTitle) {

        try {
            ObjectMetadata metadata = new ObjectMetadata();

            metadata.setContentType(file.getContentType());

            // Объект загрузки файлв
            PutObjectRequest request = new PutObjectRequest(
                    "cohort-33-bucket",
                    file.getOriginalFilename(),
                    file.getInputStream(),
                    metadata
            );

            // делаем файл общедоступным
            request.withCannedAcl(CannedAccessControlList.PublicRead);

            // Физически отправляем файл в облако
            client.putObject(request);

            // Получаем ссылку на загруженный файл
            String url = client.getUrl("cohort-33-bucket", file.getOriginalFilename()).toString();

            //Todo привязать ссылку к продукту

            return url;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
