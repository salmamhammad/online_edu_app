<?php 

require_once 'include/connect.php';
if($_POST){

      
    
  

  $stmt = $connect->prepare("select * from level  ");
  $stmt->execute();
 
  if($stmt->rowCount()>0){

  $rows = $stmt->fetchall();
      foreach ($rows as $row) {
          $status="success";
          $id=$row['id'];
          $name=$row['name']; 
          $idcourse=$row['idcourse'];
          $numques=$row['numques'];
        
    $posts[] = array('status'=>$status,'id'=>$id,'name'=> $name, 'idcourse'=> $idcourse ,'numques'=>$numques);
      }
    
  }
  else{
    $status="error";
    $posts[] = array('status'=>$status);
  

  }
  $response['level'] = $posts;

  
  $id_student=$_POST['id_student'];
  $stmt1 = $connect->prepare("select * from student_log where id_student=:id_student  ");
  $stmt1->execute(array(':id_student'=>$id_student));
 
  if($stmt1->rowCount()>0){

 
    $rows = $stmt1->fetchall();

    foreach ($rows as $row) {
      $status="success";

      
              $id_level=$row['id_level'];

              $stmt2 = $connect->prepare("select * from level where id=$id_level  ");
              $stmt2->execute();
              $level = $stmt2->fetch();
               $num=$level['numques'];
              $id_course=$row['id_course'];
              $mark=$row['mark'];
              $post2[] = array('status'=>$status,'id_level'=>$num,'id_course'=> $id_course, 'mark'=> $mark);


    }
  }
    else{
      $status="error";
      $id_level=1;
      $post2[] = array('status'=>$status,'numlevel'=>$id_level);
    
  
    }
  
  

  $response['student'] = $post2;
  $fp = fopen('levels.json', 'w');


  fwrite($fp, json_encode( $response));
  fclose($fp);
  
  header("location:levels.json");
}
?>