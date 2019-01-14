package nschultz.game.states;

import javafx.scene.canvas.GraphicsContext;
import nschultz.game.util.Highscore;
import org.junit.Test;

import static org.junit.Assert.*;

public class MenuStateTest {
    @Test
    public void testMainMenu()
    {
        GameState state = new MenuState(null);
        assertEquals(state.getLastGameState()==null, true);
    }

    @Test
    public void testMainMenu_newGame()
    {
        GameState state = new MenuState(null);
        assertEquals(((MenuState) state).getOptions()[0], "New game");
    }

    @Test
    public void testPauseMenu()
    {
        GameState state = new MenuState(null, new GameState(null) {
            @Override
            public void update(long now) {

            }

            @Override
            public void render(GraphicsContext brush, long now) {

            }
        });
        assertEquals(state.getLastGameState()==null, false);
    }

    @Test
    public void testPauseMenu_resumeGame()
    {
        GameState state = new MenuState(null, new GameState(null) {
            @Override
            public void update(long now) {

            }

            @Override
            public void render(GraphicsContext brush, long now) {

            }
        });
        assertEquals(((MenuState) state).getOptions()[0], "Resume Game");
    }

    @Test
    public void testMenuOptionsCount()
    {
        GameState state = new MenuState(null);
        assertEquals(((MenuState) state).getOptions().length, 5);
    }

    @Test
    public void testHighScore()
    {
        int highScore = new Highscore().score();
        int expectedScore = 0;
        assertEquals(highScore, expectedScore);
    }

}
