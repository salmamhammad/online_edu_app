<?php 

require_once 'include/connect.php';
if($_POST){

  $id_course=$_POST['id_course'];
  $id_student=$_POST['id_student'];
  $num=0;
  $stmt = $connect->prepare("select max(id_level) as id_level from student_log where id_student=$id_student and id_course=$id_course ");
  $stmt->execute();
 
  if($stmt->rowCount()>0){

    $status="success";
    $row = $stmt->fetch();
    $id= $row['id_level'];
   
      $stmt1 = $connect->prepare("select * from level where id=$id  ");
      $stmt1->execute();
      $level = $stmt1->fetchall();
       $num=$level['numques'];
     
    
 
  
      
    
  }

  else{
    $status="error";
 

  }
  
  $response =  array('status'=>$status,'numlevel'=>$num);
  $fp = fopen('getstudentlog.json', 'w');


  fwrite($fp, json_encode( $response));
  fclose($fp);
  
  header("location:getstudentlog.json");
}
?>