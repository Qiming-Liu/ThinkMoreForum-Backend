package com.thinkmore.forum.service;

import com.thinkmore.forum.mapper.ComponentMapper;
import com.thinkmore.forum.repository.ComponentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComponentService {
    private final ComponentRepository componentRepository;
    private final ComponentMapper componentMapper;
}