package net.maku.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Home welcome message
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Tag(name = "Home")
@RestController
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "Hello, the project has started, enjoy!";
    }
}
