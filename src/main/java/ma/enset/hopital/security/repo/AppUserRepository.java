package ma.enset.hopital.security.repo;

import ma.enset.hopital.security.entities.AppRole;
import ma.enset.hopital.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser,String> {

    AppUser findByUserName(String username);

    
}
