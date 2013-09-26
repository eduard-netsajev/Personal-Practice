<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <META name="author" content="Eduard Netsajev">
        <title></title>
    </head>
    <body>
  <table><td width ="50"></td><td>
 <a href="add_new_entry.php">Add new phonebook entry</a>
        &nbsp;|&nbsp;
        <a href="phonebook_view.php">View phonebook</a><br><br>
<font size="5"><b>Add new phonebook entry</b></font><hr width="300" align="left">

<?php



if(isset($_POST['action'])){

    if($_POST['action']=='new_user'){
    $f=0;$t=0;$l=0;
    if(strlen(trim($_POST['firstname']))>0){$f=1;}
    if(strlen(trim($_POST['familyname']))>0){$l=1;}
    if(strlen(trim($_POST['mobile_phone']))>0 || strlen(trim($_POST['home_phone']))>0){$t=1;}
   if($f==1 && $l==1 && $t==1) {include_once 'lib/Phonebook.php';
include_once 'lib/db.php';
   connect_db();

   $new_user=new PhonebookEntry();
   $new_user->setData($_POST);
   $z=$new_user->save();
if($z==1){
header('Location: add_new_entry.php?status=saved');
exit;
}
if($z==0)echo '<font color="red"><h3><b>'.strtr(trim($_POST['firstname']),",",".").' '.strtr(trim($_POST['familyname']),",",".").' on andmebaasis juba olemas!</b></h3></font>';
}
else{
    echo '<font color="red"><h3><b>Sisestage palun';
    if($f==0){ echo ' eesnimi';
        if($l==0 && $t==0) echo ', perekonnanimi ja vähemalt üks telefoni number';
        elseif($l==0 && $t!=0) echo ' ja perekonnanimi';
        elseif($l!=0 && $t==0) echo ' ja vähemalt üks telefoni number';
    }
    else{
        if($l==0 && $t==0) echo ' perekonnanimi ja vähemalt üks telefoni number';
        elseif($l==0 && $t!=0) echo ' perekonnanimi';
        elseif($l!=0 && $t==0) echo ' vähemalt üks telefoni number';
        }
    echo '!</b></h3></font>';}
}}


else{
if(isset($_GET['status'])){
    if($_GET['status']=='saved')echo '<font color="green"><h3><b>Entry added!</b></h3></font>';
    if($_GET['status']=='error')echo '<font color="red"><h3><b>Entry not added!</b></h3></font>';
}}
?>

 <form method="POST" action="add_new_entry.php"><table><td width ="240" align="left">
<p align="right">Firstname:<input type="text" name="firstname" maxlength="15"<?php if(isset($_POST['firstname'])) echo ' value="'.$_POST['firstname'].'"';?>><br>
Familyname:<input type="text" name="familyname" maxlength="15"<?php if(isset($_POST['familyname'])) echo ' value="'.$_POST['familyname'].'"';?>><br>
Mobile:<input type="text" name="mobile_phone" maxlength="15"<?php if(isset($_POST['mobile_phone'])) echo ' value="'.$_POST['mobile_phone'].'"';?>><br>
Home:<input type="text" name="home_phone" maxlength="15"<?php if(isset($_POST['home_phone'])) echo ' value="'.$_POST['home_phone'].'"';?>>

<input type="submit" value="Add">
          </p></td>
   
  <input type="hidden" name="action" value="new_user">
  </table></form></td></table>
  </body>
</html>
