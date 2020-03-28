package model;

import control.ConverterHelper;

import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

public class PresentationModel {
    private static final String CHARACTERS
            = "0123456789abcdefghijklmnopqrstuvwxyz";
    private MessageDigest md;
    private List<Password> rainbowTable = new ArrayList<>();
    private final int passwordLength = 7;
    private final int amountOfPasswords = 2000;
    private String targetHash = "1d56a37fb6b08aa709fe90e12ca59e12";

    public PresentationModel() {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String compare() {
        String hash;
        int i = amountOfPasswords;
        while (i >= 0) {
            hash = targetHash;
            for (int j = i; j < amountOfPasswords; j++) {
                String reductionTarget = reduction(hash, j);
               List<Password> results = rainbowTable.stream()
                        .filter(item -> item.getReduction().equals(reductionTarget))
                        .collect(Collectors.toList());

                if (results != null && results.size() == 1) {
                    return getPassword(results.get(0), targetHash);
                }
                hash = getHash(reductionTarget);
            }
            i--;
        }
        return null;
    }

    private String getPassword(Password foundPassWordObject, String targetHash) {
        String password = foundPassWordObject.getPassword();
        for (int i = 0; i < amountOfPasswords; i++) {
            String hash = getHash(password);
            if (hash.equals(targetHash)) {
                return password;
            }
            password = reduction(hash, i);
        }
        return null;
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

                        //do chain
                        String hashValue = "";
                        String lastReductionValue = pw;
                        for (int l = 0; l < amountOfPasswords; l++) {
                            hashValue = getHash(lastReductionValue);
                            lastReductionValue = reduction(hashValue, l);
                        }
                        passwordList.add(new Password(pw, hashValue, lastReductionValue));
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

//        hashAsInteger = BigInteger.valueOf(hashAsInteger.intValue()+ step);

        hashAsInteger = hashAsInteger.add(BigInteger.valueOf(step));

        List<BigInteger> reductions = new ArrayList<>();

        StringBuilder reductionCharacters = new StringBuilder();

        for (int i = 0; i < passwordLength; i++) {
            int position = hashAsInteger.mod(length).intValue();
            reductions.add(hashAsInteger.mod(length));
            hashAsInteger = hashAsInteger.divide(length);

            reductionCharacters.append(CHARACTERS.toCharArray()[position]);
        }


        return new StringBuilder(reductionCharacters.toString()).reverse().toString();
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

}
