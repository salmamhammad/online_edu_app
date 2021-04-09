<?php 

$DB_host = "localhost";
$DB_user = "root";
$DB_pass = "";
$DB_name = "androidcourse";
   try
   {
        $connect = new PDO("mysql:host={$DB_host};dbname={$DB_name};charset=utf8",
          $DB_user,$DB_pass);
        $connect->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
   }
   catch(PDOException $e)
   {
        echo $e->getMessage();
   }


     $email="salma@gmail.com";

     $password='salma';
        

    
      
    
	$stmt = $connect->prepare("select * from user where email=:email and password=:password and admin='student'");
  $stmt->execute(array(":email"=>$email,":password"=>$password));
 
          if($stmt->rowCount()>0){
            $row = $stmt->fetch();
            $name=$row['name']; 
            $location=$row['location'];
            $study=$row['study'];
            $age=$row['age'];
            $log_in="seccessful log in";
            $response = array('log_in'=>$log_in,'name'=> $name, 'age'=> $age ,'location'=>$location,'study'=>$study);

          }
          else{
            $log_in="error log in..try again";
 
            $response= array('log_in'=>$log_in);

          }
       $fp = fopen('log_in.json', 'w');

fwrite($fp, json_encode($response));
fclose($fp);
header("location:log_in.json");
?>