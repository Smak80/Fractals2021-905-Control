package ru.smak.ui

import ru.smak.math.fractals.Fractal
import ru.smak.math.fractals.Mandelbrot
import ru.smak.ui.painting.CartesianPlane
import ru.smak.ui.painting.FractalPainter
import ru.smak.ui.painting.fractals.colorizers
import java.awt.Color
import java.awt.Dimension
import java.awt.Rectangle
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import javax.swing.*
import javax.swing.filechooser.FileNameExtensionFilter
import kotlin.random.Random

class MainFrame : JFrame() {
    private val fractalPanel: SelectablePanel
    private val plane: CartesianPlane

    private val mainPanel: GraphicsPanel
    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        mainPanel = GraphicsPanel()
        plane = CartesianPlane(
            -2.0, 1.0, -1.0, 1.0
        )

        minimumSize = Dimension(600, 400)


        fractalPanel = SelectablePanel(
            FractalPainter(plane, Mandelbrot).apply {
                colorizer = colorizers[Random.nextInt(colorizers.size)]
            }
        ).apply {
            background = Color.WHITE
            addSelectListener { r ->
                with(plane){
                    xSegment = Pair(xScr2Crt(r.x), xScr2Crt(r.x+r.width))
                    ySegment = Pair(yScr2Crt(r.y), yScr2Crt(r.y+r.height))
                }
                repaint()
            }
        }


        layout = GroupLayout(contentPane).apply {
            setHorizontalGroup(
                createSequentialGroup()
                    .addGap(4)
                    .addComponent(fractalPanel)
                    .addGap(4)

            )
            setVerticalGroup(
                createSequentialGroup()
                    .addGap(4)
                    .addComponent(fractalPanel)
                    .addGap(4)
            )
        }
    }
}