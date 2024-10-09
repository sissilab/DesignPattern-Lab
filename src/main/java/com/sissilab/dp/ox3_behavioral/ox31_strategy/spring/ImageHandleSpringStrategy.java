package com.sissilab.dp.ox3_behavioral.ox31_strategy.spring;

import org.springframework.stereotype.Component;

/**
 * 具体策略类：图片处理策略
 */
@Component(HandleSpringContext.IMAGE_STRATEGY_TYPE)
public class ImageHandleSpringStrategy implements IHandleSpringStrategy {
    @Override
    public String handle() {
        return "handle image...";
    }
}
