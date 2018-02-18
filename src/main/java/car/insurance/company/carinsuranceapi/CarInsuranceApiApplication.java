package car.insurance.company.carinsuranceapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class CarInsuranceApiApplication {

	@Value("${base.api.config.corePoolSize:4}")
	private Integer corePoolSize;

	@Value("${base.api.config.maxPoolSize:5}")
	private Integer maxPoolSize;

	@Value("${base.api.config.queueCapacity:500}")
	private Integer queueCapacity;

	@Value("${base.api.config.threadNamePrefix:CarInsuranceApi-}")
	private String threadNamePrefix;

	public static void main(String[] args) {
		SpringApplication.run(CarInsuranceApiApplication.class, args);
	}

	@Bean
	public Executor asyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setThreadNamePrefix(threadNamePrefix);
		executor.initialize();
		return executor;
	}
}
