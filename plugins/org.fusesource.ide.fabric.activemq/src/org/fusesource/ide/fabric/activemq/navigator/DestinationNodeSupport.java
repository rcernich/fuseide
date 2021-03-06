package org.fusesource.ide.fabric.activemq.navigator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.activemq.broker.jmx.DestinationViewMBean;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.fusesource.fon.util.messages.IExchange;
import org.fusesource.fon.util.messages.IMessage;
import org.fusesource.ide.commons.tree.Node;
import org.fusesource.ide.commons.tree.NodeSupport;
import org.fusesource.ide.commons.ui.drop.DropHandler;
import org.fusesource.ide.commons.ui.drop.DropHandlerFactory;
import org.fusesource.ide.commons.util.Objects;
import org.fusesource.ide.commons.util.Strings;
import org.fusesource.ide.fabric.FabricPlugin;
import org.fusesource.ide.fabric.activemq.FabricActiveMQPlugin;
import org.fusesource.ide.fabric.navigator.MessageDropHandler;
import org.fusesource.ide.fabric.navigator.MessageDropTarget;



public abstract class DestinationNodeSupport extends NodeSupport implements MessageDropTarget, DropHandlerFactory {

	protected Set<String> ignoreSendHeaders = new HashSet<String>(Arrays.asList("JMSDestination", "JMSMessageID"));

	private final BrokerNode brokerNode;
	private final DestinationViewMBean destination;

	public DestinationNodeSupport(Node parent, BrokerNode brokerNode, DestinationViewMBean destination) {
		super(parent);
		this.brokerNode = brokerNode;
		this.destination = destination;
	}

	@Override
	public String toString() {
		return getName();
	}

	public String getName() {
		return destination.getName();
	}

	public DropHandler createDropHandler(DropTargetEvent event) {
		return new MessageDropHandler(this);
	}

	public void dropMessage(IMessage message) {
		Map<String, Object> headers = message.getHeaders();
		Map<String, Object> cleanHeaders = new HashMap<String, Object>();
		Set<Entry<String, Object>> entrySet = headers.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value != null && !ignoreSendHeaders.contains(key)) {
				if (Objects.equal("JMSReplyTo", key)) {
					value = JmsTypeConverters.toDestination(value);
				} else if (Objects.equal("JMSExpiration", key)) {
					value = JmsTypeConverters.toTimestamp(value);
				} else if (Objects.equal("JMSTimestamp", key)) {
					value = JmsTypeConverters.toTimestamp(value);
				} else if (Objects.equal("JMSDeliveryMode", key)) {
					value = JmsTypeConverters.toDeliveryMode(value);
				} else if (Objects.equal("JMSRedelivered", key)) {
					value = JmsTypeConverters.toBoolean(value);
				} else if (Objects.equal("JMSPriority", key)) {
					value = JmsTypeConverters.toInteger(value);
				}

				if (value != null) {
					cleanHeaders.put(key, value);
				}
			}
		}

		String body = Strings.getOrElse(message.getBody());

		try {
			// TODO store username/pwd on a queue basis?
			String userName = getBrokerNode().getUserName();
			String password = getBrokerNode().getPassword();
			if (userName != null && password != null) {
				destination.sendTextMessage(cleanHeaders, body, userName, password);
			} else {
				destination.sendTextMessage(cleanHeaders, body);
			}
		} catch (Exception e) {
			FabricPlugin.showUserError("Send message to " + this + " failed", "Could not send message to " + this, e);
		}
	}

	public BrokerNode getBrokerNode() {
		return brokerNode;
	}

	public DestinationViewMBean getDestination() {
		return destination;
	}

	protected IExchange createExchange(Object object) {
		return FabricActiveMQPlugin.getConverter().toExchange(object);
	}
}