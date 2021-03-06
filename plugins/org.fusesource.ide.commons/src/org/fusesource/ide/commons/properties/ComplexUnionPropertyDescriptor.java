package org.fusesource.ide.commons.properties;

import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.fusesource.ide.commons.util.ReturnType;


/**
 * A complex property which has a list of possible value types
 * 
 * @author jstrachan
 */
public class ComplexUnionPropertyDescriptor extends PropertyDescriptor implements ReturnType {

	private final UnionTypeValue[] valueTypes;
	private final Class<?> propertyType;

	/**
	 * creates a property descriptor for complex properties
	 * 
	 * @param id	the id
	 * @param displayName	the display name
	 */
	public ComplexUnionPropertyDescriptor(Object id, String displayName, Class<?> propertyType, UnionTypeValue[] valueTypes) {
		super(id, displayName);
		this.propertyType = propertyType;
		this.valueTypes = valueTypes;
	}

	public UnionTypeValue[] getValueTypes() {
		return valueTypes;
	}

	public Class<?> getPropertyType() {
		return propertyType;
	}

	@Override
	public Class<?> getReturnType() {
		return propertyType;
	}
}
