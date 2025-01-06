/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chg.rubiksolver;

import com.chg.rubiksolver.CornerCubie;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author carlosherrero
 */
public class RubikModel {
public static enum Color { White, Yellow, Red, Orange, Green, Blue };
public Color YFront[][]= {{Color.Blue,Color.Blue,Color.Blue},{Color.Blue,Color.Blue,Color.Blue},{Color.Blue,Color.Blue,Color.Blue}};
public Color YBack[][]= {{Color.Green,Color.Green,Color.Green},{Color.Green,Color.Green,Color.Green},{Color.Green,Color.Green,Color.Green}};
public Color XLeft[][]= {{Color.Red,Color.Red,Color.Red},{Color.Red,Color.Red,Color.Red},{Color.Red,Color.Red,Color.Red}};
public Color XRight[][]={{Color.Orange,Color.Orange,Color.Orange},{Color.Orange,Color.Orange,Color.Orange},{Color.Orange,Color.Orange,Color.Orange}};
public Color ZUp[][]={{Color.White,Color.White,Color.White},{Color.White,Color.White,Color.White},{Color.White,Color.White,Color.White}};
public Color ZDown[][]= {{Color.Yellow,Color.Yellow,Color.Yellow},{Color.Yellow,Color.Yellow,Color.Yellow},{Color.Yellow,Color.Yellow,Color.Yellow}};
//we initialize rubik cube with default faces up white, front blue, etc
//rubik cube is defined as an instance of this class, with a set of values (colors) for this arrays (the faces)
//but as after initialization this values are only set via a rotation, only a valid state for rubik cube is possible

//PENDING
//instead of accessing the cube directly on these faces arrays, I can create a rubi as hasMap <Face, Color[][]>, and initialize it
//when I have to do some operations, I do not need to check if Color==White, then use ZUp, but use rubik.get(White)!!

//notation
//a00 is top left, a22 is botton right, in YFront
//similar for ZDown, assuming we rotate cube clockwise on X axis to put ZDown in front
//similar for XRight, rotating cube on Z clock
//similar for XLeft BUT rotating on Z anticlock
//similar for ZUp, BUT rotating on X anticlock
//YBack, special, we rotate on Z twice clock/anticlock

public static enum Face { Up, Down, Left, Right, Back, Front};


public static enum Rotation {R, Rb, L, Lb, U, Ub, D, Db, F, Fb, B, Bb };
//standard rubik notation
// we assume R2 is R and R, not a rotation but 2, same for L2, U2, D2, F2, B2

public static enum RotationCube {Z, Zb, X, Xb, Y, Yb};

public RubikModel OrigRubik;

public void init(RubikModel a_orig)
{
    OrigRubik = a_orig;
}

public static String printRubikFace(Color[][] aFace)
{
String s="";
for(int i=0; i<3; i++)
  for (int j=0;j<3;j++)
  {
    s=s+"i: "+i+" j: "+j+" Color: "+aFace[i][j].toString()+"<br>\n";
  }
return s;
}

public static String drawRubikFace(Color[][] aFace) throws Exception
{
String s="<table>\n";
for(int i=0; i<3; i++)
{  
  s=s+"<tr>\n";  
  for (int j=0;j<3;j++)
  {
    switch (aFace[i][j])
    {
        case White:
            s=s+"<th><div style=\"width: 50px;height: 50px;background-color: grey\"></div></th>\n";
            break;
        case Blue:
            s=s+"<th><div style=\"width: 50px;height: 50px;background-color: blue\"></div></th>\n";
            break;
        case Green:
            s=s+"<th><div style=\"width: 50px;height: 50px;background-color: green\"></div></th>\n";
            break;
        case Red:
            s=s+"<th><div style=\"width: 50px;height: 50px;background-color: red\"></div></th>\n";
            break;
        case Orange:
            s=s+"<th><div style=\"width: 50px;height: 50px;background-color: orange\"></div></th>\n";
            break;
        case Yellow:
            s=s+"<th><div style=\"width: 50px;height: 50px;background-color: yellow\"></div></th>\n";
            break;
        default:
            throw (new Exception());
    }
  }
  s=s+"</tr>\n";
}
s=s+"</table>\n";
return s;
}

public static Color FaceOrigColor(Face aFace)
{
    Color origColor=null;
    switch (aFace)
    {
        case Up: origColor=Color.White; break;
        case Down: origColor=Color.Yellow; break;
        case Front: origColor=Color.Blue; break;
        case Back: origColor=Color.Green; break;
        case Right: origColor=Color.Orange; break;
        case Left: origColor=Color.Red; break;                
    }
    return origColor;
}

public static Face ColorOrigFace(Color aColor)
{
    Face origFace=null;
    switch (aColor)
    {
        case White: origFace=Face.Up; break;
        case Yellow: origFace=Face.Down; break;
        case Blue: origFace=Face.Front; break;
        case Green: origFace=Face.Back; break;
        case Orange: origFace=Face.Right; break;
        case Red: origFace=Face.Left; break;           
    }
    return origFace;
}


public RubikModel Copy()
{
    RubikModel copy=new RubikModel();
    for (int i=0; i<3; i++)
        for (int j=0; j<3; j++)
        {
            copy.XRight[i][j]=XRight[i][j];
            copy.XLeft[i][j]=XLeft[i][j];
            copy.YBack[i][j]=YBack[i][j];
            copy.YFront[i][j]=YFront[i][j];
            copy.ZUp[i][j]=ZUp[i][j];
            copy.ZDown[i][j]=ZDown[i][j];
        }
    return copy;
}

public ArrayList<Rotation> seq=new ArrayList<Rotation>();

public RubikModel Rotate (RubikModel.Rotation aRotation) throws Exception
{
        RubikModel copy=Copy();
        switch(aRotation)
        {
            case R:
                //XLeft no change
                //XRight change all
                XRight[0][0]=copy.XRight[2][0];
                XRight[0][1]=copy.XRight[1][0];
                XRight[0][2]=copy.XRight[0][0];
                XRight[1][0]=copy.XRight[2][1];
                XRight[1][1]=copy.XRight[1][1];
                XRight[1][2]=copy.XRight[0][1];
                XRight[2][0]=copy.XRight[2][2];
                XRight[2][1]=copy.XRight[1][2];
                XRight[2][2]=copy.XRight[0][2];
                //ZUp changes 3 squares
                ZUp[0][2]=copy.YFront[0][2];
                ZUp[1][2]=copy.YFront[1][2];
                ZUp[2][2]=copy.YFront[2][2];
                //YBack changes 3
                YBack[0][0]=copy.ZUp[2][2];
                YBack[1][0]=copy.ZUp[1][2];
                YBack[2][0]=copy.ZUp[0][2];
                //YFront changes 3
                YFront[0][2]=copy.ZDown[0][2];
                YFront[1][2]=copy.ZDown[1][2];
                YFront[2][2]=copy.ZDown[2][2];
                //ZDown changes 3
                ZDown[0][2]=copy.YBack[2][0];
                ZDown[1][2]=copy.YBack[1][0];
                ZDown[2][2]=copy.YBack[0][0];
                break;
            case Rb:
                //XLeft no change
                //XRight change all
                XRight[0][0]=copy.XRight[0][2];
                XRight[0][1]=copy.XRight[1][2];
                XRight[0][2]=copy.XRight[2][2];
                XRight[1][0]=copy.XRight[0][1];
                XRight[1][1]=copy.XRight[1][1];
                XRight[1][2]=copy.XRight[2][1];
                XRight[2][0]=copy.XRight[0][0];
                XRight[2][1]=copy.XRight[1][0];
                XRight[2][2]=copy.XRight[2][0];
                //ZUp changes 3 squares
                ZUp[0][2]=copy.YBack[2][0];
                ZUp[1][2]=copy.YBack[1][0];
                ZUp[2][2]=copy.YBack[0][0];
                //YBack changes 3
                YBack[0][0]=copy.ZDown[2][2];
                YBack[1][0]=copy.ZDown[1][2];
                YBack[2][0]=copy.ZDown[0][2];
                //YFront changes 3
                YFront[0][2]=copy.ZUp[0][2];
                YFront[1][2]=copy.ZUp[1][2];
                YFront[2][2]=copy.ZUp[2][2];
                //ZDown changes 3
                ZDown[0][2]=copy.YFront[0][2];
                ZDown[1][2]=copy.YFront[1][2];
                ZDown[2][2]=copy.YFront[2][2];
                break;
            case L:
                //XRight no change
                //XLeft change all
                XLeft[0][0]=copy.XLeft[2][0];
                XLeft[0][1]=copy.XLeft[1][0];
                XLeft[0][2]=copy.XLeft[0][0];
                XLeft[1][0]=copy.XLeft[2][1];
                XLeft[1][1]=copy.XLeft[1][1];
                XLeft[1][2]=copy.XLeft[0][1];
                XLeft[2][0]=copy.XLeft[2][2];
                XLeft[2][1]=copy.XLeft[1][2];
                XLeft[2][2]=copy.XLeft[0][2];
                //ZUp changes 3 squares
                ZUp[0][0]=copy.YBack[2][2];
                ZUp[1][0]=copy.YBack[1][2];
                ZUp[2][0]=copy.YBack[0][2];
                //ZDown changes 3
                ZDown[0][0]=copy.YFront[0][0];
                ZDown[1][0]=copy.YFront[1][0];
                ZDown[2][0]=copy.YFront[2][0];
                //YFront changes 3
                YFront[0][0]=copy.ZUp[0][0];
                YFront[1][0]=copy.ZUp[1][0];
                YFront[2][0]=copy.ZUp[2][0];
                //YBack changes 3
                YBack[0][2]=copy.ZDown[2][0];
                YBack[1][2]=copy.ZDown[1][0];
                YBack[2][2]=copy.ZDown[0][0];                
                break;
            case Lb:
                //XRight no change
                //XLeft change all
                XLeft[0][0]=copy.XLeft[0][2];
                XLeft[0][1]=copy.XLeft[1][2];
                XLeft[0][2]=copy.XLeft[2][2];
                XLeft[1][0]=copy.XLeft[0][1];
                XLeft[1][1]=copy.XLeft[1][1];
                XLeft[1][2]=copy.XLeft[2][1];
                XLeft[2][0]=copy.XLeft[0][0];
                XLeft[2][1]=copy.XLeft[1][0];
                XLeft[2][2]=copy.XLeft[2][0];
                //ZUp changes 3 squares
                ZUp[0][0]=copy.YFront[0][0];
                ZUp[1][0]=copy.YFront[1][0];
                ZUp[2][0]=copy.YFront[2][0];
                //ZDown changes 3
                ZDown[0][0]=copy.YBack[2][2];
                ZDown[1][0]=copy.YBack[1][2];
                ZDown[2][0]=copy.YBack[0][2];
                //YFront changes 3
                YFront[0][0]=copy.ZDown[0][0];
                YFront[1][0]=copy.ZDown[1][0];
                YFront[2][0]=copy.ZDown[2][0];
                //YBack changes 3
                YBack[0][2]=copy.ZUp[2][0];
                YBack[1][2]=copy.ZUp[1][0];
                YBack[2][2]=copy.ZUp[0][0];                
                break;
            case U:
                //ZDown no change
                //ZUp change all
                ZUp[0][0]=copy.ZUp[2][0];
                ZUp[0][1]=copy.ZUp[1][0];
                ZUp[0][2]=copy.ZUp[0][0];
                ZUp[1][0]=copy.ZUp[2][1];
                ZUp[1][1]=copy.ZUp[1][1];
                ZUp[1][2]=copy.ZUp[0][1];
                ZUp[2][0]=copy.ZUp[2][2];
                ZUp[2][1]=copy.ZUp[1][2];
                ZUp[2][2]=copy.ZUp[0][2];
                //YFront changes 3
                YFront[0][0]=copy.XRight[0][0];
                YFront[0][1]=copy.XRight[0][1];
                YFront[0][2]=copy.XRight[0][2];
                //YBack changes 3
                YBack[0][0]=copy.XLeft[0][0];
                YBack[0][1]=copy.XLeft[0][1];
                YBack[0][2]=copy.XLeft[0][2];                
                //XLeft changes 3
                XLeft[0][0]=copy.YFront[0][0];
                XLeft[0][1]=copy.YFront[0][1];
                XLeft[0][2]=copy.YFront[0][2];
                //XRight changes 3
                XRight[0][0]=copy.YBack[0][0];
                XRight[0][1]=copy.YBack[0][1];
                XRight[0][2]=copy.YBack[0][2];
                break;
            case Ub:
                //ZDown no change
                //ZUp change all
                ZUp[0][0]=copy.ZUp[0][2];
                ZUp[0][1]=copy.ZUp[1][2];
                ZUp[0][2]=copy.ZUp[2][2];
                ZUp[1][0]=copy.ZUp[0][1];
                ZUp[1][1]=copy.ZUp[1][1];
                ZUp[1][2]=copy.ZUp[2][1];
                ZUp[2][0]=copy.ZUp[0][0];
                ZUp[2][1]=copy.ZUp[1][0];
                ZUp[2][2]=copy.ZUp[2][0];
                //YFront changes 3
                YFront[0][0]=copy.XLeft[0][0];
                YFront[0][1]=copy.XLeft[0][1];
                YFront[0][2]=copy.XLeft[0][2];
                //YBack changes 3
                YBack[0][0]=copy.XRight[0][0];
                YBack[0][1]=copy.XRight[0][1];
                YBack[0][2]=copy.XRight[0][2];                
                //XLeft changes 3
                XLeft[0][0]=copy.YBack[0][0];
                XLeft[0][1]=copy.YBack[0][1];
                XLeft[0][2]=copy.YBack[0][2];
                //XRight changes 3
                XRight[0][0]=copy.YFront[0][0];
                XRight[0][1]=copy.YFront[0][1];
                XRight[0][2]=copy.YFront[0][2];
                break;
            case D:
                //ZUp no change
                //ZDown change all
                ZDown[0][0]=copy.ZDown[2][0];
                ZDown[0][1]=copy.ZDown[1][0];
                ZDown[0][2]=copy.ZDown[0][0];
                ZDown[1][0]=copy.ZDown[2][1];
                ZDown[1][1]=copy.ZDown[1][1];
                ZDown[1][2]=copy.ZDown[0][1];
                ZDown[2][0]=copy.ZDown[2][2];
                ZDown[2][1]=copy.ZDown[1][2];
                ZDown[2][2]=copy.ZDown[0][2];
                //YFront changes 3
                YFront[2][0]=copy.XLeft[2][0];
                YFront[2][1]=copy.XLeft[2][1];
                YFront[2][2]=copy.XLeft[2][2];
                //YBack changes 3
                YBack[2][0]=copy.XRight[2][0];
                YBack[2][1]=copy.XRight[2][1];
                YBack[2][2]=copy.XRight[2][2];                
                //XLeft changes 3
                XLeft[2][0]=copy.YBack[2][0];
                XLeft[2][1]=copy.YBack[2][1];
                XLeft[2][2]=copy.YBack[2][2];
                //XRight changes 3
                XRight[2][0]=copy.YFront[2][0];
                XRight[2][1]=copy.YFront[2][1];
                XRight[2][2]=copy.YFront[2][2];
                break;
            case Db:
                //ZUp no change
                //ZDown change all
                ZDown[0][0]=copy.ZDown[0][2];
                ZDown[0][1]=copy.ZDown[1][2];
                ZDown[0][2]=copy.ZDown[2][2];
                ZDown[1][0]=copy.ZDown[0][1];
                ZDown[1][1]=copy.ZDown[1][1];
                ZDown[1][2]=copy.ZDown[2][1];
                ZDown[2][0]=copy.ZDown[0][0];
                ZDown[2][1]=copy.ZDown[1][0];
                ZDown[2][2]=copy.ZDown[2][0];
                //YFront changes 3
                YFront[2][0]=copy.XRight[2][0];
                YFront[2][1]=copy.XRight[2][1];
                YFront[2][2]=copy.XRight[2][2];
                //YBack changes 3
                YBack[2][0]=copy.XLeft[2][0];
                YBack[2][1]=copy.XLeft[2][1];
                YBack[2][2]=copy.XLeft[2][2];                
                //XLeft changes 3
                XLeft[2][0]=copy.YFront[2][0];
                XLeft[2][1]=copy.YFront[2][1];
                XLeft[2][2]=copy.YFront[2][2];
                //XRight changes 3
                XRight[2][0]=copy.YBack[2][0];
                XRight[2][1]=copy.YBack[2][1];
                XRight[2][2]=copy.YBack[2][2];
                break;
            case F:
                //YBack no change
                //YFront change all
                YFront[0][0]=copy.YFront[2][0];
                YFront[0][1]=copy.YFront[1][0];
                YFront[0][2]=copy.YFront[0][0];
                YFront[1][0]=copy.YFront[2][1];
                YFront[1][1]=copy.YFront[1][1];
                YFront[1][2]=copy.YFront[0][1];
                YFront[2][0]=copy.YFront[2][2];
                YFront[2][1]=copy.YFront[1][2];
                YFront[2][2]=copy.YFront[0][2];
                //XRight 3 changes
                XRight[0][0]=copy.ZUp[2][0];
                XRight[1][0]=copy.ZUp[2][1];
                XRight[2][0]=copy.ZUp[2][2];
                //XLeft 3 changes
                XLeft[0][2]=copy.ZDown[0][0];
                XLeft[1][2]=copy.ZDown[0][1];
                XLeft[2][2]=copy.ZDown[0][2];
                //ZUp 3 changes
                ZUp[2][0]=copy.XLeft[2][2];
                ZUp[2][1]=copy.XLeft[1][2];
                ZUp[2][2]=copy.XLeft[0][2];                
                //ZDown 3 changes
                ZDown[0][0]=copy.XRight[2][0];
                ZDown[0][1]=copy.XRight[1][0];
                ZDown[0][2]=copy.XRight[0][0];
                break;
            case Fb:
                //YBack no change
                //YFront change all
                YFront[0][0]=copy.YFront[0][2];
                YFront[0][1]=copy.YFront[1][2];
                YFront[0][2]=copy.YFront[2][2];
                YFront[1][0]=copy.YFront[0][1];
                YFront[1][1]=copy.YFront[1][1];
                YFront[1][2]=copy.YFront[2][1];
                YFront[2][0]=copy.YFront[0][0];
                YFront[2][1]=copy.YFront[1][0];
                YFront[2][2]=copy.YFront[2][0];
                //XRight 3 changes
                XRight[0][0]=copy.ZDown[0][2];
                XRight[1][0]=copy.ZDown[0][1];
                XRight[2][0]=copy.ZDown[0][0];
                //XLeft 3 changes
                XLeft[0][2]=copy.ZUp[2][2];
                XLeft[1][2]=copy.ZUp[2][1];
                XLeft[2][2]=copy.ZUp[2][0];
                //ZUp 3 changes
                ZUp[2][0]=copy.XRight[0][0];
                ZUp[2][1]=copy.XRight[1][0];
                ZUp[2][2]=copy.XRight[2][0];                
                //ZDown 3 changes
                ZDown[0][0]=copy.XLeft[0][2];
                ZDown[0][1]=copy.XLeft[1][2];
                ZDown[0][2]=copy.XLeft[2][2];
                break;
            case B:
                //YFront no change
                //YBack change all
                YBack[0][0]=copy.YBack[2][0];
                YBack[0][1]=copy.YBack[1][0];
                YBack[0][2]=copy.YBack[0][0];
                YBack[1][0]=copy.YBack[2][1];
                YBack[1][1]=copy.YBack[1][1];
                YBack[1][2]=copy.YBack[0][1];
                YBack[2][0]=copy.YBack[2][2];
                YBack[2][1]=copy.YBack[1][2];
                YBack[2][2]=copy.YBack[0][2];
                //XRight 3 changes
                XRight[0][2]=copy.ZDown[2][2];
                XRight[1][2]=copy.ZDown[2][1];
                XRight[2][2]=copy.ZDown[2][0];
                //XLeft 3 changes
                XLeft[0][0]=copy.ZUp[0][2];
                XLeft[1][0]=copy.ZUp[0][1];
                XLeft[2][0]=copy.ZUp[0][0];
                //ZUp 3 changes
                ZUp[0][0]=copy.XRight[0][2];
                ZUp[0][1]=copy.XRight[1][2];
                ZUp[0][2]=copy.XRight[2][2];
                //ZDown 3 changes
                ZDown[2][0]=copy.XLeft[0][0];
                ZDown[2][1]=copy.XLeft[1][0];
                ZDown[2][2]=copy.XLeft[2][0];
                break;
            case Bb:
                //YFront no change
                //YBack change all
                YBack[0][0]=copy.YBack[0][2];
                YBack[0][1]=copy.YBack[1][2];
                YBack[0][2]=copy.YBack[2][2];
                YBack[1][0]=copy.YBack[0][1];
                YBack[1][1]=copy.YBack[1][1];
                YBack[1][2]=copy.YBack[2][1];
                YBack[2][0]=copy.YBack[0][0];
                YBack[2][1]=copy.YBack[1][0];
                YBack[2][2]=copy.YBack[2][0];
                //XRight 3 changes
                XRight[0][2]=copy.ZUp[0][0];
                XRight[1][2]=copy.ZUp[0][1];
                XRight[2][2]=copy.ZUp[0][2];
                //XLeft 3 changes
                XLeft[0][0]=copy.ZDown[2][0];
                XLeft[1][0]=copy.ZDown[2][1];
                XLeft[2][0]=copy.ZDown[2][2];
                //ZUp 3 changes
                ZUp[0][0]=copy.XLeft[2][0];
                ZUp[0][1]=copy.XLeft[1][0];
                ZUp[0][2]=copy.XLeft[0][0];
                //ZDown 3 changes
                ZDown[2][0]=copy.XRight[2][2];
                ZDown[2][1]=copy.XRight[1][2];
                ZDown[2][2]=copy.XRight[0][2];
                break;
            default:
                throw new Exception();
        }
        seq.add(aRotation);
        return copy;
}

public ArrayList<RubikModel> RotationSequenceRubik (ArrayList<RubikModel.Rotation> aRotationList) throws Exception
{
    ArrayList<RubikModel> aRubikList = new ArrayList<RubikModel> ();
    for (int i=0; i< aRotationList.size(); i++)
    {
        aRubikList.add(Rotate(aRotationList.get(i)));
    }
    //aRubikList.add(this);
    //not necessary, assume this is in latest state
    return aRubikList;
}

public void RotationSequence (ArrayList<RubikModel.Rotation> aRotationList) throws Exception
{
    for (int i=0; i< aRotationList.size(); i++)
    {
        Rotate(aRotationList.get(i));
    }
}

public void RotationSequenceFromString (String aRotationListSt) throws Exception
{
    String[] tokens=aRotationListSt.split(" ");
    for (int i=0; i< tokens.length; i++)
    {
        Rotate(RotationFromString(tokens[i]));
    }
}

public static Rotation RotationFromString(String rotSt) throws Exception
{
    if (rotSt.equals("R")) return Rotation.R;
    else if (rotSt.equals("Rb")) return Rotation.Rb;
    else if (rotSt.equals("L")) return Rotation.L;
    else if (rotSt.equals("Lb")) return Rotation.Lb;
    else if (rotSt.equals("F")) return Rotation.F;
    else if (rotSt.equals("Fb")) return Rotation.Fb;
    else if (rotSt.equals("B")) return Rotation.B;
    else if (rotSt.equals("Bb")) return Rotation.Bb;
    else if (rotSt.equals("U")) return Rotation.U;
    else if (rotSt.equals("Ub")) return Rotation.Ub;
    else if (rotSt.equals("D")) return Rotation.D;
    else if (rotSt.equals("Db")) return Rotation.Db;
    else throw new Exception();
    
}

public void Scramble () throws Exception
{
    int randomNum = ThreadLocalRandom.current().nextInt(10, 30);

    RubikModel.Rotation aRotation;
    for (int i=0; i< randomNum; i++)
    {
        aRotation=randomRotation();
        Rotate(aRotation);
    }
}

public Rotation randomRotation() throws Exception
{
  int randomNum = ThreadLocalRandom.current().nextInt(1, 13);
  Rotation aRotation;
  switch (randomNum)
  {
      case 1: aRotation=Rotation.B;
      break;
      case 2: aRotation=Rotation.Bb;
      break;
      case 3: aRotation=Rotation.D;
      break;
      case 4: aRotation=Rotation.Db;
      break;
      case 5: aRotation=Rotation.F;
      break;
      case 6: aRotation=Rotation.Fb;
      break;
      case 7: aRotation=Rotation.L;
      break;
      case 8: aRotation=Rotation.Lb;
      break;
      case 9: aRotation=Rotation.R;
      break;
      case 10: aRotation=Rotation.Rb;
      break;
      case 11: aRotation=Rotation.U;
      break;
      case 12: aRotation=Rotation.Ub;
      break;
      default:
          throw new Exception();
  }
  return aRotation;
}

public boolean checkCubie( Color aFace, int i, int j)
{
    //we check in face with color aFace, the position i, j, to see if the right cubie is there
    //we need to check the color of the position and the other colors of the cubie, edge or corner
    //first check if the main face position is the right color:
    boolean checked=true;
    switch(aFace)
    {
        case Blue:
            checked=(YFront[i][j]==aFace);
            break;
        case Green:
            checked=(YBack[i][j]==aFace);
            break;
        case Red:
            checked=(XRight[i][j]==aFace);
            break;
        case Orange:
            checked=(XLeft[i][j]==aFace);
            break;
        case White:
            checked=(ZUp[i][j]==aFace);
            break;
        case Yellow:
            checked=(ZDown[i][j]==aFace);
            break;
        default:
            break;
    }
    if (!checked) return checked;
    
    //then we check the other colors of the cubie, the complex part
    switch(aFace)
    {
        //first edges Blue 0,1;1,0;1,2;2,1
        case Blue:
            if (i==0 && j==1) checked=(ZUp[2][1]==Color.White);
            else if (i==1 && j==0) checked=(XLeft[1][2]==Color.Red);
            else if (i==1 && j==2) checked=(XRight[1][0]==Color.Orange);
            else if (i==2 && j==1) checked=(ZDown[0][1]==Color.Yellow);
            //else corners       
            break;
        case White:
            if (i==0 && j==1) checked=(YBack[0][1]==Color.Green);
            else if (i==1 && j==0) checked=(XLeft[0][1]==Color.Red);
            else if (i==1 && j==2) checked=(XRight[0][1]==Color.Orange);
            else if (i==2 && j==1) checked=(YFront[0][1]==Color.Blue);
            //else corners   
            else if (i==0 && j==0) checked=(XLeft[0][0]==Color.Red && YBack[0][2]==Color.Green);
            else if (i==0 && j==2) checked=(XRight[0][2]==Color.Orange && YBack[0][0]==Color.Green);
            else if (i==2 && j==0) checked=(XLeft[0][2]==Color.Red && YFront[0][0]==Color.Blue);
            else if (i==2 && j==2) checked=(XRight[0][0]==Color.Orange && YFront[0][2]==Color.Blue);
            break;
        case Orange:
            if (i==0 && j==1) checked=(ZUp[1][2]==Color.White);
            else if (i==1 && j==0) checked=(YFront[1][2]==Color.Blue);
            else if (i==1 && j==2) checked=(YBack[1][0]==Color.Green);
            else if (i==2 && j==1) checked=(ZDown[1][2]==Color.Yellow);
            break;
            //else corners       
        case Red:
            if (i==0 && j==1) checked=(ZUp[1][0]==Color.White);
            else if (i==1 && j==0) checked=(YBack[1][2]==Color.Green);
            else if (i==1 && j==2) checked=(YFront[1][0]==Color.Blue);
            else if (i==2 && j==1) checked=(ZDown[1][0]==Color.Yellow);
            //else corners       
            break;
        case Green:
            if (i==0 && j==1) checked=(ZUp[0][1]==Color.White);
            else if (i==1 && j==0) checked=(XRight[1][2]==Color.Orange);
            else if (i==1 && j==2) checked=(XLeft[1][0]==Color.Red);
            else if (i==2 && j==1) checked=(ZDown[2][1]==Color.Yellow);
            //else corners       
            break;
        case Yellow:
            if (i==0 && j==1) checked=(YFront[2][1]==Color.Blue);
            else if (i==1 && j==0) checked=(XLeft[2][1]==Color.Red);
            else if (i==1 && j==2) checked=(XRight[2][1]==Color.Orange);
            else if (i==2 && j==1) checked=(YBack[2][1]==Color.Green);
            //else corners       
            break;
        default:
            break;
    }
    return checked;
    
    //PENDING REST OF FACES CORNERS!!! Maybe enough with Yellow (we consired corners always from White (Up) or yellow (Down) perspective
    
    //This method assumes rubik is in the original orientation, white up, Blue front, etc
    //we may need to complete also for rubik in different perspectives, so we need to check the center cubies of the face and near faces?
}

public EdgePosition locateEdge(Color main, Color b) throws Exception
{
    //we locate edge cubie, we want to place main Color in the main face in the right place (where b color matches the other face)
    //we look for main color, then check b color in the corresponding cubie, and return the main Face, i, j position, and b face and the i, j position
    //for simplicity we return just the main face (for edges ths b face is implicit)
    
    //edges positions are:
    //01,10,12,21 in all faces
    
    if (ZUp[0][1]==main && YBack[0][1]==b) return new EdgePosition(0,1,RubikModel.Face.Up);
    if (ZUp[1][0]==main && XLeft[0][1]==b) return new EdgePosition(1,0,RubikModel.Face.Up);
    if (ZUp[1][2]==main && XRight[0][1]==b) return new EdgePosition(1,2,RubikModel.Face.Up);
    if (ZUp[2][1]==main && YFront[0][1]==b) return new EdgePosition(2,1,RubikModel.Face.Up);
    
    if (ZDown[0][1]==main && YFront[2][1]==b) return new EdgePosition(0,1,RubikModel.Face.Down);
    if (ZDown[1][0]==main && XLeft[2][1]==b) return new EdgePosition(1,0,RubikModel.Face.Down);
    if (ZDown[1][2]==main && XRight[2][1]==b) return new EdgePosition(1,2,RubikModel.Face.Down);
    if (ZDown[2][1]==main && YBack[2][1]==b) return new EdgePosition(2,1,RubikModel.Face.Down);

    if (XRight[0][1]==main && ZUp[1][2]==b) return new EdgePosition(0,1,RubikModel.Face.Right);
    if (XRight[1][0]==main && YFront[1][2]==b) return new EdgePosition(1,0,RubikModel.Face.Right);
    if (XRight[1][2]==main && YBack[1][0]==b) return new EdgePosition(1,2,RubikModel.Face.Right);
    if (XRight[2][1]==main && ZDown[1][2]==b) return new EdgePosition(2,1,RubikModel.Face.Right);

    if (XLeft[0][1]==main && ZUp[1][0]==b) return new EdgePosition(0,1,RubikModel.Face.Left);
    if (XLeft[1][0]==main && YBack[1][2]==b) return new EdgePosition(1,0,RubikModel.Face.Left);
    if (XLeft[1][2]==main && YFront[1][0]==b) return new EdgePosition(1,2,RubikModel.Face.Left);
    if (XLeft[2][1]==main && ZDown[1][0]==b) return new EdgePosition(2,1,RubikModel.Face.Left);

    if (YFront[0][1]==main && ZUp[2][1]==b) return new EdgePosition(0,1,RubikModel.Face.Front);
    if (YFront[1][0]==main && XLeft[1][2]==b) return new EdgePosition(1,0,RubikModel.Face.Front);
    if (YFront[1][2]==main && XRight[1][0]==b) return new EdgePosition(1,2,RubikModel.Face.Front);
    if (YFront[2][1]==main && ZDown[0][1]==b) return new EdgePosition(2,1,RubikModel.Face.Front);

    if (YBack[0][1]==main && ZUp[0][1]==b) return new EdgePosition(0,1,RubikModel.Face.Back);
    if (YBack[1][0]==main && XRight[1][2]==b) return new EdgePosition(1,0,RubikModel.Face.Back);
    if (YBack[1][2]==main && XLeft[1][0]==b) return new EdgePosition(1,2,RubikModel.Face.Back);
    if (YBack[2][1]==main && ZDown[2][1]==b) return new EdgePosition(2,1,RubikModel.Face.Back);

    //no other case possible
    throw new Exception();
}
        
public CornerPosition locateCorner(Color main, Color b, Color c) throws Exception
{
    //understand we just need to find main color in a corner where b and c match the corner (no worry about where b and c are, there is only one posibility!)
    //in fact we do identify corners by the main color, so it is not the same (white, blue, orange) than (blue, white, orange)
    //corner position then is clearly a Face and i, j
    CornerPosition cp=new CornerPosition(0,0,Face.Up); //any corner position we set the right one on the method
    
    boolean found=false;
    //some checks are ot necessary ?
    //up
    if (ZUp[0][0]==main)
    {
        found= (XLeft[0][0]==b && YBack[0][2]==c || XLeft[0][0]==c && YBack[0][2]==b);
        if (found) 
        {
            cp.set(Face.Up, 0, 0);
        }           
    }
    if (!found && ZUp[0][2]==main)
    {
        found= (XRight[0][2]==b && YBack[0][0]==c || XRight[0][2]==c && YBack[0][0]==b);
        if (found) 
        {
            cp.set(Face.Up, 0, 2);
        }           
    }
    if (!found && ZUp[2][0]==main)
    {
        found= (XLeft[0][2]==b && YFront[0][0]==c || XLeft[0][2]==c && YFront[0][0]==b);
        if (found) 
        {
            cp.set(Face.Up, 2, 0);
        }           
    }
    if (!found && ZUp[2][2]==main)
    {
        found= (XRight[0][0]==b && YFront[0][2]==c || XRight[0][0]==c && YFront[0][2]==b);
        if (found) 
        {
            cp.set(Face.Up, 2, 2);
        }           
    }
    //down
    if (!found && ZDown[0][0]==main)
    {
        found= (XLeft[2][2]==b && YFront[2][0]==c || XLeft[2][2]==c && YFront[2][0]==b);
        if (found) 
        {
            cp.set(Face.Down, 0, 0);
        }           
    }
    if (!found && ZDown[0][2]==main)
    {
        found= (XRight[2][0]==b && YFront[2][2]==c || XRight[2][0]==c && YFront[2][2]==b);
        if (found) 
        {
            cp.set(Face.Down, 0, 2);
        }           
    }
    if (!found && ZDown[2][0]==main)
    {
        found= (XLeft[2][0]==b && YBack[2][2]==c || XLeft[2][0]==c && YBack[2][2]==b);
        if (found) 
        {
            cp.set(Face.Down, 2, 0);
        }           
    }
    if (!found && ZDown[2][2]==main)
    {
        found= (XRight[2][2]==b && YBack[2][0]==c || XRight[2][2]==c && YBack[2][0]==b);
        if (found) 
        {
            cp.set(Face.Down, 2, 2);
        }            
    }
    //right
    if (!found && XRight[0][0]==main)
    {
        found= (ZUp[2][2]==b && YFront[0][2]==c || ZUp[2][2]==c && YFront[0][2]==b);
        if (found) 
        {
            cp.set(Face.Right, 0, 0);
        }           
    }
    if (!found && XRight[0][2]==main)
    {
        found= (ZUp[0][2]==b && YBack[0][0]==c || ZUp[0][2]==c && YBack[0][0]==b);
        if (found) 
        {
            cp.set(Face.Right, 0, 2);
        }           
    }
    if (!found && XRight[2][0]==main)
    {
        found= (ZDown[0][2]==b && YFront[2][2]==c || ZDown[0][2]==c && YFront[2][2]==b);
        if (found) 
        {
            cp.set(Face.Right, 2, 0);
        }           
    }
    if (!found && XRight[2][2]==main)
    {
        found= (ZDown[2][2]==b && YBack[2][0]==c || ZDown[2][2]==c && YBack[2][0]==b);
        if (found) 
        {
            cp.set(Face.Right, 2, 2);
        }           
    }
    //left
    if (!found && XLeft[0][0]==main)
    {
        found= (ZUp[0][0]==b && YBack[0][2]==c || ZUp[0][0]==c && YBack[0][2]==b);
        if (found) 
        {
            cp.set(Face.Left, 0, 0);
        }           
    }
    if (!found && XLeft[0][2]==main)
    {
        found= (ZUp[2][0]==b && YFront[0][0]==c || ZUp[2][0]==c && YFront[0][0]==b);
        if (found) 
        {
            cp.set(Face.Left, 0, 2);
        }           
    }
    if (!found && XLeft[2][0]==main)
    {
        found= (ZDown[2][0]==b && YBack[2][2]==c || ZDown[2][0]==c && YBack[2][2]==b);
        if (found) 
        {
            cp.set(Face.Left, 2, 0);
        }           
    }
    if (!found && XLeft[2][2]==main)
    {
        found= (ZDown[0][0]==b && YFront[2][0]==c || ZDown[0][0]==c && YFront[2][0]==b);
        if (found) 
        {
            cp.set(Face.Left, 2, 2);
        }           
    }
    //front
    if (!found && YFront[0][0]==main)
    {
        found= (ZUp[2][0]==b && XLeft[0][2]==c || ZUp[2][0]==c && XLeft[0][2]==b);
        if (found) 
        {
            cp.set(Face.Front, 0, 0);
        }           
    }
    if (!found && YFront[0][2]==main)
    {
        found= (ZUp[2][2]==b && XRight[0][0]==c || ZUp[2][2]==c && XRight[0][0]==b);
        if (found) 
        {
            cp.set(Face.Front, 0, 2);
        }           
    }
    if (!found && YFront[2][0]==main)
    {
        found= (ZDown[0][0]==b && XLeft[2][2]==c || ZDown[0][0]==c && XLeft[2][2]==b);
        if (found) 
        {
            cp.set(Face.Front, 2, 0);
        }           
    }
    if (!found && YFront[2][2]==main)
    {
        found= (ZDown[0][2]==b && XRight[2][0]==c || ZDown[0][2]==c && XRight[2][0]==b);
        if (found) 
        {
            cp.set(Face.Front, 2, 2);
        }           
    }
    //back
    if (!found && YBack[0][0]==main)
    {
        found= (ZUp[0][2]==b && XRight[0][2]==c || ZUp[0][2]==c && XRight[0][2]==b);
        if (found) 
        {
            cp.set(Face.Back, 0, 0);
        }           
    }
    if (!found && YBack[0][2]==main)
    {
        found= (ZUp[0][0]==b && XLeft[0][0]==c || ZUp[0][0]==c && XLeft[0][0]==b);
        if (found) 
        {
            cp.set(Face.Back, 0, 2);
        }           
    }
    if (!found && YBack[2][0]==main)
    {
        found= (ZDown[2][2]==b && XRight[2][2]==c || ZDown[2][2]==c && XRight[2][2]==b);
        if (found) 
        {
            cp.set(Face.Back, 2, 0);
        }           
    }
    if (!found && YBack[2][2]==main)
    {
        found= (ZDown[2][0]==b && XLeft[2][0]==c || ZDown[2][0]==c && XLeft[2][0]==b);
        if (found) 
        {
            cp.set(Face.Back, 2, 2);
        }           
    }
    if (!found) throw new Exception(main.toString()+" "+b.toString()+" "+c.toString() 
            + "\nLeft " + printRubikFace(XLeft)
            + "\nRight " + printRubikFace(XRight)
            + "\nFront" + printRubikFace(YFront)
            + "\nBack" + printRubikFace(YBack)
            + "\nUp" + printRubikFace(ZUp)
            + "\nDown" + printRubikFace(ZDown)
    );
    return cp;
}

public void solve() throws Exception
{
    upCross();
    layer1();
    layer2();
    downCross();
    downPlaceEdges();
    downCorners();
    downPlaceCorners();
}

public void upCross() throws Exception
{
    upCross_WhiteBlueEdge();
    upCross_WhiteOrangeEdge();
    upCross_WhiteGreenEdge();
    upCross_WhiteRedEdge();
}

public void upCross_WhiteBlueEdge() throws Exception
{
    //recursive, we place the white-blue edge cubie from any of the direct positions, Right 1 0, Left 1 2, Up 1 0, Up 1 2
    //then from 2 distance edges we reach 1 distance edge, e.g. from Up 0 1, approach to Up 1 2 (or Up 1 0) then recursive
    
    EdgePosition pos;
    RubikModel.Rotation rot;

    if (!checkCubie(RubikModel.Color.White, 2, 1))
        // white blue edge
    {
        pos=locateEdge(RubikModel.Color.White, RubikModel.Color.Blue);
        if (pos.aFace==RubikModel.Face.Up)
        {
            if (pos.i==1 && pos.j==2) 
            {
                rot=RubikModel.Rotation.U;
                Rotate(rot);
            }
            else if (pos.i==0 && pos.j==1) 
            {
                //RubikModel.Rotation.U
                //RubikModel.Rotation.U
                //recursive better
                rot=RubikModel.Rotation.U;
                Rotate(rot);
                upCross_WhiteBlueEdge();
            }
            else // pos 1,0
            {
                rot=RubikModel.Rotation.Ub;
                Rotate(rot);
                //or equivalent
                //RubikModel.Rotation.U
                //RubikModel.Rotation.U
                //RubikModel.Rotation.U
                //recursive better
                //rot=RubikModel.Rotation.U;
                //Rotate(rot);
                //seq=upCross_WhiteBlueEdge(seq);
            }
            //no need to check 2,1
        }
        if (pos.aFace==RubikModel.Face.Down)
        {
            if (pos.i==1 && pos.j==2) 
            {
                //RubikModel.Rotation.R
                //RubikModel.Rotation.R
                //RubikModel.Rotation.U
                //better recursive
                Rotate(RubikModel.Rotation.R);
                upCross_WhiteBlueEdge();
                
            }
            else if (pos.i==0 && pos.j==1) 
            {
                //Rotate(RubikModel.Rotation.F);
                //Rotate(RubikModel.Rotation.F);
                //better recursive
                Rotate(RubikModel.Rotation.F);
                upCross_WhiteBlueEdge();
            }
            else if (pos.i==1 && pos.j==0)
            {
                //Rotate(RubikModel.Rotation.L);
                //Rotate(RubikModel.Rotation.L);
                //Rotate(RubikModel.Rotation.Ub);
                //better recursive
                Rotate(RubikModel.Rotation.L);
                upCross_WhiteBlueEdge();
            }
            else if (pos.i==2 && pos.j==1)
            {
                //Rotate(RubikModel.Rotation.B);
                //Rotate(RubikModel.Rotation.B);
                //Rotate(RubikModel.Rotation.U);
                //Rotate(RubikModel.Rotation.U);
                //better recursive
                Rotate(RubikModel.Rotation.B);
                upCross_WhiteBlueEdge();
            }
        }
        if (pos.aFace==RubikModel.Face.Right)
        {
            if (pos.i==1 && pos.j==2) 
            {
                //Rotate(RubikModel.Rotation.B);
                //Rotate(RubikModel.Rotation.U);
                //Rotate(RubikModel.Rotation.U);
                //better recursive
                Rotate(RubikModel.Rotation.B);
                upCross_WhiteBlueEdge();
                
            }
            else if (pos.i==0 && pos.j==1) 
            {
                //Rotate(RubikModel.Rotation.Rb);
                //Rotate(RubikModel.Rotation.Fb);
                //better recursive
                Rotate(RubikModel.Rotation.Rb);
                upCross_WhiteBlueEdge();
            }
            else if (pos.i==1 && pos.j==0)
            {
                Rotate(RubikModel.Rotation.Fb);
            }
            else if (pos.i==2 && pos.j==1)
            {
                //Rotate(RubikModel.Rotation.R);
                //Rotate(RubikModel.Rotation.Fb);
                //better recursive
                Rotate(RubikModel.Rotation.R);
                upCross_WhiteBlueEdge();
            }
        }
        if (pos.aFace==RubikModel.Face.Left)
        {
            if (pos.i==1 && pos.j==2) 
            {
                Rotate(RubikModel.Rotation.F);
                
            }
            else if (pos.i==0 && pos.j==1) 
            {
                //Rotate(RubikModel.Rotation.L);
                //Rotate(RubikModel.Rotation.F);
                //better recursive
                Rotate(RubikModel.Rotation.L);
                upCross_WhiteBlueEdge();
            }
            else if (pos.i==1 && pos.j==0)
            {
                //Rotate(RubikModel.Rotation.Bb);
                //Rotate(RubikModel.Rotation.U);
                //Rotate(RubikModel.Rotation.U);
                //better recursive
                Rotate(RubikModel.Rotation.Bb);
                upCross_WhiteBlueEdge();
            }
            else if (pos.i==2 && pos.j==1)
            {
                //Rotate(RubikModel.Rotation.L);
                //Rotate(RubikModel.Rotation.F);
                //better recursive
                Rotate(RubikModel.Rotation.L);
                upCross_WhiteBlueEdge();
            }
        }
        if (pos.aFace==RubikModel.Face.Front)
        {
            if (pos.i==1 && pos.j==2) 
            {
                //Rotate(RubikModel.Rotation.R);
                //Rotate(RubikModel.Rotation.Ub);
                //better recursive
                Rotate(RubikModel.Rotation.R);
                upCross_WhiteBlueEdge();
                
            }
            else if (pos.i==0 && pos.j==1) 
            {
                //Rotate(RubikModel.Rotation.F);
                //Rotate(RubikModel.Rotation.R);
                //Rotate(RubikModel.Rotation.U);
                //or
                //RubikModel.Rotation.Fb
                //RubikModel.Rotation.Lb
                //RubikModel.Rotation.Ub
                //better recursive
                Rotate(RubikModel.Rotation.F);
                upCross_WhiteBlueEdge();
            }
            else if (pos.i==1 && pos.j==0)
            {

                //Rotate(RubikModel.Rotation.L);
                //Rotate(RubikModel.Rotation.Ub);
                //better recursive
                Rotate(RubikModel.Rotation.L);
                upCross_WhiteBlueEdge();
            }
            else if (pos.i==2 && pos.j==1)
            {
                //Rotate(RubikModel.Rotation.Fb);
                //Rotate(RubikModel.Rotation.R);
                //Rotate(RubikModel.Rotation.U);
                //or equivalent
                //RubikModel.Rotation.F
                //RubikModel.Rotation.Lb
                //RubikModel.Rotation.Ub                
                //better recursive
                Rotate(RubikModel.Rotation.Fb);
                upCross_WhiteBlueEdge();

            }
        }
        if (pos.aFace==RubikModel.Face.Back)
        {
            if (pos.i==1 && pos.j==2) 
            {
                //Rotate(RubikModel.Rotation.L);
                //Rotate(RubikModel.Rotation.Ub);
                //better recursive
                Rotate(RubikModel.Rotation.L);
                upCross_WhiteBlueEdge();
                
            }
            else if (pos.i==0 && pos.j==1) 
            {
                //Rotate(RubikModel.Rotation.B);
                //Rotate(RubikModel.Rotation.Lb);
                //Rotate(RubikModel.Rotation.Ub);
                //or
                //RubikModel.Rotation.Bb
                //RubikModel.Rotation.Rb
                //RubikModel.Rotation.U
                //better recursive
                Rotate(RubikModel.Rotation.B);
                upCross_WhiteBlueEdge();
            }
            else if (pos.i==1 && pos.j==0)
            {
                //Rotate(RubikModel.Rotation.Rb);
                //Rotate(RubikModel.Rotation.Ub);
                //better recursive
                Rotate(RubikModel.Rotation.Rb);
                upCross_WhiteBlueEdge();
            }
            else if (pos.i==2 && pos.j==1)
            {
                //Rotate(RubikModel.Rotation.B);
                //Rotate(RubikModel.Rotation.Rb);
                //Rotate(RubikModel.Rotation.Ub);
                //or
                //RubikModel.Rotation.Bb
                //RubikModel.Rotation.L
                //RubikModel.Rotation.Ub
                                //better recursive
                Rotate(RubikModel.Rotation.B);
                upCross_WhiteBlueEdge();

            }
        }
        
    }


}

public void upCross_WhiteOrangeEdge() throws Exception
{
    //restriction we can not move F or Fb as we remove white-blue
    //recursive, we place the white-orange edge cubie from any of the direct positions, Right 1 0, Left 1 2, Up 1 0, Up 1 2
    //then from 2 distance edges we reach 1 distance edge, e.g. from Up 0 1, approach to Up 1 2 (or Up 1 0) then recursive
    
    EdgePosition pos;
    RubikModel.Rotation rot;

    if (!checkCubie(RubikModel.Color.White, 1, 2))
        // white Orange edge
    {
        pos=locateEdge(RubikModel.Color.White, RubikModel.Color.Orange);
        if (pos.aFace==RubikModel.Face.Front)
        // possible 1,0; 1,2, or 2,1 as 0,1 is white blue laready placed
        {
            //we can not rotate front, as we are restricted by white blue edge already placed
            if (pos.i==1 && pos.j==2) 
            {
                rot=RubikModel.Rotation.R;
                Rotate(rot);
            }
            else if (pos.i==1 && pos.j==0) 
            {
                rot=RubikModel.Rotation.Lb;
                Rotate(rot);
                upCross_WhiteOrangeEdge();
            }
            else if (pos.i==2 && pos.j==1) 
            {
                rot=RubikModel.Rotation.D;
                //rot=RubikModel.Rotation.Db;
                //one way or the other
                Rotate(rot);
                upCross_WhiteOrangeEdge();
            }
        }
        else if (pos.aFace==RubikModel.Face.Back)
        // possible 1,0; 1,2, 2,1 ir 0,1
        {
            if (pos.i==1 && pos.j==0) 
            {
                rot=RubikModel.Rotation.Rb;
                Rotate(rot);
            }
            else if (pos.i==0 && pos.j==1) 
            {
                rot=RubikModel.Rotation.Bb;
                Rotate(rot);
                upCross_WhiteOrangeEdge();
            }
            else if (pos.i==1 && pos.j==2) 
            {
                rot=RubikModel.Rotation.Bb;
                Rotate(rot);
                upCross_WhiteOrangeEdge();
            }
            else if (pos.i==2 && pos.j==1) 
            {
                rot=RubikModel.Rotation.B;
                Rotate(rot);
                upCross_WhiteOrangeEdge();
            }
        }
        else if (pos.aFace==RubikModel.Face.Up)
        // possible 1,0 or 0,1, as 1,2 is the right one and 2,1 is white blue laready placed
        {
            //we can not rotate up, as we are restricted by white blue edge already placed
            if (pos.i==1 && pos.j==0) 
            {
                rot=RubikModel.Rotation.Lb;
                Rotate(rot);
                upCross_WhiteOrangeEdge();
            }
            else if (pos.i==0 && pos.j==1) 
            {
                rot=RubikModel.Rotation.Bb;
                Rotate(rot);
                upCross_WhiteOrangeEdge();
            }
        }
        else if (pos.aFace==RubikModel.Face.Down)
        // possible 1,0; 1,2, 2,1 ir 0,1
        {
            //we can not rotate front, as we are restricted by white blue edge already placed
            if (pos.i==0 && pos.j==1) 
            {
                rot=RubikModel.Rotation.D;
                Rotate(rot);
                upCross_WhiteOrangeEdge();
            }
            else if (pos.i==1 && pos.j==0) 
            {
                rot=RubikModel.Rotation.D;
                Rotate(rot);
                upCross_WhiteOrangeEdge();
            }
            else if (pos.i==1 && pos.j==2) 
            {
                rot=RubikModel.Rotation.Rb;
                Rotate(rot);
                upCross_WhiteOrangeEdge();
            }
            else if (pos.i==2 && pos.j==1) 
            {
                rot=RubikModel.Rotation.Db;
                Rotate(rot);
                upCross_WhiteOrangeEdge();
            }
        }
        else if (pos.aFace==RubikModel.Face.Left)
        // possible 1,0; 1,2, 2,1 ir 0,1
        {
            //we can not rotate front, as we are restricted by white blue edge already placed
            if (pos.i==0 && pos.j==1) 
            {
                rot=RubikModel.Rotation.L;
                Rotate(rot);
                upCross_WhiteOrangeEdge();
            }
            else if (pos.i==1 && pos.j==0) 
            {
                rot=RubikModel.Rotation.Lb;
                Rotate(rot);
                upCross_WhiteOrangeEdge();
            }
            else if (pos.i==1 && pos.j==2) 
            {
                rot=RubikModel.Rotation.L;
                Rotate(rot);
                upCross_WhiteOrangeEdge();
            }
            else if (pos.i==2 && pos.j==1) 
            {
                rot=RubikModel.Rotation.Db;
                Rotate(rot);
                upCross_WhiteOrangeEdge();
            }
        }
        //else is enough, still to debug, check
        else if (pos.aFace==RubikModel.Face.Right)
        // possible 1,0; 1,2, 2,1 ir 0,1
        {
            //we can not rotate front, as we are restricted by white blue edge already placed
            if (pos.i==0 && pos.j==1) 
            {
                rot=RubikModel.Rotation.R;
                Rotate(rot);
                upCross_WhiteOrangeEdge();
            }
            else if (pos.i==1 && pos.j==0) 
            {
                rot=RubikModel.Rotation.Rb;
                Rotate(rot);
                upCross_WhiteOrangeEdge();
            }
            else if (pos.i==1 && pos.j==2) 
            {
                rot=RubikModel.Rotation.R;
                Rotate(rot);
                upCross_WhiteOrangeEdge();
            }
            else if (pos.i==2 && pos.j==1) 
            {
                rot=RubikModel.Rotation.D;
                Rotate(rot);
                upCross_WhiteOrangeEdge();
            }
        } 
        }

}

public void upCross_WhiteGreenEdge() throws Exception
{
    //restriction we can not move F or Fb as we remove white-blue or R or Rb as we remove WhiteOrange
    // so we need to do a complex combination
    
    EdgePosition pos;
    RubikModel.Rotation rot;

    if (!checkCubie(RubikModel.Color.White, 0, 1))
        // in case edge not already placed
    {
        int d;
        pos=locateEdge(RubikModel.Color.White, RubikModel.Color.Green);
        switch (pos.aFace)
        {
            case Right: 
            case Left: 
            case Front:
            case Back:
                //we may need to align up face to bring white green edge up from middle layer
                //best case, it is in Right 1 2, we run B
                //symatric best case, it is in Left 1 0, we run Bb
                // other is Ub n times - B equivalent (permutation in the Right of Right n times) - U (reverse) n times
                // or symetric case
                if (pos.i ==1 && pos.j==2)
                {
                    d=Distance( Face.Back, pos.aFace, RotationCube.Z );
                    d++;
                    //the sample rotation in B from Right, at distance 1, so we increase d in 1
                    if (d==4) d=0;
                    for (int i=0; i<d; i++)
                    {
                        Rotate(Rotation.Ub);                    
                    }
                    Rotate(Permutation(d,RotationCube.Z,Rotation.B));
                    for (int i=0; i<d; i++)
                    {
                        Rotate(Rotation.U);                    
                    }
                }
                else if (pos.i==1 && pos.j==0)
                {
                    d=Distance(Face.Back, pos.aFace, RotationCube.Z );
                    d--;
                    //the sample rotation in Bb from Left, at distance 1, so we decrease d in 1
                    if (d==-1) d=3;
                    for (int i=0; i<d; i++)
                    {
                        Rotate(Rotation.Ub);                    
                    }
                    Rotate(Permutation(d,RotationCube.Z,Rotation.Bb));
                    for (int i=0; i<d; i++)
                    {
                        Rotate(Rotation.U);                    
                    }
                }
                // other cases, edge is not in layer2
                else if (pos.i==0)
                //possible only in Left or Back, as up-front (white-blue) and up-right (white-orange) are placed
                {
                    //move edge to layer 2, then recursive place from layer2
                    //in back face "B" is valid 
                    //in left face "L" is valid
                    d=Distance(Face.Back, pos.aFace, RotationCube.Z );
                    Rotate(Permutation(d,RotationCube.Z,Rotation.B));
                    //the sample rotation in B from Back, at distance 0, other faces, we permute n times 
                    upCross_WhiteGreenEdge();                
                }
                else if (pos.i==2)
                {
                    d=Distance(Face.Back, pos.aFace, RotationCube.Z );
                    //we move edge to 2,1 in Back
                    for (int i=0; i<d; i++)
                    {
                        Rotate(Rotation.D);                    
                    }                    
                    //then it is secure to move it to layer 2 with Bb
                    Rotate(Rotation.Bb);
                    // and solve recursively
                    upCross_WhiteGreenEdge();                
                }
                break;
            case Up:
                //we move to layer 2 then recursive
                //only one case, otherwise is already in its place, as white blue (up front) and and up-right (white-orange) are also placed
                if (pos.i==1 && pos.j==0) Rotate(Rotation.Lb);
                upCross_WhiteGreenEdge();                
                break;
            case Down:
                //we move to layer 2, carefully, we do not want to disorder up face
                if (pos.i==1 && pos.j==0) 
                {
                    Rotate(Rotation.L);
                    upCross_WhiteGreenEdge();                
                }
                if (pos.i==2) //redundant pos.j=1, no other edge in i=2 (2,1)
                {
                    Rotate(Rotation.B);
                    upCross_WhiteGreenEdge();                
                }
                // in these cases below, we need to avoid disordering up face, so we move down first (once or recursively twice) then recursively solve it
                if ((pos.i==1 && pos.j==2) 
                        ||
                    (pos.i==0)) //redundant pos.j=1, no other edge in i=0 (0,1)
                {
                    Rotate(Rotation.D);
                    upCross_WhiteGreenEdge();                
                }
                break;
        }
        
    };
    
}

public void upCross_WhiteRedEdge() throws Exception
{
    //restriction we can not move F or Fb as we remove white-blue or R or Rb as we remove WhiteOrange, B or Bb as we remove WhiteGreen
    // so we need to do a complex combination
    
    EdgePosition pos;
    RubikModel.Rotation rot;

    if (!checkCubie(RubikModel.Color.White, 1, 0))
        // in case edge not already placed
    {
        int d;
        pos=locateEdge(RubikModel.Color.White, RubikModel.Color.Red);
        switch (pos.aFace)
        {
            case Right: 
            case Left: 
            case Front:
            case Back:
                //we may need to align up face to bring white green edge up from middle layer
                //best case, it is in Front 0 1, we run Lb
                //symatric best case, it is in Back 1 2, we run L
                // other is Ub n times - Lb equivalent (permutation in the Right of Right n times) - U (reverse) n times
                // or symetric case
                if (pos.i ==1 && pos.j==2)
                {
                    d=Distance( Face.Left, pos.aFace, RotationCube.Z );
                    d++;
                    //the sample rotation in L from Front, at distance 1, so we increase d in 1
                    if (d==4) d=0;
                    for (int i=0; i<d; i++)
                    {
                        Rotate(Rotation.Ub);                    
                    }
                    Rotate(Permutation(d,RotationCube.Z,Rotation.L));
                    for (int i=0; i<d; i++)
                    {
                        Rotate(Rotation.U);                    
                    }
                }
                else if (pos.i==1 && pos.j==0)
                {
                    d=Distance(Face.Left, pos.aFace, RotationCube.Z );
                    d--;
                    //the sample rotation in Bb from Left, at distance 1, so we decrease d in 1
                    if (d==-1) d=3;
                    for (int i=0; i<d; i++)
                    {
                        Rotate(Rotation.Ub);                    
                    }
                    Rotate(Permutation(d,RotationCube.Z,Rotation.Lb));
                    for (int i=0; i<d; i++)
                    {
                        Rotate(Rotation.U);                    
                    }
                }
                // other cases, edge is not in layer2
                else if (pos.i==0)
                //possible only in Left as up-front (white-blue) and up-right (white-orange) and up-back (white-green) are placed
                {
                    //move edge to layer 2, then recursive place from layer2
                    // L or Lb
                    Rotate(Rotation.L);
                    upCross_WhiteRedEdge();                
                    //if it was possible in other faces, it will be as below:
                    //d=Distance(Face.Left, pos.aFace, RotationCube.Z );
                    //Rotate(Permutation(d,RotationCube.Z,Rotation.L));
                    //upCross_WhiteRedEdge();                
                }
                else if (pos.i==2)
                {
                    d=Distance(Face.Left, pos.aFace, RotationCube.Z );
                    //we move edge to 2,1 in Back
                    for (int i=0; i<d; i++)
                    {
                        Rotate(Rotation.D);                    
                    }                    
                    //then it is secure to move it to layer 2 with Bb
                    Rotate(Rotation.L);
                    // and solve recursively
                    upCross_WhiteRedEdge();                
                }
                break;
            case Up:
                //not possible
                throw new Exception();                
                /*throw new Exception(
                pos.aFace.toString()+String.format(" i %d , j %d", pos.i, pos.j)
                + "\nLeft " + printRubikFace(XLeft)
                + "\nRight " + printRubikFace(XRight)
                + "\nFront" + printRubikFace(YFront)
                + "\nBack" + printRubikFace(YBack)
                + "\nUp" + printRubikFace(ZUp)
                + "\nDown" + printRubikFace(ZDown));*/
            case Down:
                //we move to layer 2, carefully, we do not want to disorder up face, from Down 1 0, moving L or Lb
                if (pos.i==1 && pos.j==0) 
                {
                    Rotate(Rotation.L);
                    upCross_WhiteRedEdge();                
                }
                else
                //all other cases we move to 1 0 recursively
                {
                    Rotate(Rotation.D);
                    upCross_WhiteRedEdge();                
                }
                break;
        }
        
    };
    
}

public void layer1() throws Exception
{
    //we have white cross up, now we place corners in up face to build layer1
    //we first check if any corner is already placed
    //then check if any corner is in down layer, this is very easy to place
    //locateCorner, if layer=2, then placeLayer2Corner()
    
    ArrayList<CornerCubie> whiteCorners =new ArrayList<CornerCubie>();
    //the 4 corners in white (up) face
    RubikModel.Rotation rot;
    CornerCubie corner;
    
    //we create a list with corners not placed already
    if (!checkCubie(Color.White, 0, 0))
    {
        whiteCorners.add(new CornerCubie(locateCorner(Color.White, Color.Red, Color.Green), OrigRubik.locateCorner(Color.White, Color.Red, Color.Green), Color.White, Color.Red, Color.Green));
    }
    if (!checkCubie(Color.White, 0, 2))
    {
        whiteCorners.add(new CornerCubie(locateCorner(Color.White, Color.Orange, Color.Green), OrigRubik.locateCorner(Color.White, Color.Orange, Color.Green), Color.White, Color.Orange, Color.Green));
    }
    if (!checkCubie(Color.White, 2, 0))
    {
        whiteCorners.add(new CornerCubie(locateCorner(Color.White, Color.Red, Color.Blue),OrigRubik.locateCorner(Color.White, Color.Red, Color.Blue), Color.White, Color.Red, Color.Blue));
    }
    if (!checkCubie(Color.White, 2, 2))
    {
        whiteCorners.add(new CornerCubie(locateCorner(Color.White, Color.Orange, Color.Blue), OrigRubik.locateCorner(Color.White, Color.Orange, Color.Blue), Color.White, Color.Orange, Color.Blue));
    }
    
    //we place easy corners in layer2
    //if we place one, we recursively call layer1(), as the corners have changed positions, and we will look again for easy corners in the new rubik
    //if we do not place an easy one, we try with a difficult one, then we call recursively layer1() to start with the next easy one ...
    boolean found=false;
    for (int i=0; i<whiteCorners.size() && !found ; i++)
    {
        corner=whiteCorners.get(i);
        switch (corner.pos.aFace)
        {
            case Up:
            case Down:
                break;
            default:
                if (corner.pos.i==2)
                {
                        placeWhiteCornerFromLayer2(corner);
                        found=true;
                }
                break;
        }
    }
    
    if (found) 
    {
        if (whiteCorners.size()>1)
        {
            layer1();
            //recursive, to try with new rubik position after placing one easy corner
        }
        //else we just placed last corner!
    }
    else //we do not have an easy one
    {
        if (whiteCorners.size() > 0)
        // is there any corner to place?
        {
            //non easy corners, up or down or layer 1
            corner=whiteCorners.get(0); //get first remaining
            int permutationDistance=0;
            int downToTarget=0;
            switch(corner.pos.aFace)
            {
                case Up:
                    //assume model up 0 0, we do B, D, Bb, to place it in layer 2, then recursively solve
                    //we can do B D Bb, as we know where the wrong corner is, so we know we do not break other cubie in up
                    if (corner.pos.i==0 && corner.pos.j==0)                         
                        permutationDistance=0;
                    else if (corner.pos.i==0 && corner.pos.j==2) 
                    {
                        permutationDistance=1;
                    }
                    else if (corner.pos.i==2 && corner.pos.j==2) 
                    {
                        permutationDistance=2;
                    }
                    else if (corner.pos.i==2 && corner.pos.j==0) 
                    {
                        permutationDistance=3;
                    }
                    Rotate(Permutation(permutationDistance, RotationCube.Zb, Rotation.B));
                    Rotate(Rotation.D);
                    Rotate(Permutation(permutationDistance, RotationCube.Zb, Rotation.Bb));
                    layer1();
                    break;
                case Down:
                    //we need to know where to place it down, to do the rotation which does not break anything up
                    //we place it down in the opposite place from where it has to be placed up
                    // model target pos is up 0 0, so we move to down 2 0, then Lb D L, place it in layer 2 and recursively solve
                    if (corner.origPos.i == 0 && corner.origPos.j ==0)
                    {
                        permutationDistance=0;
                    }
                    else if (corner.origPos.i == 0 && corner.origPos.j ==2)
                    {
                        permutationDistance=1;

                    }
                    else if (corner.origPos.i == 2 && corner.origPos.j ==2)
                    {
                        permutationDistance=2;
                    }
                    else if (corner.origPos.i == 2 && corner.origPos.j ==0)
                    {
                        permutationDistance=3;
                    }
                    
                    if (corner.pos.i == 2 && corner.pos.j == 0 )
                    {
                        downToTarget=0;
                    }
                    else if (corner.pos.i == 2 && corner.pos.j == 2 )
                    {
                        downToTarget=1;
                    }
                    else if (corner.pos.i == 0 && corner.pos.j == 2 )
                    {
                        downToTarget=2;
                    }
                    else if (corner.pos.i == 0 && corner.pos.j == 0 )
                    {
                        downToTarget=3;
                    }
                    downToTarget=(4+downToTarget-permutationDistance) % 4;
                    for (int i=0; i<downToTarget; i++)
                    {
                        Rotate(Rotation.D);
                    }
                    Rotate(Permutation(permutationDistance,RotationCube.Zb, Rotation.Lb));
                    Rotate(Rotation.D);
                    Rotate(Permutation(permutationDistance,RotationCube.Zb, Rotation.L));
                    layer1();
                    break;
                default:
                    //i==0 in Left, Rigth, Front, Back
                    //we need to rotate the face where it is placed
                    Rotation aRot;
                    switch(corner.pos.aFace)
                    {
                            case Back:
                                permutationDistance=0;
                                break;
                            case Right:
                                permutationDistance=1;
                                break;
                            case Front:
                                permutationDistance=2;
                                break;
                            case Left:
                                permutationDistance=3;
                                break;
                    }
                    if (corner.pos.j==2)
                    {
                        Rotate(Permutation(permutationDistance, RotationCube.Zb, Rotation.B));
                        Rotate(Rotation.D);
                        Rotate(Permutation(permutationDistance, RotationCube.Zb, Rotation.Bb));
                    }
                    else
                    //j==0
                    {
                        Rotate(Permutation(permutationDistance, RotationCube.Zb, Rotation.Bb));
                        Rotate(Rotation.D);
                        Rotate(Permutation(permutationDistance, RotationCube.Zb, Rotation.B));
                    }
                    layer1();
                    break;
            }
            
        }
    }
    
}

public void placeWhiteCornerFromLayer2(CornerCubie corner) throws Exception
{
    // 2 cases, j 0 or 2, we place them with different rotations
    RubikModel.Face targetFace;
    int permutationDistance;
    int d;
    if (corner.pos.j==0)
    {
        //example white green red, place it in Left 2 0, then L, Db, Lb
        //same symetric for other corners
        //so first we determine the layer 2 right position to start move
        permutationDistance=0;
        if (corner.origPos.i==0 && corner.origPos.j==0) targetFace=Face.Left;
        else if (corner.origPos.i==0 && corner.origPos.j==2) 
        {
            targetFace=Face.Back;
            permutationDistance=1;
        }
        else if (corner.origPos.i==2 && corner.origPos.j==2) 
        {
            targetFace=Face.Right;
            permutationDistance=2;
        }
        else if (corner.origPos.i==2 && corner.origPos.j==0) 
        {
            targetFace=Face.Front;
            permutationDistance=3;
        }
        else throw new Exception();
        d=Distance(corner.pos.aFace, targetFace, RotationCube.Z);
        for (int i=0; i<d; i++)
        {
            Rotate(Rotation.D);
        }
        Rotate(Permutation(permutationDistance, RotationCube.Zb, Rotation.Lb));
        //permutation distance is calculated from Left face anticlockwise
        Rotate(Rotation.Db);
        Rotate(Permutation(permutationDistance, RotationCube.Zb, Rotation.L));    
    }
    else if (corner.pos.j==2)
    //else is enough
    {
        //example white green red, place it in Back 2 2, then B, D, Bb
        permutationDistance=0;
        if (corner.origPos.i==0 && corner.origPos.j==0) targetFace=Face.Back;
        else if (corner.origPos.i==0 && corner.origPos.j==2) 
        {
            targetFace=Face.Right;
            permutationDistance=1;
        }
        else if (corner.origPos.i==2 && corner.origPos.j==2) 
        {
            targetFace=Face.Front;
            permutationDistance=2;
        }
        else if (corner.origPos.i==2 && corner.origPos.j==0) 
        {
            targetFace=Face.Left;
            permutationDistance=3;
        }
        else throw new Exception();
        d=Distance(corner.pos.aFace, targetFace, RotationCube.Z);
        for (int i=0; i<d; i++)
        {
            Rotate(Rotation.D);
        }
        Rotate(Permutation(permutationDistance, RotationCube.Zb, Rotation.B));
        //permutation distance is calculated from Left face anticlockwise
        Rotate(Rotation.D);
        Rotate(Permutation(permutationDistance, RotationCube.Zb, Rotation.Bb));    
    }
    else throw new Exception();
}

public static Face[] Z_rotations= {Face.Front, Face.Right, Face.Back, Face.Left, Face.Front, Face.Right, Face.Back};
public static Face[] Zb_rotations ={Face.Front, Face.Left, Face.Back, Face.Right, Face.Front, Face.Left, Face.Back};
public static Face[] X_rotations ={Face.Front, Face.Down, Face.Back, Face.Up, Face.Front, Face.Down, Face.Back};
public static Face[] Xb_rotations ={Face.Front, Face.Up, Face.Back, Face.Down, Face.Front, Face.Up, Face.Back};
public static Face[] Y_rotations ={Face.Up, Face.Left, Face.Down, Face.Right, Face.Up, Face.Left, Face.Down};
public static Face[] Yb_rotations ={Face.Up, Face.Right, Face.Down, Face.Left, Face.Up, Face.Right, Face.Down};

public int Distance (Face f_layer2, Face f_layer1, RotationCube r) throws Exception
{
    int d=0;
    int i=0;
    int j=0;
    boolean found=false;
    switch (r)
    {
        case Z:
        {
            while (i<7 && !found)
            {
                if (Z_rotations[i]==f_layer2)
                {
                    while (j<4 && !found)
                    {
                        if (Z_rotations[i+j]==f_layer1)
                        {
                            found=true;
                        }
                        j++;
                    }
                }
                i++;
            }
            d=j-1;
            break;
        }
            
        case Zb:
        {
            while (i<7 && !found)
            {
                if (Zb_rotations[i]==f_layer2)
                {
                    while (j<3 && !found)
                    {
                        if (Zb_rotations[i+j]==f_layer1)
                        {
                            found=true;
                        }
                        j++;
                    }
                }
                i++;
            }
            d=j;
            break;
        }
        default:
            throw new Exception();
            //PENDING other faces
    }
    return d;
}

public ArrayList<Rotation> PermutationList(int n, RotationCube rc, ArrayList<Rotation> rl) throws Exception
{
    ArrayList<Rotation> rrl=new ArrayList<Rotation>();
    int i;
    for (i=0; i<rl.size(); i++)
    {
        rrl.add(Permutation(n, rc, rl.get(i)));
    }
    return rrl;
}


public Rotation Permutation(int n, RotationCube rc, Rotation r) throws Exception
{
    //retunrs the equivalent Rotation for r in the cube, if we apply the same r rotation from a different perspective, rotate n times the cube (whole) in rc rotation
    //example n=1, r=R rc=Z, returns B, as if we do R on a cube rotated on Z clockwise, we are really rotating B
    Rotation perm=r;
    if (n>0)
    //recursive for n rotations
    {
        switch (rc)
        {
            case Z:
            {
                switch(r)
                {
                    case R:
                        perm=Permutation(n-1,rc, Rotation.B);
                        break;
                    case B:
                        perm=Permutation(n-1,rc, Rotation.L);
                        break;
                    case L:
                        perm=Permutation(n-1,rc, Rotation.F);
                        break;
                    case F:
                        perm=Permutation(n-1,rc, Rotation.R);
                        break;
                    case Rb:
                        perm=Permutation(n-1,rc, Rotation.Bb);
                        break;
                    case Bb:
                        perm=Permutation(n-1,rc, Rotation.Lb);
                        break;
                    case Lb:
                        perm=Permutation(n-1,rc, Rotation.Fb);
                        break;
                    case Fb:
                        perm=Permutation(n-1,rc, Rotation.Rb);
                        break;
                        
                }
                break;
            }
            case Zb:
            {
                switch(r)
                {
                    case R:
                        perm=Permutation(n-1,rc, Rotation.F);
                        break;
                    case F:
                        perm=Permutation(n-1,rc, Rotation.L);
                        break;
                    case L:
                        perm=Permutation(n-1,rc, Rotation.B);
                        break;
                    case B:
                        perm=Permutation(n-1,rc, Rotation.R);
                        break;
                    case Rb:
                        perm=Permutation(n-1,rc, Rotation.Fb);
                        break;
                    case Fb:
                        perm=Permutation(n-1,rc, Rotation.Lb);
                        break;
                    case Lb:
                        perm=Permutation(n-1,rc, Rotation.Bb);
                        break;
                    case Bb:
                        perm=Permutation(n-1,rc, Rotation.Rb);
                        break;
                }
                break;
                        
            }
            default:
                throw new Exception();
                //PENDING other cube rotations

        }
    }
    return perm;
}



public void layer2() throws Exception
{
    //create a list of edges in layer 2
    //discard those edges already placed, If I am lucky :-)
    ArrayList<EdgeCubie> layer2edges= new ArrayList<EdgeCubie>(); 
    EdgePosition ep;
    EdgeCubie ec;
    if (!checkCubie(Color.Blue, 1,0)) layer2edges.add(new EdgeCubie(locateEdge(Color.Blue, Color.Red), OrigRubik.locateEdge(Color.Blue, Color.Red), Color.Blue, Color.Red));
    if (!checkCubie(Color.Blue, 1,2)) layer2edges.add(new EdgeCubie(locateEdge(Color.Blue, Color.Orange),OrigRubik.locateEdge(Color.Blue, Color.Orange),Color.Blue, Color.Orange));
    if (!checkCubie(Color.Green, 1,0)) layer2edges.add(new EdgeCubie(locateEdge(Color.Green, Color.Orange),OrigRubik.locateEdge(Color.Green, Color.Orange), Color.Green, Color.Orange));
    if (!checkCubie(Color.Green, 1,2)) layer2edges.add(new EdgeCubie(locateEdge(Color.Green, Color.Red),OrigRubik.locateEdge(Color.Green, Color.Red),Color.Green, Color.Red) );
    
    //check easy to place edges, those in layer 3 vs not easy, those in layer 2
    //and place it
    boolean found=false;
    for (int i=0; i<layer2edges.size() && !found; i++)
    {
        ec=layer2edges.get(i);
        ep=ec.pos;
        if (ep.aFace == Face.Down || ep.i==2) 
        // the easy position for an edge is down or in layer3 (i==2), please note an edge is identified by the main Face color
        // we chose Blue and Green as the main color, it is a convention, we could have chosen Red and Orange as well
        //e.g. blue/orange in down face is similar to orange/blue in layer2
        {
            placeLayer2Edge(ec);
            found=true;
            layer2(); //recursively place the remaining edges if any
        }
    }
    
    if (layer2edges.size() != 0 && !found) 
    {
        //there is no easy edge, so we have to extract the edges which are misplaced in the layer2 (i==1)
        //we get first in the list, and extract it (later recursively we will do the same for others in the same situation)
        extractLayer2Edge(layer2edges.get(0));
        layer2();
    }
    
}

public void placeLayer2Edge(EdgeCubie e) throws Exception
{
    // 2 pre-cases, cubie e main color is in layer 3 or main color is down
    // perspective, we place cubie in front face 1,2 - right 1,0
    // for the rest of the layer 2 edges, imagine we do the same rotation sequence, a permutation, 
    // applied in the face where the edge is in the position 1,2 as if was front face in this example
    // case 1)
    // precase, main color is in layer 3, and matches the front face color
    // or else, precase main color is down and matches the right face color
    // Db, Rb, D, R, D, F, Db, Fb
    // case 2)
    // precase, main color is in layer 3, and matches the right face color
    // or else, precase main color is down and matches the front face color
    // D, F, Db, Fb, Db, Rb , D, R
    
    ArrayList<Rotation> case1= new ArrayList<Rotation>();
    ArrayList<Rotation> case2= new ArrayList<Rotation>();
    case1.add(Rotation.Db);
    case1.add(Rotation.Rb);
    case1.add(Rotation.D);
    case1.add(Rotation.R);
    case1.add(Rotation.D);
    case1.add(Rotation.F);
    case1.add(Rotation.Db);
    case1.add(Rotation.Fb);
    
    case2.add(Rotation.D);
    case2.add(Rotation.F);
    case2.add(Rotation.Db);
    case2.add(Rotation.Fb);
    case2.add(Rotation.Db);
    case2.add(Rotation.Rb);
    case2.add(Rotation.D);
    case2.add(Rotation.R);

    //determine which is the case to apply
    //not all cases may happen, we have selected cubies with a specific main color, e.g. blue/orange, not orange/blue
    int d=Distance (ColorOrigFace(e.main),ColorOrigFace(e.b), RotationCube.Z);
    // d means if the main color is in the left of b or in the right, d==1 is in the left, d==3 is in the right
    int dPerm=0;
    // dPerm is the distance from the edge orig face with respect to Front face to determine the permutations to apply to rotation sequence 
    // as the rotation sequence is from a front face perspective
    int dPermb=0;
    // dPermb is the distance from the edge orig with respect to front face, similar to above, but when it is b color which is in layer3

    int dToHisFaceMain=0;
    //distance from edge position when main is in layer 3 (i==2 not in down face) to his face
    
    int dToHisFaceb=0;
    //distance from edge position when b is in layer 3 (i==2 not in down face, so main is down) to his face
    // not edge pos is for main, so we will need to get pos.aFace for b !!
    Face bFace=null; 
    // face where b color for the edge is located
            
    //case 1
    if (e.pos.aFace!=Face.Down) //i==2 is not right as it can be Down and i==2!!!
    {
        dPerm=Distance(Face.Front, ColorOrigFace(e.main), RotationCube.Z);
        dToHisFaceMain=Distance(e.pos.aFace, ColorOrigFace(e.main), RotationCube.Z);

        if (d==1)
        {
            //place it (main) in his face color, the equivalent to front face
            for (int j=0; j<dToHisFaceMain; j++)
            {
                Rotate(Rotation.D);
            }
            RotationSequence(PermutationList(dPerm, RotationCube.Z, case1));
        }
        else //d == 3 as main is the equivalent right face for the edge
        {
            //place it (main) in his face color, the equivalent to right face
            for (int j=0; j<dToHisFaceMain; j++)
            {
                Rotate(Rotation.D);
            }
            RotationSequence(PermutationList(dPerm, RotationCube.Z, case2));
        }
    }
    else // Down Face
    {
        if (e.pos.i ==0 && e.pos.j==1)
            bFace = Face.Front;
        else if (e.pos.i ==1 && e.pos.j==0)
            bFace = Face.Left;
        else if (e.pos.i ==1 && e.pos.j==2)
            bFace = Face.Right;
        else if (e.pos.i ==2 && e.pos.j==1)
            bFace = Face.Back;
            
        dPermb=Distance(Face.Front, ColorOrigFace(e.b), RotationCube.Z);
        dToHisFaceb=Distance(bFace, ColorOrigFace(e.b), RotationCube.Z);
        if (d==1)
        {
            //place it (b not main) in his face color, the equivalent to right face
            for (int j=0; j<dToHisFaceb; j++)
            {
                Rotate(Rotation.D);
            }
            RotationSequence(PermutationList(dPermb, RotationCube.Z, case2));
        }
        else //d == 3 as b (not main) is the equivalent main face for the edge
        {
            //place it (b) in his face color, the equivalent to main face
            for (int j=0; j<dToHisFaceb; j++)
            {
                Rotate(Rotation.D);
            }
            RotationSequence(PermutationList(dPermb, RotationCube.Z, case1));
        }
    }
    
}

public void extractLayer2Edge(EdgeCubie e) throws Exception
{
    //if edge is already in layer 2 but in a different place, we have to extract it
    //this will not be frequent as we start with other cases first and we will extract edges in this case as we place other edges most of the times
    
    //if it is in front face we would apply next rotation sequence case1
    ArrayList<Rotation> case1= new ArrayList<Rotation>();
    case1.add(Rotation.Db);
    case1.add(Rotation.Rb);
    case1.add(Rotation.D);
    case1.add(Rotation.R);
    case1.add(Rotation.D);
    case1.add(Rotation.F);
    case1.add(Rotation.Db);
    case1.add(Rotation.Fb);    
    //but in general, we have to apply it from the face perspective where the edge is placed, considered the edge in 1,2 (permutation)
    //if it is in 1,0 we apply it to the left face from the edge 
    //please note we consider the edge from its main color, as a convetion we chose a main color for each layer 2 edge
    //but any edge is in 1,0 in one face and in 1,2 in its left face
    int d=Distance(Face.Front,e.pos.aFace, RotationCube.Z);
    if (e.pos.j == 0) //in 1,0
    {
        d=d-1;
        if (d<0) { d=3;}
    }
    RotationSequence(PermutationList(d, RotationCube.Z, case1));
}

public void downCross() throws Exception
{
    //there are only a few possible cases:
    // the cross, L in any orientation, bar in any orientation, and nothing (no yellos in any edge)
    // no possible 3 edges
    //check if down cross is there already -> exit
    //if not, check if we have a L pattern
    // if yes, we place it down-left 
    //in both cases (not L or L already placed down-left) we apply downCross sequence, and recursively call downCross
    boolean downCrossSolved=false;
    if (ZDown[0][1]==Color.Yellow && ZDown[1][0]==Color.Yellow && ZDown[1][2]==Color.Yellow && ZDown[2][1]==Color.Yellow) downCrossSolved=true;
    
    if (!downCrossSolved)
    {
        ArrayList<Rotation> rs=new ArrayList<Rotation>();
        rs.add(Rotation.R);
        rs.add(Rotation.Lb);
        rs.add(Rotation.F);
        rs.add(Rotation.Rb);
        rs.add(Rotation.L);
        //the sequece is rs, D, rs, D, rs

        //check if L exist, and place it DownLeft
        if (ZDown[0][1]==Color.Yellow && ZDown[1][0]==Color.Yellow) 
        {
            Rotate(Rotation.Db); //place L in DownLeft
        }
        else if (ZDown[0][1]==Color.Yellow && ZDown[1][2]==Color.Yellow) 
        {
            //place L in DownLeft
            Rotate(Rotation.Db); 
            Rotate(Rotation.Db);
        }
        else if (ZDown[1][2]==Color.Yellow && ZDown[2][1]==Color.Yellow) 
        {
            //place L in DownLeft
            Rotate(Rotation.D); 
        }
        //else
        //there is no L or L is already placed

        RotationSequence(rs);
        Rotate(Rotation.D);
        RotationSequence(rs);
        Rotate(Rotation.D);
        RotationSequence(rs);
        
        //recursively
        downCross();
    }
}

public void downPlaceEdges() throws Exception
{
    //check if edges are already in place, or in place after down rotations
    //if not, check if 2 edges in place (or eventually after Down rotations), then apply rotation sequence from a different one in his place (permutation with respect to front)
    //else apply rotation sequence from any, previously place it in his location (permutation)
    // recursively again until edges placed
    
    EdgePosition p=locateEdge(Color.Yellow, Color.Blue);
    if (p.i!=0) //and j==1, not in its place, so place it
    {
        if(p.i==1 && p.j==0) Rotate(Rotation.D);
        else if(p.i==1 && p.j==2) Rotate(Rotation.Db);
        else if(p.i==2 && p.j==1) 
        {
            Rotate(Rotation.Db);
            Rotate(Rotation.Db);
        }
    }
    //now cubie Yello Blue in its palce, check rest
    if (!checkCubie(Color.Yellow, 1,2) || !checkCubie(Color.Yellow, 2,1) || !checkCubie(Color.Yellow, 1,0))
    //if we find 2 edges in the right order (already in their place, or not in their place, but they would eventually be after D rotations) 
            //then we want to run the rotation sequence with these 2 edges in down - left - front
    {
        int permDistance=0;
        EdgePosition np;
        //p is blue
        EdgePosition orangep=locateEdge(Color.Yellow, Color.Orange);
        EdgePosition greenp=locateEdge(Color.Yellow, Color.Green);
        EdgePosition redp=locateEdge(Color.Yellow, Color.Red);

        np=DownNextPositionClockWise(p);
        if (np.i == orangep.i && np.j==orangep.j)
        {
            //permutation distance 1 on Zb
            permDistance=1;
        }
        else
        {
            //orange is either in 2,1 or in 1,0
            np=DownNextPositionClockWise(orangep);
            if (np.i == greenp.i && np.j==greenp.j)
            //only possible in orange in 2,1 and green in 1,0
            {
                Rotate(Rotation.Db);
                //permutation disntance 2 on Zb
                permDistance=2;
            }
            else
            //green is either in 1,2, in 2,1 or in 1,0
            {
                np=DownNextPositionClockWise(greenp);
                if (np.i == redp.i && np.j==redp.j)
                //only possible in green in 1,2 or 2,1 and red in 2,1 or 1,0 respectively
                {
                    if (greenp.i==1) // && greenp.j==2
                    {
                        Rotate(Rotation.D);
                    }
                    //else grenp i=2 j=1
                    //already in place, no rotation required
                    
                    //permutation distance 3 on Zb
                    permDistance=3;
                }
                //else
                //{
                    //np=DownNextPositionClockWise(redp);
                    //only possible if red in 1,0
                    //if (np.i == p.i && np.j ==p.j)
                    //equivalent to if (redp.i==1 && redp.j==0) as we know blue is in 0,1
                    //{
                        //no permutation
                        //no rotation required
                    //}
                    //we do not need to check as we will do the same if no 2 edges in order found
                //}
                
            }
        
        }
    
        Rotate(Permutation (permDistance, RotationCube.Zb, Rotation.R ));
        Rotate(Permutation (permDistance, RotationCube.Zb, Rotation.D ));
        Rotate(Permutation (permDistance, RotationCube.Zb, Rotation.D ));
        Rotate(Permutation (permDistance, RotationCube.Zb, Rotation.Rb ));
        Rotate(Permutation (permDistance, RotationCube.Zb, Rotation.Db ));
        Rotate(Permutation (permDistance, RotationCube.Zb, Rotation.R ));
        Rotate(Permutation (permDistance, RotationCube.Zb, Rotation.Db ));
        Rotate(Permutation (permDistance, RotationCube.Zb, Rotation.Rb ));

        //recursively
        downPlaceEdges();
    }
    
    
}

EdgePosition DownNextPositionClockWise(EdgePosition e)
{
    EdgePosition next=new EdgePosition(0,0,Face.Up); //construct with any value

    next.aFace=Face.Down;
    if (e.i == 0) //and j==1
    {
        next.i=1;
        next.j=2;
    }
    else if (e.i == 1 && e.j == 2)
    {
        next.i=2;
        next.j=1;    
    }
    else if (e.i == 2 && e.j == 1)
    {
        next.i=1;
        next.j=0;    
    }
    else if (e.i == 1 && e.j == 0)
    {
        next.i=0;
        next.j=1;    
    }
    return next;
}

public void downCorners() throws Exception
{
    //check if they are all placed already in the corner (in the right way or rotated)
    //if yes, done
    //if not, check if there is at least one corner placed (in the right way or rotated)
    // if yes place it in 2,2
    // if not do nothing
    // after that we apply rotation sequence to place corners
    // recursively again downCorners
    
    //check corner Down 0 0
    boolean placed00=false;
    switch (ZDown[0][0])
    {
        case Yellow:
        case Blue:
        case Red:
            placed00=true;
            break;
        default:
            break;
    }
    if (placed00)
    {
        switch (YFront[2][0])
        {
        case Yellow:
        case Blue:
        case Red:
                break;
        default:
            placed00=false;
            break;    
        }

        if (placed00)
        {
            switch (XLeft[2][2])
            {
            case Yellow:
            case Blue:
            case Red:
                break;
            default:
                placed00=false;
                break;
            }        
        }
    }
    
    boolean placed02=false;
    switch (ZDown[0][2])
    {
        case Yellow:
        case Blue:
        case Orange:
            placed02=true;
            break;
        default:
            break;
    }
    if (placed02)
    {
        switch (YFront[2][2])
        {
            case Yellow:
            case Blue:
            case Orange:
                    break;
            default:
                placed02=false;
                break;    
        }

        if (placed02)
        {
            switch (XRight[2][0])
            {
                case Yellow:
                case Blue:
                case Orange:
                    break;
                default:
                    placed02=false;
                    break;
            }        
        }
    }
    
    boolean placed20=false;
    switch (ZDown[2][0])
    {
        case Yellow:
        case Green:
        case Red:
            placed20=true;
            break;
        default:
            break;
    }
    if (placed20)
    {
        switch (YBack[2][2])
        {
            case Yellow:
            case Green:
            case Red:
                    break;
            default:
                placed20=false;
                break;    
        }

        if (placed20)
        {
            switch (XLeft[2][0])
            {
                case Yellow:
                case Green:
                case Red:
                    break;
                default:
                    placed20=false;
                    break;
            }        
        }
    }

    int permDistance=0;
    if (!placed00 || !placed02)
    {
        //only possible all placed or only one placed, so we just need to check 2 corners
        //not all corners placed
        //we need to place them
        if (placed00)
        {
            //we apply movement as if 00 was in 22
            permDistance=2;        
        }   
        if (placed02)
        {
            //we apply movement as if 02 was in 22
            permDistance=1;        
        }   
        if (placed20)
        {
            //we apply movement as if 20 was in 22
            permDistance=3;        
        }   
        
        ArrayList<Rotation> rl=new ArrayList<Rotation>();
        rl.add(Rotation.F);
        rl.add(Rotation.Db);
        rl.add(Rotation.Bb);
        rl.add(Rotation.D);
        rl.add(Rotation.Fb);
        rl.add(Rotation.Db);
        rl.add(Rotation.B);
        rl.add(Rotation.D);
        ArrayList<Rotation> pl=PermutationList(permDistance, RotationCube.Zb, rl);
        RotationSequence(pl);
        
        downCorners();
    }
    
}

public void downPlaceCorners() throws Exception
{
    //we start movement from corner Down 2 2
    //for this corner, check if it is placed properly (main - yellow is down), if not order it
    //2 cases, 
    //main - yellow is back - Ub R U Rb x 2
    //main - yellow is right - R Ub Rb U x 2
    //move D to check next corner, and eventually order it with same movements
    //applying the movement from the same position except Down face preseves the rest of the cube ...
    
    ArrayList<Rotation> rla=new ArrayList<Rotation>();
    ArrayList<Rotation> rlb=new ArrayList<Rotation>();
    rla.add(Rotation.Ub);
    rla.add(Rotation.Rb);
    rla.add(Rotation.U);
    rla.add(Rotation.R);
    rla.add(Rotation.Ub);
    rla.add(Rotation.Rb);
    rla.add(Rotation.U);
    rla.add(Rotation.R);
    rlb.add(Rotation.Rb);
    rlb.add(Rotation.Ub);
    rlb.add(Rotation.R);
    rlb.add(Rotation.U);
    rlb.add(Rotation.Rb);
    rlb.add(Rotation.Ub);
    rlb.add(Rotation.R);
    rlb.add(Rotation.U);
    
    int cornersToCheck=4;
    while (cornersToCheck > 0)
    {
        cornersToCheck--;
        if (ZDown[2][2]!=Color.Yellow)
        {
            if (YBack[2][0]==Color.Yellow)
            {
                RotationSequence(rla);
            }
            else
            {
                //XLeft[2][2]==Yellow
                RotationSequence(rlb);
            }
        }
        Rotate(Rotation.D);
    }
    
    //all corners are properly placed, although DownFace is rotated
    CornerPosition cp = locateCorner(Color.Yellow, Color.Blue, Color.Orange);
    if (cp.aFace != Face.Down) throw new Exception();
    if (cp.i==0 && cp.j==0) Rotate(Rotation.D);
    if (cp.i==2 && cp.j==0) 
    {
        Rotate(Rotation.D);
        Rotate(Rotation.D);        
    }
    if (cp.i==2 && cp.j==2) 
    {
        Rotate(Rotation.Db);
    }
    
    
    
}

public String printSequence()
{
    String s="";
    for (int i=0; i<seq.size();i++)
    {
       switch(seq.get(i))
       {
           case R:
               s=s+"R ";
               break;
           case Rb:
               s=s+"Rb ";
               break;
           case L:
               s=s+"L ";
               break;
           case Lb:
               s=s+"Lb ";
               break;
           case U:
               s=s+"U ";
               break;
           case Ub:
               s=s+"Ub ";
               break;
           case D:
               s=s+"D ";
               break;
           case Db:
               s=s+"Db ";
               break;
           case F:
               s=s+"F ";
               break;
           case Fb:
               s=s+"Fb ";
               break;
           case B:
               s=s+"B ";
               break;
           case Bb:
               s=s+"Bb ";
               break;
           default:
               break;
       }
    }
    return s;
}

}