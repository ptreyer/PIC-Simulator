package picsimulator.services;

/**
 * Created by ptrey on 24.04.2017.
 */
public class BefehlSteuerungService {

    public void steuereBefehl(String binaryString) {

        if (binaryString.startsWith("000111")) {
// ADDWF
        }
        if (binaryString.startsWith("000101")) {
// ANDWF
        }
        if (binaryString.startsWith("0000011")) {
// CLRF
        }
        if (binaryString.startsWith("0000010")) {
// CLRW
        }
        if (binaryString.startsWith("001001")) {
// COMF
        }
        if (binaryString.startsWith("000011")) {
// DECF
        }
        if (binaryString.startsWith("001011")) {
// DECFSZ
        }
        if (binaryString.startsWith("001010")) {
// INCF
        }
        if (binaryString.startsWith("001111")) {
// INCFSZ
        }
        if (binaryString.startsWith("000100")) {
// IORWF
        }
        if (binaryString.startsWith("001000")) {
// MOVF
        }
        if (binaryString.startsWith("0000001")) {
// WOVWF
        }
        if (binaryString.startsWith("0000000") && binaryString.endsWith("00000")) {
// NOP
        }
        if (binaryString.startsWith("001101")) {
// RLF
        }
        if (binaryString.startsWith("001100")) {
// RRF
        }
        if (binaryString.startsWith("000010")) {
// SUBWF
        }
        if (binaryString.startsWith("001110")) {
// SWAPF
        }
        if (binaryString.startsWith("000110")) {
// XORWF
        }
        if (binaryString.startsWith("0100")) {
// BCF
        }
        if (binaryString.startsWith("0101")) {
// BSF
        }
        if (binaryString.startsWith("0110")) {
// BTFSC
        }
        if (binaryString.startsWith("0111")) {
// BTFSS
        }
        if (binaryString.startsWith("11111")) {
// ADDLW
        }
        if (binaryString.startsWith("111001")) {
// ANDLW
        }
        if (binaryString.startsWith("100")) {
// CALL
        }
        if (binaryString.startsWith("00000001100100")) {
// CLRWDT
        }
        if (binaryString.startsWith("101")) {
// GOTO
        }
        if (binaryString.startsWith("111000")) {
// IORLW
        }
        if (binaryString.startsWith("1100")) {
// MOVLW
        }
        if (binaryString.startsWith("00000000001001")) {
// RETFIE
        }
        if (binaryString.startsWith("1101")) {
// RETLW
        }
        if (binaryString.startsWith("00000000001000")) {
// RETURN
        }
        if (binaryString.startsWith("00000001100011")) {
// SLEEP
        }
        if (binaryString.startsWith("11110")) {
// SUBLW
        }
        if (binaryString.startsWith("111010")) {
// XORLW
        }


    }

}
