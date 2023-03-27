package com.solver.solver_be.global.security.webSecurity;

import com.solver.solver_be.domain.user.entity.Admin;
import com.solver.solver_be.domain.user.entity.Guest;
import com.solver.solver_be.domain.user.entity.UserRoleEnum;
import com.solver.solver_be.domain.user.repository.AdminRepository;
import com.solver.solver_be.domain.user.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final GuestRepository guestRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) {
        Optional<Admin> adminOptional = adminRepository.findByUserId(userId);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            return new UserDetailsImpl(admin, admin.getUserId());
        }

        Optional<Guest> guestOptional = guestRepository.findByUserId(userId);
        if (guestOptional.isPresent()) {
            Guest guest = guestOptional.get();
            return new UserDetailsImpl(guest, guest.getUserId());
        }

        throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
    }
}

