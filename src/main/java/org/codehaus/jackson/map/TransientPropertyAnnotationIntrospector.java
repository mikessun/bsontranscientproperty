package org.codehaus.jackson.map;

import org.codehaus.jackson.map.introspect.Annotated;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;

import com.michaelssun.annotation.TransientProperty;

/**
 * ignore the properties annotated with TransientProperty when doing serializing actions in persistence during
 * java object serialization; mostly used in DAO layer.
 * 
 *
 */
public class TransientPropertyAnnotationIntrospector extends JacksonAnnotationIntrospector {

	@Override
	protected boolean _isIgnorable(Annotated a) {
		TransientProperty ann = a.getAnnotation(TransientProperty.class);
		if (ann != null && ann.value())
			return ann.value();
		return super._isIgnorable(a);
	}
}
