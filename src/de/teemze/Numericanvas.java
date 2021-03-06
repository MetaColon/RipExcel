package de.teemze;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Adapted from the Numericat project
 */
public class Numericanvas extends JPanel
{
    private List<List<Coordinate>> coordinates = new ArrayList<>(0);
    List<Color> colors = Arrays.asList(Color.BLACK, Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA);
    private final static int padding = 1;
    private final static int labelPadding = 1;
    private List<String> labels = new ArrayList<>(0);

    private void paintBackGround(Graphics2D g, int width, int height)
    {
        // draw white background
        g.setColor(Color.WHITE);
        g.fillRect(
                padding + labelPadding,
                padding,
                width - (2 * padding) - labelPadding,
                height - 2 * padding - labelPadding);
    }

    private void paintXandYAxis(Graphics2D g, int width, int height)
    {
        // create x and y axes
        g.drawLine(padding + labelPadding, height - padding - labelPadding, padding + labelPadding, padding);
        g.drawLine(
                padding + labelPadding,
                height - padding - labelPadding,
                width - padding,
                height - padding - labelPadding);
    }

    Dimension size;
    @Override
    public void paintComponent(Graphics graphics) {
        if (size == null || !size.equals(getSize())) {
            size = getSize();
            Main.repaint();
            return;
        }

        super.paintComponent(graphics);
        if (!(graphics instanceof Graphics2D)) {
            graphics.drawString("Graphics is not Graphics2D, unable to render", 0, 0);
            return;
        }
        final Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final int width = getWidth();
        final int height = getHeight();

        paintBackGround(g, width, height);

        paintXandYAxis(g, width, height);

        for (int j = 0; j < coordinates.size(); j++)
        {
            if (coordinates.get(j) == null)
                continue;
            List<Coordinate> coordinates = this.coordinates.get(j);
            g.setColor(colors.get(j));
            for (int i = 1; i < coordinates.size(); i++)
                g.drawLine(coordinates.get(i - 1).roundX(), getSize().height - coordinates.get(i - 1).roundY(),
                        coordinates.get(i).roundX(), getSize().height - coordinates.get(i).roundY());
        }

        // Draw Legend
        for (int i = 0; i < colors.size() && i < labels.size(); i++) {
            g.setColor(colors.get(i));
            g.drawString(labels.get(i), 20, 30 + 20 * i);
        }
    }

    public void drawData(List<Coordinate> coordinates) {
        this.coordinates.clear();
        this.coordinates.add(coordinates);
        repaint();
    }

    public void drawMultipleData(List<List<Coordinate>> coordinates) {
        this.coordinates = coordinates;
        repaint();
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }
}
