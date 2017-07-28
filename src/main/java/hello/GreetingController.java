package hello;



import java.util.concurrent.atomic.AtomicLong;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    
    

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
    	
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
    
    @RequestMapping(path ="/js", method = RequestMethod.POST, consumes = "application/json")
    public Greeting js(@RequestBody PojoLocationScanResult jobject, @RequestParam(value="myKey", defaultValue="null") String myKey) {
    	//Producer prod = new Producer();
    	Producer.send ("AC_DATAINGEST", myKey, jobject);
        //HanaProducer hp = new HanaProducer();
    	//hp.write(jobject, myKey);
    	return new Greeting (counter.incrementAndGet(),
                             String.format(template, myKey));
    }
    
    @RequestMapping(path ="/st", method = RequestMethod.POST)
    public Greeting st(@RequestBody String jobject, @RequestParam(value="myKey", defaultValue="null") String myKey)    {
        long id = counter.incrementAndGet();
        //Producer prod = new Producer();
        Producer.send ("AC_DATAINGEST", myKey, jobject);
    	
        return new Greeting (id,
                             String.format(template, jobject));
    }
}
