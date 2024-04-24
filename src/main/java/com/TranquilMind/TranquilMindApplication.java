package com.TranquilMind;

import com.TranquilMind.config.SpringSecurityConfig;
import com.TranquilMind.model.*;
import com.TranquilMind.repository.DoctorRepository;
import com.TranquilMind.repository.PatientRepository;
import com.TranquilMind.repository.RoleRepository;
import com.TranquilMind.repository.UserRepository;
import com.TranquilMind.service.PatientService;
import com.TranquilMind.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootApplication
public class TranquilMindApplication {

    public static void main(String[] args) {
        SpringApplication.run(TranquilMindApplication.class, args);
    }

    PasswordEncoder passEncoder = new SpringSecurityConfig().passwordEncoder();

    @Bean
    CommandLineRunner run(UserService iUserService, RoleRepository iRoleRepository, UserRepository iUserRepository,
                          PasswordEncoder passwordEncoder, PatientService patientService, DoctorRepository doctorRepository) {
        return args ->
        {

//            iUserService.saveRole(new Role(RoleName.PATIENT));
//            iUserService.saveRole(new Role(RoleName.DOCTOR));
//            iUserService.saveRole(new Role(RoleName.ADMIN));
//            iUserService.saveRole(new Role(RoleName.MODERATOR));
//            iUserService.saveRole(new Role(RoleName.RESPONDER));
////            iUserService.saveRole(new Role(RoleName.SUPERADMIN));
//
//
////            iUserService.saveUser(new User("patient@gmail.com", passwordEncoder.encode("patientPassword"), new ArrayList<>()));
////            iUserService.saveUser(new User("admin@gmail.com", passwordEncoder.encode("adminPassword"), new ArrayList<>()));
//////            iUserService.saveUser(new User("superadminadmin@gmail.com", passwordEncoder.encode("superadminPassword"), new ArrayList<>()));
////
////            Role role = iRoleRepository.findByRoleName(RoleName.PATIENT);
////            User user = iUserRepository.findByEmail("patient@gmail.com").orElse(null);
////            user.getRoles().add(role);
////            iUserService.saveUser(user);
////
////            Role role1 = iRoleRepository.findByRoleName(RoleName.ADMIN);
////            User user1 = iUserRepository.findByEmail("admin@gmail.com").orElse(null);
////            user1.getRoles().add(role1);
////            iUserService.saveUser(user1);
////
////            patientService.createPatient(new Patient(1L,user, "Smit","H","Mehta",12, Gender.MALE));
//
////            User userr = iUserRepository.findByEmail("superadminadmin@gmail.com").orElse(null);
////            Role rolee = iRoleRepository.findByRoleName(RoleName.SUPERADMIN);
////            userr.getRoles().add(rolee);
////            iUserService.saverUser(userr);
//
//            User mod1 = new User();
//            mod1.setEmail("mod1@gmail.com");
//            mod1.setPassword(passEncoder.encode("mod1"));
//            iUserRepository.save(mod1);
//
//            Role role1 = iRoleRepository.findByRoleName(RoleName.MODERATOR);
//            User user1 = iUserRepository.findByEmail("mod1@gmail.com").orElse(null);
//            user1.getRoles().add(role1);
//
//            iUserRepository.save(user1);
////
//            User resp2 = new User();
//            resp2.setEmail("resp2@gmail.com");
//            resp2.setPassword(passEncoder.encode("resp2"));
//            iUserRepository.save(resp2);
//
//            Role role2 = iRoleRepository.findByRoleName(RoleName.RESPONDER);
//            User user2 = iUserRepository.findByEmail("resp2@gmail.com").orElse(null);
//            user2.getRoles().add(role2);
//
//            iUserRepository.save(user2);
//
//
//            User admin = new User();
//            admin.setEmail("admin@gmail.com");
//            admin.setPassword(passEncoder.encode("admin"));
//            iUserRepository.save(admin);
//
//            Role role3 = iRoleRepository.findByRoleName(RoleName.ADMIN);
//            User user3 = iUserRepository.findByEmail("admin@gmail.com").orElse(null);
//            user3.getRoles().add(role3);
//
//            iUserRepository.save(user3);

        };
    }


}
