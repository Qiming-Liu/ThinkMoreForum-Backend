package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.test.TestGetDto;
import com.thinkmore.forum.mapper.TestMapper;
import com.thinkmore.forum.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TestRepository testRepository;
    private final TestMapper testMapper;

    public List<TestGetDto> getUsersById(UUID Id) {
        return testRepository.findAllByUsersId(Id).stream().map(testMapper::fromEntity).collect(Collectors.toList());
    }
}
