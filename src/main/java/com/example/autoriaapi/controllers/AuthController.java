package com.example.autoriaapi.controllers;

import com.example.autoriaapi.configs.jwt.JwtUtils;
import com.example.autoriaapi.models.ERole;
import com.example.autoriaapi.models.Role;
import com.example.autoriaapi.models.User;
import com.example.autoriaapi.requests.*;
import com.example.autoriaapi.repositories.RoleRepository;
import com.example.autoriaapi.repositories.UserRepository;
import com.example.autoriaapi.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired

    UserRepository userRepository;
    @Autowired

    RoleRepository roleRepository;
    @Autowired

    PasswordEncoder passwordEncoder;
    @Autowired

    JwtUtils jwtUtils;


    @PostMapping("/signin")
    public ResponseEntity<?> authUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/signup/moder")
    public ResponseEntity<?> registerModer(@RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is exist"));
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error : Email is exist"));
        }
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()));
        Set<Role> roles = new HashSet<>();

        Role modrole = roleRepository
                .findByName(ERole.ROLE_MODER)
                .orElseThrow(() -> new RuntimeException("Error, Role MODER is not found"));
        roles.add(modrole);

        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User CREATED"));
    }

    @PostConstruct
    public void adminReg() {
        if (userRepository.existsByUsername("Super Admin")) {

        } else {
            String username = "Super Admin";
            String email = "superadmin@gmail.com";
            String password = "superadmin";
            Set<Role> roles = new HashSet<>();
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow();
            roles.add(adminRole);
            User user = new User(username, email, passwordEncoder.encode(password));
            user.setRoles(roles);
            userRepository.save(user);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Validated @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is exist"));
        } else if (signUpRequest.getUsername().isBlank()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is empty"));
        } else if (signUpRequest.getUsername().length() < 3 || signUpRequest.getUsername().length() > 15) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Invalid username"));

        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error : Email is exist"));
        } else if (signUpRequest.getEmail().isBlank()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error : Email is empty"));
        }


        if (signUpRequest.getPassword().isBlank()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error : Password is empty"));
        } else if (signUpRequest.getPassword().length() < 8 || signUpRequest.getPassword().length() > 16) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Invalid password"));
        }
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()));

        Set<String> reqRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (reqRoles == null) {
            Role userRole = roleRepository
                    .findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
            roles.add(userRole);
        } else {
            reqRoles.forEach(r -> {
                switch (r) {
                    case "seller":
                        Role sellerRole = roleRepository
                                .findByName(ERole.ROLE_SELLER)
                                .orElseThrow(() -> new RuntimeException("Error, Role MODERATOR is not found"));
                        roles.add(sellerRole);
                        break;
                    case "user":
                        Role userRole = roleRepository
                                .findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
                        roles.add(userRole);
                        break;
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User CREATED"));
    }


    @PreAuthorize("hasRole('SELLER')")
    @GetMapping("/up")
    public void upgradeRole(Authentication authentication, @RequestBody UpgradeRequest upgradeRequest) {
        String currentUserName = authentication.getName();
        User user = userRepository.findByUsername(currentUserName)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Set<Role> roles = new HashSet<>();
        Role upRole = roleRepository
                .findByName(ERole.ROLE_UPPER_SELLER)
                .orElseThrow(() -> new RuntimeException("Error, Role MODERATOR is not found"));
        user.setRoles(roles);
        roles.add(upRole);
        userRepository.save(user);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MODER')")
    @GetMapping("/users")
    public ResponseEntity<List<Map<String, Object>>> getAllUsers() {
        List<User> userList = userRepository.findAll();
        List<Map<String, Object>> userInfoList = new ArrayList<>();
        for (User user : userList) {
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("username", user.getUsername());
            userInfo.put("id", user.getId());
            userInfo.put("email", user.getEmail());
            userInfo.put("password", user.getPassword());
            userInfo.put("role", user.getRoles());
            userInfoList.add(userInfo);
        }
        return new ResponseEntity<>(userInfoList, HttpStatus.OK);


    }
}