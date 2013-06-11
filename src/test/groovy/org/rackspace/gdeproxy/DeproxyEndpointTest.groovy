package org.rackspace.gdeproxy
import spock.lang.Specification

class DeproxyEndpointTest extends Specification {

    def "when connecting with server socket, should send and receive"() {

        given:
        def server = new ServerSocket(8888)
        def client = new Socket("localhost", 8888)

        def th = new Thread({
            server.accept({ socket ->
                println "processing new connection..."
                socket.withStreams { input, output ->
                    def reader = input.newReader()
                    def buffer = reader.readLine()
                    println "server received: $buffer"
                    output << "echo-response: " + buffer + "\n"
                }
                println "processing/thread complete."
            })
        });

        th.run()
        th.join()

        when:
        def buffer
        client.withStreams { input, output ->
            output << "echo testing...\n"
            buffer = input.newReader().readLine()
            println "response = $buffer"
        }

        then:
        buffer == "echo-response: echo testing..."
    }

}
