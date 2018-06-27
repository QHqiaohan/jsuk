package com.jh.jsuk.service.impl;

import com.jh.jsuk.service.RuleEngineService;
import com.jh.jsuk.utils.KieUtils;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.Match;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleEngineServiceImpl<T> implements RuleEngineService<T> {
    private KieServices kieServices;

    public void PointRuleEngineImpl() {
        kieServices = KieServices.Factory.get();
    }

    @Override
    public void refreshEnginRule(String rulesFileName, String rules) {
        kieServices = KieServices.Factory.get();
        KieFileSystem kfs = kieServices.newKieFileSystem();
        kfs.write("src/main/resources/rules/" + rulesFileName + ".drl", rules);
        KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
        Results results = kieBuilder.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            System.out.println(results.getMessages());
            throw new IllegalStateException("### errors:规则重载失败 ###");
        }
        KieUtils.setKieContainer(kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId()));
        System.out.println("新规则重载成功");
    }

    @Override
    public T executeRuleEngine(List<String> rulesNames, T fact) {
        KieSession kieSession = KieUtils.getKieSession();
        kieSession.insert(fact);
        int ruleFiredCount = kieSession.fireAllRules(new AgendaFilter() {
            @Override
            public boolean accept(Match match) {
                if (null != rulesNames && rulesNames.size() > 0) {
                    return rulesNames.contains(match.getRule().getName());
                } else {
                    return true;
                }
            }
        });
        System.out.println("触发了" + ruleFiredCount + "条规则");
        return fact;
    }

    @Override
    public T executeRuleEngine(String rulesName, T fact) {
        KieSession kieSession = KieUtils.getKieSession();
        kieSession.insert(fact);
        int ruleFiredCount = kieSession.fireAllRules(new AgendaFilter() {
            @Override
            public boolean accept(Match match) {
                if (null != rulesName) {
                    return match.getRule().getName().equals(rulesName);
                } else {
                    return true;
                }
            }
        });
        System.out.println("触发了" + ruleFiredCount + "条规则");
        return fact;
    }
}
