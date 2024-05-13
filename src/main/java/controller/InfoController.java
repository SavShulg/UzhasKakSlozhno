package controller;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import org.apache.el.stream.Stream;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static service.FacultyService.logger;


@RestController
@RequestMapping("/info")


public class InfoController {
    private final static Logger =LoggerFactory.getLogger(InfoController .class);


    @Value("${server.port}")
    private int port;


    @GetMapping("/port")
    public int port() {
        logger.info("Getting port {}", port);
        return port;
    }

    @GetMapping("/sum")
    public int sum () {
        var start = System.currentTimeMillis();
       // int sum = Stream.iterate(1, a -> a + 1).limit(1_000_000).reduce(0, (a, b) -> a + b);
        int sum = IntStream.iterate(1,a -> a +1).limit(1_000_000).parallel().sum();
        var end = System.currentTimeMillis() - start;
        logger.info("Elapsed time: {} ms", end);
        return sum;
    }
    @GetMapping("/sum2")
    public int sum2 () {
        // google guava
        var sw = StopWatch.createStarted();
        int sum = IntStream.iterate(1,a -> a +1).limit(1_000_000_000).sum();
        logger.info("Elapsed time: {} ms", sw.elapsed(TimeUnit.NANOSECONDS));
        return sum;
    }

}
