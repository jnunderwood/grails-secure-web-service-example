package api

import grails.transaction.Transactional

@Transactional(readOnly=true)
class EmployeeService {

    Integer count() {
        Employee.count()
    }

    List<Employee> list(def params, Integer max, Integer offset) {
        params.max = Math.min(max ?: 10, 100)
        params.offset = Math.max(offset ?: 0, 0)
        Employee.list(params)
    }

    List<Employee> findByField(String name, String value) {
        Employee."findAllBy${name}"(value)
    }

    List<Employee> findByName(def params, String lastName, String firstName) {
        def employees = []
        if (params.lastName) {
            if (params.firstName) {
                employees = Employee.withCriteria {
                    and {
                        ilike('lastName', "${params.lastName}%")
                        ilike('firstName', "${params.firstName}%")
                    }
                }
            }
            else {
                employees = Employee.withCriteria {
                    ilike('lastName', "${params.lastName}%")
                }
            }
        }
        employees
    }
}
