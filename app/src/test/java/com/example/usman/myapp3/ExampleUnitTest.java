package com.example.usman.myapp3;
import org.junit.Test;
import java.util.Random;
import static org.junit.Assert.*;
/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    int vX = 0,gen=0;boolean okap = false;
    Random ran = new Random();
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        gen = ran.nextInt(10);
        if (gen==3 || gen==6 || gen==9){
            okap = true;
            vX = att(vX);
        }
        System.out.println(vX);
    }
    public int att(int voX){
        voX+=200;
        return voX;
    }
}