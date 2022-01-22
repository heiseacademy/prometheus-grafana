package dev.karsten.monitoringdemo;

import io.github.mweirauch.micrometer.jvm.extras.ProcessMemoryMetrics;
import io.github.mweirauch.micrometer.jvm.extras.ProcessThreadMetrics;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class MonitoringDemoApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(MonitoringDemoApplication.class, args);
    }

    // Fuer Demozwecke kompakt gehalten - bitte regulär immer separate Klassen bauen

    @RestController
    @RequestMapping("/api/demo")
    static class DemoController
    {
        @GetMapping
        public Map<String, Object> demo() throws InterruptedException
        {
            var start = LocalDateTime.now();
            TimeUnit.MILLISECONDS.sleep(780);
            var end = LocalDateTime.now();
            return Map.of(
               "start", start,
               "end", end,
               "random", UUID.randomUUID()
            );
        }

        @Autowired RestTemplate restTemplate;

        @GetMapping("/random/{max}")
        public Map<String, Object> random(@PathVariable Integer max)
        {
           var data = restTemplate
              .getForObject("http://www.randomnumberapi.com/api/v1.0/random?min=10&max={max}&count=1",
                 Integer[].class,
                 max);
           return Map.of(
              "max", max,
              "random", data[0]);
        }

        @Autowired CustomerRepository customerRepository;
        @GetMapping("/customer")
        public List<Customer> customers()
        {
            customerReadCounter.increment();
            return customerRepository.findAll();
        }

        @PostMapping("/customer")
        public Customer save(@RequestBody Customer customer)
        {
            customerWriteCounter.increment();
            return customerRepository.save(customer);
        }

        Counter customerReadCounter;
        Counter customerWriteCounter;
        DemoController(MeterRegistry meterRegistry)
        {
            customerReadCounter = meterRegistry
               .counter("customers.read.total", "type", "customer");
            customerWriteCounter = Counter.builder("customers.saved.total")
               .description("Total numbers of customers created")
               .tags("type", "customer")
               .register(meterRegistry);
        }
    }

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder)
    {
        return restTemplateBuilder.build();
    }

    // persistence
    @Entity
    public static class Customer
    {
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Integer id;
        String name;

        public void setId(Integer id)
        { this.id = id; }

        public Integer getId()
        { return id; }

        public void setName(String name)
        { this.name = name; }

        public String getName()
        { return name; }
    }

    @Repository
    public interface CustomerRepository extends JpaRepository<Customer, Integer> {}

    @Configuration
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class EnableNestedRepoConfig { }

    //create schema - do not use in production!
    static { System.setProperty("spring.jpa.generate-ddl", "true"); }

    // micrometer-jvm-extras
    @Bean
    public MeterBinder processMemoryMetrics() { return new ProcessMemoryMetrics(); }

    @Bean
    public MeterBinder processThreadMetrics() { return new ProcessThreadMetrics(); }
}
