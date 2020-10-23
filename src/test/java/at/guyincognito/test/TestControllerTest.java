package at.guyincognito.test;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.net.MalformedURLException;
import java.net.URL;

import static at.guyincognito.test.TestController.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
class TestControllerTest {

    @Inject
    private EmbeddedServer server;

    private URL getServerUrl() throws MalformedURLException {
        return new URL("http://" + server.getHost() + ":" + server.getPort());
    }


    /**
     * This test calls the method returning Single<HttpResponse<byte[]>> .
     * Fails!
     *
     * @throws MalformedURLException
     */
    @Test
    void getFilesFails() throws MalformedURLException {
        var client = HttpClient.create(getServerUrl());
        String result = client.toBlocking()
                .retrieve(
                        HttpRequest.GET(PATH_FAILS),
                        String.class
                );

        // second call fails:
        // ERROR i.m.h.s.netty.RoutingInBoundHandler - Error writing final response: io.netty.util.IllegalReferenceCountException: refCnt: 0, decrement: 1
        // io.netty.handler.codec.EncoderException: io.netty.util.IllegalReferenceCountException: refCnt: 0, decrement: 1
        String result2 = client.toBlocking()
                .retrieve(
                        HttpRequest.GET(PATH_FAILS),
                        String.class
                );
        assertEquals(result, result2);
    }


    /**
     * This test calls the method returning HttpResponse<byte[]> .
     * Fails!
     *
     * @throws MalformedURLException
     */
    @Test
    void getFilesSyncFails() throws MalformedURLException {
        var client = HttpClient.create(getServerUrl());
        String result = client.toBlocking()
                .retrieve(
                        HttpRequest.GET(PATH_FAILS_SYNC),
                        String.class
                );

        // second call fails:
        // ERROR i.m.h.s.netty.RoutingInBoundHandler - Error writing final response: io.netty.util.IllegalReferenceCountException: refCnt: 0, decrement: 1
        // io.netty.handler.codec.EncoderException: io.netty.util.IllegalReferenceCountException: refCnt: 0, decrement: 1
        String result2 = client.toBlocking()
                .retrieve(
                        HttpRequest.GET(PATH_FAILS_SYNC),
                        String.class
                );
        assertEquals(result, result2);
    }


    /**
     * This test calls the method returning Single<byte[]> .
     * Works as expected.
     *
     * @throws MalformedURLException
     */
    @Test
    void getFiles2Works() throws MalformedURLException {
        var client = HttpClient.create(getServerUrl());
        String result = client.toBlocking()
                .retrieve(
                        HttpRequest.GET(PATH_WORKS),
                        String.class
                );

        String result2 = client.toBlocking()
                .retrieve(
                        HttpRequest.GET(PATH_WORKS),
                        String.class
                );

        assertEquals(result, result2);
    }
}
