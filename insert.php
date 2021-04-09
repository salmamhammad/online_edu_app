<?php
require_once 'include/connect.php';

   


 
$stmt = $connect->prepare("INSERT INTO `student` (`id`,`name`,`email`,`location`,`password`) VALUES (NULL,:name,:email,:location,:password);");
$stmt->execute(array(':name'=>$name,':email'=>$email,':location'=>$location,':password'=>$password));

     if($stmt->rowCount()>0){
         echo "<script>alert('article is Added Successfuly');</script>";

  
     }
     else{
         echo "<script>alert('OOOps errorr ');</script>";

  

     }
?>