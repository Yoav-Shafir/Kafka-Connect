package io.confluent.examples;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Type;
import io.confluent.examples.validators.TimestampValidator;
import org.apache.kafka.common.config.ConfigDef.Importance;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Map;

public class MySourceConnectorConfig extends AbstractConfig {
	
  public static final String TOPIC_CONFIG = "topic";
  private static final String TOPIC_DOC = "Topic to write to";
  
  public static final String SINCE_CONFIG = "since.timestamp";
  private static final String SINCE_DOC = "Get logs at or after this time.\n"
    + "Time format should be: YYYY-MM-DDHH:MM:SSZ.\n"
    + "Defaults to current time";
	
  public MySourceConnectorConfig(ConfigDef config, Map<String, String> parsedConfig) {
    super(config, parsedConfig);
  }

  public MySourceConnectorConfig(Map<String, String> parsedConfig) {
    this(conf(), parsedConfig);
  }

  public static ConfigDef conf() {
    return new ConfigDef()
      .define(TOPIC_CONFIG, Type.STRING, Importance.HIGH, TOPIC_DOC)
      .define(SINCE_CONFIG, Type.STRING, ZonedDateTime.now().minusYears(1).toInstant().toString(), 
          new TimestampValidator(), Importance.HIGH, SINCE_DOC);
  }

  public String getTopic(){
    return this.getString(TOPIC_CONFIG);
  }
  
  public Instant getSince(){
    return Instant.parse(this.getString(SINCE_CONFIG));
  }
}
