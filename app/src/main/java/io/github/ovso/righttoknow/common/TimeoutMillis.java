package io.github.ovso.righttoknow.common;

import lombok.Getter;

@Getter public enum TimeoutMillis {
    JSOUP(30 * 1000);

    private int value;

    TimeoutMillis(int value) {
        this.value = value;
    }
}
