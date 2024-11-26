package sg.com.petpal.petpal.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import sg.com.petpal.petpal.model.Owner;
import sg.com.petpal.petpal.repository.OwnerRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final OwnerRepository ownerRepository;

    public MyUserDetailsService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Owner owner = ownerRepository.findByOwnerAuthEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        MyUserDetails userDetails = new MyUserDetails(owner);

        return userDetails;

    }

}
