package org.oasumainline.SpringStarter;

import org.oasumainline.SpringStarter.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpotifyDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpotifyDemoApplication.class, args);
	}

    @Bean
    CommandLineRunner onInit(StorageService storage) {
        return (args) -> {
            System.out.println("Emptying storage");
            storage.deleteAll();
            System.out.println("Reinitializing storage");
            storage.init();
            System.out.println("Reinitializing successful");
        };
    }
}
