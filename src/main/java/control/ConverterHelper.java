package control;

import java.math.BigInteger;

public  abstract class ConverterHelper {

    /**
     * Converts hash to decimal.
     * @param hash the hash to be converted
     * @return decimal number as BigInteger
     */
    public static BigInteger hashToDec(String hash){
        return new BigInteger(hash, 16);
    }
}
