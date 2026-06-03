package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import model.tree.*;

import java.net.URL;
import java.util.*;

/**
 * Controlador principal de la UI – PG-05.
 * Gestiona los tres tabs: Árbol Binario Simple, BST y AVL.
 * Dibuja los árboles en Canvas usando GraphicsContext.
 *
 * @author Estudiante | Curso IF-3001 Algoritmos y Estructuras de Datos
 * @author Prof. Lic. Gilberth Chaves A. (base del proyecto)
 */
public class MainController implements Initializable {

    // ── Colores del tema oscuro ──────────────────────────────────────────────
    private static final Color BG_COLOR       = Color.web("#0d1117");
    private static final Color EDGE_COLOR     = Color.web("#4a5568");
    private static final Color ROOT_COLOR     = Color.web("#00bcd4");
    private static final Color LEAF_COLOR     = Color.web("#26a64f");
    private static final Color INTERNAL_COLOR = Color.web("#2d4a6e");
    private static final Color SEARCH_COLOR   = Color.web("#7b2d8b");
    private static final Color FOUND_COLOR    = Color.web("#e6b800");
    private static final Color UNBAL_COLOR    = Color.web("#d32f2f");
    private static final Color TEXT_COLOR     = Color.WHITE;

    private static final double NODE_RADIUS = 26;
    private static final double LEVEL_HEIGHT = 90;

    // ── TabPane ──────────────────────────────────────────────────────────────
    @FXML private TabPane mainTabPane;

    // ── Tab 1: BTree ─────────────────────────────────────────────────────────
    @FXML private Canvas btreeCanvas;
    @FXML private TextField btreeInput;
    @FXML private ComboBox<String> btreeTraversalCombo;
    @FXML private TextArea btreeTraversalOutput;
    @FXML private Label btreeStatsLabel;
    @FXML private Label btreeStatusLabel;

    // ── Tab 2: BST ───────────────────────────────────────────────────────────
    @FXML private Canvas bstCanvas;
    @FXML private TextField bstInput;
    @FXML private ComboBox<String> bstTraversalCombo;
    @FXML private TextArea bstTraversalOutput;
    @FXML private Label bstStatsLabel;
    @FXML private Label bstStatusLabel;

    // ── Tab 3: AVL ───────────────────────────────────────────────────────────
    @FXML private Canvas avlCanvas;
    @FXML private TextField avlInput;
    @FXML private ComboBox<String> avlTraversalCombo;
    @FXML private TextArea avlTraversalOutput;
    @FXML private Label avlStatsLabel;
    @FXML private Label avlStatusLabel;
    @FXML private Label avlRotationLabel;

    // ── Modelos de datos ────────────────────────────────────────────────────
    private final BTree<Integer>  btree = new BTree<>();
    private final BST<Integer>    bst   = new BST<>();
    private final AVL<Integer>    avl   = new AVL<>();

    // nodos resaltados durante animación de búsqueda BST
    private final Set<Integer> bstSearchPath  = new LinkedHashSet<>();
    private Integer             bstFoundNode  = null;

    // nodos resaltados durante búsqueda AVL
    private final Set<Integer> avlSearchPath  = new LinkedHashSet<>();
    private Integer             avlFoundNode  = null;

    // ── Inicialización ───────────────────────────────────────────────────────
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String[] opciones = {"PreOrder", "InOrder", "PostOrder"};
        btreeTraversalCombo.getItems().addAll(opciones);
        btreeTraversalCombo.setValue("PreOrder");

        bstTraversalCombo.getItems().addAll(opciones);
        bstTraversalCombo.setValue("InOrder");

        avlTraversalCombo.getItems().addAll(opciones);
        avlTraversalCombo.setValue("InOrder");

        clearCanvas(btreeCanvas);
        clearCanvas(bstCanvas);
        clearCanvas(avlCanvas);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  TAB 1 – BTREE  (Árbol Binario Simple)
    // ════════════════════════════════════════════════════════════════════════

    @FXML
    void onBTreeInsert(ActionEvent event) {
        Integer val = parseInput(btreeInput, btreeStatusLabel);
        if (val == null) return;
        btree.add(val);
        btreeInput.clear();
        setStatus(btreeStatusLabel, "✔ Insertado: " + val, true);
        redrawBTree();
    }

