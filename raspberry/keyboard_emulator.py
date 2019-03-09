#!/usr/bin/env python3

class keyboard_elumator:
	NULL_CHAR = chr(0)

	def __init__(self):
		self.device = open("/dev/hidg0", "rb+")
		self.pressed_keys = set()
	def press_key(self, keynum):
		self.pressed_keys.add(keynum)
		self.update()
	def release_key(self, keynum):
		self.pressed_keys.discard(keynum)
		self.update()
	def update(self):
		encoding = NULL_CHAR*2
		for key in self.pressed_keys:
			encoding += char(key)
		encoding += NULL_CHAR * (6 - len(self.pressed_keys))

		self.device.write(encoding)
	def __del__(self):
		self.device.close()
