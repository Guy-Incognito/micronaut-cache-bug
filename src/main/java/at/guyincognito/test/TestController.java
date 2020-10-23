package at.guyincognito.test;


import io.micronaut.cache.annotation.Cacheable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.reactivex.Single;

@Controller
public class TestController {

    private static final byte[] value = "Tralalalalalala".getBytes();

    public static final String PATH_FAILS = "/test";
    public static final String PATH_WORKS = "/test2";

    /**
     * Returns test value wrapped in HttpResponse.
     * This call will fail on refresh (i.e second call)
     *
     * @return the test value.
     */
    @Cacheable("files")
    @Get(value = PATH_FAILS, produces = "application/octet-stream")
    public Single<HttpResponse<byte[]>> getFiles() {
        return Single.just(HttpResponse.ok(value));
    }

    /**
     * Returns test value.
     * This call works as expected.
     *
     * @return the test value.
     */
    @Cacheable("files2")
    @Get(value = PATH_WORKS, produces = "application/octet-stream")
    public Single<byte[]> getFiles2() {
        return Single.just(value);
    }
}
