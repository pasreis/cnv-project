package instrumentation.visitors;

import instrumentation.Metric;

public interface MetricsVisitor {
	public void visit(Metric m);
}