package br.com.uninove;

import java.util.Scanner;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class previsao {
    public static void main(String[] args) {
    	
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite a quantidade de meses: ");
        int quantidadeMeses = scanner.nextInt();

        double[] meses = new double[quantidadeMeses];
        double[] lucros = new double[quantidadeMeses];

        for (int i = 0; i < quantidadeMeses; i++) {
            System.out.print("Digite o lucro do mês " + (i + 1) + ": ");
            lucros[i] = scanner.nextDouble();
            meses[i] = i + 1;
        }

      
        double n = meses.length;
        double sumX = 0;
        double sumY = 0;
        double sumXY = 0;
        double sumX2 = 0;

        for (int i = 0; i < n; i++) {
            sumX += meses[i];
            sumY += lucros[i];
            sumXY += meses[i] * lucros[i];
            sumX2 += meses[i] * meses[i];
        }

        double mediaX = sumX / n;
        double mediaY = sumY / n;

        double b1 = (sumXY - (n * mediaX * mediaY)) / (sumX2 - (n * mediaX * mediaX));
        double b0 = mediaY - (b1 * mediaX);



        System.out.print("Digite a quantidade de meses que deseja prever com seus dados: ");
        int mesesFuturos = scanner.nextInt();
        
        double ultimoMes = meses[(int) (n - 1)];
        double[] lucrosPrevistos = new double[mesesFuturos];

        for (int i = 0; i < mesesFuturos; i++) {
            double mes = ultimoMes + i + 1;
            double lucroPrevisto = b0 + (b1 * mes);
            lucrosPrevistos[i] = lucroPrevisto;
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
                "Previsão de Lucro", 
                "Mês", 
                "Lucro", 
                dataset, 
                PlotOrientation.VERTICAL,
                true, 
                true, 
                false 
        );

        // Exibir gráfico em uma janela
        ChartFrame frame = new ChartFrame("Previsão de Lucro", chart);
        frame.pack();
        frame.setVisible(true);
    }
}
