package org.fusesource.ide.commons.ui.label;

import java.text.Format;

import org.fusesource.ide.commons.Activator;
import org.fusesource.ide.commons.util.Function1;


public class FormatFunctionLabelProvider extends FunctionColumnLabelProvider {
	private Format format;

	public FormatFunctionLabelProvider(Function1 function) {
		super(function);
	}


	@Override
	public String getText(Object object) {
		Object element = apply(object);
		if (element != null) {
			try {
				return getFormat().format(element);
			} catch (Exception e) {
				Activator.getLogger().warning("Failed to format " + element
						+ " of type " + element.getClass().getName()
						+ " using formatter: " + format + ". " + e, e);
			}
		}
		return null;
	}

	public Format getFormat() {
		if (format == null) {
			format = createFormat();
		}
		return format;
	}

	public void setFormat(Format format) {
		this.format = format;
	}

	protected Format createFormat() {
		// TODO Auto-generated method stub
		return null;
	}
}