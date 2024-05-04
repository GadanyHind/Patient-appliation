package ma.enset.hopital.security.service;

import lombok.AllArgsConstructor;
import ma.enset.hopital.security.entities.AppRole;
import ma.enset.hopital.security.entities.AppUser;
import ma.enset.hopital.security.repo.AppRoleRepository;
import ma.enset.hopital.security.repo.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private AppRoleRepository appRoleRepository;
    private AppUserRepository appUserRepository;
    private PasswordEncoder passwordEncoder;
    @Override
    public AppUser addNewUser(String username, String password, String email, String confirmPassword) {
        AppUser appUser=appUserRepository.findByUserName(username);
        if (appUser!=null) throw new RuntimeException("This user already exist ");
        if (!password.equals(confirmPassword)) throw  new RuntimeException("Passwords not match");
        AppUser user=AppUser.builder().userId(UUID.randomUUID().toString())
                .userName(username).password(passwordEncoder.encode(password))
                .email(email)
                .build();
        return appUserRepository.save(user);
    }

    @Override
    public AppRole addNewRole(String role) {
        AppRole appRole=appRoleRepository.findById(role).orElse(null);
        if (appRole!=null) throw new RuntimeException("This role already exist");
        appRole=AppRole.builder().role(role).build();
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String role) {
       AppUser appUser=appUserRepository.findByUserName(username);
       AppRole appRole=appRoleRepository.findById(role).get();
       appUser.getRoles().add(appRole);
    }

    @Override
    public void removeRoleToUser(String username, String role) {
        AppUser appUser=appUserRepository.findByUserName(username);
        AppRole appRole=appRoleRepository.findById(role).get();
        appUser.getRoles().remove(appRole);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUserName(username);
    }
}
