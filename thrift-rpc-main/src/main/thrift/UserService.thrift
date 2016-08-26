namespace java thrift.service
service UserService{
	void getUser(1:string uid),
	string getUserName(1:string uid,2:i32 age)
}
