IDENTIFICATION DIVISION. 
PROGRAM-ID. BeispielProgramm.
DATA DIVISION.
WORKING-STORAGE SECTION.
01 WS-Zahl1 PIC 9.
01 WS-Zahl2 PIC 9.
01 WS-Zahl3 PIC 9.
01 WS-Zahl4 PIC 9.
01 WS-Ergebnis PIC 9.
procedure division.
MULTIPLY WS-Zahl1 BY WS-Zahl2.
MULTIPLY WS-Zahl3 BY WS-Zahl4 GIVING WS-Ergebnis.