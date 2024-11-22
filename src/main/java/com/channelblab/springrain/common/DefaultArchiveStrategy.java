package com.channelblab.springrain.common;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-11-22 16:47
 * @description：
 * @modified By：
 */
@Component
@ConditionalOnMissingBean(ArchiveStrategy.class)
public class DefaultArchiveStrategy implements ArchiveStrategy {
    @Override
    public void archiveOldData() {

    }
}
