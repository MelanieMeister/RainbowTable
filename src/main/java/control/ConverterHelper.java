package control;

import java.math.BigInteger;

public  abstract class ConverterHelper {

    public static BigInteger hashToDec(String hash){
        return new BigInteger(hash, 16);
    }
}
