package instrumentation;

import instrumentation.visitors.MetricsVisitor;

public class Metric {
	public void accept(MetricsVisitor v) {
		v.visit(this);
	}
}