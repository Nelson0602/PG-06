package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

/**
 * Dibuja el arreglo como barras verticales en un Canvas.
 * Colorea según el estado de cada celda en el paso actual.
 */
public class Painter {
    //javi
    // ── Paleta UCR ────────────────────────────────────────────────────────────
    private static final Color COL_DEFAULT   = Color.web("#1F3868");   // azul UCR
    private static final Color COL_ACTIVE    = Color.web("#E74C3C");   // rojo – comparando ahora
    private static final Color COL_RANGE_LO  = Color.web("#4A90D9");   // azul claro – dentro del rango
    private static final Color COL_FOUND     = Color.web("#1A8C7B");   // verde – encontrado
    private static final Color COL_VISITED   = Color.web("#8896A5");   // gris – ya visitado
    private static final Color COL_OUT       = Color.web("#D0D6E0");   // muy claro – fuera del rango
    private static final Color COL_TEXT      = Color.WHITE;
    private static final Color COL_IDX       = Color.web("#8896A5");
    private static final Color COL_BG        = Color.web("#F4F6FA");
    private static final Color COL_POINTER   = Color.web("#E8A020");   // ámbar UCR – puntero

    /** Estado de pintura para una celda. */
    public enum CellState {
        DEFAULT, ACTIVE, IN_RANGE, FOUND, VISITED, OUT_OF_RANGE
    }


    public static void paintEmpty(Canvas canvas, String message) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double W = canvas.getWidth(), H = canvas.getHeight();
        gc.setFill(COL_BG);
        gc.fillRect(0, 0, W, H);
        gc.setFill(Color.web("#8896A5"));
        gc.setFont(Font.font("Calibri", FontWeight.NORMAL, 14));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(message, W / 2, H / 2);
    }
}
