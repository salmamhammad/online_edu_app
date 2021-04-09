<?php 

require_once 'include/connect.php';
if($_POST){

  $id_course=$_POST['id_course'];
  $id_student=$_POST['id_student'];

  $stmt = $connect->prepare("delete from student_log where id_student=$id_student and id_course=$id_course ");
  $stmt->execute();
 
  if($stmt->rowCount()>0){

    $status="success";
  
    
 
  
      
    
  }
  else{
    $status="failed";
 

  }
  
  $response =  array('status'=>$status);
  $fp = fopen('delstudentlog.json', 'w');


  fwrite($fp, json_encode( $response));
  fclose($fp);
  
  header("location:delstudentlog.json");
}
?>