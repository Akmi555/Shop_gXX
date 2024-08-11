package de.ait_tr.gxx_shop.cofig;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public AmazonS3 doClient(DoProperties doProperties) {

        // Создание объекта, который содержит ключи доступа
        AWSCredentials awsCredentials =
                new BasicAWSCredentials(doProperties.getAccessKey(), doProperties.getSecretKey());

        // Создание объекта с информацией о подключении
        AwsClientBuilder.EndpointConfiguration endpointConfig = new AwsClientBuilder.EndpointConfiguration(
                doProperties.getUrl(),
                doProperties.getRegion()
        );

        // Сборка клиента для подключения к DigitalOcean
        AmazonS3ClientBuilder clientBuilder = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withEndpointConfiguration(endpointConfig);

        return clientBuilder.build();

    }
}
