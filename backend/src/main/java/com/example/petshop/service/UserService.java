package com.example.petshop.service;

import com.example.petshop.entity.User;
import com.example.petshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 加载用户
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在: " + username));
    }

    /**
     * 根据ID查询用户
     */
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("用户不存在: " + id));
    }

    /**
     * 注册用户
     */
    @Transactional
    public User register(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }
        if (userRepository.existsByPhone(user.getPhone())) {
            throw new RuntimeException("手机号已被注册");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        user.setEnabled(true);
        log.info("注册用户: {}", user.getUsername());
        return userRepository.save(user);
    }

    /**
     * 更新用户
     */
    @Transactional
    public User update(User user) {
        User existing = findById(user.getId());
        if (!existing.getUsername().equals(user.getUsername()) && 
            userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }
        if (!existing.getPhone().equals(user.getPhone()) && 
            userRepository.existsByPhone(user.getPhone())) {
            throw new RuntimeException("手机号已被注册");
        }
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        existing.setUsername(user.getUsername());
        existing.setPhone(user.getPhone());
        existing.setEmail(user.getEmail());
        log.info("更新用户: {}", existing.getUsername());
        return userRepository.save(existing);
    }

    /**
     * 删除用户
     */
    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("用户不存在: " + id);
        }
        userRepository.deleteById(id);
        log.info("删除用户: {}", id);
    }
}