package com.thinkmore.forum.controller.v1;

import com.thinkmore.forum.service.ComponentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/component")
@RequiredArgsConstructor
public class ComponentController {
    private final ComponentService componentService;
}
