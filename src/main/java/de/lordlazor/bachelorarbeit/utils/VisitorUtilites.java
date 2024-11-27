package de.lordlazor.bachelorarbeit.utils;

public class VisitorUtilites {

  // This class is used to add a count to nodes that are used more than once in the program and need a unique id to be displayed multiple times

  public static int currentAdd = 0;
  public static int currentSubtract = 0;
  public static int currentMultiply = 0;
  public static int currentDivide = 0;
  public static int currentIf = 0;
  public static int currentPerform = 0;
  public static int currentGoTo = 0;
  public static int currentSet = 0;
public static int currentEvaluate = 0;
public static int currentMove = 0;
public static int currentAccept = 0;

  public static void resetCounters() {
    currentAdd = 0;
    currentSubtract = 0;
    currentMultiply = 0;
    currentDivide = 0;
    currentIf = 0;
    currentPerform = 0;
    currentGoTo = 0;
    currentSet = 0;
    currentEvaluate = 0;
    currentMove = 0;
    currentAccept = 0;
  }

}
