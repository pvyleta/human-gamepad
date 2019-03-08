#!/usr/bin/env python3
NULL_CHAR = chr(0)

class keyboard_elumator
	def __init__(self)
		self.device = open("/dev/hidg0", "rb+")
	def press_key(self, keynum)
		device.write((NULL_CHAR*2+chr(keynum)+NULL_CHAR*5))
	def release_key(self)
		device.write(NULL_CHAR*8)
	def __del__(self)
