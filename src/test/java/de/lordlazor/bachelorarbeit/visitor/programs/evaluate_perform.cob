IDENTIFICATION DIVISION.
PROGRAM-ID. BeispielProgramm.
DATA DIVISION.
WORKING-STORAGE SECTION.
01 Zahl1 PIC 9.

PROCEDURE DIVISION.
BeispielParagraph.
       DISPLAY "".

EVALUATE Zahl1
  WHEN 1
    PERFORM BeispielParagraph
END-EVALUATE.