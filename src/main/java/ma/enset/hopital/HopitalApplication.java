package ma.enset.hopital;

import ma.enset.hopital.entities.Patient;
import ma.enset.hopital.repositories.PatientRepository;
import ma.enset.hopital.security.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.Date;

@SpringBootApplication
public class HopitalApplication   {
    @Autowired
   private PatientRepository patientRepository;
    public static void main(String[] args) {

        SpringApplication.run(HopitalApplication.class, args);
    }

CommandLineRunner start(){
        return args -> {

   // patientRepository.save(new Patient(null,"Hassan",new Date(),false,34));
   //patientRepository.save(new Patient(null,"Khadija",new Date(),false,33));
   // patientRepository.save(new Patient(null,"Hind",new Date(),false,32));
    //patientRepository.save(new Patient(null,"Wissal",new Date(),false,31));

       /* Patient patient=new Patient();
        patient.setId(null);
        patient.setNom("Hind");
        patient.setDateNaissance(new Date());
        patient.setMalade(false);
        patient.setScore(40);

        //En utilisant builder
        Patient patient1=Patient.builder()
                .nom("Wissal")
                .dateNaissance(new Date())
                .score(40)
                .malade(false)
                .build();

        Patient patient2=new Patient(null,"malak",new Date(),false,20);
*/
        };}
   //JDBC Authentication
   // @Bean
    CommandLineRunner start(JdbcUserDetailsManager jdbcUserDetailsManager){
        return args -> {
            UserDetails u1=jdbcUserDetailsManager.loadUserByUsername("user11");
            if (u1==null)
            jdbcUserDetailsManager.createUser(User.withUsername("user11").password(passwordEncoder().encode("1234")).roles("USER").build());
            UserDetails u2=jdbcUserDetailsManager.loadUserByUsername("user22");
            if (u2==null)
            jdbcUserDetailsManager.createUser(User.withUsername("user22").password(passwordEncoder().encode("1234")).roles("USER").build());
            UserDetails u3=jdbcUserDetailsManager.loadUserByUsername("admin1");
            if (u3==null)
            jdbcUserDetailsManager.createUser(User.withUsername("admin1").password(passwordEncoder().encode("1234")).roles("USER","ADMIN").build());

        };
    }
   // @Bean
    CommandLineRunner commandLineRunnerUserDetails(AccountService accountService){
        return args -> {
            accountService.addNewRole("USER");
            accountService.addNewRole("ADMIN");

            accountService.addNewUser("Hind","hgwk","gadanyhind@gmail.com","hgwk");
            accountService.addNewUser("wissal","hgwk","gadanywissal@gmail.com","hgwk");
            accountService.addNewUser("Khadija karimi ","hgwk","karimikhadija@gmail.com","hgwk");

            accountService.addRoleToUser("khadija karimi","ADMIN");
            accountService.addRoleToUser("khadija karimi","USER");
            accountService.addRoleToUser("Hind","USER");
            accountService.addRoleToUser("wissal","USER");




        };
    }

@Bean
PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
}}