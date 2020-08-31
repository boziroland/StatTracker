package org.github.boziroland.ui;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import org.apache.commons.lang3.mutable.MutableInt;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.XYSeries;
import org.vaadin.addon.JFreeChartWrapper;

import java.awt.*;
import java.util.List;

public final class ChartCreator {

	public static JFreeChartWrapper createChart(CategoryDataset dataset) {
		return new JFreeChartWrapper(createJFreeChart(dataset));
	}

	public static Component createChart(String name, List<Long> data) {
		var ret = new VerticalLayout();
		ret.setSpacing(true);
		//ret.addComponent(new JFreeChartWrapper(createJFreeChart(createDataset())));
		//ret.addComponent(getLevelChart());

		return getLevelChart(name, data);//new JFreeChartWrapper(createJFreeChart(createDataset()));
	}

	//TODO na gl boii
	private static CategoryDataset createDataset() {

		// row keys...
		String series1 = "First";
		String series2 = "Second";
		String series3 = "Third";

		// column keys...
		String category1 = "Category 1";
		String category2 = "Category 2";
		String category3 = "Category 3";
		String category4 = "Category 4";
		String category5 = "Category 5";

		// create the dataset...
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		dataset.addValue(1.0, series1, category1);
		dataset.addValue(4.0, series1, category2);
		dataset.addValue(3.0, series1, category3);
		dataset.addValue(5.0, series1, category4);
		dataset.addValue(5.0, series1, category5);

		dataset.addValue(5.0, series2, category1);
		dataset.addValue(7.0, series2, category2);
		dataset.addValue(6.0, series2, category3);
		dataset.addValue(8.0, series2, category4);
		dataset.addValue(4.0, series2, category5);

		dataset.addValue(4.0, series3, category1);
		dataset.addValue(3.0, series3, category2);
		dataset.addValue(2.0, series3, category3);
		dataset.addValue(3.0, series3, category4);
		dataset.addValue(6.0, series3, category5);

		return dataset;

	}

	private static JFreeChart createJFreeChart(CategoryDataset dataset) {

		// create the chart...
		JFreeChart chart = ChartFactory.createBarChart("Bar Chart Demo 1", // chart
				// title
				"Category", // domain axis label
				"Value", // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips?
				false // URLs?
		);

		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

		// set the background color for the chart...
		chart.setBackgroundPaint(Color.black);

		// get a reference to the plot for further customisation...
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.darkGray);
		plot.setDomainGridlinePaint(Color.black);
		plot.setDomainGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.black);

		// ******************************************************************
		// More than 150 demo applications are included with the JFreeChart
		// Developer Guide...for more information, see:
		//
		// > http://www.object-refinery.com/jfreechart/guide.html
		//
		// ******************************************************************

		// set the range axis to display integers only...
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		// disable bar outlines...
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		// renderer.setDrawBarOutline(false);

		// set up gradient paints for series...
		GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.blue, 0.0f,
				0.0f, new Color(0, 0, 64));
		GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, Color.green, 0.0f,
				0.0f, new Color(0, 64, 0));
		GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, Color.red, 0.0f,
				0.0f, new Color(64, 0, 0));
		renderer.setSeriesPaint(0, gp0);
		renderer.setSeriesPaint(1, gp1);
		renderer.setSeriesPaint(2, gp2);

		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions
				.createUpRotationLabelPositions(Math.PI / 6.0));
		// OPTIONAL CUSTOMISATION COMPLETED.

		return chart;
	}

	private static Component getLevelChart(String name, List<Long> data) {

		DefaultTableXYDataset ds = new DefaultTableXYDataset();
		NumberAxis y = new NumberAxis("Y");

		XYSeries series;

		DefaultTableXYDataset ds2 = new DefaultTableXYDataset();
		series = new XYSeries("DOO", false, false);
		for(int i = data.size() - 1; i >= 0; i--){
			series.add(i, data.get(i));
		}

		ds2.addSeries(series);

		XYAreaRenderer r = new XYAreaRenderer(XYAreaRenderer.AREA_AND_SHAPES);

		XYPlot plot2 = new XYPlot(ds2, new NumberAxis("X"), y,
				new XYLineAndShapeRenderer());

		plot2.setBackgroundPaint(Color.darkGray);
		plot2.setDomainGridlinePaint(Color.white);
		plot2.setDomainGridlinesVisible(true);
		plot2.setRangeGridlinePaint(Color.white);

		plot2.setDataset(1, ds);

		plot2.setRenderer(1, r);

		JFreeChart c = new JFreeChart(plot2);
		c.setBackgroundPaint(Color.darkGray);

		return new JFreeChartWrapper(c);
	}

}
