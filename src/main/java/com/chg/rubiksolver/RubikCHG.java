/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.chg.rubiksolver;

/**
 *
 * @author carlosherrero
 */
public class RubikCHG {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Hello World!");
        RubikModel rubik=new RubikModel();
        System.out.println("Front");
        rubik.printRubikFace(rubik.YFront);
        System.out.println("Back");
        rubik.printRubikFace(rubik.YBack);
        System.out.println("Left");
        rubik.printRubikFace(rubik.XLeft);
        System.out.println("Right");
        rubik.printRubikFace(rubik.XRight);
        System.out.println("Up");
        rubik.printRubikFace(rubik.ZUp);
        System.out.println("Down");
        rubik.printRubikFace(rubik.ZDown);
    }
    
}
