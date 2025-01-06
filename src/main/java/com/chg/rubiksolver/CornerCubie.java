/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chg.rubiksolver;

import com.chg.rubiksolver.CornerPosition;
import com.chg.rubiksolver.EdgeCubie;
import com.chg.rubiksolver.EdgePosition;
import com.chg.rubiksolver.RubikModel;

/**
 *
 * @author carlosherrero
 */
public class CornerCubie extends EdgeCubie {
    RubikModel.Color c;
    public CornerCubie(CornerPosition cp, CornerPosition a_orig, RubikModel.Color main, RubikModel.Color b, RubikModel.Color a_c)
    {
        super((EdgePosition) cp, (EdgePosition) a_orig, main, b);
        c=a_c;
        
    }
}
