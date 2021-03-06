package org.fusesource.ide.camel.editor.validation;

import java.util.LinkedList;
import java.util.List;

/**
 * @author lhein
 */
public class ValidationResult {
	private List<String> infos;
	private List<String> warnings;
	private List<String> errors;
	
	/**
	 * constructor
	 */
	public ValidationResult() {
		this.infos = new LinkedList<String>();
		this.warnings = new LinkedList<String>();
		this.errors = new LinkedList<String>();
	}
	
	public List<String> getInformations() {
		return this.infos;
	}
	
	public List<String> getWarnings() {
		return this.warnings;
	}
	
	public List<String> getErrors() {
		return this.errors;
	}
	
	public void addInfo(String message) {
		this.infos.add(message);
	}
	
	public void addWarning(String message) {
		this.warnings.add(message);
	}
	
	public void addError(String message) {
		this.errors.add(message);
	}
	
	public void clear() {
		this.infos.clear();
		this.warnings.clear();
		this.errors.clear();
	}
	
	public int getInformationCount() {
		return this.infos.size();
	}
	
	public int getWarningCount() {
		return this.warnings.size();
	}
	
	public int getErrorCount() {
		return this.errors.size();
	}
}