    @FXML
    void onBTreeRemove(ActionEvent event) {
        Integer val = parseInput(btreeInput, btreeStatusLabel);
        if (val == null) return;
        try {
            btree.remove(val);
            btreeInput.clear();
            setStatus(btreeStatusLabel, "✔ Eliminado: " + val, true);
        } catch (TreeException e) {
            setStatus(btreeStatusLabel, "✘ " + e.getMessage(), false);
        }
        redrawBTree();
    }

    @FXML
    void onBTreeClear(ActionEvent event) {
        btree.clear();
        setStatus(btreeStatusLabel, "Árbol limpiado.", true);
        redrawBTree();
    }

    @FXML
    void onBTreeTraversal(ActionEvent event) {
        if (btree.isEmpty()) {
            btreeTraversalOutput.setText("El árbol está vacío.");
            return;
        }
        try {
            String tipo = btreeTraversalCombo.getValue();
            String result = switch (tipo) {
                case "InOrder"    -> "InOrder:   " + btree.inOrder();
                case "PostOrder"  -> "PostOrder: " + btree.postOrder();
                default           -> "PreOrder:  " + btree.preOrder();
            };
            btreeTraversalOutput.setText(result);
        } catch (TreeException e) {
            btreeTraversalOutput.setText("Error: " + e.getMessage());
        }
    }

