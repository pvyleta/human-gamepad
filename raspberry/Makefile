SRV=root@192.168.10.10

push:
	scp -r ./keyboard_emulator.py $(SRV):/usr/share/hgp/
	scp -r ./RPi_server/*.py  $(SRV):/usr/share/hgp/RPi_server/

init:
	ssh $(SRV) 'mkdir -p /usr/share/hgp/RPi_server/'
	ssh $(SRV) 'ln -sf /usr/share/hgp/RPi_server/hgp_server.py /usr/bin/hgp'
