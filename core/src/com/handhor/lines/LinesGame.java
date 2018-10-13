package com.handhor.lines;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.handhor.lines.Ball;
import com.handhor.lines.Cell;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;

/**
 * Created by Ігор on 20.09.16.
 */
public class LinesGame implements GameLoop {
    private Cell field[][];
    private static final int step = Const.WIDTH / Const.FIELD_SIZE;
    private static final int indent = 5; //depends from ball texture
    private static final int BALLS_TO_ADD = 3;
    Texture fieldTxt;
    Texture scoreTxt;
    Texture gameOverTxt;
    private int fieldVZero;
    private int triggeredI, triggeredJ;
    private boolean gameOver;
    private int score;
    private List<Cell> sequence;
    private Numbers scoreNumbers;

    @Override
    public void init() {
        fieldTxt = RM.gIns().getTexture("field");
        scoreTxt = RM.gIns().getTexture("score");
        gameOverTxt = RM.gIns().getTexture("gameover");
        fieldVZero = (Const.HEIGHT - fieldTxt.getHeight()) / 2;
        field = new Cell[Const.FIELD_SIZE][Const.FIELD_SIZE];
        for (int i = 0; i < Const.FIELD_SIZE; i++) {
            for (int j = 0; j < Const.FIELD_SIZE; j++) {
                field[i][j] = new Cell();
                field[i][j].setBounds(new Rectangle( i * step, j * step + fieldVZero, step, step));
            }
        }
        gameOver = false;
        sequence = new ArrayList<Cell>();
        triggeredI = -1;
        triggeredJ = -1;
        scoreNumbers = new Numbers(RM.gIns().getTexture("numbers"));
        for (int i = 0; i < BALLS_TO_ADD; i++) {
            addNewBall();
        }
    }

