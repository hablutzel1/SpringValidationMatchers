package org.openmrs.hamcrest.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.openmrs.hamcrest.matchers.util.TestUtil;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

/**
 * Se basa en algo semejante a org.junit.internal.matchers.CombinableMatcher to support fluent api like this one: modelo, hasFieldError("codigo").withCode(""); TODO buscar la manera de que describeMismatch sea llamado para describir los errores. TODO create factory method to get stateless instances
 * (singletons)
 * 
 * @param <T>
 */
public class FieldBindingMatcher<T> extends BaseMatcher<T> {
	
	private Validator validator;
	
	private String objectName;
	
	private String field;
	
	public FieldBindingMatcher(Validator validator, String objectName, String field) {
		this.validator = validator;
		this.objectName = objectName;
		this.field = field;
	}
	
	@Override
	public void describeMismatch(Object item, Description description) {
		description.appendText(" not errored in field: '" + field + "'");
		//super.describeMismatch(item, description);    
	}
	
	/**
	 * This can be used fluently after using an instance of FieldBindingMatcher as
	 * <p/>
	 * hasFieldError("foo").withCode("error.missing");
	 * 
	 * @param code
	 * @return
	 */
	public CodeBindingMatcher<T> withCode(String code) {
		CodeBindingMatcher bindingMatcher = new CodeBindingMatcher(field, code, objectName);
		bindingMatcher.setValidator(validator);
		return bindingMatcher;
	}
	
	public boolean matches(Object item) {
		
		BindingResult result = TestUtil.validarObjeto(validator, item, objectName);
		int b = TestUtil.countErroredFieldWithNameAndCode(result, field, null);
		if (b > 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public void describeTo(Description description) {
		description.appendText(" error in field '" + field + "'");
		
		
	}
	
}
