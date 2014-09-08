
all: pkg_istribute.zip
	@echo '' > /dev/null

packages:
	mkdir packages
packages/com_istribute.zip: packages
	sh -c 'cd com_istribute && zip -r ../packages/com_istribute.zip .'
packages/istributebtn.zip: packages
	sh -c 'cd istributebtn && zip -r ../packages/istributebtn.zip .'
packages/istributeview.zip: packages
	sh -c 'cd istributeview && zip -r ../packages/istributeview.zip .'

pkg_istribute.zip:  packages/com_istribute.zip packages/istributebtn.zip packages/istributeview.zip
	zip -r pkg_istribute.zip pkg_istribute.xml packages

clean:
	rm -f packages/com_istribute.zip packages/istributebtn.zip packages/istributeview.zip
	if [ -d packages ]; then rmdir packages; fi
	rm -f pkg_istribute.zip
