package hong.snipp.link.snipp_link.global.hong.shortURL;

import hong.snipp.link.snipp_link.domain.shorturl.dto.request.SnipShortUrlCreate;
import hong.snipp.link.snipp_link.domain.shorturl.dto.response.SnipShortUrlView;
import hong.snipp.link.snipp_link.domain.shorturl.service.SnipShortUrlService;
import hong.snipp.link.snipp_link.global.exception.SnippException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * packageName    : hong.snipp.link.snipp_link.global.hong.shortURL
 * fileName       : CreateShortURL
 * author         : work
 * date           : 2025-04-15
 * description    : ORIGIN_URL 입력 -> SHORT_URL 출력
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */

@Service
@RequiredArgsConstructor
public class CreateShortURLService {

    @Value("${hong.short-url.secret-key}")
    private String secretKey;

    @Value("${hong.base-url}")
    private String baseURL;

    private static final String BASE62_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int SHORT_URL_LENGTH = 5; // 원하는 short URL 길이

    private final SnipShortUrlService shortUrlService;

    /**
     * @method      isValidURL
     * @author      work
     * @date        2025-04-15
     * @deacription URL 유효성 체크
    **/
    private boolean isValidURL(String url) {
        try {
            URL connectionURL = new URL(url);
            URLConnection conn = connectionURL.openConnection();
            conn.connect();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * @method      sha256HashToLong
     * @author      work
     * @date        2025-04-15
     * @deacription SHA-256 알고리즘으로 해싱하여 해시 값을 얻은 뒤, 첫 5바이트만을 사용하여 8바이트 long 값으로 변환
    **/
    private long sha256HashToLong(String input) {
        try {

            // 1. SHA-256 알고리즘으로 해싱하여 32바이트 길이의 해시 값을 생성
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));

            // 2. 해시 값의 첫 5바이트만을 사용해 long 타입(8byte)로 확장
            long value = 0;
            for(int i=0; i<Math.min(5, hash.length); i++) {
                value = (value << 8) | (hash[i] & 0xFF);
            }
            return value;

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 Algorithm not available", e);
        }
    }

    /**
     * @method      base62Encode
     * @author      work
     * @date        2025-04-15
     * @deacription 해시 값을 가지고 Base62 인코딩 진행
    **/
    private String base62Encode(long value) {
        StringBuilder encode = new StringBuilder();

        // 1. Base62 인코딩 진행
        while (value > 0) {
            encode.append(BASE62_CHARS.charAt((int) (value % 62)));
            value /= 62;
        }

        // 2. SHORT_URL 최소 길이보다 작다면, 랜덤 값으로 채우기
        while(encode.length() < SHORT_URL_LENGTH) {
            encode.append(BASE62_CHARS.charAt((int) (Math.random() * 62)));
        }

        // 3. 문자열을 반전시켜 리턴
        return encode.reverse().toString();
    }

    /**
     * @method      generateSalt
     * @author      work
     * @date        2025-04-15
     * @deacription 중복되는 SHORT_URL 생성시, 랜덤 문자열 추가를 통한 SHORT_URL 재생성을 위한 SALT 생성기
     *              -> 16바이트 크기의 난수 SALT 생성
    **/
    private String generateSalt() {
        // 1. 보안적으로 강력한 난수 생성기
        SecureRandom random = new SecureRandom();

        // 2. 16바이트 크기의 SALT 배열 생성
        byte[] salt = new byte[16];

        // 3. 난수 생성기를 통해 SALT 배열에 16개의 무작위 바이트 값을 채운다
        random.nextBytes(salt);

        // 4. 바이너리 데이터를 문자열로 변환
        // => 이때 Base64 인코딩을 URL-safe 형식으로 수행
        // => URL-safe : ( + 값을 - 값으로 대체 ) ( / 값을 _ 값으로 대체 )
        return Base64.getUrlEncoder().encodeToString(salt);
    }

    /**
     * @method      generateShortURL
     * @author      work
     * @date        2025-04-15
     * @deacription shortURL 생성
    **/
    @Transactional
    public String generateShortURL(SnipShortUrlCreate request) {

        String originURL = request.getOriginUrl();
        String shortURL = "";
        if( !this.isValidURL(originURL) ) {
            throw new SnippException("유효하지 않은 주소정보입니다.", HttpStatus.BAD_REQUEST);
        }

        SnipShortUrlView view = shortUrlService.findShortURLByOriginURL(originURL);
        if( view == null || "Y".equals(view.getIsExpired()) ) {

            Long id = shortUrlService.saveOriginURL(request);

            // 1. ID(PK) + SECRET_KEY
            String input = id + secretKey;

            // 2. 해시 값으로 변환
            long hashValue = this.sha256HashToLong(input);

            // 3. Base62 인코딩 진행
            shortURL = this.base62Encode(hashValue);

            // 4. {shortURL} 중복된다면 -> salt 값을 추가해서 새롭게 생성
            while(shortUrlService.ifShortURLExist(shortURL)) {

                String salt = this.generateSalt();
                String newInput = id + secretKey + salt;
                long newHashValue = this.sha256HashToLong(newInput);
                shortURL = this.base62Encode(newHashValue);

            }

            // 5. 최종 {shortURL} 정보 저장
            shortUrlService.changeShortURL(id, shortURL);

        } else shortURL = view.getShortUrl();

        return String.format("%s/snip-short/%s", baseURL, shortURL);
    }
}
