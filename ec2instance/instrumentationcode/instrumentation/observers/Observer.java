package instrumentation.observers;

import instrumentation.dataview.SingleClassStaticMetricsDataView;
import instrumentation.dataview.GlobalStaticMetricsDataView;

public interface Observer {
	public void notify(SingleClassStaticMetricsDataView singleClassStaticData);
	public void notify(GlobalStaticMetricsDataView globalStaticData);
}