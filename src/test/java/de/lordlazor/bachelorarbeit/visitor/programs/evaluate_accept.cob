IDENTIFICATION DIVISION.
PROGRAM-ID. BeispielProgramm.
DATA DIVISION.
WORKING-STORAGE SECTION.
01 Zahl1 PIC 9.

PROCEDURE DIVISION.
EVALUATE Zahl1
  WHEN 1
    ACCEPT Zahl1
END-EVALUATE.