package com.sissilab.dp.ox3_behavioral.ox31_strategy.normal;


/**
 * 处理上下文类：
 * 面向策略设计的类，包含策略接口的变量；包含一个委托方法，委托策略变量调用具体实现策略的处理方法
 */
public class HandleContext {

    // 策略接口的变量
    private IHandleStrategy handleStrategy;

    /**
     * 构造函数：接收策略接口的变量
     *
     * @param handleStrategy
     */
    public HandleContext(IHandleStrategy handleStrategy) {
        this.handleStrategy = handleStrategy;
    }

    /**
     * 改变处理策略
     *
     * @param handleStrategy
     */
    public void changeHandleStrategy(IHandleStrategy handleStrategy) {
        this.handleStrategy = handleStrategy;
    }

    /**
     * 委托处理方法，根据构造函数或改变策略方法设置的策略接口的变量，以决定采用何种处理策略
     */
    public void handle() {
        handleStrategy.handle();
    }
}
