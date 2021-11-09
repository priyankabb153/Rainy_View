package me.samlss.rainyviewdemo;

import me.samlss.view.RainyView;
import ohos.aafwk.ability.delegation.AbilityDelegatorRegistry;
import ohos.agp.components.Attr;
import ohos.agp.utils.Color;
import ohos.app.Context;
import org.junit.Test;
import ohos.agp.components.AttrSet;
import org.junit.Before;

import java.util.Optional;

import static org.junit.Assert.*;

public class ExampleOhosTest {
    private AttrSet attrSet;
    private Context context;
    private RainyView rainyView;
    private static final String COLOR = "#4CAF50";
    private static final String COLOR1 = "#B7AC8D";

    @Test
    public void testBundleName() {
        final String actualBundleName = AbilityDelegatorRegistry.getArguments().getTestBundleName();
        assertEquals("me.samlss.rainyview_demo", actualBundleName);
    }

    @Before
    public void setUp() {
        context = AbilityDelegatorRegistry.getAbilityDelegator().getAppContext();
        attrSet = new AttrSet() {
            @Override
            public Optional<String> getStyle() {
                return Optional.empty();
            }

            @Override
            public int getLength() {
                return 0;
            }

            @Override
            public Optional<Attr> getAttr(int i) {
                return Optional.empty();
            }

            @Override
            public Optional<Attr> getAttr(String s) {
                return Optional.empty();
            }
        };

    }

    @Test
    public void testSetLeftCloudColor() {
        rainyView = new RainyView(context, attrSet);
        rainyView.setLeftCloudColor(Color.getIntColor(COLOR));
        assertEquals(Color.getIntColor(COLOR), rainyView.getLeftCloudColor());
    }

    @Test
    public void testSetLeftCloudColor1() {
        rainyView = new RainyView(context, attrSet);
        rainyView.setLeftCloudColor(Color.getIntColor(COLOR));
        assertNotEquals(Color.getIntColor(COLOR1), rainyView.getLeftCloudColor());
    }

    @Test
    public void testSetRightCloudColor() {
        rainyView = new RainyView(context, attrSet);
        rainyView.setRightCloudColor(Color.getIntColor(COLOR));
        assertEquals(Color.getIntColor(COLOR), rainyView.getRightCloudColor());
    }

    @Test
    public void testSetRightCloudColor1() {
        rainyView = new RainyView(context, attrSet);
        rainyView.setRightCloudColor(Color.getIntColor(COLOR));
        assertNotEquals(Color.getIntColor(COLOR1), rainyView.getRightCloudColor());
    }

    @Test
    public void testSetRainDropColor() {
        rainyView = new RainyView(context, attrSet);
        rainyView.setRainDropColor(Color.getIntColor(COLOR));
        assertEquals(Color.getIntColor(COLOR), rainyView.getRainDropColor());
    }

    @Test
    public void testSetRainDropColor1() {
        rainyView = new RainyView(context, attrSet);
        rainyView.setRainDropColor(Color.getIntColor(COLOR));
        assertNotEquals(Color.getIntColor(COLOR1), rainyView.getRainDropColor());
    }

    @Test
    public void testRainDropMaxNumber() {
        rainyView = new RainyView(context, attrSet);
        rainyView.setRainDropMaxNumber(30);
        assertEquals(30, rainyView.getRainDropMaxNumber());
    }

    @Test
    public void testRainDropMaxNumber1() {
        rainyView = new RainyView(context, attrSet);
        rainyView.setRainDropMaxNumber(30);
        assertNotEquals(35, rainyView.getRainDropMaxNumber());
    }

    @Test
    public void testRainDropCreationInterval() {
        rainyView = new RainyView(context, attrSet);
        rainyView.setRainDropCreationInterval(30);
        assertEquals(30, rainyView.getRainDropCreationInterval());
    }

    @Test
    public void testRainDropCreationInterval1() {
        rainyView = new RainyView(context, attrSet);
        rainyView.setRainDropCreationInterval(30);
        assertNotEquals(20, rainyView.getRainDropCreationInterval());
    }

    @Test
    public void testRainDropMinLength() {
        rainyView = new RainyView(context, attrSet);
        rainyView.setRainDropMinLength(10);
        assertEquals(10, rainyView.getRainDropMinLength());
    }

    @Test
    public void testRainDropMinLength1() {
        rainyView = new RainyView(context, attrSet);
        rainyView.setRainDropMinLength(30);
        assertNotEquals(20, rainyView.getRainDropMinLength());
    }

    @Test
    public void testRainDropMaxLength() {
        rainyView = new RainyView(context, attrSet);
        rainyView.setRainDropMaxLength(50);
        assertEquals(50, rainyView.getRainDropMaxLength());
    }

    @Test
    public void testRainDropMaxLength1() {
        rainyView = new RainyView(context, attrSet);
        rainyView.setRainDropMaxNumber(30);
        assertNotEquals(20, rainyView.getRainDropMaxLength());
    }

    @Test
    public void testRainDropSize() {
        rainyView = new RainyView(context, attrSet);
        rainyView.setRainDropSize(15);
        assertEquals(15, rainyView.getRainDropSize());
    }

    @Test
    public void testRainDropSize1() {
        rainyView = new RainyView(context, attrSet);
        rainyView.setRainDropSize(10);
        assertNotEquals(20, rainyView.getRainDropSize());
    }

    @Test
    public void testRainDropMaxSpeed() {
        rainyView = new RainyView(context, attrSet);
        rainyView.setRainDropMaxSpeed(5f);
        assertEquals(String.valueOf(5f), String.valueOf(rainyView.getRainDropMaxSpeed()));
    }

    @Test
    public void testRainDropMaxSpeed1() {
        rainyView = new RainyView(context, attrSet);
        rainyView.setRainDropMaxSpeed(6f);
        assertNotEquals(2f, rainyView.getRainDropMaxSpeed());
    }

    @Test
    public void testRainDropMinSpeed() {
        rainyView = new RainyView(context, attrSet);
        rainyView.setRainDropMinSpeed(1f);
        assertEquals(String.valueOf(1f), String.valueOf(rainyView.getRainDropMinSpeed()));
    }

    @Test
    public void testRainDropMinSpeed1() {
        rainyView = new RainyView(context, attrSet);
        rainyView.setRainDropMinSpeed(1f);
        assertNotEquals(2f, rainyView.getRainDropMinSpeed());
    }

    @Test
    public void testRainDropSlope() {
        rainyView = new RainyView(context, attrSet);
        rainyView.setRainDropSlope(-3f);
        assertEquals(String.valueOf(-3f), String.valueOf(rainyView.getRainDropSlope()));
    }

    @Test
    public void testRainDropSlope1() {
        rainyView = new RainyView(context, attrSet);
        rainyView.setRainDropSlope(-3f);
        assertNotEquals(3f, rainyView.getRainDropSlope());
    }
}