/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chg.rubiksolver;

/**
 *
 * @author carlosherrero
 */
public class EdgePosition {
    public int i;
    public int j;
    public RubikModel.Face aFace;
    
    public EdgePosition(int a, int b, RubikModel.Face f)
    {
        i=a;
        j=b;
        aFace=f;
    }
    
    public String print()  
    {
        String s="Face: ";
        switch (aFace)
        {
            case Left:
                s=s+"Left (Red)";
                break; 
            case Right:
                s=s+"Right (Orange)";
                break; 
            case Back:
                s=s+"Back (Green)";
                break; 
            case Front:
                s=s+"Front (Blue)";
                break; 
            case Up:
                s=s+"Up (White)";
                break; 
            case Down:
                s=s+"Down (Yellow)";
                break;
            default:
                break;
        }
        s=s+String.format(" %d %d", i, j);

        return s;
    }
    
    public void set(RubikModel.Face f, int a, int b) {
    
        i=a;
        j=b;
        aFace=f;
    }
} 
