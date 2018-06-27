package com.jh.jsuk.service;

import java.util.List;

public interface RuleEngineService<T> {
    /**
     * 初始化规则引擎
     */
    //public void initEngine();

    /**
     * 刷新规则引擎中的规则
     */
    void refreshEnginRule(String rulesFileName, String rules);

    /**
     * 执行规则引擎
     *
     * @param fact 积分Fact
     */
    T executeRuleEngine(List<String> rulesNames, final T fact);

    T executeRuleEngine(String rulesName, final T fact);
}
