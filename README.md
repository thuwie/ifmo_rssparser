# ifmo_rssparser
jdk 1.8  && maven 3.0    
assembly jar  :  
     
     
     
     mvn clean test compile assembly:single    
  
run jar  :  
    
    
    java -jar ifmo_rssparser-jar-with-dependencies.jar
    
    
##Configuration 
Example in the 


     config.json 
     
file.  
Templates also configurable from the console. It has to be smth like     
     
     
    Title: ${title} ${desctiprion}
 
Tags without ${} construction gonna be used as a plain text.