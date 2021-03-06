
/**
 * NOTE - this file is auto-generated using Scalate. 
 * 
 * DO NOT EDIT!
 */
package org.fusesource.ide.camel.model.generated;

import java.util.Map;
import org.apache.camel.model.InterceptSendToEndpointDefinition;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.model.language.ExpressionDefinition;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.fusesource.ide.camel.model.AbstractNode;
import org.fusesource.ide.camel.model.ExpressionPropertyDescriptor;
import org.fusesource.ide.camel.model.RouteContainer;
import org.fusesource.ide.camel.model.util.Objects;
import org.fusesource.ide.commons.properties.BooleanPropertyDescriptor;
import org.fusesource.ide.commons.properties.ComplexPropertyDescriptor;
import org.fusesource.ide.commons.properties.ComplexUnionPropertyDescriptor;
import org.fusesource.ide.commons.properties.EnumPropertyDescriptor;
import org.fusesource.ide.commons.properties.ListPropertyDescriptor;
import org.fusesource.ide.commons.properties.UnionTypeValue;

/**
 * The Node class for Camel's InterceptSendToEndpointDefinition
 */
public class InterceptSendToEndpoint extends AbstractNode {

	public static final String PROPERTY_URI = "InterceptSendToEndpoint.Uri";
	public static final String PROPERTY_SKIPSENDTOORIGINALENDPOINT = "InterceptSendToEndpoint.SkipSendToOriginalEndpoint";
	
	private String uri;
	private Boolean skipSendToOriginalEndpoint;
	
    public InterceptSendToEndpoint() {
    }		
	
    public InterceptSendToEndpoint(InterceptSendToEndpointDefinition definition, RouteContainer parent) {

      super(parent);
    	loadPropertiesFromCamelDefinition(definition);
    	loadChildrenFromCamelDefinition(definition);
    }


    /* (non-Javadoc)
     * @see org.fusesource.ide.camel.model.AbstractNode#getIconName()
     */
    @Override
    public String getIconName() {
    	return "generic.png";
    }
    
  	@Override
  	public String getDocumentationFileName() {
  		return "interceptSendToEndpointEIP";
  	}
  	
  	@Override
  	public String getCategoryName() {
  		return "Control Flow";
  	}


	

	/**
	 * @return the uri
	 */
	public String getUri() {
		return this.uri;
	}
	
	/**
	 * @param uri the uri to set
	 */
	public void setUri(String uri) {
		String oldValue = this.uri;
		this.uri = uri;
		firePropertyChange(PROPERTY_URI, oldValue, uri);
	}

	/**
	 * @return the skipSendToOriginalEndpoint
	 */
	public Boolean getSkipSendToOriginalEndpoint() {
		return this.skipSendToOriginalEndpoint;
	}
	
	/**
	 * @param skipSendToOriginalEndpoint the skipSendToOriginalEndpoint to set
	 */
	public void setSkipSendToOriginalEndpoint(Boolean skipSendToOriginalEndpoint) {
		Boolean oldValue = this.skipSendToOriginalEndpoint;
		this.skipSendToOriginalEndpoint = skipSendToOriginalEndpoint;
		firePropertyChange(PROPERTY_SKIPSENDTOORIGINALENDPOINT, oldValue, skipSendToOriginalEndpoint);
	}


	
	/*
	 * (non-Javadoc)
	 * @see org.fusesource.ide.camel.model.AbstractNode#addCustomProperties(java.util.Map)
	 */
	@Override
	protected void addCustomProperties(Map<String, PropertyDescriptor> descriptors) {
		super.addCustomProperties(descriptors);
		
  		PropertyDescriptor descUri = new TextPropertyDescriptor(PROPERTY_URI, Messages.propertyLabelInterceptSendToEndpointUri);
      	PropertyDescriptor descSkipSendToOriginalEndpoint = new BooleanPropertyDescriptor(PROPERTY_SKIPSENDTOORIGINALENDPOINT, Messages.propertyLabelInterceptSendToEndpointSkipSendToOriginalEndpoint);
  		descriptors.put(PROPERTY_URI, descUri);
		descriptors.put(PROPERTY_SKIPSENDTOORIGINALENDPOINT, descSkipSendToOriginalEndpoint);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void setPropertyValue(Object id, Object value) {
		if (PROPERTY_URI.equals(id)) {
			setUri(Objects.convertTo(value, String.class));
		}		else if (PROPERTY_SKIPSENDTOORIGINALENDPOINT.equals(id)) {
			setSkipSendToOriginalEndpoint(Objects.convertTo(value, Boolean.class));
		}    else {
			super.setPropertyValue(id, value);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.fusesource.ide.camel.model.AbstractNode#getPropertyValue(java.lang.Object)
	 */
	@Override
	public Object getPropertyValue(Object id) {
		if (PROPERTY_URI.equals(id)) {
			return this.getUri();
		}		else if (PROPERTY_SKIPSENDTOORIGINALENDPOINT.equals(id)) {
			return this.getSkipSendToOriginalEndpoint();
		}    else {
			return super.getPropertyValue(id);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ProcessorDefinition createCamelDefinition() {
		InterceptSendToEndpointDefinition answer = new InterceptSendToEndpointDefinition();
    answer.setUri(toXmlPropertyValue(PROPERTY_URI, this.getUri()));
    answer.setSkipSendToOriginalEndpoint(toXmlPropertyValue(PROPERTY_SKIPSENDTOORIGINALENDPOINT, this.getSkipSendToOriginalEndpoint()));
        super.savePropertiesToCamelDefinition(answer);
		return answer;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class<?> getCamelDefinitionClass() {
	  return InterceptSendToEndpointDefinition.class;
  }

	@SuppressWarnings("rawtypes")
	@Override
	protected void loadPropertiesFromCamelDefinition(ProcessorDefinition processor) {
    super.loadPropertiesFromCamelDefinition(processor);
    
    if (processor instanceof InterceptSendToEndpointDefinition) {
      InterceptSendToEndpointDefinition node = (InterceptSendToEndpointDefinition) processor;
      this.setUri(node.getUri());
      this.setSkipSendToOriginalEndpoint(node.getSkipSendToOriginalEndpoint());
    } else {
      throw new IllegalArgumentException("ProcessorDefinition not an instanceof InterceptSendToEndpointDefinition. Was " + processor.getClass().getName());
    }
	}
}
 
      
