package com.channelblab.springrain.common;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-11-22 16:47
 * @description：
 * @modified By：
 */
@ConditionalOnMissingBean(ArchiveStrategy.class)
public class DefaultArchiveStrategy implements ArchiveStrategy {
    @Override
    public void archiveOldData() {

    }
}
