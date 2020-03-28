package model;

import control.ConverterHelper;

import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PresentationModel {
    private static final String CHARACTERS
            = "0123456789abcdefghijklmnopqrstuvwxyz";
    private static final char[] SYMBOLS
            = CHARACTERS.toCharArray();
    MessageDigest md;
    List<Password> rainbowTable = new ArrayList<>();
    HashMap<String, String> table = new HashMap<>();
    private int passwordLength = 7;
    private int amountOfPasswords = 2000;
    private String targetHash = "1d56a37fb6b08aa709fe90e12ca59e12";
    private String password;

    public PresentationModel() {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

//    public void generatePasswordHashes() {
//        List<Password> passwordList = new ArrayList<>();
//        String pw1;
//        for (int i = 0; i < amountOfPasswords; i++) {
//            String pw = generatePassword();
//            String hash = getHash(pw);
//            String reduction = reduction(hash, i);
//            passwordList.add(new Password(pw, hash, reduction));
//        }
//
//        do {
//            passwordList = distinct(passwordList);
//        } while (passwordList.size() != 2000);
//
//        rainbowTable = passwordList;
//    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public List<Password> distinct(List<Password> passwords) {
        return passwords.stream().filter(distinctByKey(Password::getPassword)).collect(Collectors.toList());
    }

    public void compare() {
        Password pw = null;
        List<Password> passwordList2 = new ArrayList<>();
        for (int i = 0; i < amountOfPasswords; i++) {
            String reductionTarget = reduction(targetHash, i);
            if (reductionTarget.equals(rainbowTable.get(i).getReduction())) {
                pw = rainbowTable.get(i);
                passwordList2.add(rainbowTable.get(i));
            }
        }
        if (pw == null) {
            System.out.println();
        } else {
            System.out.println();
        }
        int size = passwordList2.size();

        System.out.println();
    }

    public void compare2() {
        Password pw = null;
        List<Password> passwordList2 = new ArrayList<>();
        System.out.println("ioj");
//        for (int l = 0;  l< 2000; l++) {
//            String reduction2 = reduction(targetHash, l);
//            System.out.println(reduction2);
//            if("igmt8ml".equals(reduction2)){
//                System.out.println("ioj");
//
//            }
//        }
        for (int i = 0; i < amountOfPasswords; i++) {
            String reductionTarget = reduction(targetHash, i);

            for (int j = 0; j < amountOfPasswords; j++) {
                if (reductionTarget.equals(rainbowTable.get(j).getReduction())) {
                    pw = rainbowTable.get(j);
                    passwordList2.add(rainbowTable.get(j));
                }
                if(targetHash.equals(rainbowTable.get(j).getHash())){
                    pw = rainbowTable.get(j);
                    passwordList2.add(rainbowTable.get(j));
                }
            }

        }

        if (pw == null) {
            System.out.println();
        } else {
            System.out.println();
        }
        int size = passwordList2.size();

        System.out.println();
    }

    public void sdf() {
        List<Password> passwordList = new ArrayList<>();
        String pw1;
        for (int i = 0; i < amountOfPasswords; i++) {
            String pw = generatePassword();
            String hash = getHash(pw);
            String reduction = reduction(hash, i);
            passwordList.add(new Password(pw, hash, reduction));
        }

        do {
            passwordList = distinct(passwordList);
        } while (passwordList.size() != 2000);

        rainbowTable = passwordList;
    }


    public void generatePasswordHashes() {
        List<Password> passwordList = new ArrayList<>();

        for (int i = 0; i < CHARACTERS.length(); i++) {
            for (int j = 0; j < CHARACTERS.length(); j++) {
                for (int k = 0; k < CHARACTERS.length(); k++) {
                    if (passwordList.size() < amountOfPasswords) {
                        String pw = "0000" + CHARACTERS.toCharArray()[i]
                                + CHARACTERS.toCharArray()[j]
                                + CHARACTERS.toCharArray()[k];

                        String hash = getHash(pw);
                        String reduction = reduction(hash, i);
                        passwordList.add(new Password(pw, hash, reduction));
                    } else {
                        break;
                    }

                }

            }
        }

        rainbowTable = passwordList;

    }

    public String reduction(String hash, int step) {
        BigInteger hashAsInteger = ConverterHelper.hashToDec(hash);
        BigInteger length = BigInteger.valueOf(CHARACTERS.length());
        ;

//        hashAsInteger = BigInteger.valueOf(hashAsInteger.intValue()+ step);

        hashAsInteger = hashAsInteger.add(BigInteger.valueOf(step));

        List<BigInteger> reductions = new ArrayList<>();

        String reductionCharacters = "";

        for (int i = 0; i < passwordLength; i++) {
            int position = hashAsInteger.mod(length).intValue();
            reductions.add(hashAsInteger.mod(length));
            hashAsInteger = hashAsInteger.divide(length);

            reductionCharacters = reductionCharacters + CHARACTERS.toCharArray()[position];
        }


        return new StringBuilder(reductionCharacters).reverse().toString();
    }

    /**
     * Create a hash (MD5) of a password.
     *
     * @param password password which will be hashed
     * @return the hash of the input password
     */
    public String getHash(String password) {
        md.update(password.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter
                .printHexBinary(digest).toLowerCase();
    }

    /**
     * Generates the next secure random token in series.
     * generate the next secure random token in series.
     *
     * @return the token as string
     */
    public String generatePassword() {
        final SecureRandom secureRandom = new SecureRandom();
        char[] BUF = new char[passwordLength];

        for (int idx = 0; idx < BUF.length; ++idx) {
            BUF[idx] = SYMBOLS[secureRandom.nextInt(SYMBOLS.length)];
        }
        return new String(BUF);
    }
}
