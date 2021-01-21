package com.casamento;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.test.RooIntegrationTest;

@Configurable
@RooIntegrationTest(entity = Rsvp.class)
public class RsvpIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }
}
