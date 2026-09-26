package net.maku.framework.common.config;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdScalarSerializer;

import java.math.BigDecimal;

/**
 * Solve the js precision loss problem
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public class BigDecimalSerializer extends StdScalarSerializer<BigDecimal> {
    private static final long MAX_SAFE_INTEGER = 9007199254740991L;

    public BigDecimalSerializer() {
        super(BigDecimal.class);
    }

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializationContext provider) throws JacksonException {
        if (value == null) {
            gen.writeNull();
            return;
        }

        // Decide the serialization method by integer length
        if (value.longValue() > MAX_SAFE_INTEGER) {
            // Serialize as string
            gen.writeString(value.toPlainString());
        } else {
            // Serialize as number
            gen.writeNumber(value);
        }
    }
}