package htw.berlin.prog2.ha1;

/**
 * Eine Klasse, die das Verhalten des Online Taschenrechners imitiert, welcher auf
 * https://www.online-calculator.com/ aufgerufen werden kann (ohne die Memory-Funktionen)
 * und dessen Bildschirm bis zu zehn Ziffern plus einem Dezimaltrennzeichen darstellen kann.
 * Enthält mit Absicht noch diverse Bugs oder unvollständige Funktionen.
 */
public class Calculator {

    private String screen = "0";

    private double latestValue;

    private String latestOperation = "";

/*
    //Getter & Setter für Test
    public double getLatestValue(){
        return this.latestValue;
    }

    public void setLatestValue(double newLatestValue){
        this.latestValue = newLatestValue;
    }
*/


    /**
     * @return den aktuellen Bildschirminhalt als String
     */
    public String readScreen() {
        return screen;
    }

    /**
     * Empfängt den Wert einer gedrückten Zifferntaste. Da man nur eine Taste auf einmal
     * drücken kann muss der Wert positiv und einstellig sein und zwischen 0 und 9 liegen.
     * Führt in jedem Fall dazu, dass die gerade gedrückte Ziffer auf dem Bildschirm angezeigt
     * oder rechts an die zuvor gedrückte Ziffer angehängt angezeigt wird.
     * @param digit Die Ziffer, deren Taste gedrückt wurde
     */
    public void pressDigitKey(int digit) {
        if(digit > 9 || digit < 0) throw new IllegalArgumentException();

        if(screen.equals("0") || latestValue == Double.parseDouble(screen)) screen = ""; //wenn Screen = "0" -> nicht eingegeben /  Wenn der letzte Wert = dem Wert auf dem Bildschrim -> zurücksetzen des Bildschirm
        screen = screen + digit;
    }


    /**
     * Empfängt den Befehl der C- bzw. CE-Taste (Clear bzw. Clear Entry).
     * Einmaliges Drücken der Taste löscht die zuvor eingegebenen Ziffern auf dem Bildschirm
     * so dass "0" angezeigt wird, jedoch ohne zuvor zwischengespeicherte Werte zu löschen.
     * Wird daraufhin noch einmal die Taste gedrückt, dann werden auch zwischengespeicherte
     * Werte sowie der aktuelle Operationsmodus zurückgesetzt, so dass der Rechner wieder
     * im Ursprungszustand ist.
     */
    public void pressClearKey() {
        screen = "0";
        latestOperation = "";
        latestValue = 0.0;
    }
    //Abweichung: Zweimaliges Drücken hat keinen besonderen Effekt

    /**
     * Empfängt den Wert einer gedrückten binären Operationstaste, also eine der vier Operationen
     * Addition, Substraktion, Division, oder Multiplikation, welche zwei Operanden benötigen.
     * Beim ersten Drücken der Taste wird der Bildschirminhalt nicht verändert, sondern nur der
     * Rechner in den passenden Operationsmodus versetzt.
     * Beim zweiten Drücken nach Eingabe einer weiteren Zahl wird direkt des aktuelle Zwischenergebnis
     * auf dem Bildschirm angezeigt. Falls hierbei eine Division durch Null auftritt, wird "Error" angezeigt.
     * @param operation "+" für Addition, "-" für Substraktion, "x" für Multiplikation, "/" für Division
     */
    public void pressBinaryOperationKey(String operation)  {
        latestValue = Double.parseDouble(screen); //Double.parseDouble() konvertiert String in Double
        latestOperation = operation;
    }//Der Screen verändert sich nicht / Beim ersten drücken ändert sich die Operation /
    //Reihenfolge: Zahl Operation Zahl -> es wird nur die letzte Zahl ausgegeben




    /**
     * Empfängt den Wert einer gedrückten unären Operationstaste, also eine der drei Operationen
     * Quadratwurzel, Prozent, Inversion, welche nur einen Operanden benötigen.
     * Beim Drücken der Taste wird direkt die Operation auf den aktuellen Zahlenwert angewendet und
     * der Bildschirminhalt mit dem Ergebnis aktualisiert.
     * @param operation "√" für Quadratwurzel, "%" für Prozent, "1/x" für Inversion
     */
    public void pressUnaryOperationKey(String operation) {
        latestValue = Double.parseDouble(screen);
        latestOperation = operation;
        var result = switch(operation) {
            case "√" -> Math.sqrt(Double.parseDouble(screen));
            case "%" -> Double.parseDouble(screen) / 100;
            case "1/x" -> 1 / Double.parseDouble(screen);
            default -> throw new IllegalArgumentException();
        };
        screen = Double.toString(result);
        if(screen.equals("NaN")) screen = "Error";
        if(screen.contains(".") && screen.length() > 11) screen = screen.substring(0, 10);
        //Funktioniert alles aus dem Javadoc

    }

    /**
     * Empfängt den Befehl der gedrückten Dezimaltrennzeichentaste, im Englischen üblicherweise "."
     * Fügt beim ersten Mal Drücken dem aktuellen Bildschirminhalt das Trennzeichen auf der rechten
     * Seite hinzu und aktualisiert den Bildschirm. Daraufhin eingegebene Zahlen werden rechts vom
     * Trennzeichen angegeben und daher als Dezimalziffern interpretiert.
     * Beim zweimaligem Drücken, oder wenn bereits ein Trennzeichen angezeigt wird, passiert nichts.
     */
    public void pressDotKey() {
        if(!screen.contains(".")) screen = screen + ".";
    }
    //Ziffern werden rechts angegeben / kein doppel . möglich -> Funktioniert

