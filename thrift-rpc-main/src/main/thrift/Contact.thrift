namespace java thrift.service  
  
struct Book{
	1:i32 id
	2:string name
}

struct Contact{  
	1:i32 id  
	2:string name  
	3:i64 birthday  
	4:string phoneNo  
	5:string ipAddress  
	6:map<string,string> props  
	7:double salary
	8:set<Book> books
   
}  
  
service ContactManager{  
	void save(1:Contact contact)  
	void remove(1:i32 id)  
	list<Contact> getAll();  
	list<Contact> query(1:map<string,string> conditions)  
}  
