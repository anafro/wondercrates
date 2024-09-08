package ru.anafro.wondercrates.utils.colors;

import org.bukkit.Color;
import ru.anafro.wondercrates.utils.math.Ints;

public final class Colors {
    private Colors() {}

    public static Color create(double red, double green, double blue) {
        return Color.fromRGB(
                convertToBukkitColorComponent(red),
                convertToBukkitColorComponent(green),
                convertToBukkitColorComponent(blue)
        );
    }

    public static Color gradientPick(double thisRed, double thisGreen, double thisBlue, double cursor, double thatRed, double thatGreen, double thatBlue) {
        var redDifference = thisRed - thatRed;
        var greenDifference = thisGreen - thatGreen;
        var blueDifference = thisBlue - thatBlue;

        var redChange = redDifference * cursor;
        var greenChange = greenDifference * cursor;
        var blueChange = blueDifference * cursor;

        return create(
                thisRed + redChange,
                thisGreen + greenChange,
                thisBlue + blueChange
        );
    }

    private static int convertToBukkitColorComponent(double colorComponent) {
        return Ints.clamp(0, (int) (colorComponent * 255), 255);
    }
}
