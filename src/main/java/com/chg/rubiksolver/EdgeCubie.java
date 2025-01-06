/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chg.rubiksolver;
//utility class to reference a position in a rubik cube for an edge cubie from a main color perspective
/**
 *
 * @author carlosherrero
 */
public class EdgeCubie {
    EdgePosition pos;
    RubikModel.Color main;
    RubikModel.Color b;
    EdgePosition origPos;
    
    public EdgeCubie (EdgePosition aPos, EdgePosition a_origPos, RubikModel.Color a_main, RubikModel.Color a_b)
    {
        pos=aPos;
        main=a_main;
        b=a_b;
        origPos=a_origPos;
    }
}
