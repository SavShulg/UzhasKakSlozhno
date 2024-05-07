package controller;

import java.util.logging.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/info")


public class InfoController {
    private final static Logger =LoggerFactory.getLogger(InfoController.class);



    @Value("${server.port}")
    private int port;


    @GetMapping("/port")
    public int port() {
        logger.info("Getting port {}", port);
        return port;
    }
}