    @Override
    public void update(float dt) {
        boolean ballWasMoved = handleInput();
        if (ballWasMoved) {
            boolean wasDeleted = checkForFive();
            if (!wasDeleted) {
                for (int i = 0; i < BALLS_TO_ADD; i++) {
                    addNewBall();
                }
                checkForFive();
                checkGameOver();
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(fieldTxt, 0, fieldVZero);
        sb.draw(scoreTxt, indent, fieldVZero + fieldTxt.getHeight() + indent);
        Array<TextureRegion> digits = scoreNumbers.getNumber(score);
        int k = 0;
        for (TextureRegion digit : digits) {
            sb.draw(digit, k*digit.getRegionWidth() + indent + scoreTxt.getWidth(), fieldVZero + fieldTxt.getHeight() + indent);
            k++;
        }
        for (int i = 0; i < Const.FIELD_SIZE; i++) {
            for (int j = 0; j < Const.FIELD_SIZE; j++) {
                if (!field[i][j].isEmpty()) {
                    sb.draw(field[i][j].getBall().getTexture(),
                            i * step + indent, j * step + indent + fieldVZero);
                }
            }
        }
        if (triggeredI != -1) {
            sb.draw(RM.gIns().getTexture("shining"),
                    triggeredI * step + indent, triggeredJ * step + indent + fieldVZero);
        }
        if (gameOver) {
            sb.draw(gameOverTxt, (Const.WIDTH - gameOverTxt.getWidth()) / 2, (Const.HEIGHT + fieldVZero + fieldTxt.getHeight()) / 2);
        }
        sb.end();
    }

    @Override
    public boolean handleInput() {
        if (Gdx.input.justTouched()) {
            Vector2 point = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            localToWorld(point);
            Circle touchCircle = new Circle(point.x, point.y, 2);
            for (int i = 0; i < Const.FIELD_SIZE; i++) {
                for (int j = 0; j < Const.FIELD_SIZE; j++) {
                    if (Intersector.overlaps(touchCircle, field[i][j].getBounds())) {
                        if ((triggeredI == -1)&&(!field[i][j].isEmpty())) {
                            triggeredI = i;
                            triggeredJ = j;
                        }
                        else if (triggeredI != -1) {
                            if ((triggeredI != i) || (triggeredJ != j)) {
                                if ((field[i][j].isEmpty()) && pathExist(triggeredI, triggeredJ, i, j)) {
                                    Ball ball = field[triggeredI][triggeredJ].getBall();
                                    field[i][j].setBall(ball);
                                    field[triggeredI][triggeredJ].setEmpty(true);
                                    triggeredI = triggeredJ = -1;
                                    return true;
                                }
                            }
                            else {
                                triggeredI = triggeredJ = -1;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean addNewBall() {
        int count = 0;
        int i = (int)(Math.random() * Const.FIELD_SIZE);
        int j = (int)(Math.random() * Const.FIELD_SIZE);
        while (!field[i][j].isEmpty()) {
            i = (int)(Math.random() * Const.FIELD_SIZE);
            j = (int)(Math.random() * Const.FIELD_SIZE);
            count++;
            if (count > 1000) {
                return false;
            }
        }
        int randomIndex = Const.RANDOM.nextInt(Const.COLOR_VALUES.size());
        Const.BallColor color = Const.COLOR_VALUES.get(randomIndex);
        field[i][j].setBall(new Ball(color));
        return true;
    }

    private void localToWorld(Vector2 point) {
        float coefX = (float) Const.WIDTH / Gdx.graphics.getWidth();
        float coefY = (float) Const.HEIGHT / Gdx.graphics.getHeight();
        point.x *= coefX;
        point.y = (Gdx.graphics.getHeight() - point.y) * coefY;
    }

    private boolean pathExist(int startI, int startJ, int endI, int endJ) {
        boolean cellWasAdded = true;
        Vector2 dest = new Vector2(endI, endJ);
        List<Vector2> reachableCells = new ArrayList<Vector2>();
        reachableCells.add(new Vector2(startI, startJ));
        while (cellWasAdded) {
            for (int i = 0; i < reachableCells.size(); i++) {
                Vector2 curr = reachableCells.get(i);
                cellWasAdded = false;
                if ((curr.x < Const.FIELD_SIZE - 1) && (field[(int)curr.x + 1][(int)curr.y].isEmpty())) {
                    Vector2 nCell = new Vector2(curr.x + 1, curr.y);
                    if (!reachableCells.contains(nCell)){
                        reachableCells.add(nCell);
                        cellWasAdded = true;
                    }
                }
                if ((curr.y < Const.FIELD_SIZE - 1) && (field[(int)curr.x][(int)curr.y + 1].isEmpty())){
                    Vector2 nCell = new Vector2(curr.x, curr.y + 1);
                    if (!reachableCells.contains(nCell)){
                        reachableCells.add(nCell);
                        cellWasAdded = true;
                    }
                }
                if ((curr.x > 0) && (field[(int)curr.x - 1][(int)curr.y].isEmpty())) {
                    Vector2 nCell = new Vector2(curr.x - 1, curr.y);
                    if (!reachableCells.contains(nCell)){
                        reachableCells.add(nCell);
                        cellWasAdded = true;
                    }
                }
                if ((curr.y > 0) && (field[(int)curr.x][(int)curr.y - 1].isEmpty())) {
                    Vector2 nCell = new Vector2(curr.x, curr.y - 1);
                    if (!reachableCells.contains(nCell)){
                        reachableCells.add(nCell);
                        cellWasAdded = true;
                    }
                }
            }
        }
        return reachableCells.contains(dest);
    }

    private boolean checkForFive() {
        for (int i = 0; i < Const.FIELD_SIZE; i++) {
            for (int j = 0; j < Const.FIELD_SIZE; j++) {
                if (!field[i][j].isEmpty()) {
                    int ni = i;
                    int nj = j;
                    /*vertcal check block*/
                    sequence.add(field[i][j]);
                    while (nj < Const.FIELD_SIZE - 1) {
                        if (!field[i][nj].isEmpty()) {
                            if (field[i][nj + 1].isEmpty() ||
                               (field[i][nj + 1].getBall().getColor() != field[i][nj].getBall().getColor())) {
                                if (sequence.size() > 4) {
                                    deleteSequence();
                                    return true;
                                }
                                sequence.clear();
                            }
                            else sequence.add(field[i][nj + 1]);
                        }
                        nj++;
                    }
                    if (sequence.size() > 4) {
                        deleteSequence();
                        return true;
                    }
                    sequence.clear();
                    /*****************************/
                    /*horizontal check block*/
                    sequence.add(field[i][j]);
                    while (ni < Const.FIELD_SIZE - 1) {
                        if (!field[ni][j].isEmpty()) {
                            if (field[ni + 1][j].isEmpty() ||
                               (field[ni + 1][j].getBall().getColor() != field[ni][j].getBall().getColor())) {
                                if (sequence.size() > 4) {
                                    deleteSequence();
                                    return true;
                                }
                                sequence.clear();
                            }
                            else sequence.add(field[ni + 1][j]);
                        }
                        ni++;
                    }
                    if (sequence.size() > 4) {
                        deleteSequence();
                        return true;
                    }
                    sequence.clear();
                    /*****************************/
                }
            }
        }
        return false;
    }

    void deleteSequence() {
        for (Cell i: sequence) {
            i.eraseBall();
        }
        score += sequence.size();
        System.out.println(score);
        sequence.clear();
    }

    private void checkGameOver() {
        boolean res = true;
        for (int i = 0; i < Const.FIELD_SIZE; i++) {
            for (int j = 0; j < Const.FIELD_SIZE; j++) {
                if (field[i][j].isEmpty()) {
                    res = false;
                }
            }
        }
        gameOver = res;
    }
}
