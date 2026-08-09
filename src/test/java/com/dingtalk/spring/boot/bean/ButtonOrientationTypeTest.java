package com.dingtalk.spring.boot.bean;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ButtonOrientationType}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class ButtonOrientationTypeTest {

    @Test
    void horizontal_shouldHaveCorrectValues() {
        assertThat(ButtonOrientationType.HORIZONTAL.getComment()).isEqualTo("Horizontal");
        assertThat(ButtonOrientationType.HORIZONTAL.getValue()).isEqualTo("1");
    }

    @Test
    void vertical_shouldHaveCorrectValues() {
        assertThat(ButtonOrientationType.VERTICAL.getComment()).isEqualTo("Vertical");
        assertThat(ButtonOrientationType.VERTICAL.getValue()).isEqualTo("0");
    }

    @Test
    void values_shouldContainBothTypes() {
        assertThat(ButtonOrientationType.values()).hasSize(2);
    }
}
