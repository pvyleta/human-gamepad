import Constant
import json

class KeyCommandHandler:
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
            self.key_up(keycode)
        elif keyaction == Constant.KeyActionDown:
            self.key_down(keycode)

    def key_down(self, keyCode):
        print("key: ", keyCode, " Down")

    def key_up(self, keyCode):
        print("key: ", keyCode, " Up")
