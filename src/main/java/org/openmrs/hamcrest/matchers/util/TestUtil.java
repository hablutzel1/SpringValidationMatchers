package org.openmrs.hamcrest.matchers.util;

import java.util.List;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;


public class TestUtil {
   

    /**
     * Valida utilizando el nombre del campo que reporta fallos y no la clave de
     * internacionalizacion
     *
     * @param bindingResult
     * @param fieldName
     * @return
     */
    public static boolean hasErroredFieldWithNameBeginningWith(
            BindingResult bindingResult, String fieldName) {
        return isErroredField(bindingResult, fieldName, false);
    }

    private static boolean isErroredField(BindingResult bindingResult,
                                          String fieldName, boolean exactMatch) {
        if (!bindingResult.hasErrors())
            return false;
        List allErrors = bindingResult.getAllErrors();

        for (Object item : allErrors) {
            if (item instanceof FieldError) {
                // // valid for me
                FieldError error = ((FieldError) item);
                String foundValue = error.getField();

                if (!exactMatch) {
                    if (foundValue.startsWith(fieldName))
                        return true;
                    ;
                } else {
                    if (foundValue.equals(fieldName))
                        return true;
                }

            }
        }
        return false;
    }

    public static boolean hasErroredFieldWithName(BindingResult bindingResult,
                                                  String fieldName) {
        return isErroredField(bindingResult, fieldName, true);

    }

    /**
     * Cuando spring validator rechaza un valor, normalmente lo asocia a un
     * campo del objeto que validaba, y a un código de error. Este método
     * validará si existe un valor rechazado registrado para cierto
     * atributo/campo con un determinado codigo de error
     * <p/>
     * Si fieldName es nulo asumira que se trata de validacion a nivel del objeto
     *
     * @param bindingResult
     * @param fieldName
     * @param code
     * @return
     */
    public static boolean hasErroredFieldWithNameAndCode(
            BindingResult bindingResult, String fieldName, String code) {
        int i = countErroredFieldWithNameAndCode(bindingResult, fieldName, code);
        if (i > 0) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * @param bindingResult
     * @param fieldName     if null only consider global objects
     * @param code          if null counts all error codes
     * @return
     * @should devolver la cantidad de veces que un codigo de error se repite para un determinado campo
     */
    public static int countErroredFieldWithNameAndCode(
            BindingResult bindingResult, String fieldName, final String code) {
        int count = 0;
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError item : allErrors) {
            if (item instanceof FieldError) {
                FieldError error = ((FieldError) item);
                String foundValue = error.getField();
                if (foundValue.equals(fieldName)) {
                    for (String codigo : error.getCodes()) {
                        if (code == null) {
                            count++;
                        } else if (code.equals(codigo)) {
                            count++;
                        }
                    }
                }
            } else if (fieldName == null && item instanceof ObjectError) {
                ObjectError error = ((ObjectError) item);
                for (String codigo : error.getCodes()) {
                    if (code == null) { // for counting only field errors
                        count++;
                    } else if (code.equals(codigo)) { // for counting to a granular level
                        count++;
                    }
                }
            }
        }


        return count;
    }

    public static int countErrorsForFieldName(BindingResult bindingResult,
                                              String string) {
        List allErrors = bindingResult.getAllErrors();
        int count = 0;
        for (Object item : allErrors) {
            if (item instanceof FieldError) {
                FieldError error = ((FieldError) item);
                String foundValue = error.getField();
                if (foundValue.equals(string))
                    count++;
            }
        }
        return count;
//        return countErroredFieldWithNameAndCode(bindingResult, )
    }

  
    public static BindingResult validarObjeto(Validator validator, Object target, String objectName) {
        BindingResult bindingResult = new BeanPropertyBindingResult(target, objectName);
        validator.validate(target, bindingResult);
        return bindingResult;
    }

 
}
