package control;

import java.math.BigInteger;

public  abstract class ConverterHelper {

    public static BigInteger hashToDec(String hash){
        return new BigInteger(hash, 16);
    }

    //calculate input mod 2
    public static int modulo(BigInteger hashInput, int lenght) {
        int result = (int) (hashInput.intValue() % Math.pow(2, lenght));


        return result;
    }
}
