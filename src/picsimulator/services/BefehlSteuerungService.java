package picsimulator.services;

import picsimulator.model.Speicher;
import picsimulator.model.befehle.pic.*;

/**
 * Created by ptrey on 24.04.2017.
 */
public class BefehlSteuerungService {

    public void steuereBefehl(Speicher speicher, String binaryString) {

        if (binaryString.startsWith("000111")) {
            ADDWF addwf = new ADDWF(binaryString, 6, speicher);
        }
        if (binaryString.startsWith("000101")) {
            ANDWF andwf = new ANDWF(binaryString, 6, speicher);
        }
        if (binaryString.startsWith("0000011")) {
            CLRF clrf = new CLRF(binaryString, 7, speicher);
        }
        if (binaryString.startsWith("0000010")) {
            CLRW clrw = new CLRW(binaryString, 7, speicher);
        }
        if (binaryString.startsWith("001001")) {
            COMF comf = new COMF(binaryString, 6, speicher);
        }
        if (binaryString.startsWith("000011")) {
            DECF decf = new DECF(binaryString, 6, speicher);
        }
        if (binaryString.startsWith("001011")) {
            DECFSZ decfsz = new DECFSZ(binaryString, 6, speicher);
        }
        if (binaryString.startsWith("001010")) {
            INCF incf = new INCF(binaryString, 6, speicher);
        }
        if (binaryString.startsWith("001111")) {
            INCFSZ incfsz = new INCFSZ(binaryString, 6, speicher);
        }
        if (binaryString.startsWith("000100")) {
            IORWF iorwf = new IORWF(binaryString, 6, speicher);
        }
        if (binaryString.startsWith("001000")) {
            MOVF movf = new MOVF(binaryString, 6, speicher);
        }
        if (binaryString.startsWith("0000001")) {
            MOVWF movwf = new MOVWF(binaryString, 7, speicher);
        }
        if (binaryString.startsWith("0000000") && binaryString.endsWith("00000")) {
            NOP nop = new NOP(binaryString, 14, speicher);
        }
        if (binaryString.startsWith("001101")) {
            RLF rlf = new RLF(binaryString, 6, speicher);
        }
        if (binaryString.startsWith("001100")) {
            RRF rrf = new RRF(binaryString, 6, speicher);
        }
        if (binaryString.startsWith("000010")) {
            SUBWF subwf = new SUBWF(binaryString, 6, speicher);
        }
        if (binaryString.startsWith("001110")) {
            SWAPF swapf = new SWAPF(binaryString, 6, speicher);
        }
        if (binaryString.startsWith("000110")) {
            XORWF xorwf = new XORWF(binaryString, 6, speicher);
        }
        if (binaryString.startsWith("0100")) {
            BCF bcf = new BCF(binaryString, 4, speicher);
        }
        if (binaryString.startsWith("0101")) {
            BSF bsf = new BSF(binaryString, 4, speicher);
        }
        if (binaryString.startsWith("0110")) {
            BTFSC btfsc = new BTFSC(binaryString, 4, speicher);
        }
        if (binaryString.startsWith("0111")) {
            BTFSS btfss = new BTFSS(binaryString, 4, speicher);
        }
        if (binaryString.startsWith("11111")) {
            ADDLW addlw = new ADDLW(binaryString, 5, speicher);
        }
        if (binaryString.startsWith("111001")) {
            ANDLW andlw = new ANDLW(binaryString, 6, speicher);
        }
        if (binaryString.startsWith("100")) {
            CALL call = new CALL(binaryString, 3, speicher);
        }
        if (binaryString.startsWith("00000001100100")) {
            CLRWDT clrwdt = new CLRWDT(binaryString, 14, speicher);
        }
        if (binaryString.startsWith("101")) {
            GOTO gotoO = new GOTO(binaryString,3, speicher);
        }
        if (binaryString.startsWith("111000")) {
            IORLW iorlw = new IORLW(binaryString,6, speicher);
        }
        if (binaryString.startsWith("1100")) {
            MOVLW movlw = new MOVLW(binaryString,4, speicher);
        }
        if (binaryString.startsWith("00000000001001")) {
            RETFIE retfie = new RETFIE(binaryString,14, speicher);
        }
        if (binaryString.startsWith("1101")) {
            RETLW retlw = new RETLW(binaryString, 4, speicher);
        }
        if (binaryString.startsWith("00000000001000")) {
            RETURN returnO = new RETURN(binaryString,14, speicher);
        }
        if (binaryString.startsWith("00000001100011")) {
            SLEEP sleep = new SLEEP(binaryString,14, speicher);
        }
        if (binaryString.startsWith("11110")) {
            SUBLW sublw = new SUBLW(binaryString,5, speicher);
        }
        if (binaryString.startsWith("111010")) {
            XORLW xorlw = new XORLW(binaryString,6, speicher);
        }

    }

}
