package edu.jiangbaiyu.demo.maven;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {
    @Test
    public void testReverse() {
        assertEquals("cba", App.reverse("abc"));
    }
}