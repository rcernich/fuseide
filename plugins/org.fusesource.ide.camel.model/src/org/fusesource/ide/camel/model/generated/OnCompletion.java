
/**
 * NOTE - this file is auto-generated using Scalate. 
 * 
 * DO NOT EDIT!
 */
package org.fusesource.ide.camel.model.generated;

import java.util.Map;
import org.apache.camel.model.OnCompletionDefinition;
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
 * The Node class for Camel's OnCompletionDefinition
 */
public class OnCompletion extends AbstractNode {

	public static final String PROPERTY_EXECUTORSERVICEREF = "OnCompletion.ExecutorServiceRef";
	public static final String PROPERTY_ONCOMPLETEONLY = "OnCompletion.OnCompleteOnly";
	public static final String PROPERTY_ONFAILUREONLY = "OnCompletion.OnFailureOnly";
	public static final String PROPERTY_USEORIGINALMESSAGEPOLICY = "OnCompletion.UseOriginalMessagePolicy";
	
	private String executorServiceRef;
	private Boolean onCompleteOnly;
	private Boolean onFailureOnly;
	private Boolean useOriginalMessagePolicy;
	
    public OnCompletion() {
    }		
	
    public OnCompletion(OnCompletionDefinition definition, RouteContainer parent) {

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
  		return "onCompleteEIP";
  	}
  	
  	@Override
  	public String getCategoryName() {
  		return "Control Flow";
  	}


	

	/**
	 * @return the executorServiceRef
	 */
	public String getExecutorServiceRef() {
		return this.executorServiceRef;
	}
	
	/**
	 * @param executorServiceRef the executorServiceRef to set
	 */
	public void setExecutorServiceRef(String executorServiceRef) {
		String oldValue = this.executorServiceRef;
		this.executorServiceRef = executorServiceRef;
		firePropertyChange(PROPERTY_EXECUTORSERVICEREF, oldValue, executorServiceRef);
	}

	/**
	 * @return the onCompleteOnly
	 */
	public Boolean getOnCompleteOnly() {
		return this.onCompleteOnly;
	}
	
	/**
	 * @param onCompleteOnly the onCompleteOnly to set
	 */
	public void setOnCompleteOnly(Boolean onCompleteOnly) {
		Boolean oldValue = this.onCompleteOnly;
		this.onCompleteOnly = onCompleteOnly;
		firePropertyChange(PROPERTY_ONCOMPLETEONLY, oldValue, onCompleteOnly);
	}

	/**
	 * @return the onFailureOnly
	 */
	public Boolean getOnFailureOnly() {
		return this.onFailureOnly;
	}
	
	/**
	 * @param onFailureOnly the onFailureOnly to set
	 */
	public void setOnFailureOnly(Boolean onFailureOnly) {
		Boolean oldValue = this.onFailureOnly;
		this.onFailureOnly = onFailureOnly;
		firePropertyChange(PROPERTY_ONFAILUREONLY, oldValue, onFailureOnly);
	}

	/**
	 * @return the useOriginalMessagePolicy
	 */
	public Boolean getUseOriginalMessagePolicy() {
		return this.useOriginalMessagePolicy;
	}
	
