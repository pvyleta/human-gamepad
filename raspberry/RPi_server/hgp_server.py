#!/usr/bin/env python3

import socketserver
import RequestHandler

PORT = 8000

handler = RequestHandler.GPRequestHandler

httpd = socketserver.TCPServer(("", PORT), handler)

print("serving at port", PORT)
httpd.serve_forever()
