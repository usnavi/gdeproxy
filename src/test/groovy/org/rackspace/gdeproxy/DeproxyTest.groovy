package org.rackspace.gdeproxy

import spock.lang.Specification

/**
 *
 */
class DeproxyTest extends Specification {

    def static deproxy_port_base = 9999

    Deproxy deproxy
    int deproxyPort
    String endpoint

    def setup() {
        deproxyPort = getNextDeproxyPort()
        deproxy = new Deproxy()
        endpoint = deproxy.addEndpoint(deproxyPort)
    }

    def cleanup() {
        deproxy.shutdownAllEndpoints()
    }



    def getNextDeproxyPort() {
        return (deproxy_port_base - 1)
    }

}
