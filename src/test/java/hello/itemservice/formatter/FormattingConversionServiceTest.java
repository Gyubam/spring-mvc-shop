package hello.itemservice.formatter;

import hello.itemservice.converter.IpPortToStringConverter;
import hello.itemservice.converter.StringToIpPortConverter;
import hello.itemservice.converter.type.IpPort;
import hello.itemservice.converter.type.formatter.MyNumberFormatter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.format.support.DefaultFormattingConversionService;

public class FormattingConversionServiceTest {

    @Test
    void formattingConversionService() {
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();

        // 컨버터 등록
        conversionService.addConverter(new StringToIpPortConverter());
        conversionService.addConverter(new IpPortToStringConverter());
        // 포맷터 등록
        conversionService.addFormatter(new MyNumberFormatter());

        // 컨버터 사용
        IpPort ipPort = conversionService.convert("127.0.0.1:8080", IpPort.class);
        Assertions.assertThat(ipPort).isEqualTo(new IpPort("127.0.0.1", 8080));

        // 포맷터 사용
        String convert = conversionService.convert(1000, String.class);
        Assertions.assertThat(conversionService.convert(1000, String.class)).isEqualTo("1,000");
        Assertions.assertThat(conversionService.convert("1,000", Long.class)).isEqualTo(1000L);

    }
}
