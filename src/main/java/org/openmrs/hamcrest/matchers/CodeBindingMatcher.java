package org.openmrs.hamcrest.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.openmrs.hamcrest.matchers.util.TestUtil;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

/**
 * convert them to top level classes
 * <p/>
 * TODO eliminar referencias directas hacia esta clase, en vez de esta utilizar FieldBindingMatcher
 * 
 * @param <T>
 */
public class CodeBindingMatcher<T> extends TypeSafeMatcher<T> {
	
	private String code;
	
	private String fieldName;
	
	private String objectName;
	
	private int times;
	
	private boolean countTimes = false;
	
	public CodeBindingMatcher(String fieldName, String code, String objectName) {
		this.fieldName = fieldName;
		this.code = code;
		this.objectName = objectName;
		
	}
	
	protected CodeBindingMatcher(String fieldName, String code, String objectName, int times, boolean countTimes) {
		this.fieldName = fieldName;
		this.code = code;
		this.objectName = objectName;
		this.times = times;
		this.countTimes = countTimes;
		
	}
	
	@Override
	public boolean matchesSafely(T item) {
		validateBuilderState();
		
		BindingResult result = TestUtil.validarObjeto(validator, item, objectName);
		int actualTimes = TestUtil.countErroredFieldWithNameAndCode(result, fieldName, code);
		if (countTimes) {
			if (actualTimes == times) {
				
				return true;
			} else {
				return false;
			}
		} else {
			if (actualTimes > 0) {
				return true;
			} else {
				return false;
			}
		}
		
	}

	
	protected void describeMismatchSafely(T item, Description mismatchDescription) {
		mismatchDescription.appendText(" not errored in field: '" + fieldName + "'" + " with code: '" + code + "'");
		
	};
	
	private void validateBuilderState() {
		if (validator == null) {
			throw new IllegalStateException("Matcher haven't been fully built yet");
		}
	}
	
	public void describeTo(Description description) {
		description.appendText(" error in field '" + fieldName + "'" + "  with code '" + code + "'");
	}
	
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
	
	private Validator validator;
	
	public CodeBindingMatcher<T> times(int times) {
		CodeBindingMatcher codeBindingMatcher = new CodeBindingMatcher(fieldName, code, objectName, times, true);
		codeBindingMatcher.setValidator(validator);
		return codeBindingMatcher;
	}
}

/*********************************************************************************/

