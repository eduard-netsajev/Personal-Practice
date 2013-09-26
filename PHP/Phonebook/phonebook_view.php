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
        <form method="GET" action="phonebook_view.php">
            <font size="5"><b>Phonebook</b></font> <hr>
First letter: <select name="first_letter">
         <option selected value ="">&nbsp</option>
<?php
if($_GET['first_letter']==""){
for($a='A'; ; $a++){
    echo '<option value="'.$a.'">'.$a.'</option>';
    if($a=='Z')break;
}}
else{for($a='A'; ; $a++){
    if($a==$_GET['first_letter'])echo '<option selected value="'.$a.'">'.$a.'</option>';
    else echo '<option value="'.$a.'">'.$a.'</option>';
    if($a=='Z')break;
}}
?></select>
&nbsp;&nbsp;&nbsp;&nbsp;Number contains:
<?php
if(isset($_GET['number_part']))echo '<input type="text" name="number_part" maxlength="12" value="'.$_GET['number_part'].'"/>';
else echo '<input type="text" name="number_part" maxlength="12" />';
?>
      <input type="submit" value="Find" />
</form><hr>
<?php
include_once 'lib/Phonebook.php';
include_once 'lib/db.php';

   connect_db();

$phonebook = new Phonebook();


if(isset($_GET['first_letter'])) {
    $_GET['number_part']=trim($_GET['number_part']);
        if($_GET['first_letter']!="" && strlen($_GET['number_part'])>0){
 foreach ($phonebook->getByFirstLetterAndNumber($_GET['first_letter'],$_GET['number_part']) as $entry) {
	echo $entry;}}


elseif($_GET['first_letter']=="" && strlen($_GET['number_part'])>0){
    foreach ($phonebook->getByPhoneNumber($_GET['number_part']) as $entry) {
	echo $entry;}}
elseif($_GET['first_letter']!="" && strlen($_GET['number_part'])<1){
    foreach ($phonebook->getByFirstLetter($_GET['first_letter']) as $entry) {
	echo $entry;}
    }
    else{foreach($phonebook->getSorted('asc') as $entry){
          echo $entry;}}
        }
else{foreach($phonebook->getSorted('asc') as $entry){
          echo $entry;}}

        ?>
      <center>  &copy; Made by Eduard Net&scaron;ajev</center>

        </td></table>
        </body>
</html>
