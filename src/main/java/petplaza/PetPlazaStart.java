package petplaza;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // JAP Auditing 실행
@SpringBootApplication
public class PetPlazaStart {    //
    public static void main(String[] args) {
        SpringApplication.run(PetPlazaStart.class );
    }
}
