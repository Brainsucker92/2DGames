package ui;

import data.grid.Grid2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Dimension2D;

public class GridPanel extends JPanel {

    private Grid2D<?> grid;
    private Color gridColor = Color.BLACK;


    public GridPanel(Grid2D<?> grid) {
        super();
        this.grid = grid;
        init();
    }

    private void init() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                int x = e.getX();
                int y = e.getY();
                Dimension2D tileSize = getTileSize();
                int xIndex = (int) (x / tileSize.getWidth());
                int yIndex = (int) (y / tileSize.getHeight());
                Point point = new Point(xIndex, yIndex);
                tileClicked(point);
            }
        });
    }

    protected void tileClicked(Point point) {
        System.out.println(point.toString());
    }

    public Color getGridColor() {
        return gridColor;
    }

    public void setGridColor(Color gridColor) {
        this.gridColor = gridColor;
    }

    /**
     * Calculates the size of a single tile.
     *
     * @return The {@code Dimension} of the tile.
     */
    public Dimension2D getTileSize() {
        int width = this.getWidth();
        int height = this.getHeight();
        int rows = grid.getNumRows();
        int columns = grid.getNumColumns();

        double tileWidth = width / (double) columns;
        double tileHeight = height / (double) rows;
        Dimension2D dimension = new DoubleDimension();
        dimension.setSize(tileWidth, tileHeight);
        return dimension;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color oldColor = g.getColor();
        g.setColor(this.gridColor);
        drawGrid(g);
        g.setColor(oldColor);
    }

    private void drawGrid(Graphics g) {
        Dimension2D tileSize = this.getTileSize();
        drawVerticalLines(g, tileSize);
        drawHorizontalLines(g, tileSize);

    }

    private void drawHorizontalLines(Graphics g, Dimension2D tileSize) {
        int rows = grid.getNumRows();
        double tileHeight = tileSize.getHeight();
        int width = this.getWidth();
        for (int i = 0; i < rows; i++) {
            int lineY = (int) (i * tileHeight);
            g.drawLine(0, lineY, width, lineY);
        }
    }

    private void drawVerticalLines(Graphics g, Dimension2D tileSize) {
        int columns = grid.getNumColumns();
        double tileWidth = tileSize.getWidth();
        int height = this.getHeight();
        for (int i = 0; i < columns; i++) {
            int lineX = (int) (i * tileWidth);
            g.drawLine(lineX, 0, lineX, height);
        }
    }

    public class DoubleDimension extends Dimension2D {

        private double width;
        private double height;

        @Override
        public double getWidth() {
            return width;
        }

        @Override
        public double getHeight() {
            return height;
        }

        @Override
        public void setSize(double width, double height) {
            this.width = width;
            this.height = height;
        }

        public Dimension toDimension() {
            Dimension dimension = new Dimension();
            dimension.setSize(width, height);
            return dimension;
        }
    }
}
