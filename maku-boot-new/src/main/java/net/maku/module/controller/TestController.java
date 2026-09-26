package net.maku.module.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import net.maku.framework.common.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * New module test
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@RestController
@RequestMapping("new/test")
@Tag(name="New module test")
@AllArgsConstructor
public class TestController {

    @GetMapping()
    @Operation(summary = "Test API")
    public Result<String> test(){

        return Result.ok("Test data");
    }
}
