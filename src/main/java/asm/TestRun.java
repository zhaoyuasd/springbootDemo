package asm;

import org.springframework.transaction.annotation.Transactional;

/**
 * @author dongli
 * @create 2024/4/23 15:04
 * @desc
 */

@FunctionalInterface
public interface TestRun {

    @Transactional
    String show(String text);
}
