package instrumentation;

public interface MetricsVisitor {
	void visit(Metric m);
}