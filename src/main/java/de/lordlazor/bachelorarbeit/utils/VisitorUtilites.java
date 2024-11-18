package de.lordlazor.bachelorarbeit.utils;

public class VisitorUtilites {

  public static int currentAdd = 0;
  public static int currentSubtract = 0;
  public static int currentMultiply = 0;
  public static int currentDivide = 0;

  public static void resetCounters() {
    currentAdd = 0;
    currentSubtract = 0;
    currentMultiply = 0;
    currentDivide = 0;
  }

}
