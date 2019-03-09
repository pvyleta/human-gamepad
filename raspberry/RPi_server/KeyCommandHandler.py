import Constant
import json

import sys
sys.path.append("..")
sys.path.append("/usr/share/hgp/")
import keyboard_emulator

class KeyCommandHandler:
    def __init__(self):
        self.keyboard = keyboard_emulator.keyboard_emulator()

    def handle_input(self, data):
        jTxt = data.decode('utf-8')
        print(jTxt)
        jData = json.loads(jTxt)

        if Constant.KeyAction not in jData or Constant.KeyAction not in jData:
            print("Wrong data format received")
            return
        self._handle_key(jData[Constant.KeyCode], jData[Constant.KeyAction])

    def _handle_key(self, keyCodeStr, keyaction):
        keycode=int(keyCodeStr)
        if keyaction == Constant.KeyActionUp:
            self.key_released(keycode)
        elif keyaction == Constant.KeyActionDown:
            self.key_pressed(keycode)

    def key_pressed(self, keyCode):
        self.keyboard.press_key(keyCode)
        print("key: ", keyCode, " pressed")

    def key_released(self, keyCode):
        self.keyboard.release_key(keyCode)
        print("key: ", keyCode, " released")
