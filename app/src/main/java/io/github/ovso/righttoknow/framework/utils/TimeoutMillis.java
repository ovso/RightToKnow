package io.github.ovso.righttoknow.framework.utils;

import lombok.Getter;

@Getter public enum TimeoutMillis {
    JSOUP(10000);

    private int value;

    TimeoutMillis(int value) {
        this.value = value;
    }
}
