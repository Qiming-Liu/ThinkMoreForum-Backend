package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.component.ComponentGetDto;
import com.thinkmore.forum.dto.component.ComponentPostDto;
import com.thinkmore.forum.dto.component.ComponentPutDto;
import com.thinkmore.forum.entity.Component;
import com.thinkmore.forum.mapper.ComponentMapper;
import com.thinkmore.forum.repository.ComponentRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComponentService {
    private final ComponentRepository componentRepository;
    private final ComponentMapper componentMapper;

    public ComponentGetDto getComponent(String name) throws Exception {
        Optional<Component> component = componentRepository.findByName(name);
        return componentMapper.fromEntity(component.orElseThrow(() -> new Exception("Component not found")));
    }

    @Transactional
    public ComponentGetDto postComponent(ComponentPostDto componentPostDto) {
        Component component = componentMapper.toEntity(componentPostDto);
        componentRepository.save(component);
        return componentMapper.fromEntity(component);
    }

    @Transactional
    public ComponentGetDto putComponent(ComponentPutDto componentPutDto) {
        Component component = componentMapper.toEntity(componentPutDto);
        componentRepository.save(component);
        return componentMapper.fromEntity(component);
    }

    @Transactional
    public Boolean deleteComponent(String name) {
        Optional<Component> component = componentRepository.findByName(name);
        component.ifPresent(componentRepository::delete);
        return true;
    }
}