package com.talent.domain;

import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * @author jmj
 * @since 2021/12/5 12:07
 */
@Data
public class Page<T> {
    private int current=1;
    private int size=5;
    private Integer total=0;
    private List<T> records= Collections.emptyList();
}
