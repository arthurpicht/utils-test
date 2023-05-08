package de.arthurpicht.utils.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestIdsTest {

    @Test
    public void testTestGroup() {
        String string = "ein.zwei.tg_03_blahblah.tc_77_blub";
        String testGroupId = TestIds.getTestGroupId(string);
        Assertions.assertEquals("tg_03", testGroupId);
    }

    @Test
    public void testTestCase() {
        String string = "ein.zwei.tg_03_blahblah.tc_77_blub";
        String testGroupId = TestIds.getTestCaseId(string);
        Assertions.assertEquals("tc_77", testGroupId);
    }

}
