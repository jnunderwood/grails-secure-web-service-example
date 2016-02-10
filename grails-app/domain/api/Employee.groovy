package api

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes='id,username')
@ToString
class Employee {
    String id
    String username
    String firstName
    String lastName

    static constraints = {
        username unique: true
    }

    static mapping = {
        id generator: 'assigned'
    }
}
