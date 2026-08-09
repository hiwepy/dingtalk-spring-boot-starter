package com.dingtalk.spring.boot.bean;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link HideAvatarType}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class HideAvatarTypeTest {

    @Test
    void hide_shouldHaveCorrectValues() {
        assertThat(HideAvatarType.HIDE.getComment()).isEqualTo("Hide");
        assertThat(HideAvatarType.HIDE.getValue()).isEqualTo("1");
    }

    @Test
    void unhide_shouldHaveCorrectValues() {
        assertThat(HideAvatarType.UNHIDE.getComment()).isEqualTo("Show");
        assertThat(HideAvatarType.UNHIDE.getValue()).isEqualTo("0");
    }

    @Test
    void values_shouldContainBothTypes() {
        assertThat(HideAvatarType.values()).hasSize(2);
    }
}
