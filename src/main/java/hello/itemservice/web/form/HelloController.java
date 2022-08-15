package hello.itemservice.web.form;

import hello.itemservice.converter.type.IpPort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HelloController {

    @GetMapping("/hello/v1")
    public String helloV1(HttpServletRequest request) {
        String data = request.getParameter("data"); //문자 타입 조회
        Integer intValue = Integer.valueOf(data); //숫자 타입으로 변환
        return "ok";
    }

    @GetMapping("/hello/v2")
    public String helloV2(@RequestParam Integer data) {
        return "ok";
    }

    @GetMapping("/hello/ip/port")
    public String ipPort(@RequestParam IpPort ipPort) {
        System.out.println("ipPort = " + ipPort.getPort());
        System.out.println("ipPort = " + ipPort.getIp());
        return "ok";
    }
}
