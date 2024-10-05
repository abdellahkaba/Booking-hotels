package com.isi.booking.user;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    public List<ResponseUser> getAllUsers() {
        return repository.findAll()
                .stream()
                .map(mapper::fromUser)
                .collect(Collectors.toList());
    }
}
