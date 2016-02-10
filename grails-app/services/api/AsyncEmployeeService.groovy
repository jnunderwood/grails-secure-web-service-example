package api

import grails.async.*

class AsyncEmployeeService {
   @DelegateAsync EmployeeService employeeService
}
