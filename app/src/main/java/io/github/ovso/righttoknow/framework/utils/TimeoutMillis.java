package io.github.ovso.righttoknow.framework.utils;

import lombok.Getter;

@Getter public enum TimeoutMillis {
    JSOUP(5000);

    private int value;

    TimeoutMillis(int value) {
        this.value = value;
    }
}
