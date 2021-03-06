package org.fusesource.ide.fabric.camel.navigator;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.fusesource.fon.util.messages.NodeStatisticsContainer;
import org.fusesource.ide.fabric.camel.FabricCamelPlugin;
import org.fusesource.ide.fabric.camel.navigator.stats.model.CamelContextStatistics;
import org.fusesource.ide.fabric.camel.navigator.stats.model.IProcessorStatistics;
import org.fusesource.ide.fabric.camel.navigator.stats.model.ProcessorStatistics;
import org.fusesource.ide.fabric.camel.navigator.stats.model.RouteStatistics;


public class CachingCamelContextNodeStatisticsContainer implements NodeStatisticsContainer {
	private static final long THROTTLE_MILLIS = 300L;

	private final CamelContextNode camelContextNode;

	private long lastRequestTime = 0L;
	private Map<String, IProcessorStatistics> cache = new HashMap<String, IProcessorStatistics>();

	public CachingCamelContextNodeStatisticsContainer(CamelContextNode camelContextNode) {
		this.camelContextNode = camelContextNode;
	}

	@Override
	public IProcessorStatistics getNodeStats(String nodeId) {
		if (shouldQuery()) {
			// lets load the new data over JMX and unmarshal as XML here...
			String managementName = camelContextNode.getManagementName();
			try {
				String xml = camelContextNode.getFacade().dumpRoutesStatsAsXml(managementName);
				if (xml != null) {
					xml = xml.trim();
					if (xml.length() > 0) {
						CamelContextStatistics stats = getStatistics(xml);
						if (stats != null) {
							ArrayList<RouteStatistics> routeStatisticsList = stats.getRouteStatisticsList();
							if (routeStatisticsList != null) {
								Map<String, IProcessorStatistics> map = new HashMap<String, IProcessorStatistics>();
								for (RouteStatistics stat : routeStatisticsList) {
									String id = stat.getId();
									map.put(id, stat);

									ArrayList<ProcessorStatistics> procStats = stat.getProcessorStatisticsList();
									if (procStats != null) {
										for (ProcessorStatistics procStat : procStats) {
											map.put(procStat.getId(), procStat);
										}
									}
								}
								this.cache = map;
							}
						}
					}
				}
			} catch (Exception e) {
				FabricCamelPlugin.getLogger().error("Failed to query the JMX statistics of Camel: " + managementName, e);
			}
		}
		return cache.get(nodeId);
	}

	/**
	 * converts the xml dump into a statistics model
	 * 
	 * @param xmlDump
	 * @return the model or null on errors
	 */
	private CamelContextStatistics getStatistics(String xmlDump) {
		System.err.println("CamelContextNode.getStatistics() called...");
		CamelContextStatistics stats = null;
		try {
			JAXBContext context = JAXBContext.newInstance(CamelContextStatistics.class, RouteStatistics.class, ProcessorStatistics.class);
			Unmarshaller um = context.createUnmarshaller();
			stats = (CamelContextStatistics) um.unmarshal(new StringReader(xmlDump));
		} catch (Exception ex) {
			stats = null;
			System.err.println("Error retrieving the statistics for camel context: " + camelContextNode.getContextId());
		}
		return stats;
	}

	/**
	 * Returns true if we should re-query the stats for a JMX Camel Context
	 */
	protected boolean shouldQuery() {
		long now = System.currentTimeMillis();
		long delta = now - lastRequestTime;
		lastRequestTime = now;
		if (delta > THROTTLE_MILLIS || this.cache.isEmpty()) {
			return true;
		}
		return false;
	}
}
