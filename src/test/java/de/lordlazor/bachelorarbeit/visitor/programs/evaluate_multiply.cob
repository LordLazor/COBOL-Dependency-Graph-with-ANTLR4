IDENTIFICATION DIVISION.
PROGRAM-ID. BeispielProgramm.
DATA DIVISION.
WORKING-STORAGE SECTION.
01 Zahl1 PIC 9.
01 Zahl2 PIC 9.
01 Zahl3 PIC 9.
01 Zahl4 PIC 9.
01 Zahl5 PIC 9.
01 Ergebnis PIC 99.
01 Check PIC 9.
PROCEDURE DIVISION.
EVALUATE Check
WHEN 1
       MULTIPLY Zahl1 BY Zahl1 GIVING Ergebnis
WHEN 1
        MULTIPLY 1 BY Zahl2 GIVING Ergebnis
WHEN 1
        MULTIPLY Zahl3 BY Zahl3
WHEN 1
        MULTIPLY 3 BY Zahl4
WHEN 1
        MULTIPLY Zahl5 BY 2 GIVING Ergebnis
END-EVALUATE.
