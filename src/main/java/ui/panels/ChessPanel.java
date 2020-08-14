package ui.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Dimension2D;

import data.grid.Grid2D;

public class ChessPanel extends GridPanel {

    private Color squareColorDark = Color.BLACK;
    private Color squareColorLight = Color.WHITE;

    public ChessPanel(Grid2D<?> grid) {
        super(grid);
        setGridEnabled(false);
    }

    public Color getSquareColorDark() {
        return squareColorDark;
    }

    public void setSquareColorDark(Color squareColorDark) {
        this.squareColorDark = squareColorDark;
    }

    public Color getSquareColorLight() {
        return squareColorLight;
    }

    public void setSquareColorLight(Color squareColorLight) {
        this.squareColorLight = squareColorLight;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int numRows = this.grid.getNumRows();
        int numColumns = this.grid.getNumColumns();
        Color oldColor = g.getColor();
        for (int y = 0; y < numColumns; y++) {
            for (int x = 0; x < numRows; x++) {
                Color squareColor = getSquareColor(y, x);
                Point tilePosition = this.getTilePosition(y, x);
                Dimension2D tileSize = getTileSize();
                g.setColor(squareColor);
                g.fillRect(tilePosition.x, tilePosition.y, ((int) tileSize.getWidth()), ((int) tileSize.getHeight()));
            }
        }
        g.setColor(oldColor);
    }

    private Color getSquareColor(int rowIndex, int columnIndex) {
        return (rowIndex % 2 == 0) ^ (columnIndex % 2 == 0) ? squareColorDark : squareColorLight;
    }
}
