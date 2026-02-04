package com.epam;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author jdmon on 20/09/2025
 * @project springbasegymcrm
 */
@SpringBootApplication
//@EnableDiscoveryClient
//@EnableFeignClients
public class GymCrmApplication {
    public static void main(String[] args) {
        SpringApplication.run(GymCrmApplication.class, args);
    }
}
