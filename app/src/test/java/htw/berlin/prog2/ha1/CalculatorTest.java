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


    //TODO hier weitere Tests erstellen
    //Teilaufgabe 1
    @Test
    @DisplayName("sollte das Ergebnis nach einer Multiplikation zweier Zahlen anzeigen")
    void testPositiveMultiplication() {
        Calculator calc = new Calculator();

        // 5 x 4 = 20
        calc.pressDigitKey(5);
        calc.pressBinaryOperationKey("x");
        calc.pressDigitKey(4);
        calc.pressEqualsKey();

        String expected = "20";
        String actual = calc.readScreen();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("sollte die letzte Operation wiederholen, wenn man öfter auf ´=´ drückt")
    void testRepeatedEquals() {
        Calculator calc = new Calculator();

        //  2 + 3 = 5
        calc.pressDigitKey(2);
        calc.pressBinaryOperationKey("+");
        calc.pressDigitKey(3);
        calc.pressEqualsKey(); // Erwartetes Ergebnis: 5

        calc.pressEqualsKey(); // Erwartetes Ergebnis: 8
        calc.pressEqualsKey(); // Erwartetes Ergebnis: 11


        String expected = "11"; //was erwartet wird
        String actual = calc.readScreen(); //was tatsächlich ausgegeben wird

        assertEquals(expected, actual); // Der Bildschirm sollte 11 anzeigen
    }

    @Test
    @DisplayName("sollte die Ergebnisse auch in Dezimal ausgeben können")
    void testDecimalMultiplication() {
        Calculator calc = new Calculator();

        //(1 wird eingegeben) . (5 wird eingegeben) = 1.5 x 2 = 3.0 <- soll ausgegeben werden
        calc.pressDigitKey(1);
        calc.pressDotKey();
        calc.pressDigitKey(5); // 1.5

        calc.pressBinaryOperationKey("x");

        calc.pressDigitKey(2);
        calc.pressEqualsKey();

        String expected = "3.0"; // 1.5 x 2 = 3.0
        String actual = calc.readScreen();

        assertEquals(expected, actual);
    }
}

