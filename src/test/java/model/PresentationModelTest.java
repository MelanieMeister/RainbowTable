package model;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PresentationModelTest {

    @Test
    public void getHastTest(){
        PresentationModel m = new PresentationModel();

        String hash1 = m.getHash("0000000");
        String hash2 = m.getHash("87inwgn");
        String hash3 = m.getHash("frrkiis");
        String hash4 = m.getHash("dues6fg");

        Assert.assertEquals("29c3eea3f305d6b823f562ac4be35217", hash1);
        Assert.assertEquals("12e2feb5a0feccf82a8d4172a3bd51c3", hash2);
        Assert.assertEquals("437988e45a53c01e54d21e5dc4ae658a", hash3);
        Assert.assertEquals("c0e9a2f2ae2b9300b6f7ef3e63807e84", hash4);

        Assert.assertEquals("1d56a37fb6b08aa709fe90e12ca59e12", m.getHash("0bgec3d"));
    }

    @Test
    public void reductionTest(){
        PresentationModel m = new PresentationModel();
        String reductionValue = m.reduction("ad5b19cfcace83bb41953d28c6d36d61", 1338);

        Assert.assertEquals("jeqzqfv",reductionValue);
    }
    @Test
    public void getHastTest2(){
        PresentationModel m = new PresentationModel();
//        m.create();
//        m.createRainbowTable();
     m.generatePasswordHashes();
       m.compare2();
//       m.compare();

      String k =   m.reduction("29c3eea3f305d6b823f562ac4be35217", 0);
      System.out.println();
    }
}