    private void redrawBTree() {
        updateStats(btreeStatsLabel, btree, false, false);
        drawTree(btreeCanvas, btree.root, Collections.emptySet(), null, false);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  TAB 2 – BST
    // ════════════════════════════════════════════════════════════════════════

    @FXML
    void onBSTInsert(ActionEvent event) {
        Integer val = parseInput(bstInput, bstStatusLabel);
        if (val == null) return;
        bstSearchPath.clear(); bstFoundNode = null;
        bst.add(val);
        bstInput.clear();
        setStatus(bstStatusLabel, "✔ Insertado: " + val, true);
        redrawBST();
    }

    @FXML
    void onBSTSearch(ActionEvent event) {
        Integer val = parseInput(bstInput, bstStatusLabel);
        if (val == null) return;
        bstSearchPath.clear(); bstFoundNode = null;

        if (bst.isEmpty()) { setStatus(bstStatusLabel, "El árbol BST está vacío.", false); return; }

        // Animación: recorremos el camino de búsqueda nodo a nodo
        List<Integer> camino = buildSearchPath(bst.root, val);
        if (camino.isEmpty()) {
            setStatus(bstStatusLabel, "✘ No encontrado: " + val, false);
            return;
        }

        Timeline tl = new Timeline();
        for (int i = 0; i < camino.size(); i++) {
            final int idx = i;
            tl.getKeyFrames().add(new KeyFrame(Duration.millis(350 * (i + 1)), e -> {
                bstSearchPath.add(camino.get(idx));
                if (idx == camino.size() - 1 && camino.get(idx).equals(val)) {
                    bstFoundNode = val;
                    setStatus(bstStatusLabel, "✔ Encontrado: " + val + " (pasos: " + camino.size() + ")", true);
                }
                redrawBST();
            }));
        }
        tl.play();
    }

    @FXML
    void onBSTRemove(ActionEvent event) {
        Integer val = parseInput(bstInput, bstStatusLabel);
        if (val == null) return;
        bstSearchPath.clear(); bstFoundNode = null;
        try {
            bst.remove(val);
            bstInput.clear();
            setStatus(bstStatusLabel, "✔ Eliminado: " + val, true);
        } catch (TreeException e) {
            setStatus(bstStatusLabel, "✘ " + e.getMessage(), false);
        }
        redrawBST();
    }

    @FXML
    void onBSTClear(ActionEvent event) {
        bst.clear();
        bstSearchPath.clear(); bstFoundNode = null;
        setStatus(bstStatusLabel, "Árbol limpiado.", true);
        redrawBST();
    }

    @FXML
    void onBSTTraversal(ActionEvent event) {
        if (bst.isEmpty()) { bstTraversalOutput.setText("El árbol está vacío."); return; }
        try {
            String tipo = bstTraversalCombo.getValue();
            String result = switch (tipo) {
                case "InOrder"    -> "InOrder:   " + bst.inOrder();
                case "PostOrder"  -> "PostOrder: " + bst.postOrder();
                default           -> "PreOrder:  " + bst.preOrder();
            };
            bstTraversalOutput.setText(result);
        } catch (TreeException e) {
            bstTraversalOutput.setText("Error: " + e.getMessage());
        }
    }

    private void redrawBST() {
        updateStats(bstStatsLabel, bst, false, false);
        drawTree(bstCanvas, bst.root, bstSearchPath, bstFoundNode, false);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  TAB 3 – AVL
    // ════════════════════════════════════════════════════════════════════════

    @FXML
    void onAVLInsert(ActionEvent event) {
        Integer val = parseInput(avlInput, avlStatusLabel);
        if (val == null) return;
        avlSearchPath.clear(); avlFoundNode = null;

        // Capturar info de rotación antes/después (a través del path del nodo)
        avl.add(val);
        avlInput.clear();
        setStatus(avlStatusLabel, "✔ Insertado: " + val + " (sin rotación)", true);

        // Si el path del root contiene info de rotación, mostrarlo
        if (avl.root != null && avl.root.path != null && avl.root.path.contains("Rotate")) {
            avlRotationLabel.setText("Rotación: " + extractRotationType(avl.root.path));
        } else {
            avlRotationLabel.setText("Sin rotación necesaria");
        }

        try { updateStats(avlStatsLabel, avl, true, avl.isBalanced()); } catch (TreeException ignored) {}
        drawAVL();
    }

    @FXML
    void onAVLSearch(ActionEvent event) {
        Integer val = parseInput(avlInput, avlStatusLabel);
        if (val == null) return;
        avlSearchPath.clear(); avlFoundNode = null;
        if (avl.isEmpty()) { setStatus(avlStatusLabel, "El árbol AVL está vacío.", false); return; }

        List<Integer> camino = buildSearchPath(avl.root, val);
        if (camino.isEmpty()) {
            setStatus(avlStatusLabel, "✘ No encontrado: " + val, false);
        } else {
            avlSearchPath.addAll(camino);
            avlFoundNode = val;
            setStatus(avlStatusLabel, "✔ Encontrado: " + val, true);
        }
        drawAVL();
    }

    @FXML
    void onAVLRemove(ActionEvent event) {
        Integer val = parseInput(avlInput, avlStatusLabel);
        if (val == null) return;
        avlSearchPath.clear(); avlFoundNode = null;
        try {
            avl.remove(val);
            avlInput.clear();
            setStatus(avlStatusLabel, "✔ Eliminado: " + val, true);
        } catch (TreeException e) {
            setStatus(avlStatusLabel, "✘ " + e.getMessage(), false);
        }
        avlRotationLabel.setText("Sin rotación necesaria");
        try { updateStats(avlStatsLabel, avl, true, avl.isBalanced()); } catch (TreeException ignored) {}
        drawAVL();
    }

    @FXML
    void onAVLClear(ActionEvent event) {
        avl.clear();
        avlSearchPath.clear(); avlFoundNode = null;
        avlRotationLabel.setText("—");
        setStatus(avlStatusLabel, "Árbol limpiado.", true);
        avlStatsLabel.setText("Nodos: 0  |  Altura: 0  |  Balanceado...");
        clearCanvas(avlCanvas);
    }

    @FXML
    void onAVLTraversal(ActionEvent event) {
        if (avl.isEmpty()) { avlTraversalOutput.setText("El árbol está vacío."); return; }
        try {
            String tipo = avlTraversalCombo.getValue();
            String result = switch (tipo) {
                case "InOrder"    -> "InOrder:   " + avl.inOrder();
                case "PostOrder"  -> "PostOrder: " + avl.postOrder();
                default           -> "PreOrder:  " + avl.preOrder();
            };
            avlTraversalOutput.setText(result);
        } catch (TreeException e) {
            avlTraversalOutput.setText("Error: " + e.getMessage());
        }
    }

    private void drawAVL() {
        try { updateStats(avlStatsLabel, avl, true, avl.isEmpty() || avl.isBalanced()); }
        catch (TreeException ignored) {}
        drawTree(avlCanvas, avl.root, avlSearchPath, avlFoundNode, true);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  DIBUJO EN CANVAS
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Limpia el canvas con el color de fondo.
     */
    private void clearCanvas(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(BG_COLOR);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Dibuja un árbol genérico en el canvas dado.
     * Funciona para BTree, BST y AVL (isAVL controla si se muestra el FE).
     *
     * @param canvas     canvas de destino
     * @param root       raíz del árbol
     * @param searchPath conjunto de valores en el camino de búsqueda (para BST/AVL)
     * @param foundNode  valor del nodo encontrado (null si no hay búsqueda activa)
     * @param isAVL      true para mostrar el Factor de Equilibrio (FE) de cada nodo
     */
    private void drawTree(Canvas canvas, BTreeNode<Integer> root,
                          Set<Integer> searchPath, Integer foundNode, boolean isAVL) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        clearCanvas(canvas);

        if (root == null) return;

        // Calcular posiciones con BFS para distribuir horizontalmente
        Map<BTreeNode<Integer>, double[]> positions = new HashMap<>();
        calculatePositions(root, positions, canvas.getWidth() / 2, 60,
                canvas.getWidth() / 4, 0);

        // Dibujar aristas primero (para que queden detrás de los nodos)
        gc.setStroke(EDGE_COLOR);
        gc.setLineWidth(1.5);
        for (Map.Entry<BTreeNode<Integer>, double[]> entry : positions.entrySet()) {
            BTreeNode<Integer> node = entry.getKey();
            double[] pos = entry.getValue();
            if (node.left != null && positions.containsKey(node.left)) {
                double[] lPos = positions.get(node.left);
                gc.strokeLine(pos[0], pos[1], lPos[0], lPos[1]);
            }
            if (node.right != null && positions.containsKey(node.right)) {
                double[] rPos = positions.get(node.right);
                gc.strokeLine(pos[0], pos[1], rPos[0], rPos[1]);
            }
        }

        // Dibujar nodos
        for (Map.Entry<BTreeNode<Integer>, double[]> entry : positions.entrySet()) {
            BTreeNode<Integer> node = entry.getKey();
            double[] pos = entry.getValue();
            drawNode(gc, node, pos[0], pos[1], searchPath, foundNode, isAVL, node == root);
        }
    }

    /**
     * Calcula posiciones (x, y) para cada nodo usando DFS recursivo.
     * El ancho disponible se divide a la mitad en cada nivel.
     */
    private void calculatePositions(BTreeNode<Integer> node,
                                    Map<BTreeNode<Integer>, double[]> positions,
                                    double x, double y, double offset, int depth) {
        if (node == null) return;
        positions.put(node, new double[]{x, y});
        double nextY = y + LEVEL_HEIGHT;
        double nextOffset = Math.max(offset / 2, NODE_RADIUS + 4);
        calculatePositions(node.left,  positions, x - nextOffset, nextY, nextOffset, depth + 1);
        calculatePositions(node.right, positions, x + nextOffset, nextY, nextOffset, depth + 1);
    }

    /**
     * Dibuja un nodo individual con su color según su tipo/estado.
     */
    private void drawNode(GraphicsContext gc, BTreeNode<Integer> node,
                          double x, double y,
                          Set<Integer> searchPath, Integer foundNode,
                          boolean isAVL, boolean isRoot) {

        // Determinar color del nodo
        Color fillColor;
        if (isRoot) {
            fillColor = ROOT_COLOR;
        } else if (foundNode != null && foundNode.equals(node.data)) {
            fillColor = FOUND_COLOR;
        } else if (searchPath.contains(node.data)) {
            fillColor = SEARCH_COLOR;
        } else if (isAVL) {
            int fe = getBalanceFactor(node);
            fillColor = Math.abs(fe) > 1 ? UNBAL_COLOR
                    : fe == 0           ? LEAF_COLOR
                      : INTERNAL_COLOR;
        } else {
            boolean isLeaf = (node.left == null && node.right == null);
            fillColor = isLeaf ? LEAF_COLOR : INTERNAL_COLOR;
        }

        // Sombra sutil
        gc.setFill(Color.color(0, 0, 0, 0.3));
        gc.fillOval(x - NODE_RADIUS + 3, y - NODE_RADIUS + 3, NODE_RADIUS * 2, NODE_RADIUS * 2);

        // Círculo del nodo
        gc.setFill(fillColor);
        gc.fillOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);

        // Borde blanco
        gc.setStroke(Color.color(1, 1, 1, 0.25));
        gc.setLineWidth(1.5);
        gc.strokeOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);

        // Texto del valor
        gc.setFill(TEXT_COLOR);
        gc.setFont(Font.font("System", FontWeight.BOLD, 13));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(String.valueOf(node.data), x, y + 5);

        // Para AVL: mostrar FE encima del nodo
        if (isAVL) {
            int fe = getBalanceFactor(node);
            gc.setFont(Font.font("System", FontWeight.NORMAL, 10));
            gc.setFill(Color.LIGHTGRAY);
            gc.fillText("FE=" + fe, x, y - NODE_RADIUS - 4);
        }
    }

    /**
     * Calcula el FE de un nodo sin acceso al AVL — recalculando alturas localmente.
     */
    private int getBalanceFactor(BTreeNode<Integer> node) {
        if (node == null) return 0;
        return nodeHeight(node.left) - nodeHeight(node.right);
    }

    private int nodeHeight(BTreeNode<Integer> node) {
        if (node == null) return 0;
        return 1 + Math.max(nodeHeight(node.left), nodeHeight(node.right));
    }

    // ════════════════════════════════════════════════════════════════════════
    //  UTILIDADES
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Construye el camino de búsqueda de BST/AVL en forma de lista ordenada.
     * Devuelve lista vacía si el elemento no se encuentra.
     */
    private List<Integer> buildSearchPath(BTreeNode<Integer> root, Integer target) {
        List<Integer> path = new ArrayList<>();
        BTreeNode<Integer> current = root;
        while (current != null) {
            path.add(current.data);
            if (target.equals(current.data)) return path;
            current = target < current.data ? current.left : current.right;
        }
        return Collections.emptyList(); // no encontrado
    }

    /**
     * Actualiza el label de estadísticas del árbol.
     */
    private <T extends Comparable<T>> void updateStats(Label label, BTree<T> tree,
                                                       boolean showBalance, boolean balanced) {
        if (tree.isEmpty()) {
            label.setText("Nodos: 0  |  Altura: 0" + (showBalance ? "  |  Balanceado..." : ""));
            return;
        }
        try {
            String txt = "Nodos: " + tree.size() + "  |  Altura: " + tree.height();
            if (showBalance) txt += "  |  " + (balanced ? "✔ Balanceado" : "✘ Desbalanceado");
            label.setText(txt);
        } catch (TreeException e) {
            label.setText("Error al calcular estadísticas");
        }
    }

    /**
     * Parsea el valor entero del TextField dado.
     * Muestra un mensaje de error en el label si el input es inválido.
     *
     * @return el valor entero o null si hay error de formato
     */
    private Integer parseInput(TextField field, Label statusLabel) {
        try {
            return Integer.parseInt(field.getText().trim());
        } catch (NumberFormatException e) {
            setStatus(statusLabel, "✘ Ingrese un número entero válido.", false);
            return null;
        }
    }

    /**
     * Establece el texto y estilo del label de estado.
     *
     * @param label   label a actualizar
     * @param message mensaje a mostrar
     * @param ok      true = color verde, false = color rojo
     */
    private void setStatus(Label label, String message, boolean ok) {
        label.setText(message);
        label.setStyle(ok ? "-fx-text-fill: #4caf50;" : "-fx-text-fill: #f44336;");
    }

    /**
     * Extrae el tipo de rotación de la cadena de path del nodo AVL.
     */
    private String extractRotationType(String path) {
        if (path.contains("LL")) return "LL – Rotación Simple Derecha";
        if (path.contains("RR")) return "RR – Rotación Simple Izquierda";
        if (path.contains("LR")) return "LR – Doble Rotación Izq-Der";
        if (path.contains("RL")) return "RL – Doble Rotación Der-Izq";
        return "Sin rotación";
    }

}