package org.fusesource.ide.fabric.activemq.navigator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.apache.activemq.broker.jmx.TopicViewMBean;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.fusesource.fabric.activemq.facade.BrokerFacade;
import org.fusesource.ide.commons.tree.ConnectedNode;
import org.fusesource.ide.commons.tree.GraphableNode;
import org.fusesource.ide.commons.tree.Node;
import org.fusesource.ide.commons.ui.ImageProvider;
import org.fusesource.ide.commons.ui.Shells;
import org.fusesource.ide.fabric.FabricPlugin;
import org.fusesource.ide.fabric.activemq.Messages;
import org.fusesource.ide.jmx.ui.internal.views.navigator.ContextMenuProvider;


public class TopicNode extends DestinationNodeSupport  implements ImageProvider, ContextMenuProvider, GraphableNode, ConnectedNode {

	private final TopicsNode topicsNode;
	private final TopicViewMBean topic;
	private final TopicConsumersNode consumersNode;
	private final TopicProducersNode producersNode;
	private final TopicDurableConsumersNode durableConsumersNode;
		
	public TopicNode(TopicsNode topicsNode, TopicViewMBean topic) {
		super(topicsNode, topicsNode.getBrokerNode(), topic);
		this.topicsNode = topicsNode;
		this.topic = topic;
		consumersNode = new TopicConsumersNode(this);
		durableConsumersNode = new TopicDurableConsumersNode(this);
		producersNode = new TopicProducersNode(this);
		addChild(consumersNode);
		addChild(durableConsumersNode);
		addChild(producersNode);
		setPropertyBean(topic);
	}

	@Override
	public String toString() {
		return topic.getName();
	}

	public TopicsNode getTopicsNode() {
		return topicsNode;
	}

	public TopicViewMBean getTopic() {
		return topic;
	}

	public List<Node> getConsumerChildren() {
		List<Node> answer = new ArrayList<Node>();
		answer.addAll(Arrays.asList(consumersNode.getChildren()));
		answer.addAll(Arrays.asList(durableConsumersNode.getChildren()));
		return answer;
	}
	
	public List<Node> getChildrenGraph() {
		List<Node> answer = getConsumerChildren();
		answer.addAll(Arrays.asList(producersNode.getChildren()));
		return answer;
	}
	
	public List<?> getConnectedTo() {
		return getConsumerChildren();
	}

	
	@Override
	public Image getImage() {
		return FabricPlugin.getDefault().getImage("topic.png");
	}

	@Override
	public void provideContextMenu(IMenuManager menu) {
		Action deleteTopicAction = new Action(Messages.DeleteTopicAction, SWT.CHECK) {
			public void run() {
				showDeleteTopicDialog();
			}

		};
		deleteTopicAction.setToolTipText(Messages.DeleteTopicActionToolTip);
		deleteTopicAction.setImageDescriptor(FabricPlugin.getDefault().getImageDescriptor("delete.gif"));
		menu.add(deleteTopicAction);

	}

	protected void showDeleteTopicDialog() {
		String message = Messages.bind(Messages.DeleteTopicDialogMessage, topic.getName());
		boolean confirm = MessageDialog.openConfirm(Shells.getShell(), Messages.DeleteTopicDialogTitle,
				message);
		if (confirm) {
			deleteTopic();
		}
	}

	protected void deleteTopic() {
		String name = topic.getName();
		try {
			getFacade().getBrokerAdmin().removeTopic(name);
			topicsNode.refresh();
		} catch (Exception e) {
			FabricPlugin.showUserError("Failed to delete Topic", "Failed to delete topic: " + topic, e);
		}
	}

	protected BrokerFacade getFacade() {
		return getBrokerNode().getFacade();
	}
}
