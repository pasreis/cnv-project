package instrumentation.observers;

import instrumentation.dataview.SingleClassStaticMetricsDataView;
import instrumentation.dataview.GlobalStaticMetricsDataView;

public class StdoutObserver implements Observer {
	@Override
	public void notify(SingleClassStaticMetricsDataView singleClassStaticData) {
		System.out.println(singleClassStaticData);
	}

	@Override
	public void notify(GlobalStaticMetricsDataView globalStaticData) {
		System.out.println(globalStaticData);
	}
}