    /**
     * Empfängt den Befehl der gedrückten Vorzeichenumkehrstaste ("+/-").
     * Zeigt der Bildschirm einen positiven Wert an, so wird ein "-" links angehängt, der Bildschirm
     * aktualisiert und die Inhalt fortan als negativ interpretiert.
     * Zeigt der Bildschirm bereits einen negativen Wert mit führendem Minus an, dann wird dieses
     * entfernt und der Inhalt fortan als positiv interpretiert.
     */
    public void pressNegativeKey() {
        screen = screen.startsWith("-") ? screen.substring(1) : "-" + screen;
    }
    //negativ wird positiv und positiv wird negativ -> Funktioniert
    // Zahl wird als negative Zahl behandelt -> Funktioniert

    /**
     * Empfängt den Befehl der gedrückten "="-Taste.
     * Wurde zuvor keine Operationstaste gedrückt, passiert nichts.
     * Wurde zuvor eine binäre Operationstaste gedrückt und zwei Operanden eingegeben, wird das
     * Ergebnis der Operation angezeigt. Falls hierbei eine Division durch Null auftritt, wird "Error" angezeigt.
     * Wird die Taste weitere Male gedrückt (ohne andere Tasten dazwischen), so wird die letzte
     * Operation (ggf. inklusive letztem Operand) erneut auf den aktuellen Bildschirminhalt angewandt
     * und das Ergebnis direkt angezeigt.
     */
    public void pressEqualsKey() {
        var result = switch(latestOperation) {
            case "+" -> latestValue + Double.parseDouble(screen);
            case "-" -> latestValue - Double.parseDouble(screen);
            case "x" -> latestValue * Double.parseDouble(screen);
            case "/" -> latestValue / Double.parseDouble(screen);
            default -> throw new IllegalArgumentException();
        };
        screen = Double.toString(result);
        if(screen.equals("Infinity")) screen = "Error";
        if(screen.endsWith(".0")) screen = screen.substring(0,screen.length()-2);
        if(screen.contains(".") && screen.length() > 11) screen = screen.substring(0, 10);

    }
    //Multiplizieren -> BSP: 5*2 -> erneutes Ausführen =  jedes Mal *5 / Letzter Operand wird nicht angezeigt
    //Div 0 = Error
    //Ohne Opertaor -> Illegal Arguments Exception

    public static void main(String[] args){
        Calculator a = new Calculator(); //damit man die Methoden benutzen kann. Nicht statische Methoden nur über Instanz aufrufen -> hier "a"
        a.readScreen();
        a.pressDigitKey(1);
        a.pressDigitKey(8); // Zahl 1 plus Zahl 2 funktioniert
        //a.pressDigitKey(-5);
        //a.pressDigitKey(99); -> Exception funktioniert
        System.out.println(a.readScreen());
        //a.latestValue = 5; Test, ob Clear funktioniert
        a.pressClearKey();
        //a.pressClearKey();
        System.out.println(a.readScreen());
        System.out.println(a.latestOperation);
        System.out.println(a.latestValue); //Urpsrungszustand, allerdings wurde die Variable vorher nie geändert


        //Test Binaryopertation
        System.out.println("Binary Operator Test\n");
        a.pressDigitKey(8);
        a.pressBinaryOperationKey("+");
        a.pressDigitKey(3);
        a.pressBinaryOperationKey("-");
        System.out.println(a.readScreen());
        //System.out.println(a.latestOperation);
        System.out.println("\n");




        //Test Unary Opertator
        a.pressClearKey();
        a.pressDigitKey(5);
        a.pressDigitKey(5);
        //a.pressNegativeKey(); Zahl wird wirklich negativ da keine Wurzel möglich
        System.out.println(a.readScreen());
        System.out.println("Wurzel: " + "\n");
        a.pressUnaryOperationKey("√");
        System.out.println(a.readScreen());
        a.pressClearKey();
        a.pressDigitKey(8);
        a.pressDigitKey(6);
        System.out.println("Prozent: " + "\n");
        a.pressUnaryOperationKey("%");
        System.out.println(a.readScreen());
        System.out.println("Inverse");
        a.pressClearKey();
        a.pressDigitKey(8);
        a.pressDigitKey(6);
        a.pressUnaryOperationKey("1/x");
        System.out.println(a.readScreen());
        //a.pressUnaryOperationKey("ww"); -> Exception funktioniert


        //Test Trennzeichen
        a.pressClearKey();
        a.pressDigitKey(5);
        a.pressDotKey();
        System.out.println(a.readScreen());
        a.pressDotKey();
        System.out.println(a.readScreen());
        a.pressDigitKey(7);
        System.out.println(a.readScreen());

        //Test negative
        a.pressNegativeKey();
        System.out.println(a.readScreen());
        a.pressNegativeKey();
        System.out.println(a.readScreen());



        //Test =
        a.pressClearKey();
        a.pressDigitKey(5);
        a.pressBinaryOperationKey("+");
        a.pressDigitKey(2);
        a.pressEqualsKey();
        System.out.println(a.readScreen());

        /*Test /0
        a.pressClearKey();
        a.pressDigitKey(5);
        a.pressBinaryOperationKey("/");
        a.pressDigitKey(0);
        a.pressEqualsKey();
        System.out.println(a.readScreen());
        */

        //Test erneutes drücken
        a.pressClearKey();
        a.pressDigitKey(5);
        a.pressBinaryOperationKey("x");
        a.pressDigitKey(2);
        System.out.println(a.readScreen());
        a.pressEqualsKey();
        System.out.println(a.readScreen());
        a.pressEqualsKey();
        System.out.println(a.readScreen());
        a.pressEqualsKey();
        System.out.println(a.readScreen());

        //Test ohne operation
        a.pressClearKey();
        a.pressDigitKey(5);
        a.pressDigitKey(2);
        //a.pressEqualsKey(); -> Illegal Arguments Exception
    }
}

