package com.apus.demo.util.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum State {
    //dùng chung
    DRAFT, //dự thảo
    VALID,
    INVALID,
    CANCELLED,

    //dùng riêng cho màn reward;
    PUBLISH,
    STORE,
}
