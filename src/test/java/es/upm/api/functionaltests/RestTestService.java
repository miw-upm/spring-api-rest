package es.upm.api.functionaltests;


import es.upm.api.domain.model.Role;
import es.upm.api.domain.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;


@Service
public class RestTestService {

    @Autowired
    private JwtService jwtService;

    public HttpEntity<Void> createHttpEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return new HttpEntity<>(headers);
    }

    public <T> HttpEntity<T> createHttpEntity(T body, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(body, headers);
    }

    public HttpEntity<Void> basicAuth(String user, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(user, password);
        return new HttpEntity<>(headers);
    }

    public HttpEntity<Void> createHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        return new HttpEntity<>(headers);
    }


    public HttpEntity<Void> loginAdmin() {
        return this.createHttpEntity(this.jwtService.createToken("daemon", "6", Role.ADMIN.toString()));
    }

    public <T> HttpEntity<T> loginAdmin(T body) {
        return this.createHttpEntity(body, this.jwtService.createToken("daemon", "6", Role.ADMIN.toString()));
    }

    public HttpEntity<Void> loginManager() {
        return this.createHttpEntity(this.jwtService.createToken("daemon", "666666001", Role.MANAGER.toString()));
    }

    public HttpEntity<Void> loginOperator() {
        return this.createHttpEntity(this.jwtService.createToken("daemon", "666666001", Role.OPERATOR.toString()));
    }

    public HttpEntity<Void> loginCustomer() {
        return this.createHttpEntity(this.jwtService.createToken("daemon", "66", Role.CUSTOMER.toString()));
    }


}
