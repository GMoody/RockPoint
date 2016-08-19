package cz.rockpoint.utils.JodaAdapter;


import com.google.common.collect.Maps;
import org.thymeleaf.context.IProcessingContext;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionEnhancingDialect;

import java.util.Map;

// Class creates Dialect for ThymeLeaf to work with JodaTime
public class JodaTimeDialect extends AbstractDialect implements IExpressionEnhancingDialect {

    private static final String JODA = "joda";

    @Override
    public Map<String, Object> getAdditionalExpressionObjects(IProcessingContext processingContext) {
        Map<String, Object> expressionObjects = Maps.newHashMap();
        expressionObjects.put(JODA, new JodaTimeExpressionObject(processingContext.getContext().getLocale()));
        return expressionObjects;
    }

    @Override
    public String getPrefix() {
        return JODA;
    }
}
