package com.sissilab.dp.ox3_behavioral.ox31_strategy.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试应用
 */
@RestController
public class HandleSpringController {

    @Autowired
    private HandleSpringContext handleSpringContext;

    /**
     * http://localhost:8080/handle?type=image
     * http://localhost:8080/handle?type=video
     * http://localhost:8080/handle?type=audio
     * http://localhost:8080/handle?type=file 异常情况
     *
     * @param type
     * @return
     */
    @GetMapping("handle")
    public String handle(@RequestParam("type") String type) {
        return handleSpringContext.handle(type);
    }

}
