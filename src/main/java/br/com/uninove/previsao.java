package br.com.uninove;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class previsao {
    public static void main(String[] args) {
        // Dados históricos de lucro
        double[] meses = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,11,12};
        double[] lucros = {3000, 3200, 3300, 2100, 2100, 1600, 1231, 1000, 200,158, 133,150};

        // Criar objeto de regressão linear
        SimpleRegression regression = new SimpleRegression();
        for (int i = 0; i < meses.length; i++) {
            regression.addData(meses[i], lucros[i]);
        }

        // Previsão de lucro para os próximos meses
        int mesesFuturos = 3;
        double ultimoMes = meses[meses.length - 1];
        double[] lucrosPrevistos = new double[mesesFuturos];
        for (int i = 0; i < mesesFuturos; i++) {
            lucrosPrevistos[i] = regression.predict(ultimoMes + i + 1);
        }

        // Criar dataset com os dados históricos e previstos
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries historicoSeries = new XYSeries("Histórico de Lucro");
        XYSeries previsaoSeries = new XYSeries("Previsão de Lucro");

        for (int i = 0; i < meses.length; i++) {
            historicoSeries.add(meses[i], lucros[i]);
        }

        for (int i = 0; i < mesesFuturos; i++) {
            previsaoSeries.add(ultimoMes + i + 1, lucrosPrevistos[i]);
        }

        dataset.addSeries(historicoSeries);
        dataset.addSeries(previsaoSeries);

        // Criar gráfico de linha
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Previsão de Lucro", // Título do gráfico
                "Mês", // Título do eixo X
                "Lucro", // Título do eixo Y
                dataset, // Dados do gráfico
                PlotOrientation.VERTICAL, // Orientação do gráfico
                true, // Exibir legenda
                true, // Exibir tooltips
                false // Não exibir URLs
        );

        // Exibir gráfico em uma janela
        ChartFrame frame = new ChartFrame("Previsão de Lucro", chart);
        frame.pack();
        frame.setVisible(true);
    }
}
