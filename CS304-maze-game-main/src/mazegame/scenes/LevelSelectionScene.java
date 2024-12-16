package mazegame.scenes;

import com.sun.opengl.util.GLUT;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LevelSelectionScene implements GLEventListener, KeyListener {

    private JFrame frame;
    private int selectedDifficulty = -1; // -1 means no selection yet
    private final String[] difficultyLevels = {"Easy", "Medium", "Hard", "Extreme"};
    private final float buttonWidth = 200.0f;
    private final float buttonHeight = 50.0f;

    public void start() {
        System.out.println("Level Selection ....");

        frame = new JFrame("Maze Game - Difficulty");

        GLCapabilities capabilities = new GLCapabilities();
        GLCanvas canvas = new GLCanvas(capabilities);
        canvas.addGLEventListener(this);
        canvas.addKeyListener(this);
        canvas.setFocusable(true);
        canvas.requestFocusInWindow();

        frame.add(canvas);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Mouse Listener for button clicks
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleMouseClick(e.getX(), e.getY());
            }
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        // Set up the OpenGL context to draw buttons
        drawButtons(glAutoDrawable);
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean modeChanged, boolean deviceChanged) {

    }

    private void drawButtons(GLAutoDrawable glAutoDrawable) {
        // OpenGL rendering to draw buttons
        float centerX = 0.0f; // Center horizontally
        float centerY = 0.0f; // Center vertically (adjust this value to control vertical placement)

        // Draw buttons for each difficulty level
        for (int i = 0; i < difficultyLevels.length; i++) {
            float buttonPosY = centerY - (buttonHeight * (difficultyLevels.length / 2.0f) - (i * buttonHeight));
            drawButton(glAutoDrawable, centerX, buttonPosY, difficultyLevels[i], i == selectedDifficulty);
        }
    }

    private void drawButton(GLAutoDrawable glAutoDrawable, float x, float y, String text, boolean isSelected) {
        // Set the OpenGL color for the button (highlighted if selected)
        if (isSelected) {
            glAutoDrawable.getGL().glColor3f(0.0f, 1.0f, 0.0f); // Highlighted color (green)
        } else {
            glAutoDrawable.getGL().glColor3f(1.0f, 0.0f, 0.0f); // Normal color (red)
        }

        // Draw button as a rectangle
        glAutoDrawable.getGL().glBegin(javax.media.opengl.GL.GL_QUADS);
        glAutoDrawable.getGL().glVertex2f(x - buttonWidth / 2, y - buttonHeight / 2);
        glAutoDrawable.getGL().glVertex2f(x + buttonWidth / 2, y - buttonHeight / 2);
        glAutoDrawable.getGL().glVertex2f(x + buttonWidth / 2, y + buttonHeight / 2);
        glAutoDrawable.getGL().glVertex2f(x - buttonWidth / 2, y + buttonHeight / 2);
        glAutoDrawable.getGL().glEnd();

        // Draw the button's text using GLUT
        GLUT glut = new GLUT();
        glAutoDrawable.getGL().glPushMatrix();
        glAutoDrawable.getGL().glTranslatef(x, y, 0.0f); // Move to the button's center
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, text);
        glAutoDrawable.getGL().glPopMatrix();
    }

    private void handleMouseClick(int mouseX, int mouseY) {
        // Convert mouse coordinates to OpenGL coordinates
        int height = frame.getHeight();
        float normalizedX = (2.0f * mouseX / frame.getWidth()) - 1.0f;
        float normalizedY = 1.0f - (2.0f * mouseY / height);

        // Check if any of the buttons were clicked (based on their positions)
        float centerX = 0.0f;
        float centerY = 0.0f;

        for (int i = 0; i < difficultyLevels.length; i++) {
            float buttonPosY = centerY - (buttonHeight * (difficultyLevels.length / 2.0f) - (i * buttonHeight));
            if (normalizedX >= centerX - buttonWidth / 2 && normalizedX <= centerX + buttonWidth / 2 &&
                    normalizedY >= buttonPosY - buttonHeight / 2 && normalizedY <= buttonPosY + buttonHeight / 2) {
                selectedDifficulty = i;  // Update the selected difficulty
                System.out.println("Selected difficulty: " + difficultyLevels[i]);
                break;
            }
        }
    }
}
