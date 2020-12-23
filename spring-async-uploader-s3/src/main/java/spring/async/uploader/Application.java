package spring.async.uploader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Application {
	public static void main(final String[] args) {
		final ApplicationContext context = SpringApplication.run(Application.class, args);
		System.out.println(context.getApplicationName());
	}
}
