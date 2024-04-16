package guru.qa.rococo.jupiter.second;

import io.qameta.allure.Allure;
import io.qameta.allure.util.ResultsUtils;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;

public class AllureParamConverter implements ArgumentConverter {
  @Override
  public Object convert(Object o, ParameterContext parameterContext) throws ArgumentConversionException {
    Allure.label(ResultsUtils.ALLURE_ID_LABEL_NAME, (String) o);
    return o;
  }
}
