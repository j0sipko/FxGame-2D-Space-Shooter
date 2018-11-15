package nschultz.game.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Highscore {

    public static final Path PATH = Paths.get(
            System.getProperty("user.dir") + File.separator + "highscore.txt"
    );

    public void save(final int data) {
        try {
            final byte[] bytes = Integer.toBinaryString(data).getBytes(
                    StandardCharsets.UTF_8
            );
            Files.write(PATH, bytes);
        } catch (final IOException e) {
            new ErrorLog(e.toString()).log();
        }
    }

    public int score() {
        try {
            return Integer.parseInt(new String(Files.readAllBytes(PATH)), 2);
        } catch (final IOException e) {
            new ErrorLog(e.toString()).log();
        }
        return -1;
    }
}
