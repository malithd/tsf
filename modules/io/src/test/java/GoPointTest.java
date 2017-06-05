import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.n52.tsf.io.protobuf.gen.GeoProtobuf.Coordinate;
import org.n52.tsf.io.protobuf.gen.GeoProtobuf.Geometry;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class GoPointTest {
    private static final String TEST_FILE_LOCATION = "src/test/resources/geo_data";

    @Before
    public void setUp() throws Exception {
        Path filePath = Paths.get(TEST_FILE_LOCATION);
        Files.createFile(filePath);
    }

    @Test
    public void testSerializeGeoPoint() throws Exception {
        Coordinate.Builder cord = Coordinate.newBuilder();
        cord.setX(1);
        cord.setY(2);
        cord.setZ(Double.NaN);
        Geometry.Builder geo = Geometry.newBuilder();
        geo.setType(Geometry.Type.POINT);
        geo.addCoordinates(cord.build());
        System.out.println("-------------- Serializing Geometry Point -------------------------");
        FileOutputStream output = new FileOutputStream(TEST_FILE_LOCATION);
        try {
            geo.build().writeTo(output);
            System.out.println("Successfully Serialized....");
        } finally {
            output.close();
        }
        assertTrue(new File(TEST_FILE_LOCATION).length() > 0);
        System.out.println("-------------- DeSerializing Geometry Point -----------------------");
        Geometry geometry = Geometry.parseFrom(new FileInputStream(TEST_FILE_LOCATION));
        assertEquals(Geometry.Type.POINT, geometry.getType());
        System.out.println(geometry.getType() + "(" + geometry.getCoordinates(0).getX() + "," +
                geometry.getCoordinates(0).getY() + "," + geometry.getCoordinates(0).getZ() + ")");
        System.out.println("Successfully DeSerialized....");
    }

    @After
    public void tearDown() throws Exception {
        Path filePath = Paths.get(TEST_FILE_LOCATION);
        Files.deleteIfExists(filePath);
    }
}