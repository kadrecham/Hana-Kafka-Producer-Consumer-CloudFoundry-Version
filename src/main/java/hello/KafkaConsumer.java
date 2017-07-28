package hello;


import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

public class KafkaConsumer {
	private ConsumerConnector consumerConnector = null;
    private final String topic = "AC_DATAINGEST";
    private Environment env = new Environment ("user-provided");
    private HanaProducer hp = new HanaProducer();
    
    public void initialize() {
        Properties props = new Properties();
        props.put("zookeeper.connect", env.getZk());
        props.put("group.id", "testgroup");
        props.put("zookeeper.session.timeout.ms", "400");
        props.put("zookeeper.sync.time.ms", "300");
        props.put("auto.commit.interval.ms", "1000");
        ConsumerConfig conConfig = new ConsumerConfig(props);
        consumerConnector = Consumer.createJavaConsumerConnector(conConfig);
  }
    
    public void consume() throws IOException, ClassNotFoundException, SQLException {
        //Key = topic name, Value = No. of threads for topic
        Map<String, Integer> topicCount = new HashMap<String, Integer>();       
        topicCount.put(topic, new Integer(1));
       
        //ConsumerConnector creates the message stream for each topic
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerStreams =
              consumerConnector.createMessageStreams(topicCount);         
       
        // Get Kafka stream for topic 'mytopic'
        List<KafkaStream<byte[], byte[]>> kStreamList = consumerStreams.get(topic);
        // Iterate stream using ConsumerIterator
        for (final KafkaStream<byte[], byte[]> kStreams : kStreamList) {
               ConsumerIterator<byte[], byte[]> consumerIte = kStreams.iterator();
               
               while (consumerIte.hasNext()){
            	   MessageAndMetadata<byte[],byte[]> mesage = consumerIte.next();
            	  
            	   
            	   hp.write(byteToPojo(mesage.message()), new String (mesage.key()));
            	   
                      System.out.println("Message consumed from topic[" + topic + "] : " + byteToPojo(mesage.message()).getLocationScanResult()[0].getBSSID());              
        }}
        //Shutdown the consumer connector
        if (consumerConnector != null)   consumerConnector.shutdown();          
  }
    public static PojoLocationScanResult byteToPojo(byte[] data){
    	ObjectMapper mapper = new ObjectMapper();
    	try {
            return mapper.readValue(data, PojoLocationScanResult.class);
        }
        catch(IOException e) {
            throw new IllegalArgumentException(e);
        }
		
	}
    

}
