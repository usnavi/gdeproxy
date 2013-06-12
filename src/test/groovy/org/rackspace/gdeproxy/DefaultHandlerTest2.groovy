package org.rackspace.gdeproxy

class DefaultHandlerTest2 extends DeproxyTest {

    def "when request is handled, should return 200 OK response"() {

        when:
        MessageChain mc = deproxy.makeRequest("http://localhost:${deproxyPort}")

        then:
        mc.receivedResponse.code == 200
    }

}
