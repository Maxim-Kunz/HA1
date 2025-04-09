package htw.berlin.prog2.ha1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Retro calculator")
class CalculatorTest {



    @Test
    @DisplayName("should display result after adding two positive multi-digit numbers")
    void testPositiveAddition() {
        Calculator calc = new Calculator();

        calc.pressDigitKey(2);
        calc.pressDigitKey(0);
        calc.pressBinaryOperationKey("+");
        calc.pressDigitKey(2);
        calc.pressDigitKey(0);
        calc.pressEqualsKey();

        String expected = "40";
        String actual = calc.readScreen();

        assertEquals(expected, actual);
    }
    /*
     * Testet das addieren von 20 + 20
     */




    @Test
    @DisplayName("should display result after getting the square root of two")
    void testSquareRoot() {
        Calculator calc = new Calculator();

        calc.pressDigitKey(2);
        calc.pressUnaryOperationKey("√");

        String expected = "1.41421356";
        String actual = calc.readScreen();

        assertEquals(expected, actual);
    }
    //Testet die Wurzel von 2




    @Test
    @DisplayName("should display error when dividing by zero")
    void testDivisionByZero() {
        Calculator calc = new Calculator();

        calc.pressDigitKey(7);
        calc.pressBinaryOperationKey("/");
        calc.pressDigitKey(0);
        calc.pressEqualsKey();

        String expected = "Error";
        String actual = calc.readScreen();

        assertEquals(expected, actual);
    }
    //Testet, ob "Error" angezeigt wird bei Div durch 0




    @Test
    @DisplayName("should display error when drawing the square root of a negative number")
    void testSquareRootOfNegative() {
        Calculator calc = new Calculator();

        calc.pressDigitKey(7);
        calc.pressNegativeKey();
        calc.pressUnaryOperationKey("√");

        String expected = "Error";
        String actual = calc.readScreen();

        assertEquals(expected, actual);
    }
    //Testet, ob "Error" angezeigt wird bei Wurzel von neg. Zahl




    @Test
    @DisplayName("should not allow multiple decimal dots")
    void testMultipleDecimalDots() {
        Calculator calc = new Calculator();

        calc.pressDigitKey(1);
        calc.pressDotKey();
        calc.pressDigitKey(7);
        calc.pressDotKey();
        calc.pressDigitKey(8);

        String expected = "1.78";
        String actual = calc.readScreen();

        assertEquals(expected, actual);
    }
    //Erlaubt nicht mehr als 1 Dezimalzeichen "."


    //TODO hier weitere Tests erstellen
    //Testet, ob bei Clear Bildschirm zurückgesetzt wird
    @Test
    @DisplayName("Screen sollte zurückgesetzt werden")
    void testClearScreen(){
        Calculator calc = new Calculator();

        calc.pressClearKey();
        String expected = "0";
        String actual = calc.readScreen();

        assertEquals(expected, actual);
    }




    //Rote Tests:
    //Idee: Zweites Drücken Binary
    //Idee: Clear 1x drücken soll lastvalue und lastoperation NICHT zurücksetzen -> tut es aber

    @Test
    @DisplayName("Beim Zweiten Drücken der Taste sollte das aktuelle Zwischenergebnis direkt angezeigt werden")
    void testBinary(){
        Calculator calc = new Calculator();

        calc.pressDigitKey(5);
        calc.pressBinaryOperationKey("-");
        calc.pressDigitKey(3);
        calc.pressBinaryOperationKey("-");


        String expected = "2";
        String actual = calc.readScreen();

        assertEquals(expected, actual);
    }


    @Test
    @DisplayName("Zwischengespeicherte Werte sollten NICHT zurückgesetzt werden nach dem ersten Drücken")
    void testClearScreen2(){
        Calculator calc = new Calculator();

        calc.setLatestValue(3.0);

        calc.pressClearKey();
        double expected = 3.0;
        double actual = calc.getLatestValue();

        assertEquals(expected, actual);
    }





}
