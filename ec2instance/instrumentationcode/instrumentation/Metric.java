package instrumentation;

import instrumentation.MetricsVisitor;

public class Metric {
	public void accept(MetricsVisitor v) {
		v.visit(this);
	}
}