	/**
	 * @param useOriginalMessagePolicy the useOriginalMessagePolicy to set
	 */
	public void setUseOriginalMessagePolicy(Boolean useOriginalMessagePolicy) {
		Boolean oldValue = this.useOriginalMessagePolicy;
		this.useOriginalMessagePolicy = useOriginalMessagePolicy;
		firePropertyChange(PROPERTY_USEORIGINALMESSAGEPOLICY, oldValue, useOriginalMessagePolicy);
	}


	
	/*
	 * (non-Javadoc)
	 * @see org.fusesource.ide.camel.model.AbstractNode#addCustomProperties(java.util.Map)
	 */
	@Override
	protected void addCustomProperties(Map<String, PropertyDescriptor> descriptors) {
		super.addCustomProperties(descriptors);
		
  		PropertyDescriptor descExecutorServiceRef = new TextPropertyDescriptor(PROPERTY_EXECUTORSERVICEREF, Messages.propertyLabelOnCompletionExecutorServiceRef);
      	PropertyDescriptor descOnCompleteOnly = new BooleanPropertyDescriptor(PROPERTY_ONCOMPLETEONLY, Messages.propertyLabelOnCompletionOnCompleteOnly);
      	PropertyDescriptor descOnFailureOnly = new BooleanPropertyDescriptor(PROPERTY_ONFAILUREONLY, Messages.propertyLabelOnCompletionOnFailureOnly);
      	PropertyDescriptor descUseOriginalMessagePolicy = new BooleanPropertyDescriptor(PROPERTY_USEORIGINALMESSAGEPOLICY, Messages.propertyLabelOnCompletionUseOriginalMessagePolicy);
  		descriptors.put(PROPERTY_EXECUTORSERVICEREF, descExecutorServiceRef);
		descriptors.put(PROPERTY_ONCOMPLETEONLY, descOnCompleteOnly);
		descriptors.put(PROPERTY_ONFAILUREONLY, descOnFailureOnly);
		descriptors.put(PROPERTY_USEORIGINALMESSAGEPOLICY, descUseOriginalMessagePolicy);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void setPropertyValue(Object id, Object value) {
		if (PROPERTY_EXECUTORSERVICEREF.equals(id)) {
			setExecutorServiceRef(Objects.convertTo(value, String.class));
		}		else if (PROPERTY_ONCOMPLETEONLY.equals(id)) {
			setOnCompleteOnly(Objects.convertTo(value, Boolean.class));
		}		else if (PROPERTY_ONFAILUREONLY.equals(id)) {
			setOnFailureOnly(Objects.convertTo(value, Boolean.class));
		}		else if (PROPERTY_USEORIGINALMESSAGEPOLICY.equals(id)) {
			setUseOriginalMessagePolicy(Objects.convertTo(value, Boolean.class));
		}    else {
			super.setPropertyValue(id, value);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.fusesource.ide.camel.model.AbstractNode#getPropertyValue(java.lang.Object)
	 */
	@Override
	public Object getPropertyValue(Object id) {
		if (PROPERTY_EXECUTORSERVICEREF.equals(id)) {
			return this.getExecutorServiceRef();
		}		else if (PROPERTY_ONCOMPLETEONLY.equals(id)) {
			return this.getOnCompleteOnly();
		}		else if (PROPERTY_ONFAILUREONLY.equals(id)) {
			return this.getOnFailureOnly();
		}		else if (PROPERTY_USEORIGINALMESSAGEPOLICY.equals(id)) {
			return this.getUseOriginalMessagePolicy();
		}    else {
			return super.getPropertyValue(id);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ProcessorDefinition createCamelDefinition() {
		OnCompletionDefinition answer = new OnCompletionDefinition();
    answer.setExecutorServiceRef(toXmlPropertyValue(PROPERTY_EXECUTORSERVICEREF, this.getExecutorServiceRef()));
    answer.setOnCompleteOnly(toXmlPropertyValue(PROPERTY_ONCOMPLETEONLY, this.getOnCompleteOnly()));
    answer.setOnFailureOnly(toXmlPropertyValue(PROPERTY_ONFAILUREONLY, this.getOnFailureOnly()));
    answer.setUseOriginalMessagePolicy(toXmlPropertyValue(PROPERTY_USEORIGINALMESSAGEPOLICY, this.getUseOriginalMessagePolicy()));
        super.savePropertiesToCamelDefinition(answer);
		return answer;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class<?> getCamelDefinitionClass() {
	  return OnCompletionDefinition.class;
  }

	@SuppressWarnings("rawtypes")
	@Override
	protected void loadPropertiesFromCamelDefinition(ProcessorDefinition processor) {
    super.loadPropertiesFromCamelDefinition(processor);
    
    if (processor instanceof OnCompletionDefinition) {
      OnCompletionDefinition node = (OnCompletionDefinition) processor;
      this.setExecutorServiceRef(node.getExecutorServiceRef());
      this.setOnCompleteOnly(node.getOnCompleteOnly());
      this.setOnFailureOnly(node.getOnFailureOnly());
      this.setUseOriginalMessagePolicy(node.getUseOriginalMessagePolicy());
    } else {
      throw new IllegalArgumentException("ProcessorDefinition not an instanceof OnCompletionDefinition. Was " + processor.getClass().getName());
    }
	}
}
 
      
