package org.github.boziroland.ui;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.XYSeries;
import org.vaadin.addon.JFreeChartWrapper;

import java.awt.*;
import java.util.List;

public final class ChartCreator {

	public static Component createChart(String name, List<Long> data) {
		var ret = new VerticalLayout();
		ret.setSpacing(true);

		return getLevelChart(name, data);
	}

	private static Component getLevelChart(String name, List<Long> data) {

		DefaultTableXYDataset ds = new DefaultTableXYDataset();
		NumberAxis xAxis = new NumberAxis("Days");
		xAxis.setLabelPaint(Color.white);
		xAxis.setTickLabelPaint(Color.white);
		NumberAxis yAxis = new NumberAxis(name);
		yAxis.setLabelPaint(Color.white);
		yAxis.setTickLabelPaint(Color.white);

		XYSeries series;

		DefaultTableXYDataset ds2 = new DefaultTableXYDataset();
		series = new XYSeries(name, false, false);

		for(int i = data.size() - 1; i >= 0; i--){
			series.add(i, data.get(i));
		}

		ds2.addSeries(series);

		XYAreaRenderer r = new XYAreaRenderer(XYAreaRenderer.AREA_AND_SHAPES);

		XYPlot plot2 = new XYPlot(ds2, xAxis, yAxis,
				new XYLineAndShapeRenderer());

		plot2.setBackgroundPaint(Color.darkGray.darker());
		plot2.setDomainGridlinePaint(Color.white);
		plot2.setDomainGridlinesVisible(true);
		plot2.setRangeGridlinePaint(Color.white);

		plot2.setDataset(1, ds);

		plot2.setRenderer(1, r);

		JFreeChart c = new JFreeChart(plot2);
		c.setBackgroundPaint(Color.darkGray.darker());

		return new JFreeChartWrapper(c);
	}

}
