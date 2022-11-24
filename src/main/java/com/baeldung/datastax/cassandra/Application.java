package com.baeldung.datastax.cassandra;

//import com.baeldung.datastax.cassandra.domain.Video;
//import com.baeldung.datastax.cassandra.repository.KeyspaceRepository;
//import com.baeldung.datastax.cassandra.repository.VideoRepository;


import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.insert.RegularInsert;
import com.datastax.oss.driver.api.querybuilder.schema.CreateTable;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.InetSocketAddress;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.time.Instant;
import java.util.UUID;
import java.util.ArrayList;





public class Application {

private static final Logger LOG = LoggerFactory.getLogger(Application.class);
    public void run() {
        //CassandraConnector connector = new CassandraConnector();
/*        connect("10.198.243.75", 9042, "Test Cluster");
        //CqlSession session = connector.getSession();


        createKeyspace("testKeyspace", 1);
        useKeyspace("testKeyspace");

	//VideoRepository(session);

        createTable();
	//Video videRepository = new Video();
        insertVideo(new Video("Video Title 1", Instant.now()));
        insertVideo(new Video("Video Title 2",
            Instant.now().minus(1, ChronoUnit.DAYS)));

        List<Video> videos = selectAll();

        videos.forEach(x -> LOG.info(x.toString()));
       
	
	close();*/
    }
	public static void main(String[] args) {
    		new Application().run();
	}
}
