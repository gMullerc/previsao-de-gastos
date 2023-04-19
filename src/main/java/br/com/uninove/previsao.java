package br.com.uninove;

import java.io.BufferedReader;
import java.io.FileReader;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import weka.classifiers.functions.LinearRegression;
import weka.core.DenseInstance;
import weka.core.Instances;

public class previsao {

	public static void main(String[] args) throws Exception {


		XYSeries predicted = new XYSeries("Gastos previstos");

		// Carrega o arquivo CSV
		BufferedReader reader = new BufferedReader(new FileReader("dados.ARFF"));
		Instances data = new Instances(reader);
		reader.close();

		
		XYSeries actual = new XYSeries("Gastos reais");
		actual.add(1, 1500.0);
		actual.add(2, 1800.0);
		actual.add(3, 10100.0);
		actual.add(4, 2200.0);
		actual.add(5, 2500.0);
		actual.add(6, 2800.0);
		actual.add(7, 3000.0);
		actual.add(8, 3300.0);
		actual.add(9, 3600.0);
		actual.add(10, 4000.0);
		actual.add(11, 4500.0);
		actual.add(12, 5000.0);

		// Especifica a variável dependente (gastos) e as variáveis independentes
		// (características)
		data.setClassIndex(data.numAttributes() - 1);

		// Treina o modelo de regressão linear
		LinearRegression model = new LinearRegression();
		model.buildClassifier(data);

		for (int i = 1; i <= 12; i++) {
			double[] valores = { 2023, i }; // Exemplo para previsão dos gastos de cada mês de 2022

			// Faz a previsão dos gastos para o mês desejado
			double prev_gastos = model.classifyInstance(new DenseInstance(1.0, valores));

			// Adiciona o valor previsto ao gráfico
			predicted.add(i, prev_gastos);
		}

		// Define os valores das características para o ano desejado
		double[] valores = { 2023, 5 }; // Exemplo para previsão dos gastos de janeiro de 2022

		// Cria uma nova instância para teste
		DenseInstance instance = new DenseInstance(1.0, valores);
		instance.setDataset(data);

		// Faz a previsão dos gastos para o ano desejado
		double prev_gastos = model.classifyInstance(instance);

		// Imprime o resultado da previsão
		System.out.println("Previsão de gastos para o mês 5 do ano 2023: " + prev_gastos);
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(actual);
		dataset.addSeries(predicted);

		// Cria o gráfico
		ChartFrame frame = new ChartFrame("Previsão de Gastos para 2023", ChartFactory.createXYLineChart(
		    "Previsão de Gastos para 2023", "Mês", "Gastos", dataset, PlotOrientation.VERTICAL,
		    true, true, false));

		// Exibe o gráfico
		frame.pack();
		frame.setVisible(true);
	}

}
