package kcs.demo.springmvctest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestCancelController {

    @GetMapping("/cancel-test")
    public String cancellingThreadReqTest() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            Thread.sleep(1000);
            System.out.println("one step : "+i);
        }
        return "completed";
    }

}
