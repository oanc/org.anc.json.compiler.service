TOMCAT=/Applications/Servers/tomcat/server-1
WAR=json-compiler.war

help:
	@echo
	@echo "GOALS"
	@echo
	@echo "     war : generates the war file."
	@echo "   peace : an alias for the above."
	@echo "   clean : removes build artifacts."
	@echo "  deploy : copies the war to the local tomcat."
	@echo "  upload : uploads the war to grid.anc.org"
	@echo "    help : prints this help screen."
	@echo

war:
	mvn package
	
peace:
	mvn package

clean:
	mvn clean
	
deploy:
	sudo cp target/$(WAR) $(TOMCAT)/webapps
	
upload:
	grid-put target/$(WAR) /usr/share/tomcat/server-1/webapps

stage:
	grid-put target/$(WAR) /home/suderman/archives
	
		
	

