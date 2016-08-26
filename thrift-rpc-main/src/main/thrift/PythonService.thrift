namespace java thrift.service 
   
service PythonService{  
	string get(1:i32 id)  
	i32 remove(1:i32 id)  
}  

