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
    private final int amountOfPasswords = 2000;

    public PresentationModel() throws NoSuchAlgorithmException {
        md = MessageDigest.getInstance("MD5");
    }

    /**
     * Generates the passwords for the rainbow table.
     */
    public void generatePasswords() {
        List<Password> passwordList = new ArrayList<>();

        // Generate the 2000 passwords
        for (int i = 0; i < CHARACTERS.length(); i++) {
            for (int j = 0; j < CHARACTERS.length(); j++) {
                for (int k = 0; k < CHARACTERS.length(); k++) {
                    if (passwordList.size() < amountOfPasswords) {
                        String pw = "0000" + CHARACTERS.toCharArray()[i]
                                + CHARACTERS.toCharArray()[j]
                                + CHARACTERS.toCharArray()[k];

                        //Do the chain
                        String hashValue;
                        String lastReductionValue = pw;
                        for (int l = 0; l < amountOfPasswords; l++) {
                            hashValue = getHash(lastReductionValue);
                            lastReductionValue = reduction(hashValue, l);
                        }
                        // Add password together with the reduction to list
                        passwordList.add(new Password(pw, lastReductionValue));
                        // Stop if rainbow table has 2000 items
                    } else break;
                }
            }
        }
        // Saves list with passwords
        rainbowTable = passwordList;
    }

    /**
     * Reduces the hash.
     * @param hash the hash to be reduced
     * @param step the step for the reduction
     * @return String of reduction of hash
     */
    public String reduction(String hash, int step) {
        int passwordLength = 7;
        // Convert and add step number to hash
        BigInteger hashAsInteger = ConverterHelper.hashToDec(hash);
        hashAsInteger = hashAsInteger.add(BigInteger.valueOf(step));
        StringBuilder reductionCharacters = new StringBuilder();
        BigInteger length = BigInteger.valueOf(CHARACTERS.length());

        for (int i = 0; i < passwordLength; i++) {
            int position = hashAsInteger.mod(length).intValue();
            hashAsInteger = hashAsInteger.divide(length);
            reductionCharacters.insert(0, CHARACTERS.toCharArray()[position]);
        }

        return reductionCharacters.toString();
    }

    /**
     * Finds reduction of targetHash in rainbow table by comparing.
     * @param targetHash the hash to find a possible password for
     * @return String of the possible password for the hash
     */
    public String compare(String targetHash) {
        String currentHash;
        int i = amountOfPasswords;
        while (i >= 0) {
            currentHash = targetHash;
            for (int j = i; j < amountOfPasswords; j++) {
                String reductionTarget = reduction(currentHash, j);
                // Filter the rainbow table for the calculated reduction
                List<Password> results = rainbowTable.stream()
                        .filter(item -> item.getReduction().equals(reductionTarget))
                        .collect(Collectors.toList());

                // If the filter found a matching entry in the rainbow table
                if (results != null && results.size() == 1) {
                    // Get the possible password
                    return getPassword(results.get(0), targetHash);
                }
                // Overwrite the hast
                currentHash = getHash(reductionTarget);
            }
            i--;
        }
        return null;
    }

    /**
     * Gets the possible password by generating MD5 hashes and comparing.
     * @param foundPasswordObject the found item in the rainbow table containing a password and the reduction value
     * @param targetHash the hash to find the password for
     * @return String of the password
     */
    private String getPassword(Password foundPasswordObject, String targetHash) {
        // Get the password from the rainbow table item
        String password = foundPasswordObject.getPassword();

        for (int i = 0; i < amountOfPasswords; i++) {
            // Generate MD5 hash of current password
            String hash = getHash(password);
            // Compare - if true return the password
            if (hash.equals(targetHash)) return password;
            // if false overwrite the password with the reduction
            else password = reduction(hash, i);
        }
        return null;
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
