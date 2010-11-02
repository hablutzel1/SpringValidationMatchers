package org.openmrs.hamcrest.matchers.util;


import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

public class TestUtilTest {
	
	/**
	 * @see TestUtil#countErroredFieldWithNameAndCode(BindingResult,String,String)
	 * @verifies devolver la cantidad de veces que un codigo de error se repite para un determinado campo
	 */
	@Test
	public void countErroredFieldWithNameAndCode_shouldDevolverLaCantidadDeVecesQueUnCodigoDeErrorSeRepiteParaUnDeterminadoCampo()
	                                                                                                                              throws Exception {
		
		//  crear binding result y agregarle errores en un determinado campo
        class House {

            public int getDoors() {
                return doors;
            }

            public void setDoors(int doors) {
                this.doors = doors;
            }

            int doors;
        }
        House house = new House();

        BindingResult bindingResult = new BeanPropertyBindingResult(house, "object");
        String fieldName = "doors";
        String code = "error.code";
        bindingResult.rejectValue(fieldName, code);
        bindingResult.rejectValue(fieldName, code);
        bindingResult.rejectValue(fieldName, code);
        bindingResult.rejectValue(fieldName, code);

        int count = TestUtil.countErroredFieldWithNameAndCode(bindingResult, fieldName, code);
        assertEquals(4, count);
    }
}