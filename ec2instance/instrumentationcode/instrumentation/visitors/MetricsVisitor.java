package instrumentation.visitors;

import instrumentation.Metric;

import instrumentation.observers.Observer;

import java.lang.NullPointerException;

import java.util.ArrayList;

public abstract class MetricsVisitor {
	ArrayList<Observer> _observers = new ArrayList<Observer>();

	abstract public void visit(Metric m);

	public void addObserver(Observer o) {
		if (o != null) {
			_observers.add(o);
		} else {
			throw new NullPointerException();
		}
	}

	public void removeObserver(Observer o) {
		if (o != null) {
			_observers.remove(o);
		} else {
			throw new NullPointerException();
		}
	}
}