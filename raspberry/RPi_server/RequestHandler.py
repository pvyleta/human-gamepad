from http.server import BaseHTTPRequestHandler
import json
import KeyCommandHandler

class GPRequestHandler(BaseHTTPRequestHandler):
    def __init__(self, request, client_address, server):
        self.keyHandler = KeyCommandHandler.KeyCommandHandler()
        super().__init__(request, client_address, server)

    def _set_response(self):
        self.send_response(200)
        self.send_header('Content-type', 'text/html')
        self.end_headers()

    def _set_Wrong_request(self):
        self.send_error(400)
        self.end_headers()

    def do_GET(self):
        self._set_Wrong_request()

    def do_POST(self):
        content_length = int(self.headers['Content-Length']) # <--- Gets the size of data
        post_data = self.rfile.read(content_length) # <--- Gets the data itself

        self._set_response()
        self.wfile.write("POST request for {}\n".format(self.path).encode('utf-8'))

        self.keyHandler.handle_input(post_data)

    def do_PUT(self):
        content_length = int(self.headers['Content-Length']) # <--- Gets the size of data
        put_data = self.rfile.read(content_length) # <--- Gets the data itself

        self._set_response()
        self.wfile.write("PUT request for {}\n".format(self.path).encode('utf-8'))

        self.keyHandler.handle_input(put_data)
