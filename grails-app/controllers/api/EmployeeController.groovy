package api

import grails.rest.RestfulController
import grails.plugin.springsecurity.annotation.Secured
// import reactor.rx.Promise

import static grails.async.Promises.*

class EmployeeController extends RestfulController {
    static responseFormats = ['json', 'xml']
    def employeeService
    def asyncEmployeeService

    EmployeeController() {
        super(Employee)
    }

    @Secured('ROLE_ADMIN')
    def count() {
        log.debug "count()"
        //respond "count": employeeService.count()  // synchronous
        asyncEmployeeService.count()
            // .onComplete { Promise result ->
            .onComplete { def result ->
                log.debug "result: ${result.value}"
                respond "count": result.value
            }
            .onError { Throwable t ->
                println "An error occured ${t.message}"
            }
    }

    @Secured('ROLE_ADMIN')
    def list(Integer max, Integer offset) {
        log.debug "list(${max}, ${offset})"
        //respond employeeService.list(params, max, offset)  // synchronous
        asyncEmployeeService.list(params, max, offset)
            .onComplete { def result ->
                log.debug "result: ${result.value}"
                respond result.value
            }
            .onError { Throwable t ->
                println "An error occured ${t.message}"
            }
    }

    @Secured('ROLE_ADMIN')
    def findByField(String name, String value) {
        log.debug "findByField(${name}, ${value})"
        //respond employeeService.findByField(name, value)  // synchronous
        asyncEmployeeService.findByField(name, value)
            .onComplete { def result ->
                log.debug "result: ${result.value}"
                respond result.value
            }
            .onError { Throwable t ->
                println "An error occured ${t.message}"
            }
    }

    @Secured('ROLE_ADMIN')
    def findByName(String lastName, String firstName) {
        log.debug "findByName(${lastName}, ${firstName})"
        //respond employeeService.findByName(params, lastName, firstName)  // synchronous
        asyncEmployeeService.findByName(params, lastName, firstName)
            .onComplete { def result ->
                log.debug "result: ${result.value}"
                respond result.value
            }
            .onError { Throwable t ->
                println "An error occured ${t.message}"
            }
    }

/*
    def index(Integer max, Integer offset) {
        params.max = Math.min(max ?: 10, 100)
        params.offset = Math.max(offset ?: 0, 0)
        respond Employee.list(params), model:[employeeCount: Employee.count()]
    }

    def show(Employee employee) {
        if (params.id) {
            employee = Employee.findById(params.id)
        }
        respond employee
    }
*/

}
