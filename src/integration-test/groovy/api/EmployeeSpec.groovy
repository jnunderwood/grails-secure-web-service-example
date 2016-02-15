package api

import grails.test.mixin.integration.Integration
import grails.transaction.*
import static grails.web.http.HttpHeaders.*
import static org.springframework.http.HttpStatus.*
import spock.lang.*
import geb.spock.*
import grails.plugins.rest.client.RestBuilder

@Integration
@Rollback
class EmployeeSpec extends GebSpec {

    def setup() {
    }

    def cleanup() {
    }

    void "Test the login page"() {
        when: "The login page is requested"
            def accessToken = authenticate()

        then: "There is an access token"
            accessToken != null
    }

    void "Test the count page"() {
        when: "The count page is requested"
            def accessToken = authenticate()
            def resp = restBuilder().post("$baseUrl/api/employees/count") {
                header(AUTHORIZATION, "Bearer ${accessToken}")
            }

        then: "The response is correct"
            resp.status == OK.value()
            resp.json.count == 100
    }

    RestBuilder restBuilder() {
        new RestBuilder()
    }

    String authenticate() {
        def resp = restBuilder().post("$baseUrl/api/login") {
            contentType "application/json"
            json {
                username = "admin"
                password = "admin"
            }
        }
        resp.json.access_token
    }

}
