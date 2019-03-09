SRV=root@192.168.10.10

push:
	scp -r ./keyboard_emulator.py $(SRV):/usr/share/hgp/
	scp -r ./RPi_server/*.py  $(SRV):/usr/share/hgp/RPi_server/

init:
	ssh $(SRV) 'mkdir -p /usr/share/hgp/RPi_server/'
	ssh $(SRV) 'ln -sf /usr/share/hgp/RPi_server/hgp_server.py /usr/bin/hgp'

example:
	curl -d '{"keycode":"6", "action":"down"}' -H "Content-Type: application/json" -X PUT http://192.168.10.10:8000/hello/world
	sleep 2
	curl -d '{"keycode":"6", "action":"up"}' -H "Content-Type: application/json" -X PUT http://192.168.10.10:8000/hello/world
