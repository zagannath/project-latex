/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package project.latex.balloon.writer;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import project.latex.balloon.TransmittedDataKeysResource;

/**
 *
 * @author dgorst
 */
public class HttpDataWriterTest {
    
    private HttpDataWriter writer;
    private List<String> dataKeys;
    private TransmittedDataKeysResource mockTransmittedDataKeysResource;
    private ChecksumGenerator mockChecksumGenerator;
    
    @Before
    public void setUp() {
        this.dataKeys = new ArrayList<>();
        this.mockTransmittedDataKeysResource = mock(TransmittedDataKeysResource.class);
        when(mockTransmittedDataKeysResource.getTransmittedDataKeys()).thenReturn(dataKeys);
        mockChecksumGenerator = mock(ChecksumGenerator.class);
        this.writer = new HttpDataWriter(mockTransmittedDataKeysResource, new DataModelConverter(mockChecksumGenerator), null);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getJsonStringFromRawData method, of class HttpDataWriter.
     */
    @Test
    public void testGetJsonStringFromRawData()  {
        String actual = writer.getJsonStringFromRawData("testing");
        
        byte[] encodedBytes = Base64.encodeBase64("testing".getBytes());
        Map<String, Object> data = new HashMap<>();
        data.put("_raw", new String(encodedBytes));
        
        Map<String, Object> body = new HashMap<>();
        body.put("data", data);
        Gson gson = new Gson();
        String expected = gson.toJson(body);
        
        assertEquals(expected, actual);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetJsonStringThrowsIfRawDataIsNull() {
        writer.getJsonStringFromRawData(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSendPostRequestThrowsIfRawDataIsNull() throws IOException {
        writer.sendPostRequest(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testWriteDataThrowsIfDataIsNull() {
        writer.writeData(null);
    }
}
