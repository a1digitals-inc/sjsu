
S3 in a Tree
======================


•	University Name: http://www.sjsu.edu/  
•	Course: Cloud Technologies  
•	Professor: Sanjay Garje  
•	ISA: Divyankitha Urs  
•	Student: [Lin Cheng](https://www.linkedin.com/in/lin-cheng-08b31630/)  

(Please find more details in the Canvas [document](https://sjsu.instructure.com/files/48300370))  
	  
•	At a glance:  
  
![a](https://github.com/xzchenglin/sjsu/blob/master/awsProj1/sc.png)  
  
•	Features:  
   o Browse within a S3 bucket in a tree  
   o	Upload file to the selected path  
   o	Update the uploaded file  
   o	Down load file  
   o	Delete file  
   o	Show file metadata  
   o	Get notification when files get deleted  

•	Pre-requisites Setup:  
   o	AWS requirement  
    In this project, the following services are used:  
      - EC2  
      - ELB  
      - Lambda  
      - AutoScaling Group  
      - RDS  
      - CloudFront  
      - S3  
      - S3 Transfer Acceleration  
      - R53  
      - CloudWatch  
      - SNS  
      - WorkMail  
      - Congnito    
      
   However, the minimal requirement is just a EC2 server with public access.   
  
   o	Required software: JDK 1.8+, Python 2.7+, Maven 3.3+  
   o	Local configuration: AWS SDK [credentials](http://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html), /opt/proj1/config.txt(like below)  
      dbserver=host_or_endpoint  
      dbport=5432  
      db=dbName  
      dbuser=user  
      dbpwd=pwd  
  
•	How to set up and run project locally  
   o	Check out source code from Github  
   o	cd awsProj1, and do a "mvn clean install"  
   o	Copy all stuff in awsProj1/proj1-service/target to the working folder (/opt/proj1)  
   o	Copy /proj1-web/src/python/server.py, /proj1-web/src/webapp/index.html + main.css to the working folder  
   o	Go to working folder and run:  
       - python server.py  
       - java -cp proj1-service-1.0.jar  service.camel.RestService  
   o	Check http://localhost  
     
     
 •	References  
   o	[Vue.js documents](https://vuejs.org/v2/guide/)  
   o	[Simple Web server by Python (Chinese)](https://zhuanlan.zhihu.com/p/21323273)  


