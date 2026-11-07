package net.maku.framework.common.config;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.annotation.JacksonStdImpl;
import tools.jackson.databind.ser.jdk.NumberSerializer;

/**
 * Solve the js precision loss problem
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@JacksonStdImpl
public class BigNumberSerializer extends NumberSerializer {
    /**
     * JS Number.MAX_SAFE_INTEGER  Number.MIN_SAFE_INTEGER
     */
    private static final long MAX_SAFE_INTEGER = 9007199254740991L;
    private static final long MIN_SAFE_INTEGER = -9007199254740991L;
    /**
     * Provide an instance
     */
    public static final BigNumberSerializer INSTANCE = new BigNumberSerializer(Number.class);

    public BigNumberSerializer(Class<? extends Number> rawType) {
        super(rawType);
    }

    @Override
    public void serialize(Number value, JsonGenerator gen, SerializationContext provider) throws JacksonException {
        // Out of range, serialize as a string
        if (value.longValue() > MIN_SAFE_INTEGER && value.longValue() < MAX_SAFE_INTEGER) {
            super.serialize(value, gen, provider);
        } else {
            gen.writeString(value.toString());
        }
    }
}