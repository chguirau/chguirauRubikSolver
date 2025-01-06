<%-- 
    Document   : PrintCubeRotation
    Created on : Dec 31, 2021, 7:14:28 PM
    Author     : carlosherrero
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="com.chg.rubiksolver.RubikModel" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.chg.rubiksolver.EdgePosition" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Rubik Solver</title>
    </head>
    <body>
        <h1>Rubik Rotation</h1>
        <%
        String s;
        RubikModel rubik=new RubikModel();
        rubik.init(rubik.Copy());
        
        // tested ok
        /*
        RubikModel oldRubik=rubik.Rotate(RubikModel.Rotation.R);
        oldRubik=rubik.Rotate(RubikModel.Rotation.L);
        oldRubik=rubik.Rotate(RubikModel.Rotation.U);
        oldRubik=rubik.Rotate(RubikModel.Rotation.D);
        oldRubik=rubik.Rotate(RubikModel.Rotation.F);        
        oldRubik=rubik.Rotate(RubikModel.Rotation.B);        
        oldRubik=rubik.Rotate(RubikModel.Rotation.Bb);        
        oldRubik=rubik.Rotate(RubikModel.Rotation.Fb);        
        oldRubik=rubik.Rotate(RubikModel.Rotation.Db);
        oldRubik=rubik.Rotate(RubikModel.Rotation.Ub);
        oldRubik=rubik.Rotate(RubikModel.Rotation.Lb);
        oldRubik=rubik.Rotate(RubikModel.Rotation.Rb);
        */
        // tested OK
        /*
        oldRubik=rubik.Rotate(RubikModel.Rotation.R);
        oldRubik=rubik.Rotate(RubikModel.Rotation.Lb);
        oldRubik=rubik.Rotate(RubikModel.Rotation.U);
        oldRubik=rubik.Rotate(RubikModel.Rotation.Db);
        oldRubik=rubik.Rotate(RubikModel.Rotation.Rb);
        oldRubik=rubik.Rotate(RubikModel.Rotation.L);
        oldRubik=rubik.Rotate(RubikModel.Rotation.Ub);
        oldRubik=rubik.Rotate(RubikModel.Rotation.D);
        oldRubik=rubik.Rotate(RubikModel.Rotation.F);
        oldRubik=rubik.Rotate(RubikModel.Rotation.Bb);
        oldRubik=rubik.Rotate(RubikModel.Rotation.Ub);
        oldRubik=rubik.Rotate(RubikModel.Rotation.D);
        */
        
        //tested OK
        /*
        ArrayList<RubikModel.Rotation> rotations=new ArrayList<RubikModel.Rotation>();
        rotations.add(RubikModel.Rotation.Db);
        rotations.add(RubikModel.Rotation.U);
        rotations.add(RubikModel.Rotation.B);
        rotations.add(RubikModel.Rotation.Fb);
        rotations.add(RubikModel.Rotation.Db);
        rotations.add(RubikModel.Rotation.U);
        rotations.add(RubikModel.Rotation.Lb);
        rotations.add(RubikModel.Rotation.R);
        rotations.add(RubikModel.Rotation.D);
        rotations.add(RubikModel.Rotation.Ub);
        rotations.add(RubikModel.Rotation.L);
        rotations.add(RubikModel.Rotation.Rb);
        ArrayList<RubikModel> list=rubik.RotationSequence(rotations);
        */
        
        RubikModel oldRubik;
        //rubik.RotationSequenceFromString("Bb L Lb Lb R R F Db Bb Db B Ub Fb B B Ub Lb Lb Ub Fb L Bb B U U Db B Rb L Ub L U Ub F U D D Bb Db B D D Lb D L D D Lb Db L D D Rb Db R L D Lb D Db Fb D F D L Db Lb D D R Db Rb Db Bb D B D Db Rb D R D F Db Fb D D B Db Bb Db Lb D L D Db Bb D B D R Db Rb D D L Db Lb Db Fb D F D Db Fb D F D L Db Lb D D D Db Lb D L D B Db Bb R Lb F Rb L D R Lb F Rb L D R Lb F Rb L Db F D D Fb Db F Db Fb Db Db B Db Fb D Bb Db F D");
        rubik.Scramble();
        out.println(rubik.printSequence());
        out.println("<br>");
        out.println("Check Up face (White):\n<br>");
        
        out.println("2, 1: ");
        if (rubik.checkCubie(RubikModel.Color.White, 2, 1)) out.println("True\n"); else out.println("False\n");
        out.println("<br>");
        if(!rubik.checkCubie(RubikModel.Color.White, 2, 1)) 
        {
            EdgePosition p=rubik.locateEdge(RubikModel.Color.White, RubikModel.Color.Blue);
            out.println("Found:");
            out.println("<br>");
            out.println(p.print());
            out.println("<br>");
        }

        out.println("1, 2: ");
        if (rubik.checkCubie(RubikModel.Color.White, 1, 2)) out.println("True\n"); else out.println("False\n");
        out.println("<br>");
        if(!rubik.checkCubie(RubikModel.Color.White, 1, 2)) 
        {
            EdgePosition p=rubik.locateEdge(RubikModel.Color.White, RubikModel.Color.Orange);
            out.println("Found:");
            out.println("<br>");
            out.println(p.print());
            out.println("<br>");
        }

        out.println("0, 1: ");
        if (rubik.checkCubie(RubikModel.Color.White, 0, 1)) out.println("True\n"); else out.println("False\n");
        out.println("<br>");
        if(!rubik.checkCubie(RubikModel.Color.White, 0, 1)) 
        {
            EdgePosition p=rubik.locateEdge(RubikModel.Color.White, RubikModel.Color.Green);
            out.println("Found:");
            out.println("<br>");
            out.println(p.print());
            out.println("<br>");
        }
        
        out.println("1, 0: ");
        if (rubik.checkCubie(RubikModel.Color.White, 1, 0)) out.println("True\n"); else out.println("False\n");
        out.println("<br>");
        if(!rubik.checkCubie(RubikModel.Color.White, 1, 0)) 
        {
            EdgePosition p=rubik.locateEdge(RubikModel.Color.White, RubikModel.Color.Red);
            out.println("Found:");
            out.println("<br>");
            out.println(p.print());
            out.println("<br>");
        }
        
        rubik.upCross();
        rubik.layer1();
        out.println(rubik.printSequence());
        out.println("<br>");
        oldRubik=rubik.Copy();
        rubik.layer2();
        rubik.downCross();
        rubik.downPlaceEdges();
        rubik.downCorners();
        rubik.downPlaceCorners();
        out.println(rubik.printSequence());
        out.println("<br>");
        
        
        
        //We show all faces, staring from 4 faces in Z axis perspective
        //then from Y or X (some faces are shown in different perspectives)
        //on Z
        out.println("on Z");
        out.println("<br>");   
        out.println("<table>\n");
        out.println("<tr>\n");
        //old rubik
        out.println("<th>");
        out.println("Left");
        out.println("<br>");        
        s=RubikModel.drawRubikFace(oldRubik.XLeft);
        out.println(s);
        out.println("</th>");
        out.println("<th>");
        out.println("Front");
        out.println("<br>");        
        s=RubikModel.drawRubikFace(oldRubik.YFront);
        out.println(s);
        out.println("</th>");
        out.println("<th>");
        out.println("Right");
        out.println("<br>");        
        s=RubikModel.drawRubikFace(oldRubik.XRight);
        out.println(s);
        out.println("</th>");
        out.println("<th>");
        out.println("Back");
        out.println("<br>");        
        s=RubikModel.drawRubikFace(oldRubik.YBack);
        out.println(s);
        out.println("</th>");
        //separator
        out.println("<th>");
        out.println("Rotating");
        out.println("<br>");        
        out.println("------>");        
        out.println("<br>");        
        out.println("</th>");
        //new rubik
        out.println("<th>");
        out.println("Left");
        out.println("<br>");        
        s=RubikModel.drawRubikFace(rubik.XLeft);
        out.println(s);
        out.println("</th>");
        out.println("<th>");
        out.println("Front");
        out.println("<br>");        
        s=RubikModel.drawRubikFace(rubik.YFront);
        out.println(s);
        out.println("</th>");
        out.println("<th>");
        out.println("Right");
        out.println("<br>");        
        s=RubikModel.drawRubikFace(rubik.XRight);
        out.println(s);
        out.println("</th>");
        out.println("<th>");
        out.println("Back");
        out.println("<br>");        
        s=RubikModel.drawRubikFace(rubik.YBack);
        out.println(s);
        out.println("</th>");
        out.println("</tr>\n");
        out.println("</table>\n");
        
        //on X
        out.println("on X");
        out.println("<br>");   
        out.println("<table>\n");
        out.println("<tr>\n");
        //old rubik
        out.println("<th>");
        out.println("Up");
        out.println("<br>");        
        s=RubikModel.drawRubikFace(oldRubik.ZUp);
        out.println(s);
        out.println("</th>");
        out.println("<th>");
        out.println("Front");
        out.println("<br>");        
        s=RubikModel.drawRubikFace(oldRubik.YFront);
        out.println(s);
        out.println("</th>");
        out.println("<th>");
        out.println("Down");
        out.println("<br>");        
        s=RubikModel.drawRubikFace(oldRubik.ZDown);
        out.println(s);
        out.println("</th>");
        out.println("<th>");
        out.println("Back");
        out.println("<br>");        
        s=RubikModel.drawRubikFace(oldRubik.YBack);
        out.println(s);
        out.println("</th>");
        //separator
        out.println("<th>");
        out.println("Rotating");
        out.println("<br>");        
        out.println("------>");        
        out.println("<br>");        
        out.println("</th>");
        //new rubik
        out.println("<th>");
        out.println("Up");
        out.println("<br>");        
        s=RubikModel.drawRubikFace(rubik.ZUp);
        out.println(s);
        out.println("</th>");
        out.println("<th>");
        out.println("Front");
        out.println("<br>");        
        s=RubikModel.drawRubikFace(rubik.YFront);
        out.println(s);
        out.println("</th>");
        out.println("<th>");
        out.println("Down");
        out.println("<br>");        
        s=RubikModel.drawRubikFace(rubik.ZDown);
        out.println(s);
        out.println("</th>");
        out.println("<th>");
        out.println("Back");
        out.println("<br>");        
        s=RubikModel.drawRubikFace(rubik.YBack);
        out.println(s);
        out.println("</th>");
        out.println("</tr>\n");
        out.println("</table>\n");
        
        //on Y
        out.println("on Y");
        out.println("<br>");   
        out.println("<table>\n");
        out.println("<tr>\n");
        //old rubik
        out.println("<th>");
        out.println("Left");
        out.println("<br>");        
        s=RubikModel.drawRubikFace(oldRubik.XLeft);
        out.println(s);
        out.println("</th>");
        out.println("<th>");
        out.println("Up");
        out.println("<br>");        
        s=RubikModel.drawRubikFace(oldRubik.ZUp);
        out.println(s);
        out.println("</th>");
        out.println("<th>");
        out.println("Right");
        out.println("<br>");        
        s=RubikModel.drawRubikFace(oldRubik.XRight);
        out.println(s);
        out.println("</th>");
        out.println("<th>");
        out.println("Down");
        out.println("<br>");        
        s=RubikModel.drawRubikFace(oldRubik.ZDown);
        out.println(s);
        out.println("</th>");
        //separator
        out.println("<th>");
        out.println("Rotating");
        out.println("<br>");        
        out.println("------>");        
        out.println("<br>");        
        out.println("</th>");
        //new rubik
        out.println("<th>");
        out.println("Left");
        out.println("<br>");        
        s=RubikModel.drawRubikFace(rubik.XLeft);
        out.println(s);
        out.println("</th>");
        out.println("<th>");
        out.println("Up");
        out.println("<br>");        
        s=RubikModel.drawRubikFace(rubik.ZUp);
        out.println(s);
        out.println("</th>");
        out.println("<th>");
        out.println("Right");
        out.println("<br>");        
        s=RubikModel.drawRubikFace(rubik.XRight);
        out.println(s);
        out.println("</th>");
        out.println("<th>");
        out.println("Down");
        out.println("<br>");        
        s=RubikModel.drawRubikFace(rubik.ZDown);
        out.println(s);
        out.println("</th>");
        out.println("</tr>\n");
        out.println("</table>\n");
        %>

    </body>
</html>
