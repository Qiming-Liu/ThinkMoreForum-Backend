package com.thinkmore.forum.controller.v1;

import com.thinkmore.forum.dto.component.ComponentGetDto;
import com.thinkmore.forum.dto.component.ComponentPostDto;
import com.thinkmore.forum.dto.component.ComponentPutDto;
import com.thinkmore.forum.service.ComponentService;
import com.thinkmore.forum.util.Util;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/component")
@RequiredArgsConstructor
public class ComponentController {
    private final ComponentService componentService;

    @PostMapping
    public ResponseEntity<ComponentGetDto> postComponent(@RequestBody ComponentPostDto componentPostDto) {
        Util.checkPermission("adminManagement");
        return ResponseEntity.ok(componentService.postComponent(componentPostDto));
    }

    @PutMapping
    public ResponseEntity<ComponentGetDto> putComponent(@RequestBody ComponentPutDto componentPutDto) {
        Util.checkPermission("adminManagement");
        return ResponseEntity.ok(componentService.putComponent(componentPutDto));
    }

    @DeleteMapping(path = "/{name}")
    public ResponseEntity<Boolean> deleteComponent(@PathVariable String name) {
        Util.checkPermission("adminManagement");
        return ResponseEntity.ok(componentService.deleteComponent(name));
    }
}
