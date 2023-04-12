package xyz.felh.openai.gpt3.tokenizer;

import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ListConverter extends SimpleArgumentConverter {

    @Override
    protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
        if (source instanceof String && List.class.isAssignableFrom(targetType)) {
            String input = (String) source;
            if (input.startsWith("[") && input.endsWith("]"))
                input = input.substring(1, input.length() - 1);

            return Arrays.stream(input.split(","))
                    .map(String::trim)
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());
        }
        throw new IllegalArgumentException("Conversion from " + source.getClass() + " to "
                + targetType + " not supported.");
    }
}