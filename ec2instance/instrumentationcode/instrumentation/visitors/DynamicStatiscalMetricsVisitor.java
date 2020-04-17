package instrumentation.visitors;

import java.util.ArrayList;

import instrumentation.Metric;

import instrumentation.dataview.DirectoryDataView;

public class DynamicStatiscalMetricsVisitor implements MetricsVisitor {
	public void visit(Metric m) {
		System.out.println("Aplicando metricas dinamicas...");

		
	}
}