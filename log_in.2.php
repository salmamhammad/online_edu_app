<?php 

require_once 'include/connect.php';
if($_POST){
     $email=$_POST['email'];

     $password=$_POST['password'];
        

    
      
    
	$stmt = $connect->prepare("select * from user where email=:email and password=:password and admin='student'");
  $stmt->execute(array(":email"=>$email,":password"=>$password));
  $row = $stmt->fetch();
  if($stmt->rowCount()>0){
   
  echo "sucssess";
  }
  else{
echo "error";

  }

 
}
?>