package model;

import org.junit.Assert;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

public class PresentationModelTest {

    @Test
    public void getHastTest() throws NoSuchAlgorithmException {
        PresentationModel m = new PresentationModel();

        String hash1 = m.getHash("0000000");
        String hash2 = m.getHash("87inwgn");
        String hash3 = m.getHash("frrkiis");
        String hash4 = m.getHash("dues6fg");

        Assert.assertEquals("29c3eea3f305d6b823f562ac4be35217", hash1);
        Assert.assertEquals("12e2feb5a0feccf82a8d4172a3bd51c3", hash2);
        Assert.assertEquals("437988e45a53c01e54d21e5dc4ae658a", hash3);
        Assert.assertEquals("c0e9a2f2ae2b9300b6f7ef3e63807e84", hash4);
    }

    @Test
    public void reductionTest() throws NoSuchAlgorithmException {
        PresentationModel m = new PresentationModel();
        String reductionValue = m.reduction("ad5b19cfcace83bb41953d28c6d36d61", 1338);

        Assert.assertEquals("jeqzqfv",reductionValue);
    }
}