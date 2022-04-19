import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.SlidingCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.Map;

public class TicketPriceChart extends JFrame{
    public TicketPriceChart(Map<String, Double> data){
        initUI(data);
    }

    private void initUI(Map<String, Double> data) {

        SlidingCategoryDataset dataset = createDataset(data);

        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = createChartPanel(chart);

        add(chartPanel);
        pack();
        setTitle("Fare Chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private SlidingCategoryDataset createDataset(Map<String, Double> data){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (String key : data.keySet())
            dataset.setValue(data.get(key), "Price", key);
        return new SlidingCategoryDataset(dataset,0, 10);
    }

    private JFreeChart createChart(SlidingCategoryDataset dataset) {
        return ChartFactory.createBarChart(
                "Fare",
                "",
                "Price",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);
    }

    private ChartPanel createChartPanel(JFreeChart chart){
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        return chartPanel;
    }
}
