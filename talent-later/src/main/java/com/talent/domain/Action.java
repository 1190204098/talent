package com.talent.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author luffy
 */
@Data
public class Action implements Serializable {
    private Integer actid;
    private String title, flag;
}
