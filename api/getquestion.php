<?php 

require_once 'include/connect.php';

  $stmt = $connect->prepare("select * from question  ");
  $stmt->execute();
 
  if($stmt->rowCount()>0){

  $rows = $stmt->fetchall();
      foreach ($rows as $row) {
          $status="success";
          $id=$row['id'];
          $tans=$row['tans']; 
          $qtext=$row['qtext'];
          $fans=$row['fans'];
          $fans2=$row['fans2'];
          $id_model=$row['id_model'];
    
 
    $posts[] = array('status'=>$status,'id'=>$id,'tans'=> $tans, 'qtext'=> $qtext ,'fans'=>$fans,'fans2'=>$fans2,'id_model'=>$id_model);
      }
    
  }
  else{
    $status="error";
    $posts[] = array('status'=>$status);
  

  }
  $response['question'] = $posts;
  $fp = fopen('questions.json', 'w');


  fwrite($fp, json_encode( $response));
  fclose($fp);
  
  header("location:questions.json");

?>