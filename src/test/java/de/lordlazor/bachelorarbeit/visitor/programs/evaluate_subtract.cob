IDENTIFICATION DIVISION.
PROGRAM-ID. BeispielProgramm.
DATA DIVISION.
WORKING-STORAGE SECTION.
01 Zahl1 PIC 9.
01 Zahl2 PIC 9.
01 Zahl3 PIC 9.
01 Zahl4 PIC 9.
01 Ergebnis PIC 99.
01 Check PIC 9.
PROCEDURE DIVISION.
EVALUATE Check
    WHEN 1
       SUBTRACT Zahl1 FROM Zahl1 GIVING Ergebnis
    WHEN 2
        SUBTRACT 1 FROM Zahl2 GIVING Ergebnis
    WHEN 3
        SUBTRACT Zahl3 FROM Zahl3
    WHEN 1
        SUBTRACT 3 FROM Zahl4
END-EVALUATE.