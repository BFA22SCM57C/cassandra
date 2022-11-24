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

class Video {

    private UUID id;
    private String title;
    private Instant creationDate;

    public Video(UUID id, String title, Instant creationDate) {
        this.id = id;
        this.title = title;
        this.creationDate = creationDate;
    }

    public Video(String title, Instant creationDate) {
        this.title = title;
        this.creationDate = creationDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "[id:" + id.toString() + ", title:" + title + ", creationDate: " + creationDate.toString() + "]";
    }
}

private static final Logger LOG = LoggerFactory.getLogger(Application.class);
private static final String TABLE_NAME = "videos";
private CqlSession session;

public void createTable() {
        createTable(null);
    }

private ResultSet executeStatement(SimpleStatement statement, String keyspace) {
        if (keyspace != null) {
            statement.setKeyspace(CqlIdentifier.fromCql(keyspace));
        }
return session.execute(statement);
}
    public void createTable(String keyspace) {
        CreateTable createTable = SchemaBuilder.createTable(TABLE_NAME).ifNotExists()
          .withPartitionKey("video_id", DataTypes.UUID)
          .withColumn("title", DataTypes.TEXT)
          .withColumn("creation_date", DataTypes.TIMESTAMP);

        executeStatement(createTable.build(), keyspace);
    }

public UUID insertVideo(Video video) {
        return insertVideo(video, null);
    }

    public UUID insertVideo(Video video, String keyspace) {
        UUID videoId = UUID.randomUUID();

        video.setId(videoId);

        RegularInsert insertInto = QueryBuilder.insertInto(TABLE_NAME)
          .value("video_id", QueryBuilder.bindMarker())
          .value("title", QueryBuilder.bindMarker())
          .value("creation_date", QueryBuilder.bindMarker());

        SimpleStatement insertStatement = insertInto.build();

        if (keyspace != null) {
            insertStatement = insertStatement.setKeyspace(keyspace);
        }

        PreparedStatement preparedStatement = session.prepare(insertStatement);

        BoundStatement statement = preparedStatement.bind()
          .setUuid(0, video.getId())
          .setString(1, video.getTitle())
          .setInstant(2, video.getCreationDate());

        session.execute(statement);

        return videoId;
    }


    public List<Video> selectAll(String keyspace) {
        Select select = QueryBuilder.selectFrom(TABLE_NAME).all();

        ResultSet resultSet = executeStatement(select.build(), keyspace);

        List<Video> result = new ArrayList<>();

        resultSet.forEach(x -> result.add(
            new Video(x.getUuid("video_id"), x.getString("title"), x.getInstant("creation_date"))
        ));

        return result;
    }

public void createKeyspace(String keyspaceName, int numberOfReplicas) {
        CreateKeyspace createKeyspace = SchemaBuilder.createKeyspace(keyspaceName)
          .ifNotExists()
          .withSimpleStrategy(numberOfReplicas);

        session.execute(createKeyspace.build());
    }
    public void useKeyspace(String keyspace) {
        session.execute("USE " + CqlIdentifier.fromCql(keyspace));
    }


public void connect(final String node, final Integer port, final String dataCenter) {
        CqlSessionBuilder builder = CqlSession.builder();
        builder.addContactPoint(new InetSocketAddress(node, port));
        builder.withLocalDatacenter(dataCenter);

        session = builder.build();
    }

    public CqlSession getSession() {
        return this.session;
    }

    public void close() {
        session.close();
    }


public class Application {


    public void run() {
        //CassandraConnector connector = new CassandraConnector();
        connect("10.198.243.75", 9042, "Test Cluster");
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
       
	
	close();
    }
	public static void main(String[] args) {
    		new Application().run();
	}
}
