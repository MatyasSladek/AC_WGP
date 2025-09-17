package com.github.matyassladek.ac_wgp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum Scoring {
    TOP3(List.of(5, 2, 1)),
    TOP5(List.of(10, 6, 4, 2, 1)),
    TOP6(List.of(10, 7, 5, 3, 2, 1)),
    TOP8(List.of(20, 12, 8, 5, 4, 3, 2, 1)),
    TOP10_A(List.of(20, 15, 12, 9, 7, 5, 4, 3, 2, 1)),
    TOP10_B(List.of(50, 30, 20, 15, 10, 5, 4, 3, 2, 1)),
    TOP12(List.of(20,15,12,9,8,7,6,5,4,3,2,1)),
    TOP15_A(List.of(50,30,20,15,12,10,9,8,7,6,5,4,3,2,1)),
    TOP15_B(List.of(20,16,14,12,11,10,9,8,7,6,5,4,3,2,1)),

    FIA60(List.of(8, 6, 4, 3, 2, 1)),
    FIA62(List.of(9, 6, 4, 3, 2, 1)),
    FIA91(List.of(10, 6, 4, 3, 2, 1)),
    FIA03(List.of(10, 8, 6, 5, 4, 3, 2, 1)),
    FIA09(List.of(12, 9, 7, 5, 4, 3, 2, 1)),
    FIA10(List.of(25, 18, 15, 12, 10, 8, 6, 4, 2, 1)),
    FIA24(List.of(25, 18, 15, 12, 10, 8, 6, 5, 4, 3, 2, 1));

    private final List<Integer> points;